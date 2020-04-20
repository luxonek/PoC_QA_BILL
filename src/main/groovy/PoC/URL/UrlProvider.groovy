package PoC.URL

class UrlProvider {
    static Addresses = [
            (UrlEnum.ADD_REMOVE_ELEMENTS)  : "add_remove_elements/",
            (UrlEnum.DIGEST_AUTHENTICATION): "digest_auth",
            (UrlEnum.CONTEXTMENU)          : "context_menu"
    ]

    static String getAdress(UrlEnum urlEnum) {
        "https://the-internet.herokuapp.com/" + Addresses[urlEnum]
    }
}