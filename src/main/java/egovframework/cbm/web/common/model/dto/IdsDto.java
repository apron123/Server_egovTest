package egovframework.cbm.web.common.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(name = "ids", description = "다건 처리 ID 리스트")
public class IdsDto {

    @NotBlank(message = "{valid.common.not-blank}")
    @Size(max = 20, message = "{valid.common.size-max}")
    @Schema(description = "유저 ID", example = "user_id")
    private String userId;

    @NotEmpty(message = "{valid.common.not-blank}")
    @Schema(description = "ID 리스트", example = "[1, 2]")
    private Set<Integer> ids;

}
