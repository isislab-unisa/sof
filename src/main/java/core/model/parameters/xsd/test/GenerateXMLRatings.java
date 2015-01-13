package core.model.parameters.xsd.test;

import java.io.IOException;

import javax.xml.bind.JAXBException;

/**
 * @author Michele Carillo, Francesco Raia, Flavio Serrapica, Carmine Spagnuolo 
 */
public class GenerateXMLRatings {
	public static void main(String[] args) throws JAXBException, IOException 
    {
        
//        Ratings ratings=new Ratings();
//        ratings.id="prova";
//        ArrayList<Rating> ra_list=new ArrayList<Rating>();
//        
//        
//     
//        Input i1 =new Input();
//        i1.id="prova-input1";
//        
//        ParameterLong plong=new ParameterLong();
//        plong.setvalue(20);
//        
//        ParameterDouble pdouble=new ParameterDouble();
//        pdouble.setvalue(21.2);
//        
//        ParameterString pstring=new ParameterString();
//        pstring.setvalue("test1");
//        
//        ParameterDouble pdoublefix=new ParameterDouble();
//        pdoublefix.setvalue(0.1);
//        
//        Parameter p11=new Parameter();
//        p11.setparam(plong);
//        p11.setvariable_name("giorni");
//        
//        Parameter p12=new Parameter();
//        p12.setparam(pdouble);
//        p12.setvariable_name("peso");
//        
//        Parameter p13=new Parameter();
//        p13.setparam(pstring);
//        p13.setvariable_name("persona");
//        
//        Parameter p14=new Parameter();
//        p14.setparam(pdoublefix);
//        p14.setvariable_name("indice");
//        
//        
//        ArrayList<Parameter> parainput1=new ArrayList<Parameter>();
//        parainput1.add(p11);
//        parainput1.add(p12);
//        parainput1.add(p13);
//        parainput1.add(p14);
//        
//        i1.param_element=parainput1;
//        
//        Output output=new Output();
//        
//        output.id="prova-output1";
//        output.inputparameters=i1;
//        
//        ArrayList<Parameter> paraout=new ArrayList<Parameter>();
//        
//        ParameterDouble poutdouble=new ParameterDouble();
//        poutdouble.setvalue(99.9);
//        
//        Parameter pout=new Parameter();
//        pout.setparam(poutdouble);
//        pout.setvariable_name("outputsimulazione");
//        
//        paraout.add(pout);
//        output.outputparams=paraout;
//        
//        
//        Rating r1=new Rating();
//        r1.id="prova";
//        r1.input=i1;
//        r1.output=output;
//        
//        ArrayList<Parameter> ratingvalue=new ArrayList<Parameter>();
//        
//        ParameterDouble pratedouble=new ParameterDouble();
//        pratedouble.setvalue(1.1);
//        
//        Parameter prate=new Parameter();
//        prate.setparam(pratedouble);
//        prate.setvariable_name("rate1simulazione");
//        
//        ratingvalue.add(prate);
//        
//        ParameterLong pratelong=new ParameterLong();
//        pratelong.setvalue(100000);
//        
//        Parameter pralong=new Parameter();
//        pralong.setparam(pratelong);
//        pralong.setvariable_name("rate2simulazione");
//        
//        ratingvalue.add(pralong);
//        
//        ParameterString pratestring=new ParameterString();
//        pratestring.setvalue("hellloooo");
//        
//        Parameter prastring=new Parameter();
//        prastring.setparam(pratestring);
//        prastring.setvariable_name("rate2simulazione");
//        
//        ratingvalue.add(prastring);
//        
//        
//        r1.rates=ratingvalue;
//        
//        ra_list.add(r1);
//        ratings.ratings=ra_list;
//        
//
//        File f = new File("test/ratings/ratings.xml");
//        JAXBContext context= JAXBContext.newInstance(it.isislab.soffy.model.parameters.xsd.rating.Ratings.class);
//        Marshaller jaxbMarshaller = context.createMarshaller();
//        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//
//        jaxbMarshaller.marshal(ratings, f);
//        jaxbMarshaller.marshal(ratings, System.out);
//
//        
//        Ratings ratings=new Ratings();
//        ratings.id="prova";
//        ArrayList<Rating> ra_list=new ArrayList<Rating>();
//        
//        
//     
//        Input i1 =new Input();
//        i1.id="prova-input1";
//        
//        ParameterLong plong=new ParameterLong();
//        plong.setvalue(20);
//        
//        ParameterDouble pdouble=new ParameterDouble();
//        pdouble.setvalue(21.2);
//        
//        ParameterString pstring=new ParameterString();
//        pstring.setvalue("test1");
//        
//        ParameterDouble pdoublefix=new ParameterDouble();
//        pdoublefix.setvalue(0.1);
//        
//        Parameter p11=new Parameter();
//        p11.setparam(plong);
//        p11.setvariable_name("giorni");
//        
//        Parameter p12=new Parameter();
//        p12.setparam(pdouble);
//        p12.setvariable_name("peso");
//        
//        Parameter p13=new Parameter();
//        p13.setparam(pstring);
//        p13.setvariable_name("persona");
//        
//        Parameter p14=new Parameter();
//        p14.setparam(pdoublefix);
//        p14.setvariable_name("indice");
//        
//        
//        ArrayList<Parameter> parainput1=new ArrayList<Parameter>();
//        parainput1.add(p11);
//        parainput1.add(p12);
//        parainput1.add(p13);
//        parainput1.add(p14);
//        
//        i1.param_element=parainput1;
//        
//        Output output=new Output();
//        
//        output.id=1;
//        output.inputparameters=i1;
//        
//        ArrayList<Parameter> paraout=new ArrayList<Parameter>();
//        
//        ParameterDouble poutdouble=new ParameterDouble();
//        poutdouble.setvalue(99.9);
//        
//        Parameter pout=new Parameter();
//        pout.setparam(poutdouble);
//        pout.setvariable_name("outputsimulazione");
//        
//        paraout.add(pout);
//        output.outputparams=paraout;
//        
//        
//        Rating r1=new Rating();
//        r1.id="prova";
//        r1.input=i1;
//        r1.output=output;
//        
//        ArrayList<Parameter> ratingvalue=new ArrayList<Parameter>();
//        
//        ParameterDouble pratedouble=new ParameterDouble();
//        pratedouble.setvalue(1.1);
//        
//        Parameter prate=new Parameter();
//        prate.setparam(pratedouble);
//        prate.setvariable_name("rate1simulazione");
//        
//        ratingvalue.add(prate);
//        
//        ParameterLong pratelong=new ParameterLong();
//        pratelong.setvalue(100000);
//        
//        Parameter pralong=new Parameter();
//        pralong.setparam(pratelong);
//        pralong.setvariable_name("rate2simulazione");
//        
//        ratingvalue.add(pralong);
//        
//        ParameterString pratestring=new ParameterString();
//        pratestring.setvalue("hellloooo");
//        
//        Parameter prastring=new Parameter();
//        prastring.setparam(pratestring);
//        prastring.setvariable_name("rate2simulazione");
//        
//        ratingvalue.add(prastring);
//        
//        
//        r1.rates=ratingvalue;
//        
//        ra_list.add(r1);
//        ratings.ratings=ra_list;
//        
//
//        File f = new File("test/ratings/ratings.xml");
//        JAXBContext context= JAXBContext.newInstance(it.isislab.soffy.model.parameters.xsd.rating.Ratings.class);
//        Marshaller jaxbMarshaller = context.createMarshaller();
//        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//
//        jaxbMarshaller.marshal(ratings, f);
//        jaxbMarshaller.marshal(ratings, System.out);
    }
}
