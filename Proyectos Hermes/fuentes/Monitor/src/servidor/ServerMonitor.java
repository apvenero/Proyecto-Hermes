package servidor;

import gui.Monitor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.Properties;

import com.sun.net.httpserver.HttpServer;

public class ServerMonitor extends Thread { 

	private HttpServer server;
	
	public ServerMonitor(Monitor frame) throws IOException {
		try{
			Properties puerto = loadProperties();	

			server = HttpServer.create(new InetSocketAddress(Integer.parseInt(puerto.getProperty("port"))),0);
		
			// se añade el handler a la url especificada
			server.createContext("/hermes", new NotificacionHandler(frame));

			// se asocia un executor que permite varias peticiones a la vez.
			server.setExecutor(java.util.concurrent.Executors.newCachedThreadPool());

			System.out.println("Iniciando el servidor..");
			// iniciamos las escuchas del servidor
			server.start();
		    System.out.println("Server is started and listening on port " + 8003);
			
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	private Properties loadProperties() throws IOException {
		//InputStreamReader in = new InputStreamReader(ClassLoader.getSystemClassLoader().getResourceAsStream("/conf.properties"));
		Properties prop = new Properties();
		InputStream input = getClass().getResourceAsStream("/conf.properties");		
		if (input != null) {
			prop.load(input);
		} else {
			throw new FileNotFoundException("Server configuration file not found");
		}
		return prop;
	}



}



