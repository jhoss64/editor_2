package editor_2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.JViewport;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.EditorKit;
import javax.swing.text.StyledDocument;
import javax.swing.text.rtf.RTFEditorKit;
import javax.swing.JPanel;

public class Editor_jtextpane {

	public JFrame frame;
	public int num_pestañas;
	public String nom_variable_scroll, nom_variable_textPane, caption_tab_asignado;
	public JSpinner spinner;
	
	public JTabbedPane tabbedPane;
	public JTextPane textPane, textPane_prueba;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Editor_jtextpane window = new Editor_jtextpane();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public Editor_jtextpane() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		initialize();
	};

	/**
	 * Initialize the contents of the frame.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	private void initialize() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		frame = new JFrame();
		//frame.setBounds(100, 100, 678, 544);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.setSize(678, 544);
		frame.setLocationRelativeTo(null);
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

	    Action boldAction = new BoldAction();
	    boldAction.putValue(Action.NAME, "Negrita");

	    Action italicAction = new ItalicAction();
	    italicAction.putValue(Action.NAME, "Cursiva");

	    Action foregroundAction = new ForegroundAction();
	    foregroundAction.putValue(Action.NAME, "Color");

	    Action formatTextAction = new FontAndSizeAction();
	    formatTextAction.putValue(Action.NAME, "Letra y Tamaño");
			
	    
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setMinimumSize(new java.awt.Dimension(600, 450));
		//tabbedPane.setMaximumSize(JFrame.MAXIMIZED_BOTH);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		tabbedPane.addTab("NuevaPestaña", null, scrollPane, null);
		
		textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JMenuBar menuBar = new JMenuBar();
		panel.add(menuBar, BorderLayout.NORTH);
		
		JMenu mnNewMenu = new JMenu("Archivo");
		mnNewMenu.setForeground(Color.DARK_GRAY);
		mnNewMenu.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		mnNewMenu.setIcon(new ImageIcon(Editor_jtextpane.class.getResource("/editor_2/images/home.gif")));
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Nuevo");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				// funcion declarada abajo
				agregar_tab();				
				
			}
		});
		mntmNewMenuItem.setIcon(new ImageIcon(Editor_jtextpane.class.getResource("/editor_2/images/new.png")));
		mntmNewMenuItem.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmAbrir = new JMenuItem("Abrir");
		mntmAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				funcion_abrir();
				
			}
		});
		mntmAbrir.setIcon(new ImageIcon(Editor_jtextpane.class.getResource("/editor_2/images/open.png")));
		mntmAbrir.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		mnNewMenu.add(mntmAbrir);
		
		JMenuItem mntmGuardar = new JMenuItem("Guardar");
		mntmGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				funcion_guardar();
			}
		});
		mntmGuardar.setIcon(new ImageIcon(Editor_jtextpane.class.getResource("/editor_2/images/save.png")));
		mntmGuardar.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		mnNewMenu.add(mntmGuardar);
		
		JMenuItem mntmGuardarComo = new JMenuItem("Guardar como..");
		mntmGuardarComo.setIcon(new ImageIcon(Editor_jtextpane.class.getResource("/editor_2/images/iconfinder_Save_as_132230.png")));
		mntmGuardarComo.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		mnNewMenu.add(mntmGuardarComo);
		
		JMenu styleMenu = new JMenu();
		styleMenu.setForeground(Color.DARK_GRAY);
		styleMenu.setIcon(new ImageIcon(Editor_jtextpane.class.getResource("/editor_2/images/themes.gif")));
		styleMenu.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		styleMenu.setText("Formatos");
		JMenuItem menuItem = styleMenu.add(boldAction);
		menuItem.setIcon(new ImageIcon(Editor_jtextpane.class.getResource("/editor_2/images/letra_39.png.jpg")));
		menuItem.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		JMenuItem menuItem_1 = styleMenu.add(italicAction);
		menuItem_1.setIcon(new ImageIcon(Editor_jtextpane.class.getResource("/editor_2/images/may\u00FAscula-k-pintada-por-el-cepillo-98445139.jpg")));
		menuItem_1.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		JMenuItem menuItem_2 = styleMenu.add(foregroundAction);
		menuItem_2.setIcon(new ImageIcon(Editor_jtextpane.class.getResource("/editor_2/images/iconfinder_Control_panel_132264.png")));
		menuItem_2.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		JMenuItem menuItem_3 = styleMenu.add(formatTextAction);
		menuItem_3.setIcon(new ImageIcon(Editor_jtextpane.class.getResource("/editor_2/images/font.png")));
		menuItem_3.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		menuBar.add(styleMenu);
		
		JMenu mnCerrar = new JMenu("Cerrar");
		mnCerrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (JOptionPane.showConfirmDialog(frame, "¿Desea realmente salir del sistema?",
		                "Salir del sistema", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
		            System.exit(0);
			}
		});
		mnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(frame, "¿Desea realmente salir del sistema?",
		                "Salir del sistema", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
		            System.exit(0);
			}
		});
		mnCerrar.setIcon(new ImageIcon(Editor_jtextpane.class.getResource("/editor_2/images/exit.gif")));
		mnCerrar.setForeground(Color.DARK_GRAY);
		mnCerrar.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		menuBar.add(mnCerrar);
		
		
		
		JToolBar toolBar = new JToolBar();
		panel.add(toolBar, BorderLayout.SOUTH);
		toolBar.setFloatable(false);
		toolBar.setBounds(0, 26, 662, 28);
		
		JButton btnAadirNuevo = new JButton("");
		btnAadirNuevo.setToolTipText("Nuevo");
		toolBar.add(btnAadirNuevo);
		btnAadirNuevo.setIcon(new ImageIcon(Editor_jtextpane.class.getResource("/editor_2/images/iconfinder_Text_132188.png")));
		
		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					
				funcion_abrir();
			}
		});		
		button.setToolTipText("Abrir");
		button.setIcon(new ImageIcon(Editor_jtextpane.class.getResource("/editor_2/images/open.png")));
		toolBar.add(button);
		
		JButton button_1 = new JButton("");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				funcion_guardar();
				
			}
		});
		button_1.setToolTipText("Guardar");
		button_1.setIcon(new ImageIcon(Editor_jtextpane.class.getResource("/editor_2/images/save.png")));
		toolBar.add(button_1);
		
		
		JButton button_2 = new JButton("");
		button_2.setToolTipText("Guardar como");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		button_2.setIcon(new ImageIcon(Editor_jtextpane.class.getResource("/editor_2/images/iconfinder_Save_as_132230.png")));
		toolBar.add(button_2);
		
		JButton cerrarTab = new JButton("");
		cerrarTab.setToolTipText("Cerrar Pestaña");
		toolBar.add(cerrarTab);
		cerrarTab.setIcon(new ImageIcon(Editor_jtextpane.class.getResource("/editor_2/images/exit.gif")));
		
		cerrarTab.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				tabbedPane.remove(tabbedPane.getSelectedComponent());
				
			}
		});
		btnAadirNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				// funcion declarada abajo
				agregar_tab();
								
			}
		});
	}

	
	
	protected void funcion_guardar() {
		// TODO Auto-generated method stub
		int a = tabbedPane.getSelectedIndex();
		String nombre_textPane = "";
		
		if (a==0) {
			nombre_textPane = "textPane";
		} else {
			nombre_textPane = "textPane"+a;
		}
		
		System.out.println(nombre_textPane);
		
		JScrollPane jscrollpane =(JScrollPane)tabbedPane.getSelectedComponent();
		JViewport viewPort = (JViewport) jscrollpane.getComponent(0);
		JTextPane textPane_prueba = (JTextPane)viewPort.getComponent(0);
				
	 			
		JFileChooser file=new JFileChooser();
		FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.2jav", "2jav");
		file.setFileFilter(filtro);
		
		EditorKit kit = new RTFEditorKit();
		StyledDocument doc = textPane_prueba.getStyledDocument();
		int len = doc.getLength();				// TODO Auto-generated method stub
		textPane_prueba.setEditorKit(kit);
		
		Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);
		Thread guardar = new Thread() {
			public void run() {
				if(file.showSaveDialog(frame) != JFileChooser.APPROVE_OPTION) {
					return;				
				}
				
				File  tosave = file.getSelectedFile();
				tosave = new File(tosave.toString() + ".2jav"); 
				
			try {
				
				OutputStream out = new FileOutputStream(tosave);
				kit.write(out,doc,0,len);
				out.close();
				
								
			} catch (Exception ex) {
				
			}
							
			file.rescanCurrentDirectory();
		
			try {
				InputStream in = new FileInputStream(tosave);
				kit.read(in, doc, 0);
				textPane_prueba.setDocument(doc);
				in.close();
				//tabbedPane.setName(tosave.toString() + ".2jav");;
				
			} catch (Exception ex) {
				
			}
			
			}
		};				
		guardar.start();
		
	}

	protected void funcion_abrir() {
		// TODO Auto-generated method stub

		JFileChooser file =new JFileChooser();
		
		FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.2jav", "2jav");
		file.setFileFilter(filtro);
		
		EditorKit kit = new RTFEditorKit();
		StyledDocument doc1 = textPane.getStyledDocument();
		textPane.setEditorKit(kit);
		
		Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);
					
		Thread abrir = new Thread() {
			public void run() {
				if(file.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION) {
					return;				
				}
				File  reada = file.getSelectedFile();
				
				num_pestañas = tabbedPane.getTabCount();
					    			    
			    nom_variable_scroll = reada.getName();
			    nom_variable_textPane = reada.getName();
			    caption_tab_asignado = reada.getName();
			    
			    JScrollPane nom_variable_scroll = new JScrollPane();
				tabbedPane.add(caption_tab_asignado,  nom_variable_scroll);
				
				JTextPane nom_variable_textPane = new JTextPane();
				nom_variable_scroll.setViewportView(nom_variable_textPane);		
				
				//tabbedPane.setSelectedIndex(num_pestañas);
				
			try {
				InputStream in = new FileInputStream(reada);
				kit.read(in, doc1, 0);
				nom_variable_textPane.setDocument(doc1);
				in.close();
				
			} catch (Exception ex) {
				
			}
		}
		};				
		abrir.start();
		
	}
		
	

	protected void agregar_tab() {
		// TODO Auto-generated method stub

	    num_pestañas = tabbedPane.getTabCount();
	    			    
	    nom_variable_scroll = "scrollPane"+num_pestañas;
	    nom_variable_textPane = "textPane"+num_pestañas;
	    caption_tab_asignado = "NuevaPestaña"+num_pestañas;
	    
	    JScrollPane nom_variable_scroll = new JScrollPane();
		tabbedPane.addTab(caption_tab_asignado , null, nom_variable_scroll, null);
		
		JTextPane nom_variable_textPane = new JTextPane();
		nom_variable_scroll.setViewportView(nom_variable_textPane);		
		
		tabbedPane.setSelectedIndex(num_pestañas);
	}}		

	