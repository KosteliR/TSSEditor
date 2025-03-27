package testings;

import static org.junit.Assert.assertTrue;

import java.awt.event.ActionEvent;
import org.junit.jupiter.api.Test;

import command.NewDocument;
import view.Gui;

public class NewDocumentTest {

	@Test
	void test() {
		ActionEvent e = new ActionEvent(NewDocumentTest.class, 1, "test");
		NewDocument new_document = new NewDocument();
		new_document.actionPerformed(e);
		Gui gui = Gui.getInstance();
		assertTrue(!gui.documentIsEmpty());
	}

}
