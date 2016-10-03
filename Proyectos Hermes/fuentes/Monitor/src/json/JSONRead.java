package json;

import java.io.*;
import java.text.ParseException;

import model.Categoria;
import model.Contenido;
import model.Contexto;
import model.Etiqueta;
import model.Ninio;
import model.Notificacion;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import clasesDAO.ICategoriaDAO;
import clasesDAO.IContenidoDAO;
import clasesDAO.IContextoDAO;
import clasesDAO.IEtiquetaDAO;
import clasesDAO.INinioDAO;
import clasesDAO.INotificacionDAO;
import clasesDAOImpl.FactoryDAO;

public class JSONRead {
	
private static final String filePath = "notificaciones.json";
	
	public void cargarDatosBD() {

		try {
			// read the json file

			BufferedReader in = new BufferedReader(new InputStreamReader(ClassLoader.getSystemClassLoader().getResourceAsStream(filePath)));

			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(in);
			
			JSONArray lang= (JSONArray) jsonObject.get("notificaciones");

			for(int i=0; i<lang.size(); i++){
				JSONObject noti= (JSONObject)lang.get(i) ;
				String contenido= noti.get("contenido").toString();
				String contexto= noti.get("contexto").toString();
				String categoria= noti.get("categoria").toString();
				String ninio= noti.get("ninio").toString();
				String etiqueta= noti.get("etiqueta").toString();
				
				cargarNotificacion(contenido,contexto,categoria,ninio,etiqueta);
			}

		}catch(Exception ex){
			System.err.println("Error: "+ex.toString());
		}finally{}
	}
	
	
	public void cargarNotificacion(String contenido, String contexto,String categoria, String ninio, String etiqueta) throws ParseException {
		
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
		
		//Cargar Etiqueta
		IEtiquetaDAO eDAO = FactoryDAO.getEtiquetaDAO();
		boolean exist = false;
		Etiqueta e=null;
		for(Etiqueta et :eDAO.listaEtiquetas()){
			if (et.getDescripcion() == etiqueta){
				exist=true;
				e=et;
			}
		}
		if (!exist){
		 e= new Etiqueta();
		e.setDescripcion(etiqueta);
		int id = eDAO.guardarEtiqueta(e);
		e.setId(id);
		}
		
		
		/*Cargar notificacion */

		INotificacionDAO noDAO= FactoryDAO.getNotificacionDAO();		
		Notificacion no = new Notificacion();
		no.setIdCategoria(cDAO.getIdCategoria(categoria).getId());
		no.setIdContenido(coDAO.getIdContenido(contenido).getId());
		no.setIdContexto(cxDAO.getIdContexto(contexto).getId());
		no.setIdNinio(nDAO.getIdNinio(ninio).getId());
		no.addEtiqueta(e);
		
		noDAO.guardarNotificacion(no);

	}		


}
