package dao;

import model.LopHoc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LopHocDAOImpl implements LopHocDAO {

    @Override
    public List<LopHoc> getList() {
        Connection cons = DBConnect.getConnection();
        String sql = "SELECT * FROM lop_hoc";
        List<LopHoc> list = new ArrayList<>();
        try {
            PreparedStatement ps = cons.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LopHoc lopHoc = new LopHoc();
                lopHoc.setMa_lop_hoc(rs.getInt("ma_lop_hoc"));
                lopHoc.setMa_khoa_hoc(rs.getInt("ma_khoa_hoc"));
                lopHoc.setMa_hoc_vien(rs.getInt("ma_hoc_vien"));
                lopHoc.setNgay_dang_ky(rs.getDate("ngay_dang_ky"));
                lopHoc.setTinh_trang(rs.getBoolean("tinh_trang"));
                list.add(lopHoc);
            }
            rs.close();
            ps.close();
            cons.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int createOrUpdate(LopHoc lopHoc) {
        try {
            Connection cons = DBConnect.getConnection();
            String sql = "INSERT INTO lop_hoc(ma_lop_hoc, ma_khoa_hoc, ma_hoc_vien, ngay_dang_ky, tinh_trang) " +
                         "VALUES (?, ?, ?, ?, ?) " +
                         "ON DUPLICATE KEY UPDATE ma_khoa_hoc = VALUES(ma_khoa_hoc), ma_hoc_vien = VALUES(ma_hoc_vien), " +
                         "ngay_dang_ky = VALUES(ngay_dang_ky), tinh_trang = VALUES(tinh_trang)";
            PreparedStatement ps = cons.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, lopHoc.getMa_lop_hoc());
            ps.setInt(2, lopHoc.getMa_khoa_hoc());
            ps.setInt(3, lopHoc.getMa_hoc_vien());
            ps.setDate(4, lopHoc.getNgay_dang_ky());
            ps.setBoolean(5, lopHoc.isTinh_trang());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            int generatedKey = 0;
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }
            rs.close();
            ps.close();
            cons.close();
            return generatedKey;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }
     @Override
    public boolean delete(int id) {
        Connection cons = DBConnect.getConnection();
        String sql = "DELETE FROM lop_hoc WHERE ma_lop_hoc = ?";
        try {
            PreparedStatement ps = cons.prepareStatement(sql);
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            
            ps.close();
            cons.close();
            
            return rowsAffected > 0;  // Return true if at least one row was affected (i.e., deletion succeeded)
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;  // Return false if the deletion failed
    }

    
}
