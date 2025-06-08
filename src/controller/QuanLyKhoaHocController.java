package controller;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;

import model.KhoaHoc;
import service.KhoaHocService;
import service.KhoaHocServiceImpl;
import utility.ClassTableModel;
import view.KhoaHocJFrame; // Đảm bảo class này tồn tại

public class QuanLyKhoaHocController {
    private JPanel jpnView;
    private JTextField jtfSearch;
    private JButton btnAdd;

    private ClassTableModel classTabModel = null;
    private String[] COLUMNS = {"Mã khóa học", "Tên Khóa Học", "Mô tả", "Ngày bắt đầu", "Ngày kết thúc", "Trạng Thái"};
    private KhoaHocService khoaHocService = null;
    private TableRowSorter<TableModel> rowSorter = null;

    public QuanLyKhoaHocController(JPanel jpnView, JButton btnAdd, JTextField jtfSearch) {
        this.jpnView = jpnView;
        this.btnAdd = btnAdd;
        this.jtfSearch = jtfSearch;
        this.classTabModel = new ClassTableModel();
        this.khoaHocService = new KhoaHocServiceImpl();
    }

    public void setDataToTable() {
        // Lấy danh sách khóa học từ service
        List<KhoaHoc> listItem = khoaHocService.getList();

        // Tạo model bảng từ danh sách
        DefaultTableModel model = classTabModel.setTableKhoaHoc(listItem, COLUMNS);
        JTable table = new JTable(model);

        // Thiết lập bộ lọc tìm kiếm
        rowSorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(rowSorter);

        // Gắn DocumentListener cho ô tìm kiếm
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
            public void changedUpdate(DocumentEvent e) {
                // Không cần xử lý styled documents
            }
        });

        // Tùy chỉnh giao diện bảng
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setPreferredSize(new Dimension(100, 50));
        table.setRowHeight(50);
        table.validate();
        table.repaint();

        // Thêm bảng vào giao diện
        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(1350, 400));
        jpnView.removeAll();
        jpnView.setLayout(new CardLayout());
        jpnView.add(scroll);
        jpnView.validate();
        jpnView.repaint();
    }

    // Hàm hỗ trợ lọc bảng
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
                new KhoaHocJFrame(new KhoaHoc()).setVisible(true); // Đảm bảo class này đúng
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
    }
}
