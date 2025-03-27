package encodingstrategies;

public class AtBashEncoding extends TemplateEncoding{
	private String mode = "AtBash";
	
	@Override
	public char mapcharacter(char c) {
		int ascii = (int) c;
		if((ascii <= 90) && (ascii >= 65)) {
			ascii = 'A' + 'Z' - ascii; 
		} else if((ascii <= 122) && (ascii >= 97)) {
			ascii = 'a' + 'z' - ascii; 
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
