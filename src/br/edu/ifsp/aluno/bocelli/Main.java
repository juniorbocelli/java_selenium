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
        Vehicle vehicle = new Vehicle(webDriver);

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
        // Lista inicial de telefones e e-mails
        /*
        String[] telefones = {"(16) 2222-2222", "(16) 3333-2222"};
        String[] emails = {"jose@teste.com", "bocelli@teste.com"};

        // Cadastro com sucesso
        engineer.tryCreate("223.227.558-26", "Bocelli", "06-06-1982", "M", "6000,00", telefones, emails);

        // Edita dado cadastrado
        engineer.tryEdit("0","223.227.558-26", "José Paulo Bocelli Júnior", "06-06-1982", "M", "8000,00", telefones, emails);

        // Exclui dado cadastrado
        engineer.tryDelete("0");

        Thread.sleep(15000);

        // Cadastro com cpf duplicado
        engineer.tryCreate("223.227.558-26", "Bocelli", "06-06-1982", "M", "10000,00", telefones, emails);

        // Simula erros nos campos
        telefones = new String[] {"(16) 2222-2222", "(16) 3333-2222", "(16) 33332222", ""};
        emails = new String[] {"jose@teste.com", "bocellitestecom", ""};
        engineer.tryCreate("223.227.55826", "", "06-06-3000", "", "R$", telefones, emails);
        */

        /**
         * Testes de Veículo
         */
        // Lista inicial de acessórios
        String[] acessorios = {"Ar condicionado", "Trava elétrica"};

        // Cadastro com sucesso
        vehicle.tryCreate("abc", "1234", "SP", "SAO CARLOS", "AUTOMOVEL", "FIAT", "UNO", "2000", "5", "5", "GASOLINA", "000000", acessorios);

        // Edita dado cadastrado
        //vehicle.tryEdit("0", "abc", "1234", "SP", "IBATE", "AUTOMOVEL", "PEUGEOT", "106", "2000", "5", "5", "GASOLINA", "000000", acessorios);

        // Exclui dado cadastrado
        vehicle.tryDelete("0");

        // Cadastro com placa duplicada
        // vehicle.tryCreate("abc", "1234", "SP", "SAO CARLOS", "AUTOMOVEL", "FIAT", "UNO", "2000", "5", "5", "GASOLINA", "000000", acessorios);


        // Simula erros nos campos
        vehicle.tryCreate("bc", "124", "", "", "", "", "", "3000", "0", "0", "", "000000", acessorios);
        Thread.sleep(15000);

        // Fecha o navegador (clse() fecha apenas a aba)
        webDriver.quit();
    }
}
