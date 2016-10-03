package lab.comunicador;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ignacio on 12/12/2015.
 */
public class GrillaActivity extends Activity {
    //List<Map<String, Alumno>> alumnos = new ArrayList<Map<String,Alumno>>();
    List<Alumno> alumnosList=new ArrayList<Alumno>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // inicializarListaMaps();
        inicializarLista();
        setContentView(R.layout.grilla_activity);
        ListView listView = (ListView) this.findViewById(R.id.list_alumnos);
        // ListAdapter adapter= new SimpleAdapter(this,alumnos,android.R.layout.simple_list_item_1, new String[] {"alumno"}, new int[] {android.R.id.text1});
        ListAdapter adapter = new ArrayAdapter<Alumno>(this, android.R.layout.simple_list_item_1, alumnosList);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Toast.makeText(ComunicadorGrillaActivity.this, adapterView.getAdapter().getItem(i).toString(), Toast.LENGTH_SHORT);
                System.out.println(adapterView.getAdapter().getItem(position).toString());
                Intent intent = new Intent(GrillaActivity.this, AlumnoActivity.class);
                intent.putExtra("alumno", ((Alumno)adapterView.getAdapter().getItem(position)).getId());

                startActivity(intent);
            }
        });

        Button boton_agregar = (Button) this.findViewById(R.id.button_agregar);
        boton_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GrillaActivity.this, NuevoAlumno.class));

            }
        });
    }

   /* private void inicializarListaMaps() {
        alumnos.add(crearAlumno("alumno", new Alumno("Carlos", "Ferro Viera")));
        alumnos.add(crearAlumno("alumno", new Alumno("Guillote", "Coppola")));
        alumnos.add(crearAlumno("alumno", new Alumno("Diego", "Maradona")));
    }


    private void inicializarLista() {
        alumnosList.add(new Alumno("Carlos","Ferro Viera"));
        alumnosList.add(new Alumno("Guillote", "Coppola"));
        alumnosList.add(new Alumno("Diego","Maradona"));
    }*/
   private void inicializarLista() {
       BD database = new BD(this);
       alumnosList = database.getAllAlumnos();

   }

    private HashMap<String, Alumno> crearAlumno(String key, Alumno alumno) {
        HashMap<String, Alumno>  alumnoMap= new HashMap<String, Alumno>();
        alumnoMap.put(key, alumno);
        return alumnoMap;
    }
}
