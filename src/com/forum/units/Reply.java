package com.forum.units;

public class Reply extends AbstractEntity {

	//reply body
	private String message;
	//user who replied to the question
	private User user;
	//question for which user has replied
	private Question question;
	//id of the last reply posted on discussion forum
	private static Long lastEntry = 0L;

	public void autoGenerateId() {
		lastEntry = lastEntry + 1L;
		super.setId(lastEntry);
		
	}

	/**
	 * This method returns the reply body
	 *
	 * @return the reply body
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * This method sets the body of the reply
	 *
	 * @param message: the message that we want to set as the reply body
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * This method returns the user who wrote the reply
	 *
	 * @return the user who wrote this reply
	 */
	public User getUser() {
		return this.user;
	}

	/**
	 * This method sets the user who wrote the reply
	 *
	 * @param user: the user who wrote this reply
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * This method returns the question that this reply belongs to
	 *
	 * @return the question to which this reply belongs to
	 */
	public Question getQuestion() {
		return this.question;
	}

	/**
	 * This method sets the question that this reply belongs to
	 *
	 * @param question: the question that this reply belongs to
	 */
	public void setQuestion(Question question) {
		this.question = question;
	}
}
