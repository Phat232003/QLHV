package dao;

import model.HocVien;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HocVienDAOImpl implements HocVienDAO {

    @Override
    public List<HocVien> getList() {
        Connection cons = DBConnect.getConnection();
        String sql = "SELECT * FROM hoc_vien";
        List<HocVien> list = new ArrayList<>();
        try {
            PreparedStatement ps = cons.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HocVien hocVien = new HocVien();
                hocVien.setMa_hoc_vien(rs.getInt("ma_hoc_vien"));
                hocVien.setHo_ten(rs.getString("ho_ten"));
                hocVien.setSo_dien_thoai(rs.getString("so_dien_thoai"));
                hocVien.setDia_chi(rs.getString("dia_chi"));
                hocVien.setNgay_sinh(rs.getDate("ngay_sinh"));
                hocVien.setGioi_tinh(rs.getBoolean("gioi_tinh"));
                hocVien.setTinh_trang(rs.getBoolean("tinh_trang"));
                list.add(hocVien);
            }
            ps.close();
            cons.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int createOrUpdate(HocVien hocVien) {
        int result = 0;
        try {
            Connection cons = DBConnect.getConnection();
            String sql;
            PreparedStatement ps;

            if (hocVien.getMa_hoc_vien() == 0) {
                // INSERT
                sql = "INSERT INTO hoc_vien(ho_ten, ngay_sinh, gioi_tinh, so_dien_thoai, dia_chi, tinh_trang) VALUES (?, ?, ?, ?, ?, ?)";
                ps = cons.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, hocVien.getHo_ten());
                ps.setDate(2, hocVien.getNgay_sinh());
                ps.setBoolean(3, hocVien.isGioi_tinh());
                ps.setString(4, hocVien.getSo_dien_thoai());
                ps.setString(5, hocVien.getDia_chi());
                ps.setBoolean(6, hocVien.isTinh_trang());
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    result = rs.getInt(1); // trả về ID mới
                }
                rs.close();
            } else {
                // UPDATE
                sql = "UPDATE hoc_vien SET ho_ten = ?, ngay_sinh = ?, gioi_tinh = ?, so_dien_thoai = ?, dia_chi = ?, tinh_trang = ? WHERE ma_hoc_vien = ?";
                ps = cons.prepareStatement(sql);
                ps.setString(1, hocVien.getHo_ten());
                ps.setDate(2, hocVien.getNgay_sinh());
                ps.setBoolean(3, hocVien.isGioi_tinh());
                ps.setString(4, hocVien.getSo_dien_thoai());
                ps.setString(5, hocVien.getDia_chi());
                ps.setBoolean(6, hocVien.isTinh_trang());
                ps.setInt(7, hocVien.getMa_hoc_vien());
                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0) {
                    result = hocVien.getMa_hoc_vien(); // trả về lại ID cũ nếu update thành công
                }
            }

            ps.close();
            cons.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }
    
    @Override
    public HocVien findById(int id) {
        HocVien hocVien = null;
        Connection cons = DBConnect.getConnection();
        String sql = "SELECT * FROM hoc_vien WHERE ma_hoc_vien = ?";
        try {
            PreparedStatement ps = cons.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                hocVien = new HocVien();
                hocVien.setMa_hoc_vien(rs.getInt("ma_hoc_vien"));
                hocVien.setHo_ten(rs.getString("ho_ten"));
                hocVien.setSo_dien_thoai(rs.getString("so_dien_thoai"));
                hocVien.setDia_chi(rs.getString("dia_chi"));
                hocVien.setNgay_sinh(rs.getDate("ngay_sinh"));
                hocVien.setGioi_tinh(rs.getBoolean("gioi_tinh"));
                hocVien.setTinh_trang(rs.getBoolean("tinh_trang"));
            }
            rs.close();
            ps.close();
            cons.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hocVien;
    }
      @Override
    public boolean delete(int id) {
        Connection cons = DBConnect.getConnection();
        String sql = "DELETE FROM hoc_vien WHERE ma_hoc_vien = ?";
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

    public static void main(String[] args) {
        HocVienDAO hocVienDAO = new HocVienDAOImpl();
        System.out.println(hocVienDAO.getList());
    }
}
