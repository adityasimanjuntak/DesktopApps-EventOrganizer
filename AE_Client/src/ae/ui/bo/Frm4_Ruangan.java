/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ae.ui.bo;

import ae.config.configuration;
import ae.dao.api.IPeralatanDao;
import ae.dao.api.IRuanganDao;
import ae.model.Peralatan;
import ae.model.Ruangan;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Aditya
 */
public class Frm4_Ruangan extends javax.swing.JFrame {

    /**
     * Creates new form Frm_SignUp_User
     */
    public Frm4_Ruangan() {
        initComponents();

        init_Ruangan_Dao();
        init_Peralatan_Dao();

        init_Ruangan_ID();
        init_Sapras_ID();
        init_KD_Daftar();

        init_TablePeralatan();
        init_TableSapras();
        init_TableDetilRuangan();
        init_TableRuangan();

        loadDaftarPeralatan();
        loadDaftarRuangan();
    }

    private DefaultTableModel tabmode;
    private IPeralatanDao peralatanDao = null;
    private IRuanganDao ruanganDao = null;

    private final Object[] DaftarPeralatancolumNames = {"ID Peralatan", "Nama Alat"};
    private final DefaultTableModel tableModelPeralatan = new DefaultTableModel();
    private List<Peralatan> recordPeralatan = new ArrayList<Peralatan>();

    private final Object[] DaftarSaprascolumNames = {"Kode Sarana dan Prasarana", "Id Alat", "Jumlah"};
    private final DefaultTableModel tableModelSapras = new DefaultTableModel();
    private List<Ruangan> recordSapras = new ArrayList<Ruangan>();

    private final Object[] DaftarDetilRuangancolumNames = {"Kode Pendaftaran", "Ruangan ID", "Tanggal", "Jam", "SarPras ID", "Status"};
    private final DefaultTableModel tableModelDetilRuangan = new DefaultTableModel();
    private List<Ruangan> recordDetilRuangan = new ArrayList<Ruangan>();

    private final Object[] DaftarRuangancolumNames = {"Ruangan ID", "Nama Ruangan"};
    private final DefaultTableModel tableModelRuangan = new DefaultTableModel();
    private List<Ruangan> recordRuangan = new ArrayList<Ruangan>();

    private int mode = 1;
    private String jenismode = "Normal";

    private void init_Peralatan_Dao() {
        String url = "rmi://" + configuration.ip_server + ":1099/peralatanDao";
        try {
            //ambil referensi dari remote objek yang ada di server
            peralatanDao = (IPeralatanDao) Naming.lookup(url);
        } catch (NotBoundException | MalformedURLException ex) {
            Logger.getLogger(Frm4_Ruangan.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            //Logger.getLogger(FrmSupplier.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Koneksi ke Service" + url + "[GAGAL], cek kembali app server", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void init_Ruangan_Dao() {
        String url = "rmi://" + configuration.ip_server + ":1099/ruanganDao";
        try {
            //ambil referensi dari remote objek yang ada di server
            ruanganDao = (IRuanganDao) Naming.lookup(url);
        } catch (NotBoundException | MalformedURLException ex) {
            Logger.getLogger(Frm4_Ruangan.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            //Logger.getLogger(FrmSupplier.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Koneksi ke Service" + url + "[GAGAL], cek kembali app server", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void init_TablePeralatan() {
        //set header table
        tableModelPeralatan.setColumnIdentifiers(DaftarPeralatancolumNames);
        tbl_peralatan.setModel(tableModelPeralatan);
        tbl_peralatan.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void init_TableSapras() {
        //set header table
        tableModelSapras.setColumnIdentifiers(DaftarSaprascolumNames);
        tbl_sarpras.setModel(tableModelSapras);
        tbl_sarpras.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void init_TableDetilRuangan() {
        //set header table
        tableModelDetilRuangan.setColumnIdentifiers(DaftarDetilRuangancolumNames);
        tbl_detil_ruangan.setModel(tableModelDetilRuangan);
        tbl_detil_ruangan.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void init_TableRuangan() {
        //set header table
        tableModelRuangan.setColumnIdentifiers(DaftarRuangancolumNames);
        tbl_ruangan.setModel(tableModelRuangan);
        tbl_ruangan.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void loadDaftarPeralatan() {
        try {
            // reset data di tabel
            tableModelPeralatan.setRowCount(0);
            // mengambil data barang dari server
            // kemudian menyimpannya ke objek list
            recordPeralatan = peralatanDao.getAll();
            // ekstrak data barang yg ada di dalam objek list
            // kemudian menampilkannya ke objek tabel
            for (Peralatan P : recordPeralatan) {
                // ambil nomor urut terakhir
                Object[] objects = new Object[DaftarPeralatancolumNames.length];
                objects[0] = P.getPeralatan_id();
                objects[1] = P.getPeralatan_nama();
                // tambahkan data barang ke dalam tabel
                tableModelPeralatan.addRow(objects);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Frm4_Ruangan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadDaftarDetilRuangan() {
        try {
            // reset data di tabel
            tableModelDetilRuangan.setRowCount(0);
            // mengambil data barang dari server
            // kemudian menyimpannya ke objek list
            recordDetilRuangan = ruanganDao.getAll();
            // ekstrak data barang yg ada di dalam objek list
            // kemudian menampilkannya ke objek tabel
            for (Ruangan R : recordDetilRuangan) {
                // ambil nomor urut terakhir
                Object[] objects = new Object[DaftarDetilRuangancolumNames.length];
                objects[0] = R.getKd_daftar();
                objects[1] = R.getRuangan_id();
                objects[2] = R.getRuangan_tanggal();
                objects[3] = R.getRuangan_jam();
                objects[4] = R.getRuangan_sapras_id();
                objects[5] = R.getRuangan_status();
                // tambahkan data barang ke dalam tabel
                tableModelDetilRuangan.addRow(objects);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Frm4_Ruangan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadDaftarSapras() {
        try {
            // reset data di tabel
            tableModelSapras.setRowCount(0);
            // mengambil data barang dari server
            // kemudian menyimpannya ke objek list
            recordSapras = ruanganDao.getAllSarpras();
            // ekstrak data barang yg ada di dalam objek list
            // kemudian menampilkannya ke objek tabel
            for (Ruangan R : recordSapras) {
                // ambil nomor urut terakhir
                Object[] objects = new Object[DaftarSaprascolumNames.length];
                objects[0] = R.getRuangan_sapras_id();
                objects[1] = R.getRuangan_id_alat();
                objects[2] = R.getRuangan_jumlah_alat();
                // tambahkan data barang ke dalam tabel
                tableModelSapras.addRow(objects);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Frm4_Ruangan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadDaftarRuangan() {
        try {
            // reset data di tabel
            tableModelRuangan.setRowCount(0);
            // mengambil data barang dari server
            // kemudian menyimpannya ke objek list
            recordDetilRuangan = ruanganDao.getAllRuangan();
            // ekstrak data barang yg ada di dalam objek list
            // kemudian menampilkannya ke objek tabel
            for (Ruangan R : recordDetilRuangan) {
                // ambil nomor urut terakhir
                Object[] objects = new Object[DaftarDetilRuangancolumNames.length];
                objects[0] = R.getRuangan_id();
                objects[1] = R.getRuangan_nama();
                // tambahkan data barang ke dalam tabel
                tableModelRuangan.addRow(objects);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Frm4_Ruangan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadDaftarSaprasByKdSarpras() {
        try {
            // reset data di tabel
            tableModelSapras.setRowCount(0);
            // mengambil data barang dari server
            String kd_sarpras = txt_id_sarpras.getText();
            // kemudian menyimpannya ke objek list
            recordSapras = ruanganDao.getByKDSarpras(kd_sarpras);
            // ekstrak data barang yg ada di dalam objek list
            // kemudian menampilkannya ke objek tabel
            for (Ruangan R : recordSapras) {
                // ambil nomor urut terakhir
                Object[] objects = new Object[DaftarSaprascolumNames.length];
                objects[0] = R.getRuangan_sapras_id();
                objects[1] = R.getRuangan_id_alat();
                objects[2] = R.getRuangan_jumlah_alat();
                // tambahkan data barang ke dalam tabel
                tableModelSapras.addRow(objects);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Frm4_Ruangan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void init_KD_Daftar() {
        Ruangan R = new Ruangan();
        try {
            String hasil = ruanganDao.GenerateKDRuangan(R);
            if (hasil == null) {
                //do something
            } else {
                txt_kd_daftar.setText(hasil);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Frm4_Ruangan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void init_Ruangan_ID() {
        Ruangan R = new Ruangan();
        try {
            String hasil = ruanganDao.GenerateIDRuangan(R);
            if (hasil == null) {
                //do something
            } else {
                txt_id_ruangan.setText(hasil);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Frm4_Ruangan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void init_Sapras_ID() {
        Ruangan R = new Ruangan();
        try {
            String hasil = ruanganDao.GenerateIDSarpras(R);
            if (hasil == null) {
                //do something
            } else {
                txt_id_sarpras.setText(hasil);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(Frm4_Ruangan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void ClearTableDetilRuangan() {
        int baris = tableModelDetilRuangan.getRowCount();
        for (int a = 0; a < baris; a++) {
            tableModelDetilRuangan.removeRow(0);
        }
    }

    private void ClearTableSapras() {
        int baris = tableModelSapras.getRowCount();
        for (int a = 0; a < baris; a++) {
            tableModelSapras.removeRow(0);
        }
    }

    public int getRowByValue(TableModel model, Object value) {
        for (int i = model.getRowCount() - 1; i >= 0; --i) {
            for (int j = model.getColumnCount() - 1; j >= 0; --j) {
                if (model.getValueAt(i, j).equals(value)) {
                    // what if value is not unique?
                    return i;
                }
            }
        }
        return -1;
    }

    private void modelNormal() {
        jenismode = "Normal";
        lbl_mode.setText(jenismode);
        init_KD_Daftar();
        txt_nama_ruangan.setText("");
        date_tanggal.cleanup();
        spin_jam.setValue("00");
        spin_menit.setValue("00");
        txt_id_alat.setText("");
        txt_nama_alat.setText("");
        txt_kuantitas.setText("");
        btn_update.setVisible(true);
        btn_tampil_ruangan.setEnabled(false);
        mode = 0;
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
        Frm_Dialog_Peralatan = new javax.swing.JDialog();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_peralatan = new javax.swing.JTable();
        btn_pilih = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        Frm_Dialog_Daftar_Ruangan = new javax.swing.JDialog();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_ruangan = new javax.swing.JTable();
        btn_hapus_ruangan = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btn_back = new javax.swing.JLabel();
        btn_hapus = new javax.swing.JLabel();
        btn_update = new javax.swing.JLabel();
        btn_reset = new javax.swing.JLabel();
        btn_save = new javax.swing.JLabel();
        btn_new_ruangan = new javax.swing.JButton();
        btn_cari = new javax.swing.JButton();
        btn_tampil_sarpras = new javax.swing.JButton();
        btn_tampil_detil_ruangan = new javax.swing.JButton();
        btn_tampil_ruangan = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btn_tambah = new javax.swing.JButton();
        date_tanggal = new com.toedter.calendar.JDateChooser();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_detil_ruangan = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_sarpras = new javax.swing.JTable();
        spin_menit = new javax.swing.JSpinner();
        spin_jam = new javax.swing.JSpinner();
        txt_kd_daftar = new javax.swing.JTextField();
        txt_status = new javax.swing.JTextField();
        txt_nama_alat = new javax.swing.JTextField();
        txt_id_sarpras = new javax.swing.JTextField();
        txt_kuantitas = new javax.swing.JTextField();
        txt_id_alat = new javax.swing.JTextField();
        txt_nama_ruangan = new javax.swing.JTextField();
        txt_id_ruangan = new javax.swing.JTextField();
        lbl_mode = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        Bg_Pendaftaran_User = new javax.swing.JLabel();

        Frm_Dialog_Peralatan.setTitle("Amikom Event");
        Frm_Dialog_Peralatan.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_peralatan.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tbl_peralatan);

        Frm_Dialog_Peralatan.getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 260, 260));

        btn_pilih.setText("Pilih");
        btn_pilih.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pilihActionPerformed(evt);
            }
        });
        Frm_Dialog_Peralatan.getContentPane().add(btn_pilih, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 350, 80, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/bg_daftar_peralatan_new.png"))); // NOI18N
        Frm_Dialog_Peralatan.getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        Frm_Dialog_Daftar_Ruangan.setTitle("Amikom Event");
        Frm_Dialog_Daftar_Ruangan.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_ruangan.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(tbl_ruangan);

        Frm_Dialog_Daftar_Ruangan.getContentPane().add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 260, 260));

        btn_hapus_ruangan.setText("HAPUS");
        btn_hapus_ruangan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapus_ruanganActionPerformed(evt);
            }
        });
        Frm_Dialog_Daftar_Ruangan.getContentPane().add(btn_hapus_ruangan, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 350, 80, -1));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/bg_daftar_peralatan_new.png"))); // NOI18N
        Frm_Dialog_Daftar_Ruangan.getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

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
        getContentPane().add(btn_back, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 550, 90, -1));

        btn_hapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/btn_hapus.png"))); // NOI18N
        btn_hapus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_hapus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_hapusMouseClicked(evt);
            }
        });
        getContentPane().add(btn_hapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 550, 90, -1));

        btn_update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/btn_update.png"))); // NOI18N
        btn_update.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_update.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_updateMouseClicked(evt);
            }
        });
        getContentPane().add(btn_update, new org.netbeans.lib.awtextra.AbsoluteConstraints(857, 550, -1, -1));

        btn_reset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/btn_reset3.png"))); // NOI18N
        btn_reset.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_reset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_resetMouseClicked(evt);
            }
        });
        getContentPane().add(btn_reset, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 550, 90, -1));

        btn_save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/btn_simpan5.png"))); // NOI18N
        btn_save.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_save.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_saveMouseClicked(evt);
            }
        });
        getContentPane().add(btn_save, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 550, 90, -1));

        btn_new_ruangan.setText("BARU");
        btn_new_ruangan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_new_ruanganActionPerformed(evt);
            }
        });
        getContentPane().add(btn_new_ruangan, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 200, 110, 30));

        btn_cari.setText("CARI");
        btn_cari.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_cariMouseClicked(evt);
            }
        });
        getContentPane().add(btn_cari, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 600, 110, 40));

        btn_tampil_sarpras.setText("TAMPIL SARANA PRASARANA");
        btn_tampil_sarpras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_tampil_sarprasMouseClicked(evt);
            }
        });
        getContentPane().add(btn_tampil_sarpras, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 170, -1, -1));

        btn_tampil_detil_ruangan.setText("TAMPIL DETIL RUANGAN");
        btn_tampil_detil_ruangan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tampil_detil_ruanganActionPerformed(evt);
            }
        });
        getContentPane().add(btn_tampil_detil_ruangan, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 170, -1, -1));

        btn_tampil_ruangan.setText("TAMPIL DAFTAR RUANGAN");
        btn_tampil_ruangan.setEnabled(false);
        btn_tampil_ruangan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tampil_ruanganActionPerformed(evt);
            }
        });
        getContentPane().add(btn_tampil_ruangan, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 170, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("WIB");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 340, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText(":");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(273, 340, -1, -1));

        btn_tambah.setText(">>");
        btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahActionPerformed(evt);
            }
        });
        getContentPane().add(btn_tambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(349, 600, 80, 40));
        getContentPane().add(date_tanggal, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 335, 150, 30));

        tbl_detil_ruangan.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_detil_ruangan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_detil_ruanganMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tbl_detil_ruangan);

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 210, 710, 150));

        tbl_sarpras.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_sarpras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_sarprasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_sarpras);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 380, 710, 150));

        spin_menit.setModel(new javax.swing.SpinnerListModel(new String[] {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"}));
        getContentPane().add(spin_menit, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 335, 50, 30));

        spin_jam.setModel(new javax.swing.SpinnerListModel(new String[] {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"}));
        getContentPane().add(spin_jam, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 335, 50, 30));

        txt_kd_daftar.setEnabled(false);
        getContentPane().add(txt_kd_daftar, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 120, 170, 30));

        txt_status.setText("Tersedia");
        txt_status.setEnabled(false);
        getContentPane().add(txt_status, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 400, 250, 30));

        txt_nama_alat.setEnabled(false);
        getContentPane().add(txt_nama_alat, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 550, 200, 30));

        txt_id_sarpras.setEnabled(false);
        getContentPane().add(txt_id_sarpras, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 470, 250, 30));
        getContentPane().add(txt_kuantitas, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 550, 80, 30));

        txt_id_alat.setEnabled(false);
        txt_id_alat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_id_alatMouseClicked(evt);
            }
        });
        getContentPane().add(txt_id_alat, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 550, 80, 30));
        getContentPane().add(txt_nama_ruangan, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 265, 250, 30));

        txt_id_ruangan.setEnabled(false);
        getContentPane().add(txt_id_ruangan, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, 250, 30));

        lbl_mode.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbl_mode.setForeground(new java.awt.Color(255, 255, 255));
        lbl_mode.setText("Normal");
        getContentPane().add(lbl_mode, new org.netbeans.lib.awtextra.AbsoluteConstraints(1140, 670, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Mode :");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 670, -1, -1));

        Bg_Pendaftaran_User.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/bg_tambah_ruangan_new2.png"))); // NOI18N
        getContentPane().add(Bg_Pendaftaran_User, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_backMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_backMouseClicked
        // TODO add your handling code here:
        if (("Hapus").equals(jenismode)) {
            int reply = JOptionPane.showConfirmDialog(null, "Apakah Anda Ingin Kembali Ke Mode Normal ?", "Information", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(this, "Mode Normal Aktif", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                modelNormal();
            }
        } else if (("Update").equals(jenismode)) {
            int reply = JOptionPane.showConfirmDialog(null, "Apakah Anda Ingin Kembali Ke Mode Normal ?", "Information", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(this, "Mode Normal Aktif", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                modelNormal();
            }
        } else {
            int reply = JOptionPane.showConfirmDialog(null, "Apakah Anda Ingin Kembali Ke Menu ?", "Information", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {

                Frm1_Menu Menu = new Frm1_Menu();
                Menu.setVisible(true);
                dispose();
            }

        }

    }//GEN-LAST:event_btn_backMouseClicked

    private void btn_resetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_resetMouseClicked
        // TODO add your handling code here:
        init_TableDetilRuangan();
        init_TableSapras();
        ClearTableDetilRuangan();
        ClearTableSapras();
        modelNormal();
        init_KD_Daftar();
    }//GEN-LAST:event_btn_resetMouseClicked

    private void btn_saveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_saveMouseClicked
        // TODO add your handling code here:
        //Save Ruangan Inti
        if ("Hapus".equals(jenismode)) {
            JOptionPane.showMessageDialog(null, "Anda Sedang Dalam Mode Hapus", "Warning", JOptionPane.ERROR_MESSAGE);
            btn_back.requestFocus();
        } else if (txt_nama_ruangan.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Tambahkan Nama Ruangan", "Warning", JOptionPane.WARNING_MESSAGE);
            txt_nama_ruangan.requestFocus();
        } else if (date_tanggal.getDate().equals("")) {
            JOptionPane.showMessageDialog(null, "Tambahkan Tanggal", "Warning", JOptionPane.WARNING_MESSAGE);
            date_tanggal.requestFocus();
        } else {

            Ruangan R = new Ruangan();
            R.setKd_daftar(txt_kd_daftar.getText());
            R.setRuangan_id(txt_id_ruangan.getText());
            R.setRuangan_nama(txt_nama_ruangan.getText());

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Calendar cal = Calendar.getInstance();

            String jam = String.valueOf(spin_jam.getValue());
            String menit = String.valueOf(spin_menit.getValue());
            String waktu = jam + ":" + menit;

            R.setRuangan_tanggal(sdf.format(date_tanggal.getDate()));
            System.out.println(sdf.format(date_tanggal.getDate()));
            R.setRuangan_jam(waktu);
            R.setRuangan_sapras_id(txt_id_sarpras.getText());
            R.setRuangan_status(txt_status.getText());

            if (jenismode == "Update") {
                if (mode == 0) {
                    try {

                        int rowCount = tbl_sarpras.getRowCount();
                        int column = tbl_sarpras.getColumnCount();
                        for (int row = 0; row < rowCount; row++) {
                            R.setRuangan_sapras_id(tbl_sarpras.getValueAt(row, 0).toString());
                            R.setRuangan_id_alat(tbl_sarpras.getValueAt(row, 1).toString());
                            R.setRuangan_jumlah_alat(Integer.parseInt(tbl_sarpras.getValueAt(row, 2).toString()));
                            ruanganDao.deleteSapras(R);
                            ruanganDao.saveDataDetilSapras(R);
                        }
                        // tampilkan pesan berhasil
                        JOptionPane.showMessageDialog(this, "Save Data Sukses", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                        loadDaftarSapras();
                        loadDaftarDetilRuangan();
                        modelNormal();

                    } catch (RemoteException ex) {
                        Logger.getLogger(Frm4_Ruangan.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        int hasil2 = ruanganDao.updateDetilRuangan(R);
                        if (hasil2 == 1) {
                            loadDaftarSapras();
                            loadDaftarDetilRuangan();
                            modelNormal();
                        } else {
                            evt.consume();
                            getToolkit().beep();
                            JOptionPane.showMessageDialog(null, "Update Data Detil Ruangan Gagal", "Update Gagal", JOptionPane.ERROR_MESSAGE);

                        }
                    } catch (RemoteException ex) {
                        Logger.getLogger(Frm4_Ruangan.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }

                    try {
                        int hasil = ruanganDao.updateRuangan(R);
                        if (hasil == 1) {
                            loadDaftarSapras();
                            loadDaftarDetilRuangan();
                            modelNormal();
                        } else {
                            evt.consume();
                            getToolkit().beep();
                            JOptionPane.showMessageDialog(null, "Update Data Ruangan Gagal", "Update Gagal", JOptionPane.ERROR_MESSAGE);

                        }
                    } catch (RemoteException ex) {
                        Logger.getLogger(Frm4_Ruangan.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    try {
                        int hasil2 = ruanganDao.updateDetilRuangan(R);
                        if (hasil2 == 1) {
                            loadDaftarDetilRuangan();
                            modelNormal();
                        } else {
                            evt.consume();
                            getToolkit().beep();
                            JOptionPane.showMessageDialog(null, "Update Data Detil Ruangan Gagal", "Update Gagal", JOptionPane.ERROR_MESSAGE);

                        }
                    } catch (RemoteException ex) {
                        Logger.getLogger(Frm4_Ruangan.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    try {
                        int hasil = ruanganDao.updateRuangan(R);
                        if (hasil == 1) {
                            JOptionPane.showMessageDialog(null, "Update Ruangan Sukses", "Update Sukses", JOptionPane.INFORMATION_MESSAGE);
                            modelNormal();
                        } else {
                            evt.consume();
                            getToolkit().beep();
                            JOptionPane.showMessageDialog(null, "Update Data Ruangan Gagal", "Update Gagal", JOptionPane.ERROR_MESSAGE);

                        }
                    } catch (RemoteException ex) {
                        Logger.getLogger(Frm4_Ruangan.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    try {
                        int rowCount = tbl_sarpras.getRowCount();
                        int column = tbl_sarpras.getColumnCount();
                        for (int row = 0; row < rowCount; row++) {
                            R.setRuangan_sapras_id(tbl_sarpras.getValueAt(row, 0).toString());
                            R.setRuangan_id_alat(tbl_sarpras.getValueAt(row, 1).toString());
                            R.setRuangan_jumlah_alat(Integer.parseInt(tbl_sarpras.getValueAt(row, 2).toString()));
                            ruanganDao.updateDetilSapras(R);
                        }

                        // tampilkan pesan berhasil
                        JOptionPane.showMessageDialog(this, "Update Data Sukses", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                        modelNormal();

                    } catch (RemoteException ex) {
                        Logger.getLogger(Frm4_Ruangan.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            } else {
                if (mode == 1) {
                    try {
                        int hasil = ruanganDao.saveDataRuanganInti(R);
                        int hasil2 = ruanganDao.saveDataDetilRuangan(R);
                        if (hasil == 1 && hasil2 == 1) {
                            init_KD_Daftar();

                        } else {
                            evt.consume();
                            getToolkit().beep();
                            JOptionPane.showMessageDialog(null, "Save Data Gagal", "Save Gagal", JOptionPane.ERROR_MESSAGE);

                        }
                    } catch (RemoteException ex) {
                        Logger.getLogger(Frm4_Ruangan.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }

                    //Save Data Sarana Prasarana
                    try {
                        int rowCount = tbl_sarpras.getRowCount();
                        int column = tbl_sarpras.getColumnCount();
                        for (int row = 0; row < rowCount; row++) {
                            R.setRuangan_sapras_id(tbl_sarpras.getValueAt(row, 0).toString());
                            R.setRuangan_id_alat(tbl_sarpras.getValueAt(row, 1).toString());
                            R.setRuangan_jumlah_alat(Integer.parseInt(tbl_sarpras.getValueAt(row, 2).toString()));
                            ruanganDao.saveDataDetilSapras(R);
                            init_KD_Daftar();
                        }

                        // tampilkan pesan berhasil
                        JOptionPane.showMessageDialog(this, "Save Data Sukses", "Informasi", JOptionPane.INFORMATION_MESSAGE);

                    } catch (RemoteException ex) {
                        Logger.getLogger(Frm4_Ruangan.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    mode++;
                    init_KD_Daftar();
                } else if (mode >= 1) {
                    try {
                        int hasil2 = ruanganDao.saveDataDetilRuangan(R);
                        if (hasil2 == 1) {
                            JOptionPane.showMessageDialog(this, "Save Data Sukses", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                            init_KD_Daftar();
                        } else {
                            evt.consume();
                            getToolkit().beep();
                            JOptionPane.showMessageDialog(null, "Save Data Gagal", "Save Gagal", JOptionPane.ERROR_MESSAGE);

                        }
                    } catch (RemoteException ex) {
                        Logger.getLogger(Frm4_Ruangan.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    mode++;
                    init_KD_Daftar();
                }
                System.out.println(mode);
                init_KD_Daftar();
            }
        }
    }//GEN-LAST:event_btn_saveMouseClicked

    private void btn_hapusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_hapusMouseClicked
        // TODO add your handling code here:
        if ("Update".equals(jenismode)) {
            JOptionPane.showMessageDialog(null, "Anda Sedang Dalam Mode Update", "Warning", JOptionPane.ERROR_MESSAGE);
        } else {
            int reply = JOptionPane.showConfirmDialog(null, "Apakah Anda Ingin Masuk Mode Hapus ?", "Hapus", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {

                JOptionPane.showMessageDialog(this, "Mode Hapus Aktif", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                jenismode = "Hapus";
                lbl_mode.setText(jenismode);
                System.out.println(jenismode);
                loadDaftarDetilRuangan();
                btn_tampil_ruangan.setEnabled(true);
            }
        }
    }//GEN-LAST:event_btn_hapusMouseClicked

    private void tbl_sarprasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_sarprasMouseClicked
        // TODO add your handling code here:
        if ("Update".equals(jenismode)) {
            int baris = tbl_sarpras.getSelectedRow();
            int kolom = tbl_sarpras.getSelectedColumn();
            String IDAlat = tbl_sarpras.getValueAt(baris, 1).toString();
            String QTY = tbl_sarpras.getValueAt(baris, 2).toString();

            txt_id_alat.setText(IDAlat);
            txt_kuantitas.setText(QTY);
        } else if ("Hapus".equals(jenismode)) {

            // ambil nilai record yang dipilih
            int row = tbl_sarpras.getSelectedRow();
            if (row >= 0) {
                // ambil data barang yang dipilih
                int result = JOptionPane.showConfirmDialog(this, "Apakah data '" + tableModelSapras.getValueAt(row, 0).toString() + "'\nIngin dihapus ?", "Konfirmasi", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (result == JOptionPane.YES_NO_OPTION) {

                    Ruangan R = new Ruangan();
                    R.setRuangan_sapras_id(tbl_sarpras.getValueAt(row, 0).toString());
                    R.setRuangan_id_alat(tbl_sarpras.getValueAt(row, 1).toString());
                    R.setRuangan_jumlah_alat(Integer.parseInt(tbl_sarpras.getValueAt(row, 2).toString()));
                    try {
                        ruanganDao.deleteSapras(R);
                        // tampilkan pesan berhasil
                        JOptionPane.showMessageDialog(this, "Hapus Data Sarana Prasaranan Sukses", "Informasi", JOptionPane.INFORMATION_MESSAGE);

                    } catch (RemoteException ex) {
                        Logger.getLogger(Frm4_Ruangan.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    tableModelSapras.removeRow(row);
                    loadDaftarSapras();
                }
            }
        }
    }//GEN-LAST:event_tbl_sarprasMouseClicked

    private void btn_updateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_updateMouseClicked
        // TODO add your handling code here:
        if ("Hapus".equals(jenismode)) {
            JOptionPane.showMessageDialog(null, "Anda Sedang Dalam Mode Hapus", "Warning", JOptionPane.ERROR_MESSAGE);
        } else {
            int reply = JOptionPane.showConfirmDialog(null, "Apakah Anda Ingin Masuk Mode Update ?", "Update", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(this, "Mode Update Aktif", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                jenismode = "Update";
                lbl_mode.setText(jenismode);
                loadDaftarDetilRuangan();
                loadDaftarSaprasByKdSarpras();
                btn_update.setVisible(false);
                System.out.println(jenismode);
                btn_new_ruangan.setEnabled(false);
                mode = 0;
                System.out.println(mode);
            }
        }
    }//GEN-LAST:event_btn_updateMouseClicked

    private void txt_id_alatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_id_alatMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_id_alatMouseClicked

    private void btn_pilihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pilihActionPerformed
        // TODO add your handling code here:       
        int row = tbl_peralatan.getSelectedRow();
        Peralatan P = recordPeralatan.get(row);

        txt_id_alat.setText(P.getPeralatan_id());
        txt_nama_alat.setText(P.getPeralatan_nama());
        Frm_Dialog_Peralatan.setVisible(false);
    }//GEN-LAST:event_btn_pilihActionPerformed

    private void btn_cariMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_cariMouseClicked
        // TODO add your handling code here:
        Frm_Dialog_Peralatan.pack();
        Frm_Dialog_Peralatan.setLocationRelativeTo(this);
        Frm_Dialog_Peralatan.setModal(true);
        Frm_Dialog_Peralatan.setVisible(true);
    }//GEN-LAST:event_btn_cariMouseClicked

    private void tbl_detil_ruanganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_detil_ruanganMouseClicked
        // TODO add your handling code here:
        if (jenismode == "Update") {
            int baris = tbl_detil_ruangan.getSelectedRow();
            int kolom = tbl_detil_ruangan.getSelectedColumn();
            String KD_DR = tbl_detil_ruangan.getValueAt(baris, 0).toString();
            String DR_ID = tbl_detil_ruangan.getValueAt(baris, 1).toString();
            String SAPRAS_ID = tbl_detil_ruangan.getValueAt(baris, 4).toString();

            txt_kd_daftar.setText(KD_DR);
            txt_id_ruangan.setText(DR_ID);
            txt_id_sarpras.setText(SAPRAS_ID);
            loadDaftarSaprasByKdSarpras();
        } else if (jenismode == "Hapus") {
            int row2 = tbl_detil_ruangan.getSelectedRow();
            if (row2 >= 0) { // data barang belum dipilih
                int result = JOptionPane.showConfirmDialog(this, "Apakah data Detil Ruangan '" + tableModelDetilRuangan.getValueAt(row2, 0).toString() + "'\nIngin dihapus ?", "Konfirmasi", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (result == JOptionPane.YES_NO_OPTION) {

                    Ruangan R = new Ruangan();
                    R.setKd_daftar(tbl_detil_ruangan.getValueAt(row2, 0).toString());
                    R.setRuangan_id(tbl_detil_ruangan.getValueAt(row2, 1).toString());
                    R.setRuangan_tanggal(tbl_detil_ruangan.getValueAt(row2, 2).toString());
                    R.setRuangan_jam(tbl_detil_ruangan.getValueAt(row2, 3).toString());
                    R.setRuangan_sapras_id(tbl_detil_ruangan.getValueAt(row2, 4).toString());
                    R.setRuangan_status(tbl_detil_ruangan.getValueAt(row2, 5).toString());

                    try {
                        ruanganDao.deleteDetilRuangan(R);
                        // tampilkan pesan berhasil
                        JOptionPane.showMessageDialog(this, "Hapus Data Detil Ruangan Sukses", "Informasi", JOptionPane.INFORMATION_MESSAGE);

                    } catch (RemoteException ex) {
                        Logger.getLogger(Frm4_Ruangan.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                    tableModelDetilRuangan.removeRow(row2);
                    loadDaftarDetilRuangan();
                }
            }

        }
    }//GEN-LAST:event_tbl_detil_ruanganMouseClicked

    private void btn_tampil_sarprasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_tampil_sarprasMouseClicked
        // TODO add your handling code here:
        loadDaftarSapras();
    }//GEN-LAST:event_btn_tampil_sarprasMouseClicked

    private void btn_new_ruanganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_new_ruanganActionPerformed
        // TODO add your handling code here:
        init_KD_Daftar();
        init_Ruangan_ID();
        init_Sapras_ID();
    }//GEN-LAST:event_btn_new_ruanganActionPerformed

    private void btn_tampil_detil_ruanganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tampil_detil_ruanganActionPerformed
        // TODO add your handling code here:
        loadDaftarDetilRuangan();
    }//GEN-LAST:event_btn_tampil_detil_ruanganActionPerformed

    private void btn_tampil_ruanganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tampil_ruanganActionPerformed
        // TODO add your handling code here:
        Frm_Dialog_Daftar_Ruangan.pack();
        Frm_Dialog_Daftar_Ruangan.setLocationRelativeTo(this);
        Frm_Dialog_Daftar_Ruangan.setModal(true);
        Frm_Dialog_Daftar_Ruangan.setVisible(true);
        loadDaftarRuangan();
    }//GEN-LAST:event_btn_tampil_ruanganActionPerformed

    private void btn_hapus_ruanganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapus_ruanganActionPerformed
        // TODO add your handling code here:
        int row = tbl_ruangan.getSelectedRow();
        if (row >= 0) { // data barang belum dipilih
            int result = JOptionPane.showConfirmDialog(this, "Apakah data Detil Ruangan '" + tableModelRuangan.getValueAt(row, 0).toString() + "'\nIngin dihapus ?", "Konfirmasi", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (result == JOptionPane.YES_NO_OPTION) {

                Ruangan R = new Ruangan();
                R.setRuangan_id(tbl_ruangan.getValueAt(row, 0).toString());
                R.setRuangan_nama(tbl_ruangan.getValueAt(row, 1).toString());

                try {
                    ruanganDao.deleteRuangan(R);
                    // tampilkan pesan berhasil
                    JOptionPane.showMessageDialog(this, "Hapus Data Ruangan Sukses", "Informasi", JOptionPane.INFORMATION_MESSAGE);

                } catch (RemoteException ex) {
                    Logger.getLogger(Frm4_Ruangan.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
                tableModelRuangan.removeRow(row);
                loadDaftarRuangan();
            }
        }
    }//GEN-LAST:event_btn_hapus_ruanganActionPerformed

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        // TODO add your handling code here:       
        String kodeSapras = txt_id_sarpras.getText();
        String idAlat = txt_id_alat.getText();
        int jumlah = Integer.parseInt(txt_kuantitas.getText());
        int qty = Integer.parseInt(txt_kuantitas.getText());
        String status = txt_status.getText();

        // pencarian jika ada barang yang sama
        int row = getRowByValue(tableModelSapras, idAlat);
        if (row >= 0) {
            // update data item yg ada di tabel
            qty = qty + 0;
            tableModelSapras.setValueAt(qty, row, 2);
        } else {

            // ambil nomor urut terakhir
            Object[] objects = new Object[DaftarSaprascolumNames.length];
            objects[0] = kodeSapras;
            objects[1] = idAlat;
            objects[2] = jumlah;

            // tambahkan data item ke dalam tabel
            tableModelSapras.addRow(objects);
        }
    }//GEN-LAST:event_btn_tambahActionPerformed

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
            java.util.logging.Logger.getLogger(Frm4_Ruangan.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frm4_Ruangan.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frm4_Ruangan.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frm4_Ruangan.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frm4_Ruangan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Bg_Pendaftaran_User;
    private javax.swing.JDialog Frm_Dialog_Daftar_Ruangan;
    private javax.swing.JDialog Frm_Dialog_Peralatan;
    private javax.swing.JLabel btn_back;
    private javax.swing.JButton btn_cari;
    private javax.swing.JLabel btn_hapus;
    private javax.swing.JButton btn_hapus_ruangan;
    private javax.swing.JButton btn_new_ruangan;
    private javax.swing.JButton btn_pilih;
    private javax.swing.JLabel btn_reset;
    private javax.swing.JLabel btn_save;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JButton btn_tampil_detil_ruangan;
    private javax.swing.JButton btn_tampil_ruangan;
    private javax.swing.JButton btn_tampil_sarpras;
    private javax.swing.JLabel btn_update;
    private javax.swing.ButtonGroup buttonGroup1;
    private com.toedter.calendar.JDateChooser date_tanggal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lbl_mode;
    private javax.swing.JSpinner spin_jam;
    private javax.swing.JSpinner spin_menit;
    private javax.swing.JTable tbl_detil_ruangan;
    private javax.swing.JTable tbl_peralatan;
    private javax.swing.JTable tbl_ruangan;
    private javax.swing.JTable tbl_sarpras;
    private javax.swing.JTextField txt_id_alat;
    private javax.swing.JTextField txt_id_ruangan;
    private javax.swing.JTextField txt_id_sarpras;
    private javax.swing.JTextField txt_kd_daftar;
    private javax.swing.JTextField txt_kuantitas;
    private javax.swing.JTextField txt_nama_alat;
    private javax.swing.JTextField txt_nama_ruangan;
    private javax.swing.JTextField txt_status;
    // End of variables declaration//GEN-END:variables
}
