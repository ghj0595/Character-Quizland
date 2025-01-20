const fetchMakeQuizCode = async(num,size) => {
	let user = sessionStorage.getItem('log');
	console.log(user);
	let userCode = "test";
	if(user!==null){
		userCode=user.userCode;
		console.log(userCode);		
	}
	
	let data = {
	    quiz_number: num,
	    quiz_size: size,
	    solve_codes: []
	};
		
	let list = sessionStorage.getItem('solves');
	if (list) {
	    data.solve_codes = JSON.parse(list);
	}
	
	const response = await fetch(`/service/quiz?command=create`,{
		method: 'POST',
		headers: {
			'Authorization' : userCode,
		    'Content-Type': 'application/json'
		},
		body: JSON.stringify(data)
	});
	
	const json = await response.json();
	return json;
}


window.onload = async () => {
	const listContainer = document.getElementById("list-container");
	let gameData = await fetchMakeQuizCode(1,10);
	console.log(gameData);
};