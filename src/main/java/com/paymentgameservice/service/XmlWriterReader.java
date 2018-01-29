package com.paymentgameservice.service;

import com.paymentgameservice.domain.Request;
import com.paymentgameservice.dto.Response;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

@Component
public class XmlWriterReader {
    static final String PACKAGEREQUEST = Request.class.getPackage().getName();
    static final String PACKAGERESPONSE = Response.class.getPackage().getName();


    //маршалинг объекта в XML
    public String write(@NonNull Request request) {

        try (StringWriter stringWriter = new StringWriter()) {
            JAXBContext jc = JAXBContext.newInstance(PACKAGEREQUEST);
            Marshaller m = jc.createMarshaller();
//            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(request, stringWriter);

            return stringWriter.toString();

        } catch (JAXBException | IOException  e) {
            e.printStackTrace();
        }
        return null;
    }

    //демаршалинг
    public Response read(@NonNull String responseString){
        try (StringReader stringReader = new StringReader(responseString)) {
            JAXBContext jc = JAXBContext.newInstance(PACKAGERESPONSE);
            Unmarshaller um = jc.createUnmarshaller();
            Response response = (Response) um.unmarshal(stringReader);
            return response;

        } catch (JAXBException e) {
            e.printStackTrace();
        } finally {

        }
        return null;
    }

}
