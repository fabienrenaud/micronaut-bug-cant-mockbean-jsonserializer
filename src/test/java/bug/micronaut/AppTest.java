package bug.micronaut;

import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.annotation.MicronautTest;
import io.micronaut.test.annotation.MockBean;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

@MicronautTest
public class AppTest {

  @Inject EmbeddedServer server;

  @Inject
  @Client("/")
  HttpClient client;

  @MockBean(App.MyObjJsonSerializer.class)
  App.UpperMyObjJsonSerializer myObjJsonSerializer() {
    return new App.UpperMyObjJsonSerializer();
  }

  @MockBean(App.Obj0.class)
  App.Obj02 obj0() {
    return new App.Obj02();
  }

  @Test
  void test_bug() {
    String res = client.toBlocking().retrieve("/example/bug");
    System.out.println("response: " + res);
  }

  @Test
  void test_obj0() {
    String res = client.toBlocking().retrieve("/example/obj0");
    System.out.println("response: " + res);
  }
}
