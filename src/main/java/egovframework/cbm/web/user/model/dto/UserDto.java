package egovframework.cbm.web.user.model.dto;

import egovframework.utils.Encryption;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * 사용자 DTO
 *
 * @author 이상민
 * @since 2024.07.08 12:00
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDto {

    private UserLogin userLogin;

    private UserToken userToken;

    private UserSearch userSearch;

    private UserCheck userCheck;

    private UserEmailVerify userEmailVerify;

    /**
     * 사용자 로그인 정보
     *
     * @author 이상민
     * @since 2024.07.08 12:00
     */
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Schema(name = "user_login", description = "유저 로그인 정보")
    public static class UserLogin {

        @NotBlank(message = "{valid.common.not-blank}")
        @Size(max = 20, message = "{valid.common.size-max}")
        @Schema(description = "사용자 아이디", example = "user1234")
        private String userId;

        @NotBlank(message = "{valid.common.not-blank}")
        @Size(max = 20, message = "{valid.common.size-max}")
        @Schema(description = "사용자 비밀번호", example = "user1234!@#$")
        private String userPw;

        public String createEncryptPassword() throws Exception {
            return Encryption.encryptPassword(this.userPw, this.userId);
        }

    }

    /**
     * 사용자 토큰 정보
     *
     * @author 이상민
     * @since 2024.07.08 12:00
     */
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Schema(name = "user_token", description = "사용자 토큰 정보")
    public static class UserToken {

        @NotBlank(message = "{valid.common.not-blank}")
        @Size(max = 100, message = "{valid.common.size-max}")
        @Schema(description = "사용자 명", example = "테스트유저")
        private String userName;

        @NotBlank(message = "{valid.common.not-blank}")
        @Size(max = 100, message = "{valid.common.size-max}")
        @Schema(description = "사용자 아이디", example = "testuser")
        private String userId;

        @NotBlank(message = "{valid.common.not-blank}")
        @Size(max = 500, message = "{valid.common.size-max}")
        @Schema(description = "AccessToken", example = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiIx...")
        private String accessToken;

        @NotBlank(message = "{valid.common.not-blank}")
        @Size(max = 500, message = "{valid.common.size-max}")
        @Schema(description = "RefreshToken", example = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiIx...")
        private String refreshToken;

        public UserToken updateRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

    }

    /**
     * 사용자 아이디 중복체크
     *
     * @author 이상민
     * @since 2024.07.08 12:00
     */
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Schema(name = "user_check", description = "사용자 정보 중복체크")
    public static class UserCheck {

        @Size(max = 20, message = "{valid.common.size-max}")
        @Schema(description = "사용자 아이디", example = "user1234")
        private String userId;

        @Size(max = 11, message = "{valid.common.size-max}")
        @Schema(description = "사용자 연락처", example = "01011112222")
        private String userCntadr;

    }

    /**
     * 사용자 이메일 인증
     *
     * @author 이상민
     * @since 2024.07.08 12:00
     */
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Schema(name = "user_email_verify", description = "사용자 이메일 인증")
    public static class UserEmailVerify {

        @Email
        @Size(max = 100, message = "{valid.common.size-max}")
        @Schema(description = "사용자 이메일", example = "test@test.com")
        private String userEmail;

        @Size(max = 6, message = "{valid.common.size-max}")
        @Schema(description = "이메일 인증 코드", example = "123456")
        private String code;

    }

    /**
     * 사용자 서치 정보
     *
     * @author 이상민
     * @since 2024.07.08 12:00
     */
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Schema(name = "user_search", description = "사용자 검색 정보")
    public static class UserSearch {

        @NotBlank(message = "{valid.common.not-blank}")
        @Size(max = 100, message = "{valid.common.size-max}")
        @Schema(description = "사용자 명", example = "테스트유저")
        private String userName;

        @Size(max = 20, message = "{valid.common.size-max}")
        @Schema(description = "사용자 아이디", example = "user1234")
        private String userId;

    }

}
