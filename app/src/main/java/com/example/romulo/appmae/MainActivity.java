package com.example.romulo.appmae;

import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceActivity;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.os.AsyncTask;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;


import cz.msebera.android.httpclient.Header;

import static android.R.attr.content;

public class MainActivity extends AppCompatActivity {
    public int gravacao=0;
    public  String text="leite ovo";
    private final int ID_TEXTO_PARA_VOZ=100;
    String response = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent ivoz = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    ivoz.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

                    ivoz.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

                    ivoz.putExtra(RecognizerIntent.EXTRA_PROMPT,
                            "Fale Quais Alimentos Você Têm");


                   try {
                        startActivityForResult(ivoz, ID_TEXTO_PARA_VOZ);
                       }catch (ActivityNotFoundException a) {
                       //  Toast.makeText(getApplicationContext(),"");
                       //    Toast.LENGTH_SHORT

                       }

            }
        });
    }

    public void uob(){

        String url = "https://appmae2.mybluemix.net/receita?mensagem= "+ text;
        AsyncHttpClient client = new AsyncHttpClient();


        client.get(url, new AsyncHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (responseBody == null) { /* empty response, alert something*/ return; }
                //success response, do something with it!
                response = new String(responseBody);
          //      Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                if(!(response.equals("null"))){
                Intent ii = new Intent(MainActivity.this, Receita.class);
                              ii.putExtra("nome", response);
                              startActivity(ii);
                  //  Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Nenhuma Receita Encontrada",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (responseBody == null) { /* empty response, alert something*/ return; }
                //error response, do something with it!
           response = new String(responseBody);
                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
            }


        });



    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int id, int resultCodeID,Intent dados){
        super.onActivityResult(id,resultCodeID,dados);
        switch (id){
            case ID_TEXTO_PARA_VOZ:
                if(resultCodeID == RESULT_OK && null != dados){
                    ArrayList<String> result=dados.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    text = result.get(0);
                    uob();

                }
                break;
        }
    }

    }

