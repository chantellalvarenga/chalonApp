package com.example.chalonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chalonapp.data.model.Cita;
import com.example.chalonapp.data.model.Tratamiento;
import com.example.chalonapp.ui.login.HistoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    EditText idtxt;
    ImageView img;
    TextView tvLinkHistorial;
    ListView listviewTratamientos;
    List<Cita> listaTratamientos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        //Declaramos las variables a utilizar
        tvLinkHistorial = findViewById(R.id.tvLinkHistorial);
        //img=findViewById(R.id.imageView1);
        //txtBienvenidaUser = findViewById(R.id.txtBienvenidaUser);
        listviewTratamientos = findViewById(R.id.listView1);

        HistoryAdapter adapter = new HistoryAdapter(this, GetData(), 1);
        listviewTratamientos.setAdapter(adapter);

    }

    private List<Cita> GetData() {
        SqlLiteOpenHelperAdmin admin = new SqlLiteOpenHelperAdmin(this,"chalon_database",null,1);
        SQLiteDatabase database = admin.getReadableDatabase();
        ArrayList<Cita> listItem = new ArrayList<>();

        Cursor fila = database.rawQuery("select id, id_cliente, id_tratamiento, fecha, estado, hora from citas ",null);

        if(fila.moveToFirst())
        {
            do {
                //Llenando las listas
                listItem.add(new Cita(fila.getInt(0),fila.getInt(1),fila.getInt(2),fila.getString(3),fila.getString(4),fila.getString(5)));

            } while(fila.moveToNext());
        }
        else
        {
            Toast.makeText(this,"¡No ha realizado citas hasta el momento!", Toast.LENGTH_SHORT).show();
        }
        return listItem;
    }
}