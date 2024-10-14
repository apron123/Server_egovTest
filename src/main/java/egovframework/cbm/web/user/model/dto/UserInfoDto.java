package egovframework.cbm.web.user.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import egovframework.utils.Constants;
import egovframework.utils.Encryption;
import egovframework.utils.enums.Yn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 유저 정보 DTO
 *
 * @author 이상민
 * @since 2024.07.08 12:00
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(name = "user_info", description = "유저 정보")
public class UserInfoDto {

    @Schema(description = "사용자 정보 순번", accessMode = Schema.AccessMode.READ_ONLY)
    private int userInfoSeq;

    @NotBlank(message = "{valid.common.not-blank}")
    @Size(max = 20, message = "{valid.common.size-max}")
    @Schema(description = "사용자 아이디", example = "user1234")
    private String userId;

    @NotBlank(message = "{valid.common.not-blank}")
    @Size(max = 200, message = "{valid.common.size-max}")
    @Schema(description = "사용자 비밀번호", example = "user1234!@#$")
    private String userPw;

    @NotBlank(message = "{valid.common.not-blank}")
    @Size(max = 100, message = "{valid.common.size-max}")
    @Schema(description = "사용자 명", example = "테스트유저")
    private String userNm;

    @Schema(description = "사용 여부", example = "Y", accessMode = Schema.AccessMode.READ_ONLY)
    @Builder.Default
    private char useYn = Yn.N.getC();

    @PastOrPresent
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_FORMAT)
    @Schema(description = "생성 일시", pattern = Constants.DATE_TIME_FORMAT, example = "2024-07-12 12:00:00", type = "string", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime crtDttm;

    @Email
    @NotBlank(message = "{valid.common.not-blank}")
    @Size(max = 100, message = "{valid.common.size-max}")
    @Schema(description = "사용자 이메일", example = "test@test.com")
    private String userEmail;

    @NotBlank(message = "{valid.common.not-blank}")
    @Size(max = 11, message = "{valid.common.size-max}")
    @Schema(description = "사용자 연락처", example = "01011112222")
    private String userCntadr;

    @NotBlank(message = "{valid.common.not-blank}")
    @Size(max = 15, message = "{valid.common.size-max}")
    @Schema(description = "사용자 권한", example = "AF")
    private String userRole;

    @Size(max = 50, message = "{valid.common.size-max}")
    @Schema(description = "권한 코드 명", example = "공군", accessMode = Schema.AccessMode.READ_ONLY)
    private String roleCdNm;

    public UserInfoDto upsertFlag(int userInfoSeq) throws Exception {
        this.userPw = Encryption.encryptPassword(this.userPw, this.userId);
        if (userInfoSeq > 0)
            this.userInfoSeq = userInfoSeq;

        return this;
    }

}
