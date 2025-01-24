const fetchData = async() => {
	const response = await fetch(`/service/quiz?command=total`,{
		method: 'GET',
		headers: {
		    'Content-Type': 'application/json'
		},
	});
	
	const json = await response.json();
	return json;
}

window.onload = async () => {
	let totalData = await fetchData();
	
	const score = document.getElementById('total-score');	
	const rank=document.getElementById('current-rank');
	const percentage=document.getElementById('current-per');
	const congratulation=document.getElementById('congratulation');


	score.textContent = `총 점수 : ${totalData.score}점`;
	rank.textContent = `현재 점수의 순위 : ${totalData.rank}위`;
	percentage.textContent = `상위 ${totalData.percentage}%입니다.`;
	
	
	if(totalData.is_best_Score)
		congratulation.style.display="";
};