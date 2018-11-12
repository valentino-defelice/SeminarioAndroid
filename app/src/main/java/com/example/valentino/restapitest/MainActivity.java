package com.example.valentino.restapitest;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    public static final String PELICULA = "relleno";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
    }

    public void sendMessage(View view){

        Intent intent = new Intent(this, DisplayMovieActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String Pelicula = editText.getText().toString();

        intent.putExtra(PELICULA, Pelicula);
        startActivity(intent);
    }
}