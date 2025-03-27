package command;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import model.Line;
import testings.EditDocumentTest;
import view.Gui;

public class EditDocument extends AbstractAction implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String selection = "";
	private JMenuItem copy;
	private JMenuItem paste;
	private JMenuItem cut;
	private JMenuItem delete;
	
	private ArrayList<Line> listcontent = new ArrayList<Line>();
	private String content = "";
	
	public void setButtons(JMenuItem copy, JMenuItem paste, JMenuItem cut, JMenuItem delete) {
		this.copy = copy;
		this.paste = paste;
		this.cut = cut;
		this.delete = delete;
	}
	
	public void setSel(String s) {
		this.selection = s;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		final Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
		if(e.getSource() == copy) {
			StringSelection data = new StringSelection(selection);
			clip.setContents(data, data);
		} else if(e.getSource() == cut) {
			StringSelection data = new StringSelection(selection);
			clip.setContents(data, data);
			Gui gui = Gui.getInstance();
			gui.getJTextArea().replaceRange("", gui.getJTextArea().getSelectionStart(), gui.getJTextArea().getSelectionEnd());
		} else if(e.getSource() == paste) {
			Transferable clipdata = clip.getContents(clip);
			try {
				if(clipdata.isDataFlavorSupported(DataFlavor.stringFlavor)) {
					String s = (String) (clipdata.getTransferData(DataFlavor.stringFlavor));
					Gui gui = Gui.getInstance();
					gui.getJTextArea().replaceSelection(s);
				}
			} catch (Exception e1) {}
		} else if(e.getSource() == delete) {
			Gui gui = Gui.getInstance();
			gui.getJTextArea().replaceRange("", gui.getJTextArea().getSelectionStart(), gui.getJTextArea().getSelectionEnd());
		} else if(e.getSource() == Gui.class && e.getID() == 1 && e.getActionCommand() == "gui") {
			Gui.getInstance().getJTextArea().getDocument().addDocumentListener(new DocumentListener() {
				  public void changedUpdate(DocumentEvent e) {
				    change();
				  }
				  public void removeUpdate(DocumentEvent e) {
					change();
				  }
				  public void insertUpdate(DocumentEvent e) {
					change();
				  }

				  public void change() {				
					  content = Gui.getInstance().getJTextArea().getText();
					  String[] arr1 = content.split("\n");
					  listcontent = new ArrayList<Line>();
					  for(String i : arr1) {
						  String[] arr2 = i.split(" ");
						  ArrayList<String> arr3 = new ArrayList<String>(Arrays.asList(arr2));
						  Line l = new Line();
						  l.setLine(arr3);
						  listcontent.add(l);
					  } try { 
						  Gui.getInstance().getDocument().setLines(listcontent);
						  /*for(Line l :  Gui.getInstance().getDocument().getLines()) {
							  System.out.println(l.getLine());
						  }*/ //uncomment to check document content
					  } catch(Exception e) {}
				  }
			});	
		} else if (e.getSource() == EditDocumentTest.class && e.getID() == 1 && e.getActionCommand() == "test") {
			content = Gui.getInstance().getJTextArea().getText();
			String[] arr1 = content.split("\n");
			listcontent = new ArrayList<Line>();
			for(String i : arr1) {
				String[] arr2 = i.split(" ");
				ArrayList<String> arr3 = new ArrayList<String>(Arrays.asList(arr2));
				Line l = new Line();
				l.setLine(arr3);
				listcontent.add(l);
			} try { 
				Gui.getInstance().getDocument().setLines(listcontent);
				/*for(Line l :  Gui.getInstance().getDocument().getLines()) {
					System.out.println(l.getLine());
				}*/ //uncomment to check document content
			} catch(Exception e1) {}
		}
	}
}
