/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ninhhoangcuong.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ninhh
 */
public class Server {

    private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_";
    private static Random ran = null;

    public static String RandomString() {
        ran = new Random();
        //size of stringBuilder
        int n = ran.nextInt(20);
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {

            /* generate a random number between 
            0 to AlphaNumericString variable length */
            int index = (int) (ALPHABET.length() * Math.random());
            // add Character one by one in end of sb 
            sb.append(ALPHABET.charAt(index));
        }
        return sb.toString();
    }

    public static int RandomNumber() {
        ran = new Random();
        return ran.nextInt(100);
    }

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

    public static boolean checkResult(int a, int b, String requestIDCL, String requestIDSV, int gcd, int lcm, int sum, int mul) {
        if (requestIDCL.equals(requestIDSV) == true) {
            if (gcd == GCD(a, b)) {
                if ((a * b) / GCD(a, b) == lcm) {
                    if (a + b == sum && a * b == mul) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        try {
            DatagramSocket socketServer = null;
            int port = 1107;
            byte[] send, receive;
            DatagramPacket sendPacket, receivePacket;

            //open server
            socketServer = new DatagramSocket(port);
            System.out.println("Server is running........");
            //lisstening
            while (true) {

                //khoi tao bo dem cua cac goi tin gui va nhan
                send = new byte[1024];
                receive = new byte[1024];
                //Dinh nghia goi tin nhan duoc tu client
                receivePacket = new DatagramPacket(receive, receive.length);
                //nhan goi tin tu client
                socketServer.receive(receivePacket);
                String infoClient = new String(receivePacket.getData()).trim();
                String[] result = infoClient.split(";");
                for (String i : result) {
                    System.out.println(i);
                }

                /*chuan bi gui cho client
                random number*/
                int a = RandomNumber();
                int b = RandomNumber();
                //random String
                String requestId = RandomString();
                //lay thong tin cua client trong goi tin vua nhan duoc
                InetAddress inetAddress = receivePacket.getAddress();
                //dinh nghia goi tin gui lai cho client
                String sendSTR = requestId + ";" + a + "," + b;
                send = sendSTR.getBytes();
                sendPacket = new DatagramPacket(send, send.length, inetAddress, receivePacket.getPort());
                //send to client
                socketServer.send(sendPacket);
                System.out.println("Send : " + sendSTR);

                //nhan lai du lieu tu client va so sanh
                receive = new byte[1024];
                //Dinh nghia goi tin nhan duoc tu client
                receivePacket = new DatagramPacket(receive, receive.length);
                //nhan goi tin tu client
                socketServer.receive(receivePacket);
                String infoClient2 = new String(receivePacket.getData()).trim();
                result = infoClient2.split("[;,]");
                for (String i : result) {
                    System.out.println(i);
                }
                int[] GT = new int[result.length];
                //convert to int
                for (int i = 0; i < result.length - 1; i++) {
                    GT[i] = Integer.parseUnsignedInt(result[i + 1]);
                }
                System.out.println("Result : " + checkResult(a, b, result[0], requestId, GT[0], GT[1], GT[2], GT[3]));
            }
        } catch (SocketException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
