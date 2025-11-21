document.addEventListener('DOMContentLoaded', function () {
    // 1. Lấy các phần tử Modal
    const addModal = document.getElementById('addAddressModal');
    const updateModal = document.getElementById('updateAddressModal');
    const updateInfoModal = document.getElementById('updateInfoModal');

    // 2. Lấy các nút Mở Modal
    const openAddBtn = document.getElementById('openAddModalBtn');
    const openUpdateBtns = document.querySelectorAll('.openUpdateModalBtn');
    const openUpdateInfoBtn = document.getElementById('openUpdateInfoModalBtn');

    // 3. Lấy tất cả nút Đóng
    const closeModalBtns = document.querySelectorAll('.close-modal-btn');

    // --- HÀM XỬ LÝ ---
    function showModal(modal) {
        if (modal) modal.style.display = 'flex';
    }

    function hideModal(modal) {
        if (modal) modal.style.display = 'none';
    }

    function hideAllModals() {
        hideModal(addModal);
        hideModal(updateModal);
        hideModal(updateInfoModal);
    }

    // --- GÁN SỰ KIỆN ---

    // Mở Modal Thêm địa chỉ
    if (openAddBtn) {
        openAddBtn.addEventListener('click', (e) => {
            e.preventDefault(); // Ngăn link # nhảy trang
            showModal(addModal);
        });
    }

    // Mở Modal Sửa địa chỉ (Dùng class vì có thể có nhiều nút sửa)
    openUpdateBtns.forEach(btn => {
        btn.addEventListener('click', (e) => {
            e.preventDefault();
            showModal(updateModal);
        });
    });

    // Mở Modal Cập nhật thông tin
    if (openUpdateInfoBtn) {
        openUpdateInfoBtn.addEventListener('click', (e) => {
            e.preventDefault();
            showModal(updateInfoModal);
        });
    }

    // Nút Đóng (dấu X)
    closeModalBtns.forEach(btn => {
        btn.addEventListener('click', hideAllModals);
    });

    // Đóng khi click ra ngoài vùng trắng (Overlay)
    window.addEventListener('click', function (event) {
        if (event.target === addModal || event.target === updateModal || event.target === updateInfoModal) {
            hideAllModals();
        }
    });
});