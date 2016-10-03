package lab.comunicador;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class NuevoAlumno extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_alumno);


        Button boton_agregar = (Button) findViewById(R.id.button_agregar_alumno);
        boton_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre = ((EditText) (findViewById(R.id.nuevo_Nombre))).getText().toString();
                String apellido = ((EditText) (findViewById(R.id.nuevo_Apellido))).getText().toString();
                String sexo = ((Spinner) (findViewById(R.id.spinner_Sexo))).getSelectedItem().toString();
                String tamanio = ((Spinner) (findViewById(R.id.spinner_tamanio))).getSelectedItem().toString();
                System.out.println("Tamaño :" + tamanio);

                if ((nombre.length() != 0) && (apellido.length() != 0) && (!sexo.isEmpty()) && (!tamanio.isEmpty())){
                    BD database = new BD(v.getContext());
                    Alumno alumno = new Alumno (nombre,apellido,sexo,tamanio);
                    ArrayList<Integer> pages = new ArrayList<Integer>();
                    pages.add(Constantes.ALUMNO);
                    alumno.setPages(pages);
                    database.agregarAlumno(alumno);
                    Toast.makeText(getApplicationContext(), "Alumno Agregado", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(NuevoAlumno.this, GrillaActivity.class));
                }else{
                    Toast.makeText(getApplicationContext(), "Complete todos los campos", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    public void onBackPressed() {
        Intent intent = new Intent(NuevoAlumno.this, GrillaActivity.class);
        startActivity(intent);
    }
}
