package core.model.parameters.xsd.elements;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Michele Carillo, Francesco Raia, Flavio Serrapica, Carmine Spagnuolo 
 */
@XmlRootElement
@XmlType(propOrder = { "name","one_shot", "note","toolkit" })
public class Simulation_old {
	
	private boolean one_shot;
	private String name;
	private String description;
	private String author;
	private String toolkit;
	
	private static String type_toolkit_mason = "mason";
	private static String type_toolkit_netlogo = "netlogo";
	private static String type_toolkit_other = "other";
	
	@XmlElement(required=true, name="loop")
	public Boolean isone_shot() {return one_shot;}
	public void modifyone_shot(Boolean one_shot) {this.one_shot = one_shot;}
	
	@XmlElement(required=true)
	public String getname() {return name;}
	public void setname(String name) {this.name = name;}
	
	@XmlElement(required=true)
	public String getdescription() {return description;}
	public void setnote(String note) {this.description = note;}
	
	@XmlAttribute
	public String getauthor() {return author;}
	public void setauthor(String author) {this.author = author;}
	
	@XmlElement(required=true)
	public String gettoolkit() {return toolkit;}
	public void settoolkit(String toolkit) {this.toolkit = toolkit;}
	
	@XmlTransient
	public String gettype_toolkit_mason() {return type_toolkit_mason;}
	
	@XmlTransient
	public String gettype_toolkit_netlogo() {return type_toolkit_netlogo;}
	
	@XmlTransient
	public String gettype_toolkit_other() {return type_toolkit_other;}
		
}	
