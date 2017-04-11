package com.videoturismo.videoturismo;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by CASCENCIO on 10/04/2017.
 */

public class Category {

    private String idImagen;
    private String tituloPelicula;


    public Category (String idImagen, String tituloPelicula) {
        this.idImagen = idImagen;
        this.tituloPelicula = tituloPelicula;

    }

    public String get_tituloPeli() {
        return tituloPelicula;
    }



    public String get_imagenpeli(Context con, ImageView iv) {
        Picasso.with(con).load(idImagen).into(iv);

        return idImagen;
    }
}
