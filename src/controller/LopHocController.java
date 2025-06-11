package controller;

import com.toedter.calendar.JDateChooser;
import model.LopHoc;
import service.LopHocService;
import service.LopHocServiceImpl;

import javax.swing.*;
import java.util.Date;

public class LopHocController {

    private JButton btnSubmit;
    private JTextField jtfMaLopHoc;
    private JTextField jtfMaKhoaHoc;
    private JTextField jtfMaHocVien;
    private JDateChooser jdcNgayDangKy;
    private JCheckBox jcbTinhTrang;
    private JLabel lblMsg;

    private LopHocService lopHocService;

    private Runnable saveCallback;

    public LopHocController(
            JButton btnSubmit,
            JTextField jtfMaLopHoc,
            JTextField jtfMaKhoaHoc,
            JTextField jtfMaHocVien,
            JDateChooser jdcNgayDangKy,
            JCheckBox jcbTinhTrang,
            JLabel lblMsg) {

        this.btnSubmit = btnSubmit;
        this.jtfMaLopHoc = jtfMaLopHoc;
        this.jtfMaKhoaHoc = jtfMaKhoaHoc;
        this.jtfMaHocVien = jtfMaHocVien;
        this.jdcNgayDangKy = jdcNgayDangKy;
        this.jcbTinhTrang = jcbTinhTrang;
        this.lblMsg = lblMsg;

        this.lopHocService = new LopHocServiceImpl();

        setEvent();
    }

    private void setEvent() {
        btnSubmit.addActionListener(e -> saveLopHoc());
    }

    public void setView(LopHoc lopHoc) {
        if (lopHoc == null) return;

        jtfMaLopHoc.setText(String.valueOf(lopHoc.getMa_lop_hoc()));
        jtfMaKhoaHoc.setText(String.valueOf(lopHoc.getMa_khoa_hoc()));
        jtfMaHocVien.setText(String.valueOf(lopHoc.getMa_hoc_vien()));

        Date ngayDangKy = lopHoc.getNgay_dang_ky();
        if (ngayDangKy != null) {
            jdcNgayDangKy.setDate(ngayDangKy);
        } else {
            jdcNgayDangKy.setDate(null);
        }

        jcbTinhTrang.setSelected(lopHoc.isTinh_trang());
    }

    public void setSaveCallback(Runnable callback) {
        this.saveCallback = callback;
    }

    private void saveLopHoc() {
        try {
            int maLopHoc = Integer.parseInt(jtfMaLopHoc.getText().trim());
            int maKhoaHoc = Integer.parseInt(jtfMaKhoaHoc.getText().trim());
            int maHocVien = Integer.parseInt(jtfMaHocVien.getText().trim());
            Date ngayDangKy = jdcNgayDangKy.getDate();
            boolean tinhTrang = jcbTinhTrang.isSelected();

            if (ngayDangKy == null) {
                lblMsg.setText("Vui lòng chọn ngày đăng ký");
                return;
            }

            LopHoc lopHoc = new LopHoc();
            lopHoc.setMa_lop_hoc(maLopHoc);
            lopHoc.setMa_khoa_hoc(maKhoaHoc);
            lopHoc.setMa_hoc_vien(maHocVien);
            lopHoc.setNgay_dang_ky(new java.sql.Date(ngayDangKy.getTime()));
            lopHoc.setTinh_trang(tinhTrang);

            int result = lopHocService.createOrUpdate(lopHoc);

            if (result > 0) {
                lblMsg.setText("Lưu thành công");
                if (saveCallback != null) {
                    saveCallback.run();
                }
            } else {
                lblMsg.setText("Lưu thất bại");
            }

        } catch (NumberFormatException ex) {
            lblMsg.setText("Mã lớp, khóa học, học viên phải là số");
        } catch (Exception ex) {
            lblMsg.setText("Lỗi: " + ex.getMessage());
        }
    }
}
