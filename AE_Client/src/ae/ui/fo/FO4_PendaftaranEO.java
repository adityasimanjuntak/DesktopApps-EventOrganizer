/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ae.ui.fo;

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
public class FO4_PendaftaranEO extends javax.swing.JFrame {

    /**
     * Creates new form FO4_PendaftaranEO
     */
    public FO4_PendaftaranEO() {
        initComponents();
        init_EventOrganizer_Dao();
        init_ErrorReport_Dao();

        initModelAwal();
    }

    int mode;
    int udm = 0, resol = 100, rot = 0;
    float mi = 0.000f, md = 0.000f, ms = 0.000f, min = 0.000f, tam = 5.00f;
    byte[] qr_image = null;

    FileOutputStream fout;
    ByteArrayOutputStream out;

    private IEventOrganizerDao eventorganizerDao = null;
    private IErrorReportDao errorDao = null;
    private String instansi = null;

    private final Object[] DaftarEOcolumNames = {"ID EO", "Nama EO", "Penanggung Jawab", "No. Ponsel", "E-mail", "Website"};
    private final DefaultTableModel tableModelEo = new DefaultTableModel();
    private List<EventOrganizer> recordEo = new ArrayList<EventOrganizer>();

    private void init_EventOrganizer_Dao() {
        String url = "rmi://" + configuration.ip_server + ":1099/eoDao";
        try {
            //ambil referensi dari remote objek yang ada di server
            eventorganizerDao = (IEventOrganizerDao) Naming.lookup(url);
        } catch (NotBoundException | MalformedURLException ex) {
            Logger.getLogger(FO4_PendaftaranEO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            //Logger.getLogger(FrmSupplier.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Koneksi ke Service" + url + "[GAGAL], cek kembali app server", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void init_EventOrganizer_ID() {
        EventOrganizer eo = new EventOrganizer();
        try {
            String hasil = eventorganizerDao.GenerateIDEO(eo);
            if (hasil == null) {
                //do something
            } else {
                txt_ID_eo.setText(hasil);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(FO4_PendaftaranEO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void init_ErrorReport_Dao() {
        String url = "rmi://" + configuration.ip_server + ":1099/errorDao";
        try {
            //ambil referensi dari remote objek yang ada di server
            errorDao = (IErrorReportDao) Naming.lookup(url);
        } catch (NotBoundException | MalformedURLException ex) {
            Logger.getLogger(FO4_PendaftaranEO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            //Logger.getLogger(FrmSupplier.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Koneksi ke Service" + url + "[GAGAL], cek kembali app server", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void init_ErrorReport_ID() {
        ErrorReport er = new ErrorReport();
        try {
            String hasil = errorDao.GenerateIDError(er);
            if (hasil == null) {
                //do something
            } else {
                txt_error_id.setText(hasil);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(FO4_PendaftaranEO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initTableDaftarEO() {
        //set header table
        tableModelEo.setColumnIdentifiers(DaftarEOcolumNames);
        tbl_daftar_eo.setModel(tableModelEo);
        tbl_daftar_eo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void loadDaftarEo() {
        try {
            // reset data di tabel
            tableModelEo.setRowCount(0);
            // mengambil data barang dari server
            // kemudian menyimpannya ke objek list
            recordEo = eventorganizerDao.getAll();
            // ekstrak data barang yg ada di dalam objek list
            // kemudian menampilkannya ke objek tabel
            for (EventOrganizer eo : recordEo) {
                // ambil nomor urut terakhir
                Object[] objects = new Object[DaftarEOcolumNames.length];
                objects[0] = eo.getEo_id();
                objects[1] = eo.getEo_nama();
                objects[2] = eo.getEo_nama_pic();
                objects[3] = eo.getEo_ponsel();
                objects[4] = eo.getEo_email();
                objects[5] = eo.getEo_website();
                // tambahkan data barang ke dalam tabel
                tableModelEo.addRow(objects);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(FO4_PendaftaranEO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadDaftarEobyName() {
        try {
            // reset data di tabel
            tableModelEo.setRowCount(0);
            // mengambil data barang dari server
            // kemudian menyimpannya ke objek list
            recordEo = eventorganizerDao.getByName(txt_cari_eo.getText());
            // ekstrak data barang yg ada di dalam objek list
            // kemudian menampilkannya ke objek tabel
            for (EventOrganizer eo : recordEo) {
                // ambil nomor urut terakhir
                Object[] objects = new Object[DaftarEOcolumNames.length];
                objects[0] = eo.getEo_id();
                objects[1] = eo.getEo_nama();
                objects[2] = eo.getEo_nama_pic();
                objects[3] = eo.getEo_ponsel();
                objects[4] = eo.getEo_email();
                objects[5] = eo.getEo_website();
                // tambahkan data barang ke dalam tabel
                tableModelEo.addRow(objects);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(FO4_PendaftaranEO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initModelAwal() {
        mode = 1;
        panel_main.setVisible(true);
        panel_pendaftaran_eo.setVisible(false);
        panel_daftarlist_eo.setVisible(false);
        panel_error_report.setVisible(false);
    }

    private void initModelDua() {
        mode = 2;
        panel_main.setVisible(false);
        panel_pendaftaran_eo.setVisible(true);
        panel_daftarlist_eo.setVisible(false);
        panel_error_report.setVisible(false);
        init_EventOrganizer_ID();
    }

    private void initModelTiga() {
        mode = 3;
        initTableDaftarEO();
        init_EventOrganizer_Dao();
        loadDaftarEo();
        panel_main.setVisible(false);
        panel_pendaftaran_eo.setVisible(false);
        panel_daftarlist_eo.setVisible(true);
        panel_error_report.setVisible(false);
        txt_cari_eo.requestFocus();
    }

    private void initModelEmpat() {
        mode = 4;
        init_ErrorReport_ID();
        panel_main.setVisible(false);
        panel_pendaftaran_eo.setVisible(false);
        panel_daftarlist_eo.setVisible(false);
        panel_error_report.setVisible(true);
    }

    private void initGetQrCode() {
        if (txt_ID_eo.getText().length() == 0) {
            return;
        }

        String t = txt_ID_eo.getText();
        out = QRCode.from(t).withSize(141, 168).to(ImageType.PNG).stream();
        try {
            fout = new FileOutputStream(new File("temp.png"));
            fout.write(out.toByteArray());
            fout.flush();
            fout.close();

            BufferedImage miQr = ImageIO.read(new File("temp.png"));
            JLabel label = new JLabel(new ImageIcon(miQr));
            lbl_qrcode.setVisible(true);
            Graphics g = lbl_qrcode.getGraphics();
            g.drawImage(miQr, WIDTH, WIDTH, label);
            qr_image = out.toByteArray();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    private void ClearFormDaftar() {
        rb_amk.setSelected(false);
        rb_namk.setSelected(false);
        txt_nama_eo.setText("");
        txt_nama_pic_eo.setText("");
        txt_alamat_eo.setText("");
        txt_alamat_eo.setEnabled(true);
        txt_ponsel_eo.setText("");
        txt_mail_eo.setText("");
        txt_website_eo.setText("");
        lbl_qrcode.setVisible(false);
        init_EventOrganizer_ID();
        txt_nama_eo.requestFocus();
    }

    private void ClearFormError() {
        txt_error_id.setText("");
        txt_error_id_emp.setText("");
        txt_error_title.setText("");
        txt_error_description.setText("");
        init_ErrorReport_ID();
        txt_error_title.requestFocus();
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
        panel_kanan = new javax.swing.JPanel();
        lbl_btn_kembali = new javax.swing.JLabel();
        lbl_btn_formerror = new javax.swing.JLabel();
        base_kanan = new javax.swing.JLabel();
        panel_kiri = new javax.swing.JPanel();
        lbl_btn_listdaftareo = new javax.swing.JLabel();
        lbl_btn_formdaftareo = new javax.swing.JLabel();
        base_kiri = new javax.swing.JLabel();
        panel_main = new javax.swing.JPanel();
        icon_form = new javax.swing.JPanel();
        lbl_iconform = new javax.swing.JLabel();
        base_panel_main = new javax.swing.JLabel();
        panel_daftarlist_eo = new javax.swing.JPanel();
        lbl_btn_cari = new javax.swing.JLabel();
        lbl_reset = new javax.swing.JLabel();
        txt_cari_eo = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_daftar_eo = new javax.swing.JTable();
        jLabel22 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        base_pend_eo1 = new javax.swing.JLabel();
        panel_error_report = new javax.swing.JPanel();
        btn_chatme = new javax.swing.JLabel();
        txt_error_id = new javax.swing.JTextField();
        txt_error_title = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        txt_error_description = new javax.swing.JTextArea();
        txt_error_id_emp = new javax.swing.JTextField();
        cb_state = new javax.swing.JComboBox<>();
        lbl_simpan_error = new javax.swing.JLabel();
        lbl_reset_error = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        base_pend_eo2 = new javax.swing.JLabel();
        panel_pendaftaran_eo = new javax.swing.JPanel();
        lbl_btn_reset = new javax.swing.JLabel();
        lbl_btn_simpan = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txt_ponsel_eo = new javax.swing.JTextField();
        txt_mail_eo = new javax.swing.JTextField();
        txt_website_eo = new javax.swing.JTextField();
        txt_nama_eo = new javax.swing.JTextField();
        txt_nama_pic_eo = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_alamat_eo = new javax.swing.JTextPane();
        jLabel10 = new javax.swing.JLabel();
        rb_namk = new javax.swing.JRadioButton();
        rb_amk = new javax.swing.JRadioButton();
        generate = new javax.swing.JButton();
        panelqrcode = new javax.swing.JPanel();
        lbl_qrcode = new javax.swing.JLabel();
        qrsave = new javax.swing.JButton();
        txt_ID_eo = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        base_pend_eo = new javax.swing.JLabel();
        panel_atas = new javax.swing.JPanel();
        lbl_header = new javax.swing.JLabel();
        panel_bawah = new javax.swing.JPanel();
        lbl_footer = new javax.swing.JLabel();

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
        panel_kanan.add(lbl_btn_kembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 310, -1, -1));

        lbl_btn_formerror.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/btn_app_error_report.png"))); // NOI18N
        lbl_btn_formerror.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_btn_formerror.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_btn_formerrorMouseClicked(evt);
            }
        });
        panel_kanan.add(lbl_btn_formerror, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, -1, -1));

        base_kanan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/panel_kanan.png"))); // NOI18N
        panel_kanan.add(base_kanan, new org.netbeans.lib.awtextra.AbsoluteConstraints(-9, 0, 210, -1));

        getContentPane().add(panel_kanan, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 100, -1, -1));

        panel_kiri.setPreferredSize(new java.awt.Dimension(200, 550));
        panel_kiri.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_btn_listdaftareo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/DaftarEventB1.png"))); // NOI18N
        lbl_btn_listdaftareo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_btn_listdaftareo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_btn_listdaftareoMouseClicked(evt);
            }
        });
        panel_kiri.add(lbl_btn_listdaftareo, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 310, -1, -1));

        lbl_btn_formdaftareo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/PendaftaranEO_B1.png"))); // NOI18N
        lbl_btn_formdaftareo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_btn_formdaftareo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_btn_formdaftareoMouseClicked(evt);
            }
        });
        panel_kiri.add(lbl_btn_formdaftareo, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, -1, -1));

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

        panel_daftarlist_eo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_btn_cari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/btn_cari.png"))); // NOI18N
        lbl_btn_cari.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_btn_cari.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_btn_cariMouseClicked(evt);
            }
        });
        panel_daftarlist_eo.add(lbl_btn_cari, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 340, -1, -1));

        lbl_reset.setText("RESET");
        lbl_reset.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_reset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_resetMouseClicked(evt);
            }
        });
        panel_daftarlist_eo.add(lbl_reset, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 360, -1, -1));
        panel_daftarlist_eo.add(txt_cari_eo, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 380, 470, 30));

        tbl_daftar_eo.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tbl_daftar_eo);

        panel_daftarlist_eo.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, 720, 240));

        jLabel22.setFont(new java.awt.Font("Gotham Medium", 0, 18)); // NOI18N
        jLabel22.setText("CARI EVENT ORGANIZER");
        panel_daftarlist_eo.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 350, 320, -1));

        jLabel21.setFont(new java.awt.Font("Gotham Bold", 0, 24)); // NOI18N
        jLabel21.setText("DAFTAR / LIST EVENT ORGANIZER");
        panel_daftarlist_eo.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 30, -1, -1));

        base_pend_eo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/panel_main.png"))); // NOI18N
        panel_daftarlist_eo.add(base_pend_eo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(panel_daftarlist_eo, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 100, -1, -1));

        panel_error_report.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_chatme.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/btn_chat_me_rsz.png"))); // NOI18N
        btn_chatme.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_chatme.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_chatmeMouseClicked(evt);
            }
        });
        panel_error_report.add(btn_chatme, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 360, -1, -1));

        txt_error_id.setFont(new java.awt.Font("Gotham Light", 0, 14)); // NOI18N
        txt_error_id.setEnabled(false);
        panel_error_report.add(txt_error_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, 210, 30));

        txt_error_title.setFont(new java.awt.Font("Gotham Light", 0, 14)); // NOI18N
        panel_error_report.add(txt_error_title, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 220, 210, 30));

        txt_error_description.setColumns(20);
        txt_error_description.setRows(5);
        jScrollPane3.setViewportView(txt_error_description);

        panel_error_report.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 310, 310, 170));

        txt_error_id_emp.setFont(new java.awt.Font("Gotham Light", 0, 14)); // NOI18N
        panel_error_report.add(txt_error_id_emp, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 140, 210, 30));

        cb_state.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dalam Pengajuan", "Sedang Diproses", "Sudah Terselesaikan" }));
        cb_state.setEnabled(false);
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

        panel_pendaftaran_eo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_btn_reset.setFont(new java.awt.Font("Gotham Light", 0, 20)); // NOI18N
        lbl_btn_reset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/btn_reset2.png"))); // NOI18N
        lbl_btn_reset.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_btn_reset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_btn_resetMouseClicked(evt);
            }
        });
        panel_pendaftaran_eo.add(lbl_btn_reset, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 470, -1, -1));

        lbl_btn_simpan.setFont(new java.awt.Font("Gotham Light", 0, 20)); // NOI18N
        lbl_btn_simpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/btn_simpan4.png"))); // NOI18N
        lbl_btn_simpan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_btn_simpan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_btn_simpanMouseClicked(evt);
            }
        });
        panel_pendaftaran_eo.add(lbl_btn_simpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 470, -1, -1));

        jLabel13.setFont(new java.awt.Font("Gotham Light", 0, 20)); // NOI18N
        jLabel13.setText("EO QR Code");
        panel_pendaftaran_eo.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 90, -1, -1));

        jLabel12.setFont(new java.awt.Font("Gotham Light", 0, 20)); // NOI18N
        jLabel12.setText("Website");
        panel_pendaftaran_eo.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 230, -1, -1));

        jLabel11.setFont(new java.awt.Font("Gotham Light", 0, 20)); // NOI18N
        jLabel11.setText("E-Mail");
        panel_pendaftaran_eo.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 160, -1, -1));

        txt_ponsel_eo.setFont(new java.awt.Font("Gotham Light", 0, 14)); // NOI18N
        txt_ponsel_eo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_ponsel_eoKeyPressed(evt);
            }
        });
        panel_pendaftaran_eo.add(txt_ponsel_eo, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 120, 240, 30));

        txt_mail_eo.setFont(new java.awt.Font("Gotham Light", 0, 14)); // NOI18N
        txt_mail_eo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_mail_eoKeyPressed(evt);
            }
        });
        panel_pendaftaran_eo.add(txt_mail_eo, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 190, 240, 30));

        txt_website_eo.setFont(new java.awt.Font("Gotham Light", 0, 14)); // NOI18N
        panel_pendaftaran_eo.add(txt_website_eo, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 260, 240, 30));

        txt_nama_eo.setFont(new java.awt.Font("Gotham Light", 0, 14)); // NOI18N
        txt_nama_eo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_nama_eoKeyPressed(evt);
            }
        });
        panel_pendaftaran_eo.add(txt_nama_eo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 240, 30));

        txt_nama_pic_eo.setFont(new java.awt.Font("Gotham Light", 0, 14)); // NOI18N
        txt_nama_pic_eo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_nama_pic_eoKeyPressed(evt);
            }
        });
        panel_pendaftaran_eo.add(txt_nama_pic_eo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, 240, 30));

        txt_alamat_eo.setFont(new java.awt.Font("Gotham Light", 0, 14)); // NOI18N
        txt_alamat_eo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_alamat_eoKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(txt_alamat_eo);

        panel_pendaftaran_eo.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 400, 240, 110));

        jLabel10.setFont(new java.awt.Font("Gotham Light", 0, 20)); // NOI18N
        jLabel10.setText("Nomor Ponsel");
        panel_pendaftaran_eo.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 90, -1, -1));

        buttonGroup1.add(rb_namk);
        rb_namk.setText("NON-AMIKOM");
        rb_namk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rb_namkMouseClicked(evt);
            }
        });
        panel_pendaftaran_eo.add(rb_namk, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 190, -1, -1));

        buttonGroup1.add(rb_amk);
        rb_amk.setText("AMIKOM");
        rb_amk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rb_amkActionPerformed(evt);
            }
        });
        panel_pendaftaran_eo.add(rb_amk, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, -1, -1));

        generate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/generateqr.png"))); // NOI18N
        generate.setToolTipText("Generate QR Code");
        generate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        generate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateActionPerformed(evt);
            }
        });
        panel_pendaftaran_eo.add(generate, new org.netbeans.lib.awtextra.AbsoluteConstraints(275, 115, 60, 40));

        panelqrcode.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_qrcode.setBackground(new java.awt.Color(255, 255, 255));
        lbl_qrcode.setForeground(new java.awt.Color(255, 51, 204));
        panelqrcode.add(lbl_qrcode, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 141, 168));

        panel_pendaftaran_eo.add(panelqrcode, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 120, 160, 190));

        qrsave.setFont(new java.awt.Font("Gotham", 0, 10)); // NOI18N
        qrsave.setText("SAVE QR CODE");
        qrsave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                qrsaveActionPerformed(evt);
            }
        });
        panel_pendaftaran_eo.add(qrsave, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 320, 140, 40));

        txt_ID_eo.setFont(new java.awt.Font("Gotham Light", 0, 14)); // NOI18N
        txt_ID_eo.setEnabled(false);
        panel_pendaftaran_eo.add(txt_ID_eo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 240, 30));

        jLabel8.setFont(new java.awt.Font("Gotham Light", 0, 20)); // NOI18N
        jLabel8.setText("Nama Penanggung Jawab");
        panel_pendaftaran_eo.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, -1, -1));

        jLabel6.setFont(new java.awt.Font("Gotham Light", 0, 20)); // NOI18N
        jLabel6.setText("Institusi");
        panel_pendaftaran_eo.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, -1, -1));

        jLabel7.setFont(new java.awt.Font("Gotham Light", 0, 20)); // NOI18N
        jLabel7.setText("Alamat");
        panel_pendaftaran_eo.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 370, -1, -1));

        jLabel5.setFont(new java.awt.Font("Gotham Light", 0, 20)); // NOI18N
        jLabel5.setText("Nama Event Organizer");
        panel_pendaftaran_eo.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, -1, -1));

        jLabel3.setFont(new java.awt.Font("Gotham Light", 0, 20)); // NOI18N
        jLabel3.setText("Event Organizer ID");
        panel_pendaftaran_eo.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, -1, -1));

        jLabel2.setFont(new java.awt.Font("Gotham Bold", 0, 24)); // NOI18N
        jLabel2.setText("PENDAFTARAN EVENT ORGANIZER");
        panel_pendaftaran_eo.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 30, -1, -1));

        base_pend_eo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/panel_main.png"))); // NOI18N
        panel_pendaftaran_eo.add(base_pend_eo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(panel_pendaftaran_eo, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 100, -1, -1));

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

    private void lbl_btn_formdaftareoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_formdaftareoMouseClicked
        // TODO add your handling code here:
        initModelDua();
    }//GEN-LAST:event_lbl_btn_formdaftareoMouseClicked

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

    private void generateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateActionPerformed
        // TODO add your handling code here:
        initGetQrCode();
    }//GEN-LAST:event_generateActionPerformed

    private void lbl_btn_simpanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_simpanMouseClicked
        // TODO add your handling code here:
        if (rb_amk.isSelected() == false && rb_namk.isSelected() == false) {
            evt.consume();
            getToolkit().beep();
            JOptionPane.showMessageDialog(null, "Pilih Institusi", "Information", JOptionPane.WARNING_MESSAGE);
        } else if (txt_nama_eo.getText().equals("")) {
            evt.consume();
            getToolkit().beep();
            JOptionPane.showMessageDialog(null, "Isi Nama Event Organnizer", "Information", JOptionPane.WARNING_MESSAGE);
            txt_nama_eo.requestFocus();
        } else if (txt_nama_pic_eo.getText().equals("")) {
            evt.consume();
            getToolkit().beep();
            JOptionPane.showMessageDialog(null, "Isi Nama Penanggung Jawab Event Organizer", "Information", JOptionPane.WARNING_MESSAGE);
            txt_nama_pic_eo.requestFocus();
        } else if (txt_alamat_eo.getText().equals("")) {
            evt.consume();
            getToolkit().beep();
            JOptionPane.showMessageDialog(null, "Isi Alamat Event Organnizer", "Information", JOptionPane.WARNING_MESSAGE);
            txt_alamat_eo.requestFocus();
        } else if (txt_ponsel_eo.getText().equals("")) {
            evt.consume();
            getToolkit().beep();
            JOptionPane.showMessageDialog(null, "Isi Nomor Ponsel Event Organnizer", "Information", JOptionPane.WARNING_MESSAGE);
            txt_ponsel_eo.requestFocus();
        } else if (txt_mail_eo.getText().equals("")) {
            evt.consume();
            getToolkit().beep();
            JOptionPane.showMessageDialog(null, "Isi Alamat Email Event Organnizer", "Information", JOptionPane.WARNING_MESSAGE);
            txt_mail_eo.requestFocus();
        } else if (txt_website_eo.getText().equals("")) {
            evt.consume();
            getToolkit().beep();
            JOptionPane.showMessageDialog(null, "Isi Alamat Website Event Organnizer", "Information", JOptionPane.WARNING_MESSAGE);
            txt_website_eo.requestFocus();
        } else if (qr_image == null) {
            evt.consume();
            getToolkit().beep();
            JOptionPane.showMessageDialog(null, "Tekan Generate QR Code Event Organizer", "Information", JOptionPane.INFORMATION_MESSAGE);
        } else {
            EventOrganizer eo = new EventOrganizer();
            eo.setEo_id(txt_ID_eo.getText());
            eo.setEo_instansi(instansi);
            eo.setEo_nama(txt_nama_eo.getText());
            eo.setEo_nama_pic(txt_nama_pic_eo.getText());
            eo.setEo_alamat(txt_alamat_eo.getText());
            eo.setEo_ponsel(txt_ponsel_eo.getText());
            eo.setEo_email(txt_mail_eo.getText());
            eo.setEo_website(txt_website_eo.getText());
            eo.setEo_qr(qr_image);
            try {
                int hasil = eventorganizerDao.saveDataEo(eo);
                if (hasil == 1) {
                    JOptionPane.showMessageDialog(null, "Save Data Success", "Save Success", JOptionPane.INFORMATION_MESSAGE);
                    ClearFormDaftar();
                } else {
                    evt.consume();
                    getToolkit().beep();
                    JOptionPane.showMessageDialog(null, "Save Data Failed", "Save Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (RemoteException ex) {
                Logger.getLogger(FO4_PendaftaranEO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_lbl_btn_simpanMouseClicked

    private void rb_amkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rb_amkActionPerformed
        // TODO add your handling code here:
        instansi = "Amikom";
        txt_alamat_eo.setEnabled(false);
        txt_alamat_eo.setText("Universitas Amikom Yogyakarta \nJalan RingRoad Utara, CondongCatur, Sleman, Yogyakarta");
    }//GEN-LAST:event_rb_amkActionPerformed

    private void rb_namkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rb_namkMouseClicked
        // TODO add your handling code here:
        instansi = "Non-Amikom";
        txt_alamat_eo.setEnabled(true);
        txt_alamat_eo.setText("");
    }//GEN-LAST:event_rb_namkMouseClicked

    private void lbl_btn_resetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_resetMouseClicked
        // TODO add your handling code here:
        ClearFormDaftar();
    }//GEN-LAST:event_lbl_btn_resetMouseClicked

    private void txt_nama_eoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nama_eoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_nama_pic_eo.requestFocus();
        }
    }//GEN-LAST:event_txt_nama_eoKeyPressed

    private void txt_nama_pic_eoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nama_pic_eoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_alamat_eo.requestFocus();
        }
    }//GEN-LAST:event_txt_nama_pic_eoKeyPressed

    private void txt_alamat_eoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_alamat_eoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_ponsel_eo.requestFocus();
        }
    }//GEN-LAST:event_txt_alamat_eoKeyPressed

    private void txt_ponsel_eoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_ponsel_eoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_mail_eo.requestFocus();
        }
    }//GEN-LAST:event_txt_ponsel_eoKeyPressed

    private void txt_mail_eoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_mail_eoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_website_eo.requestFocus();
        }
    }//GEN-LAST:event_txt_mail_eoKeyPressed

    private void lbl_btn_listdaftareoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_listdaftareoMouseClicked
        // TODO add your handling code here:
        initModelTiga();
    }//GEN-LAST:event_lbl_btn_listdaftareoMouseClicked

    private void lbl_btn_cariMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_cariMouseClicked
        // TODO add your handling code here:
        if (txt_cari_eo.getText().equals("")) {
            evt.consume();
            getToolkit().beep();
            JOptionPane.showMessageDialog(null, "Isi Kolom Pencarian", "Information", JOptionPane.INFORMATION_MESSAGE);
        } else {
            loadDaftarEobyName();
        }
    }//GEN-LAST:event_lbl_btn_cariMouseClicked

    private void lbl_resetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_resetMouseClicked
        // TODO add your handling code here:
        loadDaftarEo();
        txt_cari_eo.setText("");
        txt_cari_eo.requestFocus();
    }//GEN-LAST:event_lbl_resetMouseClicked

    private void lbl_btn_formerrorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_formerrorMouseClicked
        // TODO add your handling code here:
        initModelEmpat();
    }//GEN-LAST:event_lbl_btn_formerrorMouseClicked

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
            er.setError_state((String) cb_state.getSelectedItem());
            try {
                int hasil = errorDao.saveDataError(er);
                if (hasil == 1) {
                    JOptionPane.showMessageDialog(null, "Error Data Save Success", "Save Success", JOptionPane.INFORMATION_MESSAGE);
                    ClearFormError();
                } else {
                    evt.consume();
                    getToolkit().beep();
                    JOptionPane.showMessageDialog(null, "Error Data Save Failed", "Save Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (RemoteException ex) {
                Logger.getLogger(FO4_PendaftaranEO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_lbl_simpan_errorMouseClicked

    private void lbl_reset_errorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_reset_errorMouseClicked
        // TODO add your handling code here:
        ClearFormError();
    }//GEN-LAST:event_lbl_reset_errorMouseClicked

    private void lbl_btn_kembaliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_kembaliMouseClicked
        // TODO add your handling code here:
        if (mode == 1) {
            FO3_Menu Menu = new FO3_Menu();
            Menu.setVisible(true);
            dispose();
        }
        else if (mode == 2 || mode == 3 || mode == 4) {
            initModelAwal();
        }

    }//GEN-LAST:event_lbl_btn_kembaliMouseClicked

    private void lbl_iconformMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_iconformMouseClicked
        // TODO add your handling code here:
        FO3_Menu Menu = new FO3_Menu();
        Menu.setVisible(true);
        dispose();
    }//GEN-LAST:event_lbl_iconformMouseClicked

    private void btn_chatmeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_chatmeMouseClicked
        // TODO add your handling code here:
        Extension_Chat_Client ECC = new Extension_Chat_Client();
        ECC.setVisible(true);
    }//GEN-LAST:event_btn_chatmeMouseClicked

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
            java.util.logging.Logger.getLogger(FO4_PendaftaranEO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FO4_PendaftaranEO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FO4_PendaftaranEO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FO4_PendaftaranEO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FO4_PendaftaranEO().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel base_kanan;
    private javax.swing.JLabel base_kiri;
    private javax.swing.JLabel base_panel_main;
    private javax.swing.JLabel base_pend_eo;
    private javax.swing.JLabel base_pend_eo1;
    private javax.swing.JLabel base_pend_eo2;
    private javax.swing.JLabel btn_chatme;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cb_state;
    private javax.swing.JButton generate;
    private javax.swing.JPanel icon_form;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lbl_btn_cari;
    private javax.swing.JLabel lbl_btn_formdaftareo;
    private javax.swing.JLabel lbl_btn_formerror;
    private javax.swing.JLabel lbl_btn_kembali;
    private javax.swing.JLabel lbl_btn_listdaftareo;
    private javax.swing.JLabel lbl_btn_reset;
    private javax.swing.JLabel lbl_btn_simpan;
    private javax.swing.JLabel lbl_footer;
    private javax.swing.JLabel lbl_header;
    private javax.swing.JLabel lbl_iconform;
    private javax.swing.JLabel lbl_qrcode;
    private javax.swing.JLabel lbl_reset;
    private javax.swing.JLabel lbl_reset_error;
    private javax.swing.JLabel lbl_simpan_error;
    private javax.swing.JPanel panel_atas;
    private javax.swing.JPanel panel_bawah;
    private javax.swing.JPanel panel_daftarlist_eo;
    private javax.swing.JPanel panel_error_report;
    private javax.swing.JPanel panel_kanan;
    private javax.swing.JPanel panel_kiri;
    private javax.swing.JPanel panel_main;
    private javax.swing.JPanel panel_pendaftaran_eo;
    private javax.swing.JPanel panelqrcode;
    private javax.swing.JButton qrsave;
    private javax.swing.JRadioButton rb_amk;
    private javax.swing.JRadioButton rb_namk;
    private javax.swing.JTable tbl_daftar_eo;
    private javax.swing.JTextField txt_ID_eo;
    private javax.swing.JTextPane txt_alamat_eo;
    private javax.swing.JTextField txt_cari_eo;
    private javax.swing.JTextArea txt_error_description;
    private javax.swing.JTextField txt_error_id;
    private javax.swing.JTextField txt_error_id_emp;
    private javax.swing.JTextField txt_error_title;
    private javax.swing.JTextField txt_mail_eo;
    private javax.swing.JTextField txt_nama_eo;
    private javax.swing.JTextField txt_nama_pic_eo;
    private javax.swing.JTextField txt_ponsel_eo;
    private javax.swing.JTextField txt_website_eo;
    // End of variables declaration//GEN-END:variables

}
