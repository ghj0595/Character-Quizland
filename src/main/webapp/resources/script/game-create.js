const fetchMakeGameData = async(num,size) => {
	let data = new Object();
	data.quiz_number=num;
	data.quiz_size=size;
	let list = new Array();
	data.solve_codes=list;
	
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


window.onload = async () => {
	let gameData = await fetchMakeGameData(1,10);
	console.log(gameData);
	
	
};