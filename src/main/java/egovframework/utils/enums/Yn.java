package egovframework.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Yn {

    Y('Y', "예"),
    N('N', "아니오");

    private final char c;

    private final String name;

    public static void checkYn(char yn) {
        for (Yn value : Yn.values()) {
            if (value.getC() == yn) {
                return;
            }
        }
        throw new IllegalArgumentException("유효하지 않은 문자(Y|N) : " + yn);
    }

}
