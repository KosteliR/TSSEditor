package testings;

import static org.junit.Assert.assertTrue;

import java.awt.event.ActionEvent;

import org.junit.jupiter.api.Test;

import command.EditDocument;
import model.Document;
import model.Line;
import view.Gui;

public class EditDocumentTest {

	@Test
	void test() {
		Gui.getInstance().setDocument(new Document());
		Gui.getInstance().setText("Bla bla\nBlo");
		ActionEvent e = new ActionEvent(EditDocumentTest.class, 1, "test");
		EditDocument edit_document = new EditDocument();
		edit_document.actionPerformed(e);
		String contents = "";
		int linecount = 0;
		int wordcount = 0;
		for(Line l : Gui.getInstance().getDocument().getLines()) {
			for(String s : l.getLine()) {
				contents += s;
				if(wordcount != (l.getLine().size()-1)) {
					contents +=" ";
				}
				wordcount ++;
			}
			wordcount = 0;
			if(linecount != (Gui.getInstance().getDocument().getLines().size()-1)) {
				contents += '\n';
				linecount ++;
			}
		}
		if(!contents.equals("Bla bla\nBlo")) {
			assertTrue(false);
		}
		assertTrue(true);
	}
}
