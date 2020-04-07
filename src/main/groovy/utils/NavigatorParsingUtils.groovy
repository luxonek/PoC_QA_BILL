package utils

import geb.navigator.Navigator

class NavigatorParsingUtils {

    static String parseNumeric(String source) {
        source.find(/(\d+[,.]*\d*)/)
    }

    static Integer parseInt(Navigator navigator) {
        Integer.parseInt(parseNumeric(navigator.text()))
    }

    static BigDecimal parseBigDecimal(Navigator navigator) {
        parseNumeric(navigator.text()).toBigDecimal()
    }

}
