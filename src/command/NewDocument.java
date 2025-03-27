package command;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.Timer;

import model.Document;
import testings.NewDocumentTest;
import view.Gui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class NewDocument extends AbstractAction implements ActionListener {

	private static final long serialVersionUID = 1L; //not used
	private String title = "";
	private String author = "";
	private int TIMER = 0;

	public NewDocument() {
		title = null;
		author = null;
		TIMER = 0;
	}
	
	public NewDocument(String title, String author, int TIMER) {
		this.title = title;
		this.author = author;
		this.TIMER = TIMER;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setAuthor(String author) {
		this.author = author;
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
						NewDocument.this.setEnabled(true);
					} else {
						ReplayManager.setMyTurn(true);
						DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
						Date date = new Date();
						String creation_date = dateFormat.format(date);
						String last_modified_date = dateFormat.format(date);
						Document doc = new Document(NewDocument.this.title, NewDocument.this.author, creation_date, last_modified_date);
						Gui gui = Gui.getInstance();
						gui.setDocument(doc);
						gui.setFrameTitle(NewDocument.this.title);
						System.out.println("||* New document created successfully.");
						System.out.println("||* Author: "+ NewDocument.this.author);
						System.out.println("||* Title : "+ NewDocument.this.title);
						System.out.println("||* Creation Date: " + creation_date + "\n");
						try {
							Thread.sleep(4000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						ReplayManager.setMyTurn(false);
						((Timer) e.getSource()).stop();
					}
	            }
	        }).start();
		} else if (e.getSource() == NewDocumentTest.class && e.getID() == 1 && e.getActionCommand() == "test") {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			String creation_date = dateFormat.format(date);
			String last_modified_date = dateFormat.format(date);
			Document doc = new Document("title", "author", creation_date, last_modified_date);
			Gui gui = Gui.getInstance();
			gui.setDocument(doc);
		} else {
			if((!(this.author==null) && !(this.title==null))) {
				if((!this.author.equals("") && !this.title.equals(""))) {
					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Date date = new Date();
					String creation_date = dateFormat.format(date);
					String last_modified_date = dateFormat.format(date);
					Document doc = new Document(this.title, this.author, creation_date, last_modified_date);
					Gui gui = Gui.getInstance();
					gui.setDocument(doc);
					gui.setFrameTitle(this.title);
					System.out.println("||* New document created successfully.");
					System.out.println("||* Author: "+ this.author);
					System.out.println("||* Title : "+ this.title);
					System.out.println("||* Creation Date: " + creation_date + "\n");
				}
			}
		}
	}
 
	public ActionListener clone() {
		return new NewDocument(
				new String(this.title),
				new String(this.author),
				new Integer(this.TIMER)
		 		);
		}
}
