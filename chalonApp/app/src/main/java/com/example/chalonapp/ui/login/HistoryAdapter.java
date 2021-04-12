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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.chalonapp.R;
import com.example.chalonapp.SqlLiteOpenHelperAdmin;
import com.example.chalonapp.activity_reserva;
import com.example.chalonapp.data.model.Cita;

import java.util.List;

public class HistoryAdapter extends BaseAdapter {
    Context context;
    List<Cita> list;
    int _id_cliente = 0;
    String imgTratamientoURL = "";
    String TratamientoName = "";
    String TratamientoPrice = "";
    int hora = 0;

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

        //Realizar Consulta para obtener los datos del IdTratamiento de la Cita a mostrar

        SqlLiteOpenHelperAdmin admin = new SqlLiteOpenHelperAdmin(this.context,"chalon_database",null,1);
        SQLiteDatabase database = admin.getReadableDatabase();

        Cursor filaTrat = database.rawQuery("select trat.nombre, trat.precio, trat.img_url from citas cits inner join tratamientos trat on trat.id = cits.id_tratamiento where cits.id_tratamiento = '" + String.valueOf( item.getId_tratamiento() ) + "'", null);

        if(filaTrat.moveToFirst())
        {
            TratamientoName= filaTrat.getString(0);
            TratamientoPrice = filaTrat.getString(1);
            imgTratamientoURL = filaTrat.getString(2);
        }

       Glide.with(view)
                .load(imgTratamientoURL)
                .into(imageTratamiento);

        TxtNombre.setText(TratamientoName);
        TxtPrecio.setText("$ " + TratamientoPrice);
        tvFechaCita.setText(item.getFecha());


        String arrayHora[] = item.getHora().split(":");
        hora = Integer.parseInt( arrayHora[0] );

        if ( hora > 11 ){
            tvHoraCita.setText(item.getHora() + " pm");
        }
        else {
            tvHoraCita.setText(item.getHora() + " am");
        }

        return view;
    }
}

