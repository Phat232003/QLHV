package dao;

import model.HocVien;
import java.util.List;

public interface HocVienDAO {
    List<HocVien> getList();
    int createOrUpdate(HocVien hocVien);
    HocVien findById(int id);
    boolean delete(int id);
    // Nếu có thêm các phương thức khác thì cũng khai báo ở đây
}
