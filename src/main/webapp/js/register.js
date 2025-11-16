document.addEventListener('DOMContentLoaded', function() {

    // Lấy TẤT CẢ các nút "con mắt"
    const toggleButtons = document.querySelectorAll('.btn-toggle-password');

    toggleButtons.forEach(button => {
        button.addEventListener('click', function() {
            // Lấy ô input ngay trước nút này
            const passwordInput = this.previousElementSibling;

            // Lấy icon bên trong nút
            const icon = this.querySelector('i');

            if (passwordInput.type === 'password') {
                // Nếu đang là password -> chuyển sang text
                passwordInput.type = 'text';
                icon.classList.remove('fa-eye');
                icon.classList.add('fa-eye-slash');
            } else {
                // Nếu đang là text -> chuyển về password
                passwordInput.type = 'password';
                icon.classList.remove('fa-eye-slash');
                icon.classList.add('fa-eye');
            }
        });
    });
});