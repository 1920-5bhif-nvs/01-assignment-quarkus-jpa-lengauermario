package at.htl.cinema.rest;

import at.htl.cinema.model.Cinema;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class CinemaTest {

    @Test
    public void testGetAllEndpoint() {
        given()
                .when().get("cinema")
                .then()
                .statusCode(200);
    }

    @Test
    public void testInsertCinema() {
        String json = "{\n" +
                "        \"address\": \"Musterstraße\",\n" +
                "        \"founded\": \"1999-12-08\",\n" +
                "        \"name\": \"Test Kino\"\n" +
                "    }";

        //Überprüfen wie viele Datensätze in der Datenbank sind.
        Response response = given()
                .when().get("cinema")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON).extract().response();
        int sizeBeforePut = response.body().path("list.size()");

        //Datensatz einfügen
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(json)
                .post("/cinema")
                .then()
                .statusCode(200);

        //Überprüfen ob jetzt um 1 Datensatz mehr in der Datenbank ist
        given()
                .when().get("cinema")
                .then()
                .statusCode(200)
                .body("size()", is(sizeBeforePut+1));
    }

    @Test
    public void testUpdateCinema(){

        //Datensatz einfügen
        String json = "{\n" +
                "        \"address\": \"Musterstraße 15\",\n" +
                "        \"founded\": \"1999-12-08\",\n" +
                "        \"name\": \"Test Kino\"\n" +
                "    }";

        Cinema cinema = given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(json)
                .post("/cinema")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON).extract().as(Cinema.class);
        System.out.println(cinema);
        assertThat(cinema).isNotNull();

        //Datensatz updaten
        json = "{\n" +
                "        \"id\": \""+cinema.getId()+"\",\n" +
                "        \"address\": \"Musterstraße 20\",\n" +
                "        \"founded\": \"1999-12-08\",\n" +
                "        \"name\": \"Test Kino\"\n" +
                "    }";

        cinema = given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(json)
                .put("/cinema")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON).extract().as(Cinema.class);

        //überprüfen
        assertThat(cinema.getAddress()).isEqualTo("Musterstraße 20");

    }

}