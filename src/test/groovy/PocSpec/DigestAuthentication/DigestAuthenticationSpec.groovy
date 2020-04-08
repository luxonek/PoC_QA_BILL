package PocSpec.DigestAuthentication

import PoC.DigestAuthentication.DigestAuthenticationGebPage
import PoC.URL.UrlEnum
import PoC.URL.UrlProvider
import PocSpec.BaseSpec
import spock.lang.Unroll

class DigestAuthenticationSpec extends BaseSpec{
    @Unroll
    def 'Digest Authentication Spec'(){
        when:
        go UrlProvider.getAdress(UrlEnum.DIGEST_AUTHENTICATION)
        then:
        at(DigestAuthenticationGebPage)
    }
}
