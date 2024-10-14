function findId() {
  const userNm = document.getElementById("find-id-name").value;

  const searchUrl = "user/search-id";

  data = {
    user_name: userNm,
  };

  axiosRequest("POST", searchUrl, data)
    .then(function (data) {
      console.log("Success:", data);

      if (data.result_data != null) {
        alert("회원님의 ID는 " + data.result_data + "입니다!");
      } else {
        alert("해당하는 계정 정보가 없습니다!");
      }
    })
    .catch(function (errMsgData) {
      alert(errMsgData.result_msg);
    });
}
function findPw() {
  const userNm = document.getElementById("find-pw-name").value;
  const userId = document.getElementById("find-pw-id").value;

  const searchUrl = "user/search-pw";

  data = {
    user_name: userNm,
    user_id: userId,
  };

  axiosRequest("POST", searchUrl, data)
    .then(function (data) {
      console.log("Success:", data);

      if (!userId) {
          alert("아이디 적어주세요!")
          return
      }
      if (data.result_data != null) {
        alert("새로운 비밀번호는 " + data.result_data + "입니다!");
      } else {
        alert("해당하는 계정 정보가 없습니다!");
      }
    })
    .catch(function (errMsgData) {
      alert(errMsgData.result_msg);
    });
}
