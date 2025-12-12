document.addEventListener('DOMContentLoaded', function() {
    const step1Content = document.getElementById('step-1-content');
    const step2Content = document.getElementById('step-2-content');
    const stepHeader1 = document.getElementById('step-header-1');
    const stepHeader2 = document.getElementById('step-header-2');
    const btnToStep2 = document.getElementById('btn-to-step-2');
    const backToStep1Btn = document.getElementById('back-to-step-1-btn');
    const backToCartBtn = document.getElementById('back-to-cart-btn');
    const checkoutTitle = document.getElementById('checkout-title');

    // Địa chỉ
    const radioDefault = document.getElementById('addr-default');
    const radioNew = document.getElementById('addr-new');
    const defaultBox = document.getElementById('default-address-box');
    const newBox = document.getElementById('new-address-box');
    const addressSelector = document.getElementById('address-selector');

    // Input cần validate (chỉ có khi login)
    const nameNew = document.getElementById('name-new');
    const phoneNew = document.getElementById('phone-new');
    const addressNew = document.getElementById('address-new');

    // --- CHUYỂN BƯỚC ---
    if (btnToStep2) {
        btnToStep2.addEventListener('click', function() {
            const form = document.getElementById('checkout-form');
            if (!form.checkValidity()) {
                form.reportValidity();
                return;
            }
            updateShippingSummary();

            step1Content.style.display = 'none';
            step2Content.style.display = 'block';
            stepHeader1.classList.remove('active');
            stepHeader2.classList.add('active');
            backToCartBtn.style.display = 'none';
            backToStep1Btn.style.display = 'block';
            checkoutTitle.textContent = 'Thanh toán';
            window.scrollTo(0, 0);
        });
    }

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

    // --- TOGGLE ĐỊA CHỈ & REQUIRED ---
    function toggleAddressForm() {
        if (radioDefault && radioDefault.checked) {
            if(defaultBox) defaultBox.style.display = 'block';
            if(newBox) newBox.style.display = 'none';
            setRequired(false);
            // Trigger change để điền lại data mặc định
            if(addressSelector) addressSelector.dispatchEvent(new Event('change'));
        } else if (radioNew && radioNew.checked) {
            if(defaultBox) defaultBox.style.display = 'none';
            if(newBox) newBox.style.display = 'block';
            setRequired(true);
        }
    }

    function setRequired(isRequired) {
        if(nameNew) nameNew.required = isRequired;
        if(phoneNew) phoneNew.required = isRequired;
        if(addressNew) addressNew.required = isRequired;
    }

    if (radioDefault) radioDefault.addEventListener('change', toggleAddressForm);
    if (radioNew) radioNew.addEventListener('change', toggleAddressForm);

    // --- AUTO FILL TỪ SELECTOR ---
    if (addressSelector) {
        addressSelector.addEventListener('change', function() {
            const option = this.options[this.selectedIndex];
            if(option) {
                document.getElementById('name-default').value = option.getAttribute('data-name');
                document.getElementById('phone-default').value = option.getAttribute('data-phone');
                document.getElementById('address-default').value = option.getAttribute('data-address');
            }
        });
    }

    // --- CẬP NHẬT TÓM TẮT ---
    function updateShippingSummary() {
        let name, phone, address;
        const guestName = document.getElementById('name-guest');

        if (guestName) {
            name = guestName.value;
            phone = document.getElementById('phone-guest').value;
            address = document.getElementById('address-guest').value;
        } else {
            if (radioNew && radioNew.checked) {
                name = document.getElementById('name-new').value;
                phone = document.getElementById('phone-new').value;
                address = document.getElementById('address-new').value;
            } else {
                name = document.getElementById('name-default').value;
                phone = document.getElementById('phone-default').value;
                address = document.getElementById('address-default').value;
            }
        }

        document.getElementById('summary-name').textContent = name;
        document.getElementById('summary-phone').textContent = phone;
        document.getElementById('summary-address').textContent = address;
    }

    toggleAddressForm();
});