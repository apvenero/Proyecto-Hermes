package gui;

import java.awt.Color;
import java.awt.SystemColor;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;

import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import model.Categoria;
import model.Contenido;
import model.Contexto;
import model.Etiqueta;
import model.Ninio;
import model.Notificacion;
import clasesDAO.ICategoriaDAO;
import clasesDAO.IContenidoDAO;
import clasesDAO.IContextoDAO;
import clasesDAO.IEtiquetaDAO;
import clasesDAO.INinioDAO;
import clasesDAO.INotificacionDAO;
import clasesDAOImpl.FactoryDAO;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import net.sourceforge.jdatepicker.impl.UtilDateModel;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;

public class PanelFiltros extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox<String> comboCategoria;
	private JComboBox<String> comboContenido;
	private JComboBox<String> comboContexto;
	private JComboBox<String> comboNinio;
	private JComboBox<String> comboEtiqueta;
	private DefaultComboBoxModel<String> modelFiltro;
	private JButton btnMostrarTodo;


	/**
	 * Create the panel.
	 */
	public JComboBox<String> getComboEtiqueta() {
		return comboEtiqueta;
	}
	
	public void HayNuevasNotificaciones(){
		btnMostrarTodo.setEnabled(true);
		JOptionPane.showMessageDialog(null,"Hay nuevas notificaciones, presiones Mostrar Todos");
		cargarComboBox();
	}

	public PanelFiltros(PanelNotificaciones pn) {
		setBackground(SystemColor.inactiveCaption);
		
		this.setBorder(new TitledBorder(new LineBorder(Color.WHITE), "Filtros", TitledBorder.CENTER, TitledBorder.TOP, null, SystemColor.textHighlight));
		//this.setSize(475,250);
		
		
		JLabel lblContenido = new JLabel("Contenido:");
		comboContenido = new JComboBox<String>();
		
		JLabel lblContexto = new JLabel("Contexto:");
		comboContexto = new JComboBox<String>();
		
		JLabel lblCategoria = new JLabel("Categoria:");
		comboCategoria = new JComboBox<String>();
		
		JLabel lblNinio = new JLabel("Ni\u00F1@:");
	    comboNinio = new JComboBox<String>();
		
	    
		JLabel lblFecha = new JLabel("Fecha/Hora");
		
		JLabel lblDesde = new JLabel("desde:");
		UtilDateModel utilDateModelDesde = new UtilDateModel();
		JDatePanelImpl datePanelDesde = new JDatePanelImpl(utilDateModelDesde);
		JDatePickerImpl datePickerImplDesde = new JDatePickerImpl(datePanelDesde);
		
		JLabel lblHasta = new JLabel("hasta:");
		UtilDateModel utilDateModelHasta = new UtilDateModel();
		JDatePanelImpl datePanelHasta = new JDatePanelImpl(utilDateModelHasta);
		JDatePickerImpl datePickerImplHasta = new JDatePickerImpl(datePanelHasta);
		
		JLabel lblEtiqueta = new JLabel("Etiqueta:");
		IEtiquetaDAO eDAO = FactoryDAO.getEtiquetaDAO();
		List<Etiqueta> lista = eDAO.listaEtiquetas();
		
		modelFiltro = new DefaultComboBoxModel<String>();
		modelFiltro.addElement("--Seleccione--");

		for (Etiqueta l :lista){
			modelFiltro.addElement(l.getDescripcion());
		}
		comboEtiqueta = new JComboBox<String>(modelFiltro);
		
		JButton btnFiltrar = new JButton("Filtrar");
		btnFiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				INotificacionDAO nDAO = FactoryDAO.getNotificacionDAO();
				Notificacion notificacion= new Notificacion();
				
				String co= (String) comboContenido.getSelectedItem();
				if (co!="--Seleccione--"){
					IContenidoDAO cDAO = FactoryDAO.getContenidoDAO();
					Contenido contenido= new Contenido();
					contenido.setDescripcion(co);
					contenido.setId(cDAO.getIdContenido(co).getId());
					notificacion.setContenido(contenido);

					System.out.println(contenido);
				}
				
				String cx = (String) comboContexto.getSelectedItem();
				if(cx!="--Seleccione--"){
					IContextoDAO cxDAO = FactoryDAO.getContextoDAO();
					Contexto contexto= new Contexto();
					contexto.setDescripcion(cx);
					contexto.setId(cxDAO.getIdContexto(cx).getId());
					notificacion.setContexto(contexto);
					System.out.println(contexto);
				}
				String c= (String) comboCategoria.getSelectedItem();
				if(c!="--Seleccione--"){
					ICategoriaDAO caDAO = FactoryDAO.getCategoriaDAO();
					Categoria categoria= new Categoria();
					categoria.setDescripcion(c);
					categoria.setId(caDAO.getIdCategoria(c).getId());
					notificacion.setCategoria(categoria);

					System.out.println(categoria);
				}
				
				String n= (String) comboNinio.getSelectedItem();
					if(n!="--Seleccione--"){
					INinioDAO niDAO = FactoryDAO.getNinioDAO();
					Ninio ninio= new Ninio();
					ninio.setNombre(n);
					ninio.setId(niDAO.getIdNinio(n).getId());
					notificacion.setNinio(ninio);

					System.out.println(ninio);
				}
				
				Date desde = (Date) datePickerImplDesde.getModel().getValue();		
				Date hasta = (Date) datePickerImplHasta.getModel().getValue();
				
				String et= (String) comboEtiqueta.getSelectedItem();
				if(et!="--Seleccione--"){
					IEtiquetaDAO eDAO = FactoryDAO.getEtiquetaDAO();
					Etiqueta etiqueta= new Etiqueta();
					etiqueta.setDescripcion(et);
					etiqueta.setId(eDAO.getIdEtiqueta(et).getId());
					notificacion.setEtiqueta(etiqueta);

					System.out.println(etiqueta);
				}
				
				List<Notificacion> resultado= nDAO.filtrarNotificacion(notificacion,desde,hasta);
				//System.out.println(resultado);
				if(resultado.isEmpty()){
					JOptionPane.showMessageDialog(null,"No se encontro ninguna coincidencia.");
					pn.cargarTabla(null);
				}else{
					pn.cargarTabla(resultado);

				}
			}
		});
		
		this.btnMostrarTodo = new JButton("Mostrar todo");
		this.btnMostrarTodo.addMouseListener(new MouseListener() {
		
			
			@Override
			public void mouseClicked(MouseEvent e) {
				comboCategoria.setSelectedIndex(0);
				comboContenido.setSelectedIndex(0);
				comboContexto.setSelectedIndex(0);
				comboNinio.setSelectedIndex(0);
				comboEtiqueta.setSelectedIndex(0);
				
				
				//dateFromFilter.cleanup();
				//dateToFilter.cleanup();
				INotificacionDAO nDAO = FactoryDAO.getNotificacionDAO();
				List<Notificacion> lista= nDAO.listaNotificaciones();
				
				pn.cargarTabla(lista);
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblContenido)
						.addComponent(lblContexto)
						.addComponent(lblNinio)
						.addComponent(lblFecha)
						.addComponent(lblEtiqueta))
					.addGap(70)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(datePickerImplDesde, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(datePickerImplHasta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(comboEtiqueta, 0, 181, Short.MAX_VALUE)
								.addComponent(comboNinio, 0, 181, Short.MAX_VALUE)
								.addComponent(comboContexto, 0, 181, Short.MAX_VALUE)
								.addComponent(comboContenido, 0, 181, Short.MAX_VALUE)
								.addComponent(lblDesde))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblCategoria)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(comboCategoria, 0, 150, Short.MAX_VALUE))
								.addComponent(lblHasta))))
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(34)
					.addComponent(btnFiltrar, GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
					.addGap(66)
					.addComponent(btnMostrarTodo, GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
					.addGap(64))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblContenido)
						.addComponent(comboContenido, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblContexto)
						.addComponent(comboContexto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblCategoria)
						.addComponent(comboCategoria, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNinio)
						.addComponent(comboNinio, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblFecha)
						.addComponent(lblDesde)
						.addComponent(lblHasta))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(datePickerImplDesde, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
							.addGap(20)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblEtiqueta)
								.addComponent(comboEtiqueta, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addComponent(datePickerImplHasta, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
					.addGap(32)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnFiltrar, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnMostrarTodo, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
					.addGap(30))
		);
		setLayout(groupLayout);
		
		cargarComboBox();
		
	}
	
	
	public void cargarComboBox() {
		
		this.llenarComboCategoria();
		this.llenarComboContenido();
		this.llenarComboContexto();
		this.llenarComboNinio();
		
	}

	public void llenarComboCategoria() {
		ICategoriaDAO cDAO = FactoryDAO.getCategoriaDAO();
		List<Categoria> lista = cDAO.listaCategorias();
		comboCategoria.removeAllItems();
		comboCategoria.addItem("--Seleccione--");
		for (Categoria l :lista){
			comboCategoria.addItem(l.getDescripcion());
		}
	}

	public void llenarComboContenido() {
		IContenidoDAO cDAO = FactoryDAO.getContenidoDAO();
		List<Contenido> listac = cDAO.listaContenidos();
		comboContenido.removeAllItems();
		comboContenido.addItem("--Seleccione--");
		for (Contenido l :listac){
			comboContenido.addItem(l.getDescripcion());
		}
	}
	public void llenarComboContexto() {
		IContextoDAO cxDAO = FactoryDAO.getContextoDAO();
		List<Contexto> lista = cxDAO.listaContextos();
		comboContexto.removeAllItems();
		comboContexto.addItem("--Seleccione--");

		for (Contexto l :lista){
			comboContexto.addItem(l.getDescripcion());
		}
	}
	public void llenarComboNinio() {
		INinioDAO nDAO = FactoryDAO.getNinioDAO();
		List<Ninio> lista = nDAO.listaNinios();
		comboNinio.removeAllItems();
		comboNinio.addItem("--Seleccione--");
		for (Ninio l :lista){
			comboNinio.addItem(l.getNombre());
		}
	}
	

	public DefaultComboBoxModel<String> getModelFiltro() {
		return modelFiltro;
	}


	public void setModelFiltro(DefaultComboBoxModel<String> modelFiltro) {
		this.modelFiltro = modelFiltro;
	}
}