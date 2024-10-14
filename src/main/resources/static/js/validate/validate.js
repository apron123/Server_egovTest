const VAILDATE_CONF = {
    class: "validate",
    selector: "[data-valid]",
    notNull: "data-not-null",
    size: {
        min: "data-size-min",
        max: "data-size-max"
    },
    true: "Y",
    false: "N"
};

function setValidAttribute(validateSchema) {

    let elements = document.querySelectorAll("[data-key]");

    Array.from(elements).forEach(element => {
        const key = element.getAttribute("data-key");

        if (validateSchema[key]) {
            const data = validateSchema[key];
            element.setAttribute('data-valid', data.valid ? VAILDATE_CONF.true : VAILDATE_CONF.false);
            element.setAttribute('data-not-null', data.not_null ? VAILDATE_CONF.true : VAILDATE_CONF.false);
            element.setAttribute('data-size-max', data.size ? data.size.max : '');
        }
    });

}


// 입력받은 파라미터가 size체크 검사 함수
function checkStrSizeAndEng(paramName, str, size) {

    if (EmptyCheckFunction(str)) {
        return paramName + '을(를) 입력해주십시오. \n';
    }
    if (str.length > size) {
        return paramName + '는(은) 최대 ' + size + '자 입니다. \n';
    }
    return '';
}

//empty 검사하는 함수.
function EmptyCheckFunction(value) {
    return value === "" || value == null || (typeof value == "object" && !Object.keys(value).length);
}


function validateCommonMethod(arrayParam) {
    //1. idName : label or span 설명, inputValue : ipunt 값, fontLength: 글자 크기
    let error = '';
    arrayParam.forEach((data, index) => {
        if(data.labelDataType !== VAILDATE_CONF.false){
            let result = checkStrSizeAndEng(index + 1 + ' 번째 ' + data.idName, data.inputValue, Number(data.fontLength));
            error += result;
        }

    });
    return error;
}


// id로 찾은 div 요소 중 찾고 싶은 요소들 전부 가져오고 전달받은 object 값으로 validateCommonMethod 사용
// 1. elementId = 부모 요소 id ,
// 2. element = 부모 요소 중 찾고 싳은 태그 ex) label or span
// 3. 값이 들어 있는 객체

/**
 *
 * @param elementId
 * @param element
 * @param objectValue
 * @returns {*[]}
 *
 * getInputListValidate(document.getElementById("labelList"),'label', {keyword: "", classification: "", page: 1, eq_yn: false};);
 * 1. 이러면 labelList 요소중 자식 label 모두 찾고 그 textContent를 가지고와서 error 설명으로 만듬
 * 2. 반드시 label 이나 span 태그에 data-value - 문자 길이 검사할 값을 사용
 * 3.
 */
// function getInputListValidate(elementId, element, objectValue) {
//     let arrayValidateObj = []
//
//     let labelList = document.getElementById(elementId).querySelectorAll(element);
//
//     // NodeList를 배열로 변환하면서 각 요소의 content 값을 추출
//     let labelValues = Array.from(labelList, label => label.textContent);
//
//     // NodeList를 배열로 변환하면서 각 요소의 data-value 값을 추출 = 글자 크기
//     let labelDataValues = Array.from(labelList, label => label.getAttribute('data-value'));
//     // input value object values to array
//     let inputValues = Object.values(objectValue);
//
//     for (let i = 0; i < labelDataValues.length; i++) {
//         let validateObj = {
//             idName: labelValues[i],
//             inputValue: inputValues[i],
//             fontLength: labelDataValues[i]
//         }
//         arrayValidateObj.push(validateObj)
//     }
//
//     return arrayValidateObj;
// }


function getInputListValidate(objectValue) {
    let arrayValidateObj = []

    let elements = document.getElementsByClassName(VAILDATE_CONF.class)[0].querySelectorAll(VAILDATE_CONF.selector);

    // data-value 속성이 0인 요소만 필터링
    let labelList = Array.from(elements).filter(element => element.getAttribute(VAILDATE_CONF.notNull)  === VAILDATE_CONF.true
        || element.getAttribute(VAILDATE_CONF.notNull) === VAILDATE_CONF.false);

    // NodeList를 배열로 변환하면서 각 요소의 content 값을 추출
    let labelValues = Array.from(labelList, label => label.textContent);

    // NodeList를 배열로 변환하면서 각 요소의 data-value 값을 추출 = 글자 크기
    let labelDataValues = Array.from(labelList, label => label.getAttribute(VAILDATE_CONF.size.max));

    // NodeList를 배열로 변환하면서 각 요소의 data-type 뽑기 - 0이면 null 허용 x 1이면 null 허용
    let labelDataType = Array.from(labelList, label => label.getAttribute(VAILDATE_CONF.notNull));

    // input value object values to array
    let inputValues = Object.values(objectValue);

    for (let i = 0; i < labelDataValues.length; i++) {

        let validateObj = {
            idName: labelValues[i],
            inputValue: inputValues[i],
            fontLength: labelDataValues[i],
            labelDataType: labelDataType[i]
        }
        arrayValidateObj.push(validateObj)
    }

    return arrayValidateObj;
}


// 빈 문자열 값을 null로 변환하는 함수
function convertEmptyStringsToNull(obj) {
    const newObject = {};
    for (let key in obj) {
        if (obj[key] === "") {
            newObject[key] = null;
        } else {
            newObject[key] = obj[key];
        }
    }
    return newObject;
}

/**
 *
 * 개발 모드 : user_id 고정으로 생성함.
 */

function getUserId(){
    return "지음";
}

