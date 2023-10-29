package com.api.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.*;
import org.testng.annotations.Test;

import java.io.IOException;

public class getApiCall {

    @Test
    public void getUsersApiTest() throws IOException {

        Playwright playwright= Playwright.create() ;
        APIRequest request = playwright.request();
        APIRequestContext requestContext= request.newContext();

        APIResponse apiResponse = requestContext.get("https://login-test.sparebank1.no/?goto=https%3A%2F%2Fapi-test.sparebank1.no%2Foauth%2Fauthorize%3Fmarket%3Dp%26scope%3Dresource.WRITE%252Cresource.READ%26response_type%3Dcode%26state%3Dbbc459c5-48de-413e-be4c-e85210cf888d%26redirect_uri%3Dhttp%3A%2F%2Flocalhost%3A8000%26client_id%3D30377a32-b3e4-4b9a-8044-820686f7de8f%26finInst%3Dfid-smn%26flowStarted%3Dtrue&listenNnin=false&app=age-pm&finInst=fid-smn");

        System.out.println("\nApi respons statuscode :"+ apiResponse.status());
        System.out.println("\nApi respons url: " +apiResponse.url());
        System.out.println("\nApi respons header: "+ apiResponse.headers());
        System.out.println("\nApi respons headerarray: "+ apiResponse.headersArray().toString());
        System.out.println("\nApi respons body: "+ apiResponse.body().toString());

        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        BrowserContext browserContext= browser.newContext();
        Page page = browserContext.newPage();
        page.navigate("https://login-test.sparebank1.no/?goto=https%3A%2F%2Fapi-test.sparebank1.no%2Foauth%2Fauthorize%3Fmarket%3Dp%26scope%3Dresource.WRITE%252Cresource.READ%26response_type%3Dcode%26state%3Dbbc459c5-48de-413e-be4c-e85210cf888d%26redirect_uri%3Dhttp%3A%2F%2Flocalhost%3A8000%26client_id%3D30377a32-b3e4-4b9a-8044-820686f7de8f%26finInst%3Dfid-smn%26flowStarted%3Dtrue&listenNnin=false&app=age-pm&finInst=fid-smn");

        /*
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse= objectMapper.readTree(apiResponse.body());
        String jsonPrettyResponse=jsonResponse.toPrettyString();
        System.out.println("\nJson response: "+ jsonPrettyResponse);


         */


    }
}
