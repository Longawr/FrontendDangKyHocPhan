Hướng dẫn cài đặt app  
Tải và giải nén 2 file CourseRegistration.zip và api.zip

Cần Xampp, Java Development Kit, Inteliji để sử dụng app

B1: Bật Xampp lên. Nhấn start 2 mục Apache và My SQL,
 
 Sau đó nhấn vào Admin tại mục My SQL.
 
Tại web mới hiện lên, ta tạo database mới và đặt tên là schoolmanagement. (Ở đây chúng em đã tạo rồi nên không tạo nữa)
 
B2: Mở file api bằng Inteliji, sau đó ta theo đường dẫn src/main/java/resources sau đó:

- Tại file application.properties ta xóa 123456 đi tại dòng

 spring.datasource.password=123456

- Tương tự tại file hibernate.cfg.xml ta xóa 123456 tại 

  <property name="connection.password">123456</property>

Sau đó chạy code của file ApiApplication.java (nếu chạy lỗi thì ta chạy lại thêm lần nữa)

B3: Mở file CourseRegistration bằng Inteliji sau đó chạy code và màn hình đăng nhập hiện lên

B3: Đăng nhập vào (Acc: admin, Passwork: nimda)


