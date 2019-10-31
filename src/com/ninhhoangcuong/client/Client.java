/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ninhhoangcuong.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ninhh
 */
public class Client {

    public static int GCD(int a, int b) {
        if (a == 0 || b == 0) {
            return a + b;
        }
        while (a != b) {
            if (a > b) {
                a = a - b;
            } else {
                b = b - a;
            }
        }
        return a;
    }

    public static void main(String[] args) {
        try {
            DatagramSocket socketClient = null;
            DatagramPacket recievePacket, sendPacket = null;
            byte[] recieve, send;
            int port = 1107;
            String hostName = "localhost";
            int qCode = 100;
            String studentCode = "B16DCCN046";
            int a,b,sum,mul,lcd;
            String requestId = null;
            //connect
            socketClient = new DatagramSocket();
            System.out.println("Client is running ...........");

            //gui ma sv
            recieve = new byte[1024];
            send = new byte[1024];

            InetAddress ia = InetAddress.getByName(hostName);
            //Dinh nghia packet gui di
            String str1 = ";" + studentCode + ";" + qCode;
            send = str1.getBytes();
            sendPacket = new DatagramPacket(send, send.length, ia, port);
            //gui toi server
            socketClient.send(sendPacket);
            System.out.println("Send sucsessfully !");

            //nhan yeu cau tu server
            //Dinh nghia packet nhan
            recievePacket = new DatagramPacket(recieve, recieve.length);
            //nhan du lieu
            socketClient.receive(recievePacket);
            String recieveString = new String(recievePacket.getData()).trim();
            String[] result = recieveString.split("[;,]");
            for (String i : result) {
                System.out.println(i);
            }
            requestId = result[0];
            a =  Integer.parseInt(result[1]);
            b =  Integer.parseInt(result[2]);
            //tinh toan gui lai server
            sum=a+b;
            mul=a*b;
            lcd=mul/GCD(a, b);
            recieve = new byte[1024];
            send = new byte[1024];

            //Dinh nghia packet gui di
            String str2 = requestId+";" + GCD(a, b) + ","+lcd+","+sum+","+mul;
            send = str2.getBytes();
            sendPacket = new DatagramPacket(send, send.length,recievePacket.getSocketAddress());
            System.out.println(str2);
            socketClient.send(sendPacket);
            System.out.println("Send sucsessfully !");
            socketClient.close();
        } catch (SocketException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
