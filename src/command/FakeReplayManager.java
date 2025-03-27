package command;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FakeReplayManager {
	
	ArrayList<ActionListener> fakeReplayArray = new ArrayList<ActionListener>();
	
	public FakeReplayManager(ArrayList<ActionListener> fakeReplayArray) {
		this.fakeReplayArray = fakeReplayArray;
	}
	
	public ArrayList<ActionListener> getFakeReplayArray() {
		return this.fakeReplayArray;
	}
	
	public void fakeIssueCommand(int index) { //replay
		ActionEvent e = new ActionEvent(FakeReplayManager.class, 1, "test");
		this.fakeReplayArray.get(index).actionPerformed(e);
	}
}
