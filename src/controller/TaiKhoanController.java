package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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

        // Tạo instance của service
        this.taiKhoanService = new TaiKhoanServiceImpl();
    }

    public void setEvent() {
        // Thêm ActionListener cho nút Submit
        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();  // Gọi phương thức xử lý đăng nhập
            }
        });

        // Thêm KeyListener cho ô mật khẩu để xử lý khi người dùng nhấn Enter
        jtfMatKhau.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleLogin();  // Gọi phương thức xử lý đăng nhập khi nhấn Enter
//dialog.dispose();  // Đóng cửa sổ đăng nhập
//            // Mở cửa sổ chính
//            MainJFrame frame = new MainJFrame();
//            frame.setTitle("Quản Lý Học Viên");
//            frame.setLocationRelativeTo(null);  // Đặt vị trí cửa sổ
//            frame.setVisible(true);
                }
            }
        });

        // Thêm hiệu ứng hover cho nút Submit
        btnSubmit.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSubmit.setBackground(new Color(0, 200, 83));  // Màu khi hover
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSubmit.setBackground(new Color(100, 221, 23));  // Màu khi không hover
            }
        });
    }

    private void handleLogin() {
        String tdn = jtfTenDangNhap.getText().trim();
        String mk = jtfMatKhau.getText().trim();

        // Kiểm tra nếu người dùng chưa nhập tên đăng nhập hoặc mật khẩu
        if (tdn.isEmpty() || mk.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ Tên đăng nhập và Mật khẩu");
            return;
        }

        // Gọi phương thức login từ service
        TaiKhoan taiKhoan = taiKhoanService.login(tdn, mk);

        // Kiểm tra kết quả đăng nhập
        if (taiKhoan != null) {
            dialog.dispose();  // Đóng cửa sổ đăng nhập
            // Mở cửa sổ chính
            MainJFrame frame = new MainJFrame();
            frame.setTitle("Quản Lý Học Viên");
            frame.setLocationRelativeTo(null);  // Đặt vị trí cửa sổ
            frame.setVisible(true);  // Hiển thị cửa sổ chính
        } else {
            // Nếu đăng nhập thất bại
            JOptionPane.showMessageDialog(null, "Sai tên đăng nhập hoặc mật khẩu!");
        }
    }
}
