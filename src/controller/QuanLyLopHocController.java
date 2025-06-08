package controller;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;

import model.LopHoc;
import service.LopHocService;
import service.LopHocServiceImpl;
import utility.ClassTableModel;
import view.LopHocJFrame;

public class QuanLyLopHocController {

    private JPanel jpnView;
    private JTextField jtfSearch;
    private JButton btnAdd;

    private ClassTableModel classTableModel = null;
    private LopHocService lopHocService = null;

    private TableRowSorter<TableModel> rowSorter = null;

    private final String[] COLUMNS = {
        "Mã lớp học", "Mã khóa học", "Mã học viên", "Ngày đăng ký", "Tình trạng"
    };

    public QuanLyLopHocController(JPanel jpnView, JButton btnAdd, JTextField jtfSearch) {
        this.jpnView = jpnView;
        this.btnAdd = btnAdd;
        this.jtfSearch = jtfSearch;

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
            public void insertUpdate(DocumentEvent e) {
                filterTable(jtfSearch.getText());
            }
            public void removeUpdate(DocumentEvent e) {
                filterTable(jtfSearch.getText());
            }
            public void changedUpdate(DocumentEvent e) {}
        });

        // Table UI
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setPreferredSize(new Dimension(100, 40));
        table.setRowHeight(40);
        table.validate();
        table.repaint();

        // Scroll and display
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1350, 400));

        jpnView.removeAll();
        jpnView.setLayout(new CardLayout());
        jpnView.add(scrollPane);
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
                new LopHocJFrame(new LopHoc()).setVisible(true);
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
