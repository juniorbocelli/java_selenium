package br.edu.ifsp.aluno.bocelli;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class ErrorHandler {
    private WebDriver webDriver;

    public ErrorHandler(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void errorInForm(String form, String title) {
        List<WebElement> errorsList = new ArrayList<>(webDriver.findElements(By.className("invalid-feedback")));
        System.out.println("Erros foram encontrados no formulário " + form + "na página com título " + title + ":");

        for(WebElement error : errorsList) {
            if(error.isDisplayed()) {
                System.out.println("    - Elemento '" + error.getAttribute("data-for") + "': " + error.getText());
            }
        }
    }

    public void otherErrors(String form, String title) {
        WebElement errorMessage = webDriver.findElement(By.id("avisoErro"));
        if(errorMessage.isDisplayed()) {
            System.out.println("Erros gerais foram encontrados no formulário " + form + " na página com título " + title + ":");
            System.out.println("    - Mensagem: " + errorMessage.getText());
        }
    }
}
