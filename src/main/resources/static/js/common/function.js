function createURL(url, params) {
  //Object to queryString
  const queryStr = new URLSearchParams(params).toString();
  return (url + queryStr).toString();
}

// 외부 자바스크립트 함수에서 Alpine 인스턴스의 메서드 호출하기
function callAlpineMethod(pageNumber) {
  console.log("Callback function called!");
  // Find the Alpine component
  const alpineComponent = document.querySelector("[x-data]");

  // Access the Alpine component's context
  const componentContext = Alpine.$data(alpineComponent);
  componentContext.setPageNumber(pageNumber);
  // Call the Alpine function
  componentContext.getData();
}

/**
 * @param localStorageKey
 * @returns {}
 */
function getLocalStorageForKey(localStorageKey) {
  if (localStorage.getItem(localStorageKey) !== null) {
    console.log(`${localStorageKey} exists in local storage`);
    return localStorage.getItem(localStorageKey);
  } else {
    console.log(`${localStorageKey} does not exist in local storage`);
    return null;
  }
}

/* doamin / dctn 승인 전체 체크박스 선택 함수
 나중에 공용으로 변경해주세요~ */
function checkAll() {
  const allCheckBox = document.getElementById("all_check");
  const checkboxes = document.querySelectorAll('input[name="delete"]'); //선택되야 하는 체크 박스 class
  for (let i = 0; i < checkboxes.length; i++) {
    checkboxes[i].checked = allCheckBox.checked;
  }
}
