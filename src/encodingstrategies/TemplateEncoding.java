package encodingstrategies;

public abstract class TemplateEncoding implements EncodingStrategy {
	
	@Override
	public String encode(String content) {
		char character;
		String newcontent = "";
		for(int i = 0; i < content.length(); i++) { 
			character = content.charAt(i);
			character = mapcharacter(character);
			newcontent += character;			
		}
		return newcontent;
	}
	
	public abstract char mapcharacter(char c);
}
