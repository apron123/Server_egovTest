package egovframework.cbm.web.dctn.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import egovframework.utils.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * 데이터 사전 승인 내역 DTO
 *
 * @author  이상민
 * @since   2024.07.08 12:00
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(name = "apprv_ptcls", description = "데이터 사전 승인 내역")
public class ApprvPtclsDto {

    @Schema(description = "시퀀스", accessMode = Schema.AccessMode.READ_ONLY)
    private int apprvPtclsSeq;

    @Schema(description = "외래키 사전 테이블", accessMode = Schema.AccessMode.READ_ONLY)
    private int dctnInfoSeq;

    @NotBlank(message = "{valid.common.not-blank}")
    @Size(max = 20, message = "{valid.common.size-max}")
    @Schema(description = "승인자", example = "user_id")
    private String apprv;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_FORMAT)
    @Schema(description = "승인 일시", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime apprvDttm;

}
