package egovframework.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    public static final String ADMIN_ID = "admin";

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final int DEFAULT_ROWS = 50;

    public static final int TEMPORARY_PASSWORD = 6;

    public static final String REGEXP_YN = "^(Y|N)$";

    public static final String DEFAULT_CLSFCT = "ND";

    public static final String REGEXP_CLSFCT = "^(ND|PBL|USER)$";

    public static final String REGEXP_LOGIC_DATA_TYPE = "^(XML|CHAR|VARCHAR|ACTNO|ALWC|DATE|TS|CLOB|BLOB)$";

    public static final String DEFAULT_LOGIC_DATA_TYPE = "VARCHAR";

    public static final String REGEXP_DOMAIN_TYPE = "^(ANG|AMTMN|DT|AREA|WGHT|NO|RATE|QTY|TIME|IDTFR|TPRT|CD|NAME|COORD|DMNSN|VLM|TEXT)$";

    public static final String DEFAULT_DOMAIN_TYPE = "NO";

    /*
     * JWT TOKEN SECRET KEY
     * Linux console에서 openssl rand -base64 32 입력으로 재발급 가능
     */
    public static final String KEY = "niW1RUrwjW/cui/BxJpE3lKISm0g9EkPs0//Fg0qYyg=";

}
