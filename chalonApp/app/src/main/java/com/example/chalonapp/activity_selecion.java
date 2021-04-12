package com.example.chalonapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chalonapp.data.model.Tratamiento;
import com.example.chalonapp.ui.login.CustomAdapter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class activity_selecion extends AppCompatActivity {

    EditText idtxt;
    ImageView img;
    TextView txtBienvenidaUser;
    ImageButton tvLinkHistorial;
    ListView listviewTratamientos;
    List<Tratamiento> listaTratamientos;
    String nombres = "";
    String apellidos = "";
    String displayname;
    FirebaseAuth firebaseAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions gso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecion);

        //Declaramos las variables a utilizar
        tvLinkHistorial = findViewById(R.id.tvLinkHistorial);
        //img=findViewById(R.id.imageView1);
        //txtBienvenidaUser = findViewById(R.id.txtBienvenidaUser);
        listviewTratamientos=findViewById(R.id.listView1);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        displayname = currentUser.getDisplayName();
        if (displayname != null){
            String[] arrSplit_3 = displayname.split("\\s");
            nombres = arrSplit_3[0];
            //apellidos = arrSplit_3[1];
        }

        //Borré métodos de verificar usuarios e insertar usuarios

       CustomAdapter adapter = new CustomAdapter(this, GetData(), 1);
        listviewTratamientos.setAdapter(adapter);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    public void closeFirebaseAccount(View view) {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(activity_selecion.this, "Sesión cerrada con éxito", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(activity_selecion.this,LoginActivity.class);
        startActivity(i);
        activity_selecion.this.finish();
    }

    public void closeGoogleAccount(View view) {
        FirebaseAuth.getInstance().signOut();
        mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent mainActivity = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(mainActivity);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "No se pudo cerrar sesión con google",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        Toast.makeText(activity_selecion.this, "Sesión cerrada con éxito", Toast.LENGTH_SHORT).show();
    }

    public void deleteGoogleAccount(View view) {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (signInAccount != null) {
            AuthCredential credential =
                    GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);
            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        user.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                signOut();
                            }
                        });
                    } else {

                    }
                }
            });
        } else {

        }
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Intent IntentMainActivity = new Intent(getApplicationContext(), LoginActivity.class);
                IntentMainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(IntentMainActivity);
                finish();
            }
        });
    }

    private List<Tratamiento> GetData() {
        SqlLiteOpenHelperAdmin admin = new SqlLiteOpenHelperAdmin(this,"chalon_database",null,1);
        SQLiteDatabase database = admin.getReadableDatabase();
        ArrayList<Tratamiento> listItem = new ArrayList<>();

        Cursor fila = database.rawQuery("select id, nombre, precio, img_url from tratamientos ",null);

        if(fila.moveToFirst())
        {
            do {
                //Llenando las listas
                listItem.add(new Tratamiento(fila.getInt(0),fila.getString(1),fila.getDouble(2),fila.getString(3)));

            } while(fila.moveToNext());
        }
        else
        {
            Toast.makeText(this,"¡No hay tratamientos disponibles en este momento!", Toast.LENGTH_SHORT).show();
        }
        return  listItem;
    }

    public void goHistory(View v) {
        Intent seleccion = new Intent(this, HistoryActivity.class);
        startActivity(seleccion);
    }

}