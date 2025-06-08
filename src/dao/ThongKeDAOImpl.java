package dao;

import bean.KhoaHocBean;
import bean.LopHocBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ThongKeDAOImpl implements ThongKeDAO {

    public List<LopHocBean> getListByLopHoc() {
        List<LopHocBean> list = new ArrayList<>();
        String sql = "SELECT ngay_dang_ky, COUNT(*) AS so_luong FROM lop_hoc GROUP BY ngay_dang_ky";
        try (Connection cons = DBConnect.getConnection();
             PreparedStatement ps = cons.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                LopHocBean lopHocBean = new LopHocBean();
                lopHocBean.setNgay_dang_ky(rs.getString("ngay_dang_ky"));
                lopHocBean.setSo_luong_hoc_vien(rs.getInt("so_luong"));
                list.add(lopHocBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<KhoaHocBean> getListByKhoaHoc() {
        List<KhoaHocBean> list = new ArrayList<>();
        String sql = "SELECT ten_khoa_hoc, ngay_bat_dau, ngay_ket_thuc FROM khoa_hoc WHERE tinh_trang = TRUE ORDER BY ngay_bat_dau ASC";
        try (Connection cons = DBConnect.getConnection();
             PreparedStatement ps = cons.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                KhoaHocBean khoaHocBean = new KhoaHocBean();
                khoaHocBean.setTen_khoa_hoc(rs.getString("ten_khoa_hoc"));
                khoaHocBean.setNgay_bat_dau(rs.getDate("ngay_bat_dau"));
                khoaHocBean.setNgay_ket_thuc(rs.getDate("ngay_ket_thuc"));
                list.add(khoaHocBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
