// 삭제버튼 클릭시 actionForm 전송
const af = document.querySelector("#actionForm");
document.querySelector(".btn-danger").addEventListener("click", () => {
  const flag = confirm("정말로 삭제하시겠습니까?");

  if (flag) {
    af.action = "remove";
    af.submit();
  }
});

// 목록 클릭시
document.querySelector(".btn-secondary").addEventListener("click", (e) => {
  // 기능 중지
  e.preventDefault();
  // id값 제거
  af.querySelector("[name='id']").remove();
  // actionForm method get으로 변경
  af.method = "get";
  // actionForm action list로 변경
  af.action = "list";
  // submit
  af.submit();
});
