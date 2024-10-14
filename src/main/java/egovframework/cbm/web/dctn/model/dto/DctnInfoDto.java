package egovframework.cbm.web.dctn.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import egovframework.utils.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * 데이터 사전 정보 DTO
 *
 * @author  이상민
 * @since   2024.07.08 12:00
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(name = "dctn_info", description = "데이터 사전 정보")
public class DctnInfoDto {

    @Schema(description = "시퀀스", accessMode = Schema.AccessMode.READ_ONLY)
    private int dctnInfoSeq;

    @NotBlank(message = "{valid.common.not-blank}")
    @Size(max = 50, message = "{valid.common.size-max}")
    @Schema(description = "약어 명", example = "Z")
    private String abbrnm;

    @NotBlank(message = "{valid.common.not-blank}")
    @Size(max = 300, message = "{valid.common.size-max}")
    @Schema(description = "단어 명", example = "지음")
    private String wordNm;

    @NotBlank(message = "{valid.common.not-blank}")
    @Size(max = 4000, message = "{valid.common.size-max}")
    @Schema(description = "단어 설명", example = "지음지식서비스 약어")
    private String wordExpln;

    @NotBlank(message = "{valid.common.not-blank}")
    @Size(max = 300, message = "{valid.common.size-max}")
    @Schema(description = "영문 명", example = "ZIUM")
    private String engnm;

    @NotBlank(message = "{valid.common.not-blank}")
    @Size(max = 15, message = "{valid.common.size-max}")
    @Pattern(regexp = Constants.REGEXP_CLSFCT, message = "{valid.common.pattern}")
    @Schema(description = "분류 코드", defaultValue = Constants.DEFAULT_CLSFCT, format = "DCTN")
    private String clsfct;

    @NotBlank(message = "{valid.common.not-blank}")
    @Size(max = 50, message = "{valid.common.size-max}")
    @Schema(description = "작성자", example = "김주현")
    private String wrtr;

    @PastOrPresent
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_FORMAT)
    @Schema(description = "생성 일시", pattern = Constants.DATE_TIME_FORMAT, example = "2024-07-12 12:00:00", type = "string", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime crtDttm;

    @Size(max = 50, message = "{valid.common.size-max}")
    @Schema(description = "수정자", example = "김주현")
    private String mdfr;

    @Size(max = 500, message = "{valid.common.size-max}")
    @Schema(description = "수정 사유", example = "오타 정정")
    private String mdfrRsn;

    @PastOrPresent
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_FORMAT)
    @Schema(description = "수정 일시", pattern = Constants.DATE_TIME_FORMAT, example = "2024-07-12 12:00:00", type = "string", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime mdfcDttm;

    @Size(max = 50, message = "{valid.common.size-max}")
    @Schema(description = "코드 명", example = "공공", accessMode = Schema.AccessMode.READ_ONLY)
    private String cdNm;

    public DctnInfoDto upsertFlag(int dctnInfoSeq) {
        if (dctnInfoSeq > 0)
            this.dctnInfoSeq = dctnInfoSeq;
        return this;
    }

}
