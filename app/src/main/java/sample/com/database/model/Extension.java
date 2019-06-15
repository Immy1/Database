package sample.com.database.model;

public class Extension {
	public Extension(String context) {
		this.context = context;
	}

	public Extension() {
	}

	int phoneContactId;
	String context;

	public Extension(int phoneContactId, String context) {
		this.phoneContactId = phoneContactId;
		this.context = context;
	}

	public int getPhoneContactId() {
		return phoneContactId;
	}

	public void setPhoneContactId(int phoneContactId) {
		this.phoneContactId = phoneContactId;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}
}
