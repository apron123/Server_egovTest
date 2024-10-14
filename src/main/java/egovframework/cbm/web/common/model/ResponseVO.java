package egovframework.cbm.web.common.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@ToString
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Schema(name = "response", description = "공통 response")
public class ResponseVO<T> {

    @Schema(description = "응답 상태 코드", example = "200")
    private int resultCode;
    @Schema(description = "응답 데이터", example = " ")
    private T resultData;
    @Schema(description = "응답 메세지", example = " ")
    private String resultMsg;
    @Schema(description = "응답 성공 여부", example = "true")
    private boolean resultFlag;

    public ResponseVO(T resultData){
        this.resultCode = 200;
        this.resultFlag = true;
        this.resultData = resultData;
    }

    public ResponseVO(int resultCode, String resultMsg, T resultData){
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        this.resultFlag = resultCode == 200;
        this.resultData = resultData;
    }

}
