package egovframework.cbm.web.user.model.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import egovframework.utils.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(name = "login_hist", description = "로그인 이력")
public class LoginHistDto {

    @Schema(description = "ID", accessMode = Schema.AccessMode.READ_ONLY)
    private int id;

    @NotBlank(message = "{valid.common.not-blank}")
    @Size(max = 50, message = "{valid.common.size-max}")
    @Schema(description = "유저 ID", example = "user1234", accessMode = Schema.AccessMode.READ_ONLY)
    private String userId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_FORMAT)
    @Schema(description = "로그인 날짜", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime loginDttm;

    @NotBlank(message = "{valid.common.not-blank}")
    @Size(max = 50, message = "{valid.common.size-max}")
    @Schema(description = "IP 주소", example = "192.168.0.1")
    private String ipAddress;

    public LoginHistDto upsertFlag(int id) {
        if (id > 0)
            this.id = id;
        return this;
    }
}
