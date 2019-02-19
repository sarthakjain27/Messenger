package org.jain.sarthak.messenger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.jain.sarthak.messenger.database.DatabaseClass;
import org.jain.sarthak.messenger.exception.DataNotFoundException;
import org.jain.sarthak.messenger.model.Message;

public class MessageService {
	
	//getMessages is static so our response will be synced.
	//multiple operations is going to give back a consistent result
	private Map<Long,Message> messages=DatabaseClass.getMessages();
	
	public MessageService()
	{
		
	}
	
	public List<Message> getAllMessages(){
		return new ArrayList<Message>(messages.values());
	}
	
	public List<Message> getAllMessagesForYear(int year)
	{
		List<Message> ll=new ArrayList<>();
		Calendar cal=Calendar.getInstance();
		for(Message each:messages.values())
		{
			cal.setTime(each.getCreated());
			if(cal.get(Calendar.YEAR)==year)
				ll.add(each);
		}
		return ll;
	}
	
	public List<Message> getAllMessagesPaginated(int start,int size)
	{
		List<Message> ll=new ArrayList<>(messages.values());
		if(start+size>ll.size()) return new ArrayList<Message>();
		return ll.subList(start, start+size);
	}
	
	public Message getMessage(long id)
	{
		Message message=messages.get(id);
		if(message==null)
			throw new DataNotFoundException("Message with id "+id+" not found");
		return message;
	}
	
	public Message addMessage(Message message)
	{
		message.setId(messages.size()+1);
		messages.put(message.getId(),message);
		return message;
	}
	
	public Message updateMessage(Message message)
	{
		if(message.getId()<=0)
			return null;
		messages.put(message.getId(),message);
		return message;
	}
	
	public Message removeMessage(long id)
	{
		return messages.remove(id);
	}
	
}
