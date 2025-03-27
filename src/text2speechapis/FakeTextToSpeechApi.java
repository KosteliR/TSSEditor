package text2speechapis;

public class FakeTextToSpeechApi implements TextToSpeechApi{
	private static String play_text;
	private static int volume;
	private static int pitch;
	private static int rate;
	
	@Override
	public void play(String s) {
		play_text = s;
	}

	@Override
	public void setVolume(int v) {
		volume = v;
	}

	@Override
	public void setRate(int r) {
		rate = r;
	}

	@Override
	public void setPitch(int p) {
		pitch = p;
	}
	
	public String getPlayText() {
		return play_text;
	}
	
	public int getVolume() {
		return volume;
	}
	
	public int getRate() {
		return rate;
	}
	
	public int getPitch() {
		return pitch;
	}

}
