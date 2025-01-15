export function updateErrorElementStyle(element, visible) {
	if(visible) {
		element.style.display = "block";
	} else {
		element.style.display = "none";	
	}
}

export function validateCode(code) {
	const regex = /^[a-z0-9](?:[_-]*[a-z0-9]){2,19}$/;
	return regex.test(code);
}

export function validatePassword(password) {
	const regex = /^[a-zA-Z0-9_\-~`!@#$%^&*()+=|\\'";:.,/]{5,}$/;
	return regex.test(password);
}

export function validateName(name) {
	const regex = /^[가-힣a-zA-Z]{2,19}$/;
	return regex.test(name);
}

export async function checkDuplCode(code) {
	const response = await fetch("/service/api?command=search-code", {
		method: "POST",
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify({
			"code": code
		})
	});
	const json = await response.json();

	return json.isValid;
}