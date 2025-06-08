package view;

import com.toedter.calendar.JDateChooser;
import controller.LopHocController;
import model.LopHoc;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class LopHocJFrame extends JFrame {

    private JPanel contentPane;
    private JTextField jtfMaLopHoc;
    private JTextField jtfMaKhoaHoc;
    private JTextField jtfMaHocVien;
    private JDateChooser jdcNgayDangKy;
    private JCheckBox jcbTinhTrang;
    private JLabel jlbMsg;
    private JButton btnSubmit;

    public LopHocJFrame() {
        initComponents();
    }

    public LopHocJFrame(LopHoc lopHoc) {
        initComponents();
        LopHocController controller = new LopHocController(
                btnSubmit,
                jtfMaLopHoc,
                jtfMaKhoaHoc,
                jtfMaHocVien,
                jdcNgayDangKy,
                jcbTinhTrang,
                jlbMsg
        );
        controller.setView(lopHoc);
    }

    private void initComponents() {
        setTitle("Thông tin lớp học");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 350);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Thông tin lớp học", TitledBorder.LEADING, TitledBorder.TOP));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        jtfMaLopHoc = new JTextField(15);
        jtfMaKhoaHoc = new JTextField(15);
        jtfMaHocVien = new JTextField(15);
        jdcNgayDangKy = new JDateChooser();
        jdcNgayDangKy.setDateFormatString("dd/MM/yyyy");
        jcbTinhTrang = new JCheckBox("Kích hoạt");

        // Row 1
        gbc.gridy = 0;
        gbc.gridx = 0; formPanel.add(new JLabel("Mã lớp học:"), gbc);
        gbc.gridx = 1; formPanel.add(jtfMaLopHoc, gbc);

        // Row 2
        gbc.gridy = 1;
        gbc.gridx = 0; formPanel.add(new JLabel("Mã khóa học:"), gbc);
        gbc.gridx = 1; formPanel.add(jtfMaKhoaHoc, gbc);
        gbc.gridx = 2; formPanel.add(new JLabel("(*)"), gbc);

        // Row 3
        gbc.gridy = 2;
        gbc.gridx = 0; formPanel.add(new JLabel("Mã học viên:"), gbc);
        gbc.gridx = 1; formPanel.add(jtfMaHocVien, gbc);
        gbc.gridx = 2; formPanel.add(new JLabel("(*)"), gbc);

        // Row 4
        gbc.gridy = 3;
        gbc.gridx = 0; formPanel.add(new JLabel("Ngày đăng ký:"), gbc);
        gbc.gridx = 1; formPanel.add(jdcNgayDangKy, gbc);
        gbc.gridx = 2; formPanel.add(new JLabel("(*)"), gbc);

        // Row 5
        gbc.gridy = 4;
        gbc.gridx = 0; formPanel.add(new JLabel("Tình trạng:"), gbc);
        gbc.gridx = 1; formPanel.add(jcbTinhTrang, gbc);

        // Row 6: note
        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        formPanel.add(new JLabel("<html><font color='red'>(*) Dữ liệu bắt buộc</font></html>"), gbc);

        // Row 7: message
        gbc.gridy = 6;
        jlbMsg = new JLabel();
        jlbMsg.setForeground(Color.RED);
        formPanel.add(jlbMsg, gbc);

        // Submit
        btnSubmit = new JButton("Lưu dữ liệu");
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(btnSubmit);

        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);
        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LopHocJFrame().setVisible(true);
        });
    }
}
