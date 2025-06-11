/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;


import dao.KhoaHocDAO;
import dao.KhoaHocDAOImpl;
import java.util.List;
import model.KhoaHoc;

/**
 *
 * @author phath
 */
public  class KhoaHocServiceImpl implements KhoaHocService{
    private KhoaHocDAO khoaHocDAO = null;
    
    public KhoaHocServiceImpl(){
        this.khoaHocDAO = new KhoaHocDAOImpl();
    }
    @Override
    public List<KhoaHoc> getList(){
        return khoaHocDAO.getList();
    }
	@Override
	public int createOrUpdate(KhoaHoc khoaHoc) {
		// TODO Auto-generated method stub
		return khoaHocDAO.createOrUpdate(khoaHoc);
	}
	   @Override
	    public KhoaHoc findById(int id) {
	        return khoaHocDAO.findById(id);
	    }

    @Override
    public boolean delete(int id) {
        
    return khoaHocDAO.delete(id);
            }

}