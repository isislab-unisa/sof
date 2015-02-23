/**
 * 
 * Copyright ISISLab, 2015 Università degli Studi di Salerno.
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  You may not use this file except in compliance with the License. You may obtain a copy of the License at
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and limitations under the License.
 * @author Michele Carillo michelecarillo@gmail.com
 * @author Flavio Serrapica flavioserrapica@gmail.com
 * @author Carmine Spagnuolo spagnuolocarmine@gmail.com
 *
 */
package it.isislab.scud.core.model.parameters.xsd.test;

import it.isislab.scud.core.engine.hadoop.sshclient.utils.simulation.Simulation;
import it.isislab.scud.core.model.parameters.xsd.domain.Domain;
import it.isislab.scud.core.model.parameters.xsd.domain.ParameterDomain;
import it.isislab.scud.core.model.parameters.xsd.domain.ParameterDomainContinuous;
import it.isislab.scud.core.model.parameters.xsd.domain.ParameterDomainDiscrete;
import it.isislab.scud.core.model.parameters.xsd.domain.ParameterDomainListString;
import it.isislab.scud.core.model.parameters.xsd.domain.ParameterDomainListValues;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class GenerateXMLDomain {
	public static void main(String[] args) throws JAXBException, IOException 
    {

        Domain d =new Domain();
        
        Simulation s=new Simulation();
        s.setName("prova");
        s.setAuthor("pippo");
        s.setDescription("test nota");
        s.setLoop(true);
        s.setToolkit("mason");
        d.simulation=s;
       
        ArrayList<ParameterDomain> params=new ArrayList<ParameterDomain>();
        ParameterDomain p1=new ParameterDomain();
        p1.setvariable_name("val1");
        ParameterDomainDiscrete p1d=new ParameterDomainDiscrete();
        p1d.setmin(10);
        p1d.setmax(20);
        p1d.setincrement(10);
        p1.setparameter(p1d);
        params.add(p1);
        
        ParameterDomain p2=new ParameterDomain();
        p2.setvariable_name("val2");
        ParameterDomainContinuous p2d=new ParameterDomainContinuous();
        p2d.setmin(10.1);
        p2d.setmax(25.5);
        p2d.setincrement(1.1);
        p2.setparameter(p2d);
        params.add(p2);
        
        ParameterDomain p3=new ParameterDomain();
        p3.setvariable_name("val3");
        ParameterDomainListString p3d=new ParameterDomainListString();
        p3d.setlist(new ArrayList<String>());
        p3d.getlist().add("test1");
        p3d.getlist().add("test2");
        p3.setparameter(p3d);
        params.add(p3);
        
        ParameterDomain p4=new ParameterDomain();
        p4.setvariable_name("val4");
        ParameterDomainListValues p4d=new ParameterDomainListValues();
        p4d.setlist(new ArrayList<Double>());
        p4d.getlist().add(0.10);
        p4d.getlist().add(1000.10);
        p4.setparameter(p4d);
        params.add(p4);
        
        d.param=params;

        File f = new File("domain.xml");
        JAXBContext context= JAXBContext.newInstance(it.isislab.scud.core.model.parameters.xsd.domain.Domain.class);
        Marshaller jaxbMarshaller = context.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        jaxbMarshaller.marshal(d, f);
        jaxbMarshaller.marshal(d, System.out);
    }
}
