package com.example.romulo.appmae;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.moshi.Moshi;

import java.util.ArrayList;
import java.util.Arrays;


public class Receita extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receita);

        Intent intent = getIntent();
        String recei = (String) intent.getSerializableExtra("nome");

     //   ArrayList<String> listItems = new ArrayList<String>();
     //   listItems = new HomeActivity();
        String texto = recei;

        int inicio1 = texto.indexOf("NOME\":\"")+7;
        int fim1 = texto.indexOf("\",\"", inicio1);
      //  System.out.println(texto.substring(inicio, fim));
        int inicio2 = texto.indexOf("CEITA\":\"")+7;
        int fim2 = texto.indexOf("\"}", inicio2);


        TextView textView = (TextView) findViewById(R.id.textRec);
        TextView textView1= (TextView) findViewById(R.id.textRec2);
        String Receita = texto.substring(inicio1,fim1)+" \n";
        String Descricao = texto.substring(inicio2,fim2)+" \n";
        textView.setText(Receita);
        textView.setVisibility(View.VISIBLE);
        textView1.setText(Descricao);
        textView1.setVisibility(View.VISIBLE);

        // ajustar(recei);
    }
}

