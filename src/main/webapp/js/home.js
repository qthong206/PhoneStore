document.addEventListener('DOMContentLoaded', function() {

    // 1. LẤY TẤT CẢ CÁC NÚT TIM TRÊN TRANG
    const wishlistButtons = document.querySelectorAll('.btn-wishlist');

    // 2. LẤY MODAL (TỪ footer.jsp)
    const loginModal = document.getElementById('review-login-modal');
    const closeModalBtn = document.getElementById('modal-close-login-btn');

    // 3. XỬ LÝ KHI BẤM NÚT ĐÓNG MODAL
    if (closeModalBtn) {
        closeModalBtn.addEventListener('click', function() {
            loginModal.style.display = 'none';
        });
    }

    // 4. GẮN SỰ KIỆN CLICK CHO TỪNG NÚT TIM
    wishlistButtons.forEach(button => {
        button.addEventListener('click', function() {

            // Lấy ID sản phẩm từ data-attribute
            const productId = this.dataset.productId;

            // SỬ DỤNG BIẾN 'isUserLoggedIn' TOÀN CỤC MÀ home.jsp ĐÃ TẠO
            if (!isUserLoggedIn) {
                // Nếu CHƯA đăng nhập (isUserLoggedIn == false)
                // HIỆN MODAL
                if (loginModal) {
                    loginModal.style.display = 'flex';
                }
            } else {
                // Nếu ĐÃ đăng nhập (isUserLoggedIn == true)
                // GỌI AJAX
                toggleWishlist(productId, button);
            }
        });
    });

    // 5. HÀM GỌI AJAX
    function toggleWishlist(productId, button) {
        const isActive = button.classList.contains('active');
        const action = isActive ? 'remove' : 'add';
        const contextPath = document.body.dataset.contextPath || '';

        fetch(`${contextPath}/wishlist`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: `action=${action}&productId=${productId}`
        })
            .then(response => {
                // Kiểm tra 401 (Unauthorized - Session hết hạn)
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
                // Nếu data là null (do 401) thì không làm gì cả
                if (!data) return;

                if (data.success) {
                    // Cập nhật giao diện nút
                    button.classList.toggle('active');
                } else {
                    // Xử lý lỗi (nếu server trả 200 OK nhưng success: false)
                    console.error(data.message);
                }
            })
            .catch(error => console.error('Lỗi AJAX:', error));
    }

    // Đóng modal khi click ra ngoài
    if (loginModal) {
        loginModal.addEventListener('click', function(e) {
            if (e.target === loginModal) {
                loginModal.style.display = 'none';
            }
        });
    }
});