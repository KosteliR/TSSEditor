package encodingstrategies;

public class StrategiesFactory {
	private EncodingStrategy enc;
	
	public EncodingStrategy createStrategy(String encodingstrategy) {
		if(encodingstrategy == "Rot13") {
			enc = new Rot13Encoding();
			return enc;
		} else if (encodingstrategy == "AtBash") {
			enc = new AtBashEncoding();
			return enc;
		}
		return null;
	}
}
