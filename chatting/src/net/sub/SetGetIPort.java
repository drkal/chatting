/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sub;

/**
 *
 * @author Panos
 */
public class SetGetIPort {

    public static String IP;
    public static int Port;
    public static String UserName;

    public static String getIP() {
        return IP;
    }

    public static void setIP(String IP) {
        SetGetIPort.IP = IP;
    }

    public static int getPort() {
        return Port;
    }

    public static void setPort(String Port) {
        if ("".equals(Port)) {
            SetGetIPort.Port = 0;
        } else {
            SetGetIPort.Port = Integer.parseInt(Port);
        }
    }

    public static String getUserName() {
        return UserName;
    }

    public static void setUserName(String UserName) {
        SetGetIPort.UserName = UserName;
    }
}
