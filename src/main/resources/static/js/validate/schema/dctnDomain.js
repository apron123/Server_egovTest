const DCTN_DOMAIN_INFO_VALID = [
    {
        key: "domain_nm",
        type: "input",
        name: "도메인 명",
        isInsert: true,
        valid: true,
        notNull: true,
        size: {
            min: 1,
            max: 50
        }
    },
    {
        key: "domain_clsfct_word",
        type: "input",
        name: "도메인 분류어",
        isInsert: true,
        valid: true,
        notNull: true,
        size: {
            min: 1,
            max: 50
        }
    },
    {
        key: "logic_data_type",
        type: "select",
        name: "논리 데이터 유형",
        selectOptions: [
            {text: "XML타입", value: "XML"},
            {text: "고정길이문자형", value: "CHAR"},
            {text: "가변길이문자형", value: "VARCHAR"},
            {text: "실수", value: "ACTNO"},
            {text: "정수", value: "ALWC"},
            {text: "날짜-DATE", value: "DATE"},
            {text: "날짜-TIMESTAMP", value: "TS"},
            {text: "문자형대형객체", value: "CLOB"},
            {text: "바이너리대형객체", value: "BLOB"}
        ],
        isInsert: true,
        valid: true,
        notNull: true,
        size: {
            min: 1,
            max: 15
        }
    },
    {
        key: "domain_type",
        type: "select",
        selectOptions: [
            {text: "각도", value: "ANG"},
            {text: "금액", value: "AMTMN"},
            {text: "날짜", value: "DT"},
            {text: "면적", value: "AREA"},
            {text: "무게", value: "WGHT"},
            {text: "번호", value: "NO"},
            {text: "비율", value: "RATE"},
            {text: "수량", value: "QTY"},
            {text: "시간", value: "TIME"},
            {text: "식별자", value: "IDTFR"},
            {text: "온도", value: "TPRT"},
            {text: "코드", value: "CD"},
            {text: "이름", value: "NAME"},
            {text: "좌표", value: "COORD"},
            {text: "차원", value: "DMNSN"},
            {text: "체적", value: "VLM"},
            {text: "텍스트", value: "TEXT"}
        ],
        name: "도메인 유형",
        isInsert: true,
        valid: true,
        notNull: true,
        size: {
            min: 1,
            max: 15
        }
    },
    {
        key: "rpst_yn",
        type: "select",
        selectOptions: [
            {text: "예", value: "Y"},
            {text: "아니오", value: "N"}
        ],
        name: "대표 여부",
        isInsert: true,
        valid: true,
        notNull: true,
        size: {
            min: 1,
            max: 1
        }
    },
    {
        key: "clsfct",
        type: "select",
        selectOptions: [
            {text: "국방데이터", value: "ND"},
            {text: "공공데이터", value: "PBL"},
            {text: "유저데이터", value: "USER"}
        ],
        name: "분류 코드",
        isInsert: true,
        valid: true,
        notNull: true,
        size: {
            min: 1,
            max: 15
        }
    },
    {
        key: "wrtr",
        type: "input",
        name: "작성자",
        isInsert: true,
        valid: true,
        notNull: true,
        size: {
            min: 1,
            max: 50
        }
    },
    {
        key: "crt_dttm",
        type: "span",
        name: "생성 일시",
        isInsert: false,
        valid: false,
        notNull: false,
        size: {
            min: 0,
            max: 0
        }
    },
    {
        key: "mdfr",
        type: "input",
        name: "수정자",
        isInsert: false,
        valid: true,
        notNull: false,
        size: {
            min: 1,
            max: 50
        }
    },
    {
        key: "mdfr_rsn",
        type: "input",
        name: "수정 사유",
        isInsert: false,
        valid: true,
        notNull: false,
        size: {
            min: 1,
            max: 500
        }
    },
    {
        key: "mdfc_dttm",
        name: "수정 일시",
        type: "span",
        isInsert: false,
        valid: false,
        notNull: false,
        size: {
            min: 0,
            max: 0
        }

    }
]