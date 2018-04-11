package com.university.service;

import java.util.List;

import com.university.dao.CommentDao;
import com.university.model.Comment;

import Exception.ServiceException;

public class CommentService {
	
	private CommentDao commentDao;

	public CommentService() {
		commentDao = CommentDao.getInstance();
	}
	
	public List<Comment> getCometnsUerCommented(String email) throws ServiceException {
		return commentDao.getCometnsUerCommented(email);
	}
	
	public List<Comment> getCometnsUerComment(String email) throws ServiceException {
		return commentDao.getCometnsUerComment(email);
	}
}
