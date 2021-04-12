package com.example.chalonapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class activity_loginuser extends AppCompatActivity {
    private EditText edt1, edt2, edt3, edt4;
    private Button btn1, btn2;
    private int id = 0;
    FirebaseAuth firebaseAuth;
    AwesomeValidation awesomeValidation;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginuser);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this,R.id.txtemailuserlog, Patterns.EMAIL_ADDRESS,R.string.wrong_mail);
        awesomeValidation.addValidation(this,R.id.txtpassuserlog, ".{6,}",R.string.wrong_pass);

        edt1 = (EditText)findViewById(R.id.txtnameuserlog);
        edt2 = (EditText)findViewById(R.id.txtsnameuserlog);
        edt3 = (EditText)findViewById(R.id.txtemailuserlog);
        edt4 = (EditText)findViewById(R.id.txtpassuserlog);
        btn1 = (Button)findViewById(R.id.btnregisteruserlog);
        btn2 = (Button)findViewById(R.id.btnbackuserlog);
    }

    public void IngresarUser(View view){
        String nombre = edt1.getText().toString();
        String apellido = edt2.getText().toString();
        String correo = edt3.getText().toString();
        String contra = edt4.getText().toString();

        if (nombre.length() == 0 || apellido.length() == 0 || correo.length() == 0 || contra.length() == 0)
            Toast.makeText(activity_loginuser.this,"No pueden haber campos en blanco.",Toast.LENGTH_SHORT).show();
        else{
            if (awesomeValidation.validate()){
                firebaseAuth.createUserWithEmailAndPassword(correo, contra).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            id = VerificarUser(correo);
                            if(id > 0)
                            {
                                Toast.makeText(activity_loginuser.this,"El correo ingresado ya está asociado a una cuenta existente",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                InsertUser(id,nombre,apellido,correo);
                                Toast.makeText(activity_loginuser.this,"Usuario creado con éxito",Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(activity_loginuser.this,LoginActivity.class);
                                startActivity(i);
                                FirebaseAuth.getInstance().signOut();
                            }

                            activity_loginuser.this.finish();
                        }else{
                            String ErrorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                            Toast.makeText(activity_loginuser.this,ErrorCode,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else{
                Toast.makeText(activity_loginuser.this,"Los datos ingresados están incompletos",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public  void back(View view){
        activity_loginuser.this.finish();
    }

    public int VerificarUser(String _correo)
    {
        int respuesta = 0;

        //Conexión a la base de datos
        SqlLiteOpenHelperAdmin admin = new SqlLiteOpenHelperAdmin(this,"chalon_database",null,1);
        SQLiteDatabase database = admin.getReadableDatabase();

        Cursor fila = database.rawQuery("select id from clientes where correo = '" + _correo + "'",null);

        if(fila.moveToFirst())
        {
            respuesta = fila.getInt(0);
        }

        return respuesta;
    }

    public void InsertUser(Integer id, String nombres, String apellidos, String correo)
    {
        int last_id = 0;
        SqlLiteOpenHelperAdmin admin = new SqlLiteOpenHelperAdmin(this,"chalon_database",null,1);

        SQLiteDatabase database = admin.getReadableDatabase();

        Cursor fila0 = database.rawQuery("SELECT MAX(id) FROM clientes",null);

        if(fila0.moveToFirst())
        {
            last_id = fila0.getInt(0);
        }

        int _id = last_id + 1;
        final String Insert_cliente = "INSERT INTO clientes VALUES( " + _id + ", '" + nombres + "', '" + apellidos + "', '" + correo + "')";
        database.execSQL(Insert_cliente);
        //Actualizar la activity para mostrar los datos del insert actualizados
        this.recreate();
    }
}