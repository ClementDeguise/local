package com.emse.spring.local.model;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.*;

public class HttpRequest {

    // handle server request
    private String charset = "UTF_8";
    private String targetUrl = "http://192.168.1.131/api/xCXbljoa3kZ-iI0yQIc6luELl2mdtQjBnX5SkO8x/";
    //private String content_type = "application/json";

    public HttpRequest(){};


    public String GetRequest(String UrlParameters) {

        HttpURLConnection connection;

        try {
            URL url = new URL(targetUrl + UrlParameters);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            //connection.setRequestProperty("Content-Type", "application/json");


            // response handling
            InputStream is = connection.getInputStream();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = buffer.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            buffer.close();
            return response.toString();



        }

        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    public String PutRequest(String UrlParameters, String body) {

        HttpURLConnection connection;

        try {
            URL url = new URL(targetUrl + UrlParameters);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            OutputStreamWriter out = new OutputStreamWriter(
                    connection.getOutputStream());
            out.write(body);
            out.close();


            // response handling
            InputStream is = connection.getInputStream();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = buffer.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            buffer.close();
            return response.toString();



        }

        catch (Exception e) {
            e.printStackTrace();
            return null;
        }



    }


    public String JsonResponseFormatting(String getRequest, String id, Integer iter) {
        // formatting Json response here
        String sendBack = null;
        //String getRequest = s.GetRequest();

        //iter used only on global get to make numerous calls


        //global get,parse by id
        //while(true) {
        //System.out.println(getRequest);
        if (getRequest != null) {

//            JSONObject json = new JSONObject("{\"9\": {" +
//                    "\"state\": {\"on\": true,\"bri\": 254,\"hue\": 8418,\"sat\": 140,\"effect\": \"none\",\"xy\": [0.4573, 0.4100],\"ct\": 366,\"alert\": \"none\",\"colormode\": \"ct\",\"mode\": \"homeautomation\",\"reachable\": true}," +
//
//                    "\"type\": \"Extended color light\"," +
//                    "\"name\": \"2\"," +
//                    "}}");


            JSONObject json = new JSONObject(getRequest);
            // 2 possibilitie	s : global or local
            JSONObject light;
            boolean on;
            int sat;
            JSONArray XYZ;
            String name;
            double x;
            double y;
            String color;
            String status;

            xyYtoRGB xy = new xyYtoRGB();

            //System.out.println(json);

            //TODO : vérifier que le get global marche

            if (id.equals("ALL")) {

                //System.out.println(json);
                String iteration = Integer.toString(iter);

                light = json.getJSONObject(iteration);

                System.out.println(light);
                on = light.getJSONObject("state").getBoolean("on");
                sat = light.getJSONObject("state").getInt("sat");
                XYZ = light.getJSONObject("state").getJSONArray("xy");
                name = light.getString("name");

                status = on ? "ON": "OFF";

                x = XYZ.getDouble(0);
                y = XYZ.getDouble(1);
                color = xy.ToHex(x,y);

                //name is actually the id

                // use objectmapper for prettier code
                sendBack = "JSON/{\"id\": " + iter + ",\"color\": " + color + ",\"status\": " + status + ",\"roomId\": " + name + ",\"saturation\": " + sat + "}";
                //s.sendMessage("sendBack", "sender");

//                for (int i = 3; i <= 9; i++) {
//                    light = json.getJSONObject("i");
//                    on = light.getJSONObject("state").getBoolean("on");
//                    sat = light.getJSONObject("state").getInt("sat");
//                    XYZ = light.getJSONObject("state").getJSONArray("xy");
//                    name = light.getString("name");
//
//                    x = XYZ.getDouble(0);
//                    y = XYZ.getDouble(1);
//                    color = xy.ToHex(x,y);
//
//                    status = on ? "ON": "OFF";
//
//                    sendBack = "JSON/{\"id\": 1,\"color\": " + color + ",\"status\": " + status + ",\"roomId\": " + name + ",\"saturation\": " + sat + "}";
//                    //s.sendMessage("sendBack", "sender");
//                }


            } else {

                System.out.println(json);
                //light = json.getJSONObject(id);

                on = json.getJSONObject("state").getBoolean("on");
                sat = json.getJSONObject("state").getInt("sat");
                XYZ = json.getJSONObject("state").getJSONArray("xy");
                name = json.getString("name");

                x = XYZ.getDouble(0);
                y = XYZ.getDouble(1);
                color = xy.ToHex(x,y);

                status = on ? "ON": "OFF";

                sendBack = "JSON/{\"id\": " + id + ",\"color\": " + "\"" + color + "\"" + ",\"status\": " + "\"" + status + "\"" + ",\"roomId\": " + name + ",\"saturation\": " + sat + "}";
                //s.sendMessage("sendBack", "sender");
                //System.out.println(sendBack);

                //TODO : mauvais roomId a handle
            }

        }
        return sendBack;
    }



}
