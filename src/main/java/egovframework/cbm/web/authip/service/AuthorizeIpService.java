package egovframework.cbm.web.authip.service;

import egovframework.cbm.web.authip.model.mapper.AuthorizeIpMapper;
import egovframework.cbm.web.authip.model.dto.AuthrzIpCtlgDto;
import egovframework.cbm.web.authip.repository.AuthrzIpCtlgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static egovframework.cbm.config.database.DatabaseConstants.BaseDatabase.tx_manager;

/**
 * 인가 IP 서비스 로직
 *
 * @author 이상민
 * @since 2024.07.09 12:00
 */
@RequiredArgsConstructor
@Service("authorizeIpService")
public class AuthorizeIpService {

    private final AuthrzIpCtlgRepository authrzIpCtlgRepository;

    private final AuthorizeIpMapper mapper;

    /**
     * 검색 필터 데이터 기준으로 페이지 객체 생성하는 메서드
     *
     * @param keyword                검색 키워드
     * @param ip                     IP 키워드
     * @param pageable               페이지 정보 데이터
     * @return Page<AuthrzIpCtlgDto> 페이지 데이터
     */
    @Transactional(transactionManager = tx_manager, readOnly = true)
    public Page<AuthrzIpCtlgDto> getPage(String keyword, String ip, Pageable pageable) throws Exception {
        return authrzIpCtlgRepository.findByFilters(keyword, ip, pageable)
                .map(mapper::toDto);
    }

    /**
     * 데이터를 id값 기준으로 조회하는 메서드
     *
     * @param id                데이터 ID
     * @return AuthrzIpCtlgDto  AuthrzIpCtlg 데이터
     */
    @Transactional(transactionManager = tx_manager, readOnly = true)
    public AuthrzIpCtlgDto getAuthrzIpCthlg(int id) throws Exception {
        return authrzIpCtlgRepository.findById(id)
                .map(mapper::toDto)
                .orElse(null);
    }

    /**
     * 데이터를 저장 또는 수정하는 메서드
     *
     * @param id                데이터 ID
     * @param authrzIpCtlgDto   AuthrzIpCtlg 데이터
     */
    @Transactional(transactionManager = tx_manager)
    public void upsertDctnInfo(int id, AuthrzIpCtlgDto authrzIpCtlgDto) throws Exception {
        authrzIpCtlgRepository.save(mapper.toEntity(authrzIpCtlgDto.upsertFlag(id)));
    }

    /**
     * 데이터를 삭제하는 메서드
     *
     * @param id  데이터 ID
     */
    @Transactional(transactionManager = tx_manager)
    public void deleteDctnInfo(int id) throws Exception {
        authrzIpCtlgRepository.deleteById(id);
    }

}
