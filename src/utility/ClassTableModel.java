package utility;

import java.util.Date;
import model.HocVien;
import model.KhoaHoc;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class ClassTableModel {

    public DefaultTableModel setTableHocVien(List<HocVien> listItem, String[] listColumn) {
        int columns = listColumn.length;
        DefaultTableModel dtm = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 7 ? Boolean.class : String.class;
            }
        };
        dtm.setColumnIdentifiers(listColumn);
        Object[] obj;
        int num = listItem.size();
        HocVien hocVien = null;
        for (int i = 0; i < num; i++) {
            hocVien = listItem.get(i);
            obj = new Object[columns];
            obj[0] = hocVien.getMa_hoc_vien();
            obj[1] = (i + 1);
            obj[2] = hocVien.getHo_ten();
            obj[3] = hocVien.getNgay_sinh();
            obj[4] = hocVien.isGioi_tinh() ? "Nam" : "Nữ";
            obj[5] = hocVien.getSo_dien_thoai();
            obj[6] = hocVien.getDia_chi();
            obj[7] = hocVien.isTinh_trang();
            dtm.addRow(obj);
        }
        return dtm;
    }

    public DefaultTableModel setTableKhoaHoc(List<KhoaHoc> listItem, String[] listColumn) {
        int columns = listColumn.length;
        DefaultTableModel dtm = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 4 || columnIndex == 5) {
                    return Date.class;
                } else if (columnIndex == 6) {
                    return Boolean.class;
                } else {
                    return String.class;
                }
            }
        };
        dtm.setColumnIdentifiers(listColumn);
        Object[] obj;
        int num = listItem.size();
        KhoaHoc khoaHoc = null;
        for (int i = 0; i < num; i++) {
            khoaHoc = listItem.get(i);
            obj = new Object[columns];
            obj[0] = khoaHoc.getMa_khoa_hoc();
            obj[1] = khoaHoc.getTen_khoa_hoc();
            obj[2] = khoaHoc.getMo_ta();
            obj[3] = khoaHoc.getNgay_bat_dau();
            obj[4] = khoaHoc.getNgay_ket_thuc();
            obj[5] = khoaHoc.isTinh_trang();
            dtm.addRow(obj);
        }
        return dtm;
    }
}
