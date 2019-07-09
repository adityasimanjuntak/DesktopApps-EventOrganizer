/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ae.ui.fo;

import ae.config.configuration;
import ae.dao.api.IErrorReportDao;
import ae.dao.api.IEventDao;
import ae.dao.api.IEventOrganizerDao;
import ae.dao.api.IKategoriDao;
import ae.dao.api.IPeralatanDao;
import ae.dao.api.IRuanganDao;
import ae.model.ErrorReport;
import ae.model.Event;
import ae.model.EventOrganizer;
import ae.model.Kategori;
import ae.model.Peralatan;
import ae.model.Ruangan;
import java.awt.event.KeyEvent;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
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
public class FO5_PendaftaranEvent extends javax.swing.JFrame {

    /**
     * Creates new form FO4_PendaftaranEO
     */
    public FO5_PendaftaranEvent() {
        initComponents();
        init_ErrorReport_Dao();

        init_Kategori_Dao();
        init_TableKategori();
        loadDaftarKategori();

        init_Ruangan_Dao();
        init_TableRuangan();
        loadDaftarRuangan();

        init_EventOrganizer_Dao();
        init_TableEo();
        loadDaftarEo();

        init_Peralatan_Dao();
        init_TablePeralatan();
        loadDaftarPeralatan();

        init_TableTambahPeralatan();

        initModelAwal();

        init_Event_Dao();
        init_KD_Event();
        init_KD_Tambah_Alat();
    }

    int mode;
    int udm = 0, resol = 100, rot = 0;
    float mi = 0.000f, md = 0.000f, ms = 0.000f, min = 0.000f, tam = 5.00f;
    byte[] qr_image = null;

    FileOutputStream fout;
    ByteArrayOutputStream out;

    private IErrorReportDao errorDao = null;
    private IEventDao eventDao = null;
    private String instansi = null;

    private IKategoriDao kategoriDao = null;
    private final Object[] DaftarKategoricolumNames = {"ID Kategori", "Nama Kategori"};
    private final DefaultTableModel tableModelKategori = new DefaultTableModel();
    private List<Kategori> recordKategori = new ArrayList<Kategori>();

    private IRuanganDao ruanganDao = null;
    private final Object[] DaftarRuangancolumNames = {"Kode", "ID Ruangan", "Tanggal", "Waktu", "Detil Sarpras", "Status"};
    private final DefaultTableModel tableModelRuangan = new DefaultTableModel();
    private List<Ruangan> recordRuangan = new ArrayList<Ruangan>();

    private IEventOrganizerDao eventorganizerDao = null;
    private final Object[] DaftarEOcolumNames = {"ID EO", "Nama EO", "Penanggung Jawab", "No. Ponsel", "E-mail", "Website"};
    private final DefaultTableModel tableModelEo = new DefaultTableModel();
    private List<EventOrganizer> recordEo = new ArrayList<EventOrganizer>();

    private IPeralatanDao peralatanDao = null;
    private final Object[] DaftarPeralatancolumNames = {"ID Alat", "Nama Alat"};
    private final DefaultTableModel tableModelPeralatan = new DefaultTableModel();
    private List<Peralatan> recordperalatan = new ArrayList<Peralatan>();

    private final Object[] DaftarTambahAlatcolumNames = {"ID Alat", "Nama Alat", "Jumlah"};
    private final DefaultTableModel tableModelTambahAlat = new DefaultTableModel();
    private List<Ruangan> recordTambahAlat = new ArrayList<Ruangan>();

    private void init_Kategori_Dao() {
        String url = "rmi://" + configuration.ip_server + ":1099/kategoriDao";
        try {
            //ambil referensi dari remote objek yang ada di server
            kategoriDao = (IKategoriDao) Naming.lookup(url);
        } catch (NotBoundException | MalformedURLException ex) {
            Logger.getLogger(FO5_PendaftaranEvent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            //Logger.getLogger(FrmSupplier.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Koneksi ke Service" + url + "[GAGAL], cek kembali app server", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void init_TableKategori() {
        //set header table
        tableModelKategori.setColumnIdentifiers(DaftarKategoricolumNames);
        tbl_kategori.setModel(tableModelKategori);
        tbl_kategori.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void loadDaftarKategori() {
        try {
            // reset data di tabel
            tableModelKategori.setRowCount(0);
            // mengambil data barang dari server
            // kemudian menyimpannya ke objek list
            recordKategori = kategoriDao.getAll();
            // ekstrak data barang yg ada di dalam objek list
            // kemudian menampilkannya ke objek tabel
            for (Kategori K : recordKategori) {
                // ambil nomor urut terakhir
                Object[] objects = new Object[DaftarKategoricolumNames.length];
                objects[0] = K.getId_kat();
                objects[1] = K.getNama_kat();
                // tambahkan data barang ke dalam tabel
                tableModelKategori.addRow(objects);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(FO5_PendaftaranEvent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void init_Ruangan_Dao() {
        String url = "rmi://" + configuration.ip_server + ":1099/ruanganDao";
        try {
            //ambil referensi dari remote objek yang ada di server
            ruanganDao = (IRuanganDao) Naming.lookup(url);
        } catch (NotBoundException | MalformedURLException ex) {
            Logger.getLogger(FO5_PendaftaranEvent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            //Logger.getLogger(FrmSupplier.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Koneksi ke Service" + url + "[GAGAL], cek kembali app server", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void init_TableRuangan() {
        //set header table
        tableModelRuangan.setColumnIdentifiers(DaftarRuangancolumNames);
        tbl_ruangan.setModel(tableModelRuangan);
        tbl_ruangan.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void loadDaftarRuangan() {
        try {
            // reset data di tabel
            tableModelRuangan.setRowCount(0);
            // mengambil data barang dari server
            // kemudian menyimpannya ke objek list
            recordRuangan = ruanganDao.getAll();
            // ekstrak data barang yg ada di dalam objek list
            // kemudian menampilkannya ke objek tabel
            for (Ruangan R : recordRuangan) {
                // ambil nomor urut terakhir
                Object[] objects = new Object[DaftarRuangancolumNames.length];
                objects[0] = R.getKd_daftar();
                objects[1] = R.getRuangan_id();
                objects[2] = R.getRuangan_tanggal();
                objects[3] = R.getRuangan_jam();
                objects[4] = R.getRuangan_sapras_id();
                objects[5] = R.getRuangan_status();
                // tambahkan data barang ke dalam tabel
                tableModelRuangan.addRow(objects);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(FO5_PendaftaranEvent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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

    private void init_TableEo() {
        //set header table
        tableModelEo.setColumnIdentifiers(DaftarEOcolumNames);
        tbl_eo.setModel(tableModelEo);
        tbl_eo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
            Logger.getLogger(FO5_PendaftaranEvent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void init_Peralatan_Dao() {
        String url = "rmi://" + configuration.ip_server + ":1099/peralatanDao";
        try {
            //ambil referensi dari remote objek yang ada di server
            peralatanDao = (IPeralatanDao) Naming.lookup(url);
        } catch (NotBoundException | MalformedURLException ex) {
            Logger.getLogger(FO5_PendaftaranEvent.class.getName()).log(Level.SEVERE, null, ex);
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

    private void loadDaftarPeralatan() {
        try {
            // reset data di tabel
            tableModelPeralatan.setRowCount(0);
            // mengambil data barang dari server
            // kemudian menyimpannya ke objek list
            recordperalatan = peralatanDao.getAll();
            // ekstrak data barang yg ada di dalam objek list
            // kemudian menampilkannya ke objek tabel
            for (Peralatan P : recordperalatan) {
                // ambil nomor urut terakhir
                Object[] objects = new Object[DaftarPeralatancolumNames.length];
                objects[0] = P.getPeralatan_id();
                objects[1] = P.getPeralatan_nama();
                // tambahkan data barang ke dalam tabel
                tableModelPeralatan.addRow(objects);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(FO5_PendaftaranEvent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void init_TableTambahPeralatan() {
        //set header table
        tableModelTambahAlat.setColumnIdentifiers(DaftarTambahAlatcolumNames);
        tbl_tambah.setModel(tableModelTambahAlat);
        tbl_tambah.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void init_ErrorReport_Dao() {
        String url = "rmi://" + configuration.ip_server + ":1099/errorDao";
        try {
            //ambil referensi dari remote objek yang ada di server
            errorDao = (IErrorReportDao) Naming.lookup(url);
        } catch (NotBoundException | MalformedURLException ex) {
            Logger.getLogger(FO5_PendaftaranEvent.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(FO5_PendaftaranEvent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void init_Event_Dao() {
        String url = "rmi://" + configuration.ip_server + ":1099/eventDao";
        try {
            //ambil referensi dari remote objek yang ada di server
            eventDao = (IEventDao) Naming.lookup(url);
        } catch (NotBoundException | MalformedURLException ex) {
            Logger.getLogger(FO5_PendaftaranEvent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            //Logger.getLogger(FrmSupplier.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Koneksi ke Service" + url + "[GAGAL], cek kembali app server", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void init_KD_Event() {
        Event E = new Event();
        try {
            String hasil = eventDao.GenerateKDEvent(E);
            if (hasil == null) {
                //do something
            } else {
                txt_id_event.setText(hasil);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(FO5_PendaftaranEvent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void init_KD_Tambah_Alat() {
        Event E = new Event();
        try {
            String hasil = eventDao.GenerateKDTambahAlat(E);
            if (hasil == null) {
                //do something
            } else {
                txt_id_ekst_alat.setText(hasil);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(FO5_PendaftaranEvent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initModelAwal() {
        mode = 1;
        panel_main.setVisible(true);
        panel_pendaftaran_event.setVisible(false);
        panel_daftarlist_eo.setVisible(false);
        panel_error_report.setVisible(false);
        btn_pilih_eo.setVisible(false);
        btn_pilih_peralatan.setVisible(false);
        btn_pilih_kategori.setVisible(false);
        btn_pilih_ruangan.setVisible(false);
    }

    private void initModelDua() {
        mode = 2;
        panel_main.setVisible(false);
        panel_pendaftaran_event.setVisible(true);
        panel_daftarlist_eo.setVisible(false);
        panel_error_report.setVisible(false);
        btn_pilih_eo.setVisible(true);
        btn_pilih_peralatan.setVisible(true);
        btn_pilih_kategori.setVisible(true);
        btn_pilih_ruangan.setVisible(true);
    }

    private void initModelTiga() {
        mode = 3;
        init_ErrorReport_ID();
        panel_main.setVisible(false);
        panel_pendaftaran_event.setVisible(false);
        panel_daftarlist_eo.setVisible(false);
        panel_error_report.setVisible(true);
    }

    private void ClearFormError() {
        txt_error_id.setText("");
        txt_error_id_emp.setText("");
        txt_error_title.setText("");
        txt_error_description.setText("");
        init_ErrorReport_ID();
        txt_error_title.requestFocus();
    }

    private void ClearFormAfterSave() {
        init_KD_Event();
        txt_id_eo.setText("");
        txt_nama_kegiatan.setText("");
        txt_id_kategori.setText("");
        txt_nama_kategori.setText("");
        txt_id_ruangan.setText("");
        init_KD_Tambah_Alat();
        txt_id_alat.setText("");
        txt_nama_alat.setText("");
        txt_kuantitas.setText("");
        ClearTableTambah();
    }

    private void ClearTableTambah() {
        int baris = tableModelTambahAlat.getRowCount();
        for (int a = 0; a < baris; a++) {
            tableModelTambahAlat.removeRow(0);
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        Daftar_Kategori = new javax.swing.JDialog();
        btn_pilih_kategori = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        tbl_kategori = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        Daftar_Ruangan = new javax.swing.JDialog();
        btn_pilih_ruangan = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        tbl_ruangan = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        Daftar_Event_Organizer = new javax.swing.JDialog();
        btn_pilih_eo = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        tbl_eo = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        Daftar_Peralatan = new javax.swing.JDialog();
        btn_pilih_peralatan = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        tbl_peralatan = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        panel_kanan = new javax.swing.JPanel();
        lbl_btn_cari_ruangan = new javax.swing.JLabel();
        lbl_btn_kembali = new javax.swing.JLabel();
        lbl_btn_formerror = new javax.swing.JLabel();
        base_kanan = new javax.swing.JLabel();
        panel_kiri = new javax.swing.JPanel();
        lbl_btn_list_kategori = new javax.swing.JLabel();
        lbl_btn_formdaftarevent = new javax.swing.JLabel();
        lbl_btn_listdaftar_eo = new javax.swing.JLabel();
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
        panel_pendaftaran_event = new javax.swing.JPanel();
        txt_nama_alat = new javax.swing.JTextField();
        txt_id_alat = new javax.swing.JTextField();
        txt_kuantitas = new javax.swing.JTextField();
        lbl_btn_reset = new javax.swing.JLabel();
        lbl_btn_simpan = new javax.swing.JLabel();
        txt_nama_kegiatan = new javax.swing.JTextField();
        txt_id_event = new javax.swing.JTextField();
        btn_tambah_Alat = new javax.swing.JButton();
        btn_cari_alat = new javax.swing.JButton();
        txt_nama_kategori = new javax.swing.JTextField();
        txt_id_eo = new javax.swing.JTextField();
        txt_id_kategori = new javax.swing.JTextField();
        date_tanggal = new com.toedter.calendar.JDateChooser();
        txt_id_ekst_alat = new javax.swing.JTextField();
        txt_id_ruangan = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_tambah = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        base_pend_eo = new javax.swing.JLabel();
        panel_atas = new javax.swing.JPanel();
        lbl_header = new javax.swing.JLabel();
        panel_bawah = new javax.swing.JPanel();
        lbl_footer = new javax.swing.JLabel();

        Daftar_Kategori.setTitle("Amikom Event");
        Daftar_Kategori.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_pilih_kategori.setText("PILIH");
        btn_pilih_kategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pilih_kategoriActionPerformed(evt);
            }
        });
        Daftar_Kategori.getContentPane().add(btn_pilih_kategori, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 340, 90, -1));

        tbl_kategori.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane6.setViewportView(tbl_kategori);

        Daftar_Kategori.getContentPane().add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 280, 220));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/bG_daftar_kategori_dialog.png"))); // NOI18N
        Daftar_Kategori.getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        Daftar_Ruangan.setTitle("Amikom Event");
        Daftar_Ruangan.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_pilih_ruangan.setText("PILIH");
        btn_pilih_ruangan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pilih_ruanganActionPerformed(evt);
            }
        });
        Daftar_Ruangan.getContentPane().add(btn_pilih_ruangan, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 340, 90, -1));

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
        jScrollPane7.setViewportView(tbl_ruangan);

        Daftar_Ruangan.getContentPane().add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 480, 220));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/bG_daftar_ruangan_dialog_2.png"))); // NOI18N
        Daftar_Ruangan.getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        Daftar_Event_Organizer.setTitle("Amikom Event");
        Daftar_Event_Organizer.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_pilih_eo.setText("PILIH");
        btn_pilih_eo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pilih_eoActionPerformed(evt);
            }
        });
        Daftar_Event_Organizer.getContentPane().add(btn_pilih_eo, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 340, 90, -1));

        tbl_eo.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane8.setViewportView(tbl_eo);

        Daftar_Event_Organizer.getContentPane().add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 480, 220));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/bG_daftar_eo_dialog.png"))); // NOI18N
        Daftar_Event_Organizer.getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        Daftar_Peralatan.setTitle("Amikom Event");
        Daftar_Peralatan.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_pilih_peralatan.setText("PILIH");
        btn_pilih_peralatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pilih_peralatanActionPerformed(evt);
            }
        });
        Daftar_Peralatan.getContentPane().add(btn_pilih_peralatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 340, 90, -1));

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
        jScrollPane9.setViewportView(tbl_peralatan);

        Daftar_Peralatan.getContentPane().add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 280, 220));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/bG_daftar_kategori_dialog.png"))); // NOI18N
        Daftar_Peralatan.getContentPane().add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Amikom Event");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel_kanan.setPreferredSize(new java.awt.Dimension(200, 550));
        panel_kanan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_btn_cari_ruangan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/btn_list_ruangan.png"))); // NOI18N
        lbl_btn_cari_ruangan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_btn_cari_ruangan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_btn_cari_ruanganMouseClicked(evt);
            }
        });
        panel_kanan.add(lbl_btn_cari_ruangan, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, -1));

        lbl_btn_kembali.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/btn_kembali.png"))); // NOI18N
        lbl_btn_kembali.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_btn_kembali.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_btn_kembaliMouseClicked(evt);
            }
        });
        panel_kanan.add(lbl_btn_kembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 390, -1, -1));

        lbl_btn_formerror.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/btn_app_error_report.png"))); // NOI18N
        lbl_btn_formerror.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_btn_formerror.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_btn_formerrorMouseClicked(evt);
            }
        });
        panel_kanan.add(lbl_btn_formerror, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, -1, -1));

        base_kanan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/panel_kanan.png"))); // NOI18N
        panel_kanan.add(base_kanan, new org.netbeans.lib.awtextra.AbsoluteConstraints(-9, 0, 210, -1));

        getContentPane().add(panel_kanan, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 100, -1, -1));

        panel_kiri.setPreferredSize(new java.awt.Dimension(200, 550));
        panel_kiri.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_btn_list_kategori.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/btn_list_kategori.png"))); // NOI18N
        lbl_btn_list_kategori.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_btn_list_kategori.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_btn_list_kategoriMouseClicked(evt);
            }
        });
        panel_kiri.add(lbl_btn_list_kategori, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 370, -1, -1));

        lbl_btn_formdaftarevent.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/btn_frm_daftar_event.png"))); // NOI18N
        lbl_btn_formdaftarevent.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_btn_formdaftarevent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_btn_formdaftareventMouseClicked(evt);
            }
        });
        panel_kiri.add(lbl_btn_formdaftarevent, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, -1, -1));

        lbl_btn_listdaftar_eo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/DaftarEventB1.png"))); // NOI18N
        lbl_btn_listdaftar_eo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_btn_listdaftar_eo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_btn_listdaftar_eoMouseClicked(evt);
            }
        });
        panel_kiri.add(lbl_btn_listdaftar_eo, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, -1, -1));

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

        panel_pendaftaran_event.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_nama_alat.setEnabled(false);
        panel_pendaftaran_event.add(txt_nama_alat, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 200, 210, 30));

        txt_id_alat.setEnabled(false);
        txt_id_alat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_id_alatMouseClicked(evt);
            }
        });
        panel_pendaftaran_event.add(txt_id_alat, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 200, 80, 30));
        panel_pendaftaran_event.add(txt_kuantitas, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 200, 80, 30));

        lbl_btn_reset.setFont(new java.awt.Font("Gotham Light", 0, 20)); // NOI18N
        lbl_btn_reset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/btn_reset2.png"))); // NOI18N
        lbl_btn_reset.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_btn_reset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_btn_resetMouseClicked(evt);
            }
        });
        panel_pendaftaran_event.add(lbl_btn_reset, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 480, -1, -1));

        lbl_btn_simpan.setFont(new java.awt.Font("Gotham Light", 0, 20)); // NOI18N
        lbl_btn_simpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/btn_simpan4.png"))); // NOI18N
        lbl_btn_simpan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_btn_simpan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_btn_simpanMouseClicked(evt);
            }
        });
        panel_pendaftaran_event.add(lbl_btn_simpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 480, -1, -1));

        txt_nama_kegiatan.setFont(new java.awt.Font("Gotham Light", 0, 14)); // NOI18N
        txt_nama_kegiatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_nama_kegiatanKeyPressed(evt);
            }
        });
        panel_pendaftaran_event.add(txt_nama_kegiatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 240, 30));

        txt_id_event.setFont(new java.awt.Font("Gotham Light", 0, 14)); // NOI18N
        txt_id_event.setEnabled(false);
        txt_id_event.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_id_eventKeyPressed(evt);
            }
        });
        panel_pendaftaran_event.add(txt_id_event, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 240, 30));

        btn_tambah_Alat.setText("TAMBAH ALAT");
        btn_tambah_Alat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambah_AlatActionPerformed(evt);
            }
        });
        panel_pendaftaran_event.add(btn_tambah_Alat, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 250, -1, -1));

        btn_cari_alat.setText("CARI ALAT");
        btn_cari_alat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cari_alatActionPerformed(evt);
            }
        });
        panel_pendaftaran_event.add(btn_cari_alat, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 250, -1, -1));

        txt_nama_kategori.setFont(new java.awt.Font("Gotham Light", 0, 14)); // NOI18N
        txt_nama_kategori.setEnabled(false);
        panel_pendaftaran_event.add(txt_nama_kategori, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 330, 130, 30));

        txt_id_eo.setFont(new java.awt.Font("Gotham Light", 0, 14)); // NOI18N
        txt_id_eo.setEnabled(false);
        panel_pendaftaran_event.add(txt_id_eo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 240, 30));

        txt_id_kategori.setFont(new java.awt.Font("Gotham Light", 0, 14)); // NOI18N
        txt_id_kategori.setEnabled(false);
        panel_pendaftaran_event.add(txt_id_kategori, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, 90, 30));
        panel_pendaftaran_event.add(date_tanggal, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 480, 240, 30));

        txt_id_ekst_alat.setFont(new java.awt.Font("Gotham Light", 0, 14)); // NOI18N
        txt_id_ekst_alat.setEnabled(false);
        panel_pendaftaran_event.add(txt_id_ekst_alat, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 120, 240, 30));

        txt_id_ruangan.setFont(new java.awt.Font("Gotham Light", 0, 14)); // NOI18N
        txt_id_ruangan.setEnabled(false);
        panel_pendaftaran_event.add(txt_id_ruangan, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 400, 240, 30));

        tbl_tambah.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tbl_tambah);

        panel_pendaftaran_event.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 290, 410, 130));

        jLabel15.setFont(new java.awt.Font("Gotham Light", 0, 20)); // NOI18N
        jLabel15.setText("Jumlah");
        panel_pendaftaran_event.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 170, -1, -1));

        jLabel14.setFont(new java.awt.Font("Gotham Light", 0, 20)); // NOI18N
        jLabel14.setText("Nama Alat");
        panel_pendaftaran_event.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 170, -1, -1));

        jLabel13.setFont(new java.awt.Font("Gotham Light", 0, 20)); // NOI18N
        jLabel13.setText("ID Alat");
        panel_pendaftaran_event.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 170, -1, -1));

        jLabel12.setFont(new java.awt.Font("Gotham Light", 0, 20)); // NOI18N
        jLabel12.setText("Tambahan Peralatan");
        panel_pendaftaran_event.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 90, -1, -1));

        jLabel11.setFont(new java.awt.Font("Gotham Light", 0, 20)); // NOI18N
        jLabel11.setText("Tanggal");
        panel_pendaftaran_event.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 450, -1, -1));

        jLabel10.setFont(new java.awt.Font("Gotham Light", 0, 20)); // NOI18N
        jLabel10.setText("Ruangan");
        panel_pendaftaran_event.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 370, -1, -1));

        jLabel9.setFont(new java.awt.Font("Gotham Light", 0, 20)); // NOI18N
        jLabel9.setText("Ruangan");
        panel_pendaftaran_event.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 370, -1, -1));

        jLabel8.setFont(new java.awt.Font("Gotham Light", 0, 20)); // NOI18N
        jLabel8.setText("Event ID");
        panel_pendaftaran_event.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, -1, -1));

        jLabel7.setFont(new java.awt.Font("Gotham Light", 0, 20)); // NOI18N
        jLabel7.setText("Kategori");
        panel_pendaftaran_event.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, -1, -1));

        jLabel5.setFont(new java.awt.Font("Gotham Light", 0, 20)); // NOI18N
        jLabel5.setText("Nama Kegiatan / Event");
        panel_pendaftaran_event.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, -1, -1));

        jLabel3.setFont(new java.awt.Font("Gotham Light", 0, 20)); // NOI18N
        jLabel3.setText("Event Organizer ID");
        panel_pendaftaran_event.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, -1, -1));

        jLabel2.setFont(new java.awt.Font("Gotham Bold", 0, 24)); // NOI18N
        jLabel2.setText("PENDAFTARAN EVENT / KEGIATAN");
        panel_pendaftaran_event.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 30, -1, -1));

        base_pend_eo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/panel_main.png"))); // NOI18N
        panel_pendaftaran_event.add(base_pend_eo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(panel_pendaftaran_event, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 100, -1, -1));

        panel_atas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_header.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/headerpendaftaranevent.png"))); // NOI18N
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

    private void lbl_btn_formdaftareventMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_formdaftareventMouseClicked
        // TODO add your handling code here:
        initModelDua();
        
    }//GEN-LAST:event_lbl_btn_formdaftareventMouseClicked

    private void lbl_btn_simpanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_simpanMouseClicked
        // TODO add your handling code here:
        Event E = new Event();
        E.setEvent_id(txt_id_event.getText());
        E.setEvent_nama(txt_nama_kegiatan.getText());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        E.setEvent_tanggal(sdf.format(date_tanggal.getDate()));
        E.setEO_id(txt_id_eo.getText());
        E.setEvent_kategori(txt_id_kategori.getText());
        E.setEvent_ruangan(txt_id_ruangan.getText());
        E.setTambah_alat(txt_id_ekst_alat.getText());

        try {
            int hasil = eventDao.saveDataEvent(E);
            if (hasil == 1) {

            } else {
                evt.consume();
                getToolkit().beep();
                JOptionPane.showMessageDialog(null, "Save Data Gagal", "Save Gagal", JOptionPane.ERROR_MESSAGE);

            }
        } catch (RemoteException ex) {
            Logger.getLogger(FO5_PendaftaranEvent.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Save Data Sarana Prasarana
        try {
            int rowCount = tbl_tambah.getRowCount();
            int column = tbl_tambah.getColumnCount();
            for (int row = 0; row < rowCount; row++) {
                E.setTambah_alat(txt_id_ekst_alat.getText());
                E.setId_alat(tbl_tambah.getValueAt(row, 0).toString());
                E.setKuantitas(Integer.parseInt(tbl_tambah.getValueAt(row, 2).toString()));
                eventDao.saveDataDetilTambahAlat(E);
            }

            // tampilkan pesan berhasil
        } catch (RemoteException ex) {
            Logger.getLogger(FO5_PendaftaranEvent.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            int hasil2 = eventDao.updateRuangan(E);
            if (hasil2 == 1) {
                JOptionPane.showMessageDialog(this, "Save Data Event Sukses", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            } else {
                evt.consume();
                getToolkit().beep();
                JOptionPane.showMessageDialog(null, "Update Data Detil Ruangan Gagal", "Update Gagal", JOptionPane.ERROR_MESSAGE);

            }
        } catch (RemoteException ex) {
            Logger.getLogger(FO5_PendaftaranEvent.class.getName()).log(Level.SEVERE, null, ex);
        }
        ClearFormAfterSave();
    }//GEN-LAST:event_lbl_btn_simpanMouseClicked

    private void lbl_btn_resetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_resetMouseClicked
        // TODO add your handling code here:
        ClearFormAfterSave();
    }//GEN-LAST:event_lbl_btn_resetMouseClicked

    private void txt_nama_kegiatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nama_kegiatanKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            txt_id_event.requestFocus();
        }
    }//GEN-LAST:event_txt_nama_kegiatanKeyPressed

    private void txt_id_eventKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_id_eventKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_id_eventKeyPressed

    private void lbl_btn_list_kategoriMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_list_kategoriMouseClicked
        // TODO add your handling code here:
        Daftar_Kategori.pack();
        Daftar_Kategori.setLocationRelativeTo(this);
        Daftar_Kategori.setModal(true);
        Daftar_Kategori.setVisible(true);
        loadDaftarKategori();
        mode = 6;
        btn_pilih_kategori.setVisible(true);
    }//GEN-LAST:event_lbl_btn_list_kategoriMouseClicked

    private void lbl_btn_cariMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_cariMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lbl_btn_cariMouseClicked

    private void lbl_resetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_resetMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_lbl_resetMouseClicked

    private void lbl_btn_formerrorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_formerrorMouseClicked
        // TODO add your handling code here:
        initModelTiga();
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
                Logger.getLogger(FO5_PendaftaranEvent.class.getName()).log(Level.SEVERE, null, ex);
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
        } else if (mode == 2 || mode == 3 || mode == 4 || mode ==5 || mode ==6 || mode ==7) {
            initModelAwal();
        }

    }//GEN-LAST:event_lbl_btn_kembaliMouseClicked

    private void lbl_iconformMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_iconformMouseClicked
        // TODO add your handling code here:
        FO3_Menu Menu = new FO3_Menu();
        Menu.setVisible(true);
        dispose();
    }//GEN-LAST:event_lbl_iconformMouseClicked

    private void txt_id_alatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_id_alatMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_id_alatMouseClicked

    private void lbl_btn_listdaftar_eoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_listdaftar_eoMouseClicked
        // TODO add your handling code here:
        Daftar_Event_Organizer.pack();
        Daftar_Event_Organizer.setLocationRelativeTo(this);
        Daftar_Event_Organizer.setModal(true);
        Daftar_Event_Organizer.setVisible(true);
        loadDaftarEo();
        mode = 5;
        btn_pilih_eo.setVisible(true);
    }//GEN-LAST:event_lbl_btn_listdaftar_eoMouseClicked

    private void lbl_btn_cari_ruanganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_btn_cari_ruanganMouseClicked
        // TODO add your handling code here:
        Daftar_Ruangan.pack();
        Daftar_Ruangan.setLocationRelativeTo(this);
        Daftar_Ruangan.setModal(true);
        Daftar_Ruangan.setVisible(true);
        loadDaftarRuangan();
        mode = 7;
        btn_pilih_ruangan.setVisible(true);
    }//GEN-LAST:event_lbl_btn_cari_ruanganMouseClicked

    private void btn_pilih_eoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pilih_eoActionPerformed
        // TODO add your handling code here:
        int row = tbl_eo.getSelectedRow();
        EventOrganizer EO = recordEo.get(row);

        txt_id_eo.setText(EO.getEo_id());
        Daftar_Event_Organizer.setVisible(false);
    }//GEN-LAST:event_btn_pilih_eoActionPerformed

    private void btn_pilih_kategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pilih_kategoriActionPerformed
        // TODO add your handling code here:
        int row = tbl_kategori.getSelectedRow();
        Kategori K = recordKategori.get(row);

        txt_id_kategori.setText(K.getId_kat());
        txt_nama_kategori.setText(K.getNama_kat());
        Daftar_Kategori.setVisible(false);
    }//GEN-LAST:event_btn_pilih_kategoriActionPerformed

    private void btn_pilih_ruanganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pilih_ruanganActionPerformed
        // TODO add your handling code here:
        int row = tbl_ruangan.getSelectedRow();
        Ruangan R = recordRuangan.get(row);

        txt_id_ruangan.setText(R.getKd_daftar());
        Daftar_Ruangan.setVisible(false);
    }//GEN-LAST:event_btn_pilih_ruanganActionPerformed

    private void btn_pilih_peralatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pilih_peralatanActionPerformed
        // TODO add your handling code here:
        int row = tbl_peralatan.getSelectedRow();
        Peralatan P = recordperalatan.get(row);

        txt_id_alat.setText(P.getPeralatan_id());
        txt_nama_alat.setText(P.getPeralatan_nama());

        Daftar_Peralatan.setVisible(false);
    }//GEN-LAST:event_btn_pilih_peralatanActionPerformed

    private void btn_cari_alatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cari_alatActionPerformed
        // TODO add your handling code here:
        Daftar_Peralatan.pack();
        Daftar_Peralatan.setLocationRelativeTo(this);
        Daftar_Peralatan.setModal(true);
        Daftar_Peralatan.setVisible(true);
        loadDaftarPeralatan();
    }//GEN-LAST:event_btn_cari_alatActionPerformed

    private void btn_tambah_AlatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambah_AlatActionPerformed
        // TODO add your handling code here:
        String kodeAlat = txt_id_alat.getText();
        String namaAlat = txt_nama_alat.getText();
        int qty = Integer.parseInt(txt_kuantitas.getText());

        // pencarian jika ada barang yang sama
        int row = getRowByValue(tableModelTambahAlat, kodeAlat);
        if (row >= 0) {
            // update data item yg ada di tabel
            qty = qty + 0;
            tableModelTambahAlat.setValueAt(qty, row, 2);
        } else {

            // ambil nomor urut terakhir
            Object[] objects = new Object[DaftarTambahAlatcolumNames.length];
            objects[0] = kodeAlat;
            objects[1] = namaAlat;
            objects[2] = qty;

            // tambahkan data item ke dalam tabel
            tableModelTambahAlat.addRow(objects);
        }
    }//GEN-LAST:event_btn_tambah_AlatActionPerformed

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
            java.util.logging.Logger.getLogger(FO5_PendaftaranEvent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FO5_PendaftaranEvent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FO5_PendaftaranEvent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FO5_PendaftaranEvent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FO5_PendaftaranEvent().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog Daftar_Event_Organizer;
    private javax.swing.JDialog Daftar_Kategori;
    private javax.swing.JDialog Daftar_Peralatan;
    private javax.swing.JDialog Daftar_Ruangan;
    private javax.swing.JLabel base_kanan;
    private javax.swing.JLabel base_kiri;
    private javax.swing.JLabel base_panel_main;
    private javax.swing.JLabel base_pend_eo;
    private javax.swing.JLabel base_pend_eo1;
    private javax.swing.JLabel base_pend_eo2;
    private javax.swing.JButton btn_cari_alat;
    private javax.swing.JLabel btn_chatme;
    private javax.swing.JButton btn_pilih_eo;
    private javax.swing.JButton btn_pilih_kategori;
    private javax.swing.JButton btn_pilih_peralatan;
    private javax.swing.JButton btn_pilih_ruangan;
    private javax.swing.JButton btn_tambah_Alat;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cb_state;
    private com.toedter.calendar.JDateChooser date_tanggal;
    private javax.swing.JPanel icon_form;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JLabel lbl_btn_cari;
    private javax.swing.JLabel lbl_btn_cari_ruangan;
    private javax.swing.JLabel lbl_btn_formdaftarevent;
    private javax.swing.JLabel lbl_btn_formerror;
    private javax.swing.JLabel lbl_btn_kembali;
    private javax.swing.JLabel lbl_btn_list_kategori;
    private javax.swing.JLabel lbl_btn_listdaftar_eo;
    private javax.swing.JLabel lbl_btn_reset;
    private javax.swing.JLabel lbl_btn_simpan;
    private javax.swing.JLabel lbl_footer;
    private javax.swing.JLabel lbl_header;
    private javax.swing.JLabel lbl_iconform;
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
    private javax.swing.JPanel panel_pendaftaran_event;
    private javax.swing.JTable tbl_daftar_eo;
    private javax.swing.JTable tbl_eo;
    private javax.swing.JTable tbl_kategori;
    private javax.swing.JTable tbl_peralatan;
    private javax.swing.JTable tbl_ruangan;
    private javax.swing.JTable tbl_tambah;
    private javax.swing.JTextField txt_cari_eo;
    private javax.swing.JTextArea txt_error_description;
    private javax.swing.JTextField txt_error_id;
    private javax.swing.JTextField txt_error_id_emp;
    private javax.swing.JTextField txt_error_title;
    private javax.swing.JTextField txt_id_alat;
    private javax.swing.JTextField txt_id_ekst_alat;
    private javax.swing.JTextField txt_id_eo;
    private javax.swing.JTextField txt_id_event;
    private javax.swing.JTextField txt_id_kategori;
    private javax.swing.JTextField txt_id_ruangan;
    private javax.swing.JTextField txt_kuantitas;
    private javax.swing.JTextField txt_nama_alat;
    private javax.swing.JTextField txt_nama_kategori;
    private javax.swing.JTextField txt_nama_kegiatan;
    // End of variables declaration//GEN-END:variables

}
