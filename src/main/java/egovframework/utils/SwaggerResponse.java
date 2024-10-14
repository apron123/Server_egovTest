package egovframework.utils;

import egovframework.cbm.web.authip.model.dto.AuthrzIpCtlgDto;
import egovframework.cbm.web.common.model.ResponseVO;
import egovframework.cbm.web.common.model.dto.CmncdInfoDto;
import egovframework.cbm.web.common.model.dto.IdsDto;
import egovframework.cbm.web.dctn.model.dto.DctnDomainInfoDto;
import egovframework.cbm.web.dctn.model.dto.DctnInfoDto;
import egovframework.cbm.web.user.model.dto.UserDto;
import egovframework.cbm.web.user.model.dto.UserInfoDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.Set;

@UtilityClass
public class SwaggerResponse {

    /**
     * Common Swagger
     */
    @Schema(name = "response_error")
    public static class ResponseError extends ResponseVO<String> {

        @Schema(description = "응답 상태 코드", example = "5000")
        private int resultCode;
        @Schema(description = "응답 데이터", example = " ")
        private String resultData;
        @Schema(description = "응답 메세지", example = "유효하지 않는 값이 발생했습니다!")
        private String resultMsg;
        @Schema(description = "응답 성공 여부", example = "false")
        private boolean resultFlag;

    }

    @Schema(name = "response_vaild_error")
    public static class ResponseVaildError extends ResponseVO<Map<String, List<String>>> {

        @Schema(description = "응답 상태 코드", example = "5002")
        private int resultCode;
        @Schema(description = "응답 데이터", example = "{\"errorMessage\": [\"Y, N으로 입력해주세요.\"]}")
        private Map<String, List<String>> resultData;
        @Schema(description = "응답 메세지", example = "유효하지 않는 값이 발생했습니다!")
        private String resultMsg;
        @Schema(description = "응답 성공 여부", example = "false")
        private boolean resultFlag;

    }

    @Schema(name = "response_unauthorized_error")
    public static class ResponseUnauthorized extends ResponseVO<Map<String, List<String>>> {

        @Schema(description = "응답 상태 코드", example = "401")
        private int resultCode;
        @Schema(description = "응답 데이터", example = "{\"errorMessage\": [\"유효하지 않은 토큰입니다!\"]}")
        private Map<String, List<String>> resultData;
        @Schema(description = "응답 메세지", example = "유효하지 않는 토큰입니다!")
        private String resultMsg;
        @Schema(description = "응답 성공 여부", example = "false")
        private boolean resultFlag;

    }

    @Schema(name = "response_forbidden_error")
    public static class ResponseForbidden extends ResponseVO<Map<String, List<String>>> {

        @Schema(description = "응답 상태 코드", example = "403")
        private int resultCode;
        @Schema(description = "응답 데이터", example = "{\"errorMessage\": [\"권한이 없습니다.\"]}")
        private Map<String, List<String>> resultData;
        @Schema(description = "응답 메세지", example = "권한이 없습니다!")
        private String resultMsg;
        @Schema(description = "응답 성공 여부", example = "false")
        private boolean resultFlag;

    }

    @Schema(name = "response_boolean")
    public static class ResponseBoolean extends ResponseVO<Boolean> {

        @Schema(description = "응답 상태 코드", example = "200")
        private int resultCode;
        @Schema(description = "응답 데이터", example = "true")
        private Boolean resultData;
        @Schema(description = "응답 메세지", example = "")
        private String resultMsg;
        @Schema(description = "응답 성공 여부", example = "true")
        private boolean resultFlag;

    }

    @Schema(name = "response_long")
    public static class ResponseLong extends ResponseVO<Long> {

        @Schema(description = "응답 상태 코드", example = "200")
        private int resultCode;
        @Schema(description = "응답 데이터", example = "10")
        private Long resultData;
        @Schema(description = "응답 메세지", example = "")
        private String resultMsg;
        @Schema(description = "응답 성공 여부", example = "true")
        private boolean resultFlag;

    }

    /**
     * Cmncd Swagger
     */
    @Schema(name = "response_page_cmncd_info")
    public static class ResponsePageCmncdInfo extends ResponseVO<Page<CmncdInfoDto>> {
        @Schema(description = "공통코드 정보 페이지")
        public Page<CmncdInfoDto> resultData;
    }

    @Schema(name = "create_cmncd_info")
    public static class CreateCmnCdInfo extends CmncdInfoDto {
        @Size(max = 15, message = "{valid.common.size-max}")
        @Schema(description = "그룹 코드", example = "TEST")
        private String grpCd;

    }

    /**
     * User Swagger
     */
    @Schema(name = "response_page_user_info")
    public static class ResponsePageUserInfo extends ResponseVO<Page<UserInfoDto>> {
        @Schema(description = "회원 정보 페이지")
        public Page<UserInfoDto> resultData;
    }

    @Schema(name = "response_user_token")
    public static class ResponsePageUserToken extends ResponseVO<UserDto.UserToken> {
        @Schema(description = "회원 토큰")
        public UserDto.UserToken resultData;
    }

    @Schema(name = "response_user_id")
    public static class ResponseUserId extends ResponseVO<String> {
        @Schema(description = "유저 ID", example = "user1234")
        public String resultData;
    }

    @Schema(name = "response_user_pw")
    public static class ResponseUserPw extends ResponseVO<String> {
        @Schema(description = "유저 비밀번호", example = "user1234!@#$")
        public String resultData;
    }

    @Schema(name = "search_user_id")
    public static class SearchUserId extends ResponseVO<UserDto.UserSearch> {
        @Size(max = 20, message = "{valid.common.size-max}")
        @Schema(description = "유저 ID", example = "user1234", hidden = true)
        private String userId;

    }

    @Schema(name = "send_user_email")
    public static class SendUserEmail extends UserDto.UserEmailVerify {
        @Size(max = 6, message = "{valid.common.size-max}")
        @Schema(description = "이메일 인증 코드", example = "123456", accessMode = Schema.AccessMode.READ_ONLY)
        private String code;

    }


    /**
     * AtuhIp Swagger
     */
    @Schema(name = "response_page_authrz_ip")
    public static class ResponsePageAuthrzIpCtlg extends ResponseVO<Page<AuthrzIpCtlgDto>> {
        @Schema(description = "인가 IP 페이지")
        public Page<AuthrzIpCtlgDto> resultData;
    }

    @Schema(name = "response_authrz_ip")
    public static class ResponseAuthrzIpCtlg extends ResponseVO<AuthrzIpCtlgDto> {
        public AuthrzIpCtlgDto resultData;
    }

    @Schema(name = "create_authrz_ip")
    public static class CreateAuthrzIpCtlg extends AuthrzIpCtlgDto {

        @Size(max = 50, message = "{valid.common.size-max}")
        @Schema(description = "수정자", example = "김주현", hidden = true)
        private String mdfr;

    }

    /**
     * DCTN Swagger
     */
    @Schema(name = "response_page_dctn_info")
    public static class ResponsePageDctnInfo extends ResponseVO<Page<DctnInfoDto>> {
        @Schema(description = "데이터 사전 페이지")
        public Page<DctnInfoDto> resultData;
    }

    @Schema(name = "response_dctn_info")
    public static class ResponseDctnInfo extends ResponseVO<DctnInfoDto> {
        public DctnInfoDto resultData;
    }

    @Schema(name = "create_dctn_info")
    public static class CreateDctnInfo extends DctnInfoDto {
        @Size(max = 50, message = "{valid.dctn.mdfr.sizemax}")
        @Schema(description = "수정자", example = "김주현", hidden = true)
        private String mdfr;

        @Size(max = 500, message = "{valid.dctn.mdfrrsn.sizemax}")
        @Schema(description = "수정 사유", example = "오타 정정", hidden = true)
        private String mdfrRsn;

    }

    @Schema(name = "response_page_dctn_domain_info")
    public static class ResponsePageDctnDomainInfo extends ResponseVO<Page<DctnDomainInfoDto>> {
        @Schema(description = "데이터 사전 페이지")
        public Page<DctnDomainInfoDto> resultData;
    }

    @Schema(name = "response_dctn_domain_info")
    public static class ResponseDctnDomainInfoDto extends ResponseVO<DctnDomainInfoDto> {
        public DctnDomainInfoDto resultData;
    }

    @Schema(name = "create_dctn_domain_info")
    public static class CreateDctnDomainInfoDto extends DctnDomainInfoDto {
        @Size(max = 50, message = "{valid.dctn.mdfr.sizemax}")
        @Schema(description = "수정자", example = "김주현", hidden = true)
        private String mdfr;

        @Size(max = 500, message = "{valid.dctn.mdfrrsn.sizemax}")
        @Schema(description = "수정 사유", example = "오타 정정", hidden = true)
        private String mdfrRsn;

    }

    @Schema(name = "ids_user")
    public static class IdsOnlyUserId extends IdsDto {
        @Schema(description = "데이터 사전 ID 리스트", example = "[1, 2]", accessMode = Schema.AccessMode.READ_ONLY)
        private Set<Integer> ids;

    }

}
