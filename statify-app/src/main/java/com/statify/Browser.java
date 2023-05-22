package com.statify;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebDriver;

public class Browser {

    WebDriver driver;

    public void setUp(String url) {

        WebDriverManager.chromedriver().setup();

        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
        driver = new ChromeDriver();

        driver.get(url);
    }

    public static void main(String[] args) {
        Browser browser = new Browser();
        String url = "http://www.google.pl";
        browser.setUp(url);
        try {

            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        browser.driver.close();
    }
}
