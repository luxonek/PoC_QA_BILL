package PoC.DigestAuthentication

import geb.Page

class DigestAuthenticationGebPage extends Page {
    static String username = "admin"
    static String password = "admin"

    def login(String username = this.username, String password = this.password) {
        driver.get(driver.getCurrentUrl().replace("https://", "https://$username:$password@"))
        return true
    }

    static content = {
        pageContent(wait: true) { $("#content") }
        pageFooter(wait: true) { $("#page-footer") }
    }

    static at = {
        login()
        pageContent.displayed
        pageFooter.displayed
    }
}