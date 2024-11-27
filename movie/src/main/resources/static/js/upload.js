const fileInput = document.querySelector("[type='file']");

function showUploadImages(files) {
  // 이미지 보여줄 영역 찾아오기
  const output = document.querySelector(".uploadResult ul");

  let tags = "";

  files.forEach((file) => {
    tags += `<li data-name="${file.fileName}" data-path="${file.folderPath}" data-uuid="${file.uuid}">`;
    tags += `   <a href="">`;
    tags += `       <img src= "/upload/display?fileName=${file.thumbImageURL}" class="block"`;
    tags += `   </a>`;
    tags += `   <span class="text-sm d-inlin-block mx-1">${file.fileName}</span>`;
    tags += `   <a href="${file.imageURL}" data-file="">`;
    tags += `       <i class="fa-solid fa-xmark"></i>`;
    tags += `   </a>`;
    tags += `</li>`;
  });
  output.insertAdjacentHTML("beforeend", tags);
}

fileInput.addEventListener("change", (e) => {
  const files = e.target.files;

  let formData = new FormData();
  for (let index = 0; index < files.length; index++) {
    formData.append("uploadFiles", files[index]);
  }

  fetch("/upload/upload", {
    method: "post",
    body: formData,
  })
    .then((response) => response.json())
    .then((data) => {
      console.log(data);

      // 첨부 파일 화면 출력
      showUploadImages(data);
    });
});

// 작성 클릭시
document.querySelector("#createForm").addEventListener("submit", (e) => {
  // form submit 중지
  e.preventDefault();

  // 첨부파일 정보 수집 : uploadResult li
  const attachInfos = document.querySelectorAll(".uploadResult li");
  // data-name="" data-path="" data-uuid=""
  // 요소.dataset.name
  let result = "";
  attachInfos.forEach((obj, idx) => {
    console.log(idx);
    console.log(obj.dataset.name);
    console.log(obj.dataset.path);
    console.log(obj.dataset.uuid);
    result += `<input type="hidden" name="movieImageDTOs[${idx}].path" value="${obj.dataset.path}" />`;
    result += `<input type="hidden" name="movieImageDTOs[${idx}].uuid" value="${obj.dataset.uuid}" />`;
    result += `<input type="hidden" name="movieImageDTOs[${idx}].imgName" value="${obj.dataset.name}" />`;
  });

  e.target.insertAdjacentHTML("beforeend", result);

  // 폼 내용 확인
  console.log(e.target.innerHTML);

  e.target.submit();
});

// x를 누르면 삭제 요청 => 부모 이벤트
document.querySelector(".uploadResult").addEventListener("click", (e) => {
  // a태그 기능 중지
  e.preventDefault();
  // x 눌러진 태그 요소 찾기
  console.log(e.target);
  console.log(e.currentTarget);
  // href 값 가져오기
  const element = e.target.closest("a");
  console.log(element);
  console.log(element.getAttribute("href"));

  // 이미지 삭제
  const removeLi = e.target.closest("li");

  // 삭제 할 이미지 경로 추출
  const filePath = element.getAttribute("href");

  let formData = new FormData();
  formData.append("filePath", filePath);

  fetch("/upload/remove", {
    method: "post",
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
