package com.s07150651.datastorage;

import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private EditText editText1;
    private EditText editText2;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText1 = (EditText) findViewById(R.id.edittext1);
        editText2 = (EditText) findViewById(R.id.edittext2);
        textView  = (TextView) findViewById(R.id.textview);
    }
    public void spWrite(View v){
        SharedPreferences user = getSharedPreferences("user",MODE_APPEND);
        SharedPreferences.Editor  editor= user.edit();
        editor.putString("account",editText1.getText().toString());
        editor.putString("pass",editText2.getText().toString());
       editor.commit();
        Toast.makeText(this,"sharedpreferences写入成功",Toast.LENGTH_LONG).show();
    }
    public void spRead(View v){
        SharedPreferences user = getSharedPreferences("user",MODE_PRIVATE);
        String account = user.getString("account","没有这个值");
        String  pass  = user.getString("pass","没有这个值");
        textView.setText("账号："+account+"\n"+"密码："+pass);
        Toast.makeText(this,"sharedpreferences读取成功",Toast.LENGTH_LONG).show();
    }
    public void ROMRead(View v){
        try {
            FileInputStream fis = openFileInput("user.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuffer sb = new StringBuffer();
            String s ;
            while (null != (s=br.readLine())){
                sb.append(s+'\n');
            }
            fis.close();
            textView.setText(sb);
            Toast.makeText(this,"ROM读取成功",Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ROMWrite(View v){
        String account = editText1.getText().toString();
        String pass = editText2.getText().toString();
        try {
            FileOutputStream fos = openFileOutput("user.txt",MODE_APPEND);
            OutputStreamWriter osw= new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(account+":"+pass);
            bw.flush();
            fos.close();
            Toast.makeText(this,"ROM写入成功",Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void SDWrite(View v){
        String str = editText1.getText().toString()+":"+editText2.getText().toString();
        String sdCardRoot  = Environment.getExternalStorageDirectory().getAbsolutePath();
        String filename = sdCardRoot+"/text.txt";
        File file = new File(filename);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(str.getBytes());
            fos.flush();
            fos.close();
            Toast.makeText(this,"SD写入成功",Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void SDRead(View v){
        String sdCardRoot  = Environment.getExternalStorageDirectory().getAbsolutePath();
        String filename = sdCardRoot+"/text.txt";
        File file = new File(filename);
        int length = (int) file.length();
        byte[] b =new byte[length];
        try {
            FileInputStream fis = new FileInputStream(filename);
            fis.read(b,0,length);
            fis.close();
            textView.setText(new String(b));
            Toast.makeText(this,"SD读取成功",Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
