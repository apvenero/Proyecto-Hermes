package clasesDAOImpl;

import clasesDAO.ICategoriaDAO;
import clasesDAO.IContenidoDAO;
import clasesDAO.IContextoDAO;
import clasesDAO.IEtiquetaDAO;
import clasesDAO.INinioDAO;
import clasesDAO.INotificacionDAO;

public class FactoryDAO {
	
	public static ICategoriaDAO getCategoriaDAO(){
		return new CategoriaDAO(); 
	}
	
	public static IContenidoDAO getContenidoDAO(){
		return new ContenidoDAO(); 
	}
	
	public static IContextoDAO getContextoDAO(){
		return new ContextoDAO(); 
	}
	
	public static IEtiquetaDAO getEtiquetaDAO(){
		return new EtiquetaDAO(); 
	}
	
	public static INinioDAO getNinioDAO(){
		return new NinioDAO(); 
	}
	
	public static INotificacionDAO getNotificacionDAO(){
		return new NotificacionDAO(); 
	}
}
