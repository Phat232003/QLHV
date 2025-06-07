package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import controller.HocVienController;
import model.HocVien;
import com.toedter.calendar.JDateChooser;

public class HocVienJFrame extends JFrame {

    private JPanel contentPane;
    private JTextField jtfMaHocVien;
    private JTextField jtfHoTen;
    private JDateChooser jdcNgaySinh;
    private JTextField jtfSoDienThoai;
    private JTextArea jtaDiaChi;
    private JCheckBox jcbKichHoat;
    private JRadioButton jrdGioiTinhNam;
    private JRadioButton jrdGioiTinhNu;
    private JLabel jlbMsg;
    private JButton btnSubmit;

    // Constructor khởi tạo giao diện trống
    public HocVienJFrame() {
        initComponents();
    }

    // Constructor khởi tạo giao diện từ dữ liệu học viên
    public HocVienJFrame(HocVien hocVien) {
        initComponents(); // tạo giao diện trước
        HocVienController controller = new HocVienController(
                btnSubmit,
                jtfMaHocVien,
                jtfHoTen,
                jdcNgaySinh,
                jtfSoDienThoai,
                jrdGioiTinhNam,
                jrdGioiTinhNu,
                jtaDiaChi,
                jcbKichHoat,
                jlbMsg
        );
        controller.setView(hocVien);
    }

    private void initComponents() {
        setTitle("Thông tin học viên");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Thông tin học viên"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        jtfMaHocVien = new JTextField(15);
        jtfHoTen = new JTextField(15);
        jdcNgaySinh = new JDateChooser();
        jdcNgaySinh.setDateFormatString("dd/MM/yyyy");
        jtfSoDienThoai = new JTextField(15);
        jtaDiaChi = new JTextArea(4, 15);
        JScrollPane scrollDiaChi = new JScrollPane(jtaDiaChi);

        jrdGioiTinhNam = new JRadioButton("Nam");
        jrdGioiTinhNu = new JRadioButton("Nữ");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(jrdGioiTinhNam);
        genderGroup.add(jrdGioiTinhNu);

        jcbKichHoat = new JCheckBox("Kích hoạt");

        // Row 1
        gbc.gridy = 0;
        gbc.gridx = 0; formPanel.add(new JLabel("Mã học viên:"), gbc);
        gbc.gridx = 1; formPanel.add(jtfMaHocVien, gbc);
        gbc.gridx = 2; formPanel.add(new JLabel("(*)"), gbc);
        gbc.gridx = 3; formPanel.add(new JLabel("Số điện thoại:"), gbc);
        gbc.gridx = 4; formPanel.add(jtfSoDienThoai, gbc);

        // Row 2
        gbc.gridy = 1;
        gbc.gridx = 0; formPanel.add(new JLabel("Họ và tên:"), gbc);
        gbc.gridx = 1; formPanel.add(jtfHoTen, gbc);
        gbc.gridx = 2; formPanel.add(new JLabel("(*)"), gbc);
        gbc.gridx = 3; formPanel.add(new JLabel("Địa chỉ:"), gbc);
        gbc.gridx = 4; formPanel.add(scrollDiaChi, gbc);

        // Row 3
        gbc.gridy = 2;
        gbc.gridx = 0; formPanel.add(new JLabel("Ngày sinh:"), gbc);
        gbc.gridx = 1; formPanel.add(jdcNgaySinh, gbc);
        gbc.gridx = 2; formPanel.add(new JLabel("(*)"), gbc);

        // Row 4
        gbc.gridy = 3;
        gbc.gridx = 0; formPanel.add(new JLabel("Giới tính:"), gbc);
        gbc.gridx = 1;
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        genderPanel.add(jrdGioiTinhNam);
        genderPanel.add(jrdGioiTinhNu);
        formPanel.add(genderPanel, gbc);

        // Row 5
        gbc.gridy = 4;
        gbc.gridx = 0; formPanel.add(new JLabel("Tình trạng:"), gbc);
        gbc.gridx = 1; formPanel.add(jcbKichHoat, gbc);

        // Row 6: ghi chú
        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 5;
        formPanel.add(new JLabel("<html><font color='red'>(*) Dữ liệu yêu cầu bắt buộc</font></html>"), gbc);

        // Message label
        gbc.gridy = 6;
        jlbMsg = new JLabel();
        jlbMsg.setForeground(Color.RED);
        formPanel.add(jlbMsg, gbc);

        // Submit button
        btnSubmit = new JButton("Lưu dữ liệu");
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(btnSubmit);

        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);
        add(panel);
        
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new HocVienJFrame().setVisible(true);
        });
    }
}
