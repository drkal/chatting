/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sub;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author kalognomas
 */
public class clientThread extends Thread implements Runnable {

    public GuiClient myFather;
    public static BufferedReader in;
    public static PrintWriter out;
    public static Socket socket;

    public clientThread(GuiClient father) {

        this.myFather = father;
    }

    public void connectedOptions() {
        GuiClient.textField.setEditable(true);
        GuiClient.textField.setBackground(Color.GREEN);
        GuiClient.messageArea.setBackground(Color.BLACK);
        GuiClient.connect.setEnabled(false);
        GuiClient.disConnect.setEnabled(true);
    }

    public void notConnectedOptions() {
        GuiClient.textField.setEditable(false);
        GuiClient.textField.setBackground(Color.MAGENTA);
        GuiClient.messageArea.setBackground(Color.MAGENTA);
        GuiClient.connect.setEnabled(true);
        GuiClient.disConnect.setEnabled(false);
    }

    public void getReport() {
        this.myFather.setReport("I'm Ok daddy...");
    }

    @Override
    public void run() {
        try {
            String serverAddress = getServerAddress();
            socket = new Socket(serverAddress, SetGetIPort.getPort());
            in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);



            while (true) {
                String line = in.readLine();
                if (line.startsWith("SUBMITNAME")) {
                    out.println(getNamed());
                } else if (line.startsWith("NAMEACCEPTED")) {
                   this.connectedOptions();
                } else if (line.startsWith("NAMES")) {
                    if (!GuiClient.nameArea.getText().contains(line.substring(5))) {
                        GuiClient.nameArea.append(line.substring(5) + "\n");
                    }                   

                } else if (line.startsWith("MESSAGE")) {
                    GuiClient.messageArea.append(line.substring(8) + "\n");
                    GuiClient.nameArea.setText("");
                } else if (line.startsWith("DISCONNECT%%$$")) {
                    System.out.println("1");
                    this.in.close();
                    this.out.flush();
                    this.out.close();
                    this.socket.close();
                    this.interrupt();
                    System.out.println("2");
//                    GuiClient.gui.dispose();
//                    GuiClient.gui = new GuiClient();
//                    GuiClient.gui.setVisible(true);
//                    GuiClient.gui.setLocation(400, 200);
                    JOptionPane.showMessageDialog(myFather, "The Chatserver is DOWN!");
                }else if(line.startsWith("REJECTION%%##")){
                   
                    this.in.close();
                    this.out.flush();
                    this.out.close();
                    socket.close();
                    this.interrupt();
                    System.out.println("2");
//                    GuiClient.gui.dispose();
//                    GuiClient.gui = new GuiClient();
//                    GuiClient.gui.setVisible(true);
//                    GuiClient.gui.setLocation(400, 200);
                    JOptionPane.showMessageDialog(myFather, "The name you entered\nalready exists!");
                
                }
            }
            
        }catch(NullPointerException n){
            System.out.println("null catched");
        }catch(ConnectException c){
                    GuiClient.gui.dispose();
                    GuiClient.gui = new GuiClient();
                    GuiClient.gui.setVisible(true);
                    GuiClient.gui.setLocation(400, 200);
                    JOptionPane.showMessageDialog(myFather, "Connection Error!");
        } catch (UnknownHostException ex) {
            Logger.getLogger(clientThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
             GuiClient.gui.dispose();
                    GuiClient.gui = new GuiClient();
                    GuiClient.gui.setVisible(true);
                    GuiClient.gui.setLocation(400, 200);
                    System.out.println("Con Error");
        } finally {
            try {
                in.close();
                out.close();
            }catch(NullPointerException n){
                System.out.println("null catched");
            } catch (IOException ex) {
                Logger.getLogger(clientThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private String getServerAddress() {
        return SetGetIPort.getIP();
    }

    private String getNamed() {
        String k = SetGetIPort.getUserName();
        if (k == null) {
            getNamed();

        }
        return k;

    }
}
