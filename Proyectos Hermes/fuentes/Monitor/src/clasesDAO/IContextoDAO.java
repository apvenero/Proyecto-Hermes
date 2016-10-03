package clasesDAO;

import java.util.List;

import model.Contexto;

public interface IContextoDAO {

	public abstract Contexto getContexto(Integer id);
	
	public abstract Contexto getIdContexto(String contexto);
	
	public abstract void guardarContexto(Contexto contexto);
	
	public abstract List<Contexto> listaContextos();

}
