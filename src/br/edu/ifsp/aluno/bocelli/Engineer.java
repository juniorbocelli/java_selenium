package br.edu.ifsp.aluno.bocelli;

import org.openqa.selenium.WebDriver;

import java.util.ArrayList;

public class Engineer {
    private WebDriver webDriver;

    public Engineer(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void tryCreate(String cpf, String nome, String nascimento, String sexo, String salario, ArrayList<String> telefones, ArrayList<String> emails) {

    }
}
