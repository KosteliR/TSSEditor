package testings;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.ActionEvent;

import org.junit.jupiter.api.Test;

import command.TuneEncoding;
import model.Document;
import view.Gui;

public class TuneEncodingTest {

	@Test
	void test() {
		Gui.getInstance().setDocument(new Document());
		Gui.getInstance().setEncodingTechnique("Rot13");
		ActionEvent e = new ActionEvent(TuneEncodingTest.class, 1, "test");
		TuneEncoding tuneenc = new TuneEncoding();
		tuneenc.actionPerformed(e);
		if(!Gui.getInstance().getDocument().getHasChanged()) {
			assertTrue(false);
		}
		Gui.getInstance().setEncodingTechnique("Rot13");
		tuneenc.actionPerformed(e);
		if(Gui.getInstance().getDocument().getHasChanged()) {
			assertTrue(false);
		}
		Gui.getInstance().setEncodingTechnique("AtBash");
		tuneenc.actionPerformed(e);
		if(!Gui.getInstance().getDocument().getHasChanged()) {
			assertTrue(false);
		}
		assertTrue(true);
	}
}
