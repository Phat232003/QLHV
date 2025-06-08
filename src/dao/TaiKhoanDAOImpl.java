package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.TaiKhoan;

public class TaiKhoanDAOImpl implements TaiKhoanDAO {

    @Override
    public TaiKhoan login(String tdn, String mk) {
        TaiKhoan taiKhoan = null;
        try {
            Connection cons = DBConnect.getConnection();
            String sql = "SELECT * FROM tai_khoan WHERE ten_dang_nhap = ? AND mat_khau = ?";
            PreparedStatement ps = cons.prepareStatement(sql);
            ps.setString(1, tdn);
            ps.setString(2, mk);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                taiKhoan = new TaiKhoan();
                taiKhoan.setMa_tai_khoan(rs.getInt("ma_tai_khoan"));
                taiKhoan.setTen_dang_nhap(rs.getString("ten_dang_nhap"));
                taiKhoan.setMat_khau(rs.getString("mat_khau"));
            }
            rs.close();
            ps.close();
            cons.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return taiKhoan;
    }
}
