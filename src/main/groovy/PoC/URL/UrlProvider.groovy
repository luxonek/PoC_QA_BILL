package PoC.URL

class UrlProvider {
    static Addresses = [
            (UrlEnum.DIGEST_AUTHENTICATION): "https://the-internet.herokuapp.com/digest_auth",
    ]

    static String getAdress(UrlEnum urlEnum){
        Addresses[urlEnum]
    }
}