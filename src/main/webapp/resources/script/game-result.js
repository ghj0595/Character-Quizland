window.onload = () => {
	const result = document.getElementById('result');	
	const quizSize = document.getElementById('quiz_size');

	const resultData = JSON.parse(result.value)
	
	const num = document.getElementById('quiz-num');	
	const score=document.getElementById('quiz-score');

	const poster=document.getElementById('content-poster');
	const title = document.getElementById('content-title');
	const overview = document.getElementById('content-overview');
	
	const profile=document.getElementById('person-profile');
	const name=document.getElementById('person-name');
	
	const totalScore=document.getElementById('total_score');
	
	const form = document.getElementById('result-form');
	const submit=document.getElementById('next');
	
	title.innerHTML = "";
	overview.innerHTML = "";
	name.innerHTML = "";
	
	num.textContent = `Quiz ${resultData.num} / ${quizSize.value}`;
	score.textContent = `이번 점수 : ${resultData.score.score}점`;
	
	const contentTitle=resultData.content.type===0? resultData.content.title : resultData.content.name;
	poster.alt = contentTitle || "포스터 이미지";
	poster.src = resultData.content.poster_path || "default-poster.png";
	
	title.appendChild(createLinkElement(resultData.content.content_path, contentTitle , "제목 없음"));
	
	if (resultData.content.overview && resultData.content.overview.trim() !== "") {
		let overviewData=resultData.content.overview;
		if(overviewData.length>150)
			overviewData=overviewData.slice(0, 150) + ".....";

		const overviewText = document.createTextNode(overviewData);
	    overview.appendChild(overviewText);
	} else {
	    overview.textContent = "줄거리가 제공되지 않는 작품입니다.";
	}

	
	profile.alt = resultData.people.name || "프로필 이미지";
	profile.src = resultData.people.profile_path || "default-profile.png";
	
	name.appendChild(createLinkElement(resultData.people.people_path, resultData.people.name, "이름 없음"));
	
	totalScore.textContent = `현재까지 총점 : ${resultData.score.total_score}점`;
	
	if (resultData.num == quizSize.value) {
	    submit.value = "전체 결과";
		form.action="/total";
	} else {
	    submit.value = "다음 게임";
		form.action="/game";
	}
};

function createLinkElement(href, text, defaultText) {
    const link = document.createElement("a");
    link.href = href || "#";
    link.textContent = text?.trim() || defaultText;
    link.target = "_blank";
    return link;
}
