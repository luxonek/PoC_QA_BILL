package PoC.DigestAuthentication

import com.codeborne.selenide.SelenideElement

import static com.codeborne.selenide.Selenide.$

class DigestAuthenticationSelenidePage {

    SelenideElement element = $("#content")

    SelenideElement footer = $("#page-footer")

    boolean isDisplayed() {
        [element, footer].every { x -> x.displayed }
    }
}
