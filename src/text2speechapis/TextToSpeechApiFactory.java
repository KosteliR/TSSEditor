package text2speechapis;

public class TextToSpeechApiFactory {
	private TextToSpeechApi ttsapi;
	
	public TextToSpeechApi createTTApi(String ttsapi) {
		if(ttsapi == "Real") {
			this.ttsapi = new FreeTTSAPIAdapter();
			return this.ttsapi;
		} else if (ttsapi == "Fake"){
			this.ttsapi = new FakeTextToSpeechApi();
			return this.ttsapi;
		}
		return null;
	}
}
