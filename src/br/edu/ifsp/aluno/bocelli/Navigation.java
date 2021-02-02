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

        // Mensagens
        if(webDriver.getCurrentUrl().compareTo("http://localhost/oficina/index.html") == 0) {
            System.out.println("Iniciou corretamente na página: " + "http://localhost/oficina/index.html");
        } else {
            System.out.println("Error de navegação:");
            System.out.println("    - Era esperado iniciar em 'http://localhost/oficina/index.html', porém: " + webDriver.getCurrentUrl());
        }
    }

    public void navigateToHome() {
        webDriver.findElement(By.className("navbar-brand")).click();

        // Mensagens
        if(webDriver.getCurrentUrl().compareTo("http://localhost/oficina/index.html") == 0) {
            System.out.println("Navegou corretamente para: " + "http://localhost/oficina/index.html");
        } else {
            System.out.println("Error de navegação:");
            System.out.println("    - Era esperado 'http://localhost/oficina/index.html', porém: " + webDriver.getCurrentUrl());
        }
    }

    public void navigateToEngineersList() {
        webDriver.findElement(By.id("navMecanicos")).click();
        webDriver.findElement(By.xpath("//a[@href='mecanicos.html']")).click();

        // Mensagens
        if(webDriver.getCurrentUrl().compareTo("http://localhost/oficina/mecanicos.html") == 0) {
            System.out.println("Navegou corretamente para: " + "http://localhost/oficina/mecanicos.html");
        } else {
            System.out.println("Error de navegação:");
            System.out.println("    - Era esperado 'http://localhost/oficina/mecanicos.html', porém: " + webDriver.getCurrentUrl());
        }
    }

    public void navigateToNewEngineer() {
        webDriver.findElement(By.id("navMecanicos")).click();
        webDriver.findElement(By.xpath("//a[@href='novo-mecanico.html']")).click();

        // Mensagens
        if (webDriver.getCurrentUrl().compareTo("http://localhost/oficina/novo-mecanico.html") == 0) {
            System.out.println("Navegou corretamente para: " + "http://localhost/oficina/novo-mecanico.html");
        } else {
            System.out.println("Error de navegação:");
            System.out.println("    - Era esperado 'http://localhost/oficina/novo-mecanico.html', porém: " + webDriver.getCurrentUrl());
        }
    }

    public void navigateToVehiclesList() {
        webDriver.findElement(By.id("navVeiculos")).click();
        webDriver.findElement(By.xpath("//a[@href='veiculos.html']")).click();

        // Mensagens
        if(webDriver.getCurrentUrl().compareTo("http://localhost/oficina/veiculos.html") == 0) {
            System.out.println("Navegou corretamente para: " + "http://localhost/oficina/veiculos.html");
        } else {
            System.out.println("Error de navegação:");
            System.out.println("    - Era esperado 'http://localhost/oficina/veiculos.html', porém: " + webDriver.getCurrentUrl());
        }
    }

    public void navigateToNewVehicle() {
        webDriver.findElement(By.id("navVeiculos")).click();
        webDriver.findElement(By.xpath("//a[@href='novo-veiculo.html']")).click();

        // Mensagens
        if (webDriver.getCurrentUrl().compareTo("http://localhost/oficina/novo-veiculo.html") == 0) {
            System.out.println("Navegou corretamente para: " + "http://localhost/oficina/novo-veiculo.html");
        } else {
            System.out.println("Error de navegação:");
            System.out.println("    - Era esperado 'http://localhost/oficina/novo-veiculo.html', porém: " + webDriver.getCurrentUrl());
        }
    }
}
