
package gui;

import java.awt.Color;
import java.awt.SystemColor;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import model.Etiqueta;
import model.Notificacion;
import clasesDAO.IEtiquetaDAO;
import clasesDAO.INotificacionDAO;
import clasesDAOImpl.FactoryDAO;

import java.awt.Cursor;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class PanelEtiquetas extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFieldCrearE;
	private JTextField textFieldNewNombre;
	private JComboBox<String> comboEliminar;
	private JComboBox<String> comboAsignar;
	private JComboBox<String> comboRenombrar;
	private IEtiquetaDAO eDAO = FactoryDAO.getEtiquetaDAO();
	private DefaultComboBoxModel<String> model;
	/**
	 * Create the panel.
	 */
	public PanelEtiquetas(PanelFiltros pf, PanelNotificaciones pn) {
		setBackground(SystemColor.inactiveCaption);
		this.setBorder(new TitledBorder(new LineBorder(new Color(255, 255, 255)), "Etiquetas", TitledBorder.CENTER, TitledBorder.TOP, null, SystemColor.textHighlight));
		this.setSize(475,250);
		JLabel lblCrearEtiqueta = new JLabel("Crear Etiqueta:");
		textFieldCrearE = new JTextField();
		textFieldCrearE.setColumns(10);
		
		model= new DefaultComboBoxModel<String>();

		JButton btnCrear = new JButton("Crear");
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				if(!textFieldCrearE.getText().isEmpty()){
					Etiqueta e = new Etiqueta();
					e.setDescripcion(textFieldCrearE.getText());
					eDAO.guardarEtiqueta(e);
					textFieldCrearE.setText(null);
					model.addElement(e.getDescripcion());
					((DefaultComboBoxModel<String>)pf.getComboEtiqueta().getModel()).addElement(e.getDescripcion());
					
				}
			}
		});
		
		JSeparator separator = new JSeparator();
		JLabel lblNewLabel = new JLabel("Eliminar Etiqueta:");
		
		IEtiquetaDAO eDAO = FactoryDAO.getEtiquetaDAO();
		List<Etiqueta> lista = eDAO.listaEtiquetas();
		model.addElement("--Seleccione--");

		for (Etiqueta l :lista){
			model.addElement(l.getDescripcion());
			
		}

		comboEliminar = new JComboBox<String>(model);
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evento) {
				Etiqueta e= new Etiqueta();
				e.setDescripcion((String) comboEliminar.getSelectedItem());
				eDAO.eliminarEtiqueta(e);
				eDAO.eliminarEtiquetaNotificacion(e);
				
				int resp=JOptionPane.showConfirmDialog(null,"¿Esta seguro que desea eliminar la etiqueta?");
			    if (JOptionPane.OK_OPTION == resp){
			    	model.removeElement(e.getDescripcion());
					((DefaultComboBoxModel<String>)pf.getComboEtiqueta().getModel()).removeElement(e.getDescripcion());
					
					INotificacionDAO nDAO = FactoryDAO.getNotificacionDAO();
					List<Notificacion> noti = nDAO.listaNotificaciones();
					pn.cargarTabla(noti);
			    }
			    comboEliminar.setSelectedItem("--Seleccione--");
			}
			
		});

		JSeparator separator_1 = new JSeparator();
		
		JLabel lblAsignacionEtiqueta = new JLabel("Asignacion Etiqueta:");
		comboAsignar = new JComboBox<String>(model);
		
		JButton btnAsignardesasig = new JButton("Asignar/Desasig.");
		btnAsignardesasig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// busco la etiqueta que se quiere agregar
				String str_selected_eti =(String) model.getSelectedItem();
				if (!str_selected_eti.equals("--Seleccione--")){
				IEtiquetaDAO eDAO = FactoryDAO.getEtiquetaDAO();
				Etiqueta selected_eti = eDAO.getIdEtiqueta(str_selected_eti);
				
				// Busco la notificacion que fue seleccionada
				INotificacionDAO nDAO = FactoryDAO.getNotificacionDAO();
				int id_not = pn.getTable().getSelectedRow();
				id_not++; //porque la posicion en la bd arranca de 1
				Notificacion selected_not = nDAO.getNotificacion(id_not);
				
				//busco si la notificacion tiene la etiqueta
				boolean existe_etiqueta = false;
				for(Etiqueta n_etiqueta: selected_not.getEtiquetas()){					
					if (selected_eti.getDescripcion().equals(n_etiqueta.getDescripcion())){ 
						existe_etiqueta = true;
						}
				}
				if (existe_etiqueta){
					// Borro la etiqueta de la notificacion
					eDAO.eliminarEtiquetaDeNotificacion(selected_not, selected_eti);
				}else{
					// Agrego la etiqueta a la notificacion
					eDAO.agregarEtiquetaNotificacion(selected_not, selected_eti);
				}
				//recargo la tabla del panel de notificaciones
				pn.cargarTabla(nDAO.listaNotificaciones());

				}
			}
				
		});
		JSeparator separator_2 = new JSeparator();

		JLabel lblRenombrarEtiqueta = new JLabel("Renombrar Etiqueta:");
		comboRenombrar = new JComboBox<String>(model);
		JLabel lblNuevoNombre = new JLabel("Nuevo Nombre:");
		textFieldNewNombre = new JTextField();
		textFieldNewNombre.setColumns(10);
		JButton btnRenombrar = new JButton("Renombrar");
		btnRenombrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String seleccion= (String) model.getSelectedItem();
				if(seleccion!= "--Seleccione--"){
					String nuevoNombre= textFieldNewNombre.getText();
					List<Etiqueta>lista = eDAO.listaEtiquetas();
					Boolean existe=false;
					for (Etiqueta l: lista){
						if (l.getDescripcion().equals(nuevoNombre)){
							existe=true;
						}
					}
					if(!existe){
						Etiqueta etiqueta= new Etiqueta();
						etiqueta.setDescripcion(seleccion);
						eDAO.actualizarEtiqueta(etiqueta, nuevoNombre);
						model.removeElement(etiqueta.getDescripcion());
						model.addElement(nuevoNombre);
						((DefaultComboBoxModel<String>)pf.getComboEtiqueta().getModel()).removeElement(etiqueta.getDescripcion());
						((DefaultComboBoxModel<String>)pf.getComboEtiqueta().getModel()).addElement(nuevoNombre);
						
						INotificacionDAO nDAO = FactoryDAO.getNotificacionDAO();
						List<Notificacion> noti = nDAO.listaNotificaciones();
						pn.cargarTabla(noti);
						textFieldNewNombre.setText("");
						model.setSelectedItem("--Seleccione--");
						JOptionPane.showMessageDialog (null, "La etiqueta se renombro exitosamente", "Mensaje", JOptionPane.INFORMATION_MESSAGE);

					}else{
						JOptionPane.showMessageDialog (null, "La etiqueta ya existe!", "Mensaje", JOptionPane.ERROR_MESSAGE);
						textFieldNewNombre.setText("");
					}
				}else
			        JOptionPane.showMessageDialog(null, "Seleccione la etiqueta a RENOMBRAR");
			}
		});
			
		btnRenombrar.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblRenombrarEtiqueta, GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
									.addGap(26))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblNuevoNombre, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addGap(53)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(comboRenombrar, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(textFieldNewNombre, GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE))
							.addGap(20)
							.addComponent(btnRenombrar, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE))
						.addComponent(separator_2, GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblAsignacionEtiqueta, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(27)
							.addComponent(comboAsignar, 0, 168, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnAsignardesasig, GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE))
						.addComponent(separator_1, GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(41)
							.addComponent(comboEliminar, 0, 167, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnEliminar, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE))
						.addComponent(separator, GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblCrearEtiqueta, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(49)
							.addComponent(textFieldCrearE, GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnCrear, GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)))
					.addGap(23))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCrearEtiqueta)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(textFieldCrearE, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnCrear, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(separator, GroupLayout.DEFAULT_SIZE, 2, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(comboEliminar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnEliminar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(13)
					.addComponent(separator_1, GroupLayout.DEFAULT_SIZE, 2, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAsignacionEtiqueta)
						.addComponent(comboAsignar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnAsignardesasig, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(separator_2, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblRenombrarEtiqueta)
						.addComponent(comboRenombrar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNuevoNombre)
								.addComponent(btnRenombrar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGap(33))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(21)
							.addComponent(textFieldNewNombre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())))
		);
		setLayout(groupLayout);
		
	}
	public DefaultComboBoxModel<String> getModel() {
		return model;
	}
	public void setModel(DefaultComboBoxModel<String> model) {
		this.model = model;
	}
	
	
}
