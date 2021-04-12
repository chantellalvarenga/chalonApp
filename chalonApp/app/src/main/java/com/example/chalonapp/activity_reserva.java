package com.example.chalonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chalonapp.data.model.Cliente;
import com.example.chalonapp.data.model.Tratamiento;

public class activity_reserva extends AppCompatActivity {
    TextView textView;
    TextView tvActualUserName, tvTratamientoElegido, tvPrecioTratamiento;
    Button btnAgendarCita;
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
           tvActualUserName.setText(CustomerName);
           tvTratamientoElegido.setText(nombre_tratamiento);
           tvPrecioTratamiento.setText("$ " + String.valueOf(precio_tratamiento));

    }

    public void AgendarCita(View view){
        Toast.makeText(activity_reserva.this,"Creando metodo para reservar", Toast.LENGTH_SHORT).show();
    }
}