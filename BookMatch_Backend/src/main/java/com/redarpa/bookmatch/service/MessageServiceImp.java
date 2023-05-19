package com.redarpa.bookmatch.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redarpa.bookmatch.dao.IMessageDAO;
import com.redarpa.bookmatch.dto.Message;

@Service
public class MessageServiceImp implements IMessageService {
	
	@Autowired
	IMessageDAO iMessageDAO;

	@Override
	public List<Message> listAllMessages() {
		return iMessageDAO.findAll();
	}

	@Override
	public Message saveMessage(Message message) {
		return iMessageDAO.save(message);
	}

	@Override
	public Message messageById(Long id) {
		return iMessageDAO.findById(id).get();
	}

	@Override
	public Message updateMessage(Message message) {
		return iMessageDAO.save(message);
	}

	@Override
	public void deleteMessage(Long id) {
		iMessageDAO.deleteById(id);
	}
	
	
}
