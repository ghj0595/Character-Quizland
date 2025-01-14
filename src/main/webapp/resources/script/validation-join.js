import { updateErrorElementStyle, validatePassword,validateId, validateName, validateContact, formatContactString, checkDuplContact, checkDuplId } from "./validation.js";

window.onload = () => {
	const form = document.getElementById("form-join");

	const id = document.getElementById("id");
	const password = document.getElementById("password");
	const name = document.getElementById("name");
	const contact = document.getElementById("contact");

	let isValidId = validateId(id.value);
	let isValidPassword = validatePassword(password.value);
	let isValidName = validateName(name.value);
	let isValidContact = validateContact(contact.value);

	id.addEventListener("change", async (e) => {
		const input = e.target.value;

		const errEmpty = document.getElementById("error-msg-id-empty");
		const errDupl = document.getElementById("error-msg-id");

		if (input === "") {
			updateErrorElementStyle(errEmpty, true);
			return;
		} else {
			updateErrorElementStyle(errEmpty, false);
		}

		isValidId = await checkDuplId(input);
		updateErrorElementStyle(errDupl, !isValidId);
	});

	id.addEventListener("focusout", e => {
		const input = e.target.value;

		const errEmpty = document.getElementById("error-msg-id-empty");

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

	contact.addEventListener("change", async (e) => {
		let input = e.target.value;

		const errPattern = document.getElementById("error-msg-contact-pattern");
		const errEmpty = document.getElementById("error-msg-contact-empty");
		const errMsg = document.getElementById("error-msg-contact");

		if (input === "") {
			updateErrorElementStyle(errEmpty, true);
			return;
		} else {
			updateErrorElementStyle(errEmpty, false);
		}

		input = formatContactString(input);
		contact.value = input;

		isValidContact = validateContact(input);

		if (!isValidContact) {
			updateErrorElementStyle(errPattern, true);
		} else {
			updateErrorElementStyle(errPattern, false);
		}

		isValidContact = await checkDuplContact(input);
		updateErrorElementStyle(errMsg, !isValidContact);
	});

	contact.addEventListener("focusout", e => {
		const input = e.target.value;

		const errEmpty = document.getElementById("error-msg-contact-empty");

		if (input === "") {
			updateErrorElementStyle(errEmpty, true);
			return;
		} else {
			updateErrorElementStyle(errEmpty, false);
		}
	});

	form.addEventListener("submit", async (e) => {
	    e.preventDefault();

	    if (!isValidId && id.value === "") {
	        const error = document.getElementById("error-msg-id-empty");
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

	    if (!isValidContact && contact.value === "") {
	        const error = document.getElementById("error-msg-contact-empty");
	        updateErrorElementStyle(error, true);
	    }

	    isValidId = await checkDuplId(id.value);
	    isValidContact = await checkDuplContact(contact.value);

	    if (isValidId && isValidPassword && isValidName && isValidContact) {
	        form.submit();
	    }
	});

}