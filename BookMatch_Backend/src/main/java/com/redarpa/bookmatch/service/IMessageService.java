package com.redarpa.bookmatch.service;

import java.util.List;

import com.redarpa.bookmatch.dto.Message;

public interface IMessageService {

	public List<Message> listAllMessages(); // List All messages

	public Message saveMessage(Message message); // Save message

	public Message messageById(Long id); // Read message data

	public Message updateMessage(Message message); // Update message

	public void deleteMessage(Long id); // Delete an message
	
}
