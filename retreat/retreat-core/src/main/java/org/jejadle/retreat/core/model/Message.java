package org.jejadle.retreat.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Message extends BaseEntity {

	@Id @GeneratedValue
	int messageId;
	
	@Column(length=20, nullable=false)
	String receiveNumber;
	
	@Column(length=20, nullable=false)
	String sendNumber;
	
	@Column(length=160, nullable=false)
	String content;
	
	@Column(length=10, nullable=false)
	String httpStatus;
	
	@Column(length=200, nullable=true)
	String result;
	
	@Column(length=20, nullable=true)
	String senderName;
	
	int retreatId=0;
	
	public Message(){
		
	}
	
	public Message(String sendNumber, String senderName, String content,String receiveNumber){
		this.sendNumber=sendNumber;
		this.senderName=senderName;
		this.content=content;
		this.receiveNumber=receiveNumber;
	}

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public String getReceiveNumber() {
		return receiveNumber;
	}

	public void setReceiveNumber(String receiveNumber) {
		this.receiveNumber = receiveNumber;
	}

	public String getSendNumber() {
		return sendNumber;
	}

	public void setSendNumber(String sendNumber) {
		this.sendNumber = sendNumber;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(String httpStatus) {
		this.httpStatus = httpStatus;
	}
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public int getRetreatId() {
		return retreatId;
	}

	public void setRetreatId(int retreatId) {
		this.retreatId = retreatId;
	}
	
	
	
	
}
