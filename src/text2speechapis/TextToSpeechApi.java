package text2speechapis;

public interface TextToSpeechApi {
	
	public void play(String s);
	public void setVolume(int v) ;
	public void setRate(int r);
	public void setPitch(int p);
}
