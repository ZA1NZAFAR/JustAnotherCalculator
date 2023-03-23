package ovh.zain.calculator.tools;

import android.os.Handler;
import android.os.Looper;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Calculator {

    public static void evaluateAsyncWithHandler(final String expression, final CalculatorCallback callback) {
        new Thread(() -> {
            try {
                Socket conn = new Socket("10.0.2.2", 9876);

                DataInputStream dis = new DataInputStream(conn.getInputStream());
                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

                dos.writeUTF(expression); // send the expression to the server

                boolean isException = dis.readBoolean(); // read if there is an exception
                if (isException) { // if there is an exception read the exception message and throw it in the main thread
                    String exceptionMessage = dis.readUTF();
                    new Handler(Looper.getMainLooper()).post(() -> callback.onError(new Exception(exceptionMessage)));
                } else {// if there is no exception read the result and send it to the main thread
                    double res = dis.readDouble();
                    new Handler(Looper.getMainLooper()).post(() -> callback.onSuccess(res));
                }

                dis.close();
                dos.close();
            } catch (Exception e) {
                new Handler(Looper.getMainLooper()).post(() -> callback.onError(e));
            }
        }).start();
    }




    public interface ResultCallback {
        void onResult(double result);
    }

    public interface CalculatorCallback {
        void onSuccess(double result);

        void onError(Exception e);
    }
}