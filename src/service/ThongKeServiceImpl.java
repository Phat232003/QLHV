/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import bean.KhoaHocBean;
import bean.LopHocBean;
import dao.ThongKeDAO;
import dao.ThongKeDAOImpl;
import java.util.List;

/**
 *
 * @author phath
 */
public class ThongKeServiceImpl implements ThongKeService{
    private ThongKeDAO thongKeDAO = null;
    
    public ThongKeServiceImpl(){
        thongKeDAO = new ThongKeDAOImpl();
    }
    @Override
    public List<LopHocBean> getListByLopHoc(){
        return thongKeDAO.getListByLopHoc();
    }

    @Override
    public List<KhoaHocBean> getListByKhoaHoc() {
        return thongKeDAO.getListByKhoaHoc();
    }
    
}
