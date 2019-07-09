/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ae.ui.bo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Aditya
 */
public class Extension_Chat_Server extends javax.swing.JFrame {

    /**
     * Creates new form Extension_Chat_Server
     */
    public Extension_Chat_Server() {
        initComponents();
        setDefaultCloseOperation(Extension_Chat_Server.DISPOSE_ON_CLOSE);
        start_chat_server();
    }

    ArrayList clientOutputStreams;
    ArrayList<String> users;

    private void start_chat_server() {
        Thread starter = new Thread(new ServerStart());
        starter.start();

        txt_status.setText("Sedang Berjalan");
    }

    public class ClientHandler implements Runnable {

        BufferedReader reader;
        Socket sock;
        PrintWriter client;

        public ClientHandler(Socket clientSocket, PrintWriter user) {
            client = user;
            try {
                sock = clientSocket;
                InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(isReader);
            } catch (Exception ex) {
                txt_state.append("Unexpected error... \n");
            }

        }

        @Override
        public void run() {
            String message, connect = "Connect", disconnect = "Disconnect", chat = "Chat";
            String[] data;

            try {
                while ((message = reader.readLine()) != null) {
                    txt_state.append("Received : " + message + "\n");
                    data = message.split(":");

//                    for (String token : data) {
//                        txt_state.append(token + "\n");
//                    }

                    if (data[2].equals(connect)) {
                        tellEveryone((data[0] + ": " + data[1] + ":" + chat));
                        userAdd(data[0]);
                    } else if (data[2].equals(disconnect)) {
                        tellEveryone((data[0] + ": Meninggalkan Obrolan." + ":" + chat));
                        userRemove(data[0]);
                    } else if (data[2].equals(chat)) {
                        tellEveryone(message);
                    } else {
                        txt_state.append("No Conditions were met. \n");
                    }
                }
            } catch (Exception ex) {
                txt_state.append("Koneksi Tidak Ditemukan. \n");
                ex.printStackTrace();

            }
        }
    }

    public class ServerStart implements Runnable {

        @Override
        public void run() {
            clientOutputStreams = new ArrayList();
            users = new ArrayList();

            try {
                ServerSocket serverSock = new ServerSocket(2222);

                while (true) {
                    Socket clientSock = serverSock.accept();
                    PrintWriter writer = new PrintWriter(clientSock.getOutputStream());
                    clientOutputStreams.add(writer);

                    Thread listener = new Thread(new ClientHandler(clientSock, writer));
                    listener.start();
                    txt_state.append("Got a connection. \n");
                }
            } catch (Exception ex) {
                txt_state.append("Error making a connection. \n");
            }
        }
    }

    public void userAdd(String data) {
        String message, add = ": :Connect", done = "Server: :Done", name = data;
        txt_state.append("Before " + name + " added. \n");
        users.add(name);
        txt_state.append("After " + name + " added. \n");
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);

        for (String token : tempList) {
            message = (token + add);
            tellEveryone(message);
        }
        tellEveryone(done);
    }

    public void userRemove(String data) {
        String message, add = ": :Connect", done = "Server: :Done", name = data;
        users.remove(name);
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);

        for (String token : tempList) {
            message = (token + add);
            tellEveryone(message);
        }
        tellEveryone(done);
    }

    public void tellEveryone(String message) {
        Iterator it = clientOutputStreams.iterator();

        while (it.hasNext()) {
            try {
                PrintWriter writer = (PrintWriter) it.next();
                writer.println(message);
                txt_state.append("Sending: " + message + "\n");
                writer.flush();
                txt_state.setCaretPosition(txt_state.getDocument().getLength());

            } catch (Exception ex) {
                txt_state.append("Error telling everyone. \n");
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txt_status = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_state = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        btn_stop_server = new javax.swing.JLabel();
        btn_clear = new javax.swing.JLabel();
        btn_online_user = new javax.swing.JLabel();
        bg_chat_server = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat Now - Server");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_status.setFont(new java.awt.Font("Gotham Light", 0, 14)); // NOI18N
        txt_status.setEnabled(false);
        getContentPane().add(txt_status, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 77, 200, -1));

        txt_state.setColumns(20);
        txt_state.setRows(5);
        txt_state.setEnabled(false);
        jScrollPane1.setViewportView(txt_state);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 380, 290));

        jLabel1.setFont(new java.awt.Font("Gotham Light", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Status :");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        btn_stop_server.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/chat_btn_stop_server.png"))); // NOI18N
        btn_stop_server.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_stop_server.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_stop_serverMouseClicked(evt);
            }
        });
        getContentPane().add(btn_stop_server, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 430, -1, -1));

        btn_clear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/chat_btn_clear.png"))); // NOI18N
        btn_clear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_clear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_clearMouseClicked(evt);
            }
        });
        getContentPane().add(btn_clear, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 430, -1, -1));

        btn_online_user.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/chat_btn_online_user.png"))); // NOI18N
        btn_online_user.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_online_user.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_online_userMouseClicked(evt);
            }
        });
        getContentPane().add(btn_online_user, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, -1, -1));

        bg_chat_server.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ae/image/templatechatnewserver.png"))); // NOI18N
        getContentPane().add(bg_chat_server, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_stop_serverMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_stop_serverMouseClicked
        // TODO add your handling code here:
        try {
            Thread.sleep(1000);                 //5000 milliseconds is five second.
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        tellEveryone("Server:is stopping and all users will be disconnected.\n:Chat");
        txt_state.append("Server stopping... \n");
        //ta_chat.setText("");
    }//GEN-LAST:event_btn_stop_serverMouseClicked

    private void btn_clearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_clearMouseClicked
        // TODO add your handling code here:
        txt_state.setText("");
    }//GEN-LAST:event_btn_clearMouseClicked

    private void btn_online_userMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_online_userMouseClicked
        // TODO add your handling code here:
                txt_state.append("\n Online users : \n");
        for (String current_user : users)
        {
            txt_state.append(current_user);
            txt_state.append("\n");
        }    
    }//GEN-LAST:event_btn_online_userMouseClicked

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
            java.util.logging.Logger.getLogger(Extension_Chat_Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Extension_Chat_Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Extension_Chat_Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Extension_Chat_Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Extension_Chat_Server().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bg_chat_server;
    private javax.swing.JLabel btn_clear;
    private javax.swing.JLabel btn_online_user;
    private javax.swing.JLabel btn_stop_server;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txt_state;
    private javax.swing.JTextField txt_status;
    // End of variables declaration//GEN-END:variables
}
