package com.example.valentino.restapitest;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = (TextView) findViewById(R.id.textView);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.omdbapi.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieService service = retrofit.create(MovieService.class);

        Call<Movie> movies = service.searchedMovie("titanic", "2dbc1f17");

        movies.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful()) {
                    Movie pelicula = response.body();
                    textView.setText(pelicula.getTitle());
                } else {
                    Log.e("Movies:", "error" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.e("Movies:", "Error al buscar peli, " + t);
                textView.setText("ERROR!!");
            }
        });

    }
}
