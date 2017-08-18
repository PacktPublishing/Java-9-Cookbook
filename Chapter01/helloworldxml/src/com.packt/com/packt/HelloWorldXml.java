package com.packt;

import java.io.StringWriter;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class HelloWorldXml{
  public static void main(String[] args) throws JAXBException{
    //create instance of JAXBContext with the class we want to serialize into XML
    JAXBContext jaxb = JAXBContext.newInstance(Messages.class);

    //create a marshaller which will do the task of generating xml
    Marshaller marshaller = jaxb.createMarshaller();

    //setting the property of marshaller to not add the <? xml> tag
    marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

    StringWriter writer = new StringWriter();

    //serialze the Messages instance and send the string to the writer
    marshaller.marshal(new Messages(), writer);

    //get the XML from the writer
    System.out.println(writer.toString());
  }

}

/**
The class whose instance is serialized into XML
*/
//becomes XML root tag with tagname - <messages>
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class Messages{
	//becomes an xml tag with tagname - <message>, with the value being tag content
    @XmlElement public final String message = "Hello World in XML";
}
