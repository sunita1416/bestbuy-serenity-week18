package com.bestbuy.storeinfo;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.model.StorePojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.Map;
public class StoreSteps {
    @Step("Creating store with name : {0}, type : {1}, address : {2}, address2 : {3}, city : {4}, " +
            "state : {5}, zip : {6}, lat : {7}, lng : {8}, hours : {9}, services : {10}")
    public ValidatableResponse createStore(String name, String type, String address, String address2, String city,
                                           String state, String zip, double lat, double lng, String hours,
                                           Map<String,Object> services){
        StorePojo storePojo= StorePojo.getStorePojo(name, type, address, address2, city, state, zip, lat, lng, hours, services);
        return SerenityRest.given()
                .contentType(ContentType.JSON)
                .header("Accept","application/json")
                .when()
                .body(storePojo)
                .post()
                .then();
    }
    @Step("Getting all store information")
    public ValidatableResponse getAllStoreInfo(){
        return SerenityRest.given()
                .when()
                .get()
                .then();
    }
    @Step("Getting store id using id : {0}")
    public int getStoreId(int id){
        return SerenityRest.given()
                .pathParams("id",id)
                .when()
                .get(EndPoints.GET_SINGLE_STORE_BY_ID)
                .then().statusCode(200)
                .extract()
                .path("id");
    }
    @Step("Getting store information using id : {0}")
    public ValidatableResponse getStoreById(int id){
        return SerenityRest.given()
                .pathParams("id",id)
                .when()
                .get(EndPoints.GET_SINGLE_STORE_BY_ID)
                .then();
    }
    @Step("Updating store using id :{0}, name : {1}, type : {2}, address : {3}, address2 : {4}, city : {5},"
            + "state : {6}, zip : {7}, lat : {8}, lng : {9}, hours : {10}, services : {11}")
    public ValidatableResponse updateStore(int id,String name, String type, String address, String address2, String city,
                                           String state, String zip, double lat, double lng, String hours,
                                           Map<String,Object> services){
        StorePojo storePojo=StorePojo.getStorePojo(name, type, address, address2, city, state, zip, lat, lng, hours, services);
        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .pathParams("id", id)
                .when()
                .body(storePojo)
                .put(EndPoints.UPDATE_STORE_BY_ID)
                .then();
    }
    @Step("Deleting store information with id: {0}")
    public ValidatableResponse deleteStore(int id){
        return SerenityRest.given().log().all()
                .pathParam("id", id)
                .when()
                .delete(EndPoints.DELETE_STORE_BY_ID)
                .then();
    }
}
