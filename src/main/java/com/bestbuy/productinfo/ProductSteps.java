package com.bestbuy.productinfo;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.model.ProductPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

public class ProductSteps {
    @Step("Creating product with name : {0}, type : {1}, price : {2}, shipping : {3}, upc : {4}, " +
            "description : {5}, manufacturer : {6}, model : {7}, url : {8}, image : {9}")
    public ValidatableResponse createProduct(String name, String type, double price, double shipping,
                                             String upc, String description, String manufacturer,
                                             String model, String url, String image){
        ProductPojo productPojo=ProductPojo.getProductPojo(name, type, price, shipping, upc, description, manufacturer, model, url, image);
        return SerenityRest.given()
                .contentType(ContentType.JSON)
                .header("Accept","application/json")
                .when()
                .body(productPojo)
                .post()
                .then();
    }
    @Step("Getting all products information")
    public ValidatableResponse getAllProductInfo(){
        return SerenityRest.given()
                .when()
                .get()
                .then();
    }
    @Step("Getting product information using id : {0}")
    public int getProductId(int id){
        return SerenityRest.given()
                .pathParams("id",id)
                .when()
                .get(EndPoints.GET_SINGLE_PRODUCT_BY_ID)
                .then()
                .extract()
                .path("id");
    }
    @Step("Getting product information using id : {0}")
    public ValidatableResponse getProductById(int id){
        return SerenityRest.given()
                .pathParams("id",id)
                .when()
                .get(EndPoints.GET_SINGLE_PRODUCT_BY_ID)
                .then();
    }
    @Step("Updating product with id :{0}, name : {1}, type : {2}, price : {3}, shipping : {4}, upc : {5}, " +
            "description : {6}, manufacturer : {7}, model : {8}, url : {9}, image : {10}")
    public ValidatableResponse updateProduct(int id,String name,String type,double price,double shipping,
                                             String upc,String description, String manufacturer,String model,
                                             String url,String image){
        ProductPojo productPojo= ProductPojo.getProductPojo(name, type, price, shipping, upc, description, manufacturer, model, url, image);
        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .pathParams("id", id)
                .when()
                .body(productPojo)
                .put(EndPoints.UPDATE_PRODUCT_BY_ID)
                .then();
    }
    @Step("Deleting product information with id: {0}")
    public ValidatableResponse deleteProduct(int id){
        return SerenityRest.given().log().all()
                .pathParam("id", id)
                .when()
                .delete(EndPoints.DELETE_PRODUCT_BY_ID)
                .then();
    }
}
