import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matcher;
import org.hamcrest.core.Every;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PetTest {

    @Before
    public void before() {
        RequestSpecBuilder spec = new RequestSpecBuilder();
        spec.setBaseUri("https://petstore.swagger.io/v2");
        spec.addHeader("Content-Type", "application/json");
        RestAssured.requestSpecification = spec.build();
    }

    @Test
    public void getPetById() {
        int id = 435;
        given()
                .log()
                .all()
                .when()
                .get("/pet/{id}", id)
                .then()
                .log()
                .all()
                .body( "id", anyOf(is(id), is("available")))
                .statusCode(200);
    }

    @Test
    public void getPetByStatusAvailable() {
        String status = "available";
        given()
                .log()
                .all()
                .when()
                .get("/pet/findByStatus?status={status}", status)
                .then()
                .log()
                .all()
                .body("status", everyItem(equalTo(status)))
                .statusCode(200);
    }

    @Test
    public void getPetByStatusSold() {
        String status = "sold";
        given()
                .log()
                .all()
                .when()
                .get("/pet/findByStatus?status={status}", status)
                .then()
                .log()
                .all()
                .body("status", everyItem(equalTo(status)))
                .statusCode(200);
    }

    @Test
    public void getPetByStatusPending() {
        String status = "pending";
        ValidatableResponse validatableResponse = given()
                .log()
                .all()
                .when()
                .get("/pet/findByStatus?status={status}", status)
                .then()
                .log()
                .all()
                .body("status", everyItem(equalTo(status)))
                .statusCode(200);
    }

    @Test
    public void addNewPetToStore() {
        String id = "4564";
        String body = "{\n" +
                "  \"id\": 4564,\n" +
                "  \"category\": {\n" +
                "    \"id\": 0,\n" +
                "    \"name\": \"string\"\n" +
                "  },\n" +
                "  \"name\": \"UmaTurman\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"string\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": 0,\n" +
                "      \"name\": \"string\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"available\"\n" +
                "}";
        given()
                .log()
                .all()
                .body(body)
                .when()
                .post("/pet")
                .then()
                .log()
                .all()
                .body("id", is(id))
                .statusCode(200);
    }

    @Test
    public void updatePetByDataForm() {
        //String id = RandomStringUtils.randomNumeric(3);
        String id = "435";
        given()
                .log()
                .all()
                .contentType("application/x-www-form-urlencoded")
                .param("name", "Jackie")
                .param("status", "Sold")
                .when()
                .post("/pet/{id}", id)
                .then()
                .log()
                .all()
                .body("message", is(id))
                .statusCode(200);
    }

    @Test
    public void updateExistingPet() {
        String body = "{\n" +
                "  \"id\": 644,\n" +
                "  \"category\": {\n" +
                "    \"id\": 0,\n" +
                "    \"name\": \"string\"\n" +
                "  },\n" +
                "  \"name\": \"Homer\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"string\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": 0,\n" +
                "      \"name\": \"string\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"available\"\n" +
                "}";
        given()
                .log()
                .all()
                .body(body)
                .when()
                .put("/pet")
                .then()
                .log()
                .all()
                .body("name", anyOf(is(644), is("Homer")))
                //.body("id", is(644))
                .statusCode(200);
    }

    @Test
    public void deletePetById() {
        String id = "4564";
        given()
                .log()
                .all()
                .when()
                .delete("/pet/{id}", id)
                .then()
                .log()
                .all()
                .body("message", is(id))
                .statusCode(200);
    }
}
