/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import bean.LopHocBean;
import bean.KhoaHocBean;
import java.util.List;

/**
 *
 * @author phath
 */
public interface ThongKeDAO {

    public List<LopHocBean> getListByLopHoc();

    public List<KhoaHocBean> getListByKhoaHoc();
    
}
