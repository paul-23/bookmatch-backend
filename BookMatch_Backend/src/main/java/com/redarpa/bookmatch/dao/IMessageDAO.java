package com.redarpa.bookmatch.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.redarpa.bookmatch.dto.Message;

public interface IMessageDAO extends JpaRepository<Message, Long> {

}
