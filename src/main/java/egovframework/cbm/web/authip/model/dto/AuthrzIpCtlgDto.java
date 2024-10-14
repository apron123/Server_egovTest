package egovframework.cbm.web.authip.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import egovframework.utils.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 인가 IP DTO
 *
 * @author  이상민
 * @since   2024.07.08 12:00
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(name = "authrz_ip_ctlg", description = "인가 IP")
public class AuthrzIpCtlgDto {

    @Schema(description = "순번", accessMode = Schema.AccessMode.READ_ONLY)
    private int authrzIpCtlgSeq;

    @NotBlank(message = "{valid.common.not-blank}")
    @Size(max = 50, message = "{valid.common.size-max}")
    @Pattern(regexp = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$", message = "{valid.common.pattern-ip}")
    @Schema(description = "인가 IP", example = "192.168.0.1")
    private String  authrzIpCtlg;

    @Size(max = 4000, message = "{valid.common.size-max}")
    @Schema(description = "설명", example = "허용")
    private String expln;

    @Size(max = 50, message = "{valid.common.size-max}")
    @Schema(description = "등록자", example = "양승룡")
    private String regstr;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_FORMAT)
    @Schema(description = "등록일시", pattern = Constants.DATE_TIME_FORMAT, example = "2024-07-17 12:00:00", type = "string", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime restDttm;

    @Size(max = 50, message = "{valid.common.size-max}")
    @Schema(description = "수정자", example = "김주현")
    private String mdfr;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_FORMAT)
    @Schema(description = "수정일시", pattern = Constants.DATE_TIME_FORMAT, example = "2024-07-17 12:00:00", type = "string", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime mdfrDttm;

    public AuthrzIpCtlgDto upsertFlag(int authrzIpCtlgSeq) {
        if (authrzIpCtlgSeq > 0)
            this.authrzIpCtlgSeq = authrzIpCtlgSeq;
        return this;
    }

}
