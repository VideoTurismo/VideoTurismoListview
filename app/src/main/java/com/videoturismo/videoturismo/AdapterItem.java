package com.videoturismo.videoturismo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by CASCENCIO on 10/04/2017.
 */

public abstract class AdapterItem extends BaseAdapter {

    private ArrayList<?> entradas;
    private int R_layout_IdView;
    private Context context;

    public AdapterItem(Context context, int R_layout_IdView, ArrayList<?> entradas) {
        super();
        this.context = context;
        this.entradas = entradas;
        this.R_layout_IdView = R_layout_IdView;
    }
    @Override
    public View getView(int posicion, View view, ViewGroup pariente) {
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R_layout_IdView, null);
        }
        onEntrada (entradas.get(posicion), view);
        return view;
    }

    @Override
    public int getCount() {

        return entradas.size();
    }

    @Override
    public Object getItem(int posicion) {

        return entradas.get(posicion);
    }

    @Override
    public long getItemId(int posicion) {
        return posicion;
    }

    public String getPathPeli(){
        String path = "puto el q lo lea";
        return path;
    }

    public abstract void onEntrada(Object entrada, View view) ;
}
