window.onload = () => {
	
    const banButtons = document.querySelectorAll(".btn-ban");
    const deleteButtons = document.querySelectorAll(".btn-delete");

    banButtons.forEach(button => {
        button.onclick = function() {
            const userCode = this.dataset.userCode;
            if (confirm("정지하시겠습니까?")) {
                location.href = "/UpdateUserStatus?userCode=" + userCode + "&status=1";
            }
        }
    });

    deleteButtons.forEach(button => {
        button.onclick = function() {
            const userCode = this.dataset.userCode;
            if (confirm("삭제하시겠습니까?")) {
                location.href = "/UpdateUserCloseDate?userCode=" + userCode;
            }
        }
    });
};
