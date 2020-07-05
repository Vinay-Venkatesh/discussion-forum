package discussion.forum.activiy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.forum.main.DiscussionForum;
import com.forum.units.Question;
import com.forum.units.Reply;
import com.forum.units.User;
import com.forum.units.UserRole;
import com.forum.util.Utility;

import discussion.forum.units.service.QuestionService;
import discussion.forum.units.service.QuestionServiceImpl;
import discussion.forum.units.service.ReplyService;
import discussion.forum.units.service.ReplyServiceImpl;
import discussion.forum.units.service.UpvoteService;
import discussion.forum.units.service.UpvoteServiceImpl;
import discussion.forum.units.service.UserService;
import discussion.forum.units.service.UserServiceImpl;

public class UserActivity {
	public UserService userService;
	public QuestionService questionService;
	public ReplyService replyService;
	public UpvoteService upvoteService;

	public UserActivity() {
		userService = new UserServiceImpl();
		questionService = new QuestionServiceImpl();
		replyService = new ReplyServiceImpl();
		upvoteService = new UpvoteServiceImpl();
	}

	public User loginActivity() throws IOException {
		System.out.println("Welcome to discussion forum login");
		System.out.println("Enter your username : ");
		String username = Utility.inputFromUser();
		System.out.println("Enter your password : ");
		String password = Utility.inputFromUser();
		User user = userService.getUser(username, password);
		if (user != null) {
			return user;
		}
		System.out.println("You do not have the account. Request admin to get account in discussion forum");
		return null;
	}

	public void createNewUser() throws IOException {
		System.out.println("Enter username : ");
		String username = Utility.inputFromUser();
		System.out.println("Enter password : ");
		String password = Utility.inputFromUser();
		System.out.println("Enter email : ");
		String email = Utility.inputFromUser();
		System.out.println("What role : ");
		UserRole role = DiscussionForum.roleFromMenu();
		userService.createUser(username, password, email, role);
	}

	/**
	 * Ask the user to enter a new question
	 *
	 * @param user: the user who is asking the question
	 * @throws IOException
	 */
	public void postNewQuestion(User user) throws IOException {
		
		String message = null;
		// ask user to write the question title
		System.out.println("Enter question title : ");
		String title = Utility.inputFromUser();

		// ask user to write the question body
		System.out.println("Enter question : ");
		message = Utility.inputFromUser();

		questionService.createQuestion(title, message, user);
	}

	/**
	 * This method returns all of the questions that are stored in the class forum
	 *
	 * @param userActivity: the activity history of the user who is viewing this question
	 * @param user: the user who is viewing this question
	 *
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public void seeAllQuestions(UserActivity userActivity, User user) throws NumberFormatException, IOException {
		ArrayList<Question> questions = QuestionServiceImpl.questions; // getting all the questions
		//Check if there are any questions
		if ( questions.size() == 0 ) {
			System.out.println("No question posted yet");
		} else {
			sort(questions); // sorting the questions from the newest to the oldest
			for (Question question : questions) { // printing out information about each question
				System.out.println(question.getId() + ". Question Title - " + question.getTitle());
				System.out.println("Question - " + question.getMessage());
				System.out.println("Upvote - " + question.getUpvoteCount());
			}
			DiscussionForum.questionMenu(userActivity, user);
		}
	}

	public void sort(ArrayList<Question> questions) {
		Collections.sort(questions, new Comparator<Question>() {
			public int compare(Question q1, Question q2) {
				if (q1.getUpvoteCount() == q2.getUpvoteCount())
					return 0;

				return q1.getUpvoteCount() < q1.getUpvoteCount() ? 1 : -1;
			}
		});
	}

	public void upvoteQuestion(User user) throws NumberFormatException, IOException {
		System.out.println("Enter question number you want to upvote : ");
		upvoteService.addUpvote(getQuestion(), user);
	}

	public void replyToQuestion(User user) throws IOException {
		System.out.println("Enter question number you want to reply to : ");
		Question question = getQuestion();
		System.out.println("Post your reply");
		replyService.addReply(Utility.inputFromUser(), question, user);
	}

	/**
	 *
	 * @param userActivity: the user's activity history
	 * @param user: the current logged in user
	 *
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public void deleteQuestion(UserActivity userActivity, User user) throws NumberFormatException, IOException {
		
		System.out.println("Enter question number you want to delete : ");
		
		Question question = getQuestion();
		
		//Admin user can delete any question in the forum
		if(user.getUserRole() == UserRole.ADMIN) {
			questionService.deleteQuestion(question);
			//Moderator user can delete question posted by normal user or if the question is posted by moderator himself
		}else if(user.getUserRole() == UserRole.MODERATOR) {
			if( (question.getUser().getUserRole() == UserRole.USER) || (question.getUser()== user)) {
				questionService.deleteQuestion(question);
			}else {
				System.out.println("You are not authorised to delete this question");
			}
			//normal user can delete the question posted by him and only him..
		}else if(question.getUser() == user) {
			questionService.deleteQuestion(question);
		}else {
			System.out.println("You are not authorised to delete this question");
		}

		if (QuestionServiceImpl.questions.size() == 0)
			DiscussionForum.menu(user, userActivity);
	}

	private Question getQuestion() throws NumberFormatException, IOException {
		Question question;
		while (true) {
			question = questionService.getQuestionById(Long.parseLong(Utility.inputFromUser()));
			if (question != null)
				break;
			System.out.println("Enter correct question from displayed questions");
		}
		return question;
	}

	public void seeAllReplies(UserActivity userActivity, User user) throws NumberFormatException, IOException {
		System.out.println("For which question number you want to see replies : ");
		Question question = getQuestion();
		ArrayList<Reply> replies = replyService.getReplies(question);
		if (replies.size() == 0) {
			System.out.println("No reply posted yet");
		} else {
			for (Reply reply : replies) {
				System.out.println(reply.getId() + ". Comment - " + reply.getMessage());
				System.out.println("Upvote - " + upvoteService.upvoteCount(reply));
			}
			DiscussionForum.replyMenu(userActivity, user, question);
		}
	}

	public void upvoteReply(User user) throws NumberFormatException, IOException {
		System.out.println("Enter reply number you want to upvote : ");
		upvoteService.addUpvote(getReply(), user);
	}

	public void deleteReply(Question question, UserActivity userActivity, User user) throws NumberFormatException, IOException {
		System.out.println("Enter reply number you want to delete : ");
		Reply reply = getReply();
		if (user.getUserRole() == UserRole.ADMIN) {
			replyService.deleteReply(reply);
		} else if (user.getUserRole() == UserRole.MODERATOR) {
			if (reply.getUser().getUserRole() == UserRole.USER) {
				replyService.deleteReply(reply);
			} else if (reply.getUser() == user) {
				replyService.deleteReply(reply);
			} else {
				System.out.println("You are not authorised to delete this reply");
			}
		} else {
			if (reply.getUser() == user) {
				replyService.deleteReply(reply);
			} else {
				System.out.println("You are not authorised to delete this reply");
			}
		}
		if (replyService.getReplies(question).size() == 0)
			DiscussionForum.questionMenu(userActivity, user);

	}

	private Reply getReply() throws NumberFormatException, IOException {
		Reply reply;
		while (true) {
			reply = replyService.getReply(Long.parseLong(Utility.inputFromUser()));
			if (reply != null)
				break;
			System.out.println("Enter correct reply from displayed replies");
		}
		return reply;
	}

}
