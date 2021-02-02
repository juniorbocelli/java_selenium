package br.edu.ifsp.aluno.bocelli;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class Engineer {
    private WebDriver webDriver;
    private ErrorHandler errorHandler;
    private Navigation navigation;

    public Engineer(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.errorHandler = new ErrorHandler(this.webDriver);
        this.navigation = new Navigation(this.webDriver);
    }

    public void tryCreate(String cpf, String nome, String nascimento, String sexo, String salario, String[] telefones, String[] emails) throws InterruptedException {
        // Verifica se está na pagina correta
        if(webDriver.getTitle().compareTo("Oficina Mecânica - Salvar Mecânico") != 0) {
            navigation.navigateToEngineersList();
        }

        webDriver.findElement(By.id("cpf")).sendKeys(cpf);
        webDriver.findElement(By.id("nome")).sendKeys(nome);
        webDriver.findElement(By.id("nascimento")).sendKeys(nascimento);

        List<WebElement> radioElements = new ArrayList<>(webDriver.findElements(By.name("sexo")));

        for(WebElement element : radioElements) {
            if(element.getAttribute("value").compareTo(sexo) == 0) {
                JavascriptExecutor javascriptExecutor = (JavascriptExecutor)webDriver;
                javascriptExecutor.executeScript("arguments[0].click();", element);
            }
        }

        webDriver.findElement(By.id("salario")).sendKeys(salario);

        // Telefones
        if(telefones.length > 0) {
            webDriver.findElements(By.name("telefone")).get(0).sendKeys(telefones[0]);
        }

        for(int i = 1; i < telefones.length; i++) {
            webDriver.findElement(By.id("novoTelefone")).click();
            webDriver.findElements(By.name("telefone")).get(i).sendKeys(telefones[i]);
        }

        // E-mails
        if(emails.length > 0) {
            webDriver.findElements(By.name("email")).get(0).sendKeys(emails[0]);
        }

        for(int i = 1; i < emails.length; i++) {
            webDriver.findElement(By.id("novoEmail")).click();
            webDriver.findElements(By.name("email")).get(i).sendKeys(emails[i]);
        }

        // Tenta enviar form
        webDriver.findElement(By.id("sendForm")).click();

        // Verifica se houve o cadastro
        if(webDriver.getCurrentUrl().compareTo("http://localhost/oficina/mecanicos.html") != 0) {
            // Erros no form
            errorHandler.errorInForm("meuForm", webDriver.getTitle());

            // Erros gerais
            errorHandler.otherErrors("meuForm", webDriver.getTitle());
        } else {
            System.out.println("Mecânico salvo com sucesso!");
        }
    }

    public void tryEdit(String index, String cpf, String nome, String nascimento, String sexo, String salario, String[] telefones, String[] emails) throws InterruptedException {
        // Verifica se está na pagina correta
        if(webDriver.getCurrentUrl().compareTo("http://localhost/oficina/mecanicos.html") != 0) {
            navigation.navigateToEngineersList();
        }

        // Tenta selecionar a linha
        List<WebElement> listElements = new ArrayList<>(webDriver.findElements(By.cssSelector("button[data-index_editar='" + index + "']")));
        if(listElements.size() > 0) {
            listElements.get(0).click();
        } else {
            System.out.println("Foi encontrado um erro:");
            System.out.println("    - Não existe o ítem requerido");
        }

        if(webDriver.getCurrentUrl().compareTo("http://localhost/oficina/novo-mecanico.html?a=e&id=" + index) != 0) {
            System.out.println("Foi encontrado um erro:");
            System.out.println("    - A url " + webDriver.getCurrentUrl() + " não é a esperada!");

            webDriver.quit();
        }

        tryCreate(cpf, nome, nascimento, sexo, salario, telefones, emails);
    }

    public void tryDelete(String index) {
        // Verifica se está na pagina correta
        if(webDriver.getCurrentUrl().compareTo("http://localhost/oficina/mecanicos.html") != 0) {
            navigation.navigateToEngineersList();
        }

        // Tenta selecionar a linha
        List<WebElement> listElements = new ArrayList<>(webDriver.findElements(By.cssSelector("button[data-index_editar='" + index + "']")));
        if(listElements.size() > 0) {
            listElements.get(0).click();
        } else {
            System.out.println("Foi encontrado um erro:");
            System.out.println("    - Não existe o ítem requerido");
        }
    }
}
