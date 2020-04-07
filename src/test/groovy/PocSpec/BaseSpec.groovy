package PocSpec

import geb.spock.GebReportingSpec

class BaseSpec extends GebReportingSpec {
    def setup() {
        driver.manage().window().maximize()
        def verCode = UUID.randomUUID().toString()
        println verCode
    }
}