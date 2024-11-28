// 포스터 추가 시 삭제 기능
document.querySelector(".uploadResult").addEventListener("click", (e) => {
  if (e.target.tagName !== "I") return;

  // href 값 가져오기
  const element = e.target.closest("li");
  // 서버 저장한 포스터 삭제 X
  if (confirm("정말로 이미지를 삭제하시겠습니까?")) {
    element.remove();
  }
});

// modifyForm 찾은 후 action = "/movie/remove"
const af = document.querySelector("#actionForm");
const removeBtn = document.querySelector("#createForm .bg-red-600");
const listBtn = document.querySelector("#createForm .bg-orange-100");

if (removeBtn) {
  removeBtn.addEventListener("click", () => {
    if (!confirm("정말로 삭제하시겠습니까?")) {
      return;
    }

    af.action = "/movie/remove";
    af.submit();
  });
}

listBtn.addEventListener("click", () => {
  af.querySelector("[name='mno']").remove();

  af.method = "get";
  af.action = "/movie/list";
  af.submit();
});
