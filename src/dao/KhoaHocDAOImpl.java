package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.KhoaHoc;

public class KhoaHocDAOImpl implements KhoaHocDAO{
    @Override
    public List<KhoaHoc> getList() {
        Connection cons = DBConnect.getConnection(); // Không cần ép kiểu
        String sql = "SELECT * FROM khoa_hoc";
        List<KhoaHoc> list = new ArrayList<>();
        try {
            PreparedStatement ps = cons.prepareStatement(sql);
            ResultSet rs = ps.executeQuery(); // ✅ Dùng ResultSet
            while (rs.next()) {
                KhoaHoc khoaHoc = new KhoaHoc();
                khoaHoc.setMa_khoa_hoc(rs.getInt("ma_khoa_hoc"));
                khoaHoc.setTen_khoa_hoc(rs.getString("ten_khoa_hoc")); // ✅ sửa getSring
                khoaHoc.setMo_ta(rs.getString("mo_ta"));
                khoaHoc.setNgay_bat_dau(rs.getDate("ngay_bat_dau"));
                khoaHoc.setNgay_ket_thuc(rs.getDate("ngay_ket_thuc"));
                khoaHoc.setTinh_trang(rs.getBoolean("tinh_trang"));
                list.add(khoaHoc);
            }
            rs.close();  // ✅ nên đóng ResultSet
            ps.close();
            cons.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
@Override
	public int createOrUpdate(KhoaHoc khoaHoc) {
		int result = 0;
	    try {
	        Connection cons = DBConnect.getConnection();
	        String sql;
	        PreparedStatement ps;

	        if (khoaHoc.getMa_khoa_hoc() == 0) {
	            // INSERT
	            sql = "INSERT INTO khoa_hoc (ten_khoa_hoc, mo_ta, ngay_bat_dau, ngay_ket_thuc, tinh_trang) VALUES (?, ?, ?, ?, ?)";
	            ps = cons.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
	            ps.setString(1, khoaHoc.getTen_khoa_hoc());
	            ps.setString(2, khoaHoc.getMo_ta());
	            ps.setDate(3, khoaHoc.getNgay_bat_dau());
	            ps.setDate(4, khoaHoc.getNgay_ket_thuc());
	            ps.setBoolean(5, khoaHoc.isTinh_trang());

	            ps.executeUpdate();
	            ResultSet rs = ps.getGeneratedKeys();
	            if (rs.next()) {
	                result = rs.getInt(1); // Trả về ID mới được tạo
	            }
	            rs.close();
	        } else {
	            // UPDATE
	            sql = "UPDATE khoa_hoc SET ten_khoa_hoc = ?, mo_ta = ?, ngay_bat_dau = ?, ngay_ket_thuc = ?, tinh_trang = ? WHERE ma_khoa_hoc = ?";
	            ps = cons.prepareStatement(sql);
	            ps.setString(1, khoaHoc.getTen_khoa_hoc());
	            ps.setString(2, khoaHoc.getMo_ta());
	            ps.setDate(3, khoaHoc.getNgay_bat_dau());
	            ps.setDate(4, khoaHoc.getNgay_ket_thuc());
	            ps.setBoolean(5, khoaHoc.isTinh_trang());
	            ps.setInt(6, khoaHoc.getMa_khoa_hoc());

	            int rowsAffected = ps.executeUpdate();
	            if (rowsAffected > 0) {
	                result = khoaHoc.getMa_khoa_hoc(); // Trả về ID nếu update thành công
	            }
	        }

	        ps.close();
	        cons.close();
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return result;
	}
}