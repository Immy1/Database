package sample.com.database.model;

public class Contact {

	int id;
	String contactId;
	String stagingId;

	public Contact() {
	}

	public Contact(int id, String contactId, String stagingId) {
		this.id = id;
		this.contactId = contactId;
		this.stagingId = stagingId;
	}

	public Contact(String contactId, String stagingId) {
		this.contactId = contactId;
		this.stagingId = stagingId;
	}

	public int getId() {

		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	public String getStagingId() {
		return stagingId;
	}

	public void setStagingId(String stagingId) {
		this.stagingId = stagingId;
	}
}
