package gui;

import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import servidor.ServerMonitor;
import database.OperacionesDB;


public class Monitor extends JFrame{
	
	PanelNotificaciones PN;
	PanelFiltros PF;
	PanelEtiquetas PE;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Monitor(){
		OperacionesDB.createDB();
		/* Ya no va
		if(noCreada){
			JSONRead.cargarDatosBD();
		}
		*/			
		this.setTitle("Hermes"); 
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1200,650);
		this.getContentPane().setLayout(new GridLayout(2, 1));
		//this.setExtendedState(this.NORMAL);
		
		JPanel panelArriba = new JPanel();
		panelArriba.setSize(950, 250);
		panelArriba.setLayout(new GridLayout(1, 2));

		PanelNotificaciones panelAbajo = new PanelNotificaciones();
		
		PanelFiltros pf= new PanelFiltros(panelAbajo);
		panelArriba.add(pf);
		PanelEtiquetas pe= new PanelEtiquetas(pf,panelAbajo);
		panelArriba.add(pe);
		
		this.PN = panelAbajo;
		this.PF= pf;
		this.PE=pe;
		
		this.add(panelArriba);
		this.add(panelAbajo);
	}
	
	public PanelNotificaciones getPanelNotificaciones(){
		return this.PN;
	}
	
	public PanelFiltros getPanelFiltros() {
		return PF;
	}

	public PanelEtiquetas getPanelEtiquetas() {
		return PE;
	}


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Monitor frame = new Monitor();
					frame.setVisible(true);
					new ServerMonitor(frame).start();
					

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
