package com.packt;

import jdk.incubator.http.*;
import java.net.URI;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
public class HttpPostDemo{
    public static void main(String [] args) throws Exception{
        
        HttpClient client = HttpClient
            .newBuilder()
            .build();

        Map<String, String> requestBody = Map.of("key1", "value1", "key2", "value2");

        ObjectMapper mapper = new ObjectMapper();
        HttpRequest request = HttpRequest
            .newBuilder(new URI("http://httpbin.org/post"))
            .POST(HttpRequest.BodyProcessor.fromString(mapper.writeValueAsString(requestBody)))
            .version(HttpClient.Version.HTTP_1_1)
            .build();

        HttpResponse<String> response = client.send(request,
            HttpResponse.BodyHandler.asString());

        System.out.println("Status code: " + response.statusCode());
        System.out.println("Response Body: " + response.body());

    }
}