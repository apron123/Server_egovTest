package egovframework.cbm.web.common.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 *  Front Vaildate DTO
 *
 * @author  이상민
 * @since   2024.07.24 12:00
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(name = "front_validate", description = "프론트 데이터 검증")
public class FrontVaildateDto {

    @Schema(description = "데이터 키", example = "test")
    private String key;

    @Schema(description = "데이터 이름", example = "테스트")
    private String name;

    @Schema(description = "검증 유무", example = "true")
    private boolean valid;

    @Schema(description = "Not Null 유무", example = "true")
    private boolean notNull;

    @Builder.Default
    @Schema(description = "길이 제한")
    private Size size = new Size();

    @Builder.Default
    @Schema(description = "공통코드 리스트")
    private List<Cmncd> cmncdList = new ArrayList<>();


    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Size {

        @Schema(description = "최소 길이", example = "1")
        private int min = 0;

        @Schema(description = "최대 길이", example = "50")
        private int max = 0;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Cmncd {

        @Schema(description = "코드", example = "NM")
        private String cd;

        @Schema(description = "코드 명", example = "이름")
        private String cdNm;

    }

    public FrontVaildateDto checkNotNull(boolean flag) {
        this.notNull = flag;
        this.valid = flag;
        return this;
    }

    public FrontVaildateDto checkSize(jakarta.validation.constraints.Size validSize) {
        if (validSize != null) {
            this.size.min = validSize.min();
            this.size.max = validSize.max();
            this.valid = true;
        }
        return this;
    }

    public FrontVaildateDto checkReadOnly(boolean flag) {
        this.valid = !flag;
        return this;
    }

    public void addCmncd(String cd, String cdNm) {
        this.cmncdList.add(Cmncd.builder()
                .cd(cd)
                .cdNm(cdNm)
                .build());
    }


}
