package com.example.chalonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import java.util.Calendar;


import com.example.chalonapp.data.model.Cliente;
import com.example.chalonapp.data.model.Tratamiento;

public class activity_reserva extends AppCompatActivity implements View.OnClickListener{
    TextView tvActualUserName, tvTratamientoElegido, tvPrecioTratamiento;
    Button btnAgendarCita;
    TextView tvSetFecha, tvSetHora;
    ImageButton btnFechaLbl, btnHoraLbl;
    private  int dia,mes,ano,hora,minutos;


    public int id_cliente = 0;
    public int id_tratamiento = 0;
    public String nombre_cliente = "";
    public String apellidos_cliente = "";
    public String nombre_tratamiento = "";
    public double precio_tratamiento = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva);

        //Declaración de variables a utilizar
        //textView = findViewById(R.id.textView_reserva);
        tvActualUserName = findViewById(R.id.tvActualUserName);
        tvTratamientoElegido = findViewById(R.id.tvTratamientoElegido);
        tvPrecioTratamiento = findViewById(R.id.tvPrecioTratamiento);
        btnAgendarCita = findViewById(R.id.btnAgendarCita);
        btnFechaLbl = findViewById(R.id.tvFechaLbl);
        btnHoraLbl = findViewById(R.id.tvHoraLbl);

        tvSetFecha = findViewById(R.id.tvSetFecha);
        tvSetHora = findViewById(R.id.tvSetHora);

          //Obtenemos datos del usuario y del tratamiento seleccionado
          Bundle bundle = getIntent().getExtras();

          //Datos para realizar una reservación
           id_cliente = bundle.getInt("id_cliente");
           id_tratamiento = bundle.getInt("id_tratamiento");
           nombre_cliente = bundle.getString("nombres_cliente");
           apellidos_cliente = bundle.getString("apellidos_cliente");
           nombre_tratamiento = bundle.getString("nombre_tratamiento");
           precio_tratamiento = bundle.getDouble("precio_tratamiento");

           String CustomerName = "" + nombre_cliente + " " + apellidos_cliente + "";
           tvActualUserName.setText( String.valueOf(CustomerName) );
           tvTratamientoElegido.setText(nombre_tratamiento);
           tvPrecioTratamiento.setText("$ " + String.valueOf(precio_tratamiento));

           btnFechaLbl.setOnClickListener(this);
           btnHoraLbl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //Registramos la fecha seleccionada en el TextView tvSetFecha
        if(v==btnFechaLbl){
            final Calendar c= Calendar.getInstance();
            dia=c.get(Calendar.DAY_OF_MONTH);
            mes=c.get(Calendar.MONTH);
            ano=c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    tvSetFecha.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                }
            }
                    ,ano,mes,dia);
            datePickerDialog.show();
        }

        //Registramos la hora en el TextView tvSetHora
        if (v==btnHoraLbl){
            final Calendar c= Calendar.getInstance();
            hora=c.get(Calendar.HOUR_OF_DAY);
            minutos=c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    tvSetHora.setText(hourOfDay+":"+minute);
                }
            },hora,minutos,false);
            timePickerDialog.show();
        }
    }

    public void AgendarCita(View view){


        String fechaAgendada = "";
        fechaAgendada = tvSetFecha.getText().toString();

        String horaAgendada = "";
        horaAgendada = tvSetHora.getText().toString();

        int hora = 0;
        int minutos = 0;

        //Primero validar que haya elegido fecha y hora
        if ( !( fechaAgendada.equals("Elegir...") || horaAgendada.equals("Elegir...") || fechaAgendada.isEmpty() || horaAgendada.isEmpty() ) ){

            String arrayHora[] = horaAgendada.split(":");
            hora = Integer.parseInt( arrayHora[0] );
            minutos = Integer.parseInt(arrayHora[1]);

            if ( hora > 16 ){
                Toast.makeText(activity_reserva.this,"Ops! Horario no disponible \nRegresa mañana...", Toast.LENGTH_SHORT).show();
            }
            else if( hora < 8 ){
                Toast.makeText(activity_reserva.this,"Ops! Chalon abre a las 8 am...", Toast.LENGTH_SHORT).show();
            }
            else {

                //Validar que ese usuario no tenga cita para ese mismo día y horario.

                int idCitaExistente = 0;

                //Conexión a la base de datos
                SqlLiteOpenHelperAdmin admin = new SqlLiteOpenHelperAdmin(this,"chalon_database",null,1);
                SQLiteDatabase database = admin.getReadableDatabase();

                Cursor fila = database.rawQuery("select id from citas where id_cliente = '" + String.valueOf(id_cliente) + "' " +
                        "AND fecha = '" + fechaAgendada + "' AND hora = '" + horaAgendada + "'",null);

                if(fila.moveToFirst())
                {
                    idCitaExistente = fila.getInt(0);
                }

                if( idCitaExistente > 0 ){
                    Toast.makeText(activity_reserva.this,"Ya tienes una cita programada para ese día y hora seleccionada", Toast.LENGTH_SHORT).show();
                }
                else {
                    //Ahora verificamos que no tenga la cita para ese articulo para el mismo día
                    int IdCitaMismodia = 0;

                    Cursor fila2 = database.rawQuery("select id from citas where id_cliente = '" + String.valueOf(id_cliente) + "' " +
                            "AND id_tratamiento = '" + String.valueOf(id_tratamiento) + "' " +
                            "AND fecha = '" + fechaAgendada + "'",null);

                    if(fila2.moveToFirst())
                    {
                        IdCitaMismodia = fila2.getInt(0);
                    }

                    if( IdCitaMismodia > 0 ){
                        Toast.makeText(activity_reserva.this,"Ya tienes una cita programada para ese Tratamiento el mismo día", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        //Se agenda la cita

                        //Generar ID

                        int last_idCita = 0;
                        Cursor filaID = database.rawQuery("SELECT MAX(id) FROM citas",null);

                        if(filaID.moveToFirst())
                        {
                            last_idCita = filaID.getInt(0);
                        }

                        int New_idCita = last_idCita + 1;


                        final String Insert_cita = "INSERT INTO citas VALUES( " + New_idCita + ", " + id_cliente + ", " + id_tratamiento + ", '1' , '" + fechaAgendada + "' , '" + horaAgendada + "')";
                        database.execSQL(Insert_cita);

                        Toast.makeText(activity_reserva.this,"¡Cita agendada exitosamente!", Toast.LENGTH_SHORT).show();
                    }

                }

            }

        }
        else {
            Toast.makeText(activity_reserva.this,"Elija una fecha y hora para su cita", Toast.LENGTH_SHORT).show();
        }

    }
}