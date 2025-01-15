import { updateErrorElementStyle, validatePassword,validateCode, validateName, checkDuplCode} from "./validation.js";

window.onload = () => {
	const form = document.getElementById("form-join");

	const code = document.getElementById("code");
	const password = document.getElementById("password");
	const name = document.getElementById("name");

	let isValidCode = validateCode(code.value);
	let isValidPassword = validatePassword(password.value);
	let isValidName = validateName(name.value);

	code.addEventListener("change", async (e) => {
		const input = e.target.value;

		const errEmpty = document.getElementById("error-msg-code-empty");
		const errDupl = document.getElementById("error-msg-code");

		if (input === "") {
			updateErrorElementStyle(errEmpty, true);
			return;
		} else {
			updateErrorElementStyle(errEmpty, false);
		}

		isValidCode= await checkDuplCode(input);
		updateErrorElementStyle(errDupl, !isValidCode);
	});

	code.addEventListener("focusout", e => {
		const input = e.target.value;

		const errEmpty = document.getElementById("error-msg-code-empty");

		if (input === "") {
			updateErrorElementStyle(errEmpty, true);
			return;
		} else {
			updateErrorElementStyle(errEmpty, false);
		}
	});

	password.addEventListener("change", e => {
		const input = e.target.value;

		const errPattern = document.getElementById("error-msg-password-pattern");
		const errEmpty = document.getElementById("error-msg-password-empty");

		if (input === "") {
			updateErrorElementStyle(errEmpty, true);
			return;
		} else {
			updateErrorElementStyle(errEmpty, false);
		}

		isValidPassword = validatePassword(input);

		if (!isValidPassword) {
			updateErrorElementStyle(errPattern, true);
		} else {
			updateErrorElementStyle(errPattern, false);
		}
	});

	password.addEventListener("focusout", e => {
		const input = e.target.value;

		const errEmpty = document.getElementById("error-msg-password-empty");

		if (input === "") {
			updateErrorElementStyle(errEmpty, true);
			return;
		} else {
			updateErrorElementStyle(errEmpty, false);
		}
	});

	name.addEventListener("change", e => {
		const input = e.target.value;

		const errPattern = document.getElementById("error-msg-name-pattern");
		const errEmpty = document.getElementById("error-msg-name-empty");

		if (input === "") {
			updateErrorElementStyle(errEmpty, true);
			return;
		} else {
			updateErrorElementStyle(errEmpty, false);
		}

		isValidName = validateName(input);

		if (!isValidName) {
			updateErrorElementStyle(errPattern, true);
		} else {
			updateErrorElementStyle(errPattern, false);
		}
	});

	name.addEventListener("focusout", e => {
		const input = e.target.value;

		const errEmpty = document.getElementById("error-msg-name-empty");

		if (input === "") {
			updateErrorElementStyle(errEmpty, true);
			return;
		} else {
			updateErrorElementStyle(errEmpty, false);
		}
	});

	form.addEventListener("submit", async (e) => {
	    e.preventDefault();

	    if (!isValidCode && code.value === "") {
	        const error = document.getElementById("error-msg-code-empty");
	        updateErrorElementStyle(error, true);
	    }

	    if (!isValidPassword && password.value === "") {
	        const error = document.getElementById("error-msg-password-empty");
	        updateErrorElementStyle(error, true);
	    }

	    if (!isValidName && name.value === "") {
	        const error = document.getElementById("error-msg-name-empty");
	        updateErrorElementStyle(error, true);
	    }

	    isValidCode = await checkDuplCode(code.value);

	    if (isValidCode && isValidPassword && isValidName) {
	        form.submit();
	    }
	});

}