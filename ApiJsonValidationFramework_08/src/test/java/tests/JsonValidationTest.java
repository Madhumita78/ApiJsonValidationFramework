package tests;

import static constants.Endpoints.PRODUCTS;
import static constants.Endpoints.USERS;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.notNullValue;
import constants.Endpoints;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.testng.annotations.Test;

import base.BaseTest;
import io.restassured.response.Response;

public class JsonValidationTest extends BaseTest {

    
    // 1️BASIC JSON VALIDATION
    @Test
    public void validate_basic_fields() {

//        given()
//                .when()
//                .get(USERS)
//                .then()
//                .statusCode(200)
//                .body("total", greaterThan(0))
//                .body("users[0].firstName", notNullValue());
  
    	Response response =
    		    when()
    		        .get(Endpoints.USERS)
    		        .then()
    		        .statusCode(200)
    		        .body("total", greaterThan(0))    // check whether this user having total count >0
    		        .body("users[0].firstName", notNullValue()) //pahaley user ka naam hai ki nahi
    		        .extract()
    		        .response();

    String firstName = response.jsonPath().getString("users[4].firstName");
    int totalUsers = response.jsonPath().getInt("total");

    System.out.println("First User Name : " + firstName);
    System.out.println("Total Users     : " + totalUsers);
//    System.out.println("JSON Path Used  : users[0].firstName");
    }
    
    // 2️ VALIDATING ARRAY SIZE
  
    @Test
    public void validate_array_size() {

        given()
                .when()
                .get(USERS)
                .then()
                .body("users.size()", greaterThan(10));
    }

   
    // 3️ FIND ELEMENT IN ARRAY (JSONPath)
   
    @Test
    public void find_specific_user() {

        given()
                .when()
                .get(USERS)
                .then()
                .body("users.find { it.firstName == 'Emily' }.lastName",
                        equalTo("Johnson"));
        
//        //for(User user : users) {
//        if(user.firstName.equals("Emily")) {
//            return user;
//        }
//    }// converted by groovy in one line { it.firstName == 'Emily' }, it-> current user
    }

   
    // 4️ GROOVY FILTER (ADVANCED)
    
    @Test
    public void validate_users_age_above_30() {

        given()
                .when()
                .get(USERS)
                .then()
                .body("users.findAll { it.age > 30 }.size()",
                        greaterThan(0));
    }

    
    // 5️ VALIDATE ARRAY VALUES
  
    @Test
    public void validate_product_prices() {

        List<Number> prices =
                given()
                        .when()
                        .get(PRODUCTS)
                        .then()
                        .extract()
                        .jsonPath()
                        .getList("products.price");

        for (Number price : prices) {
            assert price.doubleValue() > 0
                    : "Invalid price found: " + price;
        }
    }

 
    // 6️ SORTED LIST VALIDATION
   
//    @Test
//    public void validate_sorted_prices() {
//
//        List<Integer> prices =
//                given()
//                        .when()
//                        .get(PRODUCTS)
//                        .then()
//                        .extract()
//                        .jsonPath()
//                        .getList("products.price");
//
//        List<Integer> sorted = new ArrayList<>(prices);
//        Collections.sort(sorted);
//
//        assert prices.equals(sorted);
//    }

   
    // 7️ UNSORTED LIST VALIDATION
   
    @Test
    public void validate_unsorted_list() {

        List<Integer> prices =
                given()
                        .when()
                        .get(PRODUCTS)
                        .then()
                        .extract()
                        .jsonPath()
                        .getList("products.price");

        List<Integer> sorted = new ArrayList<>(prices);
        Collections.sort(sorted);

        assert !prices.equals(sorted);
    }


    
    // 8️ anyOf() – ANY CONDITION TRUE
   
    @Test
    public void validate_any_of_conditions() {

        given()
                .when()
                .get(USERS)
                .then()
                .body("users[0].gender",
                        anyOf(equalTo("male"), equalTo("female")));
    }

    
    // 9️ allOf() – ALL CONDITIONS TRUE
   
    @Test
    public void validate_all_conditions() {

        given()
                .when()
                .get(USERS)
                .then()
                .body("users[0]",
                        allOf(
                                hasKey("firstName"),
                                hasKey("lastName"),
                                hasKey("email")
                        ));
    }

    
    // COMPLEX NESTED JSON VALIDATION
   
    @Test
    public void validate_nested_json() {

        given()
                .when()
                .get(USERS)
                .then()
                .body("users[0].address.city", notNullValue())
                .body("users[0].company.name", notNullValue());
    }
    
}
