package service;

import model.TaiKhoan;
import dao.TaiKhoanDAO;
import dao.TaiKhoanDAOImpl;

public class TaiKhoanServiceImpl implements TaiKhoanService {

    private TaiKhoanDAO taiKhoanDAO;

    public TaiKhoanServiceImpl() {
        taiKhoanDAO = new TaiKhoanDAOImpl();
    }

    @Override
    public TaiKhoan login(String tdn, String mk) {
        return taiKhoanDAO.login(tdn, mk);
    }
}
