package com.bestbuy.storeinfo;

import com.bestbuy.testbase.TestBaseStore;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.Map;
import java.util.HashMap;

@RunWith(SerenityRunner.class)
public class StoreCRUDTestWithSteps extends TestBaseStore {
    @Steps
    StoreSteps storeSteps;
    static String name="City corner store";
    static String type="BigBox";
    static String address="13513 Ridgedale Dr";
    static String address2="Stepny Green";
    static String city="Hopkins";
    static String state="MN";
    static String zip="55305";
    static double lat=44.9696;
    static  double lng=99.449;
    static String hours="Mon: 10-9; Tue: 10-9; Wed: 10-9; Thurs: 10-9; Fri: 10-9; Sat: 10-9; Sun: 10-8";
    static int id;
    @Title("Creating a store")
    @Test
    public void test001(){
        Map<String,Object> services = new HashMap<>();
        services.put("name","Geek Squad Services");
        services.put("id","01");
        ValidatableResponse response= storeSteps.createStore(name,type,address,address2,city,state,zip,lat,lng,hours,services);
        response.log().all().statusCode(201);
        id=response.extract().path("id");
    }
    @Title("Verify that store is added or not")
    @Test
    public void test002(){
        int storeId = storeSteps.getStoreId(id);
        Assert.assertEquals(storeId,id);
    }
    @Title("Updating store information")
    @Test
    public void test003(){
        Map<String,Object> services = new HashMap<>();
        services.put("name","Geek Squad Services");
        services.put("id","02");
        name="City foods";
        storeSteps.updateStore(id,name,type,address,address2,city,state,zip,lat,lng,hours,services);
        int storeId = storeSteps.getStoreId(id);
        Assert.assertEquals(storeId,id);
    }
    @Title("Deleting store and verifying that store is deleted")
    @Test
    public void test004(){
        storeSteps.deleteStore(id).statusCode(200);
        storeSteps.getStoreById(id).statusCode(404);
    }
}
