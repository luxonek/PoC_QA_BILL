package PocSpec.ContextMenu

import PoC.ContextMenu.ContextMenuSelenidePage
import PoC.URL.UrlEnum
import PoC.URL.UrlProvider
import spock.lang.Specification

import static com.codeborne.selenide.Selenide.open

class ContextMenuSelenideSpec extends Specification {

    def 'ContextMenu Simulation - Selenide'() {
        when:
        open(UrlProvider.getAdress(UrlEnum.CONTEXTMENU))
        then:
        new ContextMenuSelenidePage().contextMenuSimulation()
    }
}
