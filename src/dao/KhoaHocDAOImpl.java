package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
}
