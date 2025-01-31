window.onload = () => {
	
    const banButtons = document.querySelectorAll(".btn-ban");
	const unBanButtons = document.querySelectorAll(".btn-unban");
    const deleteButtons = document.querySelectorAll(".btn-delete");

    banButtons.forEach(button => {
        button.onclick = function() {
            const userCode = this.dataset.userCode;
            if (confirm("정지하시겠습니까?")) {
                location.href = "/UpdateUserStatus?userCode=" + userCode + "&status=1";
            }
        }
    });
	
	unBanButtons.forEach(button => {
	    button.onclick = function() {
	        const userCode = this.dataset.userCode;
	        if (confirm("해제하시겠습니까?")) {
	            location.href = "/UpdateUserStatus?userCode=" + userCode + "&status=0";
	        }
	    }
	});

    deleteButtons.forEach(button => {
        button.onclick = function() {
            const userCode = this.dataset.userCode;
            if (confirm("삭제하시겠습니까?")) {
                location.href = "/DeleteUser?userCode=" + userCode;
            }
        }
    });
};
