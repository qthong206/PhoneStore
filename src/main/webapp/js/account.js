document.addEventListener('DOMContentLoaded', function () {
    // 1. Lấy các DOM Elements (Modals)
    const addModal = document.getElementById('addAddressModal');
    const updateModal = document.getElementById('updateAddressModal');
    const updateInfoModal = document.getElementById('updateInfoModal');

    // 2. Lấy các nút mở Modal
    const openAddBtn = document.getElementById('openAddModalBtn');
    const openUpdateBtns = document.querySelectorAll('.openUpdateModalBtn');
    const openUpdateInfoBtn = document.getElementById('openUpdateInfoModalBtn');

    // 3. Lấy tất cả các nút đóng modal (dấu X và nút Hủy)
    const closeModalBtns = document.querySelectorAll('.close-modal-btn');

    // --- HÀM HỖ TRỢ ---
    function showModal(modal) {
        if (modal) {
            modal.style.display = 'flex'; // Dùng flex để căn giữa màn hình
        }
    }

    function hideModal(modal) {
        if (modal) {
            modal.style.display = 'none';
        }
    }

    function hideAllModals() {
        hideModal(addModal);
        hideModal(updateModal);
        hideModal(updateInfoModal);
    }

    // --- SỰ KIỆN XỬ LÝ ---

    // A. Mở Modal Thêm Địa Chỉ
    if (openAddBtn) {
        openAddBtn.addEventListener('click', (e) => {
            e.preventDefault();
            const form = addModal.querySelector('form');
            if(form) form.reset();
            showModal(addModal);
        });
    }

    // B. Mở Modal Cập nhật thông tin User
    if (openUpdateInfoBtn) {
        openUpdateInfoBtn.addEventListener('click', (e) => {
            e.preventDefault();
            showModal(updateInfoModal);
        });
    }

    // C. Mở Modal Sửa Địa chỉ & ĐIỀN DỮ LIỆU CŨ
    if (openUpdateBtns) {
        openUpdateBtns.forEach(btn => {
            btn.addEventListener('click', (e) => {
                e.preventDefault();

                // 1. Lấy dữ liệu từ data attributes
                const id = btn.getAttribute('data-id');
                const name = btn.getAttribute('data-name');
                const phone = btn.getAttribute('data-phone');
                const street = btn.getAttribute('data-street');
                const type = btn.getAttribute('data-type');
                const isDefault = btn.getAttribute('data-default') === 'true';

                // 2. Điền vào Form trong Update Modal
                const idInput = document.getElementById('update-address-id');
                const nameInput = document.getElementById('update-receiver');
                const phoneInput = document.getElementById('update-phone-addr');
                const streetInput = document.getElementById('update-street');
                const radioOffice = document.getElementById('update-type-office');
                const radioHome = document.getElementById('update-type-home');
                const defaultCheck = document.getElementById('update-default');

                if (idInput) idInput.value = id;
                if (nameInput) nameInput.value = name;
                if (phoneInput) phoneInput.value = phone;
                if (streetInput) streetInput.value = street;

                // Xử lý Radio Button
                if (type === 'Văn phòng' && radioOffice) {
                    radioOffice.checked = true;
                } else if (radioHome) {
                    radioHome.checked = true;
                }

                // Xử lý Checkbox Mặc định
                if (defaultCheck) {
                    defaultCheck.checked = isDefault;
                }

                showModal(updateModal);
            });
        });
    }

    // D. Đóng Modal khi bấm nút X hoặc nút Hủy
    if (closeModalBtns) {
        closeModalBtns.forEach(btn => {
            btn.addEventListener('click', (e) => {
                e.preventDefault();
                hideAllModals();
            });
        });
    }

    // E. Đóng Modal khi click ra vùng tối bên ngoài
    window.addEventListener('click', (e) => {
        if (e.target === addModal || e.target === updateModal || e.target === updateInfoModal) {
            hideAllModals();
        }
    });
});