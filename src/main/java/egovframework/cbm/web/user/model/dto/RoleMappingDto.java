package egovframework.cbm.web.user.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
@Schema(name = "ROLE_MAPPING", description = "Role 권한 매핑")
public class RoleMappingDto {

    @Schema(description = "Role mapping 아이디", accessMode = Schema.AccessMode.READ_ONLY)
    private int roleMappingSeq;

    @NotBlank(message = "{valid.common.not-blank}")
    @Size(max = 20, message = "{valid.common.size-max}")
    @Schema(description = "유저 Role 코드", example = "")
    private String userRoleCode;

    @NotBlank(message = "{valid.common.not-blank}")
    @Size(max = 200, message = "{valid.common.size-max}")
    @Schema(description = "체계 시스템 코드", example = "user1234!@#$")
    private String systemCode;

    public RoleMappingDto upsertFlag(int roleMappingSeq) throws Exception {
        if (roleMappingSeq > 0)
            this.roleMappingSeq = roleMappingSeq;
        return this;
    }
}
