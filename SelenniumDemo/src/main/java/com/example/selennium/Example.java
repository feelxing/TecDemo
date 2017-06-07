package com.example.selennium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.ActionChainExecutor;
import org.openqa.selenium.interactions.CanPerformActionChain;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;

/**
 * Created by MEV on 2017/4/13.
 */

public class Example {
    @Test
    public void test(){
        FirefoxDriver driver = new FirefoxDriver();
        //实例化一个 firefox webdriver 对象。FirefoxDriver 可以看做是一组操作 Firefox 浏览器的工具集合
        driver.navigate().to("https://coding.net");
        // 让浏览器跳转到 https://coding.net
        System.setProperty("webdriver.chrome.driver","D:\\drivers\\chromedriver.exe");
        ChromeDriver chromeDriver=new ChromeDriver();
        chromeDriver.get("https://www.baidu.com/");

    }
    @Test
    public void test1(){
        System.setProperty("webdriver.chrome.driver","D:\\drivers\\chromedriver.exe");
        ChromeDriver chromeDriver=new ChromeDriver();
        chromeDriver.get("https://www.baidu.com/");

        /*chromeDriver.findElementById("kw").sendKeys("selenium");
        chromeDriver.findElementById("su").click();*/
       // chromeDriver.navigate().back();
        chromeDriver.findElementByLinkText("贴吧").click();
        //chromeDriver.quit();
    }
    @Test
    public void login(){

        System.setProperty("webdriver.chrome.driver","D:\\drivers\\chromedriver.exe");
        ChromeDriver driver=new ChromeDriver();
        driver.get("http://makenv.ddns.net:8088/polist/index.html");
        driver.findElementById("user_name").sendKeys("000000");
        driver.findElementById("password").sendKeys("123456");
        driver.findElementsByClassName("loginbtn").get(0).click();

        //清单耦合
        driver.findElementsByClassName("cf");
        for (WebElement wb:driver.findElementsByClassName("cf")) {
            if(wb.getText().equals("清单生成")){
                wb.click();
            }
        }
        driver.findElementByLinkText("清单耦合").click();
        //driver.findElementById("login").click();
    }
    @Test
    public void calculation(){
        System.setProperty("webdriver.chrome.driver", "D:\\drivers\\chromedriver.exe");
        WebDriver driver=new ChromeDriver();
        driver.get("http://makenv.ddns.net:8088/polist/index.html");
        driver.findElement(By.id("user_name")).sendKeys("130100");
        driver.findElement(By.id("password")).sendKeys("123456");
        driver.findElements(By.className("loginbtn")).get(0).click();

        for(WebElement wb:driver.findElements(By.className("cf"))){
            if(wb.getText().equals("排放计算")){
                wb.click();
            }
        }
        driver.findElement(By.linkText("排放计算区")).click();


    }
    @Test
    public void fireFoxCalculation(){
      //  System.setProperty("webdriver.firefox.bin", "D:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
        //System.setProperty("webdriver.gecko.driver", "D:\\drivers\\geckodriver.exe");
        //System.setProperty("webdriver.firefox.driver", "D:\\drivers\\geckodriver.exe");


        /*System.setProperty("webdriver.gecko.driver", "D:\\drivers\\geckodriver.exe");
        WebDriver driver = new FirefoxDriver();*/

        System.setProperty("webdriver.gecko.driver", "D:\\drivers\\geckodriver.exe");
        DesiredCapabilities capabilities=DesiredCapabilities.firefox();
        capabilities.setCapability("marionette", true);
        WebDriver driver = new FirefoxDriver(capabilities);

        //WebDriver driver=new FirefoxDriver();
        driver.get("http://makenv.ddns.net:8088/polist/index.html");
        driver.findElement(By.id("user_name")).sendKeys("130100");
        driver.findElement(By.id("password")).sendKeys("123456");
        driver.findElements(By.className("loginbtn")).get(0).click();

        for(WebElement wb:driver.findElements(By.className("cf"))){
            if(wb.getText().equals("排放计算")){
                wb.click();
            }
        }
        driver.findElement(By.linkText("排放计算区")).click();


    }
    @Test
    public void fireFoxTest(){
        //System.setProperty("webdriver.firefox.bin","D:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
        System.setProperty("webdriver.gecko.driver","D:\\drivers\\geckodriver.exe");
      //System.setProperty("webdriver.gecko.driver", "D:\\selenium\\geckodriver.exe");
        WebDriver driver=new FirefoxDriver();
        driver.get("http://www.baidu.com/");
        driver.manage().window().maximize();
        WebElement txtbox=driver.findElement(By.name("wd"));
        txtbox.sendKeys("WebDriver");
        WebElement btn=driver.findElement(By.id("su"));
        btn.click();
        driver.close();


    }

    @Test
    public void ieCalculation(){
        System.setProperty("webdriver.ie.driver", "D:\\drivers\\IEDriverServer.exe");
        WebDriver driver=new InternetExplorerDriver();
        driver.get("http://makenv.ddns.net:8088/polist/index.html");
        driver.findElement(By.id("user_name")).sendKeys("130100");
        driver.findElement(By.id("password")).sendKeys("123456");
        driver.findElements(By.className("loginbtn")).get(0).click();

        for(WebElement wb:driver.findElements(By.className("cf"))){
            if(wb.getText().equals("排放计算")){
                wb.click();
            }
        }
        driver.findElement(By.linkText("排放计算区")).click();


    }

    @Test
    public void chromeMouse(){
        System.setProperty("webdriver.chrome.driver", "D:\\drivers\\chromedriver.exe");
        WebDriver driver=new ChromeDriver();
        WebElement element = driver.findElement(By.id("s_usersetting_top"));


    }
}
