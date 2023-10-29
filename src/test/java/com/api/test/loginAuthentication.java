package com.api.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.*;
import org.testng.annotations.Test;

import java.io.IOException;


import com.microsoft.playwright.*;

public class loginAuthentication {

    @Test
    public void UserloginAttempt () throws IOException, InterruptedException {

        Playwright playwright= Playwright.create() ;

        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        BrowserContext browserContext= browser.newContext();
        Page page = browserContext.newPage();

        page.navigate("https://login-test.sparebank1.no/?goto=https%3A%2F%2Fapi-test.sparebank1.no%2Foauth%2Fauthorize%3Fmarket%3Dp%26scope%3Dresource.WRITE%252Cresource.READ%26response_type%3Dcode%26state%3Dbbc459c5-48de-413e-be4c-e85210cf888d%26redirect_uri%3Dhttp%3A%2F%2Flocalhost%3A8000%26client_id%3D30377a32-b3e4-4b9a-8044-820686f7de8f%26finInst%3Dfid-smn%26flowStarted%3Dtrue&listenNnin=false&app=age-pm&finInst=fid-smn");

        //page.click("a:text('BankID')");
        page.click("#WEB_CLIENT-panel"); // presses the button labeled "BankID", showing the BankID-search bar

        //tror feilenl ligger i at elementet "Fødelsnummer" ogsåå har samme id=
        //page.fill("#input-d23b4f8f-493e-4b0e-bc5b-16128d585ae1", "02018715727" );
        page.locator("text=Fødselsnummer").fill("02018715727"); // Fills in fødselsnummer
        page.click("#submit");

        //  page.getByLabel("Username or email address").fill("username");

        //page.locator("text=Engangskode").fill("otp");
        // bankid login:
        /*
        1.	Locator by Text content prøver dette på Bankid: kl16.10
        2.	Locator by Text ID
        3.	Locator by Placeholder
        4.	Locator by Tittle
        5.	Locator by Alt text
        6.	Locator by Label text
                */

        //1.	Locator by Text content prøver dette på Bankid: kl16.10

        //page.click("text=BankID TestBank Friendly");

        // får timeout error "waiting for locator "er
        //page.locator("text=BankID TestBank Friendly").fill("otp"); // Fills in fødselsnummer
        //page.getByTitle("Valgt BankID: BankID TestBank Friendly. Trykk for å endre BankID.").click();



        //Denne funket ikke --> page.getByText("BankID TestBank Friendly").click();
       // Denne funket -->page.locator("text=BankID TestBank Friendly");
        //page.locator("text=BankID TestBank Friendly").fill("otp");

        System.out.println( "\nApi respons frames  :" + page.frames().toString());
        System.out.println( "\nApi respons url før  :" + page.url());


        //page.frameLocator();
        page.waitForLoadState();
        //page.wait(15000);

        //page.frameLocator("//iframe[contains(@title, 'BankID')]").locator("text=BankID TestBank Friendly").fill("otp"); // den sier at element BankID TestBank Friendly ikke er en inputt
       // dene funka! page.frameLocator("//iframe[contains(@title, 'BankID')]").locator("//input[contains(@id, '')]").fill("otp",  new Locator.FillOptions().setTimeout(100.0)); // den sier at element BankID TestBank Friendly ikke er en inputt
        page.frameLocator("//iframe[contains(@title, 'BankID')]").locator("//input[contains(@id, '')]").fill("otp"); // den sier at element BankID TestBank Friendly ikke er en inputt
        //page.frameLocator("//iframe[contains(@title, 'BankID')]").locator("//input[contains(@id, '')]").pressSequentially("otp", new Locator.PressSequentiallyOptions().setDelay(15.0)); // den sier at element BankID TestBank Friendly ikke er en inputt

        //page.frameLocator("//iframe[contains(@title, 'BankID')]").wait(5000); Ser ut til å gjøre at 3 symboler skrives inn, istedet for 8 under skjermen "Engangskode"

        page.waitForLoadState();
        // presses the Next button
        page.frameLocator("//iframe[contains(@title, 'BankID')]").locator("//html/body/div[3]/div/main/div/div[10]/div/form/div[2]/div[2]/button").click();

        page.waitForLoadState();

        page.frameLocator("//iframe[contains(@title, 'BankID')]").locator("//input[contains(@id, '')]").fill("qwer1234" ); // den sier at element BankID TestBank Friendly ikke er en inputt
        page.waitForLoadState();

        //page.frameLocator("//iframe[contains(@title, 'BankID')]").locator("//input[contains(@id, '')]").pressSequentially("qwer1234", new Locator.PressSequentiallyOptions().setDelay(25.0)); // den sier at element BankID TestBank Friendly ikke er en inputt
        page.frameLocator("//iframe[contains(@title, 'BankID')]").locator("//html/body/div[3]/div/main/div/div[11]/div/form/div[2]/div[2]/button").click();


        page.waitForLoadState();


        System.out.println("\nApi respons url etter:" +  page.url());
        System.out.print( "\nApi respons context etter :" + page.context());

        //page.frameLocator("frame[title='BankID']").locator("BankID TestBank Friendly").fill("otp");




        //page.getByRole(,new Page.GetByRoleOptions().)
        //page.locator("Engangskode")
        //page.locator("text=BankID TestBank Friendly").screenshot();
        //page.locator("text=BankID TestBank Friendly").pressSequentially("otp", new Locator.PressSequentiallyOptions().setDelay(15.0));
        // Denne funket ikkepage.fill("text=BankID TestBank Friendly","otp");


        //page.locator("#plNvFdztvWfUfrcnDGmD_6").fill("otp"); // Fills in fødselsnummer
        //page.fill("#plNvFdztvWfUfrcnDGmD_6", "otp" ); // min



        // 2.	Locator by Text ID min 7: Prøver dette 1630, 29.10
        // vi har ikke datatestID inni HTML elementene til søkefeltet til BAnkID






        //3.	Locator by Placeholder prøver dette 29.10
        //page.getByPlaceholder("BankID TestBank Friendly").fill("otp");
       // page.getByPlaceholder("BankID TestBank Friendly").click();

        //page.getByAltText("text=BankID TestBank Friendly").fill("");


        // vi har ikke noe placeholder




       // 4.	Locator by Tittle vi prøver kl18.09 29-10

        //Ingen av de 3 under funka
        //page.getByTitle("Neste").click();
        //page.getByTitle("Engangskode").click();

        //page.getByTitle("BankID TestBank Friendly").click();


        //5.	Locator by Alt text
        //6.	Locator by Label text
        //page.getByLabel("password").fill("otp", new Locator.FillOptions().setTimeout(100.0));

    }


}
