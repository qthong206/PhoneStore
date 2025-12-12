document.addEventListener("DOMContentLoaded", function () {

    // --- (Phần code Load More giữ nguyên) ---
    const btnLoadMore = document.getElementById("btnLoadMore");
    if (btnLoadMore) {
        // ... (giữ nguyên logic load more cũ) ...
        const itemsPerLoad = 20;
        btnLoadMore.addEventListener("click", function () {
            const hiddenItems = document.querySelectorAll(".product-card.hidden-item");
            const countToShow = Math.min(itemsPerLoad, hiddenItems.length);
            for (let i = 0; i < countToShow; i++) {
                hiddenItems[i].classList.remove("hidden-item");
            }
            const remaining = hiddenItems.length - countToShow;
            const countSpan = document.getElementById("remainingCount");
            if (countSpan) countSpan.textContent = remaining;
            if (remaining <= 0) btnLoadMore.style.display = "none";
        });
    }

    // ==========================================
    // 2. XỬ LÝ NÚT YÊU THÍCH (CÓ KIỂM TRA ĐĂNG NHẬP)
    // ==========================================
    const wishlistBtns = document.querySelectorAll(".btn-wishlist");
    const loginModal = document.getElementById('review-login-modal'); // Lấy modal từ footer
    const closeModalBtn = document.getElementById('modal-close-login-btn');

    // Xử lý đóng modal
    if (closeModalBtn) {
        closeModalBtn.addEventListener('click', () => loginModal.style.display = 'none');
    }
    if (loginModal) {
        loginModal.addEventListener('click', (e) => {
            if (e.target === loginModal) loginModal.style.display = 'none';
        });
    }

    wishlistBtns.forEach(btn => {
        btn.addEventListener("click", function (e) {
            e.preventDefault();
            e.stopPropagation();

            // 1. KIỂM TRA ĐĂNG NHẬP (Biến toàn cục từ JSP)
            if (typeof isUserLoggedIn !== 'undefined' && !isUserLoggedIn) {
                if (loginModal) {
                    loginModal.style.display = 'flex'; // Hiện modal đăng nhập
                } else {
                    alert("Vui lòng đăng nhập để thực hiện chức năng này.");
                }
                return; // Dừng lại, không gọi server
            }

            // 2. NẾU ĐÃ ĐĂNG NHẬP -> XỬ LÝ LOGIC
            const productId = this.getAttribute("data-product-id");

            // Hiệu ứng UI (Optimistic UI)
            this.classList.toggle("active");

            // Gọi AJAX (Mở comment phần này khi bạn có API Wishlist)
            /*
            const action = this.classList.contains('active') ? 'add' : 'remove';
            fetch('wishlist', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: `action=${action}&productId=${productId}`
            })
            .then(res => {
                if (res.status === 401) { // Session hết hạn
                    this.classList.toggle("active"); // Revert UI
                    if(loginModal) loginModal.style.display = 'flex';
                }
            });
            */

            console.log("Đã toggle yêu thích (User Logged In) - ID: " + productId);
        });
    });
});