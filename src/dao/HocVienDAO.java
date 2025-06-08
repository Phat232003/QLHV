package dao;

import model.HocVien;
import java.util.List;

public interface HocVienDAO {

    public List<HocVien> getList();
    public int createOrUpdate(HocVien hocVien);
     public HocVien findById(int id);
     public int demHocVien();

}