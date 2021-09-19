package petstore;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class Pet {
    String uri = "https://petstore.swagger.io/v2/pet";


    public String readJson(String pathJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(pathJson)));
    }

    @BeforeSuite
    @Test
    public void createPet() throws IOException {
        String jsonBody = readJson("src/test/resources/petCreate.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .post(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Luke"))
                .body("status", is("available"))
        ;
    }

    @Test
    public void updatePet() throws IOException {
        String jsonBody = readJson("src/test/resources/petUpdate.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .put(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Anakin"))
                .body("status", is("available"))
        ;
    }

    @Test
    public void findPetByStatus() throws IOException {
        String jsonBody = readJson("src/test/resources/petUpdate.json");

        String uriFind = "https://petstore.swagger.io/v2/pet/findByStatus";

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .get(uriFind)
        .then()
                .log().all()
                .statusCode(200)
        ;
    }

    @Test
    public void findPetById() throws IOException {
        String jsonBody = readJson("src/test/resources/petCreate.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .get(uri + "/512")
        .then()
                .log().all()
                .statusCode(200)
                .assertThat()
                .body("id", equalTo(512))
        ;
    }
}
