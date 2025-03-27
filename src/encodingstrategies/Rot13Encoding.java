package encodingstrategies;

public class Rot13Encoding extends TemplateEncoding{
	private String mode = "Rot13";
	
	@Override
	public char mapcharacter(char c) {
		int ascii = (int) c;
		if((ascii <= 'Z') && (ascii >= 'A')) {
			if(ascii + 13 <= 'Z') {
				ascii += 13;
			} else {
				ascii -= 13;
			}
		} else if((ascii <= 'z') && (ascii >= 'a')) {
			if(ascii + 13 <= 'z') {
				ascii += 13;
			} else {
				ascii -= 13;
			}
		}
		c = (char) ascii;
		return c;
	}
	
	public String encode(String content) {
		String ret = "";
		ret = super.encode(content);
		return ret;
	}
	
	public String getMode() {
		return this.mode;
	}
}
