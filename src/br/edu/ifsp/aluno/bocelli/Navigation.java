package br.edu.ifsp.aluno.bocelli;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Navigation {
    private WebDriver webDriver;

    public Navigation(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void startHome() {
        // Abre página inicial
        webDriver.get("http://localhost/oficina/index.html");

        // Maximiza navegador
        webDriver.manage().window().maximize();
    }

    public void navigateToHome() {
        webDriver.findElement(By.className("navbar-brand")).click();
    }

    public void navigateToEngineersList() {
        webDriver.findElement(By.id("navMecanicos")).click();
        webDriver.findElement(By.xpath("//a[@href='mecanicos.html']")).click();
    }

    public void navigateToNewEngineer() {
        webDriver.findElement(By.id("navMecanicos")).click();
        webDriver.findElement(By.xpath("//a[@href='novo-mecanico.html']")).click();
    }

    public void navigateToVehiclesList() {
        webDriver.findElement(By.id("navVeiculos")).click();
        webDriver.findElement(By.xpath("//a[@href='veiculos.html']")).click();
    }

    public void navigateToNewVehicle() {
        webDriver.findElement(By.id("navVeiculos")).click();
        webDriver.findElement(By.xpath("//a[@href='novo-veiculo.html']")).click();
    }
}
