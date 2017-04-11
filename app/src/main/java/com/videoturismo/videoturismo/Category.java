package com.videoturismo.videoturismo;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by CASCENCIO on 10/04/2017.
 */

public class Category {

    private Bitmap idImagen;
    private URL urlImagen;
    private String tituloPelicula;


    public Category (URL urlImagen, String tituloPelicula) {
        this.tituloPelicula = tituloPelicula;
        this.urlImagen = urlImagen;

    }

    public String get_tituloPeli() {
        return tituloPelicula;
    }



    public Bitmap get_imagenpeli(ImageView iv) throws MalformedURLException {

        new CargarImagenes(urlImagen,iv).execute();
        return idImagen;
    }
}
