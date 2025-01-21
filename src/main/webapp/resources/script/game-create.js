const fetchData = async(data) => {
	const response = await fetch(`/service/quiz?command=create`,{
		method: 'POST',
		headers: {
		    'Content-Type': 'application/json'
		},
		body: JSON.stringify(data)
	});
	
	const json = await response.json();
	return json;
}

const fetchResult = async(data) => {
	const response = await fetch(`/service/quiz?command=result`,{
		method: 'POST',
		headers: {
		    'Content-Type': 'application/json'
		},
		body: JSON.stringify(data)
	});
	
	const json = await response.json();
	return json;
}


window.onload = async () => {
	const form = document.getElementById('game-form');
	
	const formData = new FormData(form);

	const jsonData = {};
	formData.forEach((value, key) => {
		if(key==="solve_codes")
			jsonData[key] = JSON.parse(value);
		else
			jsonData[key] = value;
	});
	
	let quizData = await fetchData(jsonData);
	
	const quizCode = document.getElementById('quiz_code');	
	const score = document.getElementById('score');	
	const timer = document.getElementById('timer');	
	const solveCodes = document.getElementById('solve_codes');
	const viewTimer = document.getElementById('view-timer');	
	
	const poster=document.getElementById('quiz-poster');
	const images=document.getElementsByClassName('answer-image');
		
	quizCode.value = quizData.quiz_code;
	solveCodes.value = JSON.stringify(quizData.solve_codes);
	
	poster.src= quizData.poster_path;
	for (let i = 0; i < images.length; i++) {
		images[i].src=quizData.options[i]
		
	}
	
	let remainingTime = 20
	let countdown = setInterval(()=>{
		if(remainingTime > 0){
			remainingTime-=0.01;
			timer.value = remainingTime;
			viewTimer.innerText = remainingTime.toFixed(2);
		}else{
			clearInterval(timer);
		}
	},10); 
};