package command;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Timer;

import encodingstrategies.StrategiesFactory;
import model.Document;
import model.Line;
import testings.PlayAtBashLineTest;
import testings.PlayLineReverseTest;
import testings.PlayLineTest;
import testings.PlayRot13LineTest;
import text2speechapis.TextToSpeechApi;
import text2speechapis.TextToSpeechApiFactory;
import view.Gui;

public class LineToSpeech extends AbstractAction implements ActionListener {
	
    private static final long serialVersionUID = 1L;  //not used
	
	private boolean isEncoded;
	private boolean isReversed;
	private Document document;
	private int TIMER;
	private int line;
	
	public LineToSpeech() {
		this.isEncoded = false;
		this.isReversed = false;
		this.document = null;
		this.TIMER = 0;
		this.line = 0;
	}
	
	public LineToSpeech(boolean isEncoded, boolean isReversed, Document document, int t, int l) {
		this.isEncoded = isEncoded;
		this.isReversed = isReversed;
		this.document = document;
		this.TIMER = t;
		this.line = l;
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
						LineToSpeech.this.setEnabled(true);
					} else {
						ReplayManager.setMyTurn(true);
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						if(LineToSpeech.this.isEncoded == true) {
							try {
								LineToSpeech.this.document.playEncodedLine(LineToSpeech.this.line); 
							} catch(Exception e1) {
							}
						} else {
							if(LineToSpeech.this.isReversed == true) {
								try {
									LineToSpeech.this.document.playReverseLine(LineToSpeech.this.line);
								} catch(Exception e1) {
								}
							} else {
								try {
									LineToSpeech.this.document.playLine(LineToSpeech.this.line);
								} catch(Exception e1) {
								}
							}
						}
						ReplayManager.setMyTurn(false);
						((Timer) e.getSource()).stop();
					}
	            }
	        }).start();
		} else if(e.getID() == 1 && e.getActionCommand() == "test") {
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
			if(e.getSource() == PlayLineTest.class) {
				ArrayList<String> l = new ArrayList<String>();
				l.add("bla");
				l.add("hi");
				line.setLine(l);
				lines.add(line);
				document.setLines(lines);
				document.getLines().get(2).setAudioManager(audiomanager);
				document.playLine(2);
			} else if(e.getSource() == PlayLineReverseTest.class) {
				ArrayList<String> l = new ArrayList<String>();
				l.add("bla");
				l.add("hi");
				line.setLine(l);
				lines.add(line);
				document.setLines(lines);
				document.getLines().get(2).setAudioManager(audiomanager);
				document.playReverseLine(2);
			} else if(e.getSource() == PlayRot13LineTest.class) { 
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
				document.getLines().get(1).setAudioManager(audiomanager);
				document.playEncodedLine(1);
			} else if(e.getSource() == PlayAtBashLineTest.class) { 
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
				document.getLines().get(1).setAudioManager(audiomanager);
				document.playEncodedLine(1);
			}			
		} else {
			Gui gui = Gui.getInstance();
			if(gui.getisEncoded() == true) {
				try {
					gui.getDocument().playEncodedLine(gui.getLine()); 
				} catch(Exception e1) {
				}
			} else {
				if(gui.getisReversed() == true) {
					try {
						gui.getDocument().playReverseLine(gui.getLine());
					} catch(Exception e1) {
					}
				} else {
					try {
						gui.getDocument().playLine(gui.getLine());
					} catch(Exception e1) {
					}
				}
			}
		}
	}	
 
	public ActionListener clone() {
		return new LineToSpeech(
				new Boolean(this.isEncoded),
				new Boolean(this.isReversed),
				new Document(this.document),
				new Integer(this.TIMER),
				new Integer(this.line)
				);
	}
}
