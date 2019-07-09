/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ae.ui.bo;

import ae.config.configuration;
import ae.dao.api.ILoginDao;
import ae.dao.api.IUserDao;
import ae.model.Login;
import ae.model.User;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileInputStream;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import static java.awt.image.ImageObserver.WIDTH;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

/**
 *
 * @author Aditya
 */
public class Frm2_Pendaftaran_User extends javax.swing.JFrame {

    /**
     * Creates new form Frm_SignUp_User
     */
    public Frm2_Pendaftaran_User() {
        initComponents();
        init_User_Dao();
        init_Login_Dao();
    }
    private IUserDao userDao = null;
    private ILoginDao loginDao = null;
    String gender = null;

    String filename = null;
    byte[] person_image = null;
    byte[] qr_image = null;
    private DefaultTableModel tabmode;
    int udm = 0, resol = 100, rot = 0;
    float mi = 0.000f, md = 0.000f, ms = 0.000f, min = 0.000f, tam = 5.00f;
    FileOutputStream fout;
    ByteArrayOutputStream out;

    private void init_User_Dao() {
        String url = "rmi://" + configuration.ip_server + ":1099/userDao";
        try {
            //ambil referensi dari remote objek yang ada di server
            userDao = (IUserDao) Naming.lookup(url);
        } catch (NotBoundException | MalformedURLException ex) {
            Logger.getLogger(Frm2_Pendaftaran_User.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            //Logger.getLogger(FrmSupplier.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Koneksi ke Service" + url + "[GAGAL], cek kembali app server", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void init_Login_Dao() {
        String url = "rmi://" + configuration.ip_server + ":1099/loginDao";
        try {
            //ambil referensi dari remote objek yang ada di server
            loginDao = (ILoginDao) Naming.lookup(url);
        } catch (NotBoundException | MalformedURLException ex) {
            Logger.getLogger(Frm2_Pendaftaran_User.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            //Logger.getLogger(FrmSupplier.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Koneksi ke Service" + url + "[GAGAL], cek kembali app server", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void GetQrCode() {
        if (txt_uid.getText().length() == 0) {
            return;
        }

        String t = txt_uid.getText();
        out = QRCode.from(t).withSize(141, 168).to(ImageType.PNG).stream();
        try {
//                        File images = new File(filename);
//            FileInputStream fis = new FileInputStream(images);
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            byte[] buf = new byte[10000];
//
//            for (int readNum; (readNum = fis.read(buf)) != -1;) {
//
//                bos.write(buf, 0, readNum);
//            }
//            person_image = bos.toByteArray();
            fout = new FileOutputStream(new File("temp.png"));
            fout.write(out.toByteArray());
            fout.flush();
            fout.close();

            BufferedImage miQr = ImageIO.read(new File("temp.png"));
            JLabel label = new JLabel(new ImageIcon(miQr));

            Graphics g = lbl_qrcode.getGraphics();
            g.drawImage(miQr, WIDTH, WIDTH, label);
            qr_image = out.toByteArray();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    private void ClearForm() {
        txt_nama.setText("");
        buttonGroup1.clearSelection();
        txt_noponsel.setText("");
        txt_email.setText("");
        txt_pwd.setText("");
        txt_uid.setText("");
        qr_image = null;
        person_image = null;
        lbl_foto.setIcon(null);
        lbl_qrcode.setIcon(null);
        txt_foto.setText("");
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
        btn_back = new javax.swing.JLabel();
        btn_reset = new javax.swing.JLabel();
        btn_save = new javax.swing.JLabel();
        btn_generate = new javax.swing.JButton();
        qrsave = new javax.swing.JButton();
        txt_pwd = new javax.swing.JPasswordField();
        txt_uid = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        txt_email = new javax.swing.JTextField();
        txt_noponsel = new javax.swing.JTextField();
        txt_nama = new javax.swing.JTextField();
        rb_pria = new javax.swing.JRadioButton();
        rb_wanita = new javax.swing.JRadioButton();
        panelqrcode = new javax.swing.JPanel();
        lbl_qrcode = new javax.swing.JLabel();
        panelfoto = new javax.swing.JPanel();
        lbl_foto = new javax.swing.JLabel();
        txt_foto = new javax.swing.JTextField();
        btn_attach = new javax.swing.JButton();
        Bg_Pendaftaran_User = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Amikom Event");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/btn_kembali2.png"))); // NOI18N
        btn_back.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_back.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_backMouseClicked(evt);
            }
        });
        getContentPane().add(btn_back, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 530, -1, -1));

        btn_reset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/btn_reset3.png"))); // NOI18N
        btn_reset.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_reset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_resetMouseClicked(evt);
            }
        });
        getContentPane().add(btn_reset, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 530, -1, -1));

        btn_save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/btn_simpan5.png"))); // NOI18N
        btn_save.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_save.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_saveMouseClicked(evt);
            }
        });
        getContentPane().add(btn_save, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 530, -1, -1));

        btn_generate.setText("GENERATE");
        btn_generate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_generateMouseClicked(evt);
            }
        });
        getContentPane().add(btn_generate, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 250, -1, 30));

        qrsave.setText("SAVE QR CODE");
        qrsave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                qrsaveActionPerformed(evt);
            }
        });
        getContentPane().add(qrsave, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 600, 140, 40));
        getContentPane().add(txt_pwd, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 350, 250, 30));
        getContentPane().add(txt_uid, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 250, 250, 30));
        getContentPane().add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 250, 250, 30));
        getContentPane().add(txt_email, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 580, 250, 30));
        getContentPane().add(txt_noponsel, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 460, 250, 30));
        getContentPane().add(txt_nama, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 250, 250, 30));

        buttonGroup1.add(rb_pria);
        rb_pria.setText("Pria");
        rb_pria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rb_priaMouseClicked(evt);
            }
        });
        getContentPane().add(rb_pria, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 350, -1, -1));

        buttonGroup1.add(rb_wanita);
        rb_wanita.setText("Wanita");
        rb_wanita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_wanitaActionPerformed(evt);
            }
        });
        getContentPane().add(rb_wanita, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 350, -1, -1));

        panelqrcode.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_qrcode.setBackground(new java.awt.Color(255, 255, 255));
        lbl_qrcode.setForeground(new java.awt.Color(255, 51, 204));
        panelqrcode.add(lbl_qrcode, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 141, 168));

        getContentPane().add(panelqrcode, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 450, 160, 190));

        lbl_foto.setBackground(new java.awt.Color(255, 255, 255));
        lbl_foto.setForeground(new java.awt.Color(255, 51, 204));

        javax.swing.GroupLayout panelfotoLayout = new javax.swing.GroupLayout(panelfoto);
        panelfoto.setLayout(panelfotoLayout);
        panelfotoLayout.setHorizontalGroup(
            panelfotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelfotoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_foto, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelfotoLayout.setVerticalGroup(
            panelfotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelfotoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_foto, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(panelfoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 250, 160, 190));

        txt_foto.setFont(new java.awt.Font("Gotham Light", 0, 14)); // NOI18N
        txt_foto.setEnabled(false);
        getContentPane().add(txt_foto, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 290, 140, 30));

        btn_attach.setText("ATTACH");
        btn_attach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_attachActionPerformed(evt);
            }
        });
        getContentPane().add(btn_attach, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 330, 100, 40));

        Bg_Pendaftaran_User.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/Bg_Pendaftaran_User_New.png"))); // NOI18N
        getContentPane().add(Bg_Pendaftaran_User, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_attachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_attachActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setFileFilter(new FileNameExtensionFilter("jpg|png|bmp", "jpg", "png", "bmp"));
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();

        filename = f.getAbsolutePath();
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(filename).getImage().getScaledInstance(lbl_foto.getWidth(), lbl_foto.getHeight(), Image.SCALE_DEFAULT));
        txt_foto.setText(filename);
        lbl_foto.setIcon(imageIcon);
        try {
            File images = new File(filename);
            FileInputStream fis = new FileInputStream(images);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[10000];

            for (int readNum; (readNum = fis.read(buf)) != -1;) {

                bos.write(buf, 0, readNum);
            }
            person_image = bos.toByteArray();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_btn_attachActionPerformed

    private void qrsaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_qrsaveActionPerformed
        JFileChooser fc = new JFileChooser();
        int rv = fc.showDialog(lbl_qrcode, null);
        if (rv == JFileChooser.APPROVE_OPTION) {
            try {
                String ruta = fc.getSelectedFile().getAbsolutePath() + ".png";
                fout = new FileOutputStream(new File(ruta));
                fout.write(out.toByteArray());
                fout.flush();
                fout.close();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }//GEN-LAST:event_qrsaveActionPerformed

    private void btn_generateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_generateMouseClicked
        // TODO add your handling code here:
        GetQrCode();
    }//GEN-LAST:event_btn_generateMouseClicked

    private void btn_backMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_backMouseClicked
        // TODO add your handling code here:
        int reply = JOptionPane.showConfirmDialog(null, "Apakah Anda Ingin Kembali Ke Menu ?", "Information", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            Frm1_Menu Menu = new Frm1_Menu();
            Menu.setVisible(true);
            dispose();
        }
    }//GEN-LAST:event_btn_backMouseClicked

    private void btn_saveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_saveMouseClicked
        // TODO add your handling code here:
        if (rb_pria.isSelected() == false && rb_wanita.isSelected() == false) {
            evt.consume();
            getToolkit().beep();
            JOptionPane.showMessageDialog(null, "Pilih Jenis Kelamin", "Information", JOptionPane.WARNING_MESSAGE);
        } else if (txt_nama.getText().equals("")) {
            evt.consume();
            getToolkit().beep();
            JOptionPane.showMessageDialog(null, "Isi Nama Anda", "Information", JOptionPane.WARNING_MESSAGE);
            txt_nama.requestFocus();
        } else if (txt_noponsel.getText().equals("")) {
            evt.consume();
            getToolkit().beep();
            JOptionPane.showMessageDialog(null, "Isi Nomor Ponsel Anda", "Information", JOptionPane.WARNING_MESSAGE);
            txt_noponsel.requestFocus();
        } else if (txt_email.getText().equals("")) {
            evt.consume();
            getToolkit().beep();
            JOptionPane.showMessageDialog(null, "Isi Alamat Email Anda", "Information", JOptionPane.WARNING_MESSAGE);
            txt_email.requestFocus();
        } else if (qr_image == null) {
            evt.consume();
            getToolkit().beep();
            JOptionPane.showMessageDialog(null, "Tekan Generate QR Code Event Organizer", "Information", JOptionPane.INFORMATION_MESSAGE);
            btn_generate.requestFocus();
        } else if (person_image == null) {
            evt.consume();
            getToolkit().beep();
            JOptionPane.showMessageDialog(null, "Tambahkan Foto Anda", "Information", JOptionPane.INFORMATION_MESSAGE);
            btn_attach.requestFocus();
        } else {
            User u = new User();
            u.setEmp_id(txt_uid.getText());
            u.setEmp_name(txt_nama.getText());
            u.setEmp_gender(gender);
            u.setEmp_noponsel(txt_noponsel.getText());
            u.setEmp_email(txt_email.getText());
            u.setEmp_foto(person_image);
            u.setEmp_qrcode(qr_image);

            Login login = new Login();
            login.setUsername(txt_uid.getText());
            login.setPassword(txt_pwd.getText());
            try {
                int hasil = userDao.saveDataEmp(u);
                int hasil2 = userDao.saveDataEmpLogin(login);

                System.out.println("hasil2");
                if (hasil == 1 && hasil2 == 1) {
                    JOptionPane.showMessageDialog(null, "Save Data Success", "Save Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    evt.consume();
                    getToolkit().beep();
                    JOptionPane.showMessageDialog(null, "Save Data Failed", "Save Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (RemoteException ex) {
                Logger.getLogger(Frm2_Pendaftaran_User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btn_saveMouseClicked

    private void btn_resetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_resetMouseClicked
        // TODO add your handling code here:
        ClearForm();
    }//GEN-LAST:event_btn_resetMouseClicked

    private void rb_priaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rb_priaMouseClicked
        // TODO add your handling code here:
        gender = "Pria";
    }//GEN-LAST:event_rb_priaMouseClicked

    private void rb_wanitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_wanitaActionPerformed
        // TODO add your handling code here:
        gender = "Wanita";
    }//GEN-LAST:event_rb_wanitaActionPerformed

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
            java.util.logging.Logger.getLogger(Frm2_Pendaftaran_User.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frm2_Pendaftaran_User.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frm2_Pendaftaran_User.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frm2_Pendaftaran_User.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frm2_Pendaftaran_User().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Bg_Pendaftaran_User;
    private javax.swing.JButton btn_attach;
    private javax.swing.JLabel btn_back;
    private javax.swing.JButton btn_generate;
    private javax.swing.JLabel btn_reset;
    private javax.swing.JLabel btn_save;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JLabel lbl_foto;
    private javax.swing.JLabel lbl_qrcode;
    private javax.swing.JPanel panelfoto;
    private javax.swing.JPanel panelqrcode;
    private javax.swing.JButton qrsave;
    private javax.swing.JRadioButton rb_pria;
    private javax.swing.JRadioButton rb_wanita;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_foto;
    private javax.swing.JTextField txt_nama;
    private javax.swing.JTextField txt_noponsel;
    private javax.swing.JPasswordField txt_pwd;
    private javax.swing.JTextField txt_uid;
    // End of variables declaration//GEN-END:variables
}
