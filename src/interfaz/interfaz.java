package interfaz;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class interfaz extends JFrame {
	
	private final Color COLOR_AGUA = new Color(90,188,216);

    private static final long serialVersionUID = 1L;
    private JTable table;

    public interfaz() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.rowWeights = new double[]{0.0, 0.0};
        gridBagLayout.columnWeights = new double[]{1.0, 0.0};
        getContentPane().setLayout(gridBagLayout);// set LayoutManager
        setPreferredSize(new Dimension(1280,720));
        JPanel panelConexion = new JPanel();
        Border eBorder = BorderFactory.createEtchedBorder();

        panelConexion.setBorder(BorderFactory.createTitledBorder(eBorder, "Conexion"));
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
        
	        JComboBox comboBoxConexiones = new JComboBox();
	        GridBagConstraints gbc_comboBoxConexiones = new GridBagConstraints();
	        gbc_comboBoxConexiones.anchor = GridBagConstraints.NORTH;
	        gbc_comboBoxConexiones.gridwidth = 3;
	        gbc_comboBoxConexiones.fill = GridBagConstraints.BOTH;
	        gbc_comboBoxConexiones.insets = new Insets(10, 10, 10, 10);
	        gbc_comboBoxConexiones.gridx = 0;
	        gbc_comboBoxConexiones.gridy = 0;
	        panelConexion.add(comboBoxConexiones, gbc_comboBoxConexiones);
	        
	        JButton btnConectar = new JButton("Conectar");
	        GridBagConstraints gbc_btnConectar = new GridBagConstraints();
	        gbc_btnConectar.fill = GridBagConstraints.BOTH;
	        gbc_btnConectar.insets = new Insets(0, 0, 5, 5);
	        gbc_btnConectar.gridx = 0;
	        gbc_btnConectar.gridy = 1;
	        panelConexion.add(btnConectar, gbc_btnConectar);
        
        JButton btnDesconectar = new JButton("Desconectar");
        GridBagConstraints gbc_btnDesconectar = new GridBagConstraints();
        gbc_btnDesconectar.fill = GridBagConstraints.BOTH;
        gbc_btnDesconectar.insets = new Insets(0, 0, 5, 5);
        gbc_btnDesconectar.gridx = 1;
        gbc_btnDesconectar.gridy = 1;
        panelConexion.add(btnDesconectar, gbc_btnDesconectar);
        
        JButton btnNuevaConexion = new JButton("Nueva Conexion");
        GridBagConstraints gbc_btnNuevaConexion = new GridBagConstraints();
        gbc_btnNuevaConexion.insets = new Insets(0, 0, 5, 0);
        gbc_btnNuevaConexion.fill = GridBagConstraints.BOTH;
        gbc_btnNuevaConexion.gridx = 2;
        gbc_btnNuevaConexion.gridy = 1;
        panelConexion.add(btnNuevaConexion, gbc_btnNuevaConexion);
        
        /* PANEL INFO */

        JPanel panelInfo = new JPanel();
        panelInfo.setBorder(BorderFactory.createTitledBorder(eBorder, "Info Nivel"));
        GridBagConstraints gbc_panelInfo = new GridBagConstraints();
        gbc_panelInfo.fill = GridBagConstraints.BOTH;
        gbc_panelInfo.anchor = GridBagConstraints.NORTHWEST;
        gbc_panelInfo.gridx = 0;
        gbc_panelInfo.gridy = 1;
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
		        
		    	String[] valores = {"Porcentaje (%)","Centimetros (cm)"};
		        JComboBox comboBoxTipoNivel = new JComboBox(valores);
		        comboBoxTipoNivel.setSelectedItem(0);
		        GridBagConstraints gbc_comboBoxTipoNivel = new GridBagConstraints();
		        gbc_comboBoxTipoNivel.insets = new Insets(0, 0, 5, 5);
		        gbc_comboBoxTipoNivel.fill = GridBagConstraints.HORIZONTAL;
		        gbc_comboBoxTipoNivel.gridx = 0;
		        gbc_comboBoxTipoNivel.gridy = 1;
		        panelTipoNivel.add(comboBoxTipoNivel, gbc_comboBoxTipoNivel);
		      
		        JLabel lblNivel = new JLabel("0 %");
		        GridBagConstraints gbc_lblNivel = new GridBagConstraints();
		        gbc_lblNivel.insets = new Insets(0, 0, 5, 5);
		        gbc_lblNivel.gridx = 0;
		        gbc_lblNivel.gridy = 2;
		        panelTipoNivel.add(lblNivel, gbc_lblNivel);
		        
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
		        
		        JTextField textFieldNivelAbsolutoMax = new JTextField();
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
		        
		        JTextField textFieldNivelAbsolutoMin = new JTextField();
		        GridBagConstraints gbc_textFieldNivelAbsolutoMin = new GridBagConstraints();
		        gbc_textFieldNivelAbsolutoMin.insets = new Insets(0,0,5,0);
		        gbc_textFieldNivelAbsolutoMin.gridx = 0;
		        gbc_textFieldNivelAbsolutoMin.gridy = 4;
		        gbc_textFieldNivelAbsolutoMin.fill = GridBagConstraints.HORIZONTAL;
		        panelNivelAbsoluto.add(textFieldNivelAbsolutoMin, gbc_textFieldNivelAbsolutoMin);
		        
		        JTextArea textAreaAbsolutoGrafico = new JTextArea("----\n---\n---\n---\n---\n---\n---\n---\n---\n---\n----");
		        textAreaAbsolutoGrafico.setRows(10);
		        textAreaAbsolutoGrafico.setEditable(false);
		        textAreaAbsolutoGrafico.setBackground(Color.LIGHT_GRAY);
		        textAreaAbsolutoGrafico.setBorder(BorderFactory.createLineBorder(Color.black));
		        GridBagConstraints gbc_lblNivelAbsolutoGrafico = new GridBagConstraints();
		        gbc_lblNivelAbsolutoGrafico.insets = new Insets(0,10,0,10);
		        gbc_lblNivelAbsolutoGrafico.gridx = 1;
		        gbc_lblNivelAbsolutoGrafico.gridy = 1;
		        gbc_lblNivelAbsolutoGrafico.gridheight = 6;
		        gbc_lblNivelAbsolutoGrafico.weightx = 60;
		        gbc_lblNivelAbsolutoGrafico.fill = GridBagConstraints.BOTH;
		        panelNivelAbsoluto.add(textAreaAbsolutoGrafico, gbc_lblNivelAbsolutoGrafico);
		        
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
			        
			        JLabel lblReferenciaLimiteExcedido = new JLabel("Limite Excedido");
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
			    
				    JLabel lblNivelRele1 = new JLabel("Nivel Rele 1");
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
			        
			        JTextField textFieldNivelRele1Max = new JTextField();
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
			        
			        JTextField textFieldNivelRele1Min = new JTextField();
			        GridBagConstraints gbc_textFieldNivelRele1Min = new GridBagConstraints();
			        gbc_textFieldNivelRele1Min.insets = new Insets(0,0,5,0);
			        gbc_textFieldNivelRele1Min.gridx = 0;
			        gbc_textFieldNivelRele1Min.gridy = 4;
			        gbc_textFieldNivelRele1Min.fill = GridBagConstraints.HORIZONTAL;
			        gbc_textFieldNivelRele1Min.weightx = 40;
			        gbc_textFieldNivelRele1Min.insets = new Insets(0, 20, 5, 20);
			        panelRele1.add(textFieldNivelRele1Min, gbc_textFieldNivelRele1Min);
			        
			        JLabel lblNivelRele1Grafico = new JLabel("ON");
			        lblNivelRele1Grafico.setBorder(BorderFactory.createLineBorder(Color.black));
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
			    
				    JLabel lblNivelRele2 = new JLabel("Nivel Rele 2");
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
			        
			        JTextField textFieldNivelRele2Max = new JTextField();
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
			        
			        JTextField textFieldNivelRele2Min = new JTextField();
			        GridBagConstraints gbc_textFieldNivelRele2Min = new GridBagConstraints();
			        gbc_textFieldNivelRele2Min.insets = new Insets(0,0,5,0);
			        gbc_textFieldNivelRele2Min.gridx = 0;
			        gbc_textFieldNivelRele2Min.gridy = 4;
			        gbc_textFieldNivelRele2Min.fill = GridBagConstraints.HORIZONTAL;
			        gbc_textFieldNivelRele2Min.insets = new Insets(0, 20, 5, 20);
			        gbc_textFieldNivelRele2Min.weightx = 40;
			        panelRele2.add(textFieldNivelRele2Min, gbc_textFieldNivelRele2Min);
			        
			        JLabel lblNivelRele2Grafico = new JLabel("ON");
			        lblNivelRele2Grafico.setBorder(BorderFactory.createLineBorder(Color.black));
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
        panelDatos.setBorder(BorderFactory.createTitledBorder(eBorder, "Datos de Comuncacion"));
        GridBagConstraints gbc_panelDatos = new GridBagConstraints();
        gbc_panelDatos.fill = GridBagConstraints.BOTH;
        gbc_panelDatos.anchor = GridBagConstraints.NORTHWEST;
        gbc_panelDatos.gridx = 1;
        gbc_panelDatos.gridy = 0;
        gbc_panelDatos.gridwidth = 1;
        gbc_panelDatos.gridheight = 2;
        gbc_panelDatos.weightx = /*gbc.weighty = */ 80;
        gbc_panelDatos.insets = new Insets(2, 2, 5, 2);
        getContentPane().add(panelDatos, gbc_panelDatos); // add component to the ContentPane
        panelDatos.setLayout(new BorderLayout(0, 0));
        
        String[] columnNames = {"First Name",
                "Last Name",
                "Sport",
                "# of Years",
                "Vegetarian"};

		Object[][] data = {
		{"Kathy", "Smith",
		"Snowboarding", new Integer(5), new Boolean(false)},
		{"John", "Doe",
		"Rowing", new Integer(3), new Boolean(true)},
		{"Sue", "Black",
		"Knitting", new Integer(2), new Boolean(false)},
		{"Jane", "White",
		"Speed reading", new Integer(20), new Boolean(true)},
		{"Joe", "Brown",
		"Pool", new Integer(10), new Boolean(false)}
		};
		table = new JTable(data, columnNames);
	    table.setFillsViewportHeight(true);
        
		
        JScrollPane scrollPane = new JScrollPane(table);
        panelDatos.add(scrollPane, BorderLayout.CENTER);
        
       
        
        

        
        JPanel panel = new JPanel();
        panelDatos.add(panel, BorderLayout.SOUTH);
        
        JButton btnBorrarLista = new JButton("Borrar Lista");
        panel.add(btnBorrarLista);
        
        JButton btnExportar = new JButton("Exportar");
        panel.add(btnExportar);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // important
        pack();
        setVisible(true); // important
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() { // important

            @Override
            public void run() {
                interfaz borderPanels = new interfaz();
            }
        });
    }
}