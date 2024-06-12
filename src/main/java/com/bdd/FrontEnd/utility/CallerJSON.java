package com.bdd.FrontEnd.utility;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;


public class CallerJSON {


    public static void main(String[] args) {

        try {
            String result = blockIP("1.1.1.1");
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static String blockIP(String ip) throws IOException {

        String result = "";

        HttpPost post = new HttpPost("https://aks-berserkers-ingress-cert1.eastus2.cloudapp.azure.com/ms-fesimple-portability-certi-e2e2/fesimple/api/v1/portability/receivemessageportability");
        post.addHeader("UNICA-ServiceId", "550e8400-e29b-41d4-a716-446655440005");
        post.addHeader("UNICA-Application", "FrontendPlatform");
        post.addHeader("UNICA-PID", "550e8400-e29b-41d4-a716-446655440011");
        post.addHeader("UNICA-User", "UserFrontend");
        post.addHeader("Authorization", "Bearer AAIkMDVhYjFmOTctMmVmYi00OWY5LWEzYWUtNTAwNjQ0NGQyMjkx1003vJyp1QEqIHCH1pmFnhNIr3pCWPsYzEFmcX7rrqIt1Hb5-RjXyP_TwB2jeKaIg5Z_WJg6qLgajmzW6dQr_w");
        post.addHeader("Content-Type", "application/json");
        post.addHeader("X-IBM-Client-Id", "05ab1f97-2efb-49f9-a3ae-5006444d2291");

        String block = "{\"target\":\"ip\",\"value\":\"" + ip + "\"}";

        StringBuilder entity = new StringBuilder();
        entity.append("{");
        entity.append("\"callback_url\": \"https://novum.com/endtest?state=2\",");
        entity.append("\"nonce\": \"4zg86i78-7060-4590-9f9-4d967f79bf143\",");
        entity.append("\"payload\": {\n" +
                "        \"msisdn\": \"51920953950\"\n" +
                "    }");
        entity.append("}");

        // send a JSON data
        post.setEntity(new StringEntity(entity.toString()));

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {

            result = EntityUtils.toString(response.getEntity());
        }
        return result;

    }

}
