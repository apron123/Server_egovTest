let idCheckValue = "N";
const checkRltSpan = document.getElementById("checkRltSpan");

/* 
  회원가입 함수
*/
function create() {
  const userId = document.getElementById("userId").value;
  const userNm = document.getElementById("userNm").value;
  const userPw = document.getElementById("userPw").value;

  userInfo = {
    user_id: userId,
    user_name: userNm,
    user_pw: userPw,
    use_at: "N",
    user_type_cd: "USER_CD",
  };

  if (idCheckValue != "Y") {
    alert("ID를 확인하여 주세요");
    return;
  }

  const url = "user/create";

  axiosRequest("POST", url, userInfo)
    .then(function (data) {
      console.log("Success:", data);
      signUpSuccess(data);
    })
    .catch(function (errMsgData) {
      alert(errMsgData.resultMsg);
    });
}

function signUpSuccess(data) {
  if (data.result_code == 200) {
    alert("회원가입에 성공하였습니다!");
    location.href = "/view/account/login";
  } else {
    alert(data.result_msg);
  }
}

/* 
  ID창 입력 시 실행되는 함수
*/
function checkId() {
  userId = document.getElementById("userId").value;

  console.log(userId);

  if (!userId) {
    idCheckValue = "N";
    checkRltSpan.style.color = "#FF0000";
    checkRltSpan.innerHTML = "ID를 입력하여 주세요";
    return;
  }

  const url = "user/list/" + userId;

  axiosRequest("GET", url)
    .then(function (data) {
      console.log("Success:", data);
      checkResult(data);
    })
    .catch(function (errMsgData) {
      console.log(errMsgData);
    });
}

/* 
  ID 중복 여부 체크
*/
function checkResult(rltData) {
  if (rltData.result_code == 200) {
    if (rltData.result_data == null) {
      checkRltSpan.innerHTML = "사용가능한 ID 입니다!";
      checkRltSpan.style.color = "#2DB400";
      idCheckValue = "Y";
    } else {
      checkRltSpan.innerHTML = "중복된 ID 입니다!";
      checkRltSpan.style.color = "#FF0000";
      idCheckValue = "N";
    }
  } else {
    console.log(rltData.result_msg);
  }

  checkRltSpan.style.display = "";
}
