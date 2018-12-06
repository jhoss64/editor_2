package editor_2;

import java.awt.BorderLayout;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.Element;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;

public class FontAndSizeAction extends StyledEditorKit.StyledTextAction {

	  private static final long serialVersionUID = 584531387732416339L;

	  private String family;

	  private float fontSize;

	  JDialog formatText;

	  private boolean accept = false;

	  JComboBox<String> fontFamilyChooser;

	  JComboBox<Integer> fontSizeChooser;

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

	    fontFamilyChooser = new JComboBox<String>();
	    for (int i = 0; i < fontNames.length; i++) {
	      fontFamilyChooser.addItem(fontNames[i]);
	    }
	    fontFamilyChooser.setSelectedItem(family);
	    fontFamilyPanel.add(fontFamilyChooser);
	    choosers.add(fontFamilyPanel);

	    JPanel fontSizePanel = new JPanel();
	    fontSizePanel.add(new JLabel("Size"));
	    fontSizeChooser = new JComboBox<Integer>();
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

