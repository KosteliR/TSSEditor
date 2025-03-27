package command;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.Gui;

public class TuneAudio implements ActionListener { 
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Gui gui = Gui.getInstance();
		gui.getDocument().setAudioManager(gui.getPitch(), gui.getRate(), gui.getVolume());	
		for(int i = 0; i < gui.getDocument().getLines().size(); i++) {
			gui.getDocument().getLines().get(i).setAudioManager(gui.getPitch(), gui.getRate(), gui.getVolume());
		}
	}
}
