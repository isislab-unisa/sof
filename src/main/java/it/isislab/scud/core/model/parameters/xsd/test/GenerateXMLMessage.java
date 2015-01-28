package it.isislab.scud.core.model.parameters.xsd.test;

import it.isislab.scud.core.model.parameters.xsd.message.Message;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class GenerateXMLMessage {
public static void main(String[] args) throws JAXBException {
	
	Message mex=new Message();
	mex.setMessage("prova");
    JAXBContext context= JAXBContext.newInstance(it.isislab.scud.core.model.parameters.xsd.message.Message.class);
    Marshaller jaxbMarshaller = context.createMarshaller();
    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    jaxbMarshaller.marshal(mex, System.out);
	
}
}
