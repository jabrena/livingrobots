import static spark.Spark.*;

import spark.*;

public class HelloWorld {

   public static void main(String[] args) {

      get("/hello", (request, response) -> {
         return "Hello World!";
      });

   }

}