package utils;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class RequestUtil {

    public static Response post(Object body, String endpoint) {
        return given()
                .header("Content-Type", "application/json")
                .body(body)
                .post(endpoint);
    }

    public static Response get(String endpoint) {
        return given().get(endpoint);
    }

    public static Response put(Object body, String endpoint) {
        return given().body(body).put(endpoint);
    }

    public static Response patch(Object body, String endpoint) {
        return given().body(body).patch(endpoint);
    }

    public static Response delete(String endpoint) {
        return given().delete(endpoint);
    }
}


/*@Test    - Problem without request util
public void testCreateUser() {
    given()
      .header("Content-Type","application/json")
      .body(body)
      .post("/users/add")
      .then().statusCode(200);
}*/
