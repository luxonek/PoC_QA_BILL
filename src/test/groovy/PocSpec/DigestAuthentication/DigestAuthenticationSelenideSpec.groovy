package PocSpec.DigestAuthentication

import PoC.DigestAuthentication.DigestAuthenticationSelenidePage
import PoC.URL.UrlEnum
import PoC.URL.UrlProvider
import com.codeborne.selenide.AuthenticationType
import spock.lang.Specification
import spock.lang.Unroll

import static com.codeborne.selenide.Selenide.open

class DigestAuthenticationSelenideSpec extends Specification {
    @Unroll
    def 'test'() {
        when:
        DigestAuthenticationSelenidePage page = open(UrlProvider.getAdress(UrlEnum.DIGEST_AUTHENTICATION),
                AuthenticationType.DIGEST,
                "admin", "admin")
        then:
        page.isDisplayed()
    }
}
