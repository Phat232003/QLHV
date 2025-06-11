package controller;

import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import model.HocVien;
import service.HocVienService;
import service.HocVienServiceImpl;

public class HocVienController {

    private JButton btnSubmit;
    private JTextField jtfMaHocVien;
    private JTextField jtfHoTen;
    private JDateChooser jdcNgaySinh;
    private JRadioButton jrdNam;
    private JRadioButton jrdNu;
    private JTextField jtfSoDienThoai;
    private JTextArea jtaDiaChi;
    private JCheckBox jcbTinhTrang;

    private HocVien hocVien = null;

    private HocVienService hocVienService = null;

    // Biến callback để gọi khi lưu thành công
    private Runnable onSaveSuccess;

    public HocVienController(JButton btnSubmit, JTextField jtfMaHocVien, JTextField jtfHoTen, JDateChooser jdcNgaySinh,
            JRadioButton jrdNam, JRadioButton jrdNu, JTextField jtfSoDienThoai, JTextArea jtaDiaChi, JCheckBox jcbTinhTrang) {

        this.btnSubmit = btnSubmit;
        this.jtfMaHocVien = jtfMaHocVien;
        this.jtfHoTen = jtfHoTen;
        this.jdcNgaySinh = jdcNgaySinh;
        this.jrdNam = jrdNam;
        this.jrdNu = jrdNu;
        this.jtfSoDienThoai = jtfSoDienThoai;
        this.jtaDiaChi = jtaDiaChi;
        this.jcbTinhTrang = jcbTinhTrang;

        this.hocVienService = new HocVienServiceImpl();
    }

    public void setView(HocVien hocVien) {
        this.hocVien = hocVien;
        jtfMaHocVien.setText("#" + hocVien.getMa_hoc_vien());
        jtfHoTen.setText(hocVien.getHo_ten());
        jdcNgaySinh.setDate(hocVien.getNgay_sinh());
        if (hocVien.isGioi_tinh()) {
            jrdNam.setSelected(true);
            jrdNu.setSelected(false);
        } else {
            jrdNam.setSelected(false);
            jrdNu.setSelected(true);
        }

        jtfSoDienThoai.setText(hocVien.getSo_dien_thoai());
        jtaDiaChi.setText(hocVien.getDia_chi());
        jcbTinhTrang.setSelected(hocVien.isTinh_trang());
    }

    // Set callback và đăng ký sự kiện nút lưu
    public void setEvent(Runnable onSaveSuccess) {
        this.onSaveSuccess = onSaveSuccess;

        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jtfHoTen.getText().trim().isEmpty() || jdcNgaySinh.getDate() == null) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ Họ tên và Ngày sinh.");
                    return;
                }

                try {
                    String maHVText = jtfMaHocVien.getText().replace("#", "").trim();
                    int maHV = maHVText.isEmpty() ? 0 : Integer.parseInt(maHVText);
                    hocVien.setMa_hoc_vien(maHV);

                    hocVien.setHo_ten(jtfHoTen.getText().trim());
                    hocVien.setNgay_sinh(new java.sql.Date(jdcNgaySinh.getDate().getTime()));
                    hocVien.setGioi_tinh(jrdNam.isSelected());
                    hocVien.setSo_dien_thoai(jtfSoDienThoai.getText().trim());
                    hocVien.setDia_chi(jtaDiaChi.getText().trim());
                    hocVien.setTinh_trang(jcbTinhTrang.isSelected());

                    int lastID = hocVienService.createOrUpdate(hocVien);
                    if (lastID > 0) {
                        hocVien.setMa_hoc_vien(lastID);
                        jtfMaHocVien.setText("#" + lastID);
                        JOptionPane.showMessageDialog(null, "Lưu thông tin thành công!");

                        // Gọi callback làm mới bảng ở đây
                        if (onSaveSuccess != null) {
                            onSaveSuccess.run();
                        }

                        // Đóng form
                        javax.swing.SwingUtilities.getWindowAncestor(btnSubmit).dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Lưu thông tin thất bại!");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi khi lưu thông tin!");
                }
            }
        });

        // Tăng hiệu ứng nút khi rê chuột
        btnSubmit.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSubmit.setBackground(new Color(0, 200, 83));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSubmit.setBackground(new Color(100, 221, 23));
            }
        });
    }
}
