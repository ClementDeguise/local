package com.emse.spring.local.Services;



import com.emse.spring.local.model.HttpRequest;
import com.emse.spring.local.model.xyYtoRGB;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import javax.validation.constraints.NotNull;


/**
 * A sample application that demonstrates how to use the Paho MQTT v3.1 Client blocking API.
 */


public class Subscriber implements MqttCallback {

    // initialisation
    private final int qos = 1;

    private MqttClient client;
    private String clientId = "LocalServer";
    public String message;
    public String username = "Local";
    public String password = "aaa";
    public String getRequest;
    public String putRequest;
    public String id = "0";



    public Subscriber(String host, @NotNull String topic, String topic2) throws MqttException {

        MqttConnectOptions conOpt = new MqttConnectOptions();
        conOpt.setCleanSession(true);
        conOpt.setUserName(this.username);
        conOpt.setPassword(this.password.toCharArray());

        // crée le client qui est notre serveur
        this.client = new MqttClient(host, clientId, new MemoryPersistence());
        this.client.setCallback(this);
        this.client.connect(conOpt);

        //String getRequest = this.getRequest;

        System.out.println("Connecting to host: "+host);
        System.out.println("Connected to topics: " + topic + ", ");

        //subscribe au topic
//        if (topic.equals("ALL")) {
//            this.client.subscribe("light/1");
//            this.client.subscribe("light/2");
//            //this.client.subscribe("light/1");
//
//        }
//        else{
        this.client.subscribe(topic, qos);
        this.client.subscribe(topic2, qos);

    }



    // publish a String to the topic

    public void sendMessage(String payload, String topic) throws MqttException {
        MqttMessage message = new MqttMessage(payload.getBytes());
        message.setQos(qos);
        this.client.publish(topic, message); // Blocking publish
        System.out.println("Publishing message: "+ message + " on topic " + topic);
        System.out.println("Message published");
        getRequest = null;
        //this.client.disconnect();
        //System.out.println("Disconnected");
    }

    /**
     * @see MqttCallback#connectionLost(Throwable)
     */

    public void connectionLost(Throwable cause) {
        System.out.println("Connection lost because: " + cause);
        System.exit(1);
    }

    /**
     * @see MqttCallback#deliveryComplete(IMqttDeliveryToken)
     */

    public void deliveryComplete(IMqttDeliveryToken token) {
    }

    /**
     * @see MqttCallback#messageArrived(String, MqttMessage)
     */

    /**
     * HANDLE JSON RESPONSE
     */
    public void messageArrived(String topic, MqttMessage mes) throws MqttException {


        /**
         * IDEA use a json parser to stringify all 4 valuable variables, just add a byid search for global GET
         */

        // Handling messages from sender topic here

        message = String.format("[%s] %s", topic, new String(mes.getPayload()));
        System.out.println(message);

        // only GET and PUT



        HttpRequest httpRequest = new HttpRequest();
        String method = message.substring(9,message.indexOf("/"));
        String remains = message.substring(message.indexOf("/") + 1);
        id = remains;


        System.out.println("method: " + method);
        System.out.println("remains: " + remains);

        if (method.equals("GET")) {
            //default get request
            if (remains.equals("ALL")) {
                //response



//                String sendBack = httpRequest.JsonResponseFormatting(getRequest, id, 1);
//                if (sendBack != null) {
//                    sendMessage(sendBack, "answer");
//                }

                int[] num = {1,3,4,5,6,7,8,9};
                for (int i : num) {
                    System.out.println(i);
                    getRequest = httpRequest.GetRequest("lights/" + i);
                    String sendBack = httpRequest.JsonResponseFormatting(getRequest, id, i);
                    if (sendBack != null) {
                        sendMessage(sendBack, "answer");
                    }
                    System.out.println(i);
                }


                System.out.println("RESPONSE: " + getRequest);
            } else {
               /// getRequest = httpRequest.GetRequest("lights/" + remains);
                //pass id for json response
                getRequest = httpRequest.GetRequest("lights/" + remains);
                String sendBack = httpRequest.JsonResponseFormatting(getRequest, id, 0);
                if (sendBack != null) {
                    sendMessage(sendBack,"answer");
                }

                System.out.println("RESPONSE: " + getRequest);


            }
        }

        else if (method.equals("PUT")) {

            id = remains.substring(0, 1);
            String body = remains.substring(remains.indexOf("{"));
            //body always contains only 1 variable to change
            System.out.println("body: " + body);
            System.out.println(id);

            System.out.println(body.substring(2,6));

            if (body.substring(2, 6).equals("name")) {
                putRequest = httpRequest.PutRequest("lights/" + id, body);

                getRequest = httpRequest.GetRequest("lights/" + id);
                String sendBack = httpRequest.JsonResponseFormatting(getRequest, id, 0);
                if (sendBack != null) {
                    sendMessage(sendBack, "answer");
                }
                //requestId = Integer.parseInt(id);
                System.out.println("RESPONSE: " + getRequest);
            }


            else if (body.substring(2, 7).equals("color")) {

                String color = body.substring(body.indexOf("#"),body.indexOf("#")+7);
                System.out.println(color);

                xyYtoRGB xy = new xyYtoRGB();
                Double[] XY = xy.ToXY(color);

                System.out.println(XY[0]);
                System.out.println(XY[1]);


                String hex = "{\"xy\" : " + "[" + XY[0] + ", " + XY[1] + "]" + "}";
                System.out.println(hex);

                putRequest = httpRequest.PutRequest("lights/" + id + "/state", hex);

                getRequest = httpRequest.GetRequest("lights/" + id);
                String sendBack = httpRequest.JsonResponseFormatting(getRequest, id,0);
                if (sendBack != null) {
                    sendMessage(sendBack, "answer");
                }
                //requestId = Integer.parseInt(id);
                System.out.println("RESPONSE: " + getRequest);


            } else {
                putRequest = httpRequest.PutRequest("lights/" + id + "/state", body);


                System.out.println("RESPONSE: " + putRequest);
                getRequest = httpRequest.GetRequest("lights/" + id);
                String sendBack = httpRequest.JsonResponseFormatting(getRequest, id,0);
                if (sendBack != null) {
                    sendMessage(sendBack, "answer");
                }
                //requestId = Integer.parseInt(id);
                System.out.println("RESPONSE: " + getRequest);
            }

        }





        }



//        public String GetRequest() {
//            return this.getRequest;
//        }
//
//        public String GetId() {
//            return this.id;
//        }


//    public String JsonResponse() {
//
//        String resp = this.message.substring(0, 4);
//        // header JSON required, string contient SEULEMENT LES ID? STATUS? COLOR & ROOMID
//        if (resp.equals("JSON")) {
//            // first, convert string into new dto
//            return resp;
//
//        }
//    }





/*
    public Subscriber(String uri) throws MqttException, URISyntaxException {
        this(new URI(uri));
    }

    // l'URL est juste un type  d'URI

    public Subscriber(URI uri) throws MqttException {
        String host = String.format("https://%s:%d", uri.getHost(), uri.getPort());
        String[] auth = this.getAuth(uri);
        String username = auth[0];
        String password = auth[1];
        String clientId = "SpringServer";
        if (!uri.getPath().isEmpty()) {
            this.topic = uri.getPath().substring(1);
        }

        MqttConnectOptions conOpt = new MqttConnectOptions();
        conOpt.setCleanSession(true);
        conOpt.setUserName(username);
        conOpt.setPassword(password.toCharArray());

        // crée le client qui est notre serveur
        this.client = new MqttClient(host, clientId, new MemoryPersistence());
        this.client.setCallback(this);
        this.client.connect(conOpt);

        //subscribe au topic
        this.client.subscribe(this.topic, qos);
    }

    private String[] getAuth(URI uri) {
        String a = uri.getAuthority();
        String[] first = a.split("@");
        return first[0].split(":");
    }
*/






}
