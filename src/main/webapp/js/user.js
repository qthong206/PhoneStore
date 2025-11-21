/**
 * Xử lý xóa sản phẩm khỏi Wishlist
 * @param {Event} event - Sự kiện click
 * @param {number} productId - ID sản phẩm cần xóa
 * @param {string} baseUrl - Đường dẫn gốc đến Servlet (để tránh lỗi context path)
 */
function removeFromWishlist(event, productId, baseUrl) {
    // Ngăn chặn sự kiện click lan ra thẻ <a> cha (tránh nhảy trang)
    event.preventDefault();
    event.stopPropagation();

    if (!confirm('Bạn có chắc muốn bỏ sản phẩm này khỏi danh sách yêu thích?')) {
        return;
    }

    // Gọi API xóa
    fetch(baseUrl + '?productId=' + productId, {
        method: 'POST'
    })
        .then(response => {
            if (response.ok) {
                // 1. Xóa thẻ HTML khỏi giao diện ngay lập tức
                const itemToRemove = document.getElementById('wishlist-item-' + productId);
                if (itemToRemove) {
                    itemToRemove.remove();

                    // 2. Kiểm tra nếu xóa hết thì reload để hiện "trạng thái trống"
                    const remainingItems = document.querySelectorAll('.wishlist-card');
                    if (remainingItems.length === 0) {
                        location.reload();
                    }
                }
            } else {
                alert('Có lỗi xảy ra, vui lòng thử lại sau.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Không thể kết nối đến server.');
        });
}