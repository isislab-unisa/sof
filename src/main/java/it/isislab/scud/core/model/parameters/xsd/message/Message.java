package it.isislab.scud.core.model.parameters.xsd.message;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

@XmlRootElement
public class Message implements Comparable<Message>{

    private Date created_date=null;
	private String message="";
	private Date send_date =null;
	private Date processed_date=null;
	private String id=null;
	
	@XmlTransient
	public static String STOP_MESSAGE = "stop";
	
	@XmlElement(required=true, name="id")
	public String getId() {
		return id;
	}
	
	@XmlElement(required=true, name="createDate")
	public Date getCreated_date() {
		return created_date;
	}
	@XmlElement(required=true, name="message")
	public String getMessage() {
		return message;
	}

	@XmlElement(required=true, name="sendDate")
	public Date getSend_date() {
		return send_date;
	}
	@XmlElement(required=true, name="processedDate")
	public Date getProcessed_date() {
		return processed_date;
	}



	public void setId(String id) {
		this.id = id;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public void setSend_date(Date send_date) {
		this.send_date = send_date;
	}


	public void setProcessed_date(Date processed_date) {
		this.processed_date = processed_date;
	}
	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	@XmlTransient
	@Override
	public int compareTo(Message m) {
		// TODO Auto-generated method stub
		return send_date.compareTo(m.send_date);
	}	



	public static void main(String[] args) throws Exception {

		JAXBContext domain = JAXBContext.newInstance(Message.class);

		domain.generateSchema(new SchemaOutputResolver() {
			@Override
			public Result createOutput(String namespaceUri, String suggestedFileName)
					throws IOException {
				FileOutputStream message=new FileOutputStream(new File("/home/michele/Scrivania/message.xsd"));
				StreamResult result = new StreamResult(message);
				result.setSystemId(suggestedFileName);
				return result;
			}
		});
	}




}
