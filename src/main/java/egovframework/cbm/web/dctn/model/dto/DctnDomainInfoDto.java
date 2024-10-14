package egovframework.cbm.web.dctn.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import egovframework.utils.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 데이터 사전 도메인 정보 DTO
 *
 * @author  이상민
 * @since   2024.07.17 12:00
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(name = "dctn_domain_info", description = "데이터 사전 도메인 정보")
public class DctnDomainInfoDto {

    @Schema(description = "시퀀스", accessMode = Schema.AccessMode.READ_ONLY)
    private int dctnDomainInfoSeq;

    @NotBlank(message = "{valid.common.not-blank}")
    @Size(max = 50, message = "{valid.common.size-max}")
    @Schema(description = "도메인 명", example = "객체번호_V8")
    private String domainNm;

    @NotBlank(message = "{valid.common.not-blank}")
    @Size(max = 50, message = "{valid.common.size-max}")
    @Schema(description = "도메인 분류어", example = "객체번호")
    private String domainClsfctWord;

    @NotBlank(message = "{valid.common.not-blank}")
    @Size(max = 15, message = "{valid.common.size-max}")
    @Pattern(regexp = Constants.REGEXP_LOGIC_DATA_TYPE, message = "{valid.common.pattern}")
    @Schema(description = "논리 데이터 유형", example = Constants.DEFAULT_LOGIC_DATA_TYPE, format = "DOMAIN_DATATYPE")
    private String logicDataType;

    @NotBlank(message = "{valid.common.not-blank}")
    @Size(max = 15, message = "{valid.common.size-max}")
    @Pattern(regexp = Constants.REGEXP_DOMAIN_TYPE, message = "{valid.common.pattern}")
    @Schema(description = "도메인 유형", example = Constants.DEFAULT_DOMAIN_TYPE, format = "DOMAIN_TYPE")
    private String domainType;

    @NotBlank(message = "{valid.common.not-blank}")
    @Size(max = 1, message = "{valid.common.size-max}")
    @Pattern(regexp = Constants.REGEXP_YN, message = "{valid.common.pattern-yn}")
    @Schema(description = "대표 여부", example = "N")
    private String rpstYn;

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
    @Schema(description = "생성 일시", pattern = Constants.DATE_TIME_FORMAT, example = "2024-07-17 12:00:00", type = "string", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime crtDttm;

    @PastOrPresent
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_FORMAT)
    @Schema(description = "수정 일시", pattern = Constants.DATE_TIME_FORMAT, example = "2024-07-12 12:00:00", type = "string", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime mdfcDttm;

    @Size(max = 50, message = "{valid.common.size-max}")
    @Schema(description = "수정자", example = "김주현")
    private String mdfr;

    @Size(max = 500, message = "{valid.common.size-max}")
    @Schema(description = "수정 사유", example = "오타 정정")
    private String mdfrRsn;

    @Size(max = 50, message = "{valid.common.size-max}")
    @Schema(description = "코드 명", example = "가변길이문자형", accessMode = Schema.AccessMode.READ_ONLY)
    private String logicDataTypeCdNm;

    @Size(max = 50, message = "{valid.common.size-max}")
    @Schema(description = "코드 명", example = "번호", accessMode = Schema.AccessMode.READ_ONLY)
    private String domainTypeCdNm;

    @Size(max = 50, message = "{valid.common.size-max}")
    @Schema(description = "코드 명", example = "공공", accessMode = Schema.AccessMode.READ_ONLY)
    private String cdNm;
    
    public DctnDomainInfoDto upsertFlag(int dctnDomainInfoSeq) {
        if (dctnDomainInfoSeq > 0)
            this.dctnDomainInfoSeq = dctnDomainInfoSeq;
        return this;
    }

}
