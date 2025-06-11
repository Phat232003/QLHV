package controller;

import com.toedter.calendar.JDateChooser;
import model.KhoaHoc;
import service.KhoaHocService;
import service.KhoaHocServiceImpl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

public class KhoaHocController {

    private JButton btnSubmit;
    private JTextField jtfMaKhoaHoc;
    private JTextField jtfTenKhoaHoc;
    private JTextArea jtaMoTa;
    private JDateChooser jdcNgayBatDau;
    private JDateChooser jdcNgayKetThuc;
    private JCheckBox jcbTinhTrang;
    private JLabel jlbMsg;
    
    private KhoaHoc khoaHoc = null;
    private KhoaHocService khoaHocService = null;

    // ✅ Thêm callback
    private Runnable saveCallback;

    public KhoaHocController(JButton btnSubmit,
                             JTextField jtfMaKhoaHoc,
                             JTextField jtfTenKhoaHoc,
                             JTextArea jtaMoTa,
                             JDateChooser jdcNgayBatDau,
                             JDateChooser jdcNgayKetThuc,
                             JCheckBox jcbTinhTrang,
                             JLabel jlbMsg) {
        this.btnSubmit = btnSubmit;
        this.jtfMaKhoaHoc = jtfMaKhoaHoc;
        this.jtfTenKhoaHoc = jtfTenKhoaHoc;
        this.jtaMoTa = jtaMoTa;
        this.jdcNgayBatDau = jdcNgayBatDau;
        this.jdcNgayKetThuc = jdcNgayKetThuc;
        this.jcbTinhTrang = jcbTinhTrang;
        this.jlbMsg = jlbMsg;

        this.khoaHocService = new KhoaHocServiceImpl();

        this.btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jtfTenKhoaHoc.getText().trim().isEmpty()
                        || jdcNgayBatDau.getDate() == null
                        || jdcNgayKetThuc.getDate() == null) {
                    jlbMsg.setText("Vui lòng nhập đầy đủ dữ liệu bắt buộc (*)");
                    return;
                }

                try {
                    khoaHoc.setTen_khoa_hoc(jtfTenKhoaHoc.getText().trim());
                    khoaHoc.setMo_ta(jtaMoTa.getText().trim());
                    khoaHoc.setNgay_bat_dau(new Date(jdcNgayBatDau.getDate().getTime()));
                    khoaHoc.setNgay_ket_thuc(new Date(jdcNgayKetThuc.getDate().getTime()));
                    khoaHoc.setTinh_trang(jcbTinhTrang.isSelected());

                    int id = khoaHocService.createOrUpdate(khoaHoc);
                    if (id > 0) {
                        jtfMaKhoaHoc.setText(String.valueOf(id));
                        jlbMsg.setText("Lưu thành công!");

                        // ✅ Gọi callback nếu có
                        if (saveCallback != null) {
                            saveCallback.run();
                        }
                    } else {
                        jlbMsg.setText("Lưu thất bại.");
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    jlbMsg.setText("Lỗi khi lưu dữ liệu!");
                }
            }
        });
    }

    public void setView(KhoaHoc kh) {
        this.khoaHoc = kh;
        jtfMaKhoaHoc.setText(kh.getMa_khoa_hoc() != 0 ? String.valueOf(kh.getMa_khoa_hoc()) : "");
        jtfTenKhoaHoc.setText(kh.getTen_khoa_hoc());
        jtaMoTa.setText(kh.getMo_ta());
        jdcNgayBatDau.setDate(kh.getNgay_bat_dau());
        jdcNgayKetThuc.setDate(kh.getNgay_ket_thuc());
        jcbTinhTrang.setSelected(kh.isTinh_trang());
    }

    // ✅ Setter cho callback
    public void setSaveCallback(Runnable callback) {
        this.saveCallback = callback;
    }
}
