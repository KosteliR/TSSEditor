package command;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ReplayManager{
	
	private static boolean myTurn = false;
	ArrayList<ActionListener> replayArray = new ArrayList<ActionListener>();
	
	public ReplayManager(ArrayList<ActionListener> replayArray) {
		this.replayArray = replayArray;
	}
	
	public static Boolean getMyTurn() {
		return myTurn;
	}
	
	public static void setMyTurn(Boolean fl) {
		myTurn = fl;
	}
	
	public ArrayList<ActionListener> getReplayArray() {
		return this.replayArray;
	}
	
	public void issueCommand(int index) { //replay
		//System.out.println("I am ReplayManager, and I issue command...");
		ActionEvent e = new ActionEvent(ReplayManager.class, 1, "dummy");
		//System.out.println(index + "aaaaaaaaaaaaaaaaaaaaaaaa");
		this.replayArray.get(index).actionPerformed(e);
	}
}
