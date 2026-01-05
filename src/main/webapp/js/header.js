document.addEventListener('DOMContentLoaded', function () {

    /* --- LOGIC MENU DANH MỤC --- */
    const dropdown = document.querySelector('.category-dropdown');
    const button = document.querySelector('.category-button');

    if (dropdown && button) {
        button.addEventListener('click', function (event) {
            event.preventDefault();
            dropdown.classList.toggle('menu-open');
        });

        document.addEventListener('click', function (event) {
            if (!dropdown.contains(event.target) && dropdown.classList.contains('menu-open')) {
                dropdown.classList.remove('menu-open');
            }
        });
    }

    /* --- [NEW] LOGIC TÌM KIẾM GỢI Ý (LIVE SEARCH) --- */
    const searchInput = document.getElementById('searchInput');
    const suggestionBox = document.getElementById('search-suggestions-box');

    // Lấy contextPath từ thẻ body đã gán ở header.jsp
    const contextPath = document.body.getAttribute('data-context-path') || '';

    let timeout = null;
    const formatter = new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' });

    if (searchInput && suggestionBox) {
        searchInput.addEventListener('input', function() {
            const keyword = this.value.trim();
            clearTimeout(timeout);

            if (keyword.length < 2) {
                suggestionBox.style.display = 'none';
                return;
            }

            timeout = setTimeout(() => {
                fetchSuggestion(keyword);
            }, 300);
        });

        // Ẩn khi click ra ngoài
        document.addEventListener('click', function(e) {
            if (!searchInput.contains(e.target) && !suggestionBox.contains(e.target)) {
                suggestionBox.style.display = 'none';
            }
        });

        // Hiện lại khi focus
        searchInput.addEventListener('focus', function() {
            if (this.value.trim().length >= 2 && suggestionBox.innerHTML.trim() !== "") {
                suggestionBox.style.display = 'block';
            }
        });
    }

    function fetchSuggestion(keyword) {
        // Gọi đến Servlet /api/search-suggestion
        const apiUrl = `${contextPath}/api/search-suggestion?q=${encodeURIComponent(keyword)}`;

        fetch(apiUrl)
            .then(response => {
                if (!response.ok) throw new Error("Lỗi kết nối");
                return response.json();
            })
            .then(data => {
                if ((data.keywords && data.keywords.length > 0) || (data.products && data.products.length > 0)) {
                    renderSuggestions(data);
                    suggestionBox.style.display = 'block';
                } else {
                    suggestionBox.style.display = 'none';
                }
            })
            .catch(err => console.error("Lỗi tìm kiếm:", err));
    }

    function renderSuggestions(data) {
        let html = '';

        // 1. Gợi ý từ khóa
        if (data.keywords && data.keywords.length > 0) {
            html += `<div class="suggestion-header">Có phải bạn muốn tìm</div>`;
            html += `<div class="suggestion-keywords"><ul>`;
            data.keywords.forEach(key => {
                html += `<li><a href="${contextPath}/products?search=${encodeURIComponent(key)}">${key}</a></li>`;
            });
            html += `</ul></div>`;
        }

        // 2. Gợi ý sản phẩm
        if (data.products && data.products.length > 0) {
            html += `<div class="suggestion-header">Sản phẩm gợi ý</div>`;
            data.products.forEach(p => {
                const detailLink = `${contextPath}/product-detail?id=${p.id}`;
                const imgUrl = `${contextPath}/${p.thumbnailUrl}`;

                let priceHtml = '';
                if (p.salePrice > 0) {
                    priceHtml = `<span class="sug-price-sale">${formatter.format(p.salePrice)}</span>
                                  <span class="sug-price-original">${formatter.format(p.price)}</span>`;
                } else {
                    priceHtml = `<span class="sug-price-sale">${formatter.format(p.price)}</span>`;
                }

                html += `
                    <a href="${detailLink}" class="suggestion-product-item">
                        <img src="${imgUrl}" alt="${p.name}" class="sug-prod-img">
                        <div class="sug-prod-info">
                            <h4>${p.name}</h4>
                            <div class="sug-price-box">${priceHtml}</div>
                        </div>
                    </a>
                `;
            });
        }
        suggestionBox.innerHTML = html;
    }
});

/* --- LOGIC MODAL LOGIN --- */
function showLoginModal(event) {
    if (event) event.preventDefault();
    const modal = document.getElementById('review-login-modal');
    if (modal) {
        modal.style.display = 'flex';
    }
}

document.addEventListener('DOMContentLoaded', function() {
    const modal = document.getElementById('review-login-modal');
    const closeBtn = document.getElementById('modal-close-login-btn');
    if (modal && closeBtn) {
        closeBtn.addEventListener('click', function() { modal.style.display = 'none'; });
        window.addEventListener('click', function(event) {
            if (event.target === modal) modal.style.display = 'none';
        });
    }
});