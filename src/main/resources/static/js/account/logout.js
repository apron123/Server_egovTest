function logout() {
  const logoutUrl = "user/logout";

  const token = {
    refresh_token: window.localStorage.getItem("refreshToken"),
  };

  axiosRequest("POST", logoutUrl, token)
    .then(function (data) {
      console.log("Success:", data);
      storageClear();
      window.location.href = "/view/account/login";
    })
    .catch(function (errMsgData) {
      console.log(errMsgData);
      alert(errMsgData.result_data.errorMessage[0]);
    });
}

function storageClear() {
  window.localStorage.clear();
  alert("로그아웃에 성공하였습니다!");
}

/* 나중에 삭제 */
const idValue = window.localStorage.getItem("userId");

if (idValue) {
  const loginButton = document.getElementById("headerLogin");
  loginButton.textContent = "로그아웃";
  loginButton.setAttribute("onclick", "logout()");
  loginButton.removeAttribute("th:href");
  loginButton.href = "#";
}
