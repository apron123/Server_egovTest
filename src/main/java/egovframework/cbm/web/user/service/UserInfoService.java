package egovframework.cbm.web.user.service;

import egovframework.cbm.web.common.model.dto.IdsDto;
import egovframework.cbm.web.user.model.dto.*;
import egovframework.cbm.web.user.model.entity.TokenInfo;
import egovframework.cbm.web.user.model.entity.UserInfo;
import egovframework.cbm.web.user.model.mapper.UserMapper;
import egovframework.cbm.web.user.repository.loginhist.LoginHistRepository;
import egovframework.cbm.web.user.repository.rolemapping.RoleMappingRepository;
import egovframework.cbm.web.user.repository.tokeninfo.TokenInfoRepository;
import egovframework.cbm.web.user.repository.userinfo.UserInfoRepository;
import egovframework.com.jwt.EgovJwtTokenUtil;
import egovframework.utils.Constants;
import egovframework.utils.Encryption;
import egovframework.utils.Utils;
import egovframework.utils.enums.Yn;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static egovframework.cbm.config.database.DatabaseConstants.BaseDatabase.tx_manager;

/**
 * 유저 정보 서비스 로직
 *
 * @author 이상민
 * @since 2024.07.09 12:00
 */
@RequiredArgsConstructor
@Service("userInfoService")
public class UserInfoService {

        private final UserInfoRepository userInfoRepository;

        private final TokenInfoRepository tokenInfoRepository;

        private final LoginHistRepository loginHistRepository;

        private final JavaMailSender javaMailSender;

        // private final UserMapper mapper;

        private final EgovJwtTokenUtil jwtTokenUtil;

        @Autowired
        private UserMapper mapper;

        @Autowired
        private RoleMappingRepository roleMappingRepository;

        private final Map<String, EmailCodeDto> emailCodeMap = new ConcurrentHashMap<>();


        /**
         * 검색 필터 데이터 기준으로 페이지 객체 생성하는 메서드
         *
         * @param keyword 검색 키워드
         * @return Page<UserInfoDto> 페이지 데이터
         */
        @Transactional(transactionManager = tx_manager, readOnly = true)
        public Page<UserInfoDto> getPage(String keyword, Pageable pageable) {
                return userInfoRepository.findByFilters(keyword, pageable)
                                .map(mapper::toDto);
        }

        /**
         * 로그인 토큰 생성 매서드
         * PW 확인 후 유저 정보 가져오기
         *
         * @param userLogin 로그인 정보
         * @return UserTokenDto 로그인 토큰
         */
        @Transactional(transactionManager = tx_manager)
        public UserDto.UserToken getLoginToken(UserDto.UserLogin userLogin, String loginIp) throws Exception {

                UserDto.UserToken token = userInfoRepository
                                .findOneByUserIdAndUserPwAndUseYn(userLogin.getUserId(),
                                                userLogin.createEncryptPassword(), 'Y')
                                .map(userInfo -> UserDto.UserToken.builder()
                                                .userId(userInfo.getUserId())
                                                .userName(userInfo.getUserNm())
                                                .accessToken(jwtTokenUtil.generateToken(userInfo))
                                                .refreshToken(refreshTokenSave(userInfo.getUserId()))
                                                .build())
                                .orElseThrow(Exception::new);

                /*
                 * 로그인 성공시 로그인 이력 저장
                 * IP, 시간, 유저 ID 저장 중
                 */
                if (token != null) {
                        loginHistRepository
                                        .save(mapper.toEntity(new LoginHistDto(0, userLogin.getUserId(),
                                                        LocalDateTime.now(), loginIp)));
                }

                return token;
        }

        /**
         * 만료된 토큰을 새로운 토큰으로 발급하는 매서드
         *
         *
         * @param tokenInfoDto 리플레시 토큰
         * @return UserTokenDto 새로운 로그인 토큰
         */
        @Transactional(transactionManager = tx_manager)
        public UserDto.UserToken tokenRefresh(TokenInfoDto tokenInfoDto) throws Exception {
                return userInfoRepository
                                .findOneByUserId(jwtTokenUtil.getUserIdFromToken(tokenInfoDto.getRefreshToken()))
                                .map(userInfo -> {
                                        UserDto.UserToken userTokenDto = UserDto.UserToken.builder()
                                                        .userName(userInfo.getUserNm())
                                                        .accessToken(jwtTokenUtil.generateToken(userInfo))
                                                        .build();
                                        return jwtTokenUtil.isTokenExpired(tokenInfoDto.getRefreshToken())
                                                        ? userTokenDto.updateRefreshToken(
                                                                        refreshTokenSave(userInfo.getUserId()))
                                                        : userTokenDto.updateRefreshToken(
                                                                        tokenInfoDto.getRefreshToken());
                                }).orElseThrow(Exception::new);
        }

        /**
         * 리플레시 토큰 저장하는 매서드
         *
         *
         * @param userId 사용자 아이디
         * @return String 리플레시 토큰
         */
        private String refreshTokenSave(String userId) {
                return Optional.ofNullable(jwtTokenUtil.generateRfToken(userId))
                                .map(refreshToken -> tokenInfoRepository.save(tokenInfoRepository.findByUserId(userId)
                                                .map(tokenInfo -> tokenInfo.updateRefreshToken(refreshToken))
                                                .orElse(TokenInfo.builder()
                                                                .userId(userId)
                                                                .refreshToken(refreshToken)
                                                                .build()))
                                                .getRefreshToken())
                                .orElse(null);
        }

        /**
         * 토큰을 삭제하는 매서드
         *
         * @param tokenInfoDto 리플레시 토큰
         */
        public void logout(TokenInfoDto tokenInfoDto) throws Exception {
                tokenInfoRepository.deleteByUserId(jwtTokenUtil.getUserIdFromToken(tokenInfoDto.getRefreshToken()));
        }

        /**
         * 데이터를 저장 또는 수정하는 메서드
         *
         * @param id          데이터 ID
         * @param userInfoDto userInfo 데이터
         */
        @Transactional(transactionManager = tx_manager)
        public void upsertUserInfo(int id, UserInfoDto userInfoDto) throws Exception {
                userInfoRepository.save(mapper.toEntity(userInfoDto.upsertFlag(id)));
        }

        /**
         * 사용자 정보 삭제하는 메서드
         *
         * @param seq 사용자 정보 순번
         */
        @Transactional(transactionManager = tx_manager)
        public void deleteUserInfo(int seq) {
                userInfoRepository.deleteById(seq);
        }

        /**
         * 신규 PW 생성하는 메서드
         *
         * @param userSearch 서치 데이터
         * @return String newPassword 데이터
         */
        @Transactional(transactionManager = tx_manager)
        public String searchPw(UserDto.UserSearch userSearch) {
                return Optional.ofNullable(userSearch.getUserId())
                                .flatMap(userId -> userInfoRepository
                                                .findOneByUserIdAndUserNm(userId, userSearch.getUserName())
                                                .map(userInfo -> {
                                                        try {
                                                                String newPassword = Encryption.createRandomCode(
                                                                                Constants.TEMPORARY_PASSWORD);
                                                                userInfo.updatePw(Encryption.encryptPassword(
                                                                                newPassword,
                                                                                userId));
                                                                userInfoRepository.save(userInfo);
                                                                return newPassword;
                                                        } catch (Exception e) {
                                                                throw new RuntimeException(e);
                                                        }
                                                }))
                                .orElse(null);
        }

        /**
         * 사용자 아이디를 조회 하는 메서드
         *
         * @param userSearch 서치 데이터
         * @return List<String> returnList 데이터
         */
        @Transactional(transactionManager = tx_manager)
        public List<String> searchId(UserDto.UserSearch userSearch) {
                List<UserInfo> userInfoList = userInfoRepository.findByUserNm(userSearch.getUserName());
                List<String> returnList = new ArrayList<>();
                for (UserInfo entity : userInfoList) {
                        returnList.add(Utils.maskUserId((mapper.toDto(entity).getUserId())));
                }
                return returnList;
        }

        /**
         * 사용자 정보 순번으로 정보 조회 하는 메서드
         *
         * @param seq 사용자 정보 순번
         * @return UserInfoDto 사용자 정보 데이터
         */
        @Transactional(transactionManager = tx_manager, readOnly = true)
        public UserInfoDto getUserInfo(int seq) {
                return userInfoRepository.findById(seq)
                        .map(mapper::toDto)
                        .orElse(null);
        }

        /**
         * 사용자 아이디로 정보 조회 하는 메서드
         *
         * @param userId 사용자 아이디
         * @return UserInfoDto 사용자 정보 데이터
         */
        @Transactional(transactionManager = tx_manager, readOnly = true)
        public UserInfoDto getUserInfoById(String userId) {
                return userInfoRepository.findOneByUserId(userId)
                                .map(mapper::toDto)
                                .orElse(null);
        }

        /**
         * 사용자 연락처로 정보 조회 하는 메서드
         *
         * @param userCntadr 사용자 연락처
         * @return UserInfoDto 사용자 정보 데이터
         */
        @Transactional(transactionManager = tx_manager, readOnly = true)
        public UserInfoDto getUserInfoByCntadr(String userCntadr) {
                return userInfoRepository.findByUserCntadr(userCntadr)
                        .map(mapper::toDto)
                        .orElse(null);
        }

        public RoleMappingDto findA(String aa) {
                return roleMappingRepository.findOneByUserRoleCode(aa).map(mapper::toDto).orElse(null);
        }

        /**
         * 사용자 정보 순번으로 사용자 승인하는 메서드
         *
         * @param seq    UserInfo Seq
         * @param yn     승인여부
         */
        @Transactional(transactionManager = tx_manager)
        public void apprveUserInfo(int seq, Yn yn) throws EntityNotFoundException {
                UserInfo userInfo = userInfoRepository.findById(seq)
                        .orElseThrow(() -> new EntityNotFoundException("UserInfo not found with seq: " + seq));
                userInfoRepository.save(userInfo.updateUseYn(yn.getC()));
        }

        /**
         * 사용자 정보 순번으로 사용자 다건 승인하는 메서드
         *
         * @param idsDto 다건 처리 데이터
         * @param yn     승인여부
         */
        @Transactional(transactionManager = tx_manager)
        public void apprveUserInfoList(IdsDto idsDto, Yn yn) throws EntityNotFoundException {
                Set<UserInfo> userInfos = new HashSet<>(userInfoRepository.findAllByUserInfoSeqIn(idsDto.getIds()));

                if (userInfos.isEmpty() || userInfos.size() != idsDto.getIds().size()) {
                        throw new EntityNotFoundException("One or more UserInfo not found for given ids.");
                }

                userInfoRepository.saveAll(
                        userInfos.stream()
                                .map(entity -> entity.updateUseYn(yn.getC()))
                                .collect(Collectors.toSet())
                );
        }

        /**
         * 메일 인증 코드를 발송하는 메서드
         *
         * @param mail 인증할 메일
         * @return boolean 메일 전송 성공 유무
         */
        public boolean sendEmail(String mail) throws Exception {

                if (userInfoRepository.findByUserEmail(mail).isPresent()) {
                        return false;
                } else {
                        MimeMessage message = javaMailSender.createMimeMessage();
                        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
                        String code = Utils.createStrNumber();

                        helper.setTo(mail);
                        helper.setSubject("이메일 인증");

                        String text = "<h3>요청하신 인증 번호입니다.</h3>" +
                                "<h1>" + code + "</h1>" +
                                "<h3>감사합니다.</h3>";

                        helper.setText(text, true);

                        javaMailSender.send(message);

                        emailCodeMap.put(mail, EmailCodeDto.builder()
                                .code(code)
                                .expiryTime(LocalDateTime.now().plusMinutes(5))
                                .build());

                        return true;
                }

        }

        /**
         * 인증번호 코드 검증하는 메서드
         *
         * @param userEmailVerify 사용자 이메일 인증 정보
         * @return boolean 메일 전송 성공 유무
         */
        public boolean verifyEmailCode(UserDto.UserEmailVerify userEmailVerify) {
                removeExpiredCodes();
                return Optional.ofNullable(emailCodeMap.get(userEmailVerify.getUserEmail()))
                        .filter(data -> !data.isExpired())
                        .map(data -> data.getCode().equals(userEmailVerify.getCode()))
                        .orElseGet(() -> false);
        }

        /**
         * 만료된 코드 삭제하는 메서드
         *
         */
        private void removeExpiredCodes() {
                emailCodeMap.entrySet().removeIf(entry -> entry.getValue().isExpired());
        }

}
