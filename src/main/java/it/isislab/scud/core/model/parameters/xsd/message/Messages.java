package it.isislab.scud.core.model.parameters.xsd.message;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public class Messages {
    @XmlElement(name="message")
	private ArrayList<Message> messages;
	
    public Messages() {
    	messages=new ArrayList<Message>();
	}
    
	public boolean addMessage(Message mex){
		return messages.add(mex);
	}
	public ArrayList<Message> getMessages(){
		return messages;
	}
	
	
}
