package clasesDAO;

import java.util.List;

import model.Ninio;


public interface INinioDAO {

	public abstract Ninio getNinio(Integer id);
	
	public abstract Ninio getIdNinio(String ninio);

	public abstract void guardarNinio(Ninio ninio);
	
	public abstract List<Ninio> listaNinios();

}
