// x를 누르면 삭제 요청 => 부모 이벤트
document.querySelector(".uploadResult").addEventListener("click", (e) => {
  // a태그 기능 중지
  e.preventDefault();
  // x 눌러진 태그 요소 찾기
  // console.log(e.target);
  // console.log(e.currentTarget);
  // href 값 가져오기
  const element = e.target.closest("a");
  // console.log(element);
  // console.log(element.getAttribute("href"));

  // 이미지 삭제
  const removeLi = e.target.closest("li");

  // 삭제 할 이미지 경로 추출
  const filePath = element.getAttribute("href");

  let formData = new FormData();
  formData.append("filePath", filePath);

  fetch("/upload/remove", {
    method: "post",
    headers: {
      "X-CSRF-TOKEN": csrfValue,
    },
    body: formData,
  })
    .then((response) => {
      if (!response.ok) throw new Error("에러 발생");
      return response.text();
    })
    .then((data) => {
      // 화면 이미지 제거
      if (data) removeLi.remove();
    });
});
