package PoC.AddRemoveElements

import geb.Page

class AddRemoveElementsGebPage extends Page {
    static content = {
        addElementButton(wait: true) { $(".example>button") }
        deleteElementButton(wait: true) { $("button.added-manually") }
    }
    static at = {
        addElementButton.displayed
    }

    def addAndRemoveElements(int addElement, int removeElement) {
        int i = 0
        while (i != addElement) {
            addElementButton.click()
            i++
            assert deleteElementButton.size() == i
        }
        i = 0
        while (i != removeElement) {
            deleteElementButton[0].click()
            i++
            assert deleteElementButton.size() == (addElement - i)
        }
        true
    }
}
