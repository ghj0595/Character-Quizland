export function updateErrorElementStyle(element, visible) {
	if(visible) {
		element.style.display = "block";
	} else {
		element.style.display = "none";	
	}
}

export function validateId(id) {
	const regex = /^[a-z0-9](?:[_-]*[a-z0-9]){4,19}$/;
	return regex.test(id);
}

export function validatePassword(password) {
	const regex = /^[a-zA-Z0-9_\-~`!@#$%^&*()+=|\\'";:.,/]{8,16}$/;
	return regex.test(password);
}

export function validateName(name) {
	const regex = /^[가-힣a-zA-Z]+$/;
	return regex.test(name);
}

export function validateContact(contact) {
	const regex = /^[0-9]{3}-[0-9]{4}-[0-9]{4}$/;
	return regex.test(contact);
}

export function formatContactString(str) {
	let result = "";
	
	str = str.replaceAll(/\D/g, "");
	
	if(str.length === 11 && /^[010]/.test(str)) {
		const head = str.substring(0, 3);
		const mid = str.substring(3,7);
		const tail = str.substring(7,11);		
		result = `${head}-${mid}-${tail}`;
	} else if(str.length === 10 && /^[011|016|017|018|019]/.test(str)) {
		const head = str.substring(0, 3);
		const mid = str.substring(3,6);
		const tail = str.substring(6,10);		
		result = `${head}-${mid}-${tail}`;
	} else {
		return str;
	}
	
	return result;
}

export async function checkDuplId(id) {
	const response = await fetch("/service/api?command=search-id", {
		method: "POST",
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify({
			"id": id
		})
	});
	const json = await response.json();

	return json.isValid;
}

export async function checkDuplContact(contact) {
	const response = await fetch("/service/api?command=search-contact", {
		method: "POST",
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify({
			"contact": contact
		})
	});
	const json = await response.json();

	return json.isValid;
}