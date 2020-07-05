package com.forum.units;

public class Question extends AbstractEntity {

	//brief description of question
	private String title;
	//detail question
	private String message;
	//user who asked the question
	private User user;
	//number of upvotes for the question.
	private int upvoteCount = 0;
	//id of the last question asked on discussion forum
	private static Long lastEntry = 0L;

	public void autoGenerateId() {
		lastEntry = lastEntry + 1L;
		super.setId(lastEntry);
	}

	/**
	 * This method returns the title of the question
	 *
	 * @return the title of the question
	 */
	public String getTitle() {
		return this.title;
	}


	/**
	 * This method sets the title of the question
	 *
	 * @param title: the title that we want to give to this question
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * This method returns the details of this question
	 *
	 * @return details of the question
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * This method sets the message of the question
	 *
	 * @param message: the message that we want to set the details of this question to
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * This method returns the user who wrote this question
	 *
	 * @return user who wrote this question
	 */
	public User getUser() {
		return this.user;
	}

	/**
	 * This method sets the user who wrote this question
	 *
	 * @param user: the user who wrote this question
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * This method returns the number of upvotes this question has received
	 *
	 * @return the number of upvotes this question has received
	 */
	public int getUpvoteCount() {
		return this.upvoteCount;
	}

	/**
	 * This question increases the number of upvotes by one
	 */
	public void increaseUpvoteCount() {
		this.upvoteCount++;
	}

}
