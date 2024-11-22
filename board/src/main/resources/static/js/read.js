// 전체 댓글 내용 표시
const replyList = document.querySelector(".reply-list");

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

      let result = "";
      data.forEach((reply) => {
        result += `<div class="d-flex justify-content-between my-2 border-bottom reply-row" data-rno="${reply.rno}" data-email="${reply.replyerEmail}">`;
        result += ` <div class="p-3">`;
        result += `   <img src="/img/default.png" class="rounded-circle mx-auto d-block" style="width: 60px; height: 60px" />`;
        result += ` </div>`;
        result += ` <div class="flex-grow-1 align-self-center">`;
        result += `   <span>${reply.replyerName}</span>`;
        result += `   <div>`;
        result += `     <span class="fs-5">${reply.text}</span>`;
        result += `   </div>`;
        result += `   <div class="text-muted">`;
        result += `     <span class="small">`;
        result += `       ${formatDateTime(reply.regDate)}`;
        result += `     </span>`;
        result += `   </div>`;
        result += ` </div>`;
        result += ` <div class="d-flex flex-column align-self-center">`;

        if (`${loginEmail}` == `${reply.replyerEmail}`) {
          result += `   <div class="mb-2">`;
          result += `     <button class="btn btn-outline-danger btn-sm">삭제</button>`;
          result += `   </div>`;
          result += `   <div>`;
          result += `     <button class="btn btn-outline-success btn-sm">수정</button>`;
          result += `   </div>`;
        }
        result += ` </div>`;
        result += `</div>`;
      });

      replyList.innerHTML = result;
    });
};

replyLoaded();

// 댓글 작성
// reply form submit시 이벤트 발생
const replyForm = document.querySelector("#replyForm");
replyForm.addEventListener("submit", (e) => {
  // submit 중지
  e.preventDefault();

  // reply form 안에 있는 replyer, text value 변수에 담기
  const replyerEmail = replyForm.querySelector("#replyerEmail");
  const text = replyForm.querySelector("#text");

  const rno = replyForm.querySelector("#rno");

  const reply = {
    text: text.value,
    replyerEmail: replyerEmail.value,
    bno: bno,
    rno: rno.value,
  };

  // JSON.stringify(객체) => json 변환
  if (!rno.value) {
    // 새 댓글
    fetch(`/replies/new`, {
      headers: {
        "content-type": "application/json",
        "X-CSRF-TOKEN": csrfValue,
      },
      body: JSON.stringify(reply),
      method: "post",
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("댓글 입력 오류");
        }
        return response.text();
      })
      .then((data) => {
        console.log(data);
        if (data) {
          // 댓글 폼의 내용 제거
          text.value = "";

          // 댓글 등록 알람
          alert(data + "번 댓글이 등록되었습니다.");

          replyLoaded();
        }
      });
  } else {
    // 댓글 수정
    fetch(`/replies/${rno.value}`, {
      headers: {
        "content-type": "application/json",
        "X-CSRF-TOKEN": csrfValue,
      },
      body: JSON.stringify(reply),
      method: "put",
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("댓글 수정 오류");
        }
        return response.text();
      })
      .then((data) => {
        console.log(data);
        if (data) {
          // 댓글 폼의 내용 제거
          text.value = "";
          rno.value = "";

          alert(data + "번 댓글이 수정되었습니다.");

          replyLoaded();
        }
      });
  }
});

// 수정 버튼 클릭시 해당 댓글이 replyForm 안에 출력
// 이벤트 전파를 이용해서 이벤트 감지(부모)
replyList.addEventListener("click", (e) => {
  // 실제 이벤트 발생 요소 확인
  console.log(e.target);
  const btn = e.target;
  // 이벤트 발생 버튼이 속한 data-rno를 가진 부모태그 찾기
  // console.log(btn.closest(".reply-row"));

  // 수정 버튼 클릭시 rno 가져오기
  // data-rno 값
  const rno = btn.closest(".reply-row").dataset.rno;

  // 수정, 삭제버튼 구분
  // 클래스명 : classList
  if (btn.classList.contains("btn-outline-danger")) {
    // 댓글 삭제
    fetch(`/replies/${rno}`, {
      headers: {
        "content-type": "application/json",
        "X-CSRF-TOKEN": csrfValue,
      },
      body: JSON.stringify({ replyerEmail: replyerEmail }),
      method: "delete",
    })
      .then((response) => response.text())
      .then((data) => {
        console.log(data);

        alert(data + "번 댓글이 삭제되었습니다.");

        replyLoaded();
      });
  } else if (btn.classList.contains("btn-outline-success")) {
    fetch(`/replies/${rno}`)
      .then((response) => response.json())
      .then((data) => {
        console.log(data);

        // 해당 댓글 replyForm안에 출력
        replyForm.querySelector("#rno").value = data.rno;
        replyForm.querySelector("#replyerEmail").value = data.replyerEmail;
        replyForm.querySelector("#replyerName").value = data.replyerName;
        replyForm.querySelector("#text").value = data.text;
      });
  }
});