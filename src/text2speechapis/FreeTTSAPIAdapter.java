package text2speechapis;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class FreeTTSAPIAdapter implements TextToSpeechApi {
	private VoiceManager vm = VoiceManager.getInstance();
	private Voice voice = vm.getVoice("kevin16");
	
	FreeTTSAPIAdapter() {
		//System.setProperty("mbrola.base", "");
		//voice = VoiceManager.getInstance().getVoice("kevin16");
		//System.setProperty("mbrola.base", "C:\\Users\\Σοφια\\Desktop\\Text2SpeechEditor2\\freetts-1.2.2-bin\\freetts-1.2\\mbrola");
		//voice = vm.getVoice("mbrola_us1");
		if (voice != null) {
            voice.allocate();//Allocating Voice
		}
	}
	
	@Override
	public void play(String s) {
		voice.speak(s);
	}
	
	@Override
	public void setVolume(int v) {
		voice.setVolume(v);
	}
	
	@Override
	public void setRate(int r) {
		voice.setRate(r);
	}
	
	@Override
	public void setPitch(int p) {
		voice.setPitch(p);
	}
}
