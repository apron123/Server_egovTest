window.addEventListener("DOMContentLoaded", function () {
  searchResult(1);
});

function tableMaker(resultList) {
  const tBody = document.getElementById("mainTable").querySelector("tbody");
  tBody.innerHTML = "";

  for (let i = 0; i < resultList.length; i++) {
    const row = tBody.insertRow(i);
    const cell1 = row.insertCell(0);
    const cell2 = row.insertCell(1);

    cell1.innerHTML = resultList[i].standard || "-";
    cell1.addEventListener("click", function () {
      location.href =
        "http://localhost:8080/view/example/edit?standard=" +
        resultList[i].standard;
    });
    cell2.innerHTML = resultList[i].word || "-";
  }
}

function search(pageNum) {
  const searchVal = document.getElementById("searchVal").value;
  const selectVal = document.getElementById("selectVal").value;

  const url =
    "standard/list?searchVal=" +
    searchVal +
    "&selectVal=" +
    selectVal +
    "&page=" +
    (pageNum - 1) +
    "&size=10&sort=standardId,asc";

  axiosRequest("GET", url)
    .then(function (data) {
      console.log("Success:", data);
      tableMaker(data.resultData.content);
      createPagination(data.resultData);

      document.getElementById("dataCnt").innerHTML =
        data.resultData.totalElements;
    })
    .catch(function (errMsgData) {
      console.log(errMsgData.resultMsg);
    });
}

function searchResult(pageNum) {
  if (pageNum == null || pageNum == undefined || pageNum == "") {
    pageNum = 1;
  }

  search(pageNum);
}
