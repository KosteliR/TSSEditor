package testings;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.ActionEvent;

import org.junit.jupiter.api.Test;

import command.DocumentToSpeech;
import text2speechapis.FakeTextToSpeechApi;

public class PlayRot13ContentsTest {

	@Test
	void test() {
		ActionEvent e = new ActionEvent(PlayRot13ContentsTest.class, 1, "test");
		DocumentToSpeech doctospeech = new DocumentToSpeech();
		doctospeech.actionPerformed(e);
		FakeTextToSpeechApi fakettsapi = new FakeTextToSpeechApi();
		if(fakettsapi.getPlayText().equals("Uryyb1\nV nz Xriva")) {
			assertTrue(true);
		} else {
			assertTrue(false);
		}
	}
}
