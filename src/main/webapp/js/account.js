document.addEventListener('DOMContentLoaded', function () {
    const addModal = document.getElementById('addAddressModal');
    const updateModal = document.getElementById('updateAddressModal');
    const updateInfoModal = document.getElementById('updateInfoModal');
    const changePassModal = document.getElementById('changePassModal'); // Mới

    const openAddBtn = document.getElementById('openAddModalBtn');
    const openUpdateBtns = document.querySelectorAll('.openUpdateModalBtn');
    const openUpdateInfoBtn = document.getElementById('openUpdateInfoModalBtn');
    const openChangePassBtn = document.getElementById('openChangePassBtn'); // Mới

    const closeModalBtns = document.querySelectorAll('.close-modal-btn');

    function showModal(modal) { if (modal) modal.style.display = 'flex'; }
    function hideModal(modal) { if (modal) modal.style.display = 'none'; }
    function hideAllModals() {
        hideModal(addModal); hideModal(updateModal); hideModal(updateInfoModal); hideModal(changePassModal);
    }

    if (openAddBtn) {
        openAddBtn.addEventListener('click', (e) => {
            e.preventDefault();
            const form = addModal.querySelector('form');
            if(form) form.reset();
            showModal(addModal);
        });
    }

    if (openUpdateInfoBtn) {
        openUpdateInfoBtn.addEventListener('click', (e) => {
            e.preventDefault();
            showModal(updateInfoModal);
        });
    }

    // --- MỞ MODAL ĐỔI PASS ---
    if (openChangePassBtn) {
        openChangePassBtn.addEventListener('click', (e) => {
            e.preventDefault();
            const form = changePassModal.querySelector('form');
            if(form) form.reset();
            showModal(changePassModal);
        });
    }

    if (openUpdateBtns) {
        openUpdateBtns.forEach(btn => {
            btn.addEventListener('click', (e) => {
                e.preventDefault();
                const id = btn.getAttribute('data-id');
                const name = btn.getAttribute('data-name');
                const phone = btn.getAttribute('data-phone');
                const street = btn.getAttribute('data-street');
                const type = btn.getAttribute('data-type');
                const isDefault = btn.getAttribute('data-default') === 'true';

                document.getElementById('update-address-id').value = id;
                document.getElementById('update-receiver').value = name;
                document.getElementById('update-phone-addr').value = phone;
                document.getElementById('update-street').value = street;

                if (type === 'Văn phòng') document.getElementById('update-type-office').checked = true;
                else document.getElementById('update-type-home').checked = true;

                document.getElementById('update-default').checked = isDefault;
                showModal(updateModal);
            });
        });
    }

    if (closeModalBtns) {
        closeModalBtns.forEach(btn => {
            btn.addEventListener('click', (e) => {
                e.preventDefault();
                hideAllModals();
            });
        });
    }

    window.addEventListener('click', (e) => {
        if (e.target === addModal || e.target === updateModal || e.target === updateInfoModal || e.target === changePassModal) {
            hideAllModals();
        }
    });

    // --- LOGIC ẨN HIỆN PASS ---
    const toggleEyeIcons = document.querySelectorAll('.toggle-password');
    toggleEyeIcons.forEach(icon => {
        icon.addEventListener('click', function() {
            const input = this.previousElementSibling;
            if (input && input.getAttribute('type') === 'password') {
                input.setAttribute('type', 'text');
                this.classList.remove('fa-eye');
                this.classList.add('fa-eye-slash');
            } else if (input) {
                input.setAttribute('type', 'password');
                this.classList.remove('fa-eye-slash');
                this.classList.add('fa-eye');
            }
        });
    });
});