package com.redarpa.bookmatch.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.redarpa.bookmatch.dto.Message;
import com.redarpa.bookmatch.service.MessageServiceImp;

/**
 * @author RedArpa - BookMatch
 *
 */

@CrossOrigin(origins = "*", maxAge = 3600) // Allows requests from all origins with 1 hour cache
@RestController
@RequestMapping("/api")
public class MessageController {

	@Autowired
	MessageServiceImp messageServiceImp;

	@GetMapping("/messages")
	public List<Message> listMessages() {
		return messageServiceImp.listAllMessages();
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

	@DeleteMapping("/messages/{id}")
	public void deleteMessage(@PathVariable(name = "id") Long id) {
		messageServiceImp.deleteMessage(id);
	}
}
