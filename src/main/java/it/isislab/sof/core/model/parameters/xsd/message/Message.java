package it.isislab.sof.core.model.parameters.xsd.message;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

@XmlRootElement
public class Message implements Comparable<Message>{

    private Date created_date=null;
	private String message="";
	private String id=null;
	
	@XmlTransient
	public static final String STOP_MESSAGE = "stop";
	
	
	public Message() {
		created_date = new Date(System.currentTimeMillis());
	}

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


	public void setId(String id) {
		this.id = id;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	@Override
	public int compareTo(Message m) {
		// TODO Auto-generated method stub
		return created_date.compareTo(m.created_date);
	}	

	public static Message convertXMLToMessage(String xmlFilename){
		JAXBContext context;
		Message m=null;
		try {
			context = JAXBContext.newInstance(Message.class);
			Unmarshaller um = context.createUnmarshaller();
			m = (Message) um.unmarshal(new FileReader(xmlFilename));
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return m;
	}
	
	public static String convertMessageToXml(Message m, String filename){
		JAXBContext context;
		try {
			context = JAXBContext.newInstance(Message.class);
			 Marshaller jaxbMarshaller = context.createMarshaller();
		        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		        File f = new File(filename);
		        jaxbMarshaller.marshal(m, f);
		        return f.getName();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       return null;
		
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
