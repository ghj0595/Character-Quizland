package controller;

import admin.action.*;
import quiz.action.*;
import user.action.*;
import util.HttpMethod;

public class ActionFactory {
	
	private ActionFactory() {
	}
	
	private static ActionFactory instance = new ActionFactory();
	
	public static ActionFactory getInstance() {
		return instance;
	}
	
	public Action getAction(String path, String command, HttpMethod method) {
		Action action = null;

		if(path == null || command == null)
			return action;

		if(path.equals("users"))
			return getUserAction(command, method);
		else if(path.equals("quiz"))
			return getQuizAction(command, method);
		else if(path.equals("api"))
			return getApiAction(command, method);
		else if(path.equals("admin"))
			return getAdminAction(command, method);
		return action;
	}
	
	private Action getUserAction(String command, HttpMethod method) {
		Action action = null;
		
		if(command.equals("login") && method == HttpMethod.POST)
			return new LoginAction();
		else if(command.equals("join") && method == HttpMethod.POST)
			return new JoinAction();
		else if(command.equals("logout") && method == HttpMethod.GET)
			return new LogoutAction();
		else if(command.equals("update") && method == HttpMethod.POST)
			return new UpdateAction();
		else if(command.equals("delete") && method == HttpMethod.POST)
			return new DeleteAction();
		else if(command.equals("view") && method == HttpMethod.GET)
			return new ViewAction();
		
		return action;
	}
	
	private Action getQuizAction(String command, HttpMethod method) {
		Action action = null;
		
		if(command.equals("create") && method == HttpMethod.POST) {
			return new CreateQuizAction();
		}else if(command.equals("result") && method == HttpMethod.POST) {
			return new QuizResultAction();
		}else if(command.equals("total") && method == HttpMethod.GET) {
			return new TotalResultAction();
		}
		
		return action;
	}
	
	private Action getApiAction(String command, HttpMethod method) {
		Action action = null;
		
		if(command.equals("search-code") && method == HttpMethod.POST)
			return new SearchCodeAction();
		else if(command.equals("users-count") && method == HttpMethod.GET)
			return new GetDailyUsers();
		
		return action;
	}
	
	private Action getAdminAction(String command, HttpMethod method) {
		Action action = null;
		
		if(command.equals("login") && method == HttpMethod.POST)
			return new LoginAdminAction();
		else if(command.equals("logout") && method == HttpMethod.GET)
			return new LogoutAdminAction();
		
		return action;
	}

}