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
	const loading = document.getElementById('loading');	
	const form = document.getElementById('game-form');
	
	const formData = new FormData(form);

	const jsonData = {};
	
	formData.forEach((value, key) => {
		if(key==="solve_codes"){
			jsonData[key] = JSON.parse(value);
		}
		else
			jsonData[key] = value;
	});
	
	let quizData = await fetchData(jsonData);

	const quizNumber = document.getElementById('quiz_number');		
	const quizSize = document.getElementById('quiz_size');		
	const quizCode = document.getElementById('quiz_code');	
	const score = document.getElementById('score');	
	const timer = document.getElementById('timer');	
	const solveCodes = document.getElementById('solve_codes');

	const viewNumber = document.getElementById('quiz-num');	
	const viewTimer = document.getElementById('view-timer');	
	
	const poster=document.getElementById('quiz-poster');
	const question=document.getElementById('quiz-question');
	const images=document.getElementsByClassName('answer-image');

	quizNumber.value = quizData.solve_codes.length;
	quizCode.value = quizData.quiz_code;
	solveCodes.value = JSON.stringify(quizData.solve_codes);
	viewNumber.innerText=`Quiz ${quizNumber.value} / ${quizSize.value}`;

	poster.src= quizData.poster_path;
	for (let i = 0; i < images.length; i++) {
		images[i].src=quizData.options[i]
	}
	
	const character = quizData.character_name;
	
	const questionText = (character, score ) =>{
		question.innerText=`해당 작품의 ${character}을(를) 연기한 배우를 선택하세요.(${score}점)`;
	}
	
	questionText(character,score.value);
	
	let isCalled=false;
	
	const fetchCall = async ()=>{
		if(!isCalled){
			const resultData = new FormData(form);
	
			const reqData = {};
			resultData.forEach((value, key) => {
				if(key==="solve_codes")
					reqData[key] = JSON.parse(value);
				else
					reqData[key] = value;
			});
			isCalled=true;
			await fetchResult(reqData);
			location.href="/result";
		}
	};
	
	const buttons=document.getElementsByClassName('answer');
	for (let i = 0; i < buttons.length; i++) {
		if(i===quizData.answer_number){
			buttons[i].addEventListener("click", async (e) => {
				fetchCall();
			});
		}else{
			buttons[i].addEventListener("click", e => {
				score.value-=3;
				questionText(character,score.value);
				e.target.disabled = true;
			});
		}
	}
	
	form.addEventListener("submit", e => {
	    e.preventDefault();
	});
	
	loading.style.display="none";
	form.style.display="";
	let remainingTime = 20
	let countdown = setInterval(async ()=>{
		if(remainingTime >= 0.01){
			remainingTime-=0.01;
			timer.value = remainingTime;
			viewTimer.innerText = remainingTime.toFixed(2);
		}else{
			clearInterval(timer);
			fetchCall();
		}
	},10);
	countdown;
};