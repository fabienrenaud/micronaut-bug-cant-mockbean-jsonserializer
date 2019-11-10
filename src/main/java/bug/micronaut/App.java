package bug.micronaut;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import io.micronaut.context.annotation.Factory;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.runtime.Micronaut;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;

@Factory
public class App {

  @Controller
  public static class Control {

    private final Obj0 obj0;

    @Inject
    public Control(Obj0 obj0) {
      this.obj0 = obj0;
    }

    @Get("/example/bug")
    MyObj bug() {
      return new MyObj();
    }

    @Get("/example/obj0")
    Obj0 obj0() {
      obj0.printMe();
      return obj0;
    }
  }

  @Singleton
  MyObjJsonSerializer myObjJsonSerializer() {
    return new LowerMyObjJsonSerializer();
  }

  @Singleton
  Obj0 obj0() {
    return new Obj01();
  }

  abstract static class Obj0 {
    @JsonProperty("id")
    private int id = 1000;

    void printMe() {
      System.out.println("printMe: " + this);
    }
  }

  public static class Obj01 extends Obj0 {
    @JsonProperty("name")
    private String name = "obj01";

    @Override
    public String toString() {
      return "Obj01{" + "name='" + name + '\'' + '}';
    }
  }

  public static class Obj02 extends Obj0 {
    @JsonProperty("name")
    private String name = "obj02";

    @Override
    public String toString() {
      return "Obj02{" + "name='" + name + '\'' + '}';
    }
  }

  abstract static class MyObjJsonSerializer extends JsonSerializer<MyObj> {
    @Override
    public Class<MyObj> handledType() {
      return MyObj.class;
    }
  }

  public static class LowerMyObjJsonSerializer extends MyObjJsonSerializer {
    @Override
    public void serialize(MyObj value, JsonGenerator gen, SerializerProvider serializers)
        throws IOException {
      gen.writeString("myobj");
    }
  }

  public static class UpperMyObjJsonSerializer extends MyObjJsonSerializer {
    @Override
    public void serialize(MyObj value, JsonGenerator gen, SerializerProvider serializers)
        throws IOException {
      gen.writeString("MYOBJ");
    }
  }

  public static class MyObj {}

  public static void main(String[] args) {
    Micronaut.run(App.class, args);
  }
}
