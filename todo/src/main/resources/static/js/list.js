// 완료 클릭시 체크박스 value 가져오기
document.querySelector("tbody").addEventListener("click", (e) => {
  const completeCheck = e.target;
  // completeForm id value값 변경
  const comForm = document.querySelector("#completeForm");
  comForm.querySelector("[name='id']").value = completeCheck.value;
  comForm.querySelector("[name='completed']").value = completeCheck.checked;
  console.log(comForm.innerHTML);

  //   const idVal = comForm.querySelector("[name='id']").value;
  //   const compCheck = comForm.querySelector("[name='completed']").value;

  //   if (idVal == undefined || idVal == null) {
  //     e.preventDefault;
  //     return;
  //   } else {
  comForm.submit();
  //   }
});
