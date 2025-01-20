window.onload = () => {

	const deleteButtons = document.querySelectorAll(".btn-delete");
	
	deleteButtons.forEach(button => {
	    button.onclick = function() {
	        const quizCode = this.dataset.quizCode;
	        if (confirm("삭제하시겠습니까?")) {
	            location.href = "/DeleteQuiz?code=" + quizCode;
	        }
	    }
	});
}