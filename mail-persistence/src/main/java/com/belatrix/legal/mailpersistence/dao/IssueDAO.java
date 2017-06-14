package com.belatrix.legal.mailpersistence.dao;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.bson.Document;

import com.belatrix.legal.mailpersistence.dto.IssueDTO;
import com.belatrix.legal.mailpersistence.exception.MailPersistenceServiceException;
import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class IssueDAO implements IIssueDAO {

	private final static Logger LOGGER = Logger.getLogger(IssueDAO.class);

	private MongoClient mongoClient;
	private MongoDatabase database;

	private MongoClient getMongoClient() {

		if (mongoClient == null) {
			mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
		}
		return mongoClient;
	}

	private MongoDatabase getDataBase() {

		if (database == null) {
			database = getMongoClient().getDatabase("mail_finder");
		}
		return database;
	}

	public void createIssue(IssueDTO issue) throws MailPersistenceServiceException {

		Document document = new Document("id", issue.getId()).append("ticket_key", issue.getKey())
				.append("date", issue.getDate()).append("email", issue.getEmail());
		try {
			MongoCollection<Document> collection = getDataBase().getCollection("issues");
			collection.insertOne(document);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new MailPersistenceServiceException(e.getMessage(), e);
		}
	}

	public IssueDTO getIssueByTicketKey(String ticket) throws MailPersistenceServiceException {

		LOGGER.trace("Getting Issues By Ticket Key: " + ticket);

		MongoCollection<Document> collection = null;
		FindIterable<Document> findIterable = null;
		
		try {
			collection = getDataBase().getCollection("issues");
			findIterable = collection.find(eq("ticket_key", ticket));

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new MailPersistenceServiceException(e.getMessage(), e);
		}
		
		IssueDTO dto = new IssueDTO();
		
		if (findIterable != null) {
			String document = findIterable.first().toJson();
			
			Gson gson = new Gson();
			dto = gson.fromJson(document, IssueDTO.class);
		}

		return dto;
	}

	public List<IssueDTO> getAllIssues() throws MailPersistenceServiceException {

		LOGGER.trace("Getting All Issues");

		FindIterable<Document> findIterable = null;
		
		try {
			
			MongoCollection<Document> collection = getDataBase().getCollection("issues");
			findIterable = collection.find();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new MailPersistenceServiceException(e.getMessage(), e);
		}
		
		List<IssueDTO> list = new ArrayList<>();

		if (findIterable != null) {
			for (Document doc : findIterable) {
				String json = doc.toJson();
				Gson gson = new Gson();
				IssueDTO dto = gson.fromJson(json, IssueDTO.class);
				list.add(dto);
			}
		}

		return list;
	}

	public void deleteIssueByTicketKey(String ticket) throws MailPersistenceServiceException {

		LOGGER.trace("Delete Issue with ticket: " + ticket);

		try {
			MongoCollection<Document> collection = getDataBase().getCollection("issues");
			collection.deleteOne(eq("ticket_key", ticket));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new MailPersistenceServiceException(e.getMessage(), e);
		}
	}

	public void updateIssueByTicketKey(String ticket, IssueDTO issue) throws MailPersistenceServiceException {

		LOGGER.trace("Update Issue With Ticket Key: " + ticket);

		Document document = new Document("id", issue.getId()).append("ticket_key", issue.getKey())
				.append("date", issue.getDate()).append("email", issue.getEmail());

		try {
			MongoCollection<Document> collection = getDataBase().getCollection("issues");
			collection.updateOne(eq("ticket_key", ticket), new Document("$set", document));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new MailPersistenceServiceException(e.getMessage(), e);
		}
	}

}
