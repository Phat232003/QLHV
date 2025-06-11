package controller;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;

import view.KhoaHocJFrame;
import model.KhoaHoc;
import service.KhoaHocService;
import service.KhoaHocServiceImpl;
import utility.ClassTableModel;

public class QuanLyKhoaHocController {
    private JPanel jpnView;
    private JTextField jtfSearch;
    private JButton btnAdd;
    private JButton btnDelete;

    private ClassTableModel classTabModel = null;
    private String[] COLUMNS = {"Mã khóa học", "Tên Khóa Học", "Mô tả", "Ngày bắt đầu", "Ngày kết thúc", "Trạng Thái"};
    private KhoaHocService khoaHocService = null;
    private TableRowSorter<TableModel> rowSorter = null;

    public QuanLyKhoaHocController(JPanel jpnView, JButton btnAdd, JTextField jtfSearch,JButton btnDelete) {
        this.jpnView = jpnView;
        this.btnAdd = btnAdd;
        this.jtfSearch = jtfSearch;
        this.btnDelete=btnDelete;
        this.classTabModel = new ClassTableModel();
        this.khoaHocService = new KhoaHocServiceImpl();
    }

    public void setDataToTable() {
        List<KhoaHoc> listItem = khoaHocService.getList();
        DefaultTableModel model = classTabModel.setTableKhoaHoc(listItem, COLUMNS);
        JTable table = new JTable(model);

        rowSorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(rowSorter);

        jtfSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterTable(jtfSearch.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterTable(jtfSearch.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {}
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    int selectedRowIndex = table.convertRowIndexToModel(table.getSelectedRow());

                    KhoaHoc khoaHoc = new KhoaHoc();
                    khoaHoc.setMa_khoa_hoc((int) model.getValueAt(selectedRowIndex, 0));
                    khoaHoc.setTen_khoa_hoc(model.getValueAt(selectedRowIndex, 1).toString());
                    khoaHoc.setMo_ta(model.getValueAt(selectedRowIndex, 2).toString());

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        String ngayBatDauStr = model.getValueAt(selectedRowIndex, 3).toString();
                        String ngayKetThucStr = model.getValueAt(selectedRowIndex, 4).toString();

                        Date ngayBatDau = sdf.parse(ngayBatDauStr);
                        Date ngayKetThuc = sdf.parse(ngayKetThucStr);

                        khoaHoc.setNgay_bat_dau(new java.sql.Date(ngayBatDau.getTime()));
                        khoaHoc.setNgay_ket_thuc(new java.sql.Date(ngayKetThuc.getTime()));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Lỗi định dạng ngày tháng");
                        return;
                    }

                    khoaHoc.setTinh_trang((boolean) model.getValueAt(selectedRowIndex, 5));

                    KhoaHocJFrame frame = new KhoaHocJFrame(khoaHoc);
                    frame.setKhoaHocControllerCallback(() -> setDataToTable());
                    frame.setLocationRelativeTo(null);
                    frame.setResizable(false);
                    frame.setTitle("Thông tin khóa học");
                    frame.setVisible(true);
                }
            }
        });

        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setPreferredSize(new Dimension(100, 50));
        table.setRowHeight(50);
        table.validate();
        table.repaint();

        JScrollPane scroll = new JScrollPane(table);
        scroll.getViewport().add(table);
        scroll.setPreferredSize(new Dimension(1350, 400));
        jpnView.removeAll();
        jpnView.setLayout(new CardLayout());
        jpnView.add(scroll);
        jpnView.validate();
        jpnView.repaint();
    }

    private void filterTable(String text) {
        if (text.trim().length() == 0) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
    }

    public void setEvent() {
        btnAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                KhoaHocJFrame frame = new KhoaHocJFrame(new KhoaHoc());
                frame.setLocationRelativeTo(null);
                frame.setResizable(false);
                frame.setTitle("Thêm khóa học mới");
                frame.setVisible(true);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btnAdd.setBackground(new Color(0, 200, 83));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnAdd.setBackground(new Color(100, 221, 23));
            }
        });
        btnDelete.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            // Lấy JTable từ jpnView (sẽ là nơi chứa bảng)
            JTable table = (JTable) ((JScrollPane) jpnView.getComponent(0)).getViewport().getView();
            
            // Kiểm tra nếu có dòng được chọn
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                // Chuyển chỉ số dòng từ view sang model
                int selectedRowIndex = table.convertRowIndexToModel(selectedRow);

                // Lấy ma_hoc_vien từ dòng được chọn
                int maKhoaHoc = (int) table.getModel().getValueAt(selectedRowIndex, 0); // Giả sử cột 0 chứa ma_khoa_vien

                // Hiển thị hộp thoại xác nhận xóa
                int confirm = JOptionPane.showConfirmDialog(table, 
                    "Bạn có chắc chắn muốn xóa khóa học này?", "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (confirm == JOptionPane.YES_OPTION) {
                    // Gọi phương thức delete trong Service để xóa học viên
                    boolean isDeleted = khoaHocService.delete(maKhoaHoc);

                    if (isDeleted) {
                        // Nếu xóa thành công, thông báo và cập nhật bảng
                        JOptionPane.showMessageDialog(table, "khoá học đã được xóa.");
                        
                        // Cập nhật lại dữ liệu trong bảng
                        setDataToTable();
                    } else {
                        // Nếu không xóa thành công, thông báo lỗi
                        JOptionPane.showMessageDialog(table, "Lỗi khi xóa khoá học .", 
                                                      "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                // Nếu không có dòng nào được chọn
                JOptionPane.showMessageDialog(table, "Vui lòng chọn một học viên để xóa.",
                                              "Thông báo", JOptionPane.WARNING_MESSAGE);
            }
        }

             
            
            @Override
            public void mouseEntered(MouseEvent e) {
                btnDelete.setBackground(new Color(0, 200, 83));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnDelete.setBackground(new Color(100, 221, 23));
            }
        });
    }
}
