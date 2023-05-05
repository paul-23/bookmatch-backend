package com.redarpa.bookmatch.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.redarpa.bookmatch.dto.Book;
import com.redarpa.bookmatch.dto.Message;
import com.redarpa.bookmatch.service.BookServiceImp;
import com.redarpa.bookmatch.service.MessageServiceImp;

public class MessageController {

	@Autowired
	MessageServiceImp messageServiceImp;

	@GetMapping("/messages")
	public List<Message> listMessages() {
		return messageServiceImp.list();
	}

	@PostMapping("/messages")
	public Message saveMessage(@RequestBody Message message) {
		return messageServiceImp.saveMessage(message);
	}

	@GetMapping("/messages/{id}")
	public Message messageById(@PathVariable(name = "id") Long id) {

		Message messageById = new Message();

		messageById = messageServiceImp.messageById(id);

		System.out.println("Message by Id: " + messageById);

		return messageById;
	}

	@PutMapping("/messages/{id}")
	public Message updateMessage(@PathVariable(name = "id") Long id, @RequestBody Message message) {

		Message selectedMessage = new Message();
		Message updatedMessage = new Message();

		selectedMessage = messageServiceImp.messageById(id);

		selectedMessage.setContent(message.getContent());
		selectedMessage.setId_book(message.getId_book());
		selectedMessage.setId_user_origin(message.getId_user_origin());
		selectedMessage.setId_user_destiny(message.getId_user_destiny());

		updatedMessage = messageServiceImp.updateMessage(selectedMessage);

		System.out.println("Updated message is: " + updatedMessage);

		return updatedMessage;
	}

	@DeleteMapping("/messages/{id}")
	public void deleteMessage(@PathVariable(name = "id") Long id) {
		messageServiceImp.deleteMessage(id);
	}
}
