<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/WEB-INF/layout/header.jsp" />
<link rel="stylesheet" href="<c:url value='/css/static-page.css'/>">

<main class="container info-page-layout">
    <aside class="info-sidebar">
        <div class="sidebar-group">
            <h4>Về chúng tôi</h4>
            <ul>
                <li class="${activeTab == 'gioi-thieu' ? 'active' : ''}"><a href="?tab=gioi-thieu">Giới thiệu về công ty</a></li>
                <li class="${activeTab == 'lien-he' ? 'active' : ''}"><a href="?tab=lien-he">Liên hệ hợp tác kinh doanh</a></li>
                <li class="${activeTab == 'cua-hang' ? 'active' : ''}"><a href="?tab=cua-hang">Danh sách cửa hàng</a></li>
                <li class="${activeTab == 'tuyen-dung' ? 'active' : ''}"><a href="?tab=tuyen-dung">Tuyển dụng mới nhất</a></li>
                <li class="${activeTab == 'huong-dan' ? 'active' : ''}"><a href="?tab=huong-dan">Hướng dẫn mua hàng Online</a></li>
                <li class="${activeTab == 'tra-gop' ? 'active' : ''}"><a href="?tab=tra-gop">Hướng dẫn mua hàng trả góp</a></li>
            </ul>
        </div>
        <div class="sidebar-group">
            <h4>Chính sách</h4>
            <ul>
                <li class="${activeTab == 'bao-hanh' ? 'active' : ''}"><a href="?tab=bao-hanh">Chính sách bảo hành</a></li>
                <li class="${activeTab == 'ban-hang' ? 'active' : ''}"><a href="?tab=ban-hang">Chính sách bán hàng</a></li>
                <li class="${activeTab == 'bao-mat' ? 'active' : ''}"><a href="?tab=bao-mat">Chính sách bảo mật</a></li>
                <li class="${activeTab == 'su-dung' ? 'active' : ''}"><a href="?tab=su-dung">Chính sách sử dụng</a></li>
                <li class="${activeTab == 'khieu-nai' ? 'active' : ''}"><a href="?tab=khieu-nai">Chính sách khiếu nại</a></li>
            </ul>
        </div>
    </aside>

    <section class="info-content static-content-wrapper">
        <c:choose>
            <%-- 1. GIỚI THIỆU --%>
            <c:when test="${activeTab == 'gioi-thieu'}">
                <h2>Giới thiệu về PhoneStore</h2>
                <p>Được thành lập từ năm 2015, <b>PhoneStore</b> khởi đầu là một cửa hàng nhỏ tại TP.HCM. Sau hơn 10 năm phát triển, chúng tôi tự hào trở thành hệ thống bán lẻ công nghệ hàng đầu Việt Nam.</p>
                <div class="highlight-box">
                    <p><b>Tầm nhìn:</b> Trở thành biểu tượng niềm tin số 1 Việt Nam về sản phẩm công nghệ.</p>
                    <p><b>Sứ mệnh:</b> Mang công nghệ đỉnh cao đến gần hơn với mọi người dân với mức giá cạnh tranh nhất.</p>
                </div>
                <h3>Giá trị cốt lõi</h3>
                <ul>
                    <li><b>Tận tâm:</b> Luôn đặt khách hàng làm trọng tâm trong mọi suy nghĩ và hành động.</li>
                    <li><b>Chính trực:</b> Cam kết sản phẩm 100% chính hãng, nguồn gốc rõ ràng.</li>
                    <li><b>Sáng tạo:</b> Không ngừng cải tiến quy trình để mang lại trải nghiệm mua sắm tốt nhất.</li>
                </ul>
            </c:when>

            <%-- 2. LIÊN HỆ --%>
            <c:when test="${activeTab == 'lien-he'}">
                <h2>Liên hệ hợp tác kinh doanh</h2>
                <p>PhoneStore luôn chào đón các đối tác là nhà cung cấp, đơn vị vận chuyển và đại lý trên toàn quốc để cùng hợp tác phát triển bền vững.</p>
                <div class="faq-item">
                    <div class="faq-question"><i class="fa-solid fa-handshake"></i> Hợp tác cung ứng hàng hóa</div>
                    <p>Chúng tôi ưu tiên các đơn vị cung cấp sản phẩm chính hãng Apple, Samsung, Xiaomi với chính sách giá ưu đãi.</p>
                    <p>Email: <b>partnership@phonestore.com.vn</b></p>
                </div>
                <div class="faq-item">
                    <div class="faq-question"><i class="fa-solid fa-bullhorn"></i> Hợp tác quảng cáo & Marketing</div>
                    <p>Các yêu cầu về quảng bá thương hiệu, KOLs review vui lòng liên hệ bộ phận truyền thông.</p>
                    <p>Hotline: <b>1800 6018 (Ext: 102)</b></p>
                </div>
            </c:when>

            <%-- 3. DANH SÁCH CỬA HÀNG --%>
            <c:when test="${activeTab == 'cua-hang'}">
                <h2>Hệ thống Showroom PhoneStore</h2>
                <p>Với hệ thống cửa hàng phủ rộng, quý khách có thể dễ dàng đến trải nghiệm sản phẩm trực tiếp.</p>
                <div class="faq-item">
                    <div class="faq-question">Khu vực TP. Hồ Chí Minh</div>
                    <ul>
                        <li><i class="fa-solid fa-location-dot"></i> 123 Cách Mạng Tháng 8, Quận 10</li>
                        <li><i class="fa-solid fa-location-dot"></i> 456 Quang Trung, Quận Gò Vấp</li>
                    </ul>
                </div>
                <div class="faq-item">
                    <div class="faq-question">Khu vực Hà Nội</div>
                    <ul>
                        <li><i class="fa-solid fa-location-dot"></i> 789 Cầu Giấy, Quận Cầu Giấy</li>
                        <li><i class="fa-solid fa-location-dot"></i> 12 Đại Cồ Việt, Quận Hai Bà Trưng</li>
                    </ul>
                </div>
                <p>Giờ mở cửa: <b>08:30 - 21:30</b> (Kể cả Chủ Nhật và ngày lễ)</p>
            </c:when>

            <%-- 4. TUYỂN DỤNG --%>
            <c:when test="${activeTab == 'tuyen-dung'}">
                <h2>Cơ hội nghề nghiệp</h2>
                <p>PhoneStore đang tìm kiếm những cộng sự nhiệt huyết. Tại đây, bạn không chỉ đi làm, bạn đang xây dựng sự nghiệp.</p>
                <div class="faq-item">
                    <div class="faq-question">1. Nhân viên tư vấn bán hàng (Số lượng: 10)</div>
                    <p>Mô tả: Tư vấn sản phẩm tại cửa hàng, chốt đơn hàng.</p>
                    <p>Thu nhập: 10.000.000đ - 20.000.000đ (Lương cứng + Hoa hồng).</p>
                </div>
                <div class="faq-item">
                    <div class="faq-question">2. Kỹ thuật viên sửa chữa (Số lượng: 05)</div>
                    <p>Mô tả: Tiếp nhận bảo hành, thay thế linh kiện điện thoại.</p>
                    <p>Yêu cầu: Có tay nghề tối thiểu 1 năm kinh nghiệm.</p>
                </div>
                <p>Gửi CV về: <b>tuyendung@phonestore.com.vn</b></p>
            </c:when>

            <%-- 5. HƯỚNG DẪN MUA ONLINE --%>
            <c:when test="${activeTab == 'huong-dan'}">
                <h2>Hướng dẫn mua hàng Online</h2>
                <p>Chỉ với vài thao tác đơn giản, bạn có thể đặt mua sản phẩm tại PhoneStore ngay tại nhà:</p>
                <div class="highlight-box">
                    <p><b>Bước 1:</b> Tìm kiếm và chọn sản phẩm ưng ý trên website.</p>
                    <p><b>Bước 2:</b> Bấm "Thêm vào giỏ hàng" hoặc "Mua ngay".</p>
                    <p><b>Bước 3:</b> Điền thông tin người nhận và địa chỉ giao hàng.</p>
                    <p><b>Bước 4:</b> Chọn phương thức thanh toán và hoàn tất đặt hàng.</p>
                </div>
                <p><i class="fa-solid fa-circle-info"></i> Sau khi đặt hàng thành công, nhân viên PhoneStore sẽ gọi điện xác nhận trong vòng 15 phút.</p>
            </c:when>

            <%-- 6. HƯỚNG DẪN TRẢ GÓP --%>
            <c:when test="${activeTab == 'tra-gop'}">
                <h2>Hướng dẫn mua hàng trả góp</h2>
                <p>PhoneStore hỗ trợ trả góp giúp bạn sở hữu sản phẩm chỉ với 0 đồng trả trước.</p>
                <h3>Cách 1: Qua Công ty tài chính</h3>
                <ul>
                    <li>Yêu cầu: CMND/CCCD từ 18 tuổi trở lên.</li>
                    <li>Duyệt hồ sơ nhanh trong 15 phút.</li>
                </ul>
                <h3>Cách 2: Qua Thẻ tín dụng (0% Lãi suất)</h3>
                <ul>
                    <li>Áp dụng cho hơn 20 ngân hàng liên kết.</li>
                    <li>Không cần trả trước, không cần xét duyệt hồ sơ.</li>
                </ul>
            </c:when>

            <%-- 7. CHÍNH SÁCH BẢO HÀNH --%>
            <c:when test="${activeTab == 'bao-hanh'}">
                <h2>Chính sách bảo hành</h2>
                <p>Chúng tôi cam kết thực hiện đúng quy định bảo hành của nhà sản xuất:</p>
                <div class="faq-item">
                    <div class="faq-question">Sản phẩm chính hãng mới</div>
                    <p>Bảo hành 12 tháng tại các trung tâm bảo hành ủy quyền (Apple, Samsung...).</p>
                </div>
                <div class="faq-item">
                    <div class="faq-question">Sản phẩm tại PhoneStore</div>
                    <p>Lỗi 1 đổi 1 trong 30 ngày đầu tiên nếu có lỗi từ nhà sản xuất.</p>
                </div>
                <p><i class="fa-solid fa-triangle-exclamation"></i> Lưu ý: Không bảo hành các sản phẩm rơi vỡ, vào nước.</p>
            </c:when>

            <%-- 8. CHÍNH SÁCH BÁN HÀNG --%>
            <c:when test="${activeTab == 'ban-hang'}">
                <h2>Chính sách bán hàng tại PhoneStore</h2>
                <p>Nhằm đảm bảo quyền lợi tối đa, quý khách cần lưu ý:</p>
                <h3>1. Kiểm tra hàng</h3>
                <p>Khách hàng được quyền kiểm tra ngoại quan và mở máy trước khi thanh toán.</p>
                <h3>2. Giá bán</h3>
                <p>Giá hiển thị là giá cuối cùng đã bao gồm thuế VAT.</p>
                <h3>3. Hóa đơn</h3>
                <p>Chúng tôi xuất hóa đơn điện tử cho 100% đơn hàng bán ra.</p>
            </c:when>

            <%-- 9. CHÍNH SÁCH BẢO MẬT --%>
            <c:when test="${activeTab == 'bao-mat'}">
                <h2>Chính sách bảo mật thông tin</h2>
                <p>Chúng tôi hiểu rằng sự riêng tư là cực kỳ quan trọng đối với khách hàng.</p>
                <ul>
                    <li>Thông tin cá nhân chỉ được dùng để liên hệ giao đơn hàng.</li>
                    <li>Mật khẩu người dùng được mã hóa bảo mật 2 lớp.</li>
                    <li>Không bán hoặc cung cấp thông tin cho bất kỳ bên thứ ba nào.</li>
                </ul>
            </c:when>

            <%-- 10. CHÍNH SÁCH SỬ DỤNG --%>
            <c:when test="${activeTab == 'su-dung'}">
                <h2>Chính sách sử dụng Website</h2>
                <p>Bằng việc sử dụng website PhoneStore, bạn đồng ý tuân thủ các quy định:</p>
                <ul>
                    <li>Không sử dụng phần mềm can thiệp vào hệ thống thanh toán và dữ liệu.</li>
                    <li>Không đăng tải các nội dung vi phạm pháp luật trong phần bình luận sản phẩm.</li>
                    <li>PhoneStore có quyền khóa tài khoản nếu phát hiện hành vi gian lận đơn hàng.</li>
                </ul>
            </c:when>

            <%-- 11. CHÍNH SÁCH KHIẾU NẠI --%>
            <c:when test="${activeTab == 'khieu-nai'}">
                <h2>Chính sách khiếu nại & Giải quyết tranh chấp</h2>
                <p>Chúng tôi luôn cầu thị và lắng nghe mọi phản hồi từ khách hàng.</p>
                <div class="highlight-box">
                    <p><b>Hotline tiếp nhận:</b> 1800 6306 (Nhánh 2)</p>
                    <p><b>Thời gian phản hồi:</b> Chậm nhất trong vòng 24h làm việc.</p>
                </div>
                <h3>Quy trình khiếu nại</h3>
                <p>1. Khách hàng gửi phản hồi qua Hotline hoặc Email.</p>
                <p>2. Bộ phận CSKH xác minh sự việc và liên hệ lại phương án giải quyết.</p>
                <p>3. Tiến hành đền bù hoặc đổi trả nếu khiếu nại là đúng sự thật.</p>
            </c:when>

            <c:otherwise>
                <script>window.location.href="?tab=gioi-thieu";</script>
            </c:otherwise>
        </c:choose>
    </section>
</main>

<jsp:include page="/WEB-INF/layout/footer.jsp" />