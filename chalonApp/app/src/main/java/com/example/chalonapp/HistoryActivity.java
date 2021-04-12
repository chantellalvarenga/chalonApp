package com.example.chalonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
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
    ImageButton tvLinkHistorial;
    ListView listviewTratamientos;
    List<Cita> listaTratamientos;
    TextView tvUserNameValue, tvTotalCitasValue;
    int IdActualUser = 0;
    String ActualUserName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        //Declaramos las variables a utilizar
        tvLinkHistorial = findViewById(R.id.tvLinkHistorial);
        //img=findViewById(R.id.imageView1);
        //txtBienvenidaUser = findViewById(R.id.txtBienvenidaUser);

        listviewTratamientos = findViewById(R.id.listView1);
        tvUserNameValue = findViewById(R.id.tvUserNameValue);
        tvTotalCitasValue = findViewById(R.id.tvTotalCitasValue);

        //Debo pasarlo desde seleccion
        Bundle bundle = getIntent().getExtras();
        IdActualUser = bundle.getInt("IdUsuario");
        ActualUserName = bundle.getString("Usuario");

        //Conexion a base de datos
        SqlLiteOpenHelperAdmin admin = new SqlLiteOpenHelperAdmin(this,"chalon_database",null,1);
        SQLiteDatabase database = admin.getReadableDatabase();

        int CitasAgendadas = 0;
        Cursor filaTotalCitas = database.rawQuery("select Count(id) from citas where id_cliente = '" + String.valueOf(IdActualUser) + "'",null);

        if(filaTotalCitas.moveToFirst())
        {
            CitasAgendadas = filaTotalCitas.getInt(0);
        }

        tvUserNameValue.setText(ActualUserName);
        tvTotalCitasValue.setText( String.valueOf(CitasAgendadas) );
        HistoryAdapter adapter = new HistoryAdapter(this, GetData(database), IdActualUser);
        listviewTratamientos.setAdapter(adapter);

    }

    private List<Cita> GetData(SQLiteDatabase db) {

        ArrayList<Cita> listItem = new ArrayList<>();

        Cursor fila = db.rawQuery("select id, id_cliente, id_tratamiento, fecha, estado, hora from citas order by id desc ",null);

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