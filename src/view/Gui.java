package view;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.undo.UndoManager;

import model.*;
import testings.OpenDocumentTest;
import command.*;

public class Gui implements ActionListener{
	
	private static Gui singleInstance = null;
	
	private JTextArea area;
	private JScrollPane scrollPane;
	private JMenuItem newdoc;
	private JButton createdoc;
	private JFrame frame;
	
	//file
	private JMenuItem opendoc;
	private JMenuItem save;
	private JMenuItem exit;
	
	//edit
	private JMenuItem undo;
	private JMenuItem copy;
	private JMenuItem paste;
	private JMenuItem cut;
	private JMenuItem del;
	private JMenuItem speak_content;
	private JMenuItem speak_content_reverse;
	private JMenuItem speak_line;
	private JMenuItem speak_line_reverse;
	private JMenu speak_encoded_content;
	private JMenu speak_encoded_line;
	private JMenuItem rot13_content;
	private JMenuItem atbash_content;
	private JMenuItem rot13_line;
	private JMenuItem atbash_line;
	private JMenuItem tune;
	private JButton replay;
	
	private JLabel txt1;
	private JLabel txt2;
	private JTextField txtfield1; 
	private JTextField txtfield2; 
	
	private String author = "";
	private String title = "";
	private String content = "";
	private String path = "";
	
	private NewDocument new_document;	
	private OpenDocument open_document;
	private SaveDocument save_document;
	
	private int volume_temp = 80; 
	private int rate_temp = 180;
	private int pitch_temp = 150;
	
	private boolean isReversed = false;
	private boolean isEncoded = false;
	private String encodingTechnique = "";
	private int line = 0;
	private int volume = 80;
	private int rate = 150;
	private int pitch = 150;	
	private Document document;
	
	private ArrayList<ActionListener> replayArray = new ArrayList<ActionListener>();
	private CommandsFactory commands_factory;
	private JMenuBar mb;
	
	private int key_counter = 0;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui window = Gui.getInstance();
					window.initialize();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private Gui() {
	}

	public static Gui getInstance() {
		if(singleInstance == null)
			singleInstance = new Gui();
		return singleInstance;
	}
	
	private void initialize() {		
		frame = new JFrame();
		frame.setBounds(500, 500, 500, 500);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Text2Speech Editor");
		area=new JTextArea();
		area.setWrapStyleWord(true);
		area.setLineWrap(true);
		area.setBackground(Color.WHITE);
		area.setBounds(10,10, 400,400);
		scrollPane = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    frame.getContentPane().add(scrollPane);
	    mb = new JMenuBar();
	    scrollPane.setColumnHeaderView(mb);
	    JMenu file = new JMenu("File");
	    JMenu edit = new JMenu("Edit");
	    JMenu play = new JMenu("Play");
	    
	    mb.add(file);
	    mb.add(edit); 
	    mb.add(play);
	    
	    JMenu settings = new JMenu("Settings");
	    tune = new JMenuItem("Tune");
	    settings.add(tune);
	    tune.addActionListener(this);
	    mb.add(settings);
	    
	    //file
	    newdoc = new JMenuItem("New");
	    opendoc = new JMenuItem("Open File...");
	    save = new JMenuItem("Save");
	    exit = new JMenuItem("Exit");
	    
	    newdoc.addActionListener(this);
	    file.add(newdoc);
	    
	    commands_factory = new CommandsFactory();
	    
	    replay = new JButton("Replay");
	    replay.addActionListener(commands_factory.createCommand("Replay"));
	    mb.add(replay);
		save_document = (SaveDocument) commands_factory.createCommand("SaveDocument");
	    
		open_document = (OpenDocument) commands_factory.createCommand("OpenDocument");
		opendoc.addActionListener(this);
	    opendoc.addActionListener(open_document);
	    
	    file.add(opendoc);
	    
	    save.addActionListener(this);
	    save.addActionListener(save_document);
	    save.addActionListener(new ActionListener() {
	    	   public void actionPerformed(ActionEvent ae) {
	    		   content = area.getText();
	    		   save_document.setContent(content);
	    	   }
	    	});
	    file.add(save);
	    
	    exit.addActionListener(this);
	    file.add(exit);
	    
	    //edit
	    undo = new JMenuItem("Undo");
	    copy = new JMenuItem("Copy       Ctrl+C");
	    paste = new JMenuItem("Paste     Ctrl+V");
	    cut = new JMenuItem("Cut          Ctrl+X");
	    del = new JMenuItem("Delete    Del");
	    
	    edit.add(undo);
	    edit.add(copy);
	    edit.add(paste);
	    edit.add(cut);
	    edit.add(del);
	    //speech
	    speak_content = new JMenuItem("Content");
	    play.add(speak_content);
	    speak_content_reverse = new JMenuItem("Reversed Content");
	    play.add(speak_content_reverse);
	    speak_encoded_content = new JMenu("Encoded Content");
	    speak_line = new JMenuItem("Line");
	    play.add(speak_line);
	    speak_line_reverse = new JMenuItem("Reversed Line");
	    play.add(speak_line_reverse);
	    speak_encoded_line = new JMenu("Encoded Line");
	    rot13_content = new JMenuItem("Rot13");
	    atbash_content = new JMenuItem("AtBash");
	    rot13_line = new JMenuItem("Rot13");
	    atbash_line = new JMenuItem("AtBash");
	    speak_encoded_content.add(rot13_content);
	    speak_encoded_content.add(atbash_content);
	    speak_encoded_line.add(rot13_line);
	    speak_encoded_line.add(atbash_line);
	    play.add(speak_encoded_content);
	    play.add(speak_encoded_line);
	    ActionEvent e = new ActionEvent(Gui.class, 1, "gui");
	    EditDocument eddoc = new EditDocument();
	    document = new Document();
	    area.getDocument().addDocumentListener(new DocumentListener() {
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
				  eddoc.actionPerformed(e); 
			  }
	    });
		editEnabled();
		area.addCaretListener(new CaretListener() {
				@Override
				public void caretUpdate(CaretEvent e) {
					 try {
			            int offset = area.getCaretPosition();
			            line = area.getLineOfOffset(offset);
			        } catch (BadLocationException ex) {
			        	ex.printStackTrace();
			        }
				}
		    });
		if(document == null) {
			document = new Document();
		}
		speechEnabled();				
	}

	public void actionPerformed(ActionEvent e) {
		//create new document
		if(e.getSource() == newdoc) {
			scrollPane.setVisible(false);
			txt1 = new JLabel("Enter Author name: ");
			txt1.setBounds(30,15,120,20);
			frame.getContentPane().add(txt1);
			
			txtfield1 = new JTextField();
			txtfield1.setBounds(160,10,120,30);
			frame.getContentPane().add(txtfield1);

			txt2 = new JLabel("Enter Title: ");
			txt2.setBounds(30,60,120,20);
			frame.getContentPane().add(txt2);
			
			txtfield2 = new JTextField();
			txtfield2.setBounds(160,60,120,30);
			
			frame.getContentPane().add(txtfield2);
			createdoc = new JButton("Create Document");			
			createdoc.addActionListener(this);
			new_document = (NewDocument) commands_factory.createCommand("NewDocument");
			createdoc.setBounds(100, 150, 150, 30);
			frame.getContentPane().add(createdoc);	
			createdoc.addActionListener(new_document);
			createdoc.addActionListener(new ActionListener(){
				   public void actionPerformed(ActionEvent ae){
					 author = txtfield1.getText();
				     title = txtfield2.getText();
					 new_document.setAuthor(author);
					 new_document.setTitle(title);
				   }
				});
		}
		else if(e.getSource() == createdoc) {
			if(title.equals("") || author.equals("")) {
				JOptionPane.showMessageDialog(null, "Please fill in all the required fields.");
				author = txtfield1.getText();
				title = txtfield2.getText();
				new_document.setAuthor(author);
				new_document.setTitle(title);
			} else {	
				txt1.setVisible(false);
				txt2.setVisible(false);
				txtfield1.setVisible(false);
				txtfield2.setVisible(false);
				createdoc.setVisible(false);
				mb.setVisible(true);
				scrollPane.setVisible(true);
				area.setText("");
			    NewDocument newdoc = new NewDocument(title, author, (key_counter*2)*500);
			    replayArray.add(key_counter, newdoc.clone());
			    key_counter++;
			}			
		}
		//save document
		else if(e.getSource() == save) {
			if(document == null) {
				document = new Document();
			}
			while(true) {
				if(!path.equals("")) {
					SaveDocument sd = new SaveDocument(path, area.getText(), (key_counter*2)*500);
					replayArray.add(key_counter, sd.clone());
					key_counter++;
					break;
				}
			}
		}
		//open document
		else if(e.getSource() == opendoc || (e.getSource() == OpenDocumentTest.class && e.getID() == 1 && e.getActionCommand() == "test")) {
			try {
				String toString = "";
				for(Line l : document.getLines()) {
					for(String s : l.getLine()) {
						toString += s + " ";
					}
					toString += '\n';
				}
				area.setText(toString);
				OpenDocument o_d = new OpenDocument(document.getLines(), (key_counter*2)*500);
				replayArray.add(key_counter, o_d.clone());
				key_counter++;
			} catch (Exception e1) {
			}
		}
		//tune audio
		else if(e.getSource() == tune) {
			
			JFrame tune_frame = new JFrame();
			tune_frame.setBounds(300, 300, 500, 300);
			tune_frame.setResizable(false);
			tune_frame.setTitle("Tune");
			tune_frame.setVisible(true);
			
			JPanel volumePane = new JPanel(new BorderLayout());
			volumePane.setBorder(BorderFactory.createTitledBorder("Volume"));
			JSlider v = new JSlider();
			v.setMajorTickSpacing(20);
		    v.setMinorTickSpacing(5);
		    v.setPaintTicks(true);
		    v.setPaintLabels(true);
		    v.setValue(volume);
		    v.addChangeListener(new ChangeListener(){
				@Override
				public void stateChanged(ChangeEvent e) {
					volume_temp = v.getValue();
				}
		    });
			volumePane.add(v);

	        JPanel pitchPane = new JPanel(new BorderLayout());
	        pitchPane.setBorder(BorderFactory.createTitledBorder("Pitch"));
	        JSlider p = new JSlider();
	        p.setMaximum(200);
			p.setMajorTickSpacing(40);
		    p.setMinorTickSpacing(10);
		    p.setPaintTicks(true);
		    p.setPaintLabels(true);
		    p.setValue(pitch);
		    p.addChangeListener(new ChangeListener(){
				@Override
				public void stateChanged(ChangeEvent e) {
					pitch_temp = p.getValue();
				}
		    });
		    pitchPane.add(p);
	        
	        JPanel ratePane = new JPanel(new BorderLayout());
	        ratePane.setBorder(BorderFactory.createTitledBorder("Rate"));
	        JSlider r = new JSlider();
	        r.setMaximum(400);
			r.setMajorTickSpacing(80);
		    r.setMinorTickSpacing(20);
		    r.setPaintTicks(true);
		    r.setPaintLabels(true);
		    r.setValue(rate);
		    r.addChangeListener(new ChangeListener(){
				@Override
				public void stateChanged(ChangeEvent e) {
					rate_temp = r.getValue();
				}
		    });
		    ratePane.add(r);
	        
		    JMenuBar m = new JMenuBar();
		    JButton apply = new JButton("Apply");
		    apply.addActionListener(commands_factory.createCommand("TuneAudio"));
		    apply.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					volume = volume_temp;
					pitch = pitch_temp;
					rate = rate_temp;
					tune_frame.dispose();
				}
		    });
		    JButton cancel = new JButton("Cancel");
		    cancel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					tune_frame.dispose();
				}
		    });
		    JSeparator s = new JSeparator();
		    s.setForeground(Color.WHITE);
		    m.add(s);
		    m.add(apply);
		    m.add(cancel);
		    tune_frame.getContentPane().setLayout(new GridLayout(4, 0));
		    tune_frame.getContentPane().add(volumePane, 0);
	        tune_frame.getContentPane().add(pitchPane, 1);
	        tune_frame.getContentPane().add(ratePane,2);
	        tune_frame.getContentPane().add(m);
		}
		else if(e.getSource() == speak_content) {
			DocumentToSpeech dts = new DocumentToSpeech(isEncoded, isReversed, document, (key_counter*2)*500);
			replayArray.add(key_counter, dts.clone());
			key_counter ++;
		} else if(e.getSource() == speak_content_reverse) {
			DocumentToSpeech dts = new DocumentToSpeech(isEncoded, isReversed, document, (key_counter*2)*500);
			replayArray.add(key_counter, dts.clone());
			key_counter ++;
		} else if(e.getSource() == speak_line) {
			LineToSpeech lts = new LineToSpeech(isEncoded, isReversed, document, (key_counter*2)*500, line);
			replayArray.add(key_counter, lts.clone());
			key_counter ++;
		} else if(e.getSource() == speak_line_reverse) {
			LineToSpeech lts = new LineToSpeech(isEncoded, isReversed, document, (key_counter*2)*500, line);
			replayArray.add(key_counter, lts.clone());
			key_counter ++;
		} else if(e.getSource() == rot13_content) {
			DocumentToSpeech dts = new DocumentToSpeech(isEncoded, isReversed, document, (key_counter*2)*500);
			replayArray.add(key_counter, dts.clone());
			key_counter ++;
		} else if(e.getSource() == rot13_line) {
			LineToSpeech lts = new LineToSpeech(isEncoded, isReversed, document, (key_counter*2)*500, line);
			replayArray.add(key_counter, lts.clone());
			key_counter ++;
		} else if(e.getSource() == atbash_content) {
			DocumentToSpeech dts = new DocumentToSpeech(isEncoded, isReversed, document, (key_counter*2)*500);
			replayArray.add(key_counter, dts.clone());
			key_counter ++;
		} else if(e.getSource() == atbash_line) {
			LineToSpeech lts = new LineToSpeech(isEncoded, isReversed, document, (key_counter*2)*500, line);
			replayArray.add(key_counter, lts.clone());
			key_counter ++;
		}
		//exit application
		else if(e.getSource() == exit) {
			System.exit(0);
		}
	}
	
	private void speechEnabled() {
		speak_content.addActionListener(this);
		speak_content.addActionListener(commands_factory.createCommand("DocumentToSpeech"));
		
		speak_content.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isReversed = false;
				isEncoded = false;
			}
		});
		speak_content_reverse.addActionListener(this);
		speak_content_reverse.addActionListener(commands_factory.createCommand("DocumentToSpeech"));
		
		speak_content_reverse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isReversed = true;
				isEncoded = false;
			}
		});
		speak_line.addActionListener(this);
		speak_line.addActionListener(commands_factory.createCommand("LineToSpeech"));
		
		speak_line.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isReversed = false;
				isEncoded = false;
			}
		});
		speak_line_reverse.addActionListener(this);
		speak_line_reverse.addActionListener(commands_factory.createCommand("LineToSpeech"));
		
		speak_line_reverse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isReversed = true;
				isEncoded = false;
			}
		});
		
		TuneEncoding enc = new TuneEncoding();
		rot13_content.addActionListener(commands_factory.createCommand("DocumentToSpeech"));
		rot13_content.addActionListener(this);
		rot13_content.addActionListener(enc);
		rot13_content.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				encodingTechnique = "Rot13";
				isEncoded = true;
			}
		}); 
		rot13_line.addActionListener(commands_factory.createCommand("LineToSpeech"));
		rot13_line.addActionListener(this);
		rot13_line.addActionListener(enc);
		rot13_line.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				encodingTechnique = "Rot13";
				isEncoded = true;
			}
		});
		atbash_content.addActionListener(commands_factory.createCommand("DocumentToSpeech"));	
		atbash_content.addActionListener(this);
		atbash_content.addActionListener(enc);
		atbash_content.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				encodingTechnique = "AtBash";
				isEncoded = true;
			}
		});
		
		atbash_line.addActionListener(commands_factory.createCommand("LineToSpeech"));
		atbash_line.addActionListener(this);
		atbash_line.addActionListener(enc);
		atbash_line.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				encodingTechnique = "AtBash";
				isEncoded = true;
			}
		});
	}

	private void editEnabled() {
		UndoManager editManager = new UndoManager();
		area.getDocument().addUndoableEditListener(editManager);
		commands_factory.setEditDocument(copy, paste, cut, del);
		EditDocument ED = (EditDocument) commands_factory.createCommand("EditDocument");
		copy.addActionListener(ED);
		copy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae){
				ED.setSel(area.getSelectedText());
			}
		});
		cut.addActionListener(ED);
		cut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae){
				ED.setSel(area.getSelectedText());
			}
		});
		paste.addActionListener(ED);	
		undo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (editManager.canUndo()) {
                    editManager.undo();
                }
            }
        });
		del.addActionListener(ED);
	}
	
	public void setDocument(Document document) {
		this.document = document;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public void setFrameTitle(String title) {
		frame.setTitle("Text2Speech Editor - " + title);
	}

	public void setText(String content) {
		if(this.area == null) {
			area = new JTextArea();
		}
		area.setText(content);
	}
	
	public void setRate(int rate) {
		this.rate = rate;
	}
	
	public void setPitch(int pitch) {
		this.pitch = pitch;
	}
	
	public void setVolume(int volume) {
		this.volume = volume;
	}
	
	public void setEncodingTechnique(String encodingTechnique) {
		this.encodingTechnique = encodingTechnique;
	}
	
	public Boolean getisReversed() {
		return isReversed;
	}
	
	public Boolean getisEncoded() {
		return isEncoded;
	}
	
	public String getEncodingTechnique() {
		return encodingTechnique;
	}
	
	public int getLine() {
		return line;
	}
	
	public Document getDocument() {
		return document;
	}
	
	public int getRate() {
		return rate;
	}
	
	public int getPitch() {
		return pitch;
	}
	
	public int getVolume() {
		return volume;
	}
	
	public JTextArea getJTextArea() {
		return area;
	}
	
	public ArrayList<ActionListener> getReplayArray() {
		return replayArray;
	}
	
	public Boolean documentIsEmpty() {
		if(this.document == null) {
			return true;
		}
		return false;
	}
}

