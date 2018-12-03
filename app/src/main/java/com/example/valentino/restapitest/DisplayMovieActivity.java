package com.example.valentino.restapitest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DisplayMovieActivity extends AppCompatActivity {

    public static final String ACTOR = "relleno";

    ListView listaActores;
    ArrayList<String> ListActores = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_display_movie);
         TextView textView = findViewById(R.id.textView);


         ImageView imgPoster = findViewById(R.id.poster);

         ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ListActores);
         listaActores = findViewById(R.id.ListViewActores);

         String Pelicula;
         Intent intent = getIntent();
         if(intent.getStringExtra(MainActivity.PELICULA).equals("relleno")){
             Pelicula = intent.getStringExtra(ActorDetail.PELICULA);
         } else {
             Pelicula = intent.getStringExtra(MainActivity.PELICULA);
         }

        listaActores.setAdapter(arrayAdapter);
        getMovie(Pelicula, textView, imgPoster, arrayAdapter);

        // Disparamos el detalle del actor
        listaActores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String value = (String) listaActores.getItemAtPosition(position);

                Intent intent1 = new Intent(getApplicationContext(), ActorDetail.class);

                intent1.putExtra(ACTOR, value);

                startActivity(intent1);
            }
        });

    }

    public void getMovie(final String Pelicula, final TextView textView, final ImageView imgPoster, final ArrayAdapter arrayAdapter) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://www.omdbapi.com")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                MovieService service = retrofit.create(MovieService.class);
                Call<Movie> movie = service.searchedMovie(Pelicula, "2dbc1f17");


                movie.enqueue(new Callback<Movie>() {
                    @Override
                    public void onResponse(Call<Movie> call, Response<Movie> response) {

                        if (response.body().getResponse().equals("False"))
                            {
                                textView.setText("¡No la encuentro!");
                            }
                        else
                            {
                            textView.setText(response.body().getTitle());
                            Picasso.get().load(response.body().getPoster()).into(imgPoster);

                            List<String> items = Arrays.asList(response.body().getActors().split("\\s*,\\s*"));
                            for (String item : items) {
                                ListActores.add(item);
                            }
                            arrayAdapter.notifyDataSetChanged();

                        }

                    }

                    @Override
                    public void onFailure(Call<Movie> call, Throwable t) {
                        textView.setText("Error al buscar la pelicula");
                        Log.e("Movies:", "Error al buscar peli, " + t);
                    }

                });
            }
        }).start();

    }

}
