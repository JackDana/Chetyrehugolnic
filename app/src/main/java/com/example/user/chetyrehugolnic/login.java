package com.example.user.chetyrehugolnic;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

import java.util.concurrent.Exchanger;

public class login extends AppCompatActivity {
    EditText _login;
    EditText _password;
    database db =Data_Base.instance();
    static CUsers userx;
    final Context context=this;
    EditText _eml;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        _login=(EditText)findViewById(R.id.lg);
        _password=(EditText)findViewById(R.id.ps);
    }
    public void Click1(final View v){
        String result=null;

        Exchanger<String> exchanger=new Exchanger<>();
        final String log=_login.getText().toString();
        final String ps=_password.getText().toString();

        new MyThread.TLogin(exchanger,log,ps).start();

        try {
          result=exchanger.exchange(null);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

            if(result.equals("ok")){
                // Intent переход на другую активность
               Intent inten1=new Intent(login.this,TabbedActivity.class);
            startActivity(inten1);
                Snackbar.make(v, "ok", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
            if(result.equals("no login")){
                // Toast.makeText(this,"Неверный Логин",Toast.LENGTH_SHORT).show();
                Snackbar.make(v, "Неверный Логин", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
            if(result.equals("no password")){
                Snackbar.make(v, "Неверный пароль", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                // Toast.makeText(this,"Неверный пароль",Toast.LENGTH_SHORT).show();
            }




    }
    public void Click2(View v){
        Intent intent=new Intent(this,Registration.class);
        startActivity(intent);
    }
    public void Click3(View v){
        LayoutInflater li=LayoutInflater.from(context);
        final View dialogView=li.inflate(R.layout.dialog3,null);
        AlertDialog.Builder mDialogBuilder=new AlertDialog.Builder(context);
        mDialogBuilder.setView(dialogView);
        _eml=(EditText)dialogView.findViewById(R.id.eml);

        mDialogBuilder.setPositiveButton("отправить новый пароль", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String neweml=_eml.getText().toString();
                new MyThread.Tnewpass(neweml).start();
                //db.NewPassword(neweml);//записать метод в Data_Base

            }
        });
        mDialogBuilder.setNegativeButton("отменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog=mDialogBuilder.create();
        alertDialog.show();
    }
}