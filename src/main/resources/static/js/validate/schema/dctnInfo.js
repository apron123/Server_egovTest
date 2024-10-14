const DCTN_INFO_VALID = [
    {
        key: "abbrnm",
        type: "input",
        name: "약어명",
        isInsert: true,
        valid: true,
        notNull: true,
        size: {
            min: 1,
            max: 50
        }
    },
    {
        key: "word_nm",
        type: "input",
        name: "단어 명",
        isInsert: true,
        valid: true,
        notNull: true,
        size: {
            min: 1,
            max: 300
        }
    },
    {
        key: "engnm",
        type: "input",
        name: "영문명",
        isInsert: true,
        valid: true,
        notNull: true,
        size: {
            min: 1,
            max: 300
        }
    },
    {
        key: "word_expln",
        type: "textarea",
        name: "단어 설명",
        isInsert: true,
        valid: true,
        notNull: true,
        size: {
            min: 1,
            max: 4000
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