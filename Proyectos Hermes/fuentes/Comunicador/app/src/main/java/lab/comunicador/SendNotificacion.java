package lab.comunicador;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;

public class SendNotificacion extends AsyncTask {

    @Override
    protected Void doInBackground(Object... params) {
        final Context context = (Context) params[0];
        BD db = new BD(context);

        List<Notificacion> lista= db.getAllNotificaciones();
        JSONArray ja= new JSONArray();

        for(Notificacion l : lista){
            if ((l.getContenido().equals("tristefem")) || (l.getContenido().equals("tristemasc"))){
                l.setContenido("triste");
            }
            if ((l.getContenido().equals("femsed")) || (l.getContenido().equals("mascsed"))){
                l.setContenido("sed");
            }
            System.out.println(l.getNombreNinio() +","+ l.getContenido() +"," +l.getCategoria());
            JSONObject js = new JSONObject();
            try{
            js.put("ninio", l.getNombreNinio());
            js.put("contenido", l.getContenido());
            js.put("contexto", "CEDICA");
            js.put("categoria", l.getCategoria());
            js.put("fechaEnvio", new Date());

            ja.add(js);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        System.out.println(ja);

        try {
        //Abrir la conexión hacia el servidor
        Configuracion config= db.getConfiguracion();
        if (config != null) {
            String ip = config.getIp();
            String puerto = config.getPuerto();

            System.out.println(config.toString());
            String url = "http://" + ip + ":" + puerto + "/hermes";
            //String url= "http://192.168.0.5:8003/hermes";

            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

            //request header
            conn.setRequestMethod("POST");
            //conn.setReadTimeout(2000);
            conn.setRequestProperty("Accept-Language", "en-US,en;q=0.8");
            conn.setRequestProperty("Content-type", "application/json");
            conn.setConnectTimeout(10 * 7000);
            // se habilita el envio
            conn.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes("{ \"notificaciones\": " + ja.toString() + "}");
            wr.flush();
            wr.close();

            int responseCode = conn.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
            System.out.println("Response Mensage : " + conn.getResponseMessage());

            if (responseCode == 200) {
                db.borrarNotificaciones(lista);
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer html = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                html.append(inputLine);
            }
            in.close();
        }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


}
