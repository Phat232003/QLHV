package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import controller.KhoaHocController;
import model.KhoaHoc;
import com.toedter.calendar.JDateChooser;

public class KhoaHocJFrame extends JFrame {

    private JTextField jtfMaKhoaHoc;
    private JTextField jtfTenKhoaHoc;
    private JTextArea jtaMoTa;
    private JDateChooser jdcNgayBatDau;
    private JDateChooser jdcNgayKetThuc;
    private JCheckBox jcbTinhTrang;
    private JLabel jlbMsg;
    private JButton btnSubmit;
    

    // ✅ Thêm biến callback
    private Runnable khoaHocControllerCallback;

    public KhoaHocJFrame() {
        initComponents();
    }

    public KhoaHocJFrame(KhoaHoc khoaHoc) {
        initComponents();
        KhoaHocController controller = new KhoaHocController(
                btnSubmit,
                jtfMaKhoaHoc,
                jtfTenKhoaHoc,
                jtaMoTa,
                jdcNgayBatDau,
                jdcNgayKetThuc,
                jcbTinhTrang,
                jlbMsg
        );
        controller.setView(khoaHoc);

        // ✅ Gọi callback sau khi lưu dữ liệu thành công
        controller.setSaveCallback(() -> {
            if (khoaHocControllerCallback != null) {
                khoaHocControllerCallback.run();
            }
        });
    }

    // ✅ Thêm setter cho callback
    public void setKhoaHocControllerCallback(Runnable callback) {
        this.khoaHocControllerCallback = callback;
    }

    private void initComponents() {
        setTitle("Thông tin khóa học");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // ✅ Không dùng EXIT_ON_CLOSE
        setSize(700, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Thông tin khóa học"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        jtfMaKhoaHoc = new JTextField(15);
        jtfTenKhoaHoc = new JTextField(15);
        jtaMoTa = new JTextArea(3, 15);
        JScrollPane scrollMoTa = new JScrollPane(jtaMoTa);
        jdcNgayBatDau = new JDateChooser();
        jdcNgayBatDau.setDateFormatString("dd/MM/yyyy");
        jdcNgayKetThuc = new JDateChooser();
        jdcNgayKetThuc.setDateFormatString("dd/MM/yyyy");
        jcbTinhTrang = new JCheckBox("Kích hoạt");

        // Form layout
        gbc.gridy = 0;
        gbc.gridx = 0; formPanel.add(new JLabel("Mã khóa học:"), gbc);
        gbc.gridx = 1; formPanel.add(jtfMaKhoaHoc, gbc);
        gbc.gridx = 2; formPanel.add(new JLabel("(*)"), gbc);
        gbc.gridx = 3; formPanel.add(new JLabel("Tên khóa học:"), gbc);
        gbc.gridx = 4; formPanel.add(jtfTenKhoaHoc, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0; formPanel.add(new JLabel("Mô tả:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 4; formPanel.add(scrollMoTa, gbc);
        gbc.gridwidth = 1;

        gbc.gridy = 2;
        gbc.gridx = 0; formPanel.add(new JLabel("Ngày bắt đầu:"), gbc);
        gbc.gridx = 1; formPanel.add(jdcNgayBatDau, gbc);
        gbc.gridx = 2; formPanel.add(new JLabel("(*)"), gbc);

        gbc.gridy = 3;
        gbc.gridx = 0; formPanel.add(new JLabel("Ngày kết thúc:"), gbc);
        gbc.gridx = 1; formPanel.add(jdcNgayKetThuc, gbc);
        gbc.gridx = 2; formPanel.add(new JLabel("(*)"), gbc);

        gbc.gridy = 4;
        gbc.gridx = 0; formPanel.add(new JLabel("Tình trạng:"), gbc);
        gbc.gridx = 1; formPanel.add(jcbTinhTrang, gbc);

        gbc.gridy = 5;
        gbc.gridx = 0; gbc.gridwidth = 5;
        formPanel.add(new JLabel("<html><font color='red'>(*) Dữ liệu yêu cầu bắt buộc</font></html>"), gbc);
        gbc.gridwidth = 1;

        gbc.gridy = 6;
        jlbMsg = new JLabel();
        jlbMsg.setForeground(Color.RED);
        formPanel.add(jlbMsg, gbc);

        btnSubmit = new JButton("Lưu dữ liệu");
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(btnSubmit);

        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);
        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new KhoaHocJFrame().setVisible(true);
        });
    }
}
