package com.bestbuy.productinfo;

import com.bestbuy.testbase.TestBaseProduct;
import com.bestbuy.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SerenityRunner.class)
public class ProductCRUDTestWithSteps extends TestBaseProduct {
    @Steps
    ProductSteps productSteps;
    static String name="Acer-Chromebook"+ TestUtils.getRandomValue();
    static String type="laptop";
    static double price=1669.89;
    static double shipping=5.99;
    static String upc=TestUtils.getRandomValue();
    static String description= "The stylish Chromebook";
    static String manufacturer="Acer";
    static String model=TestUtils.getRandomValue();
    static String url="https://pisces.bbystatic.com/"+TestUtils.getRandomValue()+".jpg;maxHeight=640;maxWidth=550";
    static String image= TestUtils.getRandomValue();
    static int id;
    @Title("Creating a new PRODUCT")
    @Test
    public void test001(){
        ValidatableResponse response= productSteps.createProduct(name,type,price,shipping,upc,description,manufacturer,model,url,image);
        response.log().all().statusCode(201);
        id=response.extract().path("id");
    }
    @Title("Verify that product is added or not")
    @Test
    public void test002(){
        int productID = productSteps.getProductId(id);
        Assert.assertEquals(productID,id);
    }
    @Title("Updating product information")
    @Test
    public void test003(){
        name="Iphone Pro 14";
        type="Mobile";
        price=999;
        shipping=0;
        description="The stylish phone";
        manufacturer="Apple";
        url="https://pisces.bbystatic.com/.jpg;maxHeight=640;maxWidth=550";
        image="https://pisces.bbystatic.com";
        productSteps.updateProduct(id,name,type,price,shipping,upc,description,manufacturer,model,url,image).statusCode(200);
        int productID = productSteps.getProductId(id);
        Assert.assertEquals(productID,id);
    }
    @Title("Delete product and verifying that product is deleted")
    @Test
    public void test004(){
        productSteps.deleteProduct(id).statusCode(200);
        productSteps.getProductById(id).statusCode(404);
    }
}
