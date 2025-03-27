package command;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import testings.ReplayCommandTest;
import view.Gui;

public class ReplayCommand implements ActionListener {

	ReplayManager replay_manager;
	FakeReplayManager fake_replay_manager;
	
	public ReplayCommand() {
		replay_manager = null;
	}
	
	public ReplayCommand(ArrayList<ActionListener> commands) {
		 fake_replay_manager = new FakeReplayManager(commands);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == ReplayCommandTest.class && e.getID() == 1 && e.getActionCommand() == "test") {
			for(int i = 0; i < fake_replay_manager.getFakeReplayArray().size(); i++) {
				fake_replay_manager.fakeIssueCommand(i);
			}
		} else {
			Gui gui = Gui.getInstance();
			if(gui.getReplayArray() != null) {
				if(gui.getReplayArray().size() != 0) {
					//System.out.println("I am replayCommand! Calling ReplayManager...");
					replay_manager = new ReplayManager(gui.getReplayArray());
					for(int i = 0; i < replay_manager.getReplayArray().size(); i++) {
						replay_manager.issueCommand(i);
					}
				} else {
					System.out.println("||* Nothing to replay.\n");
				}
			} else {
				System.out.println("||* Error in program.\n");
			}
		}
	}	
}
