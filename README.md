# 📦 Product Management System (Java Swing & CSV)

## 📝 Giới thiệu
Đây là dự án phần mềm quản lý hàng hóa tập trung vào việc tối ưu hóa quy trình lưu trữ và truy xuất thông tin sản phẩm cho các cửa hàng bán lẻ. Hệ thống được xây dựng trên nền tảng **Java Swing**, sử dụng kiến trúc **MVC + Service** và lưu trữ dữ liệu bền vững qua các tệp tin **CSV**.

Điểm mạnh của dự án là khả năng quản lý sản phẩm theo mô hình phân cấp: **Sản phẩm (Master) -> Biến thể (SKU) -> Lịch sử giá (Price History)**.

## ✨ Tính năng nổi bật
* **Quản lý đa phân loại:** Hỗ trợ một sản phẩm có nhiều biến thể (màu sắc, kích thước, chất liệu...).
* **Theo dõi giá chuyên sâu:** Tách biệt giá bán và giá gốc, hỗ trợ quản lý theo thời gian (`startDate`, `endDate`).
* **Kiểm soát dữ liệu chặt chẽ:** * Sử dụng `BigInteger` cho ID để không giới hạn quy mô.
    * Sử dụng `BigDecimal` cho tiền tệ để đảm bảo độ chính xác tuyệt đối.
    * Tự động ghi dấu thời gian `createdAt` và `updatedAt`.
* **Giao diện Master-Detail:** Chuyển đổi mượt mà giữa danh mục tổng quát và chi tiết SKU thông qua `JTabbedPane`.

## 🏗 Kiến trúc hệ thống (MVC + Service)
Dự án tuân thủ nghiêm ngặt mô hình tách biệt các tầng xử lý:
* **Model:** Định nghĩa thực thể và ràng buộc dữ liệu (`Product`, `ProductSku`, `ProductSkuPrice`).
* **View:** Thành phần giao diện người dùng bằng Java Swing.
* **Controller:** Điều phối luồng hoạt động giữa giao diện và logic.
* **Service:** Lớp xử lý nghiệp vụ chính và thao tác trực tiếp với các file CSV trong thư mục `data/`.

## 📂 Cấu trúc thư mục
```text
product-management-system/
├── data/               # Cơ sở dữ liệu CSV
│   ├── products.csv    # Thông tin sản phẩm cha
│   ├── skus.csv        # Chi tiết các biến thể SKU
│   └── sku_prices.csv  # Lịch sử biến động giá
├── src/
│   ├── controller/     # Lớp điều phối sự kiện
│   ├── model/          # Các lớp thực thể (Entity)
│   ├── service/        # Xử lý nghiệp vụ & File I/O
│   ├── view/           # Giao diện JFrame, JPanel
│   └── Main.java       # Điểm khởi chạy (Entry Point)
└── README.md
```

## 🛠 Công nghệ sử dụng
* **Ngôn ngữ:** Java 17+
* **Thư viện:** Java Swing, AWT.
* **Lưu trữ:** Flat-file CSV (UTF-8).
* **Thời gian:** `java.time.LocalDateTime` (ISO-8601).

## 🚀 Hướng dẫn cài đặt & Chạy
1.  Đảm bảo máy tính đã cài đặt **JDK 17** hoặc mới hơn.
2.  Tải mã nguồn dự án về máy.
3.  Mở dự án bằng IDE (IntelliJ IDEA, NetBeans, hoặc Eclipse).
4.  Chạy file `src/Main.java`.
5.  Dữ liệu sẽ được tự động load từ thư mục `data/`.

---
**Phát triển bởi:** ThuanPD  
**Ngày hoàn thiện:** 20/03/2026
