window.onload = () => {

	const deleteButton = document.querySelector(".btn-notice-delete");
	if (deleteButton) {
		deleteButton.onclick = function() {
			const noticeCode = document.getElementById("notice-code").value;
			if (confirm("삭제 하시겠습니까?")) {
				location.href = "/notice/delete?code=" + noticeCode;
			}
		}
	}
}