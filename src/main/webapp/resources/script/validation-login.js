import { updateErrorElementStyle, validatePassword } from "./validation.js";

window.onload = () => {
    const code = document.getElementById("code");
    const password = document.getElementById("password");
    
    const form = document.getElementById("form");
    
    form.addEventListener("submit", e => {
        e.preventDefault();
        
        const errMsgGroup = document.getElementsByClassName("error-msg");
        for (let i = 0; i < errMsgGroup.length; i++) {
            updateErrorElementStyle(errMsgGroup[i], false);
        }
        
        let isValid = true;

        if (code.value === "") {
            const errMsg = document.getElementById("error-msg-code-empty");
            updateErrorElementStyle(errMsg, true);
            isValid = false;
        }
        
        if (password.value === "") {
            const errMsg = document.getElementById("error-msg-password-empty");
            updateErrorElementStyle(errMsg, true);
            isValid = false;
        }

        if (!validatePassword(password.value)) {
            const errMsg = document.getElementById("error-msg-password-pattern");
            updateErrorElementStyle(errMsg, true);
            isValid = false;
        }

        if (isValid) {
            form.submit();
        }
    });
}
