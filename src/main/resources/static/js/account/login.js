window.addEventListener("DOMContentLoaded", function () {
  changePage();
});

function changePage() {
  if (loginCheck()) {
    //  로그인 될 시
    window.location.href = "/view/dctn/word";
  } else {
    console.log("login X");
  }
}

function create() {
  userId = document.getElementById("userId").value;
  userPw = document.getElementById("userPw").value;

  if (userId == null || userId == "" || userId == undefined) {
    alert("ID를 확인하여 주세요");
    return;
  }

  if (userPw == null || userPw == "" || userPw == undefined) {
    alert("비밀번호를 확인하여 주세요");
    return;
  }

  loginInfo = {
    user_id: userId,
    user_pw: userPw,
  };

  const url = "user/login";

  axiosRequest("POST", url, loginInfo)
    .then(function (data) {
      console.log("Success:", data);
      successAlert(data);
    })
    .catch(function (errMsgData) {
      console.log(errMsgData);
      alert("회원 정보가 올바르지 않습니다!");
    });
}

function successAlert(data) {
  // session storage에 token 저장 및 user 정보 저장
  window.localStorage.setItem("accessToken", data.result_data.access_token);
  window.localStorage.setItem("refreshToken", data.result_data.refresh_token);
  window.localStorage.setItem("userId", data.result_data.user_id);
  window.localStorage.setItem("userName", data.result_data.user_name);
  changePage();
}

// 로그인 확인 여부
function loginCheck() {
  const myStorage = window.localStorage;
  const userId = myStorage.getItem("userId");
  const accessToken = myStorage.getItem("accessToken");
  const refreshToken = myStorage.getItem("refreshToken");

  if (!userId || !accessToken || !refreshToken) {
    console.log("local storage에 정보가 없습니다");
    return false;
  }

  // 서버에서 로그인 체크 기능 추가 ?

  return true;
}
