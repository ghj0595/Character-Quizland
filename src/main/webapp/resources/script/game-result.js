window.onload = async () => {
	const result = document.getElementById('result');	
	const quizSize = document.getElementById('quiz_size');

	console.log(result.value)
	const resultData = JSON.parse(result.value)
	console.log(resultData)

	const num = document.getElementById('quiz-num');	
	const score=document.getElementById('quiz-score');

	const poster=document.getElementById('content-poster');
	const title = document.getElementById('content-title');
	const overview = document.getElementById('content-overview');
	
	const profile=document.getElementById('person-profile');
	const name=document.getElementById('person-name');
	
	const totalScore=document.getElementById('total_score');
	
	const button=document.getElementById('next');
	
	num.innerText=`Quiz ${resultData.num} / ${quizSize.value}`;
	score.innerText=`점수 : ${resultData.score.score}점`;
	
	poster.alt=`${resultData.content.title}`;
	poster.src=`${resultData.content.poster_path}`;
	title.href=`${resultData.content.content_path}`;
	title.innerText=`${resultData.content.title}`;
	overview.innerText=`${resultData.content.overview}`;
	
	profile.alt=`${resultData.people.name}`;
	profile.src=`${resultData.people.profile_path}`;
	name.href=`${resultData.people.people_path}`;
	name.innerText=`${resultData.people.name}`;
	
	totalScore.innerText=`현재 점수 : ${resultData.score.total_score}점`;
};