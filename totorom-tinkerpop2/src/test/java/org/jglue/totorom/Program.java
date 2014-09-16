package org.jglue.totorom;

public class Program extends FramedVertex {

	public String getName() {
		return getProperty("name");
	}

	public void setName(String name) {
		setProperty("name", name);
	}
	
	public String getLang() {
		return getProperty("lang");
	}

	public void setLang(String lang) {
		setProperty("lang", lang);
	}

}
