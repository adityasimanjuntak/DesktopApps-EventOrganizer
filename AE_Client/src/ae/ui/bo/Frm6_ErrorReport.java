/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ae.ui.bo;

import ae.ui.fo.*;
import ae.config.configuration;
import ae.dao.api.IErrorReportDao;
import ae.dao.api.IEventOrganizerDao;
import ae.model.ErrorReport;
import ae.model.EventOrganizer;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import static java.awt.image.ImageObserver.WIDTH;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

/**
 *
 * @author Aditya
 */
public class Frm6_ErrorReport extends javax.swing.JFrame {

    /**
     * Creates new form FO4_PendaftaranEO
     */
    public Frm6_ErrorReport() {
        initComponents();
        init_ErrorReport_Dao();
        init_TableErrorReport();
        loadDaftarError();

        initModelAwal();
    }

    int mode;
    private IErrorReportDao errorDao = null;

    private final Object[] DaftarErrorcolumNames = {"Error ID", "ID Pegawai", "Error Title", "Description", "Penanggung Jawab", "Status"};
    private final DefaultTableModel tableModelError = new DefaultTableModel();
    private List<ErrorReport> recordError = new ArrayList<ErrorReport>();

    private void init_ErrorReport_Dao() {
        String url = "rmi://" + configuration.ip_server + ":1099/errorDao";
        try {
            //ambil referensi dari remote objek yang ada di server
            errorDao = (IErrorReportDao) Naming.lookup(url);
        } catch (NotBoundException | MalformedURLException ex) {
            Logger.getLogger(Frm6_ErrorReport.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            //Logger.getLogger(FrmSupplier.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Koneksi ke Service" + url + "[GAGAL], cek kembali app server", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void init_TableErrorReport() {
        //set header table
        tableModelError.setColumnIdentifiers(DaftarErrorcolumNames);
        tbl_error.setModel(tableModelError);
        tbl_error.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void loadDaftarError() {
        try {
            // reset data di tabel
            tableModelError.setRowCount(0);
            // mengambil data barang dari server
            // kemudian menyimpannya ke objek list
            recordError = errorDao.getAll();
            // ekstrak data barang yg ada di dalam objek list
            // kemudian menampilkannya ke objek tabel
            for (ErrorReport R : recordError) {
                // ambil nomor urut terakhir
                Object[] objects = new Object[DaftarErrorcolumNames.length];
                objects[0] = R.getError_id();
                objects[1] = R.getError_emp_id();
                objects[2] = R.getError_title();
                objects[3] = R.getError_text();
                objects[4] = R.getError_pic();
                objects[5] = R.getError_state();
                // tambahkan data barang ke dalam tabel
                tableModelError.addRow(objects);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Frm6_ErrorReport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initModelAwal() {
        mode = 1;
        panel_main.setVisible(true);
        panel_error_report.setVisible(false);
    }

    private void initModelDua() {
        mode = 2;
        panel_main.setVisible(false);
        panel_error_report.setVisible(false);
    }

    private void initModelTiga() {
        mode = 3;
        panel_main.setVisible(false);
        panel_error_report.setVisible(false);
    }

    private void initModelEmpat() {
        mode = 4;
        panel_main.setVisible(false);
        panel_error_report.setVisible(true);
    }

    private void ClearFormError() {
        txt_error_id.setText("");
        txt_error_id_emp.setText("");
        txt_error_title.setText("");
        txt_error_description.setText("");
        txt_error_title.requestFocus();
        txt_pic.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        Daftar_Error = new javax.swing.JDialog();
        btn_pilih_error = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        tbl_error = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        panel_kanan = new javax.swing.JPanel();
        lbl_btn_kembali = new javax.swing.JLabel();
        base_kanan = new javax.swing.JLabel();
        panel_kiri = new javax.swing.JPanel();
        lbl_btn_listdaftareo = new javax.swing.JLabel();
        base_kiri = new javax.swing.JLabel();
        panel_main = new javax.swing.JPanel();
        icon_form = new javax.swing.JPanel();
        lbl_iconform = new javax.swing.JLabel();
        base_panel_main = new javax.swing.JLabel();
        panel_error_report = new javax.swing.JPanel();
        txt_error_id = new javax.swing.JTextField();
        txt_error_title = new javax.swing.JTextField();
        btn_cari = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        txt_error_description = new javax.swing.JTextArea();
        txt_pic = new javax.swing.JTextField();
        txt_error_id_emp = new javax.swing.JTextField();
        cb_state = new javax.swing.JComboBox<>();
        lbl_simpan_error = new javax.swing.JLabel();
        lbl_reset_error = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        base_pend_eo2 = new javax.swing.JLabel();
        panel_atas = new javax.swing.JPanel();
        lbl_header = new javax.swing.JLabel();
        panel_bawah = new javax.swing.JPanel();
        lbl_footer = new javax.swing.JLabel();

        Daftar_Error.setTitle("Amikom Event");
        Daftar_Error.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_pilih_error.setText("PILIH");
        btn_pilih_error.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pilih_errorActionPerformed(evt);
            }
        });
        Daftar_Error.getContentPane().add(btn_pilih_error, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 340, 90, -1));

        tbl_error.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane8.setViewportView(tbl_error);

        Daftar_Error.getContentPane().add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 480, 220));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/bG_daftar_eo_dialog.png"))); // NOI18N
        Daftar_Error.getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Amikom Event");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel_kanan.setPreferredSize(new java.awt.Dimension(200, 550));
        panel_kanan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_btn_kembali.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/btn_kembali.png"))); // NOI18N
        lbl_btn_kembali.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_btn_kembali.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_btn_kembaliMouseClicked(evt);
            }
        });
        panel_kanan.add(lbl_btn_kembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, -1, -1));

        base_kanan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/panel_kanan.png"))); // NOI18N
        panel_kanan.add(base_kanan, new org.netbeans.lib.awtextra.AbsoluteConstraints(-9, 0, 210, -1));

        getContentPane().add(panel_kanan, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 100, -1, -1));

        panel_kiri.setPreferredSize(new java.awt.Dimension(200, 550));
        panel_kiri.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_btn_listdaftareo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/btn_do_maintenance.png"))); // NOI18N
        lbl_btn_listdaftareo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_btn_listdaftareo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_btn_listdaftareoMouseClicked(evt);
            }
        });
        panel_kiri.add(lbl_btn_listdaftareo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, -1, -1));

        base_kiri.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/panel_kiri.png"))); // NOI18N
        panel_kiri.add(base_kiri, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(panel_kiri, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, -1, -1));

        panel_main.setPreferredSize(new java.awt.Dimension(800, 550));
        panel_main.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        icon_form.setPreferredSize(new java.awt.Dimension(510, 260));
        icon_form.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_iconform.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/ikon_form_update.png"))); // NOI18N
        lbl_iconform.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_iconform.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_iconformMouseClicked(evt);
            }
        });
        icon_form.add(lbl_iconform, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 510, -1));

        panel_main.add(icon_form, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 130, -1, -1));

        base_panel_main.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/panel_main.png"))); // NOI18N
        panel_main.add(base_panel_main, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(panel_main, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 100, -1, -1));

        panel_error_report.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_error_id.setFont(new java.awt.Font("Gotham Light", 0, 14)); // NOI18N
        txt_error_id.setEnabled(false);
        txt_error_id.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_error_idMouseClicked(evt);
            }
        });
        panel_error_report.add(txt_error_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, 210, 30));

        txt_error_title.setFont(new java.awt.Font("Gotham Light", 0, 14)); // NOI18N
        panel_error_report.add(txt_error_title, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 220, 210, 30));

        btn_cari.setText("CARI");
        btn_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cariActionPerformed(evt);
            }
        });
        panel_error_report.add(btn_cari, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 140, 60, 30));

        txt_error_description.setColumns(20);
        txt_error_description.setRows(5);
        jScrollPane3.setViewportView(txt_error_description);

        panel_error_report.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 310, 310, 170));

        txt_pic.setFont(new java.awt.Font("Gotham Light", 0, 14)); // NOI18N
        txt_pic.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_picKeyPressed(evt);
            }
        });
        panel_error_report.add(txt_pic, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 320, 270, 30));

        txt_error_id_emp.setFont(new java.awt.Font("Gotham Light", 0, 14)); // NOI18N
        panel_error_report.add(txt_error_id_emp, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 140, 210, 30));

        cb_state.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dalam Pengajuan", "Sedang Diproses", "Sudah Terselesaikan" }));
        panel_error_report.add(cb_state, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 220, 160, 30));

        lbl_simpan_error.setFont(new java.awt.Font("Gotham Light", 0, 20)); // NOI18N
        lbl_simpan_error.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/btn_simpan4.png"))); // NOI18N
        lbl_simpan_error.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_simpan_error.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_simpan_errorMouseClicked(evt);
            }
        });
        panel_error_report.add(lbl_simpan_error, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 470, -1, -1));

        lbl_reset_error.setFont(new java.awt.Font("Gotham Light", 0, 20)); // NOI18N
        lbl_reset_error.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/btn_reset2.png"))); // NOI18N
        lbl_reset_error.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_reset_error.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_reset_errorMouseClicked(evt);
            }
        });
        panel_error_report.add(lbl_reset_error, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 470, -1, -1));

        jLabel30.setFont(new java.awt.Font("Gotham Light", 0, 18)); // NOI18N
        jLabel30.setText("Penanggung Jawab");
        panel_error_report.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 280, -1, 20));

        jLabel29.setFont(new java.awt.Font("Gotham Light", 0, 18)); // NOI18N
        jLabel29.setText("Status");
        panel_error_report.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 190, -1, -1));

        jLabel28.setFont(new java.awt.Font("Gotham Light", 0, 18)); // NOI18N
        jLabel28.setText("ID Employee");
        panel_error_report.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 110, -1, 20));

        jLabel27.setFont(new java.awt.Font("Gotham Light", 0, 18)); // NOI18N
        jLabel27.setText("Error Description");
        panel_error_report.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, -1, -1));

        jLabel26.setFont(new java.awt.Font("Gotham Light", 0, 18)); // NOI18N
        jLabel26.setText("Error Title");
        panel_error_report.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, -1, -1));

        jLabel25.setFont(new java.awt.Font("Gotham Light", 0, 18)); // NOI18N
        jLabel25.setText("ID Submission");
        panel_error_report.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, -1, -1));

        jLabel24.setFont(new java.awt.Font("Gotham Bold", 0, 24)); // NOI18N
        jLabel24.setText("APPLICATION ERROR REPORT");
        panel_error_report.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 30, -1, -1));

        base_pend_eo2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/panel_main.png"))); // NOI18N
        panel_error_report.add(base_pend_eo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(panel_error_report, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 100, -1, -1));

        panel_atas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_header.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/Header2.png"))); // NOI18N
        lbl_header.setPreferredSize(new java.awt.Dimension(1200, 100));
        panel_atas.add(lbl_header, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 100));

        getContentPane().add(panel_atas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, -1));

        panel_bawah.setPreferredSize(new java.awt.Dimension(1200, 50));
        panel_bawah.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_footer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/footer.png"))); // NOI18N
        lbl_footer.setPreferredSize(new java.awt.Dimension(1200, 50));
        panel_bawah.add(lbl_footer, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(panel_bawah, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 650, 1200, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lbl_btn_listdaftareoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_listdaftareoMouseClicked
        // TODO add your handling code here:
        initModelEmpat();
    }//GEN-LAST:event_lbl_btn_listdaftareoMouseClicked

    private void lbl_simpan_errorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_simpan_errorMouseClicked
        // TODO add your handling code here:
        if (txt_error_title.getText().equals("")) {
            evt.consume();
            getToolkit().beep();
            JOptionPane.showMessageDialog(null, "Masukkan Error Title", "Information", JOptionPane.WARNING_MESSAGE);
            txt_error_title.requestFocus();
        } else if (txt_error_description.getText().equals("")) {
            evt.consume();
            getToolkit().beep();
            JOptionPane.showMessageDialog(null, "Isi Deskripsi Error/Kendala", "Information", JOptionPane.WARNING_MESSAGE);
            txt_error_description.requestFocus();
        } else if (txt_error_id_emp.getText().equals("")) {
            evt.consume();
            getToolkit().beep();
            JOptionPane.showMessageDialog(null, "Isi ID Karyawan", "Information", JOptionPane.WARNING_MESSAGE);
            txt_error_id_emp.requestFocus();
        } else {
            ErrorReport er = new ErrorReport();
            er.setError_id(txt_error_id.getText());
            er.setError_emp_id(txt_error_id_emp.getText());
            er.setError_title(txt_error_title.getText());
            er.setError_text(txt_error_description.getText());
            er.setError_pic(txt_pic.getText());
            er.setError_state((String) cb_state.getSelectedItem());

            try {
                int hasil = errorDao.updateError(er);
                if (hasil == 1) {
                    JOptionPane.showMessageDialog(null, "Error Data Save Success", "Save Success", JOptionPane.INFORMATION_MESSAGE);
                    ClearFormError();
                } else {
                    evt.consume();
                    getToolkit().beep();
                    JOptionPane.showMessageDialog(null, "Error Data Save Failed", "Save Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (RemoteException ex) {
                Logger.getLogger(Frm6_ErrorReport.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_lbl_simpan_errorMouseClicked

    private void lbl_reset_errorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_reset_errorMouseClicked
        // TODO add your handling code here:
        ClearFormError();
    }//GEN-LAST:event_lbl_reset_errorMouseClicked

    private void lbl_iconformMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_iconformMouseClicked
        // TODO add your handling code here:
        FO3_Menu Menu = new FO3_Menu();
        Menu.setVisible(true);
        dispose();
    }//GEN-LAST:event_lbl_iconformMouseClicked

    private void lbl_btn_kembaliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_kembaliMouseClicked
        // TODO add your handling code here:
        if (mode == 1) {
            FO3_Menu Menu = new FO3_Menu();
            Menu.setVisible(true);
            dispose();
        } else if (mode == 2 || mode == 3 || mode == 4) {
            initModelAwal();
        }
    }//GEN-LAST:event_lbl_btn_kembaliMouseClicked

    private void txt_picKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_picKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_picKeyPressed

    private void txt_error_idMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_error_idMouseClicked
        // TODO add your handling code here:
        Daftar_Error.pack();
        Daftar_Error.setLocationRelativeTo(this);
        Daftar_Error.setModal(true);
        Daftar_Error.setVisible(true);
        loadDaftarError();
    }//GEN-LAST:event_txt_error_idMouseClicked

    private void btn_pilih_errorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pilih_errorActionPerformed
        // TODO add your handling code here:
        int row = tbl_error.getSelectedRow();
        ErrorReport ER = recordError.get(row);

        txt_error_id.setText(ER.getError_id());
        txt_error_id_emp.setText(ER.getError_emp_id());
        txt_error_title.setText(ER.getError_title());
        txt_error_description.setText(ER.getError_text());
        txt_pic.setText(ER.getError_pic());
        cb_state.setSelectedItem(ER.getError_state());

        Daftar_Error.setVisible(false);
    }//GEN-LAST:event_btn_pilih_errorActionPerformed

    private void btn_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cariActionPerformed
        // TODO add your handling code here:
        Daftar_Error.pack();
        Daftar_Error.setLocationRelativeTo(this);
        Daftar_Error.setModal(true);
        Daftar_Error.setVisible(true);
        loadDaftarError();
    }//GEN-LAST:event_btn_cariActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Frm6_ErrorReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frm6_ErrorReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frm6_ErrorReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frm6_ErrorReport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frm6_ErrorReport().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog Daftar_Error;
    private javax.swing.JLabel base_kanan;
    private javax.swing.JLabel base_kiri;
    private javax.swing.JLabel base_panel_main;
    private javax.swing.JLabel base_pend_eo2;
    private javax.swing.JButton btn_cari;
    private javax.swing.JButton btn_pilih_error;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cb_state;
    private javax.swing.JPanel icon_form;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JLabel lbl_btn_kembali;
    private javax.swing.JLabel lbl_btn_listdaftareo;
    private javax.swing.JLabel lbl_footer;
    private javax.swing.JLabel lbl_header;
    private javax.swing.JLabel lbl_iconform;
    private javax.swing.JLabel lbl_reset_error;
    private javax.swing.JLabel lbl_simpan_error;
    private javax.swing.JPanel panel_atas;
    private javax.swing.JPanel panel_bawah;
    private javax.swing.JPanel panel_error_report;
    private javax.swing.JPanel panel_kanan;
    private javax.swing.JPanel panel_kiri;
    private javax.swing.JPanel panel_main;
    private javax.swing.JTable tbl_error;
    private javax.swing.JTextArea txt_error_description;
    private javax.swing.JTextField txt_error_id;
    private javax.swing.JTextField txt_error_id_emp;
    private javax.swing.JTextField txt_error_title;
    private javax.swing.JTextField txt_pic;
    // End of variables declaration//GEN-END:variables

}
