package com.example.shadman.calculator;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Shadman on 10/23/2015.
 */
public class History {
    String fileName = "history_Calculator";
    Context context;

    History (Context c) {
        context = c;
    }

    public void writeMessage(String msg) {
        String  message = msg;
        message += readMessage();
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fileOutputStream.write(message.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readMessage() {
            try{
                String message;
                FileInputStream fileInputStream = context.openFileInput("history_Calculator");
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuffer stringBuffer = new StringBuffer();
                while ((message = bufferedReader.readLine()) != null) {
                    stringBuffer.append(message + "\n");
                }
                return message;
            } catch (FileNotFoundException e){

            } catch (IOException e) {
                e.printStackTrace();
            }
        return "Something went wrong!";
    }
}