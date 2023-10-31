package com.api.test;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;

import java.util.HashMap;
import java.util.Map;

public class GetAccessKey {

    private static final String API_TOKEN = System.getenv("GITHUB_API_TOKEN");

    private Playwright playwright;
    private APIRequestContext request;



    void createPlaywright() {
        playwright = Playwright.create();
    }


    void createAPIRequestContext() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "token " + API_TOKEN);

        request = playwright.request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL("http://localhost:8000")
                .setExtraHTTPHeaders(headers));
    }

}
