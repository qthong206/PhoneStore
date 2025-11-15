document.addEventListener('DOMContentLoaded', function() {

    // --- CÁC PHẦN TỬ CỦA TRANG ---
    const stepHeader1 = document.getElementById('step-header-1');
    const stepHeader2 = document.getElementById('step-header-2');

    const step1Content = document.getElementById('step-1-content');
    const step2Content = document.getElementById('step-2-content');

    const btnToStep2 = document.getElementById('btn-to-step-2');

    // NÚT BACK
    const backToCartBtn = document.getElementById('back-to-cart-btn');
    const backToStep1Btn = document.getElementById('back-to-step-1-btn');

    // TIÊU ĐỀ
    const checkoutTitle = document.getElementById('checkout-title');

    // Nút gạt (toggle) địa chỉ
    const radioDefault = document.getElementById('addr-default');
    const radioNew = document.getElementById('addr-new');
    const defaultBox = document.getElementById('default-address-box');
    const newBox = document.getElementById('new-address-box');

    // (MỚI) Lấy các input ĐỊA CHỈ
    const defaultAddressInput = document.getElementById('address-default');
    const newAddressInput = document.getElementById('address-new');

    // Modal danh sách sản phẩm
    const btnCheckItems = document.getElementById('btn-check-items');
    const listModal = document.getElementById('product-list-modal');
    const closeListBtn = document.getElementById('modal-close-list-btn');


    // --- 1. LOGIC NÚT "TIẾP TỤC" (CHUYỂN BƯỚC 1 -> 2) ---
    if (btnToStep2) {
        btnToStep2.addEventListener('click', function() {

            // Lấy form
            const form = document.getElementById('checkout-form');

            // (MỚI) Kiểm tra validation của form
            // Thao tác này sẽ tự động kiểm tra tất cả các input 'required'
            // đang hiển thị trong Bước 1
            if (!form.checkValidity()) {
                // Nếu không hợp lệ, hiển thị thông báo lỗi của trình duyệt
                form.reportValidity();
                return; // Dừng lại, không cho qua Bước 2
            }

            // A. Chuyển đổi nội dung
            step1Content.style.display = 'none';
            step2Content.style.display = 'block';

            // B. Cập nhật header
            stepHeader1.classList.remove('active');
            stepHeader2.classList.add('active');

            // C. Cập nhật NÚT BACK
            backToCartBtn.style.display = 'none';
            backToStep1Btn.style.display = 'block';

            // D. CẬP NHẬT TIÊU ĐỀ
            checkoutTitle.textContent = 'Thanh toán';

            // E. Copy thông tin tóm tắt
            updateShippingSummary();

            // F. Cuộn lên đầu trang
            window.scrollTo(0, 0);
        });
    }

    // --- 2. LOGIC NÚT "QUAY LẠI" (CHUYỂN BƯỚC 2 -> 1) ---
    if (backToStep1Btn) {
        backToStep1Btn.addEventListener('click', function(e) {
            e.preventDefault();
            step1Content.style.display = 'block';
            step2Content.style.display = 'none';
            stepHeader1.classList.add('active');
            stepHeader2.classList.remove('active');
            backToCartBtn.style.display = 'block';
            backToStep1Btn.style.display = 'none';
            checkoutTitle.textContent = 'Thông tin';
            window.scrollTo(0, 0);
        });
    }


    // --- 3. LOGIC TOGGLE (GẠT) ĐỊA CHỈ (ĐÃ CẬP NHẬT) ---
    // (Hàm này sẽ tự động thêm/xóa 'required' vào ô địa chỉ)
    function toggleAddressForm() {
        if (radioDefault && radioDefault.checked) {
            // Hiển thị khung mặc định, ẩn khung mới
            if (defaultBox) defaultBox.style.display = 'block';
            if (newBox) newBox.style.display = 'none';

            // Bật 'required' cho địa chỉ MẶC ĐỊNH
            if (defaultAddressInput) defaultAddressInput.required = true;
            // Tắt 'required' cho địa chỉ MỚI
            if (newAddressInput) newAddressInput.required = false;

        } else if (radioNew && radioNew.checked) {
            // Ẩn khung mặc định, hiển thị khung mới
            if (defaultBox) defaultBox.style.display = 'none';
            if (newBox) newBox.style.display = 'block';

            // Tắt 'required' cho địa chỉ MẶC ĐỊNH
            if (defaultAddressInput) defaultAddressInput.required = false;
            // Bật 'required' cho địa chỉ MỚI
            if (newAddressInput) newAddressInput.required = true;
        }
    }

    // Gắn sự kiện 'change' cho cả 2 nút radio
    if (radioDefault) radioDefault.addEventListener('change', toggleAddressForm);
    if (radioNew) radioNew.addEventListener('change', toggleAddressForm);

    // Chạy 1 lần khi tải trang (để set required ban đầu)
    toggleAddressForm();


    // --- 4. HÀM CẬP NHẬT TÓM TẮT (CHO BƯỚC 2) (Giữ nguyên) ---
    function updateShippingSummary() {
        let name, phone, address;
        const isGuest = (document.getElementById('name-guest') !== null);

        if (radioNew && radioNew.checked) {
            if (isGuest) {
                name = document.getElementById('name-guest').value;
                phone = document.getElementById('phone-guest').value;
            } else {
                name = document.getElementById('name-new').value;
                phone = document.getElementById('phone-new').value;
            }
            address = document.getElementById('address-new').value;
        } else {
            name = document.getElementById('name-default').value;
            phone = document.getElementById('phone-default').value;
            address = document.getElementById('address-default').value;
        }
        document.getElementById('summary-name').textContent = name;
        document.getElementById('summary-phone').textContent = phone;
        document.getElementById('summary-address').textContent = address;
    }


    // --- 5. LOGIC MODAL DANH SÁCH SẢN PHẨM (Giữ nguyên) ---
    if (btnCheckItems) {
        btnCheckItems.addEventListener('click', function(e) {
            e.preventDefault();
            if (listModal) listModal.style.display = 'flex';
        });
    }
    if (closeListBtn) {
        closeListBtn.addEventListener('click', function() {
            if (listModal) listModal.style.display = 'none';
        });
    }
    if (listModal) {
        listModal.addEventListener('click', function(e) {
            if (e.target === listModal) {
                listModal.style.display = 'none';
            }
        });
    }
});