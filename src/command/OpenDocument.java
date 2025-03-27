package command;

import static org.junit.Assert.assertTrue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.Timer;

import model.Document;
import model.FileReader;
import model.Line;
import testings.OpenDocumentTest;
import view.Gui;

public class OpenDocument extends AbstractAction implements ActionListener {

	private static final long serialVersionUID = 1L; //not used
	
	private ArrayList<Line> l;
	private int TIMER;
	
	public OpenDocument() {
		this.l = null;
		this.TIMER = 0;
	}
	
	public OpenDocument(ArrayList<Line> l, int t) {
		this.l = l;
		this.TIMER = t;
	}
	
	public ArrayList<Line> getContent() {
		return this.l;
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
						OpenDocument.this.setEnabled(true);                   
					} else {
						ReplayManager.setMyTurn(true);   
						Gui gui = Gui.getInstance();
						Document d =  new Document();
						gui.setDocument(d);
						gui.getDocument().setLines(OpenDocument.this.l);
						String toString = "";
						for(Line l : gui.getDocument().getLines()) {
							for(String s : l.getLine()) {
								toString += s + " ";
							}
							toString += '\n';
						}
						gui.getJTextArea().setText(toString);	
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						ReplayManager.setMyTurn(false);                        
						((Timer) e.getSource()).stop();                        
					}
	            }
	        }).start();
		} else if (e.getSource() == OpenDocumentTest.class && e.getID() == 1 && e.getActionCommand() == "test") {
			try { 
				ArrayList<ArrayList<String>> d = new ArrayList<ArrayList<String>>();
				l = new ArrayList<Line>();
				FileReader f = new FileReader("opendoctest.txt");
				d = f.readFile();
				for(ArrayList<String> x : d) {
					Line line = new Line();
					line.setLine(x);
					l.add(line);
				}
				Gui.getInstance().setDocument(new Document());;
				Gui.getInstance().getDocument().setLines(l);
	        } catch(Exception e1) {
	            e1.printStackTrace();
	        }
		} else if (e.getSource() == FakeReplayManager.class && e.getID() == 1 && e.getActionCommand() == "test") { 
			try { 
				ArrayList<ArrayList<String>> d = new ArrayList<ArrayList<String>>();
				l = new ArrayList<Line>();
				FileReader f = new FileReader("opendoctest.txt");
				d = f.readFile();
				for(ArrayList<String> x : d) {
					Line line = new Line();
					line.setLine(x);
					l.add(line);
				}
				Gui.getInstance().setDocument(new Document());
				Gui.getInstance().getDocument().setLines(l);
				String contents = "";
				int linecount = 0;
				int wordcount = 0;
				for(Line l : Gui.getInstance().getDocument().getLines()) {
					for(String s : l.getLine()) {
						contents += s;
						if(wordcount != (l.getLine().size()-1)) {
							contents +=" ";
						}
						wordcount ++;
					}
					wordcount = 0;
					if(linecount != (Gui.getInstance().getDocument().getLines().size()-1)) {
						contents += '\n';
						linecount ++;
					}
				}
				Gui.getInstance().setText(contents);
				
	        } catch(Exception e1) {
	            e1.printStackTrace();
	        }
			FileReader fd = new FileReader("opendoctest.txt");
			ArrayList<ArrayList<String>> d = fd.readFile();
			int docsize = d.size();
			if(d.size() != Gui.getInstance().getDocument().getLines().size()) {
				assertTrue(false);
			}
			for(int i = 0; i < docsize; i++) {
				int linesize = d.get(i).size();
				if(d.get(i).size() != Gui.getInstance().getDocument().getLines().get(i).getLine().size()) {
					assertTrue(false);
				}
				for(int j = 0; j < linesize; j++) {
					if(!d.get(i).get(j).equals(Gui.getInstance().getDocument().getLines().get(i).getLine().get(j))) {
						assertTrue(false);
					}
				}
			}			
		} else {
			JFileChooser filechooser = new JFileChooser(".");
			int status = filechooser.showOpenDialog(null);
			
			if(status == JFileChooser.APPROVE_OPTION) {
				File fileToOpen = filechooser.getSelectedFile();
				String path = fileToOpen.getAbsolutePath();
				try {
					ArrayList<ArrayList<String>> d = new ArrayList<ArrayList<String>>();
					l = new ArrayList<Line>();
					FileReader f = new FileReader(path);
					d = f.readFile();
					for(ArrayList<String> x : d) {
						Line line = new Line();
						line.setLine(x);
						l.add(line);
					}
					Gui.getInstance().getDocument().setLines(l);
		        } catch(Exception e1) {
		            e1.printStackTrace();
		        }
			} else if(status == JFileChooser.CANCEL_OPTION) {
			}
			
		}
	}
 
	public ActionListener clone() {
		return new OpenDocument(
				new ArrayList<Line>(this.l),
				new Integer(this.TIMER)
				);
	}
}
