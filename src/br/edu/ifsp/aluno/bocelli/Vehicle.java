package br.edu.ifsp.aluno.bocelli;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class Vehicle {
    private WebDriver webDriver;
    private ErrorHandler errorHandler;
    private Navigation navigation;

    public Vehicle(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.errorHandler = new ErrorHandler(this.webDriver);
        this.navigation = new Navigation(this.webDriver);
    }

    public void tryCreate(String placaLetras, String placaNumeros, String placaEstado, String placaCidade, String tipo, String marca, String modelo, String ano, String portas, String lugares, String combustivel, String cor, String[] acessorios) throws InterruptedException {
        // Verifica se está na pagina correta
        if(webDriver.getTitle().compareTo("Oficina Mecânica - Salvar Veículo") != 0) {
            navigation.navigateToNewVehicle();
        }

        // Letras
        webDriver.findElement(By.id("placaLetras")).sendKeys(placaLetras);

        // Números
        webDriver.findElement(By.id("placaNumeros")).sendKeys(placaNumeros);

        // Estado
        WebElement dropDown1 = webDriver.findElement(By.id("placaEstado"));
        Select placaEstadoSelect = new Select(dropDown1);
        placaEstadoSelect.selectByValue("placaEstado");

        // Cidade
        WebElement dropDown2 = webDriver.findElement(By.id("placaCidade"));
        Select placaCidadeSelect = new Select(dropDown2);
        placaCidadeSelect.selectByValue("placaCidade");

        // Tipo
        WebElement dropDown3 = webDriver.findElement(By.id("tipo"));
        Select tipoSelect = new Select(dropDown3);
        tipoSelect.selectByValue("tipo");

        // Marca
        WebElement dropDown4 = webDriver.findElement(By.id("marca"));
        Select marcaSelect = new Select(dropDown4);
        marcaSelect.selectByValue("marca");

        // Modelo
        WebElement dropDown5 = webDriver.findElement(By.id("modelo"));
        Select modeloSelect = new Select(dropDown5);
        modeloSelect.selectByValue("modelo");

        // Ano
        webDriver.findElement(By.id("ano")).sendKeys(ano);

        // Portas
        webDriver.findElement(By.id("portas")).sendKeys(portas);

        // Lugares
        webDriver.findElement(By.id("lugares")).sendKeys(lugares);

        // Combustível
        WebElement dropDown6 = webDriver.findElement(By.id("combustivel"));
        Select combustivelSelect = new Select(dropDown6);
        combustivelSelect.selectByValue("combustivel");

        // Cor
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor)webDriver;
        WebElement corElement = webDriver.findElement(By.id("cor"));
        javascriptExecutor.executeScript("arguments[0].setAttribute('value', '" + cor +"')", corElement);

        // Acessórios
        if(acessorios.length > 0) {
            webDriver.findElements(By.name("acessorio")).get(0).sendKeys(acessorios[0]);
        }
        for(int i = 1; i < acessorios.length; i++) {
            webDriver.findElement(By.id("novoAcessorio")).click();
            webDriver.findElements(By.name("acessorio")).get(i).sendKeys(acessorios[i]);
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

    public void tryEdit(String index, String placaLetras, String placaNumeros, String placaEstado, String placaCidade, String tipo, String marca, String modelo, String ano, String portas, String lugares, String combustivel, String cor, String[] acessorios) throws InterruptedException {
        // Verifica se está na pagina correta
        if(webDriver.getCurrentUrl().compareTo("http://localhost/oficina/veiculos.html") != 0) {
            navigation.navigateToVehiclesList();
        }

        // Tenta selecionar a linha
        List<WebElement> listElements = new ArrayList<>(webDriver.findElements(By.cssSelector("button[data-index_editar='" + index + "']")));
        if(listElements.size() > 0) {
            listElements.get(0).click();
        } else {
            System.out.println("Foi encontrado um erro:");
            System.out.println("    - Não existe o ítem requerido");
        }

        if(webDriver.getCurrentUrl().compareTo("http://localhost/oficina/novo-veiculo.html?a=e&id=" + index) != 0) {
            System.out.println("Foi encontrado um erro:");
            System.out.println("    - A url " + webDriver.getCurrentUrl() + " não é a esperada!");

            webDriver.quit();
        }

        tryCreate(placaLetras, placaNumeros, placaEstado, placaCidade, tipo, marca, modelo, ano, portas, lugares, combustivel, cor, acessorios);
    }

    public void tryDelete(String index) {
        // Verifica se está na pagina correta
        if(webDriver.getCurrentUrl().compareTo("http://localhost/oficina/veiculos.html") != 0) {
            navigation.navigateToVehiclesList();
        }

        // Tenta selecionar a linha
        List<WebElement> listElements = new ArrayList<>(webDriver.findElements(By.cssSelector("button[data-index_editar='" + index + "']")));
        if(listElements.size() > 0) {
            listElements.get(0).click();
        } else {
            System.out.println("Foi encontrado um erro:");
            System.out.println("    - Não existe o ítem requerido!");

            webDriver.quit();
        }

        // Célula do botão Excluir
        WebElement td = listElements.get(0).findElement(By.xpath("./.."));
        WebElement tr = td.findElement(By.xpath("./.."));

        String placaDeletada = tr.findElements(By.tagName("td")).get(0).getText();

        // Procura botão do modal
        WebElement modalButton = webDriver.findElement(By.className("modal")).findElement(By.cssSelector("button[class='btn btn-primary']"));

        // Testa se está visível
        if(!modalButton.isDisplayed()) {
            System.out.println("Foi encontrado um erro:");
            System.out.println("    - Modal de exclusão não foi aberto!");
        }

        // Envia clique de confirmação
        modalButton.click();

        // Verifica se o ítem foi excluído
        List<WebElement> e = webDriver.findElements(By.xpath("//*[text()='" + placaDeletada + "']"));

        if(e.size() == 0) {
            System.out.println("Elemento com Placa " + placaDeletada + " foi deletado com sucesso!");
        } else {
            System.out.println("Foi encontrado um erro:");
            System.out.println("    - Erro ao tentar excluir a Placa " + placaDeletada + "!");
        }
    }
}