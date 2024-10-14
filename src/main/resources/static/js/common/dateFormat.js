function dateformat1(dateData) {
  // 입력 문자열을 Date 객체로 변환
  if (dateData != null && dateData != "") {
    const date = new Date(dateData);

    // 년, 월, 일, 시, 분, 초를 가져옴
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, "0");
    const day = String(date.getDate()).padStart(2, "0");
    const hours = String(date.getHours()).padStart(2, "0");
    const minutes = String(date.getMinutes()).padStart(2, "0");
    const seconds = String(date.getSeconds()).padStart(2, "0");

    // 포맷된 문자열을 반환
    return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
  } else {
    return null;
  }
}
