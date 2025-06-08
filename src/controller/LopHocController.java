package controller;

import com.toedter.calendar.JDateChooser;
import model.LopHoc;
import service.LopHocService;
import service.LopHocServiceImpl;
import service.HocVienService;
import service.HocVienServiceImpl;
import service.KhoaHocService;
import service.KhoaHocServiceImpl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

public class LopHocController {

    private JButton btnSubmit;
    private JTextField jtfMaLopHoc;
    private JTextField jtfMaKhoaHoc;
    private JTextField jtfMaHocVien;
    private JDateChooser jdcNgayDangKy;
    private JCheckBox jcbTinhTrang;
    private JLabel jlbMsg;

    private LopHoc lopHoc = null;
    private LopHocService lopHocService = new LopHocServiceImpl();
    private HocVienService hocVienService = new HocVienServiceImpl();
    private KhoaHocService khoaHocService = new KhoaHocServiceImpl();

    public LopHocController(JButton btnSubmit,
                            JTextField jtfMaLopHoc,
                            JTextField jtfMaKhoaHoc,
                            JTextField jtfMaHocVien,
                            JDateChooser jdcNgayDangKy,
                            JCheckBox jcbTinhTrang,
                            JLabel jlbMsg) {
        this.btnSubmit = btnSubmit;
        this.jtfMaLopHoc = jtfMaLopHoc;
        this.jtfMaKhoaHoc = jtfMaKhoaHoc;
        this.jtfMaHocVien = jtfMaHocVien;
        this.jdcNgayDangKy = jdcNgayDangKy;
        this.jcbTinhTrang = jcbTinhTrang;
        this.jlbMsg = jlbMsg;

        this.btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jtfMaHocVien.getText().trim().isEmpty() ||
                    jtfMaKhoaHoc.getText().trim().isEmpty() ||
                    jdcNgayDangKy.getDate() == null) {
                    jlbMsg.setText("Vui lòng nhập đầy đủ dữ liệu bắt buộc (*)");
                    return;
                }

                try {
                    int maHocVien = Integer.parseInt(jtfMaHocVien.getText().trim());
                    int maKhoaHoc = Integer.parseInt(jtfMaKhoaHoc.getText().trim());

                    // Kiểm tra mã học viên
                    if (hocVienService.findById(maHocVien) == null) {
                        jlbMsg.setText("Học viên không tồn tại.");
                        return;
                    }

                    // Kiểm tra mã khóa học
                    if (khoaHocService.findById(maKhoaHoc) == null) {
                        jlbMsg.setText("Khóa học không tồn tại.");
                        return;
                    }

                    if (lopHoc == null) {
                        lopHoc = new LopHoc();
                    }

                    lopHoc.setMa_lop_hoc(jtfMaLopHoc.getText().trim().isEmpty() ? 0 :
                            Integer.parseInt(jtfMaLopHoc.getText().trim()));
                    lopHoc.setMa_hoc_vien(maHocVien);
                    lopHoc.setMa_khoa_hoc(maKhoaHoc);
                    lopHoc.setNgay_dang_ky(new Date(jdcNgayDangKy.getDate().getTime()));
                    lopHoc.setTinh_trang(jcbTinhTrang.isSelected());

                    int id = lopHocService.createOrUpdate(lopHoc);
                    if (id > 0) {
                        jtfMaLopHoc.setText(String.valueOf(id));
                        jlbMsg.setText("Lưu thành công!");
                    } else {
                        jlbMsg.setText("Lưu thất bại.");
                    }

                } catch (NumberFormatException nfe) {
                    jlbMsg.setText("Mã học viên và mã khóa học phải là số.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    jlbMsg.setText("Đã xảy ra lỗi khi lưu dữ liệu.");
                }
            }
        });
    }

    public void setView(LopHoc lh) {
        this.lopHoc = lh;
        jtfMaLopHoc.setText(lh.getMa_lop_hoc() != 0 ? String.valueOf(lh.getMa_lop_hoc()) : "");
        jtfMaKhoaHoc.setText(String.valueOf(lh.getMa_khoa_hoc()));
        jtfMaHocVien.setText(String.valueOf(lh.getMa_hoc_vien()));
        jdcNgayDangKy.setDate(lh.getNgay_dang_ky());
        jcbTinhTrang.setSelected(lh.isTinh_trang());
    }
}
