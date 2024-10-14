package egovframework.cbm.web.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import egovframework.cbm.web.common.model.ResponseVO;
import egovframework.cbm.web.common.model.dto.IdsDto;
import egovframework.cbm.web.user.model.dto.RoleMappingDto;
import egovframework.cbm.web.user.model.dto.TokenInfoDto;
import egovframework.cbm.web.user.model.dto.UserDto;
import egovframework.cbm.web.user.model.dto.UserInfoDto;
import egovframework.cbm.web.user.service.UserInfoService;
import egovframework.utils.SwaggerResponse;
import egovframework.utils.Utils;
import egovframework.utils.enums.Yn;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("user")
@Tag(name = "UserInfoController", description = "사용자 정보 RestController")
public class UserInfoController {

        @Autowired
        private UserInfoService userInfoService;

        @Operation(summary = "사용자 목록 조회", description = "사용자 목록을 조회", tags = { "UserInfoController" }, responses = {
                        @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponsePageUserInfo.class))),
                        @ApiResponse(responseCode = "500", description = "로그인 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseError.class)))
        })
        @GetMapping("/list")
        public ResponseVO<Page<UserInfoDto>> searchPage(
                        @RequestParam(defaultValue = "") @Schema(description = "검색 키워드", example = "Test") String keyword,
                        @RequestParam(defaultValue = "1") @Schema(description = "페이지 번호", example = "1") int page) {
                return new ResponseVO<>(userInfoService.getPage(keyword,
                                Utils.getPageable(page, "userInfoSeq", Sort.Direction.ASC)));
        }

        @Operation(summary = "사용자 정보 조회", description = "사용자 정보를 단건 조회.", tags = { "UserInfoController" }, responses = {
                        @ApiResponse(responseCode = "200", description = "조회 성공"),
                        @ApiResponse(responseCode = "500", description = "조회 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseError.class)))
        })
        @GetMapping("/list/{seq}")
        public ResponseVO<UserInfoDto> searchUser(
                        @Parameter(description = "사용자 정보 순번", in = ParameterIn.PATH, example = "1") @Valid @PathVariable int seq) {
                return new ResponseVO<>(userInfoService.getUserInfo(seq));
        }

        @PostMapping("/login")
        @Operation(summary = "로그인", description = "사용자 로그인", tags = { "UserInfoController" }, responses = {
                        @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponsePageUserToken.class))),
                        @ApiResponse(responseCode = "500", description = "로그인 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseError.class)))
        })
        public ResponseVO<UserDto.UserToken> login(@RequestBody @Valid UserDto.UserLogin userLogin,
                        HttpServletRequest request) throws Exception {
                String loginIp = Utils.getClientIP(request);
                return new ResponseVO<>(userInfoService.getLoginToken(userLogin, loginIp));
        }

        @PostMapping("/refresh")
        @Operation(summary = "사용자 토큰 재발급", description = "사용자의 만료된 토큰을 재발급 받는다", tags = {
                        "UserInfoController" }, responses = {
                                        @ApiResponse(responseCode = "200", description = "발급 성공", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponsePageUserToken.class))),
                                        @ApiResponse(responseCode = "500", description = "발급 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseError.class)))
                        })
        public ResponseVO<UserDto.UserToken> refresh(@RequestBody @Valid TokenInfoDto tokenInfoDto) throws Exception {
                return new ResponseVO<>(userInfoService.tokenRefresh(tokenInfoDto));
        }

        @PostMapping("/logout")
        @Operation(summary = "사용자 로그아웃", description = "사용자 로그아웃", tags = { "UserInfoController" }, responses = {
                        @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
                        @ApiResponse(responseCode = "500", description = "로그아웃 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseError.class)))
        })
        public ResponseVO<String> logout(@RequestBody @Valid TokenInfoDto tokenInfoDto) throws Exception {
                userInfoService.logout(tokenInfoDto);
                return new ResponseVO<>("");
        }

        @Operation(summary = "사용자 아이디 중복 체크", description = "사용자 아이디 중복 체크", tags = {
                        "UserInfoController" }, responses = {
                                        @ApiResponse(responseCode = "200", description = "요청 성공", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseBoolean.class))),
                                        @ApiResponse(responseCode = "500", description = "요청 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseError.class)))
                        })
        @PostMapping("/check/id")
        public ResponseVO<Boolean> checkUserId(@RequestBody @Valid UserDto.UserCheck userCheck) {
                return new ResponseVO<>(userInfoService.getUserInfoById(userCheck.getUserId()) == null);
        }

        @Operation(summary = "사용자 연락처 중복 체크", description = "사용자 연락처 중복 체크", tags = {
                        "UserInfoController" }, responses = {
                                        @ApiResponse(responseCode = "200", description = "요청 성공", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseBoolean.class))),
                                        @ApiResponse(responseCode = "500", description = "요청 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseError.class)))
                        })
        @PostMapping("/check/adr")
        public ResponseVO<Boolean> checkUserCntadr(@RequestBody @Valid UserDto.UserCheck userCheck) {
                return new ResponseVO<>(userInfoService.getUserInfoByCntadr(userCheck.getUserCntadr()) == null);
        }

        @Operation(summary = "사용자 이메일 인증메일 발송", description = "사용자 이메일 인증메일 발송", tags = {
                        "UserInfoController" }, responses = {
                                        @ApiResponse(responseCode = "200", description = "발송 성공", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseBoolean.class))),
                                        @ApiResponse(responseCode = "500", description = "발송 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseError.class)))
                        })
        @PostMapping("/send/email")
        public ResponseVO<Boolean> sendEmail(
                        @RequestBody @Valid @Schema(implementation = SwaggerResponse.SendUserEmail.class) UserDto.UserEmailVerify userEmailVerify)
                        throws Exception {
                return new ResponseVO<>(userInfoService.sendEmail(userEmailVerify.getUserEmail()));
        }

        @Operation(summary = "사용자 이메일 인증코드 검증", description = "사용자 이메일 인증코드 검증", tags = {
                        "UserInfoController" }, responses = {
                                        @ApiResponse(responseCode = "200", description = "요청 성공", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseBoolean.class))),
                                        @ApiResponse(responseCode = "500", description = "요청 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseError.class)))
                        })
        @PostMapping("/verify/email")
        public ResponseVO<Boolean> verifyEmail(@RequestBody @Valid UserDto.UserEmailVerify userEmailVerify) {
                return new ResponseVO<>(userInfoService.verifyEmailCode(userEmailVerify));
        }

        @PostMapping("/create")
        @Operation(summary = "사용자 등록", description = "사용자 정보를 저장", tags = { "UserInfoController" }, responses = {
                        @ApiResponse(responseCode = "200", description = "등록 성공"),
                        @ApiResponse(responseCode = "500", description = "등록 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseError.class)))
        })
        public ResponseVO<String> create(@RequestBody @Valid UserInfoDto userInfoDto) throws Exception {
                userInfoService.upsertUserInfo(0, userInfoDto);
                return new ResponseVO<>("");
        }

        @PutMapping("/{seq}")
        @Operation(summary = "사용자 정보 수정", description = "사용자 정보를 수정한다.", tags = { "UserInfoController" }, responses = {
                        @ApiResponse(responseCode = "200", description = "수정 성공"),
                        @ApiResponse(responseCode = "500", description = "수정 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseVaildError.class)))
        })
        public ResponseVO<String> update(
                        @Parameter(description = "사용자 정보 순번", in = ParameterIn.PATH, example = "1") @PathVariable int seq,
                        @Valid @RequestBody UserInfoDto userInfoDto) throws Exception {
                userInfoService.upsertUserInfo(seq, userInfoDto);
                return new ResponseVO<>("");
        }

        @DeleteMapping("/{seq}")
        @Operation(summary = "사용자 삭제", description = "사용자 정보 삭제", tags = { "UserInfoController" }, responses = {
                        @ApiResponse(responseCode = "200", description = "삭제 성공"),
                        @ApiResponse(responseCode = "500", description = "삭제 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseError.class)))
        })
        public ResponseVO<String> delete(
                        @Parameter(description = "사용자 정보 순번", in = ParameterIn.PATH, example = "1") @Valid @PathVariable int seq)
                        throws Exception {
                userInfoService.deleteUserInfo(seq);
                return new ResponseVO<>("");
        }

        @PostMapping("/search-id")
        @Operation(summary = "사용자 아이디 찾기", description = "사용자 아이디 찾기", tags = { "UserInfoController" }, responses = {
                        @ApiResponse(responseCode = "200", description = "찾기 성공", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseUserId.class))),
                        @ApiResponse(responseCode = "500", description = "찾기 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseVaildError.class)))
        })
        public ResponseVO<List<String>> searchId(
                        @RequestBody @Valid @Schema(implementation = SwaggerResponse.SearchUserId.class) UserDto.UserSearch userSearch)
                        throws Exception {
                return new ResponseVO<>(userInfoService.searchId(userSearch));
        }

        @PostMapping("/search-pw")
        @Operation(summary = "사용자 비밀번호 찾기", description = "사용자 비밀번호 찾기", tags = { "UserInfoController" }, responses = {
                        @ApiResponse(responseCode = "200", description = "찾기 성공", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseUserPw.class))),
                        @ApiResponse(responseCode = "500", description = "찾기 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseVaildError.class)))
        })
        public ResponseVO<String> searchPw(
                        @RequestBody @Valid UserDto.UserSearch userSearch) {
                return new ResponseVO<>(userInfoService.searchPw(userSearch));
        }

        @PutMapping("/approve/{seq}")
        @Operation(summary = "사용자 승인", description = "사용자를 승인한다", tags = { "UserInfoController" }, responses = {
                        @ApiResponse(responseCode = "200", description = "승인 성공"),
                        @ApiResponse(responseCode = "500", description = "승인 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseError.class)))
        })
        public ResponseVO<String> approveUserInfo(
                        @Parameter(description = "사용자 정보 순번", in = ParameterIn.PATH, example = "1") @PathVariable int seq)
                        throws EntityNotFoundException {
                userInfoService.apprveUserInfo(seq, Yn.Y);
                return new ResponseVO<>("");
        }

        @PutMapping("/approve")
        @Operation(summary = "사용자 다건 승인", description = "사용자를 다건 승인한다", tags = { "UserInfoController" }, responses = {
                        @ApiResponse(responseCode = "200", description = "승인 성공"),
                        @ApiResponse(responseCode = "500", description = "승인 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseError.class)))
        })
        public ResponseVO<String> approveUserInfoList(@Valid @RequestBody IdsDto idsDto)
                        throws EntityNotFoundException {
                userInfoService.apprveUserInfoList(idsDto, Yn.Y);
                return new ResponseVO<>("");
        }

        @PutMapping("/revoke/{seq}")
        @Operation(summary = "사용자 승인 취소", description = "사용자를 승인 취소한다", tags = { "UserInfoController" }, responses = {
                        @ApiResponse(responseCode = "200", description = "승인 성공"),
                        @ApiResponse(responseCode = "500", description = "승인 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseError.class)))
        })
        public ResponseVO<String> revokeUserInfo(
                        @Parameter(description = "사용자 정보 순번", in = ParameterIn.PATH, example = "1") @PathVariable int seq)
                        throws EntityNotFoundException {
                userInfoService.apprveUserInfo(seq, Yn.N);
                return new ResponseVO<>("");
        }

        @PutMapping("/revoke")
        @Operation(summary = "사용자 다건 승인 취소", description = "사용자를 다건 승인 취소한다", tags = {
                        "UserInfoController" }, responses = {
                                        @ApiResponse(responseCode = "200", description = "승인 성공"),
                                        @ApiResponse(responseCode = "500", description = "승인 실패", content = @Content(schema = @Schema(implementation = SwaggerResponse.ResponseError.class)))
                        })
        public ResponseVO<String> revokeUserInfoList(@Valid @RequestBody IdsDto idsDto) throws EntityNotFoundException {
                userInfoService.apprveUserInfoList(idsDto, Yn.N);
                return new ResponseVO<>("");
        }

        @GetMapping("/test")
        public ResponseVO<RoleMappingDto> testAPi() {
                return new ResponseVO<>(userInfoService.findA("ROLE_MNG_CD"));
        }

}
