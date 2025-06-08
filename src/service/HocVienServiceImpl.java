package service;

import dao.HocVienDAO;
import dao.HocVienDAOImpl;
import model.HocVien;
import java.util.List;

public class HocVienServiceImpl implements HocVienService {

    private HocVienDAO hocVienDAO = null;

    public HocVienServiceImpl() {
        this.hocVienDAO = new HocVienDAOImpl();
    }

    @Override
    public List<HocVien> getList() {
        return hocVienDAO.getList();
    }

	@Override
	public int createOrUpdate(HocVien hocVien) {
		return hocVienDAO.createOrUpdate(hocVien);
	}
	@Override
	public HocVien findById(int id) {
	    return hocVienDAO.findById(id);
	}

	@Override
	public int demHocVien() {
		// TODO Auto-generated method stub
		return hocVienDAO.demHocVien();
	}


}