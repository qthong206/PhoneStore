document.addEventListener('DOMContentLoaded', function() {

    // --- 1. LOGIC CHỌN MÀU (Giữ nguyên) ---
    const colorList = document.getElementById('color-options-list');
    if (colorList) {
        const colorOptions = colorList.querySelectorAll('.option-item');
        colorOptions.forEach(option => {
            option.addEventListener('click', function(event) {
                event.preventDefault();
                colorOptions.forEach(opt => opt.classList.remove('active'));
                this.classList.add('active');
            });
        });
    }

    // --- 2. LOGIC MỞ MODAL ĐÁNH GIÁ (Giữ nguyên) ---
    const btnWriteReview = document.getElementById('btn-write-review');
    const loginModal = document.getElementById('review-login-modal');
    const formModal = document.getElementById('review-form-modal');
    const closeLoginBtn = document.getElementById('modal-close-login-btn');
    const closeFormBtn = document.getElementById('modal-close-form-btn');

    if (btnWriteReview) {
        btnWriteReview.addEventListener('click', function() {
            if (formModal) {
                formModal.style.display = 'flex';
            } else {
                loginModal.style.display = 'flex';
            }
        });
    }

    // --- 3. LOGIC ĐÓNG CÁC MODAL (Giữ nguyên) ---
    if (closeLoginBtn) {
        closeLoginBtn.addEventListener('click', function() {
            loginModal.style.display = 'none';
        });
    }
    if (closeFormBtn) {
        closeFormBtn.addEventListener('click', function() {
            formModal.style.display = 'none';
        });
    }
    function closeModalOnClickOutside(modal) {
        if (modal) {
            modal.addEventListener('click', function(event) {
                if (event.target === modal) {
                    modal.style.display = 'none';
                }
            });
        }
    }
    closeModalOnClickOutside(loginModal);
    closeModalOnClickOutside(formModal);

    // --- 4. LOGIC NÚT "YÊU THÍCH" (ĐÃ SỬA LỖI) ---
    const btnFavorite = document.getElementById('btn-favorite');

    if (btnFavorite) {
        btnFavorite.addEventListener('click', function() {

            const productId = this.dataset.productId;

            // Logic kiểm tra đăng nhập (đã đúng)
            if (!formModal) {
                if (loginModal) {
                    loginModal.style.display = 'flex';
                }
                return;
            }

            // ĐÃ ĐĂNG NHẬP -> Xử lý Fetch
            const isCurrentlyFavorited = this.classList.contains('active');
            const action = isCurrentlyFavorited ? 'remove' : 'add';

            // SỬA LỖI 1: Lấy contextPath (giống home.js)
            const contextPath = document.body.dataset.contextPath || '';

            // SỬA LỖI 2: Dùng contextPath trong URL
            fetch(`${contextPath}/wishlist`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: `action=${action}&productId=${productId}`
            })
                .then(response => {
                    // SỬA LỖI 3: Thêm logic kiểm tra 401 (Session hết hạn)
                    if (response.status === 401) {
                        if (loginModal) loginModal.style.display = 'flex';
                        return null; // Dừng lại
                    }
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.json();
                })
                .then(data => {
                    if (!data) return; // Bị 401

                    if (data.success) {
                        this.classList.toggle('active');
                    } else {
                        alert('Có lỗi xảy ra, vui lòng thử lại.');
                    }
                })
                .catch(error => {
                    console.error('Lỗi Fetch:', error);
                    // Xóa alert cũ vì nó gây nhầm lẫn
                });
        });
    }
});