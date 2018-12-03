package com.example.valentino.restapitest;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActorDetail extends AppCompatActivity {

    public static final String PELICULA = "relleno";

    ListView listViewPeliculasActor;
    ArrayList<String> ListPeliculasActor = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_actor_detail);

        Intent intent = getIntent();
        String Actor = intent.getStringExtra(DisplayMovieActivity.ACTOR);

        TextView textView2 = findViewById(R.id.textView2);
        ImageView imageView2 = findViewById(R.id.imageView2);
        String pathimagen = "https://image.tmdb.org/t/p/w500/";

        ArrayAdapter arrayAdapter2 = new ArrayAdapter(
                this, android.R.layout.simple_list_item_1, ListPeliculasActor);

        listViewPeliculasActor = findViewById(R.id.listViewActor);
        listViewPeliculasActor.setAdapter(arrayAdapter2);

        textView2.setText(Actor);

        getActor(Actor, textView2, pathimagen, imageView2, arrayAdapter2);

        // Disparamos el detalle del actor
        listViewPeliculasActor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String value = (String) listViewPeliculasActor.getItemAtPosition(position);

                Intent intent1 = new Intent(getApplicationContext(), DisplayMovieActivity.class);

                intent1.putExtra(PELICULA, value);

                startActivity(intent1);
            }
        });

    }

    public void getActor(final String Actor, final TextView textView2, final String pathimagen,
                         final ImageView imageView2, final ArrayAdapter arrayAdapter2) {
        new Thread(new Runnable() {

            @Override
            public void run() {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://api.themoviedb.org")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ActorService service = retrofit.create(ActorService.class);
                Call<ActorQuery> actor = service.searchedActor("070e65698f73c47e60d0bdf9c5f68b15",
                        "es-AR", Actor, "1");
                Log.d("TEMP_TAG", "actor url: " + actor.request().url().toString());

                actor.enqueue(new Callback<ActorQuery>() {

                    @Override
                    public void onResponse(Call<ActorQuery> call, Response<ActorQuery> response) {
                        if (response.body().getTotalResults() == 0) {
                            textView2.setText("¡No encuentro datos del actor!");
                        } else {
                            Result result = response.body().getResults().get(0);
                            String pathImage = pathimagen + result.getProfilePath();

                            textView2.setText(result.getName());
                            Picasso.get().load(pathImage).into(imageView2);

                            List<KnownFor> listas = result.getKnownFor();
                            for (KnownFor lista : listas) {
                                ListPeliculasActor.add(lista.getTitle());
                                Log.d("TIT_PELI", "Titulos: " + lista.getTitle());
                            }
                                arrayAdapter2.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<ActorQuery> call, Throwable t) {
                        textView2.setText("Error al buscar detalles del Actor");
                        Log.e("Movies:", "Error al buscar detalles del actor, " + t);
                    }
                });

            }
        }).start();
    }
}
