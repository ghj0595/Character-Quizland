package controller;

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
		
		return action;
	}

}