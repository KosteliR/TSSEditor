package testings;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.ActionEvent;

import org.junit.jupiter.api.Test;

import command.DocumentToSpeech;
import text2speechapis.FakeTextToSpeechApi;

public class PlayAtBashContentsTest {

	@Test
	void test() {
		ActionEvent e = new ActionEvent(PlayAtBashContentsTest.class, 1, "test");
		DocumentToSpeech doctospeech = new DocumentToSpeech();
		doctospeech.actionPerformed(e);
		FakeTextToSpeechApi fakettsapi = new FakeTextToSpeechApi();
		if(fakettsapi.getPlayText().equals("Svool1\nR zn Pverm")) {
			assertTrue(true);
		} else {
			assertTrue(false);
		}
	}
}
