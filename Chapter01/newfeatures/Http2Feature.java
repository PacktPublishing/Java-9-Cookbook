import jdk.incubator.http.*;
import java.net.URI;

public class Http2Feature{
  public static void main(String[] args) throws Exception{ 
    HttpClient client = HttpClient
      .newBuilder()
      .build();

    HttpRequest request = HttpRequest
      .newBuilder(new URI("http://httpbin.org/get"))
      .GET()
      .version(HttpClient.Version.HTTP_1_1)
      .build();

    HttpResponse<String> response = client.send(request, 
      HttpResponse.BodyHandler.asString());

    System.out.println("Status code: " + response.statusCode());
    System.out.println("Response Body: " + response.body());          
  }
}

