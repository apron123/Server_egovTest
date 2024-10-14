package egovframework.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * SchemaPackage EUNM
 *
 * @author  이상민
 * @since   2024.07.17 12:00
 */
@Getter
@AllArgsConstructor
public enum SchemaPackage {

    COMMON("common"),
    USER("user"),
    DCTN("dctn"),
    AUTH("authip");

    private final String name;

    public static String[] getSchemaPackages() {
        return Arrays.stream(SchemaPackage.values())
                .map(SchemaPackage::getName)
                .toArray(String[]::new);
    }

}
