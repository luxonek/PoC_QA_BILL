package PocSpec.ContextMenu

import PoC.ContextMenu.ContextMenuGebPage
import PoC.URL.UrlEnum
import PoC.URL.UrlProvider
import PocSpec.BaseSpec
import spock.lang.Unroll

class ContextMenuGebSpec extends BaseSpec {
    @Unroll
    def 'ContextMenu Simulation - GEB'() {
        when:
        go UrlProvider.getAdress(UrlEnum.CONTEXTMENU)
        then:
        at(ContextMenuGebPage).contextMenuSimulation()
    }
}
