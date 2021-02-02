package br.edu.ifsp.aluno.bocelli;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\junio\\IdeaProjects\\TPC1_Prova_2\\lib\\chrome\\chromedriver.exe");

        // Funciona como se fosse a instância do navegador
        WebDriver webDriver = new ChromeDriver();

        // Iniciando classes
        Navigation navigation = new Navigation((webDriver));
        Engineer engineer = new Engineer(webDriver);

        /**
         * Testes de navegação
         */
        navigation.startHome();

        /*
        navigation.navigateToEngineersList();
        navigation.navigateToNewEngineer();

        navigation.navigateToVehiclesList();
        navigation.navigateToNewVehicle();

        navigation.navigateToHome();
        */

        /**
         * Testes de Mecânico
         */
        navigation.navigateToNewEngineer();

        // Lista inicial de telefones e e-mails
        String[] telefones = {"(16) 2222-2222", "(16) 3333-2222"};
        String[] emails = {"jose@teste.com", "bocelli@teste.com"};

        // Cadastro com sucesso
        engineer.tryCreate("223.227.558-26", "Bocelli", "06-06-1982", "M", "6000,00", telefones, emails);

        navigation.navigateToNewEngineer();

        // Cadastro com cpf duplicado
        engineer.tryCreate("223.227.558-26", "Bocelli", "06-06-1982", "M", "6000,00", telefones, emails);

        webDriver.navigate().refresh();

        // Simula erro nos campos
        telefones = new String[] {"(16) 2222-2222", "(16) 3333-2222", "(16) 33332222", ""};
        emails = new String[] {"jose@teste.com", "bocellitestecom", ""};
        engineer.tryCreate("223.227.55826", "", "06-06-3000", "", "R$", telefones, emails);

        Thread.sleep(5000);

        // Fecha o navegador (clse() fecha apenas a aba)
        webDriver.quit();
    }
}
