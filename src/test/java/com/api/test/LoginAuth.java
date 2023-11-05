package com.api.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.FormData;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.annotations.*;
import sun.jvm.hotspot.utilities.Assert;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.microsoft.playwright.options.WaitUntilState.DOMCONTENTLOADED;
import static org.testng.Reporter.log;

public class LoginAuth{

    Playwright playwright;
    APIRequest request;
    APIRequestContext apiRequestContext;
    String ACCESS_TOKEN;



    @BeforeTest
    public void setup() {
        playwright= Playwright.create();
        request = playwright.request();
        apiRequestContext= request.newContext();

    }

    @AfterTest
    public void terminate(){
        playwright.close();
    }

    @Test
    public void userLoginAttempt() throws IOException {

        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        BrowserContext browserContext= browser.newContext();
        Page page = browserContext.newPage();

        page.navigate("https://api-test.sparebank1.no/oauth/authorize?market=p&flowStarted=true&scope=resource.WRITE,resource.READ&response_type=code&state=bbc459c5-48de-413e-be4c-e85210cf888d&redirect_uri=https://oauth.pstmn.io/v1/browser-callback&client_id=30377a32-b3e4-4b9a-8044-820686f7de8f&finInst=fid-smn");

        // presses the button labeled "BankID", showing the BankID-search bar
        page.click("#WEB_CLIENT-panel");


        page.locator("text=Fødselsnummer").fill("02018715727"); // Fills in fødselsnummer
        // presses the button labeled "Next"
        page.click("#submit");


        page.frameLocator("//iframe[contains(@title, 'BankID')]").locator("//input[contains(@id, '')]").fill("otp");
        page.frameLocator("//iframe[contains(@title, 'BankID')]").locator("//html/body/div[3]/div/main/div/div[10]/div/form/div[2]/div[2]/button").click();


        page.frameLocator("//iframe[contains(@title, 'BankID')]").locator("//input[contains(@id, '')]").fill("qwer1234" );
        page.frameLocator("//iframe[contains(@title, 'BankID')]").locator("//html/body/div[3]/div/main/div/div[11]/div/form/div[2]/div[2]/button").click();



        page.waitForURL("**/v1/*", new Page.WaitForURLOptions().setWaitUntil(DOMCONTENTLOADED));



        String code= page.url().substring(48,80);// Response code from login with BankID
        System.out.println("\nWebsite url: " +  page.url());
        System.out.println("\nCode:  " +  code);
        System.out.println("\nCode length:  " +  page.url().substring(48,80).length());


        //Steg 5:--------------
        APIResponse apiResponse = apiRequestContext.post("https://api-test.sparebank1.no/oauth/token",
                RequestOptions.create().setForm(FormData.create()
                        .set("client_id", "30377a32-b3e4-4b9a-8044-820686f7de8f")
                        .set("client_secret", "3cc73b74-d5c4-4c95-bb95-f410c777f8cd")
                        .set("redirect_uri", "https://oauth.pstmn.io/v1/browser-callback")
                        .set("grant_type", "authorization_code")
                        .set("code", code))
        );

        ObjectMapper objectMapper   = new ObjectMapper();
        JsonNode postJsonResponse = objectMapper.readTree(apiResponse.body());

        ACCESS_TOKEN=postJsonResponse.get("access_token").asText();// Access token: The response from the API call to resource "token" with code from BankID


        System.out.println("\nApi key response status:  " +  apiResponse.status());


        //Complete Api key response body (JSON respons)
        System.out.println("\nComplete Api key response body:  " +  postJsonResponse.toPrettyString());
        System.out.println("\nApi key:  " +  ACCESS_TOKEN);


// Steg 6:----------------------------

        //get request for resourse at path "helloworld"
        //Uses ACCESS_TOKEN from BANKID
        APIResponse apiResponse2 = apiRequestContext.get("https://api-test.sparebank1.no/common/helloworld",
                RequestOptions.create().setHeader("Accept", "application/vnd.sparebank1.v1+json; charset=utf-8").
                        setHeader("Content-Type", "application/vnd.sparebank1.v1+json; charset=utf-8").
                        setHeader("Authorization", "Bearer "+ACCESS_TOKEN)
        );


        JsonNode postJsonResponse2 = objectMapper.readTree(apiResponse2.body());

        System.out.println("\nApi key response status:  " +  apiResponse2.status());
        System.out.println("\nApi Response:  " +  postJsonResponse2.toPrettyString());



        System.out.println(( apiResponse2.status() != 200 )? " noooo" : "yeeeeees" );



        // Man må alltid huske assert, hvis ikke blir metodn true hele tiden

        PlaywrightAssertions.assertThat( apiResponse2.status() );





    }

    @Test
    public void getAccessKey(String code) {



    }
    @Test
    public void getHelloWorld() {


    }


}
