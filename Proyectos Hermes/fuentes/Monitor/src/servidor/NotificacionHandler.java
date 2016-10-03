package servidor;

import gui.Monitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import model.Categoria;
import model.Contenido;
import model.Contexto;
import model.Ninio;
import model.Notificacion;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;





import clasesDAO.ICategoriaDAO;
import clasesDAO.IContenidoDAO;
import clasesDAO.IContextoDAO;
import clasesDAO.INinioDAO;
import clasesDAO.INotificacionDAO;
import clasesDAOImpl.FactoryDAO;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


public class NotificacionHandler implements HttpHandler {
	
	Monitor frame;
	
	NotificacionHandler(Monitor f) {
		this.frame = f;
	}
	@Override
	public void handle(HttpExchange t) throws IOException {

		InputStream is = t.getRequestBody();
		BufferedReader requestBufferReader = new BufferedReader(new InputStreamReader(is));
		String inputLine = requestBufferReader.readLine();
		
		
		try {
			JSONObject jObject = (JSONObject) new JSONParser().parse(inputLine);
			
			JSONArray lang = (JSONArray) jObject.get("notificaciones");
			for(int i=0; i<lang.size();i++) {
			
				JSONObject fila = (JSONObject)lang.get(i);
				
				String contenido= (String) fila.get("contenido");
				String contexto= (String) fila.get("contexto");
				String categoria= (String) fila.get("categoria");
				String ninio= (String) fila.get("ninio");
				String fecha= (String) fila.get("fechaEnvio");

				System.out.println(contenido +" "+ contexto +" "+ categoria +" "+ ninio +" "+ fecha);
				
				//Cargar categoria
				ICategoriaDAO cDAO = FactoryDAO.getCategoriaDAO();
				Categoria c = new Categoria();
				c.setDescripcion(categoria);
				cDAO.guardarCategoria(c); 
						
				//Cargar contenido
				IContenidoDAO coDAO = FactoryDAO.getContenidoDAO();
				Contenido co = new Contenido();
				co.setDescripcion(contenido);
				coDAO.guardarContenido(co);
				
				//Cargar contexto
				IContextoDAO cxDAO = FactoryDAO.getContextoDAO();
				Contexto cx = new Contexto();
				cx.setDescripcion(contexto);
				cxDAO.guardarContexto(cx);

				//Cargar niño
				INinioDAO nDAO = FactoryDAO.getNinioDAO();
				Ninio n = new Ninio();
				n.setNombre(ninio);
				nDAO.guardarNinio(n);		
				
				/*Cargar notificacion */

				INotificacionDAO noDAO= FactoryDAO.getNotificacionDAO();		
				Notificacion no = new Notificacion();
				no.setIdCategoria(cDAO.getIdCategoria(categoria).getId());
				no.setIdContenido(coDAO.getIdContenido(contenido).getId());
				no.setIdContexto(cxDAO.getIdContexto(contexto).getId());
				no.setIdNinio(nDAO.getIdNinio(ninio).getId());
				no.setFechaEnvio(fecha);
				System.out.println("GUADANDOOO...");

				noDAO.guardarNotificacion(no);
			}

		//ACTUALIZAR TABLA y COMBOS
			this.frame.getPanelFiltros().HayNuevasNotificaciones();
		//this.frame.getPanelNotificaciones().recargarTabla();
		
		//this.frame.getPanelFiltros().cargarComboBox();
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 String response = "una respuesta";
		 t.sendResponseHeaders(200, response.length());
		 OutputStream os =  t.getResponseBody();
		 os.write(response.getBytes());
		 os.close();
		 
		}
}
