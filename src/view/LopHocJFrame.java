package view;

import com.toedter.calendar.JDateChooser;
import controller.LopHocController;
import model.LopHoc;

import javax.swing.*;
import java.awt.*;

public class LopHocJFrame extends JFrame {

    private JTextField jtfMaLopHoc;
    private JTextField jtfMaKhoaHoc;
    private JTextField jtfMaHocVien;
    private JDateChooser jdcNgayDangKy;
    private JCheckBox jcbTinhTrang;
    private JButton btnSubmit;
    private JLabel lblMsg;

    private LopHocController lopHocController;

    // Callback để thông báo khi lưu thành công
    private Runnable lopHocControllerCallback;

    public LopHocJFrame(LopHoc lopHoc) {
        initComponents();

        lopHocController = new LopHocController(
                btnSubmit,
                jtfMaLopHoc,
                jtfMaKhoaHoc,
                jtfMaHocVien,
                jdcNgayDangKy,
                jcbTinhTrang,
                lblMsg);

        lopHocController.setView(lopHoc);

        // Thiết lập sự kiện lưu dữ liệu, khi lưu thành công gọi callback để update bảng
        lopHocController.setSaveCallback(() -> {
            if (lopHocControllerCallback != null) {
                lopHocControllerCallback.run();
            }
            this.dispose(); // đóng frame sau khi lưu thành công
        });
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 350);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        jtfMaLopHoc = new JTextField(20);
        jtfMaKhoaHoc = new JTextField(20);
        jtfMaHocVien = new JTextField(20);
        jdcNgayDangKy = new JDateChooser();
        jcbTinhTrang = new JCheckBox("Tình trạng");
        btnSubmit = new JButton("Lưu");
        lblMsg = new JLabel();

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Mã lớp học:"), gbc);
        gbc.gridx = 1;
        add(jtfMaLopHoc, gbc);

        gbc.gridx = 0; gbc.gridy++;
        add(new JLabel("Mã khóa học:"), gbc);
        gbc.gridx = 1;
        add(jtfMaKhoaHoc, gbc);

        gbc.gridx = 0; gbc.gridy++;
        add(new JLabel("Mã học viên:"), gbc);
        gbc.gridx = 1;
        add(jtfMaHocVien, gbc);

        gbc.gridx = 0; gbc.gridy++;
        add(new JLabel("Ngày đăng ký:"), gbc);
        gbc.gridx = 1;
        add(jdcNgayDangKy, gbc);

        gbc.gridx = 1; gbc.gridy++;
        add(jcbTinhTrang, gbc);

        gbc.gridx = 1; gbc.gridy++;
        add(btnSubmit, gbc);

        gbc.gridx = 1; gbc.gridy++;
        add(lblMsg, gbc);
    }

    // Hàm cho phép controller bên ngoài thiết lập callback
    public void setLopHocControllerCallback(Runnable callback) {
        this.lopHocControllerCallback = callback;
    }
}
