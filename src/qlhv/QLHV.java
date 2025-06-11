/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package qlhv;

import view.DangNhapJDialog;
import view.MainJFrame;

/**
 *
 * @author phath
 */
public class QLHV {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DangNhapJDialog dialog = new DangNhapJDialog(null, true);
        dialog.setTitle("Đăng Nhập Hệ Thống");
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

}
