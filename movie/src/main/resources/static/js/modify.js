// modifyForm 찾은 후 action = "/movie/remove"

const mf = document.querySelector("#modifyForm");

if (mf) {
  mf.addEventListener("submit", (e) => {
    e.preventDefault();

    if (!confirm("정말로 삭제하시겠습니까?")) {
      return;
    }

    mf.action = "/movie/remove";
    mf.submit();
  });
}
