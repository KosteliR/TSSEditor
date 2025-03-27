package command;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import model.Document;
import model.Line;
import testings.PlayAtBashContentsTest;
import testings.PlayContentsReverseTest;
import testings.PlayContentsTest;
import testings.PlayRot13ContentsTest;
import text2speechapis.FakeTextToSpeechApi;
import text2speechapis.TextToSpeechApi;
import text2speechapis.TextToSpeechApiFactory;
import view.Gui;
import javax.swing.*;

import encodingstrategies.StrategiesFactory;

public class DocumentToSpeech extends AbstractAction implements ActionListener { //

	private static final long serialVersionUID = 1L;  //not used
	
	private boolean isEncoded;
	private boolean isReversed;
	private Document document;
	private int TIMER;
	
	public DocumentToSpeech() {
		isEncoded = false;
		isReversed = false;
		document = null;
		TIMER = 0;
	}
	
	public DocumentToSpeech(boolean isEncoded, boolean isReversed, Document document, int t) {
		this.isEncoded = isEncoded;
		this.isReversed = isReversed;
		this.document = document;
		this.TIMER = t;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == FakeReplayManager.class && e.getID() == 1 && e.getActionCommand() == "test") {
			Document document = Gui.getInstance().getDocument();
			TextToSpeechApiFactory ttsapifactory = new TextToSpeechApiFactory();
			TextToSpeechApi audiomanager = ttsapifactory.createTTApi("Fake");
			document.setAudioManager(audiomanager);
			document.playContents();
			FakeTextToSpeechApi fakettsapi = new FakeTextToSpeechApi();
			if(fakettsapi.getPlayText().equals("open document is working")) {
				assertTrue(true);
			} else {
				assertTrue(false);
			}
		} else if(e.getSource() == ReplayManager.class && e.getID() == 1 && e.getActionCommand() == "dummy") {
			setEnabled(false);
			new Timer(TIMER, new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
					if(ReplayManager.getMyTurn() == true) {
						((Timer) e.getSource()).stop();
						DocumentToSpeech.this.setEnabled(true);
					} else {
						ReplayManager.setMyTurn(true);
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
						if(DocumentToSpeech.this.isEncoded == true) {
							try {
								DocumentToSpeech.this.document.playEncodedContents(); 
							} catch(Exception e1) {
							}
						} else {
							if(DocumentToSpeech.this.isReversed == true) {
								try {
									DocumentToSpeech.this.document.playReverseContents();
								} catch(Exception e1) {
								}
							} else {
								try {
									DocumentToSpeech.this.document.playContents();
								} catch(Exception e1) {
								}
							}	
						}
						ReplayManager.setMyTurn(false);
						((Timer) e.getSource()).stop();
					}
	            }
	        }).start();
		}else if(e.getID() == 1 && e.getActionCommand() == "test") {
			Document document = new Document();
			ArrayList<Line> lines = new ArrayList<Line>();
			Line line = new Line();
			for(int i = 0; i < 2; i++) {
				ArrayList<String> l = new ArrayList<String>();
				l.add(String.valueOf(i));
				l.add(String.valueOf(i+1));
				line.setLine(l);
				lines.add(line);
			}
			document.setLines(lines);
			TextToSpeechApiFactory ttsapifactory = new TextToSpeechApiFactory();
			TextToSpeechApi audiomanager = ttsapifactory.createTTApi("Fake");
			document.setAudioManager(audiomanager);
			if(e.getSource() == PlayContentsTest.class) {
				document.playContents();
			} else if(e.getSource() == PlayContentsReverseTest.class) {
				document.playReverseContents();
			} else if(e.getSource() == PlayRot13ContentsTest.class) {
				StrategiesFactory strategies_factory = new StrategiesFactory();
				document.setEncodingStrategy(strategies_factory.createStrategy("Rot13"));
				lines = new ArrayList<Line>();
				line = new Line();
				ArrayList<String> l = new ArrayList<String>();
				line = new Line();
				l.add("Hello1");
				line.setLine(l);
				lines.add(line);
				l = new ArrayList<String>();
				line = new Line();
				l.add("I am Kevin");
				line.setLine(l);
				lines.add(line);
				document.setLines(lines);
				document.playEncodedContents();
			} else if(e.getSource() == PlayAtBashContentsTest.class) {
				StrategiesFactory strategies_factory = new StrategiesFactory();
				document.setEncodingStrategy(strategies_factory.createStrategy("AtBash"));
				lines = new ArrayList<Line>();
				line = new Line();
				ArrayList<String> l = new ArrayList<String>();
				line = new Line();
				l.add("Hello1");
				line.setLine(l);
				lines.add(line);
				l = new ArrayList<String>();
				line = new Line();
				l.add("I am Kevin");
				line.setLine(l);
				lines.add(line);
				document.setLines(lines);
				document.playEncodedContents();
			}
 		} else {
			Gui gui = Gui.getInstance();
			if(gui.getisEncoded() == true) {
				try {
					gui.getDocument().playEncodedContents(); 
				} catch(Exception e1) {
				}
			} else {
				if(gui.getisReversed() == true) {
					try {
						gui.getDocument().playReverseContents();
					} catch(Exception e1) {
					}
				} else {
					try {
						gui.getDocument().playContents();
					} catch(Exception e1) {
					}
				}
			}	
		}
	}

	public ActionListener clone() {
		return new DocumentToSpeech(
				new Boolean(this.isEncoded),
				new Boolean(this.isReversed),
				new Document(this.document),
				new Integer(this.TIMER)
				);
	}
}
