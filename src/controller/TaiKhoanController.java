package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import model.TaiKhoan;
import service.TaiKhoanService;
import service.TaiKhoanServiceImpl;
import view.MainJFrame;

public class TaiKhoanController {
    private JDialog dialog;
    private JButton btnSubmit;
    private JTextField jtfTenDangNhap;
    private JTextField jtfMatKhau;

    private TaiKhoanService taiKhoanService;

    public TaiKhoanController(JDialog dialog, JButton btnSubmit, JTextField jtfTenDangNhap, JTextField jtfMatKhau) {
        this.dialog = dialog;
        this.btnSubmit = btnSubmit;
        this.jtfTenDangNhap = jtfTenDangNhap;
        this.jtfMatKhau = jtfMatKhau;

        // Sửa lỗi ở đây: tạo đúng instance của service
        this.taiKhoanService = new TaiKhoanServiceImpl();
    }

    public void setEvent() {
        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jtfTenDangNhap.getText().trim().isEmpty() || jtfMatKhau.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ Tên đăng nhập và Mật khẩu");
                    return;
                }

                String tdn = jtfTenDangNhap.getText().trim();
                String mk = jtfMatKhau.getText().trim();

                TaiKhoan taiKhoan = taiKhoanService.login(tdn, mk);

                if (taiKhoan != null) {
                    dialog.dispose();
                    MainJFrame frame = new MainJFrame();
                    frame.setTitle("Quản Lý Học Viên"); // Sửa cú pháp lỗi
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Sai tên đăng nhập hoặc mật khẩu!");
                }
            }
        });

        // Hiệu ứng hover cho nút
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
