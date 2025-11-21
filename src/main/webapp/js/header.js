document.addEventListener('DOMContentLoaded', function () {

    const dropdown = document.querySelector('.category-dropdown');
    const button = document.querySelector('.category-button');

    if (dropdown && button) {

        // Logic Click vào nút "Danh mục"
        button.addEventListener('click', function (event) {
            event.preventDefault(); // Ngăn link href="#" nhảy trang
            dropdown.classList.toggle('menu-open'); // Bật/Tắt class mở menu
        });

        // Logic "Click-away" (Bấm ra ngoài để đóng menu)
        document.addEventListener('click', function (event) {
            // Nếu click *bên ngoài* khối dropdown VÀ nó đang mở
            if (!dropdown.contains(event.target) && dropdown.classList.contains('menu-open')) {
                dropdown.classList.remove('menu-open');
            }
        });
    }
});

/**
 * Hàm mở Modal (Được gọi trực tiếp từ onclick ở header.jsp)
 */
function showLoginModal(event) {
    if (event) event.preventDefault(); // Ngăn thẻ a chuyển trang

    // Tìm modal bằng ID (ID này nằm trong file footer.jsp)
    const modal = document.getElementById('review-login-modal');

    if (modal) {
        modal.style.display = 'flex'; // Hiển thị modal
    } else {
        console.error("Không tìm thấy modal với ID: review-login-modal");
    }
}

/**
 * Gán sự kiện đóng Modal (Khi trang tải xong)
 */
document.addEventListener('DOMContentLoaded', function() {
    const modal = document.getElementById('review-login-modal');
    const closeBtn = document.getElementById('modal-close-login-btn');

    // Chỉ chạy nếu modal tồn tại trong trang
    if (modal && closeBtn) {

        // 1. Sự kiện click nút X (Close)
        closeBtn.addEventListener('click', function() {
            modal.style.display = 'none';
        });

        // 2. Sự kiện click ra ngoài vùng trắng (Overlay) để đóng
        window.addEventListener('click', function(event) {
            if (event.target === modal) {
                modal.style.display = 'none';
            }
        });
    }
});