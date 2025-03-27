package testings;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.ActionEvent;

import org.junit.jupiter.api.Test;

import command.LineToSpeech;
import text2speechapis.FakeTextToSpeechApi;

public class PlayRot13LineTest {

	@Test
	void test() {
		ActionEvent e = new ActionEvent(PlayRot13LineTest.class, 1, "test");
		LineToSpeech linetospeech = new LineToSpeech();
		linetospeech.actionPerformed(e);
		FakeTextToSpeechApi fakettsapi = new FakeTextToSpeechApi();
		if(fakettsapi.getPlayText().equals("V nz Xriva")) {
			assertTrue(true);
		} else {
			assertTrue(false);
		}
	}
}
