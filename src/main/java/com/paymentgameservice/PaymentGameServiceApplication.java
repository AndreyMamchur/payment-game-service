package com.paymentgameservice;

import com.paymentgameservice.domain.*;
import com.paymentgameservice.dto.Response;
import com.paymentgameservice.service.DoRequest;
import com.paymentgameservice.service.EncryptionDecryptionXml;
import com.paymentgameservice.service.XmlWriterReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import static java.time.ZonedDateTime.of;


@SpringBootApplication
public class PaymentGameServiceApplication implements CommandLineRunner {

	@Autowired
	private XmlWriterReader xmlWriterReader;

	@Autowired
	private EncryptionDecryptionXml encryptionDecryptionXml;

	@Autowired
	private DoRequest doRequest;

	public static void main(String[] args) {
		new SpringApplicationBuilder(PaymentGameServiceApplication.class)
				.run(args);

	}

	@Override
	public void run(String...args) throws Exception {

		//Собираю первый request: verify с одним attribute
		Request request = new Request();
		Verify verify = new Verify(4390, "85");
		request.setVerify(verify);
		Attribute attribute = new Attribute("email", "info@rol.ru");
		List<Attribute> attributes = new ArrayList<>();
		attributes.add(attribute);
		verify.setAttributes(attributes);

		//маршалинг объекта в XML
		String xml = xmlWriterReader.write(request);
		System.out.println();
		System.out.println("----------FIRST REQUEST----------");
		System.out.println(xml);

		//кодировка сообщения
		String signXml = encryptionDecryptionXml.sign(xml);
		System.out.println("----------SIGNATURE----------");
		System.out.println(signXml);
		System.out.println();

		//делаю запрос на сервер. В ответ получаю "Signature error!"
//		String responseString = doRequest.putXml(signXml, xml);
		String responseString = doRequest.postXml(signXml, xml);
		System.out.println("----------FIRST RESPONSE----------");
		System.out.println(responseString);
		System.out.println();

		request.setVerify(null);

		//Собираю второй request: один payment с атрибутом и один без
		Payment payment1 = new Payment(14546, 1000, 17235, 1, "9132345678"
				, of(LocalDateTime.of(2007, 10, 12, 12, 0, 0)
				, ZoneId.of("+0300"))
				, attributes);

		Payment payment2 = new Payment(14547, 1500, 17235, 2, "12345"
				, of(LocalDateTime.of(2007, 10, 12, 12, 0, 0)
				, ZoneId.of("+0300")));

		List<Payment> paymentList = new ArrayList<>();
		paymentList.add(payment1);
		paymentList.add(payment2);
		request.setPaymentList(paymentList);

		//маршалинг объекта в XML
		xml = xmlWriterReader.write(request);
		System.out.println("----------SECOND REQUEST----------");
		System.out.println(xml);
		System.out.println();

		//кодировка сообщения
		signXml = encryptionDecryptionXml.sign(xml);
		System.out.println("----------SIGNATURE----------");
		System.out.println(signXml);
		System.out.println();

		//делаю запрос на сервер. В ответ получаю "Xml parse error: Unparseable date: "2007-10-12T12:00:00+03:00""
		responseString = doRequest.putXml(signXml, xml);
		System.out.println("----------SECOND RESPONSE----------");
		System.out.println(responseString);
		System.out.println();

		request.setPaymentList(null);

		//Собираю третий request: один status
		Status status = new Status(40);
		request.setStatus(status);
		xml = xmlWriterReader.write(request);
		System.out.println("----------THIRD REQUEST----------");
		System.out.println(xml);
		System.out.println();

		//кодтровка сообщения
		signXml = encryptionDecryptionXml.sign(xml);
		System.out.println("----------SIGNATURE----------");
		System.out.println(signXml);
		System.out.println();

		//делаю запрос на сервер. В ответ получаю "Signature error!"
		responseString = doRequest.putXml(signXml, xml);
		System.out.println("----------THIRD RESPONSE----------");
		System.out.println(responseString);
		System.out.println();

		//имитация полученых и расшифрованых данных
		responseString = "<response> \n" +
				"<result code=\"0\"> \n" +
				"<attribute name=\"balance\" value=\"100.00\"/> \n" +
				"<attribute name=\"fio\" value=\"Пупкин И.В.\"/> \n" +
				"</result> \n" +
				"</response>";

		//произвожу демаршалинг данных в объект
		Response response1 = xmlWriterReader.read(responseString);
		System.out.println("----------FIRST SIMULATE RESPONSE----------");
		System.out.println(response1);

		responseString = "<response> \n" +
				"<result id=\"14546\" state=\"60\" substate=\"0\" code=\"0\" final=\"1\" trans=\"123\"/> \n" +
				"<result id=\"14547\" state=\"40\" substate=\"4\" code=\"0\" final=\"0\" trans=\"312\"/> \n" +
				"</response>";
		response1 = xmlWriterReader.read(responseString);
		System.out.println("----------SECOND SIMULATE RESPONSE----------");
		System.out.println(response1);

		responseString = "<response> \n" +
				"<result id=\"123\" state=\"60\" substate=\"0\" code=\"0\" final=\"1\" trans=\"123\"/> \n" +
				"<result id=\"125\" state=\"40\" substate=\"4\" code=\"0\" final=\"0\" trans=\"312\"/> \n" +
				"</response>";
		response1 = xmlWriterReader.read(responseString);
		System.out.println("----------THIRD SIMULATE RESPONSE----------");
		System.out.println(response1);




	}
}
