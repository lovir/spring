package hello;

public class AutocatRespose {
	private Header header;
	private AutocatList body;
	
	public AutocatRespose (Header header, AutocatList body){
		this.header = header;
		this.body = body;
	}
	
	public Header getHeader(){
		return header;
	}
	
	public AutocatList getBody(){
		return body;
	}

}
