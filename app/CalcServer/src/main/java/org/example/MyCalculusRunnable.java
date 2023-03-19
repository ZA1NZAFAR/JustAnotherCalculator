package org.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class MyCalculusRunnable implements Runnable {
    private Socket sock;

    public MyCalculusRunnable(Socket s) {
        sock = s;
    }

    @Override
    public void run() {
        try {
            DataInputStream dis = new DataInputStream(sock.getInputStream());
            DataOutputStream dos = new DataOutputStream(sock.getOutputStream());

            String expression = dis.readUTF();
            System.out.println("Received expression: " + expression);

            try {
                double res = CalculusServer.doOp(expression);
                System.out.println("Result: " + res);
                dos.writeBoolean(false); // Indicate that the next data is not an exception message
                dos.writeDouble(res); // Send the result
            } catch (Exception e) {
                System.out.println("Exception: " + e.getMessage());
                dos.writeBoolean(true); // Indicate that the next data is an exception message
                dos.writeUTF(e.getMessage()); // Send the exception message
            }

            dos.flush();
            dis.close();
            dos.close();
            sock.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}