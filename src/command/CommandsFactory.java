package command;

import java.awt.event.ActionListener;
//import java.util.HashMap;

import javax.swing.JMenuItem;

public class CommandsFactory {
	private JMenuItem copy;
	private JMenuItem cut;
	private JMenuItem paste;
	private JMenuItem delete;
	private NewDocument new_document;
	private OpenDocument open_document;
	private SaveDocument save_document;
	private DocumentToSpeech doc_to_speech;
	private LineToSpeech line_to_speech;
	private EditDocument edit_document;
	private TuneAudio tune_audio;
	private ReplayCommand replay_command;
	
	//Edit Document 
	public void setEditDocument(JMenuItem copy, JMenuItem paste, JMenuItem cut, JMenuItem delete) {
		this.copy = copy;
		this.paste = paste;
		this.cut = cut;
		this.delete = delete;
	}
	
	public ActionListener createCommand(String command) {
		if (command.equals("NewDocument")) {
			new_document = new NewDocument();
			return new_document;
		} else if (command.equals("OpenDocument")) {
			open_document = new OpenDocument();
			return open_document;
		} else if (command.equals("SaveDocument")) {
			save_document = new SaveDocument();
			return save_document;
		} else if (command.equals("DocumentToSpeech")) {
			doc_to_speech = new DocumentToSpeech();	
			return doc_to_speech;
		} else if (command.equals("LineToSpeech")) {
			line_to_speech = new LineToSpeech();
			return line_to_speech;
		} else if (command.equals("EditDocument")) {
			edit_document = new EditDocument();
			edit_document.setButtons(copy, paste, cut, delete);
			return edit_document;
		} else if (command.equals("TuneAudio")) {
			tune_audio = new TuneAudio();
			return tune_audio;
		} else if (command.equals("Replay")) {
			replay_command = new ReplayCommand();
			return replay_command;
		}
		System.out.println("||*Failure");
		System.exit(0);
		return null;
	}
}
