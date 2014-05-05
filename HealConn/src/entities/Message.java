package entities;

// for caching messages locally
public class Message {
	
	// private instance variables
	private String senderID;
	private String senderName;
	private String recipientID;
	private String subject;
	private String content;
	private String date;
	
	// constructor
	public Message(String senderID, String senderName, String recipientID,
				String subject, String content, String date) {
		this.senderID = senderID;
		this.senderName = senderName;
		this.recipientID = recipientID;
		this.subject = subject;
		this.recipientID = recipientID;
		this.date = date;
	}
	
	// getters
	public String getSenderID() {
		return senderID;
	}
	
	public String getSenderName() {
		return senderName;
	}
	
	public String getRecipientID() {
		return recipientID;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public String getContent() {
		return content;
	}
	
	public String getDate() {
		return date;
	}
}
