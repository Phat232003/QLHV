package controller;

import javax.swing.JLabel;
import service.HocVienService;
import service.HocVienServiceImpl;
import service.KhoaHocService;
import service.KhoaHocServiceImpl;
import service.LopHocService;
import service.LopHocServiceImpl;

public class TrangChuController {

    private JLabel jlbKhoaHoc;
    private JLabel jlbHocVien;
    private JLabel jlbLopHoc;

    private KhoaHocService khoaHocService = null;
    private HocVienService hocVienService = null;
    private LopHocService lopHocService = null;

    public TrangChuController(JLabel jlbKhoaHoc, JLabel jlbHocVien, JLabel jlbLopHoc) {
        this.jlbKhoaHoc = jlbKhoaHoc;
        this.jlbHocVien = jlbHocVien;
        this.jlbLopHoc = jlbLopHoc;

        this.khoaHocService = new KhoaHocServiceImpl();
        this.hocVienService = new HocVienServiceImpl();
        this.lopHocService=new LopHocServiceImpl();
    }

    public void setData() {
        int soKhoaHoc = khoaHocService.getList().size();
        int soHocVien = hocVienService.getList().size();  // hoặc getSoLuongHocVien()
        int soLopHoc = lopHocService.getList().size();

        jlbKhoaHoc.setText(String.valueOf(soKhoaHoc));
        jlbHocVien.setText(String.valueOf(soHocVien));
        jlbLopHoc.setText(String.valueOf(soLopHoc));
    }
}
