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

    // --- 2. LOGIC MODAL (Giữ nguyên) ---
    const btnWriteReview = document.getElementById('btn-write-review');
    const loginModal = document.getElementById('review-login-modal'); // Kiểm tra lại ID này trong JSP của bạn có đúng không
    const formModal = document.getElementById('review-form-modal');
    const closeLoginBtn = document.getElementById('modal-close-login-btn');
    const closeFormBtn = document.getElementById('modal-close-form-btn');

    if (btnWriteReview) {
        btnWriteReview.addEventListener('click', function() {
            if (formModal) {
                formModal.style.display = 'flex';
            } else if (loginModal) { // Thêm check tồn tại
                loginModal.style.display = 'flex';
            } else {
                // Nếu chưa đăng nhập và không tìm thấy modal login, chuyển hướng trang login
                window.location.href = 'login';
            }
        });
    }

    if (closeLoginBtn) closeLoginBtn.addEventListener('click', () => loginModal.style.display = 'none');
    if (closeFormBtn) closeFormBtn.addEventListener('click', () => formModal.style.display = 'none');

    function closeModalOnClickOutside(modal) {
        if (modal) {
            modal.addEventListener('click', (event) => {
                if (event.target === modal) modal.style.display = 'none';
            });
        }
    }
    closeModalOnClickOutside(loginModal);
    closeModalOnClickOutside(formModal);


    // --- 3. HÀM XỬ LÝ YÊU THÍCH (DÙNG CHUNG) ---
    function handleToggleWishlist(btn) {
        const productId = btn.dataset.productId;

        if (!formModal) {
            // Logic chưa đăng nhập
            if (loginModal) {
                loginModal.style.display = 'flex';
            } else {
                alert("Vui lòng đăng nhập để sử dụng tính năng này");
                window.location.href = 'login';
            }
            return;
        }

        const isCurrentlyFavorited = btn.classList.contains('active');
        const action = isCurrentlyFavorited ? 'remove' : 'add';
        const contextPath = document.body.dataset.contextPath || '';
        const url = contextPath ? `${contextPath}/wishlist` : 'wishlist';

        fetch(url, {
            method: 'POST',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            body: `action=${action}&productId=${productId}`
        })
            .then(response => {
                if (response.status === 401) {
                    if (loginModal) loginModal.style.display = 'flex';
                    else window.location.href = 'login';
                    return null;
                }
                if (!response.ok) throw new Error('Network error');
                return response.json();
            })
            .then(data => {
                if (!data) return;
                if (data.success) {
                    btn.classList.toggle('active');

                    btn.style.transform = "scale(1.3)";
                    setTimeout(() => btn.style.transform = "scale(1)", 200);
                } else {
                    alert('Có lỗi xảy ra: ' + (data.message || 'Thử lại sau'));
                }
            })
            .catch(error => console.error('Error:', error));
    }


    // --- 4. GẮN SỰ KIỆN CHO CÁC NÚT ---

    const btnFavoriteMain = document.getElementById('btn-favorite');
    if (btnFavoriteMain) {
        btnFavoriteMain.addEventListener('click', function(e) {
            e.preventDefault();
            handleToggleWishlist(this);
        });
    }

    const relatedWishlistBtns = document.querySelectorAll('.btn-wishlist');
    relatedWishlistBtns.forEach(btn => {
        btn.addEventListener('click', function(e) {
            e.preventDefault();
            e.stopPropagation();
            handleToggleWishlist(this);
        });
    });

});