package dao;

import java.util.List;

import model.LopHoc;

public interface LopHocDAO {
	  public List<LopHoc> getList();
	    public int createOrUpdate(LopHoc lopHoc);
        public   boolean delete(int id);
}
