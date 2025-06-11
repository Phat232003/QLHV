package service;

import model.HocVien;
import java.util.List;

public interface HocVienService {
    
    public List<HocVien> getList();
    public int createOrUpdate(HocVien hocVien);
    public HocVien findById(int id) ;
    boolean delete(int id);
}