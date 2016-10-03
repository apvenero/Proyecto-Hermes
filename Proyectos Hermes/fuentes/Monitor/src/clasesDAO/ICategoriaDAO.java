package clasesDAO;

import java.util.List;

import model.Categoria;

public interface ICategoriaDAO {


	public abstract Categoria getCategoria(Integer id);
	
	public abstract Categoria getIdCategoria(String categoria);

	public abstract void guardarCategoria(Categoria categoria);
	
	public abstract List<Categoria> listaCategorias();


}

