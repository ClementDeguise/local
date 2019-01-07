package com.emse.spring.local;

import com.emse.spring.local.Services.Subscriber;
import com.emse.spring.local.model.HttpRequest;
import com.emse.spring.local.model.xyYtoRGB;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
//@EnableAsync
public class LocalApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocalApplication.class, args);

		/**
		 * JSON to JSON conversion, from HTTP response to MQTT message with ALL USELESS INFORMATIONS removed
		 */
		//Subscriber s;
		try {
			Subscriber s = new Subscriber("tcp://m20.cloudmqtt.com:15247", "sender", "answer");
            //Subscriber su = new Subscriber("tcp://m20.cloudmqtt.com:15247", "answer", "LocalServer2");

			//s.sendMessage("JSON/{\"id\": 9,\"color\": \"#fff000\",\"status\": \"ON\",\"roomId\": 2,\"saturation\": 100}", "answer");



			//System.out.println("loop exited");

		} catch (MqttException me) {
			System.out.println(me.getMessage());
		}

//		if (s != null) {
//
//			try {
//				s.sendMessage("Hello", "light/1");
//				s.sendMessage("Hello 2", "light/2");
//			} catch (MqttException e) {
//				System.out.println(e.getMessage());
//			}
//		}



//		String putRequest = httpRequest.PutRequest("lights/9/state");
//		System.out.println("RESPONSE: " + putRequest);
//		String getRequest = httpRequest.GetRequest("lights/9");
//		System.out.println("RESPONSE: " + getRequest);

	}




}

