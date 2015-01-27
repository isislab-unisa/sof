package it.isislab.scud.core.model.parameters.xsd.test;

import it.isislab.scud.core.model.parameters.xsd.message.Message;

import java.io.File;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class GenerateXMLMessage {
public static void main(String[] args) throws JAXBException {
	
	Message mex=new Message();
	mex.setCreated_date(new Date(System.currentTimeMillis()));
	mex.setMessage("piecoro");
	mex.setSend_date(new Date(System.currentTimeMillis()));
	//mex.setStatus("sended");
	
	
	
	
	//File f = new File("/home/michele/Scrivania/message.xml");
    JAXBContext context= JAXBContext.newInstance(it.isislab.scud.core.model.parameters.xsd.message.Message.class);
    Marshaller jaxbMarshaller = context.createMarshaller();
    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    //jaxbMarshaller.marshal(mex, f);
    jaxbMarshaller.marshal(mex, System.out);
	
}
}
