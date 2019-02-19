package org.jain.sarthak.messenger.database;

import java.util.HashMap;
import java.util.Map;

import org.jain.sarthak.messenger.model.Message;
import org.jain.sarthak.messenger.model.Profile;

public class DatabaseClass {
		
	//Map to map id to message
	private static Map<Long,Message> messages=new HashMap<>();
	//to map id to author
	private static Map<String,Profile> profiles=new HashMap<>();
	
	static
	{
		messages.put(1L,new Message(1,"First Message","SJ"));
		messages.put(2L,new Message(2,"Second Message","SJA"));
		profiles.put("sjain", new Profile(1L,"sjain","sarthak","jain"));
	}
	
	//any class can access the entire message and profiles
	//by calling the two below methods
	
	public static Map<Long,Message> getMessages(){
		return messages;
	}
	public static Map<String,Profile> getProfiles(){
		return profiles;
	}
}
