package testings;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import command.DocumentToSpeech;
import command.OpenDocument;
import command.ReplayCommand;
import command.SaveDocument;

public class ReplayCommandTest {

	@Test
	void test() {
		ArrayList<ActionListener> commands = new ArrayList<ActionListener>();
		commands.add(new OpenDocument());
		commands.add(new DocumentToSpeech());
		commands.add(new SaveDocument());
		
		ActionEvent e1 =  new ActionEvent(ReplayCommandTest.class, 1, "test");
		ReplayCommand rc = new ReplayCommand(commands);
		rc.actionPerformed(e1);
	}
}
