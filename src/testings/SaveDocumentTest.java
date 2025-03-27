package testings;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import command.SaveDocument;
import model.FileReader;
import view.Gui;

public class SaveDocumentTest {

	@Test
	void test() {
		Gui gui = Gui.getInstance();
		gui.setText("bla bla\nblabla");
		ActionEvent e = new ActionEvent(SaveDocumentTest.class, 1, "test");
		SaveDocument save_document = new SaveDocument();
		save_document.actionPerformed(e);
		FileReader fd = new FileReader("savedoctest.txt");
		ArrayList<ArrayList<String>> d = fd.readFile();
		String content = "";
		int lc = 0;
		int wc = 0;
		for(ArrayList<String> line : d) {
			wc = 0;
			for(String word : line) {
				if(wc == line.size()-1) {
					content += word;
				} else {
					content += word + " ";
				}
				wc++;
			}
			if(lc != d.size()-1) {
				content += "\n";
			}
			lc++;
		}
		assertEquals(content, "bla bla\nblabla");
	}
}
