document.querySelector("[name='keyword']").addEventListener("keyup", (e) => {
  if (e.keyCode == 13) {
    // 검색어 입력 확인
    const keyword = e.target.value;

    // 검색어 없을시 메세지 alert후 돌려보내기
    if (!keyword) {
      alert("영화 제목을 입력해주세요");
      return;
    }

    // 검색어 있으면 searchForm의 keword값으로 변경
    const searchForm = document.querySelector("#searchForm");
    searchForm.querySelector("[name='keyword']").value = keyword;

    // submit
    searchForm.submit();
  }
});
