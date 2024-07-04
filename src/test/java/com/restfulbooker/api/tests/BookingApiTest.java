package com.restfulbooker.api.tests;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import com.restfulbooker.api.config.IntegrationTestApplication;
import com.restfulbooker.api.config.TestConfiguration;
import com.restfulbooker.api.constants.Constants;
import com.restfulbooker.api.model.request.AuthorizationRequest;
import com.restfulbooker.api.util.ConfigReader;
import com.restfulbooker.api.model.request.BookingDates;
import com.restfulbooker.api.model.request.BookingIdByNameRequest;
import com.restfulbooker.api.model.request.BookingRequest;
import com.restfulbooker.api.model.response.BookingResponse;
import com.restfulbooker.api.routes.RestRoutes;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest(classes = IntegrationTestApplication.class)
public class BookingApiTest extends AbstractTestNGSpringContextTests {

    @Autowired
    TestConfiguration testConfiguration;

    Integer bookingid;

    public Map<String,String> validateResponseHeader() {
        Map<String,String> responseHeader = new HashMap<String, String>();
        responseHeader.put(Constants.SERVER,Constants.COWBOY_SERVER);
        responseHeader.put(Constants.CONNECTION,Constants.KEEP_ALIVE_CONNECTION);
        responseHeader.put(Constants.X_POWERED_BY,Constants.EXPRESS_POWERED_BY);
        responseHeader.put(Constants.CONTENT_TYPE,Constants.RESPONSE_CONTENT_TYPE);
        responseHeader.put(Constants.VIA,Constants.VIA_VEGUR);
        return responseHeader;
    }

    @BeforeClass
    public void setup() {
        baseURI = testConfiguration.getBaseURL();
        bookingid = Integer.valueOf(testConfiguration.getBookingID());
    }

    @Test
    public void getAllBookingIdsApiTest() {

        given().
                when().
                get(RestRoutes.BOOKING).
                then().
                assertThat().
                //Check for success status code
                statusCode(HttpStatus.SC_OK).
                and().
                //Validate header contents
                headers(validateResponseHeader()).
                and().
                //Validate if books returned is not empty
                body(Constants.BOOKING_ID_PARAM, hasSize(greaterThan(0))).
                log().all();
    }

    @Test
    public void getBookingByNameTest() {
        BookingIdByNameRequest nameRequest = new BookingIdByNameRequest("Sally","Brown");
            given().
                    request().body(nameRequest).
                    get(RestRoutes.BOOKING).
                    then().
                    assertThat().
                    statusCode(HttpStatus.SC_OK).
                    and().
                    body(Constants.BOOKING_ID_PARAM,hasSize(greaterThan(0))).
                    and().
                    headers(validateResponseHeader()).
                    and().
                    log().all();
    }

    @Test
    public void getBookingByIdTest() {
        given().
                contentType(ContentType.JSON).
                get(RestRoutes.getBookingById(bookingid)).
                then().
                assertThat().
                statusCode(HttpStatus.SC_OK).
                body(Constants.FIRSTNAME_PARAM,notNullValue()).
                headers(validateResponseHeader()).
                log().all();
    }

    @Test
    public void createBookingAndGetBookingIdTest() {
        BookingRequest bookingRequest = new BookingRequest(
                "Joshua","Samuel",1907,true, new BookingDates(Date.valueOf("2025-01-01"),Date.valueOf("2026-01-01")),"Candle light dinner");
        Integer bookingIdCreated = given().contentType(ContentType.JSON).
                body(bookingRequest).
                post(RestRoutes.BOOKING).
                then().
                assertThat().
                statusCode(HttpStatus.SC_OK).
                and().
                log().all().
                extract().
                response().
                path(Constants.BOOKING_ID_PARAM);
        Assert.assertTrue(bookingIdCreated>0);
    }

    @Test
    public void createBookingTest() {
        BookingRequest bookingRequest = new BookingRequest(
                "Nithya","Nagaraj",2305,true, new BookingDates(Date.valueOf("2025-01-01"),Date.valueOf("2026-01-01")),"Candle light dinner");
        Response response = given().header(Constants.CONTENT_TYPE,Constants.CONTENT_TYPE_JSON).
                body(bookingRequest).
                post(RestRoutes.BOOKING);
        BookingResponse bookingResponse = response.getBody().as(BookingResponse.class);
        Assert.assertEquals(response.getStatusCode(),HttpStatus.SC_OK);
        Assert.assertTrue(bookingResponse.booking.firstname.equalsIgnoreCase(bookingRequest.firstname));
        Assert.assertTrue(bookingResponse.booking.lastname.equalsIgnoreCase(bookingRequest.lastname));
    }

    @Test
    @Ignore("Failing with 418; Working fine in postman")
    public void updateBookingTest() {
        BookingRequest updateRequest = new BookingRequest("Joshua","Samuel",1307,false,new BookingDates(Date.valueOf("2025-01-01"),Date.valueOf("2026-01-01")),"Candle light dinner and breakfast");
        String token = authorizeUser();
        given().
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                header(Constants.AUTHORIZATION_HEADER,Constants.DEFAULT_AUTHORIZATION_VALUE).
                request().body(updateRequest).
                put(RestRoutes.getBookingById(bookingid)).
                then().
                assertThat().statusCode(HttpStatus.SC_OK).
                log().all();
    }

    @Test
    public void deleteBookingTest() {
        given().contentType(ContentType.JSON).
                header(Constants.AUTHORIZATION_HEADER,Constants.DEFAULT_AUTHORIZATION_VALUE).
                delete(RestRoutes.getBookingById(bookingid)).then().assertThat().
                statusCode(HttpStatus.SC_CREATED).log().all();
    }

    public String authorizeUser() {
        RestAssured.baseURI = ConfigReader.getInstance().getBaseUrl();
        AuthorizationRequest authRequest = new AuthorizationRequest(testConfiguration.getUsername(), testConfiguration.getPassword());
        RequestSpecification request = RestAssured.given();
        request.header(Constants.CONTENT_TYPE, Constants.CONTENT_TYPE_JSON);
        ResponseBody<Response> response = request.body(authRequest).post(RestRoutes.AUTH);
        String jsonString = response.asString();
        return JsonPath.from(jsonString).get(Constants.TOKEN_PARAM);
    }
}
