package PoC.DigestAuthentication

import static com.codeborne.selenide.Selectors.*
import static com.codeborne.selenide.Selenide.$
import com.codeborne.selenide.SelenideElement

class DigestAuthenticationSelenidePage {

    SelenideElement element= $(byId("content"))

    SelenideElement footer = $(byId("page-footer"))

    boolean isDisplayed() {
        [element, footer].every { x -> x.displayed }
    }
}
