package egovframework.cbm.web.user.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import egovframework.utils.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 이메일 인증 토큰
 *
 * @author 이상민
 * @since 2024.09.26 12:00
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(name = "email_code", description = "이메일 인증 코드")
public class EmailCodeDto {

    @Size(max = 6, message = "{valid.common.size-max}")
    @Schema(description = "이메일 인증 코드", example = "123456")
    private String code;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_FORMAT)
    @Schema(description = "토큰 만료 시간", pattern = Constants.DATE_TIME_FORMAT, example = "2024-07-12 12:00:00", type = "string", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime expiryTime;

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryTime);
    }

}
