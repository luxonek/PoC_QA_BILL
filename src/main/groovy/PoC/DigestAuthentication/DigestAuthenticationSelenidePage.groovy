package PoC.DigestAuthentication

import com.codeborne.selenide.SelenideElement
import org.openqa.selenium.support.FindBy

class DigestAuthenticationSelenidePage {

    @FindBy(id = "#content")
    SelenideElement element

    @FindBy(id = "#page-footer")
    SelenideElement footer

    boolean isDisplayed() {
        [element, footer].every { x -> x.displayed }
    }
}
