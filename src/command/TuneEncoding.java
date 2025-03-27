package command;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import encodingstrategies.EncodingStrategy;
import view.Gui;

public class TuneEncoding implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		EncodingStrategy enc_stategy = null;
		Gui gui = Gui.getInstance();
		gui.getDocument().tuneEncodingStrategy(enc_stategy);	
		try {
			gui.getDocument().getLines().get(gui.getLine()).tuneEncodingStrategy(enc_stategy);
		} catch (Exception e1) {}
	}
}
