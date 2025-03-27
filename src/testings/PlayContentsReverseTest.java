package testings;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.event.ActionEvent;
import org.junit.jupiter.api.Test;
import command.DocumentToSpeech;
import text2speechapis.FakeTextToSpeechApi;

public class PlayContentsReverseTest {

	@Test
	void test() {
		ActionEvent e = new ActionEvent(PlayContentsReverseTest.class, 1, "test");
		DocumentToSpeech doctospeech = new DocumentToSpeech();
		doctospeech.actionPerformed(e);
		FakeTextToSpeechApi fakettsapi = new FakeTextToSpeechApi();
		if(fakettsapi.getPlayText().equals("2 1\n2 1")) {
			assertTrue(true);
		} else {
			assertTrue(false);
		}
	}
}
