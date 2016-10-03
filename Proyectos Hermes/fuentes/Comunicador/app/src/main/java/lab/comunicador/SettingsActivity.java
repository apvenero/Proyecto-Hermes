package lab.comunicador;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SettingsActivity extends ActionBarActivity {
    Alumno alumno;
    Configuracion config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // recupero el alumno a traves del ID pasado por parametro
        Bundle b = getIntent().getExtras();
        int id = b.getInt("alumno");
        alumno = new BD(this).getAlumno(id);

        // obtengo los campos de la vista
        EditText nombre = (EditText)this.findViewById(R.id.nuevo_Nombre);
        EditText apellido = (EditText)this.findViewById(R.id.editar_Apellido);
        Spinner sexo = (Spinner)this.findViewById(R.id.spinner_editar_Sexo);
        Spinner tamanio = (Spinner)this.findViewById(R.id.spinner_editar_tamanio);

        CheckBox pista = (CheckBox) this.findViewById(R.id.checkBox_Pista);
        CheckBox emociones = (CheckBox) this.findViewById(R.id.checkBox_Emociones);
        CheckBox establo = (CheckBox) this.findViewById(R.id.checkBox_Establo);
        CheckBox necesidades = (CheckBox) this.findViewById(R.id.checkBox_Necesidades);

          // seteo los valores del alumno en los campos
        nombre.setText(alumno.getNombre());
        apellido.setText(alumno.getApellido());
        sexo.setSelection((alumno.getSexo().equals("M")) ? 0 : 1);

        int t;
        if (alumno.getTamanio().equals("Pequeño")){
            t=0;
        }else{
            if (alumno.getTamanio().equals("Mediano")){
                t=1;
            }
            else{t=2;}
        }
        tamanio.setSelection(t);

        if(alumno.getPages().contains(Constantes.EMOCIONES)){
            emociones.setChecked(true);
        }
        if(alumno.getPages().contains(Constantes.PISTA)){
            pista.setChecked(true);
        }
        if(alumno.getPages().contains(Constantes.ESTABLO)){
            establo.setChecked(true);
        }
        if(alumno.getPages().contains(Constantes.NECESIDADES)){
            necesidades.setChecked(true);
        }


        //configuracion
        config= new BD(this).getConfiguracion();
        TextView ip = (TextView)this.findViewById(R.id.editar_ip);
        TextView puerto = (TextView)this.findViewById(R.id.editar_puerto);
        if (config != null) {
            ip.setText(config.getIp());
            puerto.setText(config.getPuerto());
        }

        // obtengo los botones y seteo los listeners

        Button boton_guardar = (Button) this.findViewById(R.id.button_guardarCambios);
        boton_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre = ((EditText) (findViewById(R.id.nuevo_Nombre))).getText().toString();
                String apellido = ((EditText) (findViewById(R.id.editar_Apellido))).getText().toString();
                String sexo = ((Spinner) (findViewById(R.id.spinner_editar_Sexo))).getSelectedItem().toString();
                String tamanio = ((Spinner) (findViewById(R.id.spinner_editar_tamanio))).getSelectedItem().toString();

                alumno.setNombre(nombre);
                alumno.setApellido(apellido);
                alumno.setSexo(sexo);
                alumno.setTamanio(tamanio);

                CheckBox pista = (CheckBox) findViewById(R.id.checkBox_Pista);
                CheckBox emociones = (CheckBox) findViewById(R.id.checkBox_Emociones);
                CheckBox establo = (CheckBox) findViewById(R.id.checkBox_Establo);
                CheckBox necesidades = (CheckBox) findViewById(R.id.checkBox_Necesidades);

                ArrayList<Integer> pages = new ArrayList<Integer>();

                if (pista.isChecked()) {
                    pages.add(Constantes.PISTA);
                }
                if (emociones.isChecked()) {
                    pages.add(Constantes.EMOCIONES);
                }
                if (establo.isChecked()) {
                    pages.add(Constantes.ESTABLO);
                }
                if (necesidades.isChecked()) {
                    pages.add(Constantes.NECESIDADES);
                }
                pages.add(pages.size(), Constantes.ALUMNO);
                alumno.setPages(pages);

                //configuracion
                String ip = ((EditText) (findViewById(R.id.editar_ip))).getText().toString();
                String puerto = ((EditText) (findViewById(R.id.editar_puerto))).getText().toString();

                if ((nombre.length() != 0) && (apellido.length() != 0) && (sexo != null) && (tamanio != null)) {
                    BD database = new BD(v.getContext());
                    int id = alumno.getId();
                    database.editarAlumno(alumno);

                    //configuracion
                    if (config == null && (ip != null && ip.length() != 0 && puerto != null && puerto.length()!=0)){
                        database.agregarConfiguracion(ip, puerto);
                    }else{
                        if (config!= null && ((config.getIp()!= ip && ip != null && ip.length() != 0) ||
                                config.getPuerto() != puerto && puerto != null && puerto.length()!=0)){
                            database.updateConfiguracion(ip, puerto);
                        }
                    }
                    Toast.makeText(getApplicationContext(), "Datos guardados", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SettingsActivity.this, AlumnoActivity.class);
                    intent.putExtra("alumno", id);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Debe completar todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button boton_eliminar= (Button) this.findViewById(R.id.button_EliminarAlumno);

        boton_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BD database = new BD(v.getContext());
                database.borrarAlumno(alumno);
                Intent intent = new Intent(SettingsActivity.this, GrillaActivity.class);
                startActivity(intent);
            }
        });
    }
    public void onBackPressed() {
        Intent intent = new Intent(SettingsActivity.this, AlumnoActivity.class);
        intent.putExtra("alumno", alumno.getId());
        startActivity(intent);
    }
}
