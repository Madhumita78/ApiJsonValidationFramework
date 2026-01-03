package tests;

import config.BaseConfig;
import constants.Endpoints;
import enums.Designation;
import exceptions.InvalidDataException;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.request.UserRequest;
import pojo.response.UserResponse;
import utils.RequestUtil;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

public class UserApiTest extends BaseConfig {

    // 1️ POST – JSON STRING
    @Test
    public void createUserWithString() {
        String body =  "{\n" +
                "  \"firstName\": \"Amit\",\n" +
                "  \"email\": \"amit@gmail.com\"\n" +
                "}";

        Response response = RequestUtil.post(body, Endpoints.USERS + "/add");
               
        Assert.assertEquals(response.statusCode(), 201);
    }

    // 2️ POST – HASHMAP
    @Test
    public void createUserWithMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("firstName", "Rohit");
        map.put("email", "rohit@gmail.com");

        Response response = RequestUtil.post(map, Endpoints.USERS + "/add");
        Assert.assertEquals(response.statusCode(), 201);
    }
//
//    // 3️ POST – POJO + ENUM
    @Test
    public void createUserWithPojo() {

        UserRequest request = new UserRequest(
                "Vishesh",
                "Bangalore",
                Designation.SENIOR_SDET,
                "test@gmail.com",
                "9999999999"
        );
    

        Response response = RequestUtil.post(request, Endpoints.USERS + "/add");
        Assert.assertEquals(response.statusCode(), 201);

        // 4️ DESERIALIZATION
        
//        response.jsonPath().get("id"); in plce of this....
        
//        response(raw data)->.as() Deserialize ->Java Object(UserResponse).
    
    
        UserResponse userResponse = response.as(UserResponse.class);
        Assert.assertTrue(userResponse.getId() > 0);
    }

    // 5️ STATUS CODE VALIDATION
    @Test
    public void validateStatusCodes() {
        Response response = RequestUtil.get(Endpoints.USERS + "/1");

        Assert.assertTrue(
                response.statusCode() == 200 ||
                response.statusCode() == 400 ||
                response.statusCode() == 404
        );
    }

    // 6️ PUT
    @Test
    public void updateUserPut() {
        Map<String, String> map = new HashMap<>();
        map.put("firstName", "Updated");

        Response response =
                RequestUtil.put(map, Endpoints.USERS + "/1");
        Assert.assertEquals(response.statusCode(), 200);
    }

    // 6️ PATCH
    @Test
    public void updateUserPatch() {
        Map<String, String> map = new HashMap<>();
        map.put("email", "updated@gmail.com");

        Response response =
                RequestUtil.patch(map, Endpoints.USERS + "/1");
        Assert.assertEquals(response.statusCode(), 200);
    }

    // 6️ DELETE
    @Test
    public void deleteUser() {
        Response response =
                RequestUtil.delete(Endpoints.USERS + "/1");
        Assert.assertEquals(response.statusCode(), 200);
    }

    // 7️ IDEMPOTENCY (GET)
    @Test
    public void idempotencyCheck() {
        Response r1 = RequestUtil.get(Endpoints.USERS + "/1");
        Response r2 = RequestUtil.get(Endpoints.USERS + "/1");

        Assert.assertEquals(r1.asString(), r2.asString());
    }

    // 8️ CUSTOM EXCEPTION
    @Test(expectedExceptions = InvalidDataException.class)
    public void invalidDataTest() {
        throw new InvalidDataException("Invalid mobile number");
    }
}
