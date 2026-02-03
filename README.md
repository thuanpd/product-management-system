# product-management-system
product-management-system OOP

src/
 ├── com.project.model        # Chứa các lớp đối tượng (Entity)
 ├── com.project.service      # Chứa logic xử lý nghiệp vụ
 ├── com.project.view         # Chứa giao diện Java Swing (JFrame, JPanel)
 ├── com.project.repository   # Chứa code xử lý file/database
 └── com.project.main         # Lớp chứa hàm main để chạy ứng dụng

 1. Nhóm chức năng cốt lõi (CRUD)
  Đây là những chức năng bắt buộc phải có để ứng dụng hoạt động:
  Thêm sản phẩm mới: Nhập thông tin (Tên, Mã, Giá, Số lượng, Danh mục) và lưu vào danh sách.
  Hiển thị danh sách: Xuất toàn bộ sản phẩm lên JTable với giao diện ngăn nắp.
  Sửa thông tin: Chọn một sản phẩm từ bảng và cập nhật các thông số của nó.
  Xóa sản phẩm: Loại bỏ sản phẩm khỏi hệ thống (nên có hộp thoại JOptionPane để xác nhận "Bạn có chắc chắn muốn xóa không?").
2. Nhóm chức năng Tìm kiếm & Sắp xếp (OOP Logic)
Phần này giúp bạn ghi điểm nhờ khả năng xử lý thuật toán trên ArrayList hoặc Vector:
  Tìm kiếm:
  Tìm theo tên (chứa từ khóa).
  Tìm theo khoảng giá (ví dụ: từ 500k - 1 triệu).
  Lọc theo danh mục (ví dụ: chỉ hiện đồ điện tử).
  Sắp xếp:
  Sắp xếp theo giá tăng dần/giảm dần.
  Sắp xếp theo tên từ A-Z.
  Sắp xếp theo số lượng tồn kho.
