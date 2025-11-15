document.addEventListener('DOMContentLoaded', function() {

    // --- 1. LOGIC CHO VIỆC CLICK CHỌN MÀU ---
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

    // --- 2. LOGIC MỚI CHO VIỆC MỞ MODAL ĐÁNH GIÁ ---

    // Lấy các phần tử
    const btnWriteReview = document.getElementById('btn-write-review');

    // Lấy 2 modal (1 trong 2 sẽ là NULL tùy vào trạng thái đăng nhập)
    const loginModal = document.getElementById('review-login-modal');
    const formModal = document.getElementById('review-form-modal'); // Sẽ là NULL nếu chưa đăng nhập

    // Lấy các nút đóng
    const closeLoginBtn = document.getElementById('modal-close-login-btn');
    const closeFormBtn = document.getElementById('modal-close-form-btn');

    // Khi click nút "Viết đánh giá"
    if (btnWriteReview) {
        btnWriteReview.addEventListener('click', function() {
            if (formModal) {
                // TRƯỜNG HỢP 1: ĐÃ ĐĂNG NHẬP (formModal tồn tại)
                formModal.style.display = 'flex';
            } else {
                // TRƯỜNG HỢP 2: CHƯA ĐĂNG NHẬP (formModal là NULL)
                loginModal.style.display = 'flex';
            }
        });
    }

    // --- 3. LOGIC ĐÓNG CÁC MODAL ---

    // Nút đóng modal login
    if (closeLoginBtn) {
        closeLoginBtn.addEventListener('click', function() {
            loginModal.style.display = 'none';
        });
    }

    // Nút đóng modal form
    if (closeFormBtn) {
        closeFormBtn.addEventListener('click', function() {
            formModal.style.display = 'none';
        });
    }

    // Hàm đóng chung khi click ra ngoài (lớp mờ)
    function closeModalOnClickOutside(modal) {
        if (modal) {
            modal.addEventListener('click', function(event) {
                if (event.target === modal) {
                    modal.style.display = 'none';
                }
            });
        }
    }

    closeModalOnClickOutside(loginModal); // Gán cho modal login
    closeModalOnClickOutside(formModal);  // Gán cho modal form

    // --- 4. LOGIC MỚI CHO NÚT "YÊU THÍCH" ---

    const btnFavorite = document.getElementById('btn-favorite');

    if (btnFavorite) {
        btnFavorite.addEventListener('click', function() {

            // Lấy modal login (đã có sẵn từ code cũ)
            const loginModal = document.getElementById('review-login-modal');

            // Lấy ID sản phẩm từ data-attribute
            const productId = this.dataset.productId;

            // Kiểm tra xem 'formModal' có tồn tại không
            // (Chúng ta dùng 'formModal' để suy ra user đã đăng nhập chưa)
            const formModal = document.getElementById('review-form-modal');

            if (!formModal) {
                // CHƯA ĐĂNG NHẬP -> Mở modal login
                if (loginModal) {
                    loginModal.style.display = 'flex';
                }
                return; // Dừng lại
            }

            // ĐÃ ĐĂNG NHẬP -> Xử lý Fetch

            // 1. Xác định action (add hay remove)
            const isCurrentlyFavorited = this.classList.contains('active');
            const action = isCurrentlyFavorited ? 'remove' : 'add';

            // 2. Gửi dữ liệu bằng Fetch (Tương tự AJAX)
            fetch('/PhoneStore/wishlist', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: `action=${action}&productId=${productId}`
            })
                .then(response => {
                    if (response.ok) {
                        return response.json(); // Đọc JSON trả về
                    } else {
                        // Nếu lỗi (401, 500...)
                        throw new Error('Server (Wishlist) response was not ok.');
                    }
                })
                .then(data => {
                    // 3. Cập nhật Giao diện (Nút bấm)
                    if (data.success) {
                        if (data.isFavorited) {
                            this.classList.add('active'); // Thêm class 'active' (tim đỏ)
                        } else {
                            this.classList.remove('active'); // Bỏ class 'active' (tim rỗng)
                        }
                    } else {
                        alert('Có lỗi xảy ra, vui lòng thử lại.');
                    }
                })
                .catch(error => {
                    console.error('Lỗi Fetch:', error);
                    alert('Bạn cần đăng nhập hoặc có lỗi xảy ra.');
                });
        });
    }
});