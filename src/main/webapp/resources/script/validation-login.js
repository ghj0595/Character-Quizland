import { updateErrorElementStyle, validatePassword } from "./validation.js";

window.onload = () => {
	const id = document.getElementById("id");
	const password = document.getElementById("password");
	
	const form = document.querySelector("form");
	
	form.addEventListener("submit", e => {
		e.preventDefault();
		
		const errMsgGroup = document.getElementsByClassName("error-msg");
		for(let i=0; i<errMsgGroup.length; i++) {
			updateErrorElementStyle(errMsgGroup[i], false);
		}
		
		let isValid = true;
		
		isValid = id.value !== "";
		
		if(!isValid) {
			const errMsg = document.getElementById("error-msg-id-empty");
			updateErrorElementStyle(errMsg, true);
			return;
		}
		
		isValid = password.value !== "";
		
		if(!isValid) {
			const errMsg = document.getElementById("error-msg-password-empty");
			updateErrorElementStyle(errMsg, true);
			return;
		}
		
		isValid = validatePassword(password.value);
		
		if(!isValid) {
			const errMsg = document.getElementById("error-msg-password-pattern");
			updateErrorElementStyle(errMsg, true);
			return;
		}
		
		if(isValid) {
			form.submit();
		}
	});
	
}