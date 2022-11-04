package com.example.seniordesignandroidapp;


import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.SystemClock;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ConnectedThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;
    private final Handler mHandler;

    public ConnectedThread(BluetoothSocket socket, Handler handler) {
        mmSocket = socket;
        mHandler = handler;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        // Get the input and output streams, using temp objects because
        // member streams are final
        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) { }

        mmInStream = tmpIn;
        mmOutStream = tmpOut;
    }

    @Override
    public void run() {
        byte[] buffer = new byte[1024];  // buffer store for the stream
        int bytes; // bytes returned from read()


//        File targetFile = new File("targetFile.txt");
//        try {
//            targetFile.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (!targetFile.exists()) {
//            try {
//                targetFile.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        // Keep listening to the InputStream until an exception occurs
//        String str = "New File\n";
//        byte[] data = str.getBytes(StandardCharsets.UTF_8);  // Troubleshoot w/ str.getBytes()
        OutputStream outStream = null;
//        try {
//            outStream = new FileOutputStream(targetFile);
//            outStream.write(data);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            IOUtils.closeQuietly(outStream);
//        }



        // Keep listening to the InputStream until an exception occurs
        while (true) {
            try {
                // Read from the InputStream
                bytes = mmInStream.available();
                if(bytes != 0) {
                    buffer = new byte[bytes];
                    SystemClock.sleep(100); //pause and wait for rest of data. Adjust this depending on your sending speed.
                    bytes = mmInStream.available(); // how many bytes are ready to be read?
                    bytes = mmInStream.read(buffer, 0, bytes); // record how many bytes we actually read
                    mHandler.obtainMessage(Bluetooth.MESSAGE_READ, bytes, -1, buffer)
                            .sendToTarget(); // Send the obtained bytes to the UI activity

//                    // data to file
//                    outStream = new FileOutputStream(targetFile);
//                    outStream.write(buffer,0,bytes);
////                    IOUtils.closeQuietly(outStream);
//                    outStream.close();
                }
//            } catch (IOException e) {
//                e.printStackTrace();
//                break;
//            }
//            finally {
//                IOUtils.closeQuietly(outStream);
//            }



//        }
            } catch (IOException e) {
                e.printStackTrace();

                break;
            }
        }
    }

    /* Call this from the main activity to send data to the remote device */
    public void write(String input) {
        byte[] bytes = input.getBytes();           //converts entered String into bytes
        try {
            mmOutStream.write(bytes);
        } catch (IOException e) { }
    }

    /* Call this from the main activity to shutdown the connection */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) { }
    }
}