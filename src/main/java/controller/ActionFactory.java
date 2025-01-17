package controller;

import admin.action.LoginAdminAction;
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
		System.out.println("ActionFactory");
		System.out.println(path);
		System.out.println(command);
		System.out.println(method);
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
		
		if(method == HttpMethod.POST) {
			System.out.println("CreateQuizAction");
			return new CreateQuizAction();
		}
		
		return action;
	}
	
	private Action getApiAction(String command, HttpMethod method) {
		Action action = null;
		
		if(command.equals("search-code") && method == HttpMethod.POST)
			return new SearchCodeAction();
		
		return action;
	}
	
	private Action getAdminAction(String command, HttpMethod method) {
		Action action = null;
		
		if(command.equals("login") && method == HttpMethod.POST)
			return new LoginAdminAction();
		
		return action;
	}

}