package com.example.valentino.restapitest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DisplayMovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movie);
         TextView textView = findViewById(R.id.textView);

         TextView actores = findViewById(R.id.listaActores);
         ImageView imgPoster = findViewById(R.id.poster);



         Intent intent = getIntent();
         String Pelicula = intent.getStringExtra(MainActivity.PELICULA);

        getMovie(Pelicula, textView, actores, imgPoster);

    }

    public void getMovie(String Pelicula, final TextView textView, final TextView actores, final ImageView imgPoster) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.omdbapi.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieService service = retrofit.create(MovieService.class);
        Call<Movie> movie = service.searchedMovie(Pelicula, "2dbc1f17");

        movie.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                // response.isSuccessful() &&
                if (response.body().getResponse().equals("False")) {
                    textView.setText("¡No la encuentro!");
                    actores.setText("¿Acaso esa eplicula existe?");
                } else {
                    textView.setText(response.body().getTitle());
                    actores.setText(response.body().getActors());
                    Picasso.get().load(response.body().getPoster()).into(imgPoster);
                }

            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                textView.setText("Error al buscar la pelicula");
                Log.e("Movies:", "Error al buscar peli, " + t);
            }

        });
    }

}
