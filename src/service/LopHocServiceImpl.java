package service;

import java.util.List;

import dao.LopHocDAO;
import dao.LopHocDAOImpl;
import model.LopHoc;

public class LopHocServiceImpl implements LopHocService {
   private LopHocDAO lopHocDAO=null;
   public  LopHocServiceImpl() {
	// TODO Auto-generated constructor stub
	   
this.lopHocDAO=new LopHocDAOImpl();
	
}
	@Override
	public List<LopHoc> getList() {
		// TODO Auto-generated method stub
		return lopHocDAO.getList();
	}

	@Override
	public int createOrUpdate(LopHoc lopHoc) {
		// TODO Auto-generated method stub
		return lopHocDAO.createOrUpdate( lopHoc);
	}

    @Override
    public boolean delete(int id) {
        return lopHocDAO.delete(id);
    }

}
