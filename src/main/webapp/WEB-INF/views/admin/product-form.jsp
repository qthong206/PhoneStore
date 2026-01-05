<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:set var="isEdit" value="${not empty product}" />

<div class="product-form-wrapper" style="max-width: 900px; margin: 0 auto; padding-bottom: 50px;">
    <div style="margin-bottom: 25px;">
        <h2 class="page-title" style="text-transform: none;">
            ${isEdit ? 'Chỉnh sửa sản phẩm' : 'Thêm sản phẩm mới'}
        </h2>
    </div>

    <div class="glass-panel">
        <form id="main-form" method="post" enctype="multipart/form-data"
              action="${pageContext.request.contextPath}${isEdit ? '/admin/product/update' : '/admin/product/insert'}">

            <c:if test="${isEdit}">
                <input type="hidden" name="id" value="${product.id}" />
                <input type="hidden" name="oldThumbnail" value="${product.thumbnailUrl}" />
            </c:if>

            <div class="form-group">
                <label>Tên sản phẩm <span style="color:var(--grad-danger)">*</span></label>
                <input type="text" name="name" value="${isEdit ? product.name : ''}" required />
            </div>

            <div class="form-row-multi">
                <div class="form-group">
                    <label>Loại sản phẩm</label>
                    <select name="categoryId" required>
                        <c:forEach var="c" items="${categories}">
                            <option value="${c.id}" ${isEdit && product.categoryId == c.id ? 'selected' : ''}>${c.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label>Thương hiệu</label>
                    <select name="brandId" required>
                        <c:forEach var="b" items="${brands}">
                            <option value="${b.id}" ${isEdit && product.brand.id == b.id ? 'selected' : ''}>${b.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label>Dòng sản phẩm</label>
                    <select name="seriesId">
                        <option value="0">-- Không --</option>
                        <c:forEach var="s" items="${series}">
                            <option value="${s.id}" ${isEdit && product.seriesId == s.id ? 'selected' : ''}>${s.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="form-row-multi">
                <div class="form-group">
                    <label>Giá gốc (₫)</label>
                    <input type="number" name="price" value="${isEdit ? product.price : ''}" required />
                </div>
                <div class="form-group">
                    <label>Giá KM (₫)</label>
                    <input type="number" name="salePrice" value="${isEdit ? product.salePrice : ''}" />
                </div>
                <div class="form-group">
                    <label>Số lượng tồn kho <span style="color:var(--grad-danger)">*</span></label>
                    <input type="number" name="stockQuantity" value="${isEdit ? product.stockQuantity : '0'}" required min="0" />
                </div>
            </div>

            <%-- PHẦN CHỌN ẢNH --%>
            <div class="form-group">
                <label>Hình ảnh sản phẩm (Ảnh đầu tiên là ảnh chính)</label>
                <div class="upload-container" id="drop-zone">
                    <i class="fa-solid fa-cloud-arrow-up" style="font-size: 2.5rem; color: var(--color-primary); margin-bottom: 10px; display: block;"></i>
                    <p>Kéo thả nhiều ảnh vào đây hoặc <span>chọn tệp</span></p>
                    <%-- Hidden input đúng cách --%>
                    <input type="file" id="file-input" name="imageFiles" multiple accept="image/*" style="display: none !important;" />
                </div>

                <div id="image-list" style="display: flex; flex-wrap: wrap; gap: 15px; margin-top: 20px;">
                    <%-- Preview ảnh sẽ hiện ở đây --%>
                </div>
            </div>

            <div class="form-row-multi row-2">
                <div class="form-group">
                    <label>Dung lượng / Model</label>
                    <input type="text" name="storage" value="${isEdit ? product.storage : ''}" />
                </div>
                <div class="form-group">
                    <label>Trạng thái</label>
                    <select name="status">
                        <option value="1" ${isEdit && product.status == 1 ? 'selected' : ''}>Hiện</option>
                        <option value="0" ${isEdit && product.status == 0 ? 'selected' : ''}>Ẩn</option>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label>Mô tả</label>
                <textarea name="description" rows="4">${isEdit ? product.description : ''}</textarea>
            </div>

            <div style="display: flex; gap: 10px; justify-content: center; margin-top: 30px;">
                <a class="btn btn-secondary" href="${pageContext.request.contextPath}/admin/product">Hủy</a>
                <button class="btn btn-save" type="button" onclick="submitForm()">
                    <i class="fa-solid fa-floppy-disk"></i> Lưu sản phẩm
                </button>
            </div>
        </form>
    </div>
</div>

<%-- CSS CỐ ĐỊNH KÍCH THƯỚC ẢNH PREVIEW --%>
<style>
    .img-item {
        position: relative; width: 120px; height: 120px;
        border-radius: 8px; border: 2px solid #eee; background: #fff;
        padding: 5px; cursor: grab; display: flex; align-items: center; justify-content: center;
    }
    .img-item img { max-width: 100%; max-height: 100%; object-fit: contain; }
    .img-item.main-img { border-color: var(--color-primary); box-shadow: 0 0 10px rgba(8,83,119,0.2); }
    .img-item.main-img::after {
        content: "Ảnh chính"; position: absolute; top: -10px; left: 50%;
        transform: translateX(-50%); background: var(--color-primary);
        color: white; font-size: 10px; padding: 2px 8px; border-radius: 10px; white-space: nowrap;
    }
    .btn-remove {
        position: absolute; top: -8px; right: -8px; background: #dc3545;
        color: white; border-radius: 50%; width: 22px; height: 22px;
        display: flex; align-items: center; justify-content: center;
        font-size: 12px; cursor: pointer; border: 2px solid #fff; z-index: 10;
    }
</style>

<script src="https://cdn.jsdelivr.net/npm/sortablejs@1.15.0/Sortable.min.js"></script>
<script>
    let selectedFiles = [];
    const dropZone = document.getElementById('drop-zone');
    const fileInput = document.getElementById('file-input');
    const imageList = document.getElementById('image-list');

    new Sortable(imageList, {
        animation: 150,
        onEnd: () => {
            updateFileOrderFromDOM();
            renderImageList();
        }
    });

    dropZone.onclick = () => fileInput.click();

    fileInput.onchange = function() {
        const files = Array.from(this.files);
        files.forEach(file => {
            if (!selectedFiles.some(f => f.name === file.name)) {
                selectedFiles.push(file);
            }
        });
        renderImageList();
        this.value = ''; // Xóa value để có thể chọn lại cùng 1 file
    };

    function renderImageList() {
        imageList.innerHTML = '';
        selectedFiles.forEach((file, index) => {
            const reader = new FileReader();
            reader.onload = (e) => {
                const div = document.createElement('div');
                div.className = `img-item \${index === 0 ? 'main-img' : ''}`;
                div.dataset.filename = file.name;
                div.innerHTML = `
                    <img src="\${e.target.result}">
                    <div class="btn-remove" onclick="removeImage('\${file.name}')">×</div>
                `;
                imageList.appendChild(div);
            };
            reader.readAsDataURL(file);
        });
    }

    function removeImage(filename) {
        selectedFiles = selectedFiles.filter(f => f.name !== filename);
        renderImageList();
    }

    function updateFileOrderFromDOM() {
        const items = Array.from(imageList.querySelectorAll('.img-item'));
        const newOrderedFiles = [];
        items.forEach(item => {
            const fname = item.dataset.filename;
            const file = selectedFiles.find(f => f.name === fname);
            if (file) newOrderedFiles.push(file);
        });
        selectedFiles = newOrderedFiles;
    }

    function submitForm() {
        const form = document.getElementById('main-form');
        const dataTransfer = new DataTransfer();
        updateFileOrderFromDOM();
        selectedFiles.forEach(file => {
            dataTransfer.items.add(file);
        });
        fileInput.files = dataTransfer.files;
        if (form.checkValidity()) {
            form.submit();
        } else {
            form.reportValidity();
        }
    }
</script>