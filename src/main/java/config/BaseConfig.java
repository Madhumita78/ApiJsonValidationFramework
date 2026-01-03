package config;

import io.restassured.RestAssured;

public class BaseConfig {

    static {
        RestAssured.baseURI = "https://dummyjson.com";
    }
}
