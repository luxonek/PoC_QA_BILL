package PoC.AddRemoveElements

import com.codeborne.selenide.Condition
import com.codeborne.selenide.ElementsCollection
import com.codeborne.selenide.SelenideElement

import static com.codeborne.selenide.Selenide.*
import static com.codeborne.selenide.Condition.*
import static com.codeborne.selenide.CollectionCondition.*



class AddRemoveElementsSelenidePage
{
    SelenideElement addElementButton = $(".example>button")
    ElementsCollection deleteElementButton = $$("button.added-manually")

    def addAndRemoveElements(int addElement, int removeElement) {
        int i = 0
        while (i != addElement) {
            addElementButton.click()
            i++
            deleteElementButton.shouldHaveSize(i)
        }
        i = 0
        while (i != removeElement) {
            deleteElementButton.first().click()
            i++
            deleteElementButton.shouldHaveSize(addElement - i)
        }
        true
    }
}
