// File: header.js (Bản mới: Chỉ Click)
document.addEventListener('DOMContentLoaded', function () {

    const dropdown = document.querySelector('.category-dropdown');
    const button = document.querySelector('.category-button');

    if (dropdown && button) {

        // 1. LOGIC CHO CLICK
        button.addEventListener('click', function (event) {
            event.preventDefault(); // Ngăn link href="#" nhảy trang

            // Chuyển đổi (toggle) trạng thái 'menu-open'
            dropdown.classList.toggle('menu-open');
        });

        // 2. LOGIC "CLICK-AWAY" (Bấm ra ngoài để đóng)
        document.addEventListener('click', function (event) {

            // Nếu click *bên ngoài* khối dropdown (.category-dropdown)
            // VÀ nó đang mở
            if (!dropdown.contains(event.target) && dropdown.classList.contains('menu-open')) {
                dropdown.classList.remove('menu-open');
            }
        });
    }
});