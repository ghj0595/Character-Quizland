import { updateErrorElementStyle, validatePassword } from "./validation.js";

window.onload = () => {
    const code = document.getElementById("code");
    const password = document.getElementById("password");
    
    const form = document.getElementById("form"); // 수정된 부분
    
    form.addEventListener("submit", e => {
        e.preventDefault(); // 폼 제출 기본 동작 방지
        
        // 모든 오류 메시지 숨기기
        const errMsgGroup = document.getElementsByClassName("error-msg");
        for (let i = 0; i < errMsgGroup.length; i++) {
            updateErrorElementStyle(errMsgGroup[i], false);
        }
        
        let isValid = true;

        // 아이디가 비어있는지 확인
        if (code.value === "") {
            const errMsg = document.getElementById("error-msg-code-empty");
            updateErrorElementStyle(errMsg, true);
            isValid = false;
        }
        
        // 비밀번호가 비어있는지 확인
        if (password.value === "") {
            const errMsg = document.getElementById("error-msg-password-empty");
            updateErrorElementStyle(errMsg, true);
            isValid = false;
        }

        // 비밀번호 패턴 검사
        if (!validatePassword(password.value)) {
            const errMsg = document.getElementById("error-msg-password-pattern");
            updateErrorElementStyle(errMsg, true);
            isValid = false;
        }

        // 모든 검증을 통과했으면 폼을 제출
        if (isValid) {
            form.submit(); // 폼을 제출
        }
    });
}
