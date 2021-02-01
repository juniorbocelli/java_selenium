package br.edu.ifsp.aluno.bocelli;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\junio\\IdeaProjects\\TPC1_Prova_2\\lib\\chrome\\chromedriver.exe");

        // Funciona como se fosse a instância do navegador
        WebDriver webDriver = new ChromeDriver();

        // Abre o navegador e entra em página
        webDriver.get("https://google.com");

        // Maximiza navegador
        webDriver.manage().window().maximize();

        // Espera milisegundos
        Thread.sleep(5000);

        // Fecha o navegador (clse() fecha apenas a aba)
        webDriver.quit();
    }
}
