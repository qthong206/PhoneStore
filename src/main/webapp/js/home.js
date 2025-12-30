document.addEventListener('DOMContentLoaded', function() {

    const wishlistButtons = document.querySelectorAll('.btn-icon-heart');
    const loginModal = document.getElementById('review-login-modal');
    const closeModalBtn = document.getElementById('modal-close-login-btn');
    const contextPathInput = document.getElementById('contextPathHolder');
    const contextPath = contextPathInput ? contextPathInput.value : '';

    if (closeModalBtn) {
        closeModalBtn.addEventListener('click', function() {
            loginModal.style.display = 'none';
        });
    }

    wishlistButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            e.preventDefault();

            const productId = this.dataset.productId;

            if (typeof isUserLoggedIn !== 'undefined' && !isUserLoggedIn) {
                if (loginModal) {
                    loginModal.style.display = 'flex';
                } else {
                    window.location.href = contextPath + '/login';
                }
            } else {
                toggleWishlist(productId, button);
            }
        });
    });

    function toggleWishlist(productId, button) {
        const isActive = button.classList.contains('active');
        const action = isActive ? 'remove' : 'add';

        fetch(`${contextPath}/wishlist`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: `action=${action}&productId=${productId}`
        })
            .then(response => {
                if (response.status === 401) {
                    if (loginModal) loginModal.style.display = 'flex';
                    else window.location.href = contextPath + '/login';
                    return null;
                }
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                if (!data) return;

                if (data.success) {
                    button.classList.toggle('active');
                } else {
                    console.error(data.message);
                }
            })
            .catch(error => console.error('Lỗi AJAX:', error));
    }

    if (loginModal) {
        loginModal.addEventListener('click', function(e) {
            if (e.target === loginModal) {
                loginModal.style.display = 'none';
            }
        });
    }
});