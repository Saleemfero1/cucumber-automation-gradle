package com.dcsg.oso.rlmcucumber.stepdef;

import com.dcsg.oso.rlmcucumber.common.Utility;
import com.dcsg.oso.rlmcucumber.dto.Store;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Transpose;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.restassured.response.Response;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static io.restassured.RestAssured.given;

public class TestStepDef {
    @Autowired
    Utility utility;

    @Given("user navigated to login page")
    public void login_page() {
        System.out.println("LoginPage");
    }

    @Given("^user enters username and password")
    public void username(DataTable dataTable) {
        String path = "/data/sampleData.json";
        List<Map<String, String>> dataTables = dataTable.asMaps();
        dataTables = utility.convertDataTableValues(dataTables, path);
        System.out.println(dataTables.get(0).getOrDefault("username", "user"));
        System.out.println(dataTables.get(0).getOrDefault("password", "pass"));
    }

    @Given("user enters {string} and {string}")
    public void userDetails(String username, String password) {
        System.out.println("username: " + username);
        System.out.println("password: " + password);
    }

    @Given("fetch order number")
    public void fetchOrderNumber(){

    }
}
















