package testings;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import command.OpenDocument;
import model.FileReader;
import view.Gui;

public class OpenDocumentTest {

	@Test
	void test() {
		ActionEvent e = new ActionEvent(OpenDocumentTest.class, 1, "test");
		OpenDocument open_document = new OpenDocument();
		open_document.actionPerformed(e);		
		FileReader fd = new FileReader("opendoctest.txt");
		ArrayList<ArrayList<String>> d = fd.readFile();
		int docsize = d.size();
		if(d.size() != Gui.getInstance().getDocument().getLines().size()) {
			assertTrue(false);
		}
		for(int i = 0; i < (docsize-1); i++) {
			int linesize = d.get(i).size();
			if(d.get(i).size() != Gui.getInstance().getDocument().getLines().get(i).getLine().size()) {
				assertTrue(false);
			}
			for(int j = 0; j < (linesize-1); j++) {
				if(d.get(i).get(j) != Gui.getInstance().getDocument().getLines().get(i).getLine().get(j)) {
					assertTrue(false);
				}
			}
		}
		assertTrue(true);
	}
}
