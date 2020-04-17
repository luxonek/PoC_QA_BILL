package PoC.URL

class UrlProvider {
    static Addresses = [
            (UrlEnum.DIGEST_AUTHENTICATION): "https://the-internet.herokuapp.com/digest_auth",
            (UrlEnum.CONTEXTMENU):"https://the-internet.herokuapp.com/context_menu"
    ]

    static String getAdress(UrlEnum urlEnum){
        Addresses[urlEnum]
    }
}