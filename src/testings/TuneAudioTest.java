package testings;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.ActionEvent;

import org.junit.jupiter.api.Test;

import command.TuneAudio;
import model.Document;
import text2speechapis.FakeTextToSpeechApi;
import text2speechapis.TextToSpeechApiFactory;
import view.Gui;

public class TuneAudioTest {

	@Test
	void test() {
		Gui.getInstance().setDocument(new Document());
		TextToSpeechApiFactory ttsfactory = new TextToSpeechApiFactory();
		Gui.getInstance().getDocument().setAudioManager(ttsfactory.createTTApi("Fake"));
		Gui.getInstance().setPitch(100);
		Gui.getInstance().setRate(100);
		Gui.getInstance().setVolume(100);
		ActionEvent e = new ActionEvent(TuneAudioTest.class, 1, "test");
		TuneAudio tuneaudio = new TuneAudio();
		tuneaudio.actionPerformed(e);
		FakeTextToSpeechApi  fakettsapi = new FakeTextToSpeechApi(); 
		if(fakettsapi.getPitch() != 100 || fakettsapi.getRate() != 100 || fakettsapi.getVolume() != 100) {
			assertTrue(false);
		}
		assertTrue(true);
	}
}
