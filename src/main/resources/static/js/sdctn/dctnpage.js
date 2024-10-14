function createPagination(jsonData,callback) {
  const paginationList = document.getElementById("pagination-list");
  paginationList.innerHTML = ""; // 이전 페이지 번호들 제거

  const totalPages = jsonData.total_pages;
  let currentPage = jsonData.pageable.page_number + 1;

  // 현재 페이지가 10개씩 묶어서 표시될 때의 그룹 번호 계산
  const currentGroup = Math.ceil(currentPage / 10);
  const startPageOfGroup = (currentGroup - 1) * 10 + 1;
  const endPageOfGroup = Math.min(currentGroup * 10, totalPages);

  // 이전 그룹으로 이동하는 버튼 추가
  if (currentGroup > 1) {
    var doublePrevButton = document.createElement("li");
    doublePrevButton.classList.add("page-btn");
    doublePrevButton.classList.add("double-prev");
    doublePrevButton.addEventListener("click", function () {
      nextGrp = (currentGroup - 2) * 10 + 1;
      callback(nextGrp);
    });
    paginationList.appendChild(doublePrevButton);
  }

  // 이전 페이지로 이동하는 버튼 추가
  if (currentPage > 1) {
    const prevButton = document.createElement("li");
    prevButton.classList.add("page-btn");
    prevButton.classList.add("prev");
    prevButton.addEventListener("click", function () {
      callback(currentPage - 2);

    });
    paginationList.appendChild(prevButton);
  }

  // 페이지 번호들 추가
  for (let i = startPageOfGroup; i <= endPageOfGroup; i++) {
    const pageNumberButton = document.createElement("li");
    pageNumberButton.innerHTML =
      '<a ' +
      (i === currentPage ? "class='active'" : "") +
      '>' +
      i +
      "</a>";
    pageNumberButton.addEventListener("click", function (event) {
      jsonData.page = parseInt(event.target.innerText);
      callback(jsonData.page);


    });
    paginationList.appendChild(pageNumberButton);
  }

  // 다음 페이지로 이동하는 버튼 추가
  if (currentPage < totalPages) {
    const nextButton = document.createElement("li");
    nextButton.innerHTML = '<a class="page-btn next"></a>';
    nextButton.addEventListener("click", function () {
      callback(currentPage + 1);
    });
    paginationList.appendChild(nextButton);
  }

  // 다음 그룹으로 이동하는 버튼 추가
  if (currentGroup < Math.ceil(totalPages / 10)) {
    const doubleNextButton = document.createElement("li");
    doubleNextButton.innerHTML = '<a class="page-btn double-next"></a>';
    doubleNextButton.addEventListener("click", function () {
      nextGrp = currentGroup * 10 + 1;

      callback(nextGrp);
    });
    paginationList.appendChild(doubleNextButton);
  }
}
