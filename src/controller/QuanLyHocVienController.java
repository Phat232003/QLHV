package controller;

import model.HocVien;
import service.HocVienService;
import service.HocVienServiceImpl;
import utility.ClassTableModel;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import view.HocVienJFrame;

/**
 *
 * @author TVD
 */
public class QuanLyHocVienController {

    private JPanel jpnView;
    private JButton btnAdd;
    private JTextField jtfSearch;
private JButton btnDelete;
    private ClassTableModel classTableModel = null;

    private final String[] COLUMNS = {"Mã học viên", "STT", "Tên học viên", "Ngày sinh",
        "Giới tính", "Số điện thoại", "Địa chỉ", "Trạng thái"};

    private HocVienService hocVienService = null;

    private TableRowSorter<TableModel> rowSorter = null;

    public QuanLyHocVienController(JPanel jpnView, JButton btnAdd, JTextField jtfSearch,JButton btnDelete) {
        this.jpnView = jpnView;
        this.btnAdd = btnAdd;
        this.jtfSearch = jtfSearch;
        this.btnDelete=btnDelete;
        this.classTableModel = new ClassTableModel();

        this.hocVienService = new HocVienServiceImpl();
    }

    public void setDataToTable() {
        List<HocVien> listItem = hocVienService.getList();
        DefaultTableModel model = classTableModel.setTableHocVien(listItem, COLUMNS);
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
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    int selectedRowIndex = table.getSelectedRow();

                    selectedRowIndex = table.convertRowIndexToModel(selectedRowIndex);

                    HocVien hocVien = new HocVien();
                    hocVien.setMa_hoc_vien((int) model.getValueAt(selectedRowIndex, 0));
                    hocVien.setHo_ten(model.getValueAt(selectedRowIndex, 2).toString());
                    hocVien.setNgay_sinh((Date) model.getValueAt(selectedRowIndex, 3));
                    hocVien.setGioi_tinh(model.getValueAt(selectedRowIndex, 4).toString().equalsIgnoreCase("Nam"));
                    hocVien.setSo_dien_thoai(model.getValueAt(selectedRowIndex, 5).toString());
                    hocVien.setDia_chi(model.getValueAt(selectedRowIndex, 6).toString());
                    hocVien.setTinh_trang((boolean) model.getValueAt(selectedRowIndex, 7));

                    HocVienJFrame frame = new HocVienJFrame(hocVien);

                    // Truyền callback cập nhật bảng khi lưu thành công
                    frame.setHocVienControllerCallback(() -> setDataToTable());

                    frame.setLocationRelativeTo(null);
                    frame.setResizable(false);
                    frame.setTitle("Thông tin học viên");
                    frame.setVisible(true);
                }
            }
        });
        // design
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setPreferredSize(new Dimension(100, 50));
        table.setRowHeight(50);
        table.validate();
        table.repaint();

        JScrollPane scroll = new JScrollPane();
        scroll.getViewport().add(table);
        scroll.setPreferredSize(new Dimension(1350, 400));
        jpnView.removeAll();
        jpnView.setLayout(new CardLayout());
        jpnView.add(scroll);
        jpnView.validate();
        jpnView.repaint();
    }

    public void setEvent() {
        btnAdd.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                HocVienJFrame frame = new HocVienJFrame(new HocVien());

                // Truyền callback để cập nhật bảng khi thêm mới học viên
                frame.setHocVienControllerCallback(() -> setDataToTable());

                frame.setLocationRelativeTo(null);
                frame.setResizable(false);
                frame.setTitle("Thêm học viên mới");
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
                int maHocVien = (int) table.getModel().getValueAt(selectedRowIndex, 0); // Giả sử cột 0 chứa ma_hoc_vien

                // Hiển thị hộp thoại xác nhận xóa
                int confirm = JOptionPane.showConfirmDialog(table, 
                    "Bạn có chắc chắn muốn xóa học viên này?", "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (confirm == JOptionPane.YES_OPTION) {
                    // Gọi phương thức delete trong Service để xóa học viên
                    boolean isDeleted = hocVienService.delete(maHocVien);

                    if (isDeleted) {
                        // Nếu xóa thành công, thông báo và cập nhật bảng
                        JOptionPane.showMessageDialog(table, "Học viên đã được xóa.");
                        
                        // Cập nhật lại dữ liệu trong bảng
                        setDataToTable();
                    } else {
                        // Nếu không xóa thành công, thông báo lỗi
                        JOptionPane.showMessageDialog(table, "Lỗi khi xóa học viên.", 
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

