package com.api.test;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.microsoft.playwright.options.WaitUntilState.DOMCONTENTLOADED;
import static org.testng.Reporter.log;

public class LoginAuth{



    @Test
    public void userLoginAttempt() throws IOException, InterruptedException {

        Playwright playwright= Playwright.create() ;

        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        BrowserContext browserContext= browser.newContext();
        Page page = browserContext.newPage();

        page.navigate("https://api-test.sparebank1.no/oauth/authorize?market=p&flowStarted=true&scope=resource.WRITE,resource.READ&response_type=code&state=bbc459c5-48de-413e-be4c-e85210cf888d&redirect_uri=https://oauth.pstmn.io/v1/browser-callback&client_id=30377a32-b3e4-4b9a-8044-820686f7de8f&finInst=fid-smn");

        page.click("#WEB_CLIENT-panel"); // presses the button labeled "BankID", showing the BankID-search bar

        page.locator("text=Fødselsnummer").fill("02018715727"); // Fills in fødselsnummer
        page.click("#submit");// presses the button labeled "Next"


        page.frameLocator("//iframe[contains(@title, 'BankID')]").locator("//input[contains(@id, '')]").fill("otp"); // den sier at element BankID TestBank Friendly ikke er en inputt

        page.frameLocator("//iframe[contains(@title, 'BankID')]").locator("//html/body/div[3]/div/main/div/div[10]/div/form/div[2]/div[2]/button").click();
        page.frameLocator("//iframe[contains(@title, 'BankID')]").locator("//input[contains(@id, '')]").fill("qwer1234" ); // den sier at element BankID TestBank Friendly ikke er en inputt
        page.frameLocator("//iframe[contains(@title, 'BankID')]").locator("//html/body/div[3]/div/main/div/div[11]/div/form/div[2]/div[2]/button").click();// presses the Next button

        page.waitForURL("**/v1/*", new Page.WaitForURLOptions().setWaitUntil(DOMCONTENTLOADED));

        System.out.println("\nWebsite url: " +  page.url());
        System.out.println("\nCode:  " +  page.url().substring(48,80));
        System.out.println("\nCode length:  " +  page.url().substring(48,80).length());

        page.close();

    }


}
