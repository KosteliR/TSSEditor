package command;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.Timer;

import model.FileReader;
import testings.SaveDocumentTest;
import view.Gui;

public class SaveDocument extends AbstractAction implements ActionListener {

	private static final long serialVersionUID = 1L; //not used
	
	private String p = "";
	private String content = "";
	private int TIMER = 0;

	public SaveDocument() {
		this.p = "";
		this.content = "";
		this.TIMER = 0;
	}
	
	public SaveDocument(String p, String content, int TIMER) {
		this.p = p;
		this.content = content;
		this.TIMER = TIMER;
	}
	
	public void setContent(String s) {
		this.content = s;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == ReplayManager.class && e.getID() == 1 && e.getActionCommand() == "dummy") {
			setEnabled(false);
			new Timer(TIMER, new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
					if(ReplayManager.getMyTurn() == true) {
						((Timer) e.getSource()).stop();
						SaveDocument.this.setEnabled(true);
					} else {
						ReplayManager.setMyTurn(true);
						//System.out.println("Save Document clone... ");	
						try {
							SaveDocument.this.p += ".txt";
				            PrintWriter wr = new PrintWriter(SaveDocument.this.p);
				            wr.println(SaveDocument.this.content);
				            wr.close();
				            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
							Date date = new Date();
							Gui gui = Gui.getInstance();
							gui.getDocument().setLastModified(dateFormat.format(date));
							System.out.println("||* File saved successfully! -- " + dateFormat.format(date) + "\n");
				        } catch (IOException e1) {
				        	System.out.println("||* Critical error! File did not save!");
				            e1.printStackTrace();
				            System.exit(0);
				        }			
						ReplayManager.setMyTurn(false);
						((Timer) e.getSource()).stop();
					}
	            }
	        }).start();
		} else if (e.getSource() == SaveDocumentTest.class && e.getID() == 1 && e.getActionCommand() == "test") {
			try {
				String path = "savedoctest.txt";
	            PrintWriter wr = new PrintWriter(path);
	            wr.println(Gui.getInstance().getJTextArea().getText());
	            wr.close();
	        } catch (IOException e1) {
	        	System.out.println("||* Critical error! File did not save!");
	            e1.printStackTrace();
	            System.exit(0);
	        }
		} else if (e.getSource() == FakeReplayManager.class && e.getID() == 1 && e.getActionCommand() == "test") {
			try {
				String path = "replaysavedoctest.txt";
	            PrintWriter wr = new PrintWriter(path);
	            wr.println(Gui.getInstance().getJTextArea().getText());
	            wr.close();
	        } catch (IOException e1) {
	        	System.out.println("||* Critical error! File did not save!");
	            e1.printStackTrace();
	            System.exit(0);
	        }
			FileReader fd = new FileReader("replaysavedoctest.txt");
			ArrayList<ArrayList<String>> d = fd.readFile();
			String content = "";
			int lc = 0;
			int wc = 0;
			for(ArrayList<String> line : d) {
				wc = 0;
				for(String word : line) {
					if(wc == line.size()-1) {
						content += word;
					} else {
						content += word + " ";
					}
					wc++;
				}
				if(lc != d.size()-1) {
					content += "\n";
				}
				lc++;
			}
			assertEquals(content, "open document is working");
		} else {
			JFileChooser filechooser = new JFileChooser(".");
			int status = filechooser.showSaveDialog(null);
			if(status == JFileChooser.APPROVE_OPTION) {
				File fileToSave = filechooser.getSelectedFile();
				String path = fileToSave.getAbsolutePath();
				Gui gui = Gui.getInstance();
				gui.setPath(path);
				try {
					path += ".txt";
		            PrintWriter wr = new PrintWriter(path);
		            wr.println(content);
		            wr.close();
		            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Date date = new Date();
					gui.getDocument().setLastModified(dateFormat.format(date));
					System.out.println("||* File saved successfully! --" + dateFormat.format(date) + "\n");
		        } catch (IOException e1) {
		            e1.printStackTrace();
		        }
			} else if(status == JFileChooser.CANCEL_OPTION) {
			}
		}
	}
	
	public ActionListener clone() {
		return new SaveDocument(
				new String(this.p),
				new String(this.content),
				new Integer(this.TIMER)
				);
	}
}
