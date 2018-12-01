package editor_2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JSpinner;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicTabbedPaneUI.TabbedPaneLayout;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.rtf.RTFEditorKit;
import javax.swing.event.ChangeEvent;
import javax.swing.text.AttributeSet;
import javax.swing.text.EditorKit;
import javax.swing.text.Element;
import javax.swing.text.StyledDocument;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Editor_jtextpane {

	public JFrame frame;
	public int num_pestañas;
	public String nom_variable_scroll, nom_variable_textPane, caption_tab_asignado;
	public JSpinner spinner;

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
		frame.setBounds(100, 100, 678, 544);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
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
		
		////panel.add(menu);
		//panel.setBounds(70, 0, 75, 23);
		//frame.getContentPane().add(panel);
			
	    
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 56, 642, 438);
		frame.getContentPane().add(tabbedPane);
		
		JScrollPane scrollPane = new JScrollPane();
		tabbedPane.addTab("NuevaPestaña", null, scrollPane, null);
		
		JTextPane textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 662, 28);
		frame.getContentPane().add(menuBar);
		
		JMenu mnNewMenu = new JMenu("Archivo");
		mnNewMenu.setForeground(Color.DARK_GRAY);
		mnNewMenu.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		mnNewMenu.setIcon(new ImageIcon(Editor_jtextpane.class.getResource("/editor_2/images/home.gif")));
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Nuevo");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				    num_pestañas = tabbedPane.getTabCount();
				    			    
				    nom_variable_scroll = "scrollPane"+num_pestañas;
				    nom_variable_textPane = "textPane"+num_pestañas;
				    caption_tab_asignado = "NuevaPestaña_"+num_pestañas;
				    
				    JScrollPane nom_variable_scroll = new JScrollPane();
					tabbedPane.addTab(caption_tab_asignado , null, nom_variable_scroll, null);
					
					JTextPane nom_variable_textPane = new JTextPane();
					nom_variable_scroll.setViewportView(nom_variable_textPane);		
					
					tabbedPane.setSelectedIndex(num_pestañas);				
				
			}
		});
		mntmNewMenuItem.setIcon(new ImageIcon(Editor_jtextpane.class.getResource("/editor_2/images/new.png")));
		mntmNewMenuItem.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmAbrir = new JMenuItem("Abrir");
		mntmAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				
				JFileChooser file =new JFileChooser();
				EditorKit kit = new RTFEditorKit();
				StyledDocument doc = textPane.getStyledDocument();
				//textPane.setEditorKit(kit);
				
				Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);
							
				Thread abrir = new Thread() {
					public void run() {
						if(file.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION) {
							return;				
						}
						File  reada = file.getSelectedFile();
						
					try {
						
						InputStream in = new FileInputStream(reada);
						kit.read(in, doc, 0);
						textPane.setDocument(doc);
						in.close();
						
					} catch (Exception ex) {
						
					}
				}
				};				
				abrir.start();
				
			}
		});
		mntmAbrir.setIcon(new ImageIcon(Editor_jtextpane.class.getResource("/editor_2/images/open.png")));
		mntmAbrir.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
		mnNewMenu.add(mntmAbrir);
		
		JMenuItem mntmGuardar = new JMenuItem("Guardar");
		mntmGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser file=new JFileChooser();
				EditorKit kit = new RTFEditorKit();
				StyledDocument doc = textPane.getStyledDocument();
				int len = doc.getLength();				// TODO Auto-generated method stub
				//textPane.setEditorKit(kit);
				
				Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);
				Thread guardar = new Thread() {
					public void run() {
						if(file.showSaveDialog(frame) != JFileChooser.APPROVE_OPTION) {
							return;				
						}
						File  tosave = file.getSelectedFile();
						
					try {
						
						OutputStream out = new FileOutputStream(tosave);
						kit.write(out,doc,0,len);
						out.close();
					} catch (Exception ex) {
						
					}
					file.rescanCurrentDirectory();
				}
				};				
				guardar.start();				
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
		toolBar.setBounds(0, 26, 662, 28);
		frame.getContentPane().add(toolBar);
		
		JButton btnAadirNuevo = new JButton("");
		btnAadirNuevo.setToolTipText("Nuevo");
		toolBar.add(btnAadirNuevo);
		btnAadirNuevo.setIcon(new ImageIcon(Editor_jtextpane.class.getResource("/editor_2/images/iconfinder_Text_132188.png")));
		
		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
								
				JFileChooser file =new JFileChooser();
				EditorKit kit = new RTFEditorKit();
				StyledDocument doc = textPane.getStyledDocument();
				
				//textPane.setEditorKit(kit);
				
				Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);
							
				Thread abrir = new Thread() {
					public void run() {
						if(file.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION) {
							return;				
						}
						File  reada = file.getSelectedFile();
						
						num_pestañas = tabbedPane.getTabCount();
							    			    
					    nom_variable_scroll = "scrollPane"+num_pestañas;
					    nom_variable_textPane = "textPane"+num_pestañas;
					    caption_tab_asignado = reada.getName();
					    
					    JScrollPane nom_variable_scroll = new JScrollPane();
						tabbedPane.addTab(caption_tab_asignado , null, nom_variable_scroll, null);
						
						JTextPane nom_variable_textPane = new JTextPane();
						nom_variable_scroll.setViewportView(nom_variable_textPane);		
						
						tabbedPane.setSelectedIndex(num_pestañas);
						textPane.setText(" ");
					try {
						
						InputStream in = new FileInputStream(reada);
						kit.read(in, doc, 0);
						nom_variable_textPane.setDocument(doc);
						in.close();
						
					} catch (Exception ex) {
						
					}
				}
				};				
				abrir.start();
				
			}
		});
		button.setToolTipText("Abrir");
		button.setIcon(new ImageIcon(Editor_jtextpane.class.getResource("/editor_2/images/open.png")));
		toolBar.add(button);
		
		JButton button_1 = new JButton("");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int a = tabbedPane.getSelectedIndex();
				System.out.println(a);
				
				JFileChooser file=new JFileChooser();
				EditorKit kit = new RTFEditorKit();
				StyledDocument doc = textPane.getStyledDocument();
				int len = doc.getLength();				// TODO Auto-generated method stub
				//textPane.setEditorKit(kit);
				
				Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);
				Thread guardar = new Thread() {
					public void run() {
						if(file.showSaveDialog(frame) != JFileChooser.APPROVE_OPTION) {
							return;				
						}
						File  tosave = file.getSelectedFile();
						
					try {
						
						OutputStream out = new FileOutputStream(tosave);
						kit.write(out,doc,0,len);
						out.close();
					} catch (Exception ex) {
						
					}
					file.rescanCurrentDirectory();
				}
				};				
				guardar.start();
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
		btnAadirNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			    num_pestañas = tabbedPane.getTabCount();
			    			    
			    nom_variable_scroll = "scrollPane"+num_pestañas;
			    nom_variable_textPane = "textPane"+num_pestañas;
			    caption_tab_asignado = "NuevaPestaña_"+num_pestañas;
			    
			    JScrollPane nom_variable_scroll = new JScrollPane();
				tabbedPane.addTab(caption_tab_asignado , null, nom_variable_scroll, null);
				
				JTextPane nom_variable_textPane = new JTextPane();
				nom_variable_scroll.setViewportView(nom_variable_textPane);		
				
				tabbedPane.setSelectedIndex(num_pestañas);				
			}
		});
				
	}
	


class BoldAction extends StyledEditorKit.StyledTextAction {
	  private static final long serialVersionUID = 9174670038684056758L;

	  public BoldAction() {
	    super("font-bold");
	  }

	  public String toString() {
	    return "Bold";
	  }

	  public void actionPerformed(ActionEvent e) {
	    JEditorPane editor = getEditor(e);
	    if (editor != null) {
	      StyledEditorKit kit = getStyledEditorKit(editor);
	      MutableAttributeSet attr = kit.getInputAttributes();
	      boolean bold = (StyleConstants.isBold(attr)) ? false : true;
	      SimpleAttributeSet sas = new SimpleAttributeSet();
	      StyleConstants.setBold(sas, bold);
	      setCharacterAttributes(editor, sas, false);

	    }
	  }
	}

	class ItalicAction extends StyledEditorKit.StyledTextAction {

	  private static final long serialVersionUID = -1428340091100055456L;

	  public ItalicAction() {
	    super("font-italic");
	  }

	  public String toString() {
	    return "Italic";
	  }

	  public void actionPerformed(ActionEvent e) {
	    JEditorPane editor = getEditor(e);
	    if (editor != null) {
	      StyledEditorKit kit = getStyledEditorKit(editor);
	      MutableAttributeSet attr = kit.getInputAttributes();
	      boolean italic = (StyleConstants.isItalic(attr)) ? false : true;
	      SimpleAttributeSet sas = new SimpleAttributeSet();
	      StyleConstants.setItalic(sas, italic);
	      setCharacterAttributes(editor, sas, false);
	    }
	  }
	}
	class ForegroundAction extends StyledEditorKit.StyledTextAction {

	  private static final long serialVersionUID = 6384632651737400352L;

	  JColorChooser colorChooser = new JColorChooser();

	  JDialog dialog = new JDialog();

	  boolean noChange = false;

	  boolean cancelled = false;

	  public ForegroundAction() {
	    super("foreground");

	  }

	  public void actionPerformed(ActionEvent e) {
	    JTextPane editor = (JTextPane) getEditor(e);

	    if (editor == null) {
	      JOptionPane.showMessageDialog(null,
	          "Selecciona de cual editor deseas colorear", "Error",
	          JOptionPane.ERROR_MESSAGE);
	      return;
	    }
	    int p0 = editor.getSelectionStart();
	    StyledDocument doc = getStyledDocument(editor);
	    Element paragraph = doc.getCharacterElement(p0);
	    AttributeSet as = paragraph.getAttributes();
	    fg = StyleConstants.getForeground(as);
	    if (fg == null) {
	      fg = Color.BLACK;
	    }
	    colorChooser.setColor(fg);

	    JButton accept = new JButton("OK");
	    accept.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent ae) {
	        fg = colorChooser.getColor();
	        dialog.dispose();
	      }
	    });

	    JButton cancel = new JButton("Cancel");
	    cancel.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent ae) {
	        cancelled = true;
	        dialog.dispose();
	      }
	    });

	    JButton none = new JButton("None");
	    none.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent ae) {
	        noChange = true;
	        dialog.dispose();
	      }
	    });

	    JPanel buttons = new JPanel();
	    buttons.add(accept);
	    buttons.add(none);
	    buttons.add(cancel);

	    dialog.getContentPane().setLayout(new BorderLayout());
	    dialog.getContentPane().add(colorChooser, BorderLayout.CENTER);
	    dialog.getContentPane().add(buttons, BorderLayout.SOUTH);
	    dialog.setModal(true);
	    dialog.pack();
	    dialog.setVisible(true);

	    if (!cancelled) {

	      MutableAttributeSet attr = null;
	      if (editor != null) {
	        if (fg != null && !noChange) {
	          attr = new SimpleAttributeSet();
	          StyleConstants.setForeground(attr, fg);
	          setCharacterAttributes(editor, attr, false);
	        }
	      }
	    }// end if color != null
	    noChange = false;
	    cancelled = false;
	  }

	  private Color fg;
	}

	class FontAndSizeAction extends StyledEditorKit.StyledTextAction {

	  private static final long serialVersionUID = 584531387732416339L;

	  private String family;

	  private float fontSize;

	  JDialog formatText;

	  private boolean accept = false;

	  JComboBox fontFamilyChooser;

	  JComboBox fontSizeChooser;

	  public FontAndSizeAction() {
	    super("Font and Size");
	  }

	  public String toString() {
	    return "Font and Size";
	  }

	  public void actionPerformed(ActionEvent e) {
	    JTextPane editor = (JTextPane) getEditor(e);
	    int p0 = editor.getSelectionStart();
	    StyledDocument doc = getStyledDocument(editor);
	    Element paragraph = doc.getCharacterElement(p0);
	    AttributeSet as = paragraph.getAttributes();

	    family = StyleConstants.getFontFamily(as);
	    fontSize = StyleConstants.getFontSize(as);

	    formatText = new JDialog(new JFrame(), "Font and Size", true);
	    formatText.getContentPane().setLayout(new BorderLayout());
	    formatText.setLocationRelativeTo(editor);

	    JPanel choosers = new JPanel();
	    choosers.setLayout(new GridLayout(2, 1));

	    JPanel fontFamilyPanel = new JPanel();
	    fontFamilyPanel.add(new JLabel("Font"));

	    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    String[] fontNames = ge.getAvailableFontFamilyNames();

	    fontFamilyChooser = new JComboBox();
	    for (int i = 0; i < fontNames.length; i++) {
	      fontFamilyChooser.addItem(fontNames[i]);
	    }
	    fontFamilyChooser.setSelectedItem(family);
	    fontFamilyPanel.add(fontFamilyChooser);
	    choosers.add(fontFamilyPanel);

	    JPanel fontSizePanel = new JPanel();
	    fontSizePanel.add(new JLabel("Size"));
	    fontSizeChooser = new JComboBox();
	    fontSizeChooser.setEditable(true);
	    fontSizeChooser.addItem(new Integer(4));
	    fontSizeChooser.addItem(new Integer(6));
	    fontSizeChooser.addItem(new Integer(8));
	    fontSizeChooser.addItem(new Integer(10));
	    fontSizeChooser.addItem(new Integer(12));
	    fontSizeChooser.addItem(new Integer(14));
	    fontSizeChooser.addItem(new Integer(16));
	    fontSizeChooser.addItem(new Integer(18));
	    fontSizeChooser.addItem(new Integer(20));
	    fontSizeChooser.addItem(new Integer(24));
	    fontSizeChooser.addItem(new Integer(30));
	    fontSizeChooser.addItem(new Integer(36));fontSizeChooser.addItem(new Integer(42));fontSizeChooser.addItem(new Integer(70));
	    fontSizeChooser.setSelectedItem(new Float(fontSize));
	    fontSizePanel.add(fontSizeChooser);
	    choosers.add(fontSizePanel);

	    JButton ok = new JButton("OK");
	    ok.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent ae) {
	        accept = true;
	        formatText.dispose();
	        family = (String) fontFamilyChooser.getSelectedItem();
	        fontSize = Float.parseFloat(fontSizeChooser.getSelectedItem().toString());
	      }
	    });

	    JButton cancel = new JButton("Cancel");
	    cancel.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent ae) {
	        formatText.dispose();
	      }
	    });

	    JPanel buttons = new JPanel();
	    buttons.add(ok);
	    buttons.add(cancel);
	    formatText.getContentPane().add(choosers, BorderLayout.CENTER);
	    formatText.getContentPane().add(buttons, BorderLayout.SOUTH);
	    formatText.pack();
	    formatText.setVisible(true);

	    
	    // este codigo setea para que se convierta el jtextpane 
	    MutableAttributeSet attr = null;
	    if (editor != null && accept) {
	      attr = new SimpleAttributeSet();
	      StyleConstants.setFontFamily(attr, family);
	      StyleConstants.setFontSize(attr, (int) fontSize);
	      setCharacterAttributes(editor, attr, false);
	    }
		
	  }
	
}
	}





