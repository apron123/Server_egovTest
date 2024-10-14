package egovframework.cbm.web.common.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import egovframework.utils.Constants;
import egovframework.utils.enums.Yn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 표준플랫폼 공통코드 정보 DTO
 *
 * @author 이상민
 * @since 2024.07.08 12:00
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(name = "cmncd_info", description = "표준플랫폼 공통코드")
public class CmncdInfoDto {

    @Schema(description = "시퀀스", accessMode = Schema.AccessMode.READ_ONLY)
    private int cmncdInfoSeq;

    @Size(max = 15, message = "{valid.common.size-max}")
    @Schema(description = "코드", example = "test")
    private String cd;

    @Size(max = 500, message = "{valid.common.size-max}")
    @Schema(description = "코드 설명", example = "테스트 코드")
    private String cdDesc;

    @Size(max = 50, message = "{valid.common.size-max}")
    @Schema(description = "코드 이름", example = "테스트")
    private String cdNm;

    @PastOrPresent
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_FORMAT)
    @Schema(description = "생성 일시", pattern = Constants.DATE_TIME_FORMAT, example = "2024-07-17 12:00:00", type = "string", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime crtDttm;

    @PastOrPresent
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_FORMAT)
    @Schema(description = "수정 일시", pattern = Constants.DATE_TIME_FORMAT, example = "2024-07-12 12:00:00", type = "string", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime mdfcDttm;

    @Schema(description = "사용 여부", example = "Y")
    @Builder.Default
    private char useYn = Yn.Y.getC();

    @Size(max = 15, message = "{valid.common.size-max}")
    @Schema(description = "그룹 코드", accessMode = Schema.AccessMode.READ_ONLY)
    private String grpCd;

    @Schema(description = "그룹 여부", example = "Y", accessMode = Schema.AccessMode.READ_ONLY)
    private char grpYn;

    @Schema(description = "하위 코드 개수", example = "4", accessMode = Schema.AccessMode.READ_ONLY)
    private int cdCnt;

    public CmncdInfoDto setCdCnt(int cdCnt) {
        this.cdCnt = cdCnt;
        return this;
    }

    public CmncdInfoDto upsertGrpFlag(int cmncdInfoSeq) {
        this.grpCd = this.cd;
        this.grpYn = Yn.Y.getC();
        if (cmncdInfoSeq > 0)
            this.cmncdInfoSeq = cmncdInfoSeq;
        return this;
    }

    public CmncdInfoDto upsertFlag(int cmncdInfoSeq) {
        this.grpYn = Yn.N.getC();
        if (cmncdInfoSeq > 0)
            this.cmncdInfoSeq = cmncdInfoSeq;
        return this;
    }

}
