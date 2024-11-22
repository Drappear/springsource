const af = document.querySelector("#actionForm");
// Remove 클릭 시
document.querySelector(".btn-danger").addEventListener("click", () => {
  if (!confirm("삭제하시겠습니까")) {
    return;
  }
  // actionForm action 수정
  af.action = "/board/remove";
  af.submit();
});

// List 클릭 시
document.querySelector(".btn-info").addEventListener("click", () => {
  // actionForm method 수정
  af.method = "get";

  // gno 삭제
  af.querySelector("[name = 'bno']").remove();
  af.querySelector("[name = 'writerEmail']").remove();
  af.submit();
});
