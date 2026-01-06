document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('register-form');
    const nameInput = document.getElementById('fullName');
    const phoneInput = document.getElementById('phone');
    const emailInput = document.getElementById('email');
    const passwordInput = document.getElementById('password');
    const confirmInput = document.getElementById('confirmPassword');

    const nameError = document.getElementById('name-error');
    const phoneError = document.getElementById('phone-error');
    const emailError = document.getElementById('email-error');
    const confirmError = document.getElementById('confirm-error');
    const passwordNote = document.querySelector('.password-note');

    // 1. Kiểm tra mật khẩu (Đổi màu Note)
    function validatePassword() {
        const val = passwordInput.value;
        const regex = /^(?=.*[A-Za-z])(?=.*\d).+$/;
        const isValid = val.length >= 6 && regex.test(val);

        if (val === "") {
            passwordNote.style.color = "";
            return false;
        }
        passwordNote.style.color = isValid ? "#28a745" : "#dc3545";
        return isValid;
    }

    // 2. Tự động chuẩn hóa tên (Xóa cách thừa, Viết hoa chữ đầu)
    nameInput.addEventListener('blur', function() {
        let val = this.value.trim().replace(/\s+/g, ' ');
        if (val !== "") {
            this.value = val.split(' ')
                .map(word => word.charAt(0).toUpperCase() + word.slice(1).toLowerCase())
                .join(' ');
        }
    });

    // 3. Xóa báo lỗi khi người dùng gõ lại
    [nameInput, phoneInput, emailInput, passwordInput, confirmInput].forEach(input => {
        input.addEventListener('input', () => {
            const errorId = input.id === 'confirmPassword' ? 'confirm-error' : input.id + '-error';
            const errorSpan = document.getElementById(errorId);
            if (errorSpan) errorSpan.textContent = "";
        });
    });

    passwordInput.addEventListener('input', validatePassword);

    // 4. Xử lý khi Submit
    form.addEventListener('submit', function(e) {
        let isFormValid = true;

        const nameVal = nameInput.value.trim();
        const nameRegex = /^[a-zA-ZÀ-ỹ\s]+$/;
        if (nameVal.length < 2 || !nameRegex.test(nameVal)) {
            nameError.textContent = "Họ tên phải từ 2 ký tự chữ";
            isFormValid = false;
        }

        const phoneVal = phoneInput.value.trim();
        if (!/^0\d{9}$/.test(phoneVal)) {
            phoneError.textContent = "Số điện thoại phải gồm 10 số";
            isFormValid = false;
        }

        const emailVal = emailInput.value.trim();
        if (emailVal !== "" && !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(emailVal)) {
            emailError.textContent = "Email không hợp lệ";
            isFormValid = false;
        }

        if (!validatePassword()) isFormValid = false;

        if (confirmInput.value !== passwordInput.value) {
            confirmError.textContent = "Mật khẩu không khớp";
            isFormValid = false;
        }

        if (!isFormValid) {
            e.preventDefault();
        }
    });

    // 5. Con mắt
    document.querySelectorAll('.btn-toggle-password').forEach(btn => {
        btn.onclick = function() {
            const input = this.previousElementSibling;
            const icon = this.querySelector('i');
            input.type = input.type === 'password' ? 'text' : 'password';
            icon.classList.toggle('fa-eye');
            icon.classList.toggle('fa-eye-slash');
        };
    });
});