package com.tutorialsninja.demo.utilities;

import com.google.common.base.Function;
import com.tutorialsninja.demo.browserfactory.ManageBrowser;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
public class Utility extends ManageBrowser {

    /**
     * This method will click on element
     */
    public void clickOnElement(By by) {
        WebElement loginLink = driver.findElement(by);
        loginLink.click();
    }

    public void clickOnElement(WebElement element){
        element.click();
    }

    /**
     * This method will get text from element
     */
    public String getTextFromElement(By by) {
        return driver.findElement(by).getText();
    }

    /**
     * This method will send text on element
     */
    public void sendTextToElement(By by, String text) {
        driver.findElement(by).sendKeys(text);
    }

//************************* Alert Methods *****************************************************

    /**
     * This method will switch to alert
     */
    public void switchToAlert() {
        driver.switchTo().alert();
    }

    /**
     * This method will accept alert
     */
    public void acceptAlert() {
        driver.switchTo().alert().accept();
    }

    /**
     * This method will dismiss alert
     */
    public void dismissAlert() {
        driver.switchTo().alert().dismiss();
    }

    /**
     * This method will get text from alert
     */
    public String getTextFromAlert() {
        return driver.switchTo().alert().getText();
    }

    /**
     * This method will send text from alert
     */
    public void sendTextToAlert(String text) {
        driver.switchTo().alert().sendKeys(text);
    }


//*************************** Select Class Methods ***************************************//

    /**
     * This method will select the option by visible text
     */
    public void selectByVisibleTextFromDropDown(By by, String text) {
        WebElement dropDown = driver.findElement(by);
        Select select = new Select(dropDown);
        select.selectByVisibleText(text);
    }

    /**
     * This method will select the option by value
     */
    public void selectByValueFromDropDown(By by, String value) {
        new Select(driver.findElement(by)).selectByValue(value);
    }

    /**
     * This method will select the option by index
     */
    public void selectByIndexFromDropDown(By by, int index) {
        new Select(driver.findElement(by)).selectByIndex(index);
    }

    /**
     * This method will select the option by contains text
     */
    public void selectByContainsTextFromDropDown(By by, String text) {
        List<WebElement> allOptions = new Select(driver.findElement(by)).getOptions();
        for (WebElement options : allOptions) {
            if (options.getText().contains(text)) {
                options.click();
            }
        }
    }

//*************************** Window Handle Methods ***************************************//

    /**
     * This method will close all windows
     */
    public void closeAllWindows(List<String> hList, String parentWindow) {
        for (String str : hList) {
            if (!str.equals(parentWindow)) {
                driver.switchTo().window(str).close();
            }
        }
    }

    /**
     * This method will switch to parent window
     */
    public void switchToParentWindow(String parentWindowId) {
        driver.switchTo().window(parentWindowId);
    }

    /**
     * This method will find that we switch to right window
     */
    public boolean switchToRightWindow(String windowTitle, List<String> hList) {
        for (String str : hList) {
            String title = driver.switchTo().window(str).getTitle();
            if (title.contains(windowTitle)) {
                System.out.println("Found the right window....");
                return true;
            }
        }
        return false;
    }
//*************************** Action Methods ***************************************//

    /**
     * This method will use to hover mouse on element
     */
    public void mouseHoverToElement(By by) {
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(by)).build().perform();
    }

    /**
     * This method will use to hover mouse on element and click
     */
    public void mouseHoverToElementAndClick(By by) {
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(by)).click().build().perform();
    }

    //************************** Waits Methods *********************************************//

    /**
     * This method will use to wait until  VisibilityOfElementLocated
     */
    public WebElement waitUntilVisibilityOfElementLocated(By by, int time) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public WebElement waitForElementWithFluentWait(By by, int time, int pollingTime) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(time))
                .pollingEvery(Duration.ofSeconds(pollingTime))
                .ignoring(NoSuchElementException.class);

        WebElement element = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(by);
            }
        });
        return element;
    }

    //************************** ScreenShot Methods *********************************************//

    public static String currentTimeStamp() {
        Date d = new Date();
        return d.toString().replace(":", "_").replace(" ", "_");
    }

    public static String takeScreenShot(String fileName) {
        String filePath = System.getProperty("user.dir") + "/test-output/html/";
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File scr1 = screenshot.getScreenshotAs(OutputType.FILE);
        String imageName = fileName + currentTimeStamp() + ".jpg";
        String destination = filePath + imageName;
        try {
            FileUtils.copyFile(scr1, new File(destination));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return destination;
    }

    public void generateRandomEmail(By emailPath, String emailID) {
        String emailVal = "";
        if(emailID == ""){
            //Create random username
            String userName = "" + (int) (Math.random() * Integer.MAX_VALUE);            //Create random username
            emailVal = "User" + userName + "@example.com";
        } else {
            emailVal = emailID;
        }
        sendTextToElement(emailPath, emailVal);
    }

    /***************** List of wen element Method ******************/

    public static List<WebElement> ListOfWebElements(By listValue){
        List<WebElement> listOFWebElemetsArr = driver.findElements(listValue);
        return  listOFWebElemetsArr;
    }

    // Clear the text
    public void clearText(By by) {
        driver.findElement(by).clear();
    }

    //Store multiple elements
    public List<WebElement> getMultipleElements(By by) {
        return driver.findElements(by);
    }

    public List<Double> afterFilterPrice(By by) {
        //Store elements after filtering
        List<WebElement> afterFilterProductPrice = getMultipleElements(by);
        //Create another list to store text of elements after clicking on filter Price high to low
        List<Double> afterFilterProductPriceList = new ArrayList<>();
        for (WebElement afterPrice : afterFilterProductPrice) {
            String afterFilterPrice = afterPrice.getText().replaceAll("[E,x,T,a,x,£,:,$]", "").replace(",", "");
            Double afterFilterPriceValue = Double.parseDouble(afterFilterPrice);
            afterFilterProductPriceList.add(afterFilterPriceValue);
        }
        return afterFilterProductPriceList;
    }

    public List<Double> convertPriceListToReverse(By by) {
        List<WebElement> beforeFilterProductPrice = getMultipleElements(by);
        //Create empty arraylist
        List<Double> beforeFilterProductPriceList = new ArrayList<>();
        //Store elements text to array list
        for (WebElement beforePrice : beforeFilterProductPrice) {
            String beforeFilterPrice = beforePrice.getText().replaceAll("[E,x,T,a,x,£,:,$]", "").replace(",", "");
            Double priceValueBeforeFilter = Double.parseDouble(beforeFilterPrice);
            beforeFilterProductPriceList.add(priceValueBeforeFilter);
        }
        //Sort arraylist to ascending order
        Collections.sort(beforeFilterProductPriceList);
        //Reverse the list
        Collections.reverse(beforeFilterProductPriceList);
        return beforeFilterProductPriceList;
    }
}

