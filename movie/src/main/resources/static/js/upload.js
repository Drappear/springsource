// [type=file] change event 처리
const fileInput = document.querySelector("[type='file']");

function showUploadImages(files) {
  // 이미지 보여줄 영역 찾아오기
  const output = document.querySelector(".uploadResult ul");

  let tags = "";

  files.forEach((file) => {
    tags += `<li data-name="${file.fileName}" data-path="${file.folderPath}" data-uuid="${file.uuid}">`;
    tags += `  <div>`;
    tags += `   <a href="">`;
    tags += `       <img src= "/upload/display?fileName=${file.thumbImageURL}" class="block"`;
    tags += `   </a>`;
    tags += `   <span class="text-sm d-inlin-block mx-1">${file.fileName}</span>`;
    tags += `   <a href="${file.imageURL}" data-file="">`;
    tags += `       <i class="fa-solid fa-xmark"></i>`;
    tags += `   </a>`;
    tags += `  </div>`;
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
    headers: {
      "X-CSRF-TOKEN": csrfValue,
    },
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
    // console.log(idx);
    // console.log(obj.dataset.name);
    // console.log(obj.dataset.path);
    // console.log(obj.dataset.uuid);
    result += `<input type="hidden" name="movieImageDTOs[${idx}].path" value="${obj.dataset.path}" />`;
    result += `<input type="hidden" name="movieImageDTOs[${idx}].uuid" value="${obj.dataset.uuid}" />`;
    result += `<input type="hidden" name="movieImageDTOs[${idx}].imgName" value="${obj.dataset.name}" />`;
  });

  e.target.insertAdjacentHTML("beforeend", result);

  // 폼 내용 확인
  console.log(e.target.innerHTML);

  e.target.submit();
});
