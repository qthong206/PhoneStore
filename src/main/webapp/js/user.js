
/**
 * Xử lý xóa sản phẩm khỏi Wishlist
 */
function removeFromWishlist(event, productId, baseUrl) {
    // 1. Ngăn chặn hành vi mặc định
    event.preventDefault();
    event.stopPropagation();

    // 2. Xác nhận
    if (!confirm('Bạn có chắc muốn bỏ sản phẩm này khỏi danh sách yêu thích?')) {
        return;
    }

    // 3. Gọi API xóa (Sửa lại cách gửi dữ liệu chuẩn POST)
    fetch(baseUrl, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        // Gửi action và productId trong body
        body: `action=remove&productId=${productId}`
    })
        .then(response => {
            if (response.ok) {
                return response.json(); // Đọc JSON từ Servlet trả về
            }
            throw new Error('Network response was not ok');
        })
        .then(data => {
            // Kiểm tra logic thành công từ server
            if (data.success) {
                // 4. Tìm và xóa thẻ HTML
                const itemToRemove = document.getElementById('wishlist-item-' + productId);
                if (itemToRemove) {
                    // Hiệu ứng mờ dần
                    itemToRemove.style.opacity = '0';
                    itemToRemove.style.transition = 'opacity 0.3s';

                    setTimeout(() => {
                        itemToRemove.remove();

                        // 5. Kiểm tra nếu xóa hết thì reload
                        const remainingItems = document.querySelectorAll('.wishlist-card');
                        if (remainingItems.length === 0) {
                            location.reload();
                        }
                    }, 300);
                }
            } else {
                alert(data.message || 'Có lỗi xảy ra, vui lòng thử lại.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Không thể kết nối đến server.');
        });
}