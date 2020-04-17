package PoC.ContextMenu

import com.codeborne.selenide.SelenideElement
import static com.codeborne.selenide.Selenide.$
import static com.codeborne.selenide.Selenide.switchTo

class ContextMenuSelenidePage {

    SelenideElement box = $("#hot-spot")

    def contextMenuSimulation() {
        box.contextClick()
        switchTo().alert().accept()
        return box.displayed
    }
}