const urlParams = new URL(location.href).searchParams;
let urlSd = "";
if (urlParams.has("standard")) {
  urlSd = urlParams.get("standard");
}
console.log(urlSd);

if (urlSd == "") {
  console.log("등록화면");
} else {
  console.log("수정화면");
  document.getElementById("title").innerHTML = urlSd + " 수정";

  const url = "standard/info/" + urlSd;

  axiosRequest("GET", url)
    .then(function (data) {
      console.log("Success:", data);
      detailData(data);
    })
    .catch(function (errMsgData) {
      console.log(errMsgData.resultMsg);
    });
}

function detailData(data) {
  if (data.resultCode != 200) {
    document.writeln(data.resultMsg);
  } else {
    const jsonData = data.resultData;
    document.getElementById("id").value = jsonData.standardId || "";
    document.getElementById("standard").value = jsonData.standard || "";
    document.getElementById("word").value = jsonData.word || "";
    document.getElementById("desc").value = jsonData.desc || "";
    document.getElementById("upload-user").value = jsonData.uploadUser || "";
    document.getElementById("upload-time").innerHTML =
      dateformat1(jsonData.uploadTime) || "";
    document.getElementById("update-user").value = jsonData.updateUser || "";
    document.getElementById("update-time").innerHTML =
      dateformat1(jsonData.updateTime) || "";

    document.getElementById("upload-user").readOnly = true;
    document.getElementById("deleteBtn").style.display = "flex";
    document.getElementById("updateDiv").style.display = "flex";
  }
}

function upload() {
  const id = document.getElementById("id").value;
  const standard = document.getElementById("standard").value;
  const word = document.getElementById("word").value;
  const desc = document.getElementById("desc").value;
  const uploadUser = document.getElementById("upload-user").value;
  const updateUser = document.getElementById("update-user").value;
  const uploadTime = document.getElementById("upload-time").innerHTML;

  let url = "standard/save";

  if (urlSd != "") {
    /**
     * 수정 JSON DATA
     *
     */
    data = {
      standardId: id,
      standard: standard,
      word: word,
      desc: desc,
      uploadUser: uploadUser,
      updateUser: updateUser,
      uploadTime: uploadTime,
    };
  } else {
    /**
     * 등록 JSON DATA
     *
     */
    data = {
      standard: standard,
      word: word,
      desc: desc,
      uploadUser: uploadUser,
    };
  }

  axiosRequest("POST", url, data)
    .then(function (data) {
      console.log("Success:", data);
      uploadSuccess(data);
      window.location.href = "http://localhost:8080/view/example/list";
    })
    .catch(function (errMsgData) {
      alert(errMsgData.resultMsg);
    });
}

function uploadSuccess(data) {
  const id = document.getElementById("id").value;
  if (data.resultCode == "200") {
    alert("등록에 성공하였습니다!");
  } else {
    alert("등록에 실패하였습니다!");
    // if (id != "") {
    //   window.location.reload();
    // } else {
    //   window.location.href = "http://localhost:8080/view/example/list";
    // }
  }
}

function del() {
  const result = confirm("해당 자료를 삭제하시겠습니까?");

  if (result != true) {
    return;
  }

  const id = document.getElementById("id").value;
  const url = "standard/delete/" + id;

  axiosRequest("DELETE", url)
    .then(function (data) {
      console.log("Success:", data);
      alert("성공하였습니다!");

      if (data.resultCode == "200") {
        window.location.href = "http://localhost:8080/view/example/list";
      }
    })
    .catch(function (errMsgData) {
      console.log(errMsgData.resultMsg);
    });
}

function checkStandard() {
  const standard = document.getElementById("standard").value;

  if (standard != "" || standard != null) {
    document.getElementById("standard-count").innerHTML = "";
  }

  const url = "standard/info/" + standard;

  axiosRequest("GET", url).then(function (data) {
    console.log("Success:", data.resultData);
    if (data.resultData == null) {
      document.getElementById("standard-count").innerHTML =
        "사용 가능한 표준화명 입니다.";
    } else {
      document.getElementById("standard-count").innerHTML =
        "사용 할 수 없는 표준화명 입니다.";
    }
  });
}
