package PocSpec.AddRemoveElements

import PoC.AddRemoveElements.AddRemoveElementsSelenidePage
import PoC.DigestAuthentication.DigestAuthenticationSelenidePage
import PoC.URL.UrlEnum
import PoC.URL.UrlProvider
import com.codeborne.selenide.AuthenticationType
import spock.lang.Specification
import spock.lang.Unroll

import static com.codeborne.selenide.Selenide.open

class AddRemoveElementsSelenideSpec extends Specification {

    @Unroll
    def 'Add and remove elements Simulation - Selenide'() {
        when:
        open(UrlProvider.getAdress(UrlEnum.ADD_REMOVE_ELEMENTS))
        then:
        new AddRemoveElementsSelenidePage().addAndRemoveElements(5,3)

    }
}
