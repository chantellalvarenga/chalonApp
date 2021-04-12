package com.example.chalonapp.ui.login;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.chalonapp.R;
import com.example.chalonapp.SqlLiteOpenHelperAdmin;
import com.example.chalonapp.data.model.Cita;

import java.util.List;

public class HistoryAdapter extends BaseAdapter {
    Context context;
    List<Cita> list;
    int _id_cliente = 0;

    public HistoryAdapter(Context context, List<Cita> list, int id_cliente) {
        this.context = context;
        this.list = list;
        _id_cliente = id_cliente;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ImageView imageTratamiento;
        TextView TxtNombre, TxtPrecio, tvFechaCita, tvHoraCita;

        Cita item = list.get(i);

        if( view == null )
            view = LayoutInflater.from(context).inflate(R.layout.history_list_item,null);

        imageTratamiento = view.findViewById(R.id.imageViewTratamiento);
        TxtNombre = view.findViewById(R.id.txtNombre_tratamiento);
        TxtPrecio = view.findViewById(R.id.tvPriceTrat);
        tvFechaCita = view.findViewById(R.id.tvFechaCitada);
        tvHoraCita = view.findViewById(R.id.tvHoraCita);

       /* Glide.with(view)
                .load(item.getImg_url().toString())
                .into(imageTratamiento);
        */

        //TxtNombre.setText(item.getNombre());
        //TxtPrecio.setText("$ " + String.valueOf(item.getPrecio()));
        tvFechaCita.setText(item.getFecha());
        tvHoraCita.setText(item.getHora());
        return view;
    }
}

