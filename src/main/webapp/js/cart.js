document.addEventListener("DOMContentLoaded", function() {

    // --- LẤY CÁC PHẦN TỬ ---
    const selectAll = document.getElementById('selectAll');
    const itemCheckboxes = document.querySelectorAll('.item-checkbox');
    const subtotalDisplay = document.getElementById('subtotal-display');
    const totalDisplay = document.getElementById('total-display');
    const selectedCountDisplay = document.getElementById('selected-count-display');
    const btnCheckout = document.getElementById('btn-checkout-action');
    const cartForm = document.getElementById('cart-form');

    // --- HÀM FORMAT TIỀN TỆ VNĐ ---
    function formatCurrency(amount) {
        return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(amount).replace('₫', '') + ' ₫';
    }

    // --- HÀM CẬP NHẬT TỔNG TIỀN THEO CHECKBOX ---
    function updateCartSummary() {
        let total = 0;
        let count = 0;

        itemCheckboxes.forEach(cb => {
            if (cb.checked) {
                // Lấy giá trị tiền từ data-total (đã tính sẵn trong JSP)
                const lineTotal = parseFloat(cb.getAttribute('data-total'));
                if (!isNaN(lineTotal)) {
                    total += lineTotal;
                }
                count++;
            }
        });

        // Cập nhật lên giao diện
        const formattedTotal = formatCurrency(total);
        if(subtotalDisplay) subtotalDisplay.innerText = formattedTotal;
        if(totalDisplay) totalDisplay.innerText = formattedTotal;
        if(selectedCountDisplay) selectedCountDisplay.innerText = count + " sản phẩm";

        // Disable nút thanh toán nếu không chọn gì
        if (btnCheckout) {
            if (count === 0) {
                btnCheckout.disabled = true;
                btnCheckout.innerText = "Chưa chọn sản phẩm";
            } else {
                btnCheckout.disabled = false;
                btnCheckout.innerText = "Tiến hành thanh toán";
            }
        }
    }

    // --- SỰ KIỆN: CHỌN TẤT CẢ ---
    if (selectAll) {
        selectAll.addEventListener('change', function() {
            const isChecked = this.checked;
            itemCheckboxes.forEach(cb => {
                cb.checked = isChecked;
            });
            updateCartSummary();
        });
    }

    // --- SỰ KIỆN: CHỌN TỪNG ITEM ---
    itemCheckboxes.forEach(cb => {
        cb.addEventListener('change', function() {
            // Nếu bỏ chọn 1 cái -> bỏ chọn nút Select All
            if (!this.checked) {
                if(selectAll) selectAll.checked = false;
            }
            // Nếu chọn hết -> tick nút Select All
            const allChecked = Array.from(itemCheckboxes).every(c => c.checked);
            if (allChecked && selectAll) {
                selectAll.checked = true;
            }
            updateCartSummary();
        });
    });

    // --- SỰ KIỆN: CLICK NÚT THANH TOÁN ---
    if(btnCheckout && cartForm) {
        btnCheckout.addEventListener('click', function() {
            // Submit form để gửi các checkbox đã chọn lên Servlet
            cartForm.submit();
        });
    }

    // Chạy lần đầu khi load trang
    updateCartSummary();
});