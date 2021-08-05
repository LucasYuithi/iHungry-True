
import java.util.Scanner;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class iHungry {

    public static String cpf, nome, email, endereco; // variáveis globais para serem utilizadas quando logado

// função main que representa o menu principal.
    public static void main(String[] args) throws IOException {
        menu_principal();

    }

    // função para levar a opção do usuário para função 1, 2 ou para sair o programa.
    public static void menu_principal() throws IOException {

        System.out.println("Bem vindo(a) ao iHungry, onde seu pedido é nossa prioridade !! ");
        System.out.println("Novo usuário? Não há problemas!!                           Digite 1 para de cadastrar.");
        System.out.println("Já é usuário? Goela abaixo !!                              Digite 2 para entrar e matar sua fome.");
        System.out.println("Sair? Esperamos que você tenha tido a melhor experiência!! Digite 3 para sair.");

        BufferedWriter fw = new BufferedWriter(new FileWriter("Lista de Cadastro")); // inicia o sistema de cadastro
        File arquivo = new File("Usuarios.txt");
        arquivo.createNewFile();

        // captar a escolha do usuário.
        Scanner buffer = new Scanner(System.in);
        String escolha;
        escolha = buffer.nextLine();

        while (!"1".equals(escolha) && !"2".equals(escolha) && !"3".equals(escolha)) {//evitar escolhas além do 1, 2 ou 3

            System.out.println("Comando Inválido, tente de novo");
            escolha = buffer.nextLine();

        }

        switch (escolha) {
            case ("1"):

                sistema_de_cadastro();

                break;
            case ("2"):

                login();

                break;

            case ("3"):
                System.out.println("Muito Obrigado pela preferência, estaremos lhe esperando de volta!!");

                System.exit(0);
                break;

        }
        buffer.close();
    }

    public static void sistema_de_cadastro() throws IOException {

        Scanner buffer = new Scanner(System.in);

        boolean CPF_invalido;

        System.out.println("Bora se Cadastrar!!");
        System.out.println("Manda seu CPF");

        String CPF;

        do {
            System.out.println("CPF: ");//testar se o CPF ja esta cadastrado
            CPF = buffer.nextLine();

            if (teste_de_CPF(CPF)) {
                CPF_invalido = false;
            } else {
                CPF_invalido = true;
                System.out.println("CPF Inválido");
            }

        } while (CPF_invalido);

        String email, nome, endereco, senha;

        System.out.println("Agora, seu e-mail");
        email = buffer.nextLine();
        System.out.println("Fala seu nome");
        nome = buffer.nextLine();
        System.out.println("Digita seu endereço");
        endereco = buffer.nextLine();
        System.out.println("E para finalizar, sua senha");
        senha = buffer.nextLine();

        BufferedWriter fw = new BufferedWriter(new FileWriter("Usuarios.txt", true));

        PrintWriter pw = new PrintWriter(fw);

        try {
            pw.println(CPF + ";" + senha + ";" + email + ";" + nome + ";" + endereco);
            pw.close();
        } catch (Exception e) {
            System.out.println("Erro na gravação do arquivo");
        }

        login();//retorna para o login
        buffer.close();

    }

    public static boolean teste_de_CPF(String CPF) throws IOException {

        if (String.valueOf(CPF).length() != 11 || !CPF.matches("^[0-9]*$")) {

            return (false);
        }
        BufferedReader fr = new BufferedReader(new FileReader("Usuarios.txt"));

        try {
            String linha = fr.readLine();
            String CPF_arquivo;
            while (linha != null) {
                String[] campos = linha.split(";");
                CPF_arquivo = campos[0];
                if (CPF.equals(CPF_arquivo)) {
                    fr.close();
                    return (true);
                }

                linha = fr.readLine();

            }
            fr.close();
        } catch (Exception e) {
            System.out.println("Erro na leitura do arquivo");
        }
        return (true);
    }

    public static void login() throws IOException {
        Scanner buffer = new Scanner(System.in);

        boolean login_invalido;

        System.out.println("______________________________________________________________________________");
        System.out.println("Bora fazer o login!!");
        System.out.println("Manda o CPF e a senha!!");
        System.out.println("______________________________________________________________________________");

        String CPF;
        String senha;
        String option; //opcao para o usuario poder sair do looping do login

        do {
            System.out.println("CPF: ");//testar se o CPF ja esta cadastrado
            CPF = buffer.nextLine();
            System.out.println("Senha: ");
            senha = buffer.nextLine();
            if (teste_de_login(CPF, senha) == 1) {
                login_invalido = false;
            } else {
                login_invalido = true;
                System.out.println("Login Inválido");
                System.out.println("Caso queira voltar ao Menu principal            Digite 1");
                System.out.println("Caso queira tentar novamente                    Digite qualquer coisa");
                option = buffer.nextLine();
                if ("1".equals(option)) {
                    menu_principal();

                }

            }
        } while (login_invalido);

        menu_restaurante();
        buffer.close();

    }

    public static int teste_de_login(String CPF, String senha) throws IOException {

        BufferedReader fr = new BufferedReader(new FileReader("Usuarios.txt"));

        try {
            String linha = fr.readLine();
            String CPF_arquivo;
            String senha_arquivo;
            while (linha != null) {
                String[] campos = linha.split(";");
                CPF_arquivo = campos[0];
                senha_arquivo = campos[1];

                if (CPF_arquivo.equals(CPF) && senha_arquivo.equals(senha)) {
                    cpf = campos[1];//captar as variáveis globais
                    email = campos[2];
                    nome = campos[3];
                    endereco = campos[4];
                    fr.close();
                    return (1);
                }

                linha = fr.readLine();

            }
            fr.close();
        } catch (Exception e) {
            System.out.println("Erro na leitura do arquivo");
        }
        return (0);
    }

    public static void menu_restaurante() throws IOException {

        menu_restaurante_escolha();

    }

    // função para levar a opção do usuário para os restaurantes 1, 2 ou 3.
    public static void menu_restaurante_escolha() throws IOException {

        System.out.println("Escolha entre as opções disponíveis: ");
        System.out.println("______________________________________________________________________________");
        System.out.println("Burger Queen                        Digite 1 para matar a fome de uma rainha.");
        System.out.println("Mec Donalds                         Digite 2 para deixar seu estômago feliz.");
        System.out.println("Popais                              Digite 3 para ficar forte como o Pai.");
        System.out.println("______________________________________________________________________________");

        // captar a escolha do usuário.
        Scanner buffer = new Scanner(System.in);
        String escolha;
        escolha = buffer.nextLine();

        while (!"1".equals(escolha) && !"2".equals(escolha) && !"3".equals(escolha)) {//evitar escolhas além do 1, 2 ou 3

            System.out.println("Comando Inválido, tente de novo");
            escolha = buffer.nextLine();

        }

        switch (escolha) {
            case ("1"):

                Burguer_Queen();

                break;
            case ("2"):

                Mec_Donalds();

                break;

            case ("3"):

                Popais();

                break;

        }
        buffer.close();
    }

    public static void Burguer_Queen() throws IOException {

    }

    public static void Mec_Donalds() throws IOException {

    }

    public static void Popais() throws IOException {

    }

}
