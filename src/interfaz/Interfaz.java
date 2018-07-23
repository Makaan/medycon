package interfaz;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;

import almacenamiento.EscritorExcel;
import conexion.AdminConexiones;
import controlador.AdminMensajes;
import controlador.AdminTabla;
import controlador.Controlador;

public class Interfaz extends JFrame implements MensajesGUI, TablaGUI<String>, InfoGUI, GraficoGUI{
	
	private final Color COLOR_AGUA = new Color(90,188,216);
	private final Color COLOR_VERDE = new Color(0x04B404);
	private final Color COLOR_ROJO = new Color(0xDF0101);

    private static final long serialVersionUID = 1L;
    
    private final static AdminConexiones adminConexiones = AdminConexiones.getInstancia();
    
    private JTable table;
    private DefaultTableModel tableModel;
    private JPanel panelConexion;
    private Controlador controlador;
    
    private JTextField textFieldNivelAbsolutoMax;
    private JTextField textFieldNivelAbsolutoMin;
    private JTextField textFieldNivelRele1Max;
    private JTextField textFieldNivelRele1Min;
    private JTextField textFieldNivelRele2Max;
    private JTextField textFieldNivelRele2Min;
    private JLabel lblNivelRele1Grafico;
    private JLabel lblNivelRele2Grafico;
    private JLabel lblNivel;
    private JComboBox<String> comboBoxTipoNivel;
    private JProgressBar progressBarAbsolutoGrafico;
    private JList<String> listaConexiones;
    
    private JPanel panelGrafico;
    private JScrollPane scrollPane;
    private JPanel panelContenedorGrafico;
    
    private String conexionSeleccionada = null;
    
    Map<String, String> mapeoNiveles;
    

    public Interfaz() {
    	
    	this.setTitle("MedyCon NUSV2.0");
    	
    	AdminMensajes.getInstancia().setInterfaz(this);
    	
    	mapeoNiveles = new LinkedHashMap<String,String>();
    	
    	mapeoNiveles.put("Porcentaje", "% 0");
    	mapeoNiveles.put("Altura", "0 cm");
    	
    	
    	
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.rowWeights = new double[]{0.0, 0.0};
        gridBagLayout.columnWeights = new double[]{1.0, 0.0};
        getContentPane().setLayout(gridBagLayout);// set LayoutManager
        setPreferredSize(new Dimension(1280,720));
        panelConexion = new JPanel();
        Border eBorder = BorderFactory.createEtchedBorder();
        
        /* PANEL CONEXION */

        panelConexion.setBorder(BorderFactory.createTitledBorder(eBorder, "Conexión"));
        GridBagConstraints gbc_panelConexion = new GridBagConstraints();
        gbc_panelConexion.insets = new Insets(0, 0, 5, 5);
        gbc_panelConexion.gridx = gbc_panelConexion.gridy = 0;
        gbc_panelConexion.gridwidth = gbc_panelConexion.gridheight = 1;
        gbc_panelConexion.fill = GridBagConstraints.BOTH;
        gbc_panelConexion.anchor = GridBagConstraints.NORTHWEST;
        gbc_panelConexion.weightx = 20;
        gbc_panelConexion.weighty = 40;
        getContentPane().add(panelConexion, gbc_panelConexion);
        GridBagLayout gbl_panelConexion = new GridBagLayout();
        panelConexion.setLayout(gbl_panelConexion);
        	
        	DefaultListModel<String> listaModel = new DefaultListModel<String>();
        	
        	for(String nombre: adminConexiones.getNombreConexiones()) {
        		listaModel.addElement(nombre);
        	}
        	listaConexiones = new JList<String>(listaModel);
	        listaConexiones.addListSelectionListener( new ListSelectionListener() {

				@Override
				public void valueChanged(ListSelectionEvent e) {
					conexionSeleccionada = listaConexiones.getSelectedValue();
					controlador.conexionSeleccionada(conexionSeleccionada);
				}
	        	
	        });
	        
	        

	        if(listaConexiones.getSelectedValue() != null) {
	        	conexionSeleccionada = listaConexiones.getSelectedValue().toString();
	        }
	        
	        JScrollPane scrollListaConexiones = new JScrollPane(listaConexiones);
	        scrollListaConexiones.setBorder(BorderFactory.createTitledBorder("Lista de conexiones"));
	        
	        GridBagConstraints gbc_scrollListaConexiones = new GridBagConstraints();
	        gbc_scrollListaConexiones.anchor = GridBagConstraints.NORTH;
	        gbc_scrollListaConexiones.gridheight = 7;
	        gbc_scrollListaConexiones.gridwidth = 2;
	        gbc_scrollListaConexiones.weightx = 65;
	        gbc_scrollListaConexiones.fill = GridBagConstraints.BOTH;
	        gbc_scrollListaConexiones.insets = new Insets(10, 10, 10, 10);
	        gbc_scrollListaConexiones.gridx = 1;
	        gbc_scrollListaConexiones.gridy = 0;
	        panelConexion.add(scrollListaConexiones, gbc_scrollListaConexiones);
	        
	        JButton btnConectar = new JButton("Conectar");
	        btnConectar.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(listaConexiones.getSelectedValue() != null) {
						String nombreConexion = listaConexiones.getSelectedValue().toString();
						controlador.nuevaConexion(nombreConexion);
					}
					
				}
			});
	        GridBagConstraints gbc_btnConectar = new GridBagConstraints();
	        gbc_btnConectar.fill = GridBagConstraints.BOTH;
	        gbc_btnConectar.insets = new Insets(0, 0, 5, 5);
	        gbc_btnConectar.gridx = 0;
	        gbc_btnConectar.gridy = 0;
	        gbc_btnConectar.weightx = 25;
	        panelConexion.add(btnConectar, gbc_btnConectar);
        
	        JButton btnDesconectar = new JButton("Desconectar");
	        btnDesconectar.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if(conexionSeleccionada != null) {
						controlador.cerrarConexion(listaConexiones.getSelectedValue());
					}
				}
	        	
	        });
	        GridBagConstraints gbc_btnDesconectar = new GridBagConstraints();
	        gbc_btnDesconectar.fill = GridBagConstraints.BOTH;
	        gbc_btnDesconectar.insets = new Insets(0, 0, 5, 5);
	        gbc_btnDesconectar.gridx = 0;
	        gbc_btnDesconectar.gridy = 1;
	        gbc_btnDesconectar.weightx = 25;
	        panelConexion.add(btnDesconectar, gbc_btnDesconectar);
	        
	        JButton btnNuevaConexion = new JButton("Nueva Conexión");
	        JTextField nombre = new JTextField();
        	JTextField ip = new JTextField();
        	JTextField puerto = new JTextField();
        	JTextField id = new JTextField();
        	
        	Object[] message = {
        	    "Nombre:", nombre,
        	    "IP:", ip,
        	    "Puerto", puerto,
        	    "ID:", id
        	    
        	};
	        btnNuevaConexion.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int option = JOptionPane.showConfirmDialog(null, message, "Crear Nueva Conexión", JOptionPane.OK_CANCEL_OPTION);
		        	if (option == JOptionPane.OK_OPTION) {
		        		if(!nombre.getText().equals("") || !ip.getText().equals("") || !id.getText().equals("") || !puerto.getText().equals("")) {
		        			if (isValidIP(ip.getText())) {
			        	        adminConexiones.guardarConexion(nombre.getText(), ip.getText(), puerto.getText(), id.getText(), "30000", "-1", "-1");
			        	        ListModel<String> listModel = listaConexiones.getModel();
			        	        ((DefaultListModel<String>) listModel).addElement(nombre.getText());
			        	    } else {
			        	    	mostrarMensajeError("La IP ingresada no es valida");
			        	    }
		        		}
		        		else {
		        			mostrarMensajeError("Se deben completar todos los campos");
		        		}
		        	    
		        	}
				}
			});
	        GridBagConstraints gbc_btnNuevaConexion = new GridBagConstraints();
	        gbc_btnNuevaConexion.insets = new Insets(0, 0, 5, 5);
	        gbc_btnNuevaConexion.fill = GridBagConstraints.BOTH;
	        gbc_btnNuevaConexion.gridx = 0;
	        gbc_btnNuevaConexion.gridy = 2;
	        gbc_btnNuevaConexion.weightx = 25;
	        panelConexion.add(btnNuevaConexion, gbc_btnNuevaConexion);
	        
	        JButton btnEditarConexion = new JButton("Editar Conexión");
	        
	        JTextField nombreEdit = new JTextField();
        	JTextField ipEdit = new JTextField();
        	JTextField puertoEdit = new JTextField();
        	JTextField idEdit = new JTextField();
        	Object[] editar = {
        	    "Nombre:", nombreEdit,
        	    "IP:", ipEdit,
        	    "Puerto", puertoEdit,
        	    "ID:", idEdit
        	};
	        btnEditarConexion.addActionListener(new ActionListener() {
	        	

				@Override
				public void actionPerformed(ActionEvent e) {
					if(conexionSeleccionada != null) {
						Map<String, String> datosConexion = AdminConexiones.getInstancia().getConexion(conexionSeleccionada);
						nombreEdit.setText(conexionSeleccionada);
						ipEdit.setText(datosConexion.get("ip"));
						puertoEdit.setText(datosConexion.get("puerto"));
						idEdit.setText(datosConexion.get("id"));
						
						int option = JOptionPane.showConfirmDialog(null, editar, "Editar Conexión", JOptionPane.OK_CANCEL_OPTION);
			        	if (option == JOptionPane.OK_OPTION) {
			        		if(!nombreEdit.getText().equals("") || !ipEdit.getText().equals("") || !idEdit.getText().equals("") || !puertoEdit.getText().equals("")) {
			        			if (isValidIP(ipEdit.getText())) {
			        				controlador.editarConexion(conexionSeleccionada, nombreEdit.getText(), ipEdit.getText(), puertoEdit.getText(), idEdit.getText());
			        				DefaultListModel<String> listaModel = new DefaultListModel<String>();
			        	        	
			        	        	for(String nombre: adminConexiones.getNombreConexiones()) {
			        	        		listaModel.addElement(nombre);
			        	        	}
			        	        	listaConexiones.setModel(listaModel);
				        	    } else {
				        	    	mostrarMensajeError("La IP ingresada no es valida");
				        	    }
			        		}
			        		else {
			        			mostrarMensajeError("Se deben completar todos los campos");
			        		}
			        	    
			        	}
						
					}
					
					
				};
	        	
	        });
	        GridBagConstraints gbc_btnEditarConexion = new GridBagConstraints();
	        gbc_btnEditarConexion.fill = GridBagConstraints.BOTH;
	        gbc_btnEditarConexion.insets = new Insets(0, 0, 5, 5);
	        gbc_btnEditarConexion.gridx = 0;
	        gbc_btnEditarConexion.gridy = 3;
	        gbc_btnEditarConexion.weightx = 25;
	        panelConexion.add(btnEditarConexion, gbc_btnEditarConexion);
	        
	        JButton btnEliminarConexion = new JButton("Eliminar conexion");
	        btnEliminarConexion.addActionListener(new ActionListener() {
	        	
				@Override
				public void actionPerformed(ActionEvent e) {
					if(conexionSeleccionada != null) {
						if (JOptionPane.showConfirmDialog(null, "¿Está seguro que quiere eliminar esta conexión?", "ADVERTENCIA",
						        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						    controlador.eliminarConexion(conexionSeleccionada);
						}
					}
				}
			});
	        GridBagConstraints gbc_btnEliminarConexion = new GridBagConstraints();
	        gbc_btnEliminarConexion.fill = GridBagConstraints.BOTH;
	        gbc_btnEliminarConexion.insets = new Insets(0, 0, 5, 5);
	        gbc_btnEliminarConexion.gridx = 0;
	        gbc_btnEliminarConexion.gridy = 4;
	        panelConexion.add(btnEliminarConexion, gbc_btnEliminarConexion);
	        
	        JButton btnVelocidadMuestreo = new JButton("Tiempo de Muestreo");
	        btnVelocidadMuestreo.addActionListener(new ActionListener() {
	        	
	        	JTextField tiempo = new JTextField();
	        	Object[] message = {
	            	    "Tiempo en segundos:", tiempo
	        	};

				@Override
				public void actionPerformed(ActionEvent e) {
					Map<String, String> datosConexion = AdminConexiones.getInstancia().getConexion(conexionSeleccionada);
					String tmp = datosConexion.get("tiempo");
					tiempo.setText(tmp.substring(0, tmp.length()-3));
					
					if(conexionSeleccionada != null) {
						int option = JOptionPane.showConfirmDialog(null, message, "Modificar la velocidad de muestreo", JOptionPane.OK_CANCEL_OPTION);
			        	if (option == JOptionPane.OK_OPTION) {
			        		if(!tiempo.getText().equals("")) {
			        			controlador.modificarVelocidadMuestreo(tiempo.getText()+"000", listaConexiones.getSelectedValue());
			        		}
			        		else {
			        			mostrarMensajeError("Se deben completar todos los campos");
			        		}
			        	    
			        	}
					}
				}
	        	
	        });
	        GridBagConstraints gbc_btnVelocidadMuestreo = new GridBagConstraints();
	        gbc_btnVelocidadMuestreo.fill = GridBagConstraints.BOTH;
	        gbc_btnVelocidadMuestreo.insets = new Insets(0, 0, 5, 5);
	        gbc_btnVelocidadMuestreo.gridx = 0;
	        gbc_btnVelocidadMuestreo.gridy = 5;
	        gbc_btnVelocidadMuestreo.weightx = 25;
	        panelConexion.add(btnVelocidadMuestreo, gbc_btnVelocidadMuestreo);
	        
	        JButton btnConfigurarAlarma = new JButton("Configurar Alarma");
	        btnConfigurarAlarma.addActionListener(new ActionListener() {
	        	JTextField nivelMinimo = new JTextField();
	        	JTextField nivelMaximo = new JTextField();
	        	Object[] message = {
	        			"(Si los casilleros se dejan vacio se eliminan las alarmas)",
	            	    "Nivel mínimo (%): ", nivelMinimo,
	            	    "Nivel máximo (%): ", nivelMaximo
	        	};

				@Override
				public void actionPerformed(ActionEvent e) {
					if(conexionSeleccionada != null) {
						Map<String, String> datosConexion = AdminConexiones.getInstancia().getConexion(conexionSeleccionada);
						nivelMinimo.setText(datosConexion.get("alarmaMin"));
						nivelMaximo.setText(datosConexion.get("alarmaMax"));
						if(nivelMinimo.getText().equals("-1")) {
							nivelMinimo.setText("");
						}
						if(nivelMaximo.getText().equals("-1")) {
							nivelMaximo.setText("");
						}
						int option = JOptionPane.showConfirmDialog(null, message, "Configurar alarma de nivel", JOptionPane.OK_CANCEL_OPTION);
			        	if (option == JOptionPane.OK_OPTION) {
			        			controlador.configurarAlarma(listaConexiones.getSelectedValue(), nivelMinimo.getText(), nivelMaximo.getText());
			        	}
					}
				}
	        	
	        });
	        GridBagConstraints gbc_btnConfigurarAlarma = new GridBagConstraints();
	        gbc_btnConfigurarAlarma.fill = GridBagConstraints.BOTH;
	        gbc_btnConfigurarAlarma.insets = new Insets(0, 0, 5, 5);
	        gbc_btnConfigurarAlarma.gridx = 0;
	        gbc_btnConfigurarAlarma.gridy = 6;
	        gbc_btnConfigurarAlarma.weightx = 25;
	        panelConexion.add(btnConfigurarAlarma, gbc_btnConfigurarAlarma);
	        
	        
	        
	        
        /* PANEL INFO */

        JPanel panelInfo = new JPanel();
        panelInfo.setBorder(BorderFactory.createTitledBorder(eBorder, "Info Nivel"));
        GridBagConstraints gbc_panelInfo = new GridBagConstraints();
        gbc_panelInfo.fill = GridBagConstraints.BOTH;
        gbc_panelInfo.anchor = GridBagConstraints.NORTHWEST;
        gbc_panelInfo.gridx = 0;
        gbc_panelInfo.gridy = 1;
        gbc_panelInfo.gridheight = 2;
        gbc_panelInfo.weightx = 20;
        gbc_panelInfo.weighty = 60;
        gbc_panelInfo.insets = new Insets(2, 2, 5, 5);
        getContentPane().add(panelInfo, gbc_panelInfo); // add component to the ContentPane
        GridBagLayout gbl_panelInfo = new GridBagLayout();
        gbl_panelInfo.columnWeights = new double[]{1.0};
        panelInfo.setLayout(gbl_panelInfo);
        
	        /* SUB PANEL TIPO NIVEL */
	        
	        JPanel panelTipoNivel = new JPanel();
	        GridBagConstraints gbc_panelTipoNivel = new GridBagConstraints();
	        gbc_panelTipoNivel.fill = GridBagConstraints.BOTH;
	        gbc_panelTipoNivel.anchor = GridBagConstraints.NORTHWEST;
	        gbc_panelTipoNivel.gridx = 0;
	        gbc_panelTipoNivel.gridy = 0;
	        gbc_panelTipoNivel.weightx = 20;
	        panelTipoNivel.setLayout(new GridBagLayout());
	        panelInfo.add(panelTipoNivel, gbc_panelTipoNivel);
	        
		        JLabel lblTipoDeNivel = new JLabel("Tipo de Nivel");
		        GridBagConstraints gbc_lblTipoDeNivel = new GridBagConstraints();
		        gbc_lblTipoDeNivel.insets = new Insets(0, 0, 5, 5);
		        gbc_lblTipoDeNivel.gridwidth = 1;
		        gbc_lblTipoDeNivel.gridx = 0;
		        gbc_lblTipoDeNivel.gridy = 0;
		        panelTipoNivel.add(lblTipoDeNivel, gbc_lblTipoDeNivel);
		        
		        lblNivel = new JLabel("0 %");
		        GridBagConstraints gbc_lblNivel = new GridBagConstraints();
		        gbc_lblNivel.insets = new Insets(0, 0, 5, 5);
		        gbc_lblNivel.gridx = 0;
		        gbc_lblNivel.gridy = 2;
		        panelTipoNivel.add(lblNivel, gbc_lblNivel);
		        
		        comboBoxTipoNivel = new JComboBox<String>();
		        for(String valor: mapeoNiveles.keySet()) {
		        	comboBoxTipoNivel.addItem(valor);
		        }
		        comboBoxTipoNivel.setSelectedItem(0);
		        comboBoxTipoNivel.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						String valor = mapeoNiveles.get(comboBoxTipoNivel.getSelectedItem());
						lblNivel.setText(valor);
						
					}
		        	
		        });
		        GridBagConstraints gbc_comboBoxTipoNivel = new GridBagConstraints();
		        gbc_comboBoxTipoNivel.insets = new Insets(0, 0, 5, 5);
		        gbc_comboBoxTipoNivel.fill = GridBagConstraints.HORIZONTAL;
		        gbc_comboBoxTipoNivel.gridx = 0;
		        gbc_comboBoxTipoNivel.gridy = 1;
		        panelTipoNivel.add(comboBoxTipoNivel, gbc_comboBoxTipoNivel);
		      
		        
	        /* SUB PANEL NIVEL ABSOLUTO */    
		        
		    JPanel panelNivelAbsoluto = new JPanel();
		    GridBagConstraints gbc_panelNivelAbsoluto = new GridBagConstraints();
		    gbc_panelNivelAbsoluto.fill = GridBagConstraints.BOTH;
		    gbc_panelNivelAbsoluto.anchor = GridBagConstraints.NORTHWEST;
		    gbc_panelNivelAbsoluto.gridx = 1;
		    gbc_panelNivelAbsoluto.gridy = 0;
		    gbc_panelNivelAbsoluto.weightx = 80;
		    panelNivelAbsoluto.setLayout(new GridBagLayout());
	        panelInfo.add(panelNivelAbsoluto, gbc_panelNivelAbsoluto);
	        
		        JLabel lblNivelAbsoluto = new JLabel("Nivel Absoluto");
		        GridBagConstraints gbc_lblNivelAbsoluto = new GridBagConstraints();
		        gbc_lblNivelAbsoluto.insets = new Insets(0, 0, 5, 0);
		        gbc_lblNivelAbsoluto.gridwidth = 3;
		        gbc_lblNivelAbsoluto.gridx = 0;
		        gbc_lblNivelAbsoluto.gridy = 0;
		        panelNivelAbsoluto.add(lblNivelAbsoluto, gbc_lblNivelAbsoluto);
		        
		        JLabel lblNivelAbsolutoMax = new JLabel("Max");
		        GridBagConstraints gbc_lblNivelAbsolutoMax = new GridBagConstraints();
		        gbc_lblNivelAbsolutoMax.insets = new Insets(0, 20, 5, 20);
		        gbc_lblNivelAbsolutoMax.gridx = 0;
		        gbc_lblNivelAbsolutoMax.gridy = 1;
		        panelNivelAbsoluto.add(lblNivelAbsolutoMax, gbc_lblNivelAbsolutoMax);
		        
		        textFieldNivelAbsolutoMax = new JTextField();
		        textFieldNivelAbsolutoMax.setEditable(false);
		        GridBagConstraints gbc_textFieldNivelAbsolutoMax = new GridBagConstraints();
		        gbc_textFieldNivelAbsolutoMax.insets = new Insets(0,0,5,0);
		        gbc_textFieldNivelAbsolutoMax.gridx = 0;
		        gbc_textFieldNivelAbsolutoMax.gridy = 2;
		        gbc_textFieldNivelAbsolutoMax.fill = GridBagConstraints.HORIZONTAL;
		        panelNivelAbsoluto.add(textFieldNivelAbsolutoMax, gbc_textFieldNivelAbsolutoMax);
		        
		        JLabel lblNivelAbsolutoMin = new JLabel("Min");
		        GridBagConstraints gbc_lblNivelAbsolutoMin = new GridBagConstraints();
		        gbc_lblNivelAbsolutoMin.insets = new Insets(0, 0, 5, 0);
		        gbc_lblNivelAbsolutoMin.gridx = 0;
		        gbc_lblNivelAbsolutoMin.gridy = 3;
		        panelNivelAbsoluto.add(lblNivelAbsolutoMin, gbc_lblNivelAbsolutoMin);
		        
		        textFieldNivelAbsolutoMin = new JTextField();
		        textFieldNivelAbsolutoMin.setEditable(false);
		        GridBagConstraints gbc_textFieldNivelAbsolutoMin = new GridBagConstraints();
		        gbc_textFieldNivelAbsolutoMin.insets = new Insets(0,0,5,0);
		        gbc_textFieldNivelAbsolutoMin.gridx = 0;
		        gbc_textFieldNivelAbsolutoMin.gridy = 4;
		        gbc_textFieldNivelAbsolutoMin.fill = GridBagConstraints.HORIZONTAL;
		        panelNivelAbsoluto.add(textFieldNivelAbsolutoMin, gbc_textFieldNivelAbsolutoMin);
		        
		        progressBarAbsolutoGrafico = new JProgressBar(JProgressBar.VERTICAL);
		        progressBarAbsolutoGrafico.setForeground(COLOR_AGUA);
		        progressBarAbsolutoGrafico.setBackground(Color.LIGHT_GRAY);
		        GridBagConstraints gbc_progressBarAbsolutoGrafico = new GridBagConstraints();
		        gbc_progressBarAbsolutoGrafico.insets = new Insets(0,10,0,10);
		        gbc_progressBarAbsolutoGrafico.gridx = 1;
		        gbc_progressBarAbsolutoGrafico.gridy = 1;
		        gbc_progressBarAbsolutoGrafico.gridheight = 6;
		        gbc_progressBarAbsolutoGrafico.weightx = 60;
		        gbc_progressBarAbsolutoGrafico.fill = GridBagConstraints.BOTH;
		        panelNivelAbsoluto.add(progressBarAbsolutoGrafico, gbc_progressBarAbsolutoGrafico);
		        
		        /* SUB PANEL NIVEL ABSOLUTO - REFERENCIAS */
		        
		        JPanel panelReferencia = new JPanel();
		        panelReferencia.setBorder(BorderFactory.createTitledBorder(eBorder, "Referencia"));
			    GridBagConstraints gbc_panelReferencia = new GridBagConstraints();
			    gbc_panelReferencia.fill = GridBagConstraints.BOTH;
			    gbc_panelReferencia.anchor = GridBagConstraints.NORTHWEST;
			    gbc_panelReferencia.gridx = 2;
			    gbc_panelReferencia.gridy = 1;
			    gbc_panelReferencia.gridheight = 6;
			    gbc_panelReferencia.weightx = 40;
			    panelReferencia.setLayout(new GridBagLayout());
			    panelNivelAbsoluto.add(panelReferencia, gbc_panelReferencia); 
			    
				    JLabel lblReferenciaVacio = new JLabel("Vacio");
			        GridBagConstraints gbc_lblReferenciaVacio = new GridBagConstraints();
			        gbc_lblReferenciaVacio.insets = new Insets(0, 0, 5, 0);
			        gbc_lblReferenciaVacio.gridx = 0;
			        gbc_lblReferenciaVacio.gridy = 0;
			        panelReferencia.add(lblReferenciaVacio, gbc_lblReferenciaVacio);
			        
			        JLabel lblReferenciaVacioGrafico = new JLabel("------");
			        lblReferenciaVacioGrafico.setOpaque(true);
			        lblReferenciaVacioGrafico.setBackground(Color.LIGHT_GRAY);
			        GridBagConstraints gbc_lblReferenciaVacioGrafico = new GridBagConstraints();
			        gbc_lblReferenciaVacioGrafico.insets = new Insets(0, 0, 5, 0);
			        gbc_lblReferenciaVacioGrafico.gridx = 0;
			        gbc_lblReferenciaVacioGrafico.gridy = 1;
			        panelReferencia.add(lblReferenciaVacioGrafico, gbc_lblReferenciaVacioGrafico);
			        
			        JLabel lblReferenciaLleno = new JLabel("Lleno");
			        GridBagConstraints gbc_lblReferenciaLleno = new GridBagConstraints();
			        gbc_lblReferenciaLleno.insets = new Insets(0, 0, 5, 0);
			        gbc_lblReferenciaLleno.gridx = 0;
			        gbc_lblReferenciaLleno.gridy = 2;
			        panelReferencia.add(lblReferenciaLleno, gbc_lblReferenciaLleno);
			        
			        JLabel lblReferenciaLLenoGrafico = new JLabel("------");
			        lblReferenciaLLenoGrafico.setOpaque(true);
			        lblReferenciaLLenoGrafico.setBackground(COLOR_AGUA);
			        GridBagConstraints gbc_lblReferenciaLlenoGrafico = new GridBagConstraints();
			        gbc_lblReferenciaLlenoGrafico.insets = new Insets(0, 0, 5, 0);
			        gbc_lblReferenciaLlenoGrafico.gridx = 0;
			        gbc_lblReferenciaLlenoGrafico.gridy = 3;
			        panelReferencia.add(lblReferenciaLLenoGrafico, gbc_lblReferenciaLlenoGrafico);
			        
			        JLabel lblReferenciaLimiteExcedido = new JLabel("Límite Excedido");
			        GridBagConstraints gbc_lblReferenciaLimiteExcedido = new GridBagConstraints();
			        gbc_lblReferenciaLimiteExcedido.insets = new Insets(0, 0, 5, 0);
			        gbc_lblReferenciaLimiteExcedido.gridx = 0;
			        gbc_lblReferenciaLimiteExcedido.gridy = 4;
			        panelReferencia.add(lblReferenciaLimiteExcedido, gbc_lblReferenciaLimiteExcedido);
			        
			        JLabel lblReferenciaLimiteExcedidoGrafico = new JLabel("------");
			        lblReferenciaLimiteExcedidoGrafico.setOpaque(true);
			        lblReferenciaLimiteExcedidoGrafico.setBackground(Color.RED);
			        GridBagConstraints gbc_lblReferenciaLimiteExcedidoGrafico = new GridBagConstraints();
			        gbc_lblReferenciaLimiteExcedidoGrafico.insets = new Insets(0, 0, 5, 0);
			        gbc_lblReferenciaLimiteExcedidoGrafico.gridx = 0;
			        gbc_lblReferenciaLimiteExcedidoGrafico.gridy = 5;
			        panelReferencia.add(lblReferenciaLimiteExcedidoGrafico, gbc_lblReferenciaLimiteExcedidoGrafico);
        
        /* SUB PANEL RELES */
		        
	        JPanel panelReles = new JPanel();
		    GridBagConstraints gbc_panelReles = new GridBagConstraints();
		    gbc_panelReles.fill = GridBagConstraints.BOTH;
		    gbc_panelReles.anchor = GridBagConstraints.NORTHWEST;
		    gbc_panelReles.insets = new Insets(20,0,0,0);
		    gbc_panelReles.gridx = 0;
		    gbc_panelReles.gridy = 1;
		    gbc_panelReles.gridwidth = 2;
		    gbc_panelReles.weightx = 100;
		    panelReles.setLayout(new GridBagLayout());
	        panelInfo.add(panelReles, gbc_panelReles);
	        
	        /* SUB PANEL RELES - RELE 1 */ 
	        
		        JPanel panelRele1 = new JPanel();
		        GridBagConstraints gbc_panelRele1 = new GridBagConstraints();
		        gbc_panelRele1.fill = GridBagConstraints.BOTH;
		        gbc_panelRele1.anchor = GridBagConstraints.NORTHWEST;
		        gbc_panelRele1.gridx = 0;
		        gbc_panelRele1.gridy = 0;
		        gbc_panelRele1.weightx = 50;
			    panelRele1.setLayout(new GridBagLayout());
			    panelReles.add(panelRele1, gbc_panelRele1);
			    
				    JLabel lblNivelRele1 = new JLabel("Estado Bomba 1");
			        GridBagConstraints gbc_lblNivelRele1 = new GridBagConstraints();
			        gbc_lblNivelRele1.insets = new Insets(0, 0, 5, 0);
			        gbc_lblNivelRele1.gridwidth = 2;
			        gbc_lblNivelRele1.gridx = 0;
			        gbc_lblNivelRele1.gridy = 0;
			        panelRele1.add(lblNivelRele1, gbc_lblNivelRele1);
			        
			        JLabel lblNivelRele1Max = new JLabel("Max");
			        GridBagConstraints gbc_lblNivelRele1Max = new GridBagConstraints();
			        gbc_lblNivelRele1Max.insets = new Insets(0, 20, 5, 20);
			        gbc_lblNivelRele1Max.gridx = 0;
			        gbc_lblNivelRele1Max.gridy = 1;
			        panelRele1.add(lblNivelRele1Max, gbc_lblNivelRele1Max);
			        
			        textFieldNivelRele1Max = new JTextField();
			        textFieldNivelRele1Max.setEditable(false);
			        GridBagConstraints gbc_textFieldNivelRele1Max = new GridBagConstraints();
			        gbc_textFieldNivelRele1Max.insets = new Insets(0,0,5,0);
			        gbc_textFieldNivelRele1Max.gridx = 0;
			        gbc_textFieldNivelRele1Max.gridy = 2;
			        gbc_textFieldNivelRele1Max.fill = GridBagConstraints.HORIZONTAL;
			        gbc_textFieldNivelRele1Max.insets = new Insets(0, 20, 5, 20);
			        panelRele1.add(textFieldNivelRele1Max, gbc_textFieldNivelRele1Max);
			        
			        JLabel lblNivelRele1Min = new JLabel("Min"); 	
			        GridBagConstraints gbc_lblNivelRele1Min = new GridBagConstraints();
			        gbc_lblNivelRele1Min.insets = new Insets(0, 0, 5, 0);
			        gbc_lblNivelRele1Min.gridx = 0;
			        gbc_lblNivelRele1Min.gridy = 3;
			        panelRele1.add(lblNivelRele1Min, gbc_lblNivelRele1Min);
			        
			        textFieldNivelRele1Min = new JTextField();
			        textFieldNivelRele1Min.setEditable(false);
			        GridBagConstraints gbc_textFieldNivelRele1Min = new GridBagConstraints();
			        gbc_textFieldNivelRele1Min.insets = new Insets(0,0,5,0);
			        gbc_textFieldNivelRele1Min.gridx = 0;
			        gbc_textFieldNivelRele1Min.gridy = 4;
			        gbc_textFieldNivelRele1Min.fill = GridBagConstraints.HORIZONTAL;
			        gbc_textFieldNivelRele1Min.weightx = 40;
			        gbc_textFieldNivelRele1Min.insets = new Insets(0, 20, 5, 20);
			        panelRele1.add(textFieldNivelRele1Min, gbc_textFieldNivelRele1Min);
			        
			        lblNivelRele1Grafico = new JLabel("");
			        lblNivelRele1Grafico.setBorder(BorderFactory.createLineBorder(Color.black));
			        lblNivelRele1Grafico.setOpaque(true);
			        GridBagConstraints gbc_lblNivelRele1Grafico = new GridBagConstraints();
			        gbc_lblNivelRele1Grafico.insets = new Insets(0, 0, 5, 0);
			        gbc_lblNivelRele1Grafico.fill = GridBagConstraints.BOTH;
			        gbc_lblNivelRele1Grafico.gridx = 1;
			        gbc_lblNivelRele1Grafico.gridy = 1;
			        gbc_lblNivelRele1Grafico.gridheight = 4;
			        gbc_lblNivelRele1Grafico.weightx = 60;
			        gbc_lblNivelRele1Grafico.insets = new Insets(0, 20, 5, 20);
			        panelRele1.add(lblNivelRele1Grafico, gbc_lblNivelRele1Grafico);
			    
			    
	        /* SUB PANEL RELES - RELE 2 */ 
		        
		        JPanel panelRele2 = new JPanel();
		        GridBagConstraints gbc_panelRele2 = new GridBagConstraints();
		        gbc_panelRele2.fill = GridBagConstraints.BOTH;
		        gbc_panelRele2.anchor = GridBagConstraints.NORTHWEST;
		        gbc_panelRele2.gridx = 1;
		        gbc_panelRele2.gridy = 0;
		        gbc_panelRele2.weightx = 50;
			    panelRele2.setLayout(new GridBagLayout());
			    panelReles.add(panelRele2, gbc_panelRele2);
			    
				    JLabel lblNivelRele2 = new JLabel("Estado Bomba 2");
			        GridBagConstraints gbc_lblNivelRele2 = new GridBagConstraints();
			        gbc_lblNivelRele2.insets = new Insets(0, 0, 5, 0);
			        gbc_lblNivelRele2.gridwidth = 2;
			        gbc_lblNivelRele2.gridx = 0;
			        gbc_lblNivelRele2.gridy = 0;
			        panelRele2.add(lblNivelRele2, gbc_lblNivelRele2);
			        
			        JLabel lblNivelRele2Max = new JLabel("Max");
			        GridBagConstraints gbc_lblNivelRele2Max = new GridBagConstraints();
			        gbc_lblNivelRele2Max.insets = new Insets(0, 20, 5, 20);
			        gbc_lblNivelRele2Max.gridx = 0;
			        gbc_lblNivelRele2Max.gridy = 1;
			        panelRele2.add(lblNivelRele2Max, gbc_lblNivelRele2Max);
			        
			        textFieldNivelRele2Max = new JTextField();
			        textFieldNivelRele2Max.setEditable(false);
			        GridBagConstraints gbc_textFieldNivelRele2Max = new GridBagConstraints();
			        gbc_textFieldNivelRele2Max.insets = new Insets(0,0,5,0);
			        gbc_textFieldNivelRele2Max.gridx = 0;
			        gbc_textFieldNivelRele2Max.gridy = 2;
			        gbc_textFieldNivelRele2Max.fill = GridBagConstraints.HORIZONTAL;
			        gbc_textFieldNivelRele2Max.insets = new Insets(0, 20, 5, 20);
			        panelRele2.add(textFieldNivelRele2Max, gbc_textFieldNivelRele1Max);
			        
			        JLabel lblNivelRele2Min = new JLabel("Min");
			        GridBagConstraints gbc_lblNivelRele2Min = new GridBagConstraints();
			        gbc_lblNivelRele2Min.insets = new Insets(0, 0, 5, 0);
			        gbc_lblNivelRele2Min.gridx = 0;
			        gbc_lblNivelRele2Min.gridy = 3;
			        panelRele2.add(lblNivelRele2Min, gbc_lblNivelRele2Min);
			        
			        textFieldNivelRele2Min = new JTextField();
			        textFieldNivelRele2Min.setEditable(false);
			        GridBagConstraints gbc_textFieldNivelRele2Min = new GridBagConstraints();
			        gbc_textFieldNivelRele2Min.insets = new Insets(0,0,5,0);
			        gbc_textFieldNivelRele2Min.gridx = 0;
			        gbc_textFieldNivelRele2Min.gridy = 4;
			        gbc_textFieldNivelRele2Min.fill = GridBagConstraints.HORIZONTAL;
			        gbc_textFieldNivelRele2Min.insets = new Insets(0, 20, 5, 20);
			        gbc_textFieldNivelRele2Min.weightx = 40;
			        panelRele2.add(textFieldNivelRele2Min, gbc_textFieldNivelRele2Min);
			        
			        lblNivelRele2Grafico = new JLabel("");
			        lblNivelRele2Grafico.setBorder(BorderFactory.createLineBorder(Color.black));
			        lblNivelRele2Grafico.setOpaque(true);
			        GridBagConstraints gbc_lblNivelRele2Grafico = new GridBagConstraints();
			        gbc_lblNivelRele2Grafico.insets = new Insets(0, 20, 5, 20);
			        gbc_lblNivelRele2Grafico.fill = GridBagConstraints.BOTH;
			        gbc_lblNivelRele2Grafico.gridx = 1;
			        gbc_lblNivelRele2Grafico.gridy = 1;
			        gbc_lblNivelRele2Grafico.gridheight = 4;
			        gbc_lblNivelRele2Grafico.weightx = 60;
			        panelRele2.add(lblNivelRele2Grafico, gbc_lblNivelRele2Grafico);
			    	
	        

        /* PANEL DATOS */

        JPanel panelDatos = new JPanel();
        panelDatos.setBorder(BorderFactory.createTitledBorder(eBorder, "Datos de Comunicación"));
        GridBagConstraints gbc_panelDatos = new GridBagConstraints();
        gbc_panelDatos.fill = GridBagConstraints.BOTH;
        gbc_panelDatos.anchor = GridBagConstraints.NORTHWEST;
        gbc_panelDatos.gridx = 1;
        gbc_panelDatos.gridy = 0;
        gbc_panelDatos.gridwidth = 1;
        gbc_panelDatos.gridheight = 2;
        gbc_panelDatos.weightx = 80;
        gbc_panelDatos.weighty = 80;
        gbc_panelDatos.insets = new Insets(2, 2, 5, 2);
        getContentPane().add(panelDatos, gbc_panelDatos); // add component to the ContentPane
        panelDatos.setLayout(new BorderLayout(0, 0));
        
        table = new JTable();
        table.setFillsViewportHeight(true);
        scrollPane = new JScrollPane(table);
        
        panelDatos.add(scrollPane, BorderLayout.CENTER);
        
	        JPanel panelBotonesDatos = new JPanel();
	        panelDatos.add(panelBotonesDatos, BorderLayout.SOUTH);
	        
		        JButton btnBorrarLista = new JButton("Borrar Lista");
		        btnBorrarLista.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						controlador.borrarLista(conexionSeleccionada);
						
					}
		        	
		        });
		        panelBotonesDatos.add(btnBorrarLista);
		        
		        JButton btnExportar = new JButton("Exportar");
		        btnExportar.addActionListener(new ActionListener() {
		
					@Override
					public void actionPerformed(ActionEvent arg0) {
						JFileChooser c = new JFileChooser();
						int rVal = c.showSaveDialog(null);
						if (rVal == JFileChooser.APPROVE_OPTION) {
					        controlador.exportar(conexionSeleccionada, c.getCurrentDirectory().toString()+"/"+c.getSelectedFile().getName());
					      }
					}
		        	
		        });
		        panelBotonesDatos.add(btnExportar);
        
        /* PANEL GRAFICO */
	        
	    panelContenedorGrafico = new JPanel();
	    
	    panelContenedorGrafico.setBorder(BorderFactory.createTitledBorder(eBorder, "Gráfico de Consumo"));
        GridBagConstraints gbc_panelContenedorGrafico = new GridBagConstraints();
        gbc_panelContenedorGrafico.fill = GridBagConstraints.BOTH;
        gbc_panelContenedorGrafico.anchor = GridBagConstraints.NORTHWEST;
        gbc_panelContenedorGrafico.gridx = 1;
        gbc_panelContenedorGrafico.gridy = 2;
        gbc_panelContenedorGrafico.gridwidth = 1;
        gbc_panelContenedorGrafico.gridheight = 1;
        gbc_panelContenedorGrafico.weightx = 80;
        gbc_panelContenedorGrafico.weighty = 40;
        gbc_panelContenedorGrafico.insets = new Insets(2, 2, 5, 2);
        getContentPane().add(panelContenedorGrafico, gbc_panelContenedorGrafico); // add component to the ContentPane
        panelContenedorGrafico.setLayout(new BorderLayout(0, 0));
	        
	        panelGrafico = new JPanel();
	        panelContenedorGrafico.add(panelGrafico, BorderLayout.CENTER);
	        
	        JPanel panelBotonesGrafico = new JPanel();
	        panelContenedorGrafico.add(panelBotonesGrafico, BorderLayout.SOUTH);
	        
		        JButton botonGraficoMes = new JButton("Mensual");
		        botonGraficoMes.addActionListener(new ActionListener() {
	
					@Override
					public void actionPerformed(ActionEvent arg0) {
						controlador.graficarMes(listaConexiones.getSelectedValue());
						
					}
	        		
	        	});
		        panelBotonesGrafico.add(botonGraficoMes);
	        
	        	JButton botonGraficoSemana = new JButton("Semanal");
	        	botonGraficoSemana.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						controlador.graficarSemana(listaConexiones.getSelectedValue());
						
					}
	        		
	        	});
	        	panelBotonesGrafico.add(botonGraficoSemana);
	        	
	        	JButton botonGraficoDia = new JButton("Diario");
	        	botonGraficoDia.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						controlador.graficarDia(conexionSeleccionada);
						
					}
	        		
	        	});
	        	panelBotonesGrafico.add(botonGraficoDia);
	        	
	        	JButton botonGraficoHora = new JButton("Última hora");
	        	botonGraficoHora.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						controlador.graficarHora(conexionSeleccionada);
						
					}
	        		
	        	});
	        	panelBotonesGrafico.add(botonGraficoHora);
        
	    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    	this.addWindowListener(new WindowAdapter(){
    	                public void windowClosing(WindowEvent e){
    	                	int reply = JOptionPane.showConfirmDialog(null, "¿Seguro que desea cerrar la aplicacion?", "Cerrar aplicacion", JOptionPane.YES_NO_OPTION);
    	                	if (reply == JOptionPane.YES_OPTION) {
    	                    	System.exit(0);
    	                    }
    	                        
    	                }
    	                
    	                public void windowIconified(WindowEvent e)
    	                {
    	                    setVisible(false);
    	                }
    	                
    	                
    	            });

    	
	        	
        pack();
        setVisible(true); // important
        
        controlador = new Controlador(this, conexionSeleccionada);
        
        listaConexiones.setCellRenderer(new ListCellRenderer<String>() {

			@Override
			public Component getListCellRendererComponent(JList<? extends String> list, String value, int index,
					boolean isSelected, boolean cellHasFocus) {
				JLabel item = new JLabel(value);
				
				item.setOpaque(true);
				
				if(controlador.estaConectado(value)) {
					item.setForeground(COLOR_VERDE);
				}
				if(isSelected) {
					item.setBackground(Color.LIGHT_GRAY);
				}
				return item;
			}
        	
        });
        SystemTray tray = SystemTray.getSystemTray();
        Image image = new ImageIcon(getClass().getClassLoader().getResource("res/img/logo.png")).getImage();
        setIconImage(image);

        PopupMenu menu = new PopupMenu();

        MenuItem messageItem = new MenuItem("About");
        messageItem.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "MedyCon NUSV2.0");
          }
        });
        menu.add(messageItem);

        MenuItem closeItem = new MenuItem("Cerrar");
        closeItem.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
        	  int reply = JOptionPane.showConfirmDialog(null, "¿Seguro que desea cerrar la aplicacion?", "Cerrar aplicacion", JOptionPane.YES_NO_OPTION);
	          if (reply == JOptionPane.YES_OPTION) {
	        	  System.exit(0);
	          }
          }
        });
        menu.add(closeItem);
        TrayIcon icon = new TrayIcon(image, "MedyCon", menu);
        icon.setImageAutoSize(true);
        icon.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(true);
				
			}
        	
        });

        try {
			tray.add(icon);
		} catch (AWTException e1) {
			e1.printStackTrace();
		}
    }
    
    public static void main(String[] args) {
    	
        javax.swing.SwingUtilities.invokeLater(new Runnable() { // important
        	
            @Override
            public void run() {
                Interfaz gui = new Interfaz();
            }
        });
    }
    
    public static boolean isValidIP(String ipAddr){
        Pattern ptn = Pattern.compile("^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$");
        Matcher mtch = ptn.matcher(ipAddr);
        return mtch.find();
    }

    

	public void mostrarMensajeError(String msg) {
		JOptionPane.showMessageDialog(null,msg, "Error", JOptionPane.ERROR_MESSAGE);
		
	}
	
	public void mostrarMensajeInfo(String msg) {
		JOptionPane.showMessageDialog(null,msg, "Informacion", JOptionPane.INFORMATION_MESSAGE);
		
	}
	
	
	
	@Override
	public void actualizarDatosTabla(String nombre, String[][] datos) {
		System.out.println(nombre == null);
		System.out.println(conexionSeleccionada == null);
		if(conexionSeleccionada != null && nombre.equals(conexionSeleccionada)) {
			tableModel = new DefaultTableModel(datos, AdminTabla.getNombresColumnas());
			table.setModel(tableModel);
		}
	}

	@Override
	public void aggregarDatoTabla(String[] dato) {
		tableModel.addRow(dato);
		
	}

	@Override
	public void actualizarInfo(Map<String, String> datos) {
		
		textFieldNivelAbsolutoMax.setText(datos.get("NivelAbsolutoMax"));
	    textFieldNivelAbsolutoMin.setText(datos.get("NivelAbsolutoMin"));
	    
	    int nivelAbsolutoMin = Integer.parseInt(datos.get("NivelAbsolutoMin"));
	    int altura = Integer.parseInt(datos.get("Altura"));
	    
	    progressBarAbsolutoGrafico.setMaximum(nivelAbsolutoMin);
	    progressBarAbsolutoGrafico.setValue(altura);
	    
	    if(altura > nivelAbsolutoMin) {
	    	progressBarAbsolutoGrafico.setForeground(Color.red);
	    }
	    else {
	    	progressBarAbsolutoGrafico.setForeground(COLOR_AGUA);
	    }
	    
	    mapeoNiveles.put("Porcentaje", "% "+datos.get("Porcentaje"));
	    mapeoNiveles.put("Altura", datos.get("Altura")+" cm");
	    
	    lblNivel.setText(mapeoNiveles.get(comboBoxTipoNivel.getSelectedItem()));
	    
	    
	    textFieldNivelRele1Max.setText(datos.get("Rele1Max"));
	    textFieldNivelRele1Min.setText(datos.get("Rele1Min"));
	    textFieldNivelRele2Max.setText(datos.get("Rele2Max"));
	    textFieldNivelRele2Min.setText(datos.get("Rele2Min"));
	    String rele1 = datos.get("Rele1");
	    if(rele1.equals("0")) {
	    	lblNivelRele1Grafico.setText("OFF");
	    	lblNivelRele1Grafico.setBackground(COLOR_ROJO);
	    }
	    else {
	    	lblNivelRele1Grafico.setText("ON");
	    	lblNivelRele1Grafico.setBackground(COLOR_VERDE);
	    }
	    
	    String rele2 = datos.get("Rele2");
	    if(rele2.equals("0")) {
	    	lblNivelRele2Grafico.setText("OFF");
	    	lblNivelRele2Grafico.setBackground(COLOR_ROJO);
	    }
	    else {
	    	lblNivelRele2Grafico.setText("ON");
	    	lblNivelRele2Grafico.setBackground(COLOR_VERDE);
	    }
	}

	@Override
	public void actualizarGrafico(XYChart chart) {
		panelContenedorGrafico.remove(panelGrafico);
		panelGrafico = new XChartPanel(chart);
		panelContenedorGrafico.add(panelGrafico, BorderLayout.CENTER);
		panelContenedorGrafico.validate();
		panelContenedorGrafico.repaint();
	}

	public void eliminarConexion(String nombreConexion) {
		((DefaultListModel<String>) listaConexiones.getModel()).removeElement(nombreConexion);
		
	}
	
	
}