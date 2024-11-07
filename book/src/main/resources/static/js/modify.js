// 삭제버튼 클릭시 actionForm 전송
const af = document.querySelector("#actionForm");
document.querySelector(".btn-danger").addEventListener("click", () => {
  const flag = confirm("정말로 삭제하시겠습니까?");

  if (flag) {
    af.action = "remove";
    af.submit();
  }
});
