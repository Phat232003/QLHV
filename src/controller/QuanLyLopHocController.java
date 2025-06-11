package controller;

import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import model.LopHoc;
import service.LopHocService;
import service.LopHocServiceImpl;
import utility.ClassTableModel;
import view.LopHocJFrame;

public class QuanLyLopHocController {

    private JPanel jpnView;
    private JTextField jtfSearch;
    private JButton btnAdd;
    private JButton btnDelete;

    private ClassTableModel classTableModel = null;
    private LopHocService lopHocService = null;

    private TableRowSorter<TableModel> rowSorter = null;

    private final String[] COLUMNS = {
        "Mã lớp học", "Mã khóa học", "Mã học viên", "Ngày đăng ký", "Tình trạng"
    };

    public QuanLyLopHocController(JPanel jpnView, JButton btnAdd, JTextField jtfSearch,JButton btnDelete ) {
        this.jpnView = jpnView;
        this.btnAdd = btnAdd;
        this.jtfSearch = jtfSearch;
        this.btnDelete=btnDelete;

        this.classTableModel = new ClassTableModel();
        this.lopHocService = new LopHocServiceImpl();
    }

    public void setDataToTable() {
        List<LopHoc> listItem = lopHocService.getList();

        DefaultTableModel model = classTableModel.setTableLopHoc(listItem, COLUMNS);
        JTable table = new JTable(model);

        rowSorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(rowSorter);

        jtfSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = jtfSearch.getText();
                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = jtfSearch.getText();
                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });


table.addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
            try {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                int selectedRowIndex = table.convertRowIndexToModel(table.getSelectedRow());

                // Retrieve data from the table model
                Object maLopHoc = model.getValueAt(selectedRowIndex, 0);
                Object maKhoaHoc = model.getValueAt(selectedRowIndex, 1);
                Object maHocVien = model.getValueAt(selectedRowIndex, 2);
                Object ngayDangKy = model.getValueAt(selectedRowIndex, 3);
//                Object tinhTrang = model.getValueAt(selectedRowIndex, 4);

                // Create LopHoc object and set values with type checking
                LopHoc lopHoc = new LopHoc();

                // Set ma_lop_hoc (Integer)
                if (maLopHoc instanceof Integer) {
                    lopHoc.setMa_lop_hoc((Integer) maLopHoc);
                } else {
                    throw new IllegalStateException("Invalid ma_lop_hoc type: " + maLopHoc);
                }

                // Set ma_khoa_hoc (Integer)
                if (maKhoaHoc instanceof Integer) {
                    lopHoc.setMa_khoa_hoc((Integer) maKhoaHoc);
                } else {
                    throw new IllegalStateException("Invalid ma_khoa_hoc type: " + maKhoaHoc);
                }

                // Set ma_hoc_vien (Integer)
                if (maHocVien instanceof Integer) {
                    lopHoc.setMa_hoc_vien((Integer) maHocVien);
                } else {
                    throw new IllegalStateException("Invalid ma_hoc_vien type: " + maHocVien);
                }

                // Set ngay_dang_ky (Date, nullable)
                if (ngayDangKy instanceof java.util.Date) {
                    lopHoc.setNgay_dang_ky(new java.sql.Date(((java.util.Date) ngayDangKy).getTime()));
                } else if (ngayDangKy == null) {
                    lopHoc.setNgay_dang_ky(null); // Allow null as per schema
                } else {
                    throw new IllegalStateException("Invalid ngay_dang_ky type: " + ngayDangKy);
                }

//                // Set tinh_trang (Boolean)
//                if (tinhTrang instanceof Boolean) {
//                    lopHoc.setTinh_trang((Boolean) tinhTrang);
//                } else {
//                    throw new IllegalStateException("Invalid tinh_trang type: " + tinhTrang);
//                }

                // Create and configure the frame
                LopHocJFrame frame = new LopHocJFrame(lopHoc);
                frame.setLopHocControllerCallback(() -> setDataToTable());
                frame.setLocationRelativeTo(null);
                frame.setResizable(false);
                frame.setTitle("Thông tin lớp học");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Ensure frame disposal
                frame.setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(table, "Lỗi khi tải thông tin lớp học: " + ex.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
});

        // Table UI
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setPreferredSize(new Dimension(100, 40));
        table.setRowHeight(40);
        table.validate();
        table.repaint();

        // Scroll and display
        JScrollPane scroll = new JScrollPane();
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
                LopHocJFrame frame = new LopHocJFrame(new LopHoc());
                frame.setLopHocControllerCallback(() -> setDataToTable());

                frame.setLocationRelativeTo(null);
                frame.setResizable(false);
                frame.setTitle("Thêm lớp học mới");
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
                int maLopHoc = (int) table.getModel().getValueAt(selectedRowIndex, 0); // Giả sử cột 0 chứa ma_hoc_vien

                // Hiển thị hộp thoại xác nhận xóa
                int confirm = JOptionPane.showConfirmDialog(table, 
                    "Bạn có chắc chắn muốn xóa lớp học này?", "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (confirm == JOptionPane.YES_OPTION) {
                    // Gọi phương thức delete trong Service để xóa học viên
                    boolean isDeleted = lopHocService.delete(maLopHoc);

                    if (isDeleted) {
                        // Nếu xóa thành công, thông báo và cập nhật bảng
                        JOptionPane.showMessageDialog(table, "Lớp đã được xóa.");
                        
                        // Cập nhật lại dữ liệu trong bảng
                        setDataToTable();
                    } else {
                        // Nếu không xóa thành công, thông báo lỗi
                        JOptionPane.showMessageDialog(table, "Lỗi khi xóa lớp học .", 
                                                      "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                // Nếu không có dòng nào được chọn
                JOptionPane.showMessageDialog(table, "Vui lòng chọn một lớp để xóa.",
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
        
    
}}
