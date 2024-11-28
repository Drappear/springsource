// x를 누르면 삭제 요청 => 부모 이벤트
document.querySelector(".uploadResult").addEventListener("click", (e) => {
  if (e.target.tagName !== "I") return;

  // href 값 가져오기
  const element = e.target.closest("li");

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
