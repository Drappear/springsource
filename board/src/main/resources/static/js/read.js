// 날짜 처리 함수
const formatDateTime = (str) => {
  const date = new Date(str);

  return (
    date.getFullYear() +
    "/" +
    (date.getMonth() + 1) +
    "/" +
    date.getDate() +
    " " +
    date.getHours() +
    ":" +
    date.getMinutes()
  );
};

// 페이지가 로드되면 현재 bno의 댓글 가져오기
const replyLoaded = () => {
  fetch(`/replies/board/${bno}`)
    .then((response) => {
      if (!response.ok) throw new Error("ERROR!");
      return response.json();
    })
    .then((data) => {
      console.log(data);

      // 전체 댓글 수
      document.querySelector(".d-inline-block").innerHTML = data.length;

      // 전체 댓글 내용 표시
      const replyList = document.querySelector(".reply-list");

      let result = "";
      data.forEach((reply) => {
        result += `<div class="d-flex justify-content-between my-2 border-bottom reply-row" data-rno="">`;
        result += `<div class="p-3">`;
        result += `<img src="" alt="" class="rounded-circle mx-auto d-block" />`;
        result += `</div>`;
        result += `<div class="flex-grow-1 align-self-center">`;
        result += `<span>${reply.replyer}</span><div><span class="fs-5">`;
        result += `${reply.text}</span></div><div class="text-muted">`;
        result += `<span class="small">${formatDateTime(reply.regDate)}</span>`;
        result += `</div></div>`;
        result += `<div class="d-flex flex-column align-self-center">`;
        result += `<div class="mb-2"><button class="btn btn-outline-danger btn-sm">삭제</button></div>`;
        result += `<div><button class="btn btn-outline-success btn-sm">수정</button></div>`;
        result += `</div></div>`;
      });

      replyList.innerHTML = result;
    });
};

replyLoaded();
