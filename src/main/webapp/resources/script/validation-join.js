import { updateErrorElementStyle, validatePassword, validateCode, validateName, checkDuplCode } from "./validation.js";

window.onload = () => {
    const form = document.getElementById("form-join");

    const code = document.getElementById("code");
    const password = document.getElementById("password");
    const chkPassword = document.getElementById("chk-password");
    const name = document.getElementById("name");

    let isValidCode = validateCode(code.value);
    let isValidPassword = validatePassword(password.value);
    let isPasswordMatched = false;
    let isValidName = validateName(name.value);

    code.addEventListener("change", async (e) => {
        const input = e.target.value;
        const errEmpty = document.getElementById("error-msg-code-empty");
        const errDupl = document.getElementById("error-msg-code");
        const errPattern = document.getElementById("error-msg-code-pattern");

        if (input === "") {
            updateErrorElementStyle(errEmpty, true);
            updateErrorElementStyle(errDupl, false);
            updateErrorElementStyle(errPattern, false);
            return;
        } else {
            updateErrorElementStyle(errEmpty, false);
        }

        isValidCode = validateCode(input);
        if (!isValidCode) {
            updateErrorElementStyle(errPattern, true);
        } else {
            updateErrorElementStyle(errPattern, false);
        }

        const isValidId = await checkDuplCode(input);
        updateErrorElementStyle(errDupl, !isValidId);
    });

    chkPassword.addEventListener("change", e => {
        const passwordValue = password.value;
        const chkPasswordValue = e.target.value;
        const errChkPassword = document.getElementById("error-msg-password-chk");

        if (chkPasswordValue !== passwordValue) {
            isPasswordMatched = false;
            updateErrorElementStyle(errChkPassword, true);
        } else {
            isPasswordMatched = true;
            updateErrorElementStyle(errChkPassword, false);
        }
    });

    chkPassword.addEventListener("focusout", e => {
        const chkPasswordValue = e.target.value;
        const errChkPassword = document.getElementById("error-msg-password-chk");

        if (chkPasswordValue !== password.value) {
            updateErrorElementStyle(errChkPassword, true);
        } else {
            updateErrorElementStyle(errChkPassword, false);
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

        if (!isPasswordMatched) {
            const error = document.getElementById("error-msg-password-chk");
            updateErrorElementStyle(error, true);
        }

        if (!isValidName && name.value === "") {
            const error = document.getElementById("error-msg-name-empty");
            updateErrorElementStyle(error, true);
        }

        isValidCode = await checkDuplCode(code.value);

        if (isValidCode && isValidPassword && isPasswordMatched && isValidName) {
            form.submit();
        }
    });
};