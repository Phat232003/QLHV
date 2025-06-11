package service;

import java.util.List;

import model.LopHoc;

public interface LopHocService {
	 public List<LopHoc> getList();
	    public int createOrUpdate(LopHoc lopHoc);
           public boolean delete(int id);
}
