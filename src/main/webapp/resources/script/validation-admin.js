window.onload = () => {
	
	document.getElementById("btn-user").onclick = function(){
		location.href = '/user';
	}
	
	document.getElementById("btn-quiz").onclick = function(){
		location.href = '/QuizListAction';
	}
	
	document.getElementById("btn-notice").onclick = function(){
		location.href = '/list';
	}	
}