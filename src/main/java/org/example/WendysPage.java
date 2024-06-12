package org.example;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;


public class WendysPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions action;
    private JavascriptExecutor executor;
    public final Customizer customize;



    public WendysPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10); // waits for up to 10 seconds
        this.customize = new Customizer(driver, wait);
        this.action = new Actions(driver);
        this.executor = (JavascriptExecutor) driver;
        this.driver.get("https://order.wendys.com/location-selection");
        Thread.currentThread().setUncaughtExceptionHandler((thread, exception) -> {
            System.err.println("Uncaught exception in thread '" + thread.getName() + "': " + exception.getMessage());
            System.out.println("Quitting the driver");
            this.quit(0);
            this.driver = null;
        });
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (Objects.nonNull(this.driver)) {
                executor.executeScript("alert('Operation(s) completed. Chrome will close in 3 seconds')");
                quit(3000);
                System.out.println("Operation(s) completed. Chrome closed");
            }
        }));
    }

    public void acceptCookies() {
        WebElement acceptCookiesButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button#onetrust-accept-btn-handler")));
        acceptCookiesButton.click();
    }

    public void selectLocation(String location) {
        WebElement locationInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input#find-search-input-text")));
        locationInput.sendKeys(location);
        WebElement submitLocation = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("button#find-search-input-submit")));
        submitLocation.click();

        WebElement storeButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Order here']")));
        storeButton.click();
    }

    public void selectCategory(String category) {
        String xpathExpression = String.format("//div[@class='category-item' and .//div[text()='%s']]//button", category);
        WebElement categoryButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathExpression)));
        categoryButton.click();
    }

    public void selectItem(String[] item) {
        for (String itemName : item) {
            String xpathExpression = String.format("//*[text()='%s']", itemName);
            WebElement itemButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathExpression)));
            itemButton.click();
        }
    }

    public void selectDrink(String[] drink) {
        for (String drinkName : drink) {
            String xpathExpression = String.format("//div[contains(@class, 'row') and .//span[text()='%s']]//img",drinkName);
            WebElement drinkButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathExpression)));
            drinkButton.click();
        }
    }


    public void quit(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        driver.quit();
    }

    public class Customizer {
        private WebDriver driver;
        private WebDriverWait wait;

        public Customizer(WebDriver driver, WebDriverWait wait) {
            this.driver = driver;
            this.wait = wait;
        }

        private WebElement hoverOverItem(String name) {
            WebElement hoverElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(String.format("//div[contains(@class,'tile-section') and .//span[text()='%s']]", name))));
            action.moveToElement(hoverElement).perform();
            return hoverElement;
        }

        private WebElement findChildButton(WebElement parent, String buttonText, boolean shouldWait) {
            if (shouldWait) {
                return wait.until(ExpectedConditions.elementToBeClickable(findChildButton(parent, buttonText)));
            } else {
                return findChildButton(parent, buttonText);
            }
        }

        private WebElement findChildButton(WebElement parent, String buttonText) {
            return parent.findElement(By.xpath(String.format(".//*[text()='%s' or contains(@class,'%s')]", buttonText,buttonText)));
        }

        private WebElement findChildInput(WebElement parent, String inputType) {
            return parent.findElement(By.xpath(String.format(".//input[@type='%s']", inputType)));
        }

        private void forceClick(WebElement element) {
            String initialStyle = element.getAttribute("style");
            executor.executeScript("arguments[0].style = 'display:block;height:5px;width:5px;'", element);
            element.click();
            executor.executeScript("arguments[0].style = '" + initialStyle + "'", element);
        }

        public void add(Object[][] items) {
            for (Object[] item : items) {
                if (item.length == 1) {
                    add((String) item[0]);
                } else {
                    if (item[1] instanceof Integer) {
                        add((String) item[0], (int) item[1]);
                    } else {
                        add((String) item[0], (String) item[1]);
                    }
                }
            }
        }

        public void add(String name) {
            add(name, "reg");
        }

        public void add(String name, String level) {
            // implementation of add method
            System.out.println("Adding " + name + " with level " + level);
            WebElement modifierContainer = addItem(name);
            if (!level.equals("reg")) {
                findChildButton(modifierContainer, level,true).click();
            }
        }

        public void add(String name, int qty) {
            // implementation of add method
            System.out.println("Adding " + name + " with qty " + qty);
            WebElement modifierContainer = addItem(name);
            if(qty > 1) {
                WebElement qtyButton = findChildButton(modifierContainer,"plus-button",true);
                for (int i = 1; i < qty; i++) {
                    qtyButton.click();
                }
            }
        }

        private WebElement addItem(String name) {
            WebElement modifierContainer = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(String.format("//div[@class='modifier-container' and .//span[text()='%s']]", name))));
            WebElement checkbox = findChildInput(modifierContainer, "checkbox");
            forceClick(checkbox);
            return modifierContainer;
        }

        public void remove(String[] items) {
            for (String item : items) {
                remove(item);
            }
        }

        public void remove(String name) {
            WebElement hoverElement = hoverOverItem(name);
            findChildButton(hoverElement,"Remove Item",true).click();

            System.out.println("Removed " + name);
        }

        public void edit(String[][] items) {
            for (String[] item : items) {
                edit(item[0], item[1]);
            }
        }

        public void edit(String name, String level) {
            WebElement hoverElement = hoverOverItem(name);
            findChildButton(hoverElement, "Edit Item", true).click();
            findChildButton(hoverElement, level, true).click();

            System.out.println("Edited " + name + " to " + level);
        }
    }

}