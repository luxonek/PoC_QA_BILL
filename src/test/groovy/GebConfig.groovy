import static utils.geb.GebConfigUtils.*

System.setProperty("webdriver.gecko.driver","C:\\geckodriver\\geckodriver.exe")
reportsDir = 'target/geb-reports'

waiting {
    timeout = 40
    retryInterval = 0.4
    includeCauseInMessage = true
    atCheckWaiting = true
}

driver = {
    getChromeDriver()
}

environments {
    phantomjs {
        driver = { getPhantomJsDriver() }
    }
    chrome {
        driver = { getChromeDriver() }
    }
    headlessChrome {
        driver = { getChromeDriver(null, true) }
    }
    firefox {
        driver = { getFirefoxDriver() }
    }
    safari {
        driver = { getSafariDriver() }
    }
    firefoxGrid {
        driver = { getRemoteFirefoxDriver() }
    }
    chromeGrid {
        driver = { getRemoteChromeDriver() }
    }
}