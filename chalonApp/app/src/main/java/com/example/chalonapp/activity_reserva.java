package com.example.chalonapp;

import androidx.appcompat.app.AppCompatActivity;

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
           id_cliente=bundle.getInt("id_cliente");
           id_tratamiento=bundle.getInt("id_tratamiento");
           nombre_cliente=bundle.getString("nombres_cliente");
           apellidos_cliente=bundle.getString("apellidos_cliente");
           nombre_tratamiento=bundle.getString("nombre_tratamiento");
           precio_tratamiento=bundle.getDouble("precio_tratamiento");

           /*String mensaje = "\n cliente: " + apellidos_cliente + "," + nombre_cliente +
                            "\n Tratamiento: " + nombre_tratamiento +
                            "\n Precio: " + String.valueOf(precio_tratamiento);
           */
           //textView.setText(mensaje);

           String CustomerName = "" + nombre_cliente + " " + apellidos_cliente + "";
           tvActualUserName.setText(String.valueOf(id_cliente));
           tvTratamientoElegido.setText(nombre_tratamiento);
           tvPrecioTratamiento.setText("$ " + String.valueOf(precio_tratamiento));

           /*
        bfecha=(Button)findViewById(R.id.bfecha);
        bhora=(Button)findViewById(R.id.bhora);
        efecha=(EditText)findViewById(R.id.efecha);
        ehora=(EditText)findViewById(R.id.ehora);
        */

        btnFechaLbl.setOnClickListener(this);
        btnHoraLbl.setOnClickListener(this);


    }

    public void AgendarCita(View view){
        Toast.makeText(activity_reserva.this,"Creando metodo para reservar", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
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
}