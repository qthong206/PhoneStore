/* src/main/webapp/js/common.js */

document.addEventListener("DOMContentLoaded", function() {

    // --- XỬ LÝ LOGOUT MODAL ---
    const logoutBtn = document.getElementById('btn-logout-trigger');
    const logoutModal = document.getElementById('logoutConfirmModal');
    const closeLogoutBtns = document.querySelectorAll('.close-logout-modal');

    // 1. Khi bấm vào link Đăng xuất trên menu
    if (logoutBtn) {
        logoutBtn.addEventListener('click', function(e) {
            e.preventDefault(); // Chặn chuyển trang ngay lập tức
            if (logoutModal) {
                logoutModal.style.display = 'flex'; // Hiện modal (dùng flex để căn giữa)
            }
        });
    }

    // 2. Hàm đóng modal
    function hideLogoutModal() {
        if (logoutModal) logoutModal.style.display = 'none';
    }

    // Gắn sự kiện đóng cho nút "Ở lại"
    if (closeLogoutBtns) {
        closeLogoutBtns.forEach(btn => {
            btn.addEventListener('click', hideLogoutModal);
        });
    }

    // 3. Click ra vùng tối bên ngoài modal để đóng
    if (logoutModal) {
        logoutModal.addEventListener('click', function(e) {
            if (e.target === logoutModal) {
                hideLogoutModal();
            }
        });
    }
});