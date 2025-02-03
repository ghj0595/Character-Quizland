const fetchUsersCount = async() => {
	const response = await fetch(`/service/api?command=users-count`);
	const json = await response.json();
	return json;
}


window.onload = async () => {
	const usersCount = document.getElementById('users-count');
	const users = await fetchUsersCount();
	usersCount.innerText=`오늘 방문자 수 : ${users.dailyUsers}명`;
};