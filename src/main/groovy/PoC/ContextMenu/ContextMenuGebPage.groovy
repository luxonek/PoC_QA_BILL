package PoC.ContextMenu

import geb.Page

class ContextMenuGebPage extends Page {
    static content = {
        boxElement(wait: true) { $("#hot-spot") }
    }
    static at = {
        boxElement.displayed
    }

    def contextMenuSimulation() {
        interact { contextClick(boxElement) }
        driver.switchTo().alert().accept()
        at
    }
}
