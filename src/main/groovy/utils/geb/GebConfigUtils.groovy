package utils.geb

import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.Capabilities
import org.openqa.selenium.Dimension
import org.openqa.selenium.Proxy
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxProfile
import org.openqa.selenium.logging.LogType
import org.openqa.selenium.logging.LoggingPreferences
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.phantomjs.PhantomJSDriverService
import org.openqa.selenium.remote.CapabilityType
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.safari.SafariDriver
import org.openqa.selenium.safari.SafariOptions

import java.util.logging.Level


class GebConfigUtils {

    static WebDriver getChromeDriver(String mobileEmulatorName = null, Boolean headless = false) {
        getCustomExecutable('chromedriver') ?: WebDriverManager.chromedriver().setup()

        def options = mobileEmulatorName ? getMobileEmulatorOptions(mobileEmulatorName) : chromeOptions
        options.setHeadless(headless)

        def driver = new ChromeDriver(getChromeCapabilities(options))
        return driver
    }

    static WebDriver getPhantomJsDriver() {
        getCustomExecutable('phantomjs') ?: WebDriverManager.phantomjs().setup()

        def driver = new PhantomJSDriver(phantomjsCapabilities)
        setWindowSize(driver)
        return driver
    }

    static WebDriver getRemoteFirefoxDriver() {
        DesiredCapabilities capability = DesiredCapabilities.firefox()

        def hubPort = System.getProperty("hubPort")
        WebDriver driver = new RemoteWebDriver(new URL("$remoteAddress:$hubPort/wd/hub"), capability)
        setWindowSize(driver)
        return driver
    }

    static WebDriver getRemoteChromeDriver() {
        DesiredCapabilities capability = getChromeCapabilities(chromeOptions)

        def hubPort = System.getProperty("hubPort") ?: 4444
        WebDriver driver = new RemoteWebDriver(new URL("$remoteAddress:$hubPort/wd/hub"), capability)
        setWindowSize(driver)
        return driver
    }

    static WebDriver getRemotePhantomJsDriver() {
        DesiredCapabilities capabilities = getPhantomjsCapabilities()

        def hubPort = System.getProperty("hubPort")
        WebDriver driver = new RemoteWebDriver(new URL("$remoteAddress:$hubPort/wd/hub"), capabilities)
        setWindowSize(driver)
        return driver
    }

    static WebDriver getFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup()

        def driver = new FirefoxDriver(firefoxProfile as Capabilities)
        setWindowSize(driver)
        return driver
    }

    static WebDriver getSafariDriver() {
        def driver = new SafariDriver(safariOptions)
        setWindowSize(driver)
        return driver
    }

    private static String getRemoteAddress() {
        System.getProperty('remote.server') ?: 'http://localhost'
    }

    private static getChromeCapabilities(ChromeOptions options) {
        def caps = DesiredCapabilities.chrome()
        caps.setCapability(CapabilityType.LOGGING_PREFS, logPreferences)
        caps.setCapability(ChromeOptions.CAPABILITY, options)
        return caps
    }

    private static getChromeOptions() {
        def options = new ChromeOptions()
        options.addArguments(['--disable-popup-blocking', '--test-type', '--window-size=1920,1200'])
        return options
    }

    private static getMobileEmulatorOptions(String deviceName) {
        def options = chromeOptions
        options.setExperimentalOption("mobileEmulation", ['deviceName': deviceName])
        return options
    }

    private static getPhantomjsCapabilities() {
        def phantomjsCommandLineArguments = ['--debug=false', '--webdriver-loglevel=INFO', '--web-security=false',
                                             '--ssl-protocol=any', '--ignore-ssl-errors=true', '--local-to-remote-url-access=true']
        def caps = new DesiredCapabilities().phantomjs()
        caps.setJavascriptEnabled(true)
        caps.setCapability(CapabilityType.TAKES_SCREENSHOT, true)
        caps.setCapability(CapabilityType.PROXY, phantomjsHttpProxy)
        caps.setCapability(CapabilityType.LOGGING_PREFS, logPreferences)
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, phantomjsCommandLineArguments)
        return caps
    }

    private static getPhantomjsHttpProxy() {
        def proxyUrl = System.getProperty('phantomjs.httpProxy')
        def proxy = new Proxy()
        if (proxyUrl != null && proxyUrl.length() != 0) {
            proxy.setHttpProxy(proxyUrl)
        } else {
            proxy.setProxyType(Proxy.ProxyType.DIRECT)
        }
        return proxy
    }

    private static Capabilities getFirefoxProfile() {
        Capabilities firefoxProfile = new FirefoxProfile()
        firefoxProfile.setPreference('browser.cache.disk.enable', false)
        firefoxProfile.setPreference('browser.cache.memory.enable', false)
        firefoxProfile.setPreference('browser.cache.offline.enable', false)
        firefoxProfile.setPreference('network.http.use-cache', false)
        firefoxProfile.setPreference('browser.download.folderList', 2)
        firefoxProfile.setPreference('browser.download.dir', System.getProperty('java.io.tmpdir') + 'testing')
        firefoxProfile.setPreference('browser.helperApps.neverAsk.saveToDisk',
                'text/csv,text/xml,application/xml,application/pdf,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet')
        return firefoxProfile
    }

    private static getSafariOptions() {
        def options = new SafariOptions()
        options.setUseCleanSession(true)
        options
    }

    private static setWindowSize(WebDriver driver) {
        driver.manage().window().setSize(new Dimension(1920, 1200))
    }

    private static getLogPreferences() {
        def logPrefs = new LoggingPreferences()
        logPrefs.enable(LogType.BROWSER, Level.ALL)
        return logPrefs
    }

    private static File getCustomExecutable(String executable) {
        def customPath = System.getProperty("${executable}.exec.path")
        def customExecutableFile = customPath ? new File(customPath) : null
        boolean isExecutableValid = (customExecutableFile?.exists() && customExecutableFile?.length() > 0)

        return isExecutableValid ? customExecutableFile : null
    }
}