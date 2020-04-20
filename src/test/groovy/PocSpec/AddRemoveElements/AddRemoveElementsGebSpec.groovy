package PocSpec.AddRemoveElements

import PoC.AddRemoveElements.AddRemoveElementsGebPage
import PoC.URL.UrlEnum
import PoC.URL.UrlProvider
import PocSpec.BaseSpec
import spock.lang.Unroll

class AddRemoveElementsGebSpec extends BaseSpec {
    @Unroll
    def 'Add and remove elements Simulation - GEB'() {
        when:
        go UrlProvider.getAdress(UrlEnum.ADD_REMOVE_ELEMENTS)
        then:
        at(AddRemoveElementsGebPage).addAndRemoveElements(5,3)
    }
}
