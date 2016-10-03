package gui;

import java.awt.SystemColor;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import clasesDAO.INotificacionDAO;
import clasesDAOImpl.FactoryDAO;
import clasesDAOImpl.NotificacionDAO;
import model.Etiqueta;
import model.Notificacion;

import java.awt.GridLayout;
import java.util.List;

public class PanelNotificaciones extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private List<Notificacion> lista;
	private DefaultTableModel model;
	
	/**
	 * Create the panel.
	 */
	public PanelNotificaciones() {
		this.setBorder(new TitledBorder(null, "Notificaciones", TitledBorder.CENTER, TitledBorder.TOP, null, SystemColor.textHighlight));
		setLayout(new GridLayout(1, 0, 0, 0));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 560, 227);
		   
		// se añade el panel de scroll a la ventana 
		add(scrollPane);
		 
		// nombre de las columnas 
		//String[] columnNames = { "Fecha/Hora envio", "Contenido", "Contexto","Categoria","Niñ@"};

		if (lista == null){
			INotificacionDAO nDAO = FactoryDAO.getNotificacionDAO();
			lista = nDAO.listaNotificaciones();
		}
		System.out.println(lista);
		model = new DefaultTableModel();	
		
		cargarTabla(lista);
		

		table = new JTable();		
		table.setModel(model);
		scrollPane.setViewportView(table);
		// Instanciamos el TableRowSorter y lo añadimos al JTable
		TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<TableModel>(model);
		table.setRowSorter(elQueOrdena);
		
	}
	
	public List<Notificacion> getNotificaciones(){
		return lista;
	}
	
	public void setNotificaciones(List<Notificacion> list){
		lista = list;
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public DefaultTableModel getModel() {
		return model;
	}

	public void setModel(DefaultTableModel model) {
		this.model = model;
	}

	public void cargarTabla(List<Notificacion> lista){
		if (lista != null){
		model.setDataVector(null, new	String[] { "Fecha/Hora envio", "Contenido", "Contexto","Categoria","Niñ@", "Etiqueta"});
		model.setColumnIdentifiers(	new	String[] { "Fecha/Hora envio", "Contenido", "Contexto","Categoria","Niñ@", "Etiqueta"});
		for (Notificacion n: lista) {
			Object[] rowData = new Object[6];//Creamos un Objeto con tantos parámetros como datos retorne cada fila 
            							// de la consulta
	    	rowData[0] = n.getFechaEnvio();
	    	rowData[1] = n.getContenido().getDescripcion();
	    	rowData[2] = n.getContexto().getDescripcion();
	    	rowData[3] = n.getCategoria().getDescripcion();
	    	rowData[4] = n.getNinio().getNombre();
	    	List<Etiqueta> etiquetas = n.getEtiquetas();
	    	for(Etiqueta e: etiquetas){
	    		rowData[5] = (rowData[5]!=null)?rowData[5]+","+e.getDescripcion():e.getDescripcion();	
	    	}
	    	
	    	model.addRow(rowData);
			}
		}else {
			model.setDataVector(null, new	String[] { "Fecha/Hora envio", "Contenido", "Contexto","Categoria","Niñ@", "Etiqueta"});
			model.setColumnIdentifiers(	new	String[] { "Fecha/Hora envio", "Contenido", "Contexto","Categoria","Niñ@", "Etiqueta"});
		}
	}
	public void recargarTabla(){
		cargarTabla(new NotificacionDAO().listaNotificaciones());
	}
	

}



