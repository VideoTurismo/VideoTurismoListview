package com.videoturismo.videoturismo;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by CASCENCIO on 11/04/2017.
 */

public class CargarListView extends AsyncTask<Void, Void, ArrayList<Category>> {
    Context context;
    ProgressDialog pDialog;
    ListView lv;
    ImageView imagenPeli;


    String genero;
    String titulo;
    String portada;
    String rutaPelicula;
    String sinopsis;


    private static String URLSERVIDORPELICULAS = "http://192.168.5.161/streaming/WebServices/obtenerPelicula.php?nombreGenero=";

    public CargarListView(Context context, ListView lv,String genero){
        this.context = context;
        this.lv = lv;
        this.genero = genero;
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
            jArray = jSonP.readJsonArrayFromUrl(URLSERVIDORPELICULAS+genero);
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

            rutaPelicula = "http://192.168.5.161/streaming"+portada.replaceAll(" ","%20");
            try {
                adaptador.add(new Category(new URL(rutaPelicula), titulo));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Log.d("algo",rutaPelicula);
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

        lv.setAdapter(new AdapterItem(context, R.layout.item_category, result) {
            @Override
            public void onEntrada(Object entrada, View view) {
                TextView tituloPeli = (TextView) view.findViewById(R.id.tituloPelicula);
                imagenPeli = (ImageView) view.findViewById(R.id.imagenPelicula);
                tituloPeli.setText(((Category) entrada).get_tituloPeli());
                try {
                    imagenPeli.setImageBitmap(((Category) entrada).get_imagenpeli(imagenPeli));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
        pDialog.dismiss();
    }
}