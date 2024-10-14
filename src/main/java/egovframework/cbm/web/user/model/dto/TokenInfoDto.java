package egovframework.cbm.web.user.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(name = "token_info", description = "토큰 정보")
public class TokenInfoDto {

    @Schema(description = "ID", accessMode = Schema.AccessMode.READ_ONLY)
    private int id;

    @Size(max = 50, message = "{valid.common.size-max}")
    @Schema(description = "사용자 아이디", example = "user1234", accessMode = Schema.AccessMode.READ_ONLY)
    private String userId;

    @NotBlank(message = "{valid.common.not-blank}")
    @Size(max = 500, message = "{valid.common.size-max}")
    @Schema(description = "RefreshToken", example = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiIx...")
    private String refreshToken;

}
