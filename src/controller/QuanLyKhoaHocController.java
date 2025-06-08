package controller;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import model.KhoaHoc;
import service.KhoaHocService;
import service.KhoaHocServiceImpl;
import utility.ClassTableModel;

public class QuanLyKhoaHocController {
    private JPanel jpnView;
    private JTextField jtfSearch;

    private ClassTableModel classTabModel = null;
    private String[] COLUMNS = {"Mã khóa học", "Tên Khóa Học", "Mô tả", "Ngày bắt đầu", "Ngày kết thúc", "Trạng Thái"};
    private KhoaHocService khoaHocService = null;
    private TableRowSorter<TableModel> rowSorter = null;

    public QuanLyKhoaHocController(JPanel jpnView, JTextField jtfSearch) {
        this.jpnView = jpnView;
        this.jtfSearch = jtfSearch;

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
}
