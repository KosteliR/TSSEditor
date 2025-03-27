package model;

import java.util.ArrayList;
import encodingstrategies.EncodingStrategy;
import encodingstrategies.StrategiesFactory;
import text2speechapis.TextToSpeechApi;
import text2speechapis.TextToSpeechApiFactory;
import view.Gui;

public class Document {

	private ArrayList<Line> lines = new ArrayList<Line>();
	private String title;
	private String author;
	private String creation_date;
	private String last_modified_date;
	
	private EncodingStrategy enc;
	private TextToSpeechApi audiomanager;
	private TextToSpeechApiFactory textToSpeechApiFactory = new TextToSpeechApiFactory();
	private Boolean hasChanged = false;

	public Document() {
		this.title = null;
		this.author = null;
		this.creation_date = null;
		this.last_modified_date = null;
	}

	public Document(Document d) {
		setTitle(d.getTitle());
		setAuthor(d.getAuthor());
		setCreationDate(d.getCreationDate());
		setLastModified(d.getLastModified());
		setLines(d.getLines());
		setEncodingStrategy(d.getEncodingStrategy());
		TextToSpeechApi audio_manager = textToSpeechApiFactory.createTTApi("Real");
		audio_manager.setPitch(Gui.getInstance().getPitch());
		audio_manager.setRate(Gui.getInstance().getRate());
		audio_manager.setVolume(Gui.getInstance().getVolume());
		setAudioManager(audio_manager);	
	}
	
	public Document(String title, String author, String creation_date, String last_modified_date) {
		this.title = title;
		this.author = author;
		this.creation_date = creation_date;
		this.last_modified_date = last_modified_date;
	}
	
	public void setLines(ArrayList<Line> lines) {
		this.lines = lines;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public void setCreationDate(String creation_date) {
		this.creation_date = creation_date;
	}
	
	public void setLastModified(String last_modified_date) {
		this.last_modified_date = last_modified_date;
	}
	
	public void setEncodingStrategy(EncodingStrategy enc) {
		this.enc = enc;
	}
	
	public void setAudioManager(int pitch, int rate, int volume) {
		if(audiomanager == null) {
			audiomanager = textToSpeechApiFactory.createTTApi("Real");
		}
		audiomanager.setPitch(pitch);
		audiomanager.setRate(rate);
		audiomanager.setVolume(volume);	
	}
	
	public void setAudioManager(TextToSpeechApi audiomanager) {
		this.audiomanager = audiomanager;	
	}
	
	public ArrayList<Line> getLines() {
		return lines;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public String getLastModified() {
		return last_modified_date;
	}
	
	public String getCreationDate() {
		return creation_date;
	}
	
	public EncodingStrategy getEncodingStrategy() {
		return this.enc;
	}
	
	public TextToSpeechApi getAudioManager() {
		return this.audiomanager;
	}
	
	public Boolean getHasChanged() {
		return hasChanged;
	}
	
	public void playContents() {
		if(this.lines == null) {
			return;
		}
		String contents = "";
		int linecount = 0;
		int wordcount = 0;
		for(Line l : this.lines) {
			for(String s : l.getLine()) {
				contents += s;
				if(wordcount != (l.getLine().size()-1)) {
					contents +=" ";
				}
				wordcount ++;
			}
			wordcount = 0;
			if(linecount != (lines.size()-1)) {
				contents += '\n';
				linecount ++;
			}
		}
		play(contents);
	} 
	
	public void playReverseContents() {
		if(this.lines == null) {
			return;
		}
		String contents = "";
		int linecount = 0;
		int wordcount = 0;
		for(Line l : this.lines) {
			for(String s : l.getLine()) {
				if(wordcount == 0) {
					contents = s + contents;
				} else {
					contents = s + " " + contents;
				}
				wordcount ++;
			}
			wordcount = 0;
			if(linecount != (lines.size()-1)) {
				contents = '\n' + contents;
				linecount ++;
			}
		}
		play(contents);
	}

	public void playLine(int l) {
		if(this.lines == null) {
			return;
		}
		this.lines.get(l).playLine();
	}

	public void playReverseLine(int l) {
		if(this.lines == null) {
			return;
		}
		this.lines.get(l).playReverseLine();
	}

	public void playEncodedContents() {
		if(this.lines == null) {
			return;
		}
		String contents = "";
		int linecount = 0;
		int wordcount = 0;
		for(Line l : this.lines) {
			for(String s : l.getLine()) {
				contents += s;
				if(wordcount != (l.getLine().size()-1)) {
					contents +=" ";
				}
				wordcount ++;
			}
			wordcount = 0;
			if(linecount != (lines.size()-1)) {
				contents += '\n';
				linecount ++;
			}
		}
		contents = enc.encode(contents);
		play(contents);
	}
	
	public void playEncodedLine(int l) {
		if(this.lines == null) {
			return;
		}
		this.lines.get(l).playEncodedLine(this.enc);
	}
	
	public void tuneEncodingStrategy(EncodingStrategy enc) {
		StrategiesFactory str_factory = new StrategiesFactory();
		Gui gui = Gui.getInstance();
		enc = str_factory.createStrategy(gui.getEncodingTechnique());
		if(this.enc == null) {
			hasChanged = true;
		}
		else {
			if(!this.enc.getMode().equals(gui.getEncodingTechnique())) {
				hasChanged = true;
			} else {
				hasChanged = false;
			}
		}
		this.enc = enc;
	}
	
	public void play(String content) {
		if(audiomanager == null) {
			audiomanager = textToSpeechApiFactory.createTTApi("Real");
			audiomanager.setRate(180);
			audiomanager.setPitch(150);
			audiomanager.setVolume(80);
		}
		audiomanager.play(content);
	}
}
