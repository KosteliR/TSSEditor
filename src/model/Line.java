package model;

import java.util.ArrayList;
import encodingstrategies.EncodingStrategy;
import encodingstrategies.StrategiesFactory;
import text2speechapis.TextToSpeechApi;
import text2speechapis.TextToSpeechApiFactory;
import view.Gui;

public class Line {
	
	private ArrayList<String> words = new ArrayList<String>();

	private EncodingStrategy enc;
	private TextToSpeechApi audiomanager;
	private TextToSpeechApiFactory textToSpeechApiFactory = new TextToSpeechApiFactory();
	
	public Line() {
		this.words = null;
	}
	
	public void playLine() {
		String contents = "";
		int wordcount = 0;
		for(String s : this.words) {
			contents += s;
			if(wordcount != (words.size()-1)) {
				contents +=" ";
			}
			wordcount ++;
		}
		play(contents);
	}
	
	public void playReverseLine() {
		String contents = "";
		int wordcount = 0;
		for(String s : this.words) {
			//contents = s + " " + contents;
			if(wordcount == 0) {
				contents = s + contents;
			} else {
				contents = s + " " + contents;
			}
			wordcount++;
		}
		play(contents);
	}
	
	public void playEncodedLine(EncodingStrategy enc_str) {
		this.enc = enc_str;
		String contents = "";
		int wordcount = 0;
		for(String s : this.words) {
			contents += s;
			if(wordcount != (words.size()-1)) {
				contents +=" ";
			}
			wordcount ++;
		}
		contents = enc.encode(contents);
		play(contents);
	}
	
	public void tuneEncodingStrategy(EncodingStrategy enc) {
		StrategiesFactory str_factory = new StrategiesFactory();
		Gui gui = Gui.getInstance();
		enc = str_factory.createStrategy(gui.getEncodingTechnique());
		this.enc = enc;
	}
	
	public void setLine(ArrayList<String> word) {
		words = word;
	}
	
	public ArrayList<String> getLine() {
		return words;
	}
	
	public void play(String contents) {
		if(audiomanager == null) {
			audiomanager = textToSpeechApiFactory.createTTApi("Real");
			audiomanager.setRate(180);
			audiomanager.setPitch(150);
			audiomanager.setVolume(80);
		}
		audiomanager.play(contents);
	}
	
	public void setAudioManager(TextToSpeechApi audiomanager) {
		this.audiomanager = audiomanager;	
	}
	
	public void setAudioManager(int pitch, int rate, int volume) {
		if(audiomanager == null) {
			audiomanager = textToSpeechApiFactory.createTTApi("Real");
		}
		audiomanager.setPitch(pitch);
		audiomanager.setRate(rate);
		audiomanager.setVolume(volume);	
	}
}
