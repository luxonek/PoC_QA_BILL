package utils

import com.google.common.base.Predicate
import geb.Browser
import geb.navigator.Navigator
import groovy.util.logging.Slf4j
import org.apache.commons.io.FileUtils
import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.WebDriverWait

import java.util.function.Function

@Slf4j
class NavigatorOperations {

    static void clickWithOffset(WebElement webElement, int xOffset, int yOffset) {
        Actions actions = new Actions(webdriver())
        actions.moveToElement(webElement).moveByOffset(xOffset, yOffset).click().build().perform()
    }

    static void doubleClick(Navigator navigator) {
        ((JavascriptExecutor) webdriver()).executeScript("var evt = document.createEvent('MouseEvents');"
                + "evt.initMouseEvent('dblclick',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);" + "arguments[0].dispatchEvent(evt);", navigator.firstElement())
    }

    static void clickJs(Navigator navigator) {
        ((JavascriptExecutor) webdriver()).executeScript("var evt = document.createEvent('MouseEvents');"
                + "evt.initMouseEvent('click',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);" + "arguments[0].dispatchEvent(evt);", navigator.firstElement())
    }


    static void typeJs(Navigator navigator, def text) {
        ((JavascriptExecutor) webdriver()).executeScript("arguments[0].value='$text';", navigator.firstElement())
    }

    static String getText(Navigator navigator) {
        (String) ((JavascriptExecutor) webdriver()).executeScript("return arguments[0].innerHTML;", navigator.firstElement());
    }

    static String printToBrowserConsoleLog(String message) {
        (String) ((JavascriptExecutor) webdriver()).executeScript("console.log(arguments[0]);", message)
    }

    static void dragAndDrop(WebElement webElement, int xOffset, int yOffset) {
        ((JavascriptExecutor) webdriver()).executeScript("function simulate(f,c,d,e){var b,a=null;for(b in eventMatchers)if(eventMatchers[b].test(c)){a=b;break}if(!a)return!1;document.createEvent?(b=document.createEvent(a),a==\"HTMLEvents\"?b.initEvent(c,!0,!0):b.initMouseEvent(c,!0,!0,document.defaultView,0,d,e,d,e,!1,!1,!1,!1,0,null),f.dispatchEvent(b)):(a=document.createEventObject(),a.detail=0,a.screenX=d,a.screenY=e,a.clientX=d,a.clientY=e,a.ctrlKey=!1,a.altKey=!1,a.shiftKey=!1,a.metaKey=!1,a.wykonaj=1,f.fireEvent(\"on\"+c,a));return!0} var eventMatchers={HTMLEvents:/^(?:load|unload|abort|error|select|change|submit|reset|focus|blur|resize|scroll)\$/,MouseEvents:/^(?:click|dblclick|mouse(?:down|up|over|move|out))\$/}; " +
                "simulate(arguments[0],\"mousedown\",0,0); simulate(arguments[0],\"mousemove\",arguments[1],arguments[2]); simulate(arguments[0],\"mouseup\",arguments[1],arguments[2]); ",
                webElement, xOffset, yOffset);
    }

    static void uploadFileWithPhantomJs(String selector, String filePath) {
        ((PhantomJSDriver) webdriver()).executePhantomJS("var page = this; page.uploadFile('" + selector + "', '" + filePath.replace('\\', '\\\\') + "');");
    }

    static void waiting(long mills) {
        Thread.sleep(mills)
    }

    static void waitUntil(WebDriver driver, ExpectedCondition condition) {
        WebDriverWait wait = new WebDriverWait(driver, 15)
        wait.until condition
    }

    static void deleteAllCookies() {
        webdriver().manage().deleteAllCookies()
        def cookies = webdriver().manage().getCookies()
        cookies.each {
            def call = "document.cookie = \"" + it.getName() + "=; path=" + it.getPath() + "; expires=Thu, 01-Jan-1970 00:00:01 GMT;\""
            ((JavascriptExecutor) webdriver()).executeScript(call)
        }
    }

    static def ctrlOrCommandKey() {
        (Platform.current.is(Platform.MAC)) ? Keys.COMMAND : Keys.CONTROL
    }

    static void selectAndDeleteAllText(Navigator navigator) {
        navigator << Keys.chord(ctrlOrCommandKey(), "a")
        navigator << Keys.DELETE
    }

    static void addCookie(String name, String value) {
        //PhantomJS 2.1.1 adds cookies but throws InvalidCookieDomainException because adding dot before domain name. Try/catch solves this problem
        try {
            webdriver().manage().addCookie(new Cookie(name, value))
        } catch (InvalidCookieDomainException e) {
        }
    }

    static void deleteCookie(String name) {
        webdriver().manage().deleteCookieNamed(name)
    }

    static void takeScreenshot(String[] args) {
        def time = new Date().getTime()
        def partName = args.length != 0 ? args[0] : ''
        try {
            FileUtils.copyFile(getScreenshotAs(OutputType.FILE) as File, new File("build/" + partName + time + ".png"))
        } catch (IOException e) {
            log.error('Error occurred while copying screenshot file', e)
        }
    }

    static def openLocalhost() {
        webdriver().get('about:blank')
    }

    static int windowHandlesCount() {
        webdriver().getWindowHandles().size()
    }

    static void switchToWindow(int number) {
        waitOpenedWindows(number)
        def handle = webdriver().getWindowHandles().toArray()[number].toString()
        webdriver().switchTo().window(handle)
    }

    static def waitOpenedWindows(int windowsCount) {
        WebDriverWait wait = new WebDriverWait(webdriver(), 5)
        wait.until(
                new Function() {
                    @Override
                    Object apply(Object o) {
                        return (windowHandlesCount() >= windowsCount)
                    }
                } as Predicate<WebDriver>
        )
    }

    static setWindowSize(int width, int height) {
        webdriver().manage().window().setSize(new Dimension(width, height))
    }

    static def webdriver() {
        new Browser().driver
    }

    static def getScreenshotAs(OutputType outputType) {
        ((TakesScreenshot) webdriver()).getScreenshotAs(outputType)
    }


    static Navigator scrollDown(Navigator scrollable) {
        ((JavascriptExecutor) webdriver()).executeScript("arguments[0].scrollIntoView(true);", scrollable.firstElement())
        scrollable
    }

    static boolean pageIsLoaded() {
        ((JavascriptExecutor) webdriver()).executeScript("return document.readyState").equals("complete")
    }
}
