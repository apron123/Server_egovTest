package egovframework.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthUrl {
    ADMIN(new String[] { "/test/**", "/dctn/approve", "/dctn/domain/approve" }, "MNG_CD"),
    USER(new String[] { "/authip/**" }, "USER_CD");

    private final String[] urls;
    private final String role;
}
