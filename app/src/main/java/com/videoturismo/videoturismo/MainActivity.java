package com.videoturismo.videoturismo;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView lv;

    private static String URLSERVIDORPELICULAS = "http://192.168.5.161/streaming/WebServices/obtenerPelicula.php?nombreGenero=Estrenos";

    ArrayList<HashMap<String, String>> datos1;
    JSONArray pelis;

    String titulo;
    String portada;
    String rutaPelicula;
    String sinopsis;

    JSONParser jParser;
    JSONObject jSONObject = null;
    JSONArray jArray = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lv = (ListView) findViewById(R.id.listview);


        //Llenamos las celdas de la lista
       new CargarListView(MainActivity.this).execute();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private class CargarListView extends AsyncTask<Void, Void, ArrayList<Category>> {
        Context context;
        ProgressDialog pDialog;

        public CargarListView(Context context){
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Cargando Lista");
            pDialog.setCancelable(true);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.show();
        }

        @Override
        protected ArrayList<Category> doInBackground(Void... arg0) {
            // TODO Auto-generated method stub
            ArrayList<Category> adaptador = new ArrayList<>();
            JSONParser jSonP = new JSONParser();
            JSONArray jArray   = null;
            try {
                jArray = jSonP.readJsonArrayFromUrl(URLSERVIDORPELICULAS);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < jArray.length(); i++) {

                JSONObject c = null;
                try {
                    c = jArray.getJSONObject(i);
                    titulo = c.getString("titulo");
                    portada = c.getString("rutaPortada");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adaptador.add(new Category(portada, titulo));
            }
            try{
                Thread.sleep(2000);
            }catch(Exception ex){
                ex.printStackTrace();
            }
            return adaptador;
        }

        @Override
        protected void onPostExecute(ArrayList<Category> result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            lv.setAdapter(new AdapterItem(MainActivity.this, R.layout.item_category, result) {
                @Override
                public void onEntrada(Object entrada, View view) {
                    TextView tituloPeli = (TextView) view.findViewById(R.id.tituloPelicula);
                    tituloPeli.setText(((Category) entrada).get_tituloPeli());

                    ImageView imagenPeli = (ImageView) view.findViewById(R.id.imagenPelicula);

                    imagenPeli.setImageResource(Integer.parseInt(((Category) entrada).get_imagenpeli(context,imagenPeli)));
                }
            });
            pDialog.dismiss();
        }
    }
}
