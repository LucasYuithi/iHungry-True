
import java.util.Date;
import java.util.Scanner;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class iHungry {

    public static String cpf, nome, email, endereco,escolha; // variáveis globais para serem utilizadas quando logado e para fazer escolhas durante a etapa do pedido
    public static int valor, troco; // variavel global tipo int para o valor final da compra

// função main que representa o menu principal.
    public static void main(String[] args) throws IOException {
        menu_principal();
    }

    // função para levar a opção do usuário para função 1, 2 ou para sair o programa.
    public static void menu_principal() throws IOException {

        System.out.println("Bem vindo(a) ao iHungry, onde seu pedido é nossa prioridade !! ");
        System.out.println("Novo usuário? Não há problemas!!                           Digite 1 para se cadastrar.");
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
    // função para levar a opção do usuário para os restaurantes 1, 2 ou 3.
    public static void menu_restaurante() throws IOException 
    {
        String op; // declara uma variavel String
        do // cria um laco de repeticao que soh vai acabar quando o cliente selecionar a opcoa "Tenho certeza que quero pedir comida neste restaurante" enquanto isso vai permitir que altere o restaurante que deseja pedir
        {
            System.out.println("Escolha entre as opções disponíveis: ");
            System.out.println("______________________________________________________________________________");
            System.out.println("Hamburgueria                        Digite 1 para matar a fome de uma rainha.");
            System.out.println("Pizzaria                            Digite 2 para deixar seu estômago feliz."); //exibe as opcoes de restaurantes para fazer o pedido
            System.out.println("Restaurante Japonês                 Digite 3 para ficar forte como o Pai.");
            System.out.println("______________________________________________________________________________");

            // captar a escolha do usuário.
            Scanner buffer = new Scanner(System.in);
            escolha = buffer.nextLine();

            while (escolha.equals("1")==false && escolha.equals("2")==false && escolha.equals("3")==false) {//evitar escolhas além do 1, 2 ou 3

                System.out.println("Comando Inválido, tente de novo");
                escolha = buffer.nextLine();

            }
            System.out.print("\n");
            cardapio(); // exibe pro cliente o cardapio do restaurante escolhido
            System.out.println("\nDigite o número da opcao correspondente");
            System.out.println("1 - Tenho certeza que quero pedir comida neste restaurante"); //pergunta se o cliente deseja ver outros restaurantes ou se ele tem certeza que quer pedir nesse restaurante
            System.out.println("2 - Quero voltar a selecao de restaurantes");
            System.out.print("opcao:");
            op = buffer.nextLine();// pede pro cliente a opcao que ele deseja
            while (op.equals("1")== false && op.equals("2")== false) //verifica se a opcao digitada eh valida. caso nao seja prende o cliente num loop ate ele digitar uma opcao valida
            {
                System.out.println("Opção digitada não encontrada, Digite novamente por favor");
                System.out.print("opcao:"); // informa que a opcao escolhida é invalida e pede pra ele escolher uma nova opcao
                op = buffer.nextLine();
            }
            System.out.print("\n");
        }while(op.equals("1")== false);
        resumo(); // chama o metodo que vai recolher o pedido
    }
    public static void cardapio() throws IOException
    {
        String restaurante; // declara uma variavel do tipo String.
        if (escolha.equals("1")== true)
        {
            restaurante = "Hamburgueria.txt"; 
        }
        else if ( escolha.equals("2")== true)       //coloca na variavel "restaurante"o nome do arquivo do menu do restaurante que foi escolhido pelo cliente
        {
            restaurante = "Pizzaria.txt";
        }
        else 
        {
            restaurante = "Japones.txt";
        }
        System.out.print("\n______________________________________________________________________________\n\n"); // cria uma linha para separar o menu apresentado das informacoes e etapas anteriores
        BufferedReader frcardapio = new BufferedReader(new FileReader(restaurante)); // abre o arquivo que esta na variavel restaurante para leitura
        try // tenta fazer o que vem a seguir, caso nao consiga vai pro exception
        {
            String linha = frcardapio.readLine(); // le a primeira linha do arquivo
            while (linha != null) // cria um laco de repeticao que acaba quando ler todas as linhas do arquivo
            {
                System.out.println(linha); //imprime a linha que esta sendo lida
                linha = frcardapio.readLine(); // passa para a proxima linha
            }
            frcardapio.close(); // fecha o arquivo.
        } catch (Exception e) {
            System.out.println("Erro na leitura do arquivo"); // caso ocorra algum erro durante a leitura informa o cliente que teve um erro.
        }
    }
    public static void resumo() throws IOException
    {
        int desejo,unidade,k, a,i=0,b=0,c,z,y; //declara variaveis do tipo int
        int carrinho[][] = new int[30][4]; // declara uma matriz do tipo int
        String produtos[]= new String[30]; // declara um vetor do tipo String
        Scanner buffer = new Scanner(System.in); // gera um novo buffer
        do //cria um laco de repeticao que se encerra quando o cliente disser que nao deseja adicionar mais produtos ao pedido dele
        {
            cardapio(); // apresenta o cardapio novamente do restaurante escolhido
            System.out.print("\n\n");
            z=0; // z comeca em 0 como indicativo se o produto escolhido ja foi adicionado ao pedido anteriormente ou nao, caso tenha sido seu valor via mudar para 1.
            System.out.print("Digite o codigo do alimento que deseja: ");
            desejo = buffer.nextInt();      //pede o codigo do produto que ele deseja adiocionar ao pedido
            while (desejo<=0 || desejo>16) //verifica se o codigo digitado pelo cliente eh valido se nao for ele prende o cliente em um loop ate que ele digite um codigo valido.
            {
                System.out.println("alimento nao encontrado");
                System.out.print("Digite o codigo do alimento que deseja novamente: ");// informa o cliente que o codigo é invalido e pede para digitar outro codigo.
                desejo = buffer.nextInt();
            }
            System.out.print("Digite a quantidade que deseja deste alimento: ");
            unidade = buffer.nextInt(); //pede a quantidade do produto que ele deseja adiocionar ao pedido
            while (unidade<=0) //verifica se a quantidade digitada pelo cliente eh valida se nao for ele prende o cliente em um loop ate que ele digite uma quantidade valida.
            {
                System.out.println("quantidade invalida");
                System.out.print("Digite a quantidade do alimento que deseja novamente: "); // informa o cliente que a quantidade é invalida e pede para digitar outra quantidade.
                unidade = buffer.nextInt();
            }
            System.out.print("\n"); //pula uma linha
            for (y=0;y<i;y++) // verifica se o que esta sendo pedido ja nao foi adicionado no pedido anteriormente caso seja ele vai adicionar apenas as unidades pedidas ao que ja havia sido pedido
            {                 // loop que le todas as linhas da matriz para conferir se o codigo informado ja nao esta no pedido
                if (carrinho[y][0] == desejo) // caso o codigo informado ja tenha sido adicionado anteriormente no pedido entao
                {
                    z = 1; // muda z para 1, para indicar que o codigo desejado ja havia sido adicionado anteriormente ao pedido
                    carrinho[y][1] = carrinho[y][1] + unidade; // adiciona a unidade informada a unidade que ele ja tinha pedido antes
                    carrinho[y][3] = carrinho[y][2]*carrinho[y][1]; // faz o novo calculo do preco final.
                }
            }
            if (z==0) //caso nao tenha sido adionado anteriormente.
            {
                pedido(carrinho, produtos, i, desejo); // chama o metodo que adiciona ao pedido o codigo, produto, valor unitario do item escolhido
                carrinho[i][1]= unidade; // adiciona ao pedido a unidade desejada
                carrinho[i][3] = carrinho[i][2]*unidade; // adiciona ao pedido o valor final de acordo com a unidade desejada
                i++; //aumenta uma unidade na variavel int
            }
            System.out.print("______________________________________________________________________________\n\n");// cria uma linha para separar informacoes
            System.out.println("Alteracoes no pedido poderao ser feitas no resumo do pedido final ");
            System.out.println("Deseja adicionar outro item ao carrinho? ");
            System.out.println("Digite o número da opcao correspondente"); // permite o cliente escolher se vai querer adicionar mais coisas ao pedido ou nao, e pede para ele informar a opcao desejada
            System.out.println("1 - SIM");
            System.out.println("2 - NAO");
            System.out.print("opcao:");
            k = buffer.nextInt();
            while (k!=1 && k !=2) // verifica se a opcao eh valida, se nao for ele cria um loop e so sai quando o cliente digitar uma opcao valida
            {
                System.out.println("opcao invalida,digite novamente."); //informa que a opcao digitada é invalida e pede pro cliente digitar novamente a opcoa que ele deseja
                System.out.print("opcao: ");
                k = buffer.nextInt();
            }
            System.out.print("\n"); // pula linha
        }while(k!=2);
        do //cria um laco de repeticao que se encerra quando o cliente disser que nao deseja fazer mais alteracoes ao pedido dele
        {
            z=0; //indicador de se o produto
            System.out.print("\n______________________________________________________________________________\n\n"); // separa informacoes
            System.out.println("\ncod\tquant\tproduto\t\tpreco un.\tpreco final\n"); // imprime o cabecalho
            valor = 0; //coloca o valor final da compra como 0
            for (a=0;a<i;a++) // cria um laco de repeticao q le todas as linhas do pedido feito ate entao
            {
                if (carrinho[a][1]!=0) //caso a quantidade do produto seja diferente de 0
                {
                    System.out.println(carrinho[a][0]+"\t"+carrinho[a][1]+"\t"+produtos[a]+carrinho[a][2]+"\t\tRS"+carrinho[a][3]); //imprime o codigo,a unidade, o produto,o preco unitario e o preco final daquele produto
                    valor = valor + carrinho[a][3]; //adiociona ao valor total o valor final daquele produto
                }
            }
            System.out.println("\n\t\tValor final: RS"+valor); // depois de imprimir uma lista do pedido do cliente ele imprime o valor final do pedido
            System.out.println("\n"); // pula linha
            System.out.println("Deseja fazer alguma alteracao no pedido? ");
            System.out.println("Digite o número da opcao correspondente"); //pergunta se o cliente deseja fazer alguma alteracao no pedido e pede sua resposta
            System.out.println("1 - SIM");
            System.out.println("2 - NAO");
            System.out.print("opcao:");
            b = buffer.nextInt();
            while (b!=1 && b !=2) // verifica se a opcao digitada eh valida, se nao for prende o cliente em um loop ate que o cliente digite uma opcao valida
            {
                System.out.println("opcao invalida,digite novamente.");
                System.out.print("opcao: "); // informa o cliente que o opcao digitada eh invalida e pede para ele digitar novamente a opcao desejada
                b = buffer.nextInt();
            }
            System.out.print("\n"); //pula linha
            if (b==1)
            {
                System.out.println("O que deseja alterar? ");
                System.out.println("Digite o número da opcao correspondente");
                System.out.println("1 - Acrescentar algo"); // pergunta o que o cliente deseja alterar no pedido e pede sua resposta
                System.out.println("2 - Retirar algo");
                System.out.println("3 - Alterar quantidade de algo");
                System.out.print("opcao:");
                c = buffer.nextInt();
                while (c!=1 && c!=2 && c!=3) // verifica se a opcao digitada eh valida, se nao for prende o cliente em um loop ate que o cliente digite uma opcao valida
                {
                    System.out.println("opcao invalida,digite novamente.");
                    System.out.print("opcao: "); // informa o cliente que o opcao digitada eh invalida e pede para ele digitar novamente a opcao desejada
                    c = buffer.nextInt();
                }
                System.out.print("\n"); // pula linha
                if (c == 1) //caso o cliente queira acrescentar algo
                {
                    cardapio(); //imprime novamente o cardapio do restaurante escolhido
                    System.out.print("Digite o codigo do alimento que deseja: "); //pede o codigo do alimento que deseja acrescentar
                    desejo = buffer.nextInt();
                    while (desejo<=0 || desejo>16) //verifica se o codigo digitado pelo cliente eh valido se nao for ele prende o cliente em um loop ate que ele digite um codigo valido.
                    {
                        System.out.println("alimento nao encontrado");
                        System.out.print("Digite o codigo do alimento que deseja novamente: ");  // informa o cliente que o codigo digitado eh invalido e pede para ele digitar novamente o codigo
                        desejo = buffer.nextInt();
                    }
                    System.out.print("Digite a quantidade que deseja deste alimento: "); //pede a quantidade do alimento que deseja acrescentar
                    unidade = buffer.nextInt(); 
                    while (unidade<=0) //verifica se a quantidade digitada pelo cliente eh valida se nao for ele prende o cliente em um loop ate que ele digite uma quantidade valida.
                    {
                        System.out.println("quantidade invalida");
                        System.out.print("Digite a quantidade do alimento que deseja novamente: "); //informa o cliente que o codigo digitado eh invalido e pede para ele digitar novamente o codigo
                        unidade = buffer.nextInt();
                    }
                    for (y=0;y<i;y++) // verifica se o que esta sendo pedido ja nao foi adicionado no pedido anteriormente caso seja ele vai adicionar apenas as unidades pedidas ao que ja havia sido pedido
                    {                 // loop que le todas as linhas da matriz para conferir se o codigo informado ja nao esta no pedido
                        if (carrinho[y][0] == desejo) // caso o codigo informado ja tenha sido adicionado anteriormente no pedido entao
                        {
                            z = 1; // muda z para 1, para indicar que o codigo desejado ja havia sido adicionado anteriormente ao pedido
                            carrinho[y][1] = carrinho[y][1] + unidade;// adiciona a unidade informada a unidade que ele ja tinha pedido antes
                            carrinho[y][3] = carrinho[y][2]*carrinho[y][1];// faz o novo calculo do preco final.
                        }
                    }
                    if (z==0)//caso nao tenha sido adionado anteriormente.
                    {
                        pedido(carrinho, produtos, i, desejo); // chama o metodo que adiciona ao pedido o codigo, produto, valor unitario do item escolhido
                        carrinho[i][1]= unidade;  // adiciona ao pedido a unidade desejada
                        carrinho[i][3] = carrinho[i][2]*unidade; // adiciona ao pedido o valor final de acordo com a unidade desejada
                        i++; //aumenta uma unidade na variavel int
                    }
                }
                else if (c == 2) //caso queira retirar algo do pedido
                {
                    System.out.print("Digite o codigo do alimento que deseja retirar: "); //pede pro cliente informar qual o alimento que deseja retirar
                    desejo = buffer.nextInt();
                    while (desejo<=0 || desejo>16) //verifica se o codigo digitado pelo cliente eh valido se nao for ele prende o cliente em um loop ate que ele digite um codigo valido.
                    {
                        System.out.println("alimento nao encontrado");
                        System.out.print("Digite o codigo do alimento que deseja novamente: "); // informa o cliente que o codigo digitado eh invalido e pede para ele digitar novamente o codigo
                        desejo = buffer.nextInt();
                    }
                    for (a=0;a<i;a++) //laco de repeticao que le a matriz e sai quando terminar de olhar todas as linhas que foram escritas na matriz ate entao
                    {
                        if (carrinho[a][0]== desejo) //caso o codigo do alimento que o cliente deseja retirar seja igual ao codigo daquela linha do pedido
                        {
                            carrinho[a][1] = 0; //zera a unidade daquele produto
                        }
                    }
                }
                else
                {
                    System.out.print("Digite o codigo do alimento que deseja alterar unidade: ");
                    desejo = buffer.nextInt(); //pede pro cliente informar qual o alimento que deseja retirar
                    while (desejo<=0 || desejo>16) //verifica se o codigo digitado pelo cliente eh valido se nao for ele prende o cliente em um loop ate que ele digite um codigo valido.
                    {
                        System.out.println("alimento nao encontrado");
                        System.out.print("Digite o codigo do alimento que deseja novamente: "); // informa o cliente que o codigo digitado eh invalido e pede para ele digitar novamente o codigo
                        desejo = buffer.nextInt();
                    }
                    System.out.print("Digite a quantidade que deseja deste alimento: ");//pede a quantidade do alimento que deseja acrescentar
                    unidade = buffer.nextInt();
                    while (unidade<=0) //verifica se a quantidade digitada pelo cliente eh valida se nao for ele prende o cliente em um loop ate que ele digite uma quantidade valida.
                    {
                        System.out.println("quantidade invalida");
                        System.out.print("Digite a quantidade do alimento que deseja novamente: ");//informa o cliente que o codigo digitado eh invalido e pede para ele digitar novamente o codigo
                        unidade = buffer.nextInt();
                    }
                    for (a=0;a<i;a++) //laco de repeticao que le a matriz e sai quando terminar de olhar todas as linhas que foram escritas na matriz ate entao
                    {
                        if (carrinho[a][0]== desejo && carrinho[a][1] != 0) //caso o codigo do alimento que o cliente deseja alterar unidade seja igual ao codigo daquela linha do pedido e a unidade desse produto nao seja 0
                        {
                            carrinho[a][1] = unidade; //altera a unidade daquele produto
                            carrinho[a][3] = carrinho[a][1]*carrinho[a][2]; //refaz o calculo do preco final daquele produto
                        }
                    }
                }
            }
        }while(b!=2); 
        
        // Proceder para o pagamento
        efetuar_pagamento();
        
    }
    
    public static void pedido(int carrinho[][], String produtos[], int i, int desejo) throws IOException
    {
        String restaurante; // declara uma variavel do tipo String. 
        if (escolha.equals("1")== true)
        {
            restaurante = "Hamburgueria.txt";
        }
        else if ( escolha.equals("2")== true)
        {
            restaurante = "Pizzaria.txt";               //coloca na variavel "restaurante"o nome do arquivo do menu do restaurante que foi escolhido pelo cliente
        }
        else 
        {
            restaurante = "Japones.txt";
        }
        BufferedReader frpedido = new BufferedReader(new FileReader(restaurante)); // abre o arquivo que esta na variavel restaurante para leitura
        try // tenta fazer o que vem a seguir, caso nao consiga vai pro exception
        {
            String linha = frpedido.readLine();  // le a primeira linha do arquivo
            int j; //declara uma variavel tipo int
            while (linha != null) // cria um laco de repeticao que acaba quando ler todas as linhas do arquivo
            {
                String[] campos = linha.split("_"); // separa a linha em partes em que o caractere usado como separador é o "_"
                j = Integer.parseInt(campos[0]); //coloca a primeira parte da linha na variavel j
                if (desejo == j) //caso o codigo que o cliente quer for igual ao codigo dessa linha do arquivo
                {
                    carrinho[i][2] = Integer.parseInt(campos[2]); // adiciona o valor unitario dessa linha na matriz
                    carrinho[i][0] = Integer.parseInt(campos[0]); // adiciona o codifo dessa linha na matriz
                    produtos[i] = campos[1]; // adiciona o nome do alimento na lista
                }
                linha = frpedido.readLine(); // le a proxima linha
            }
            frpedido.close(); //fecha o arquivo
        } catch (Exception e) {
            System.out.println("Erro na leitura do arquivo");// caso ocorra algum erro durante a leitura informa o cliente que teve um erro.
        }
    }
    
    public static void efetuar_pagamento() {
    	Scanner bf = new Scanner(System.in);
    	
    	int meioPagamento = 0;
    	
    	System.out.println("Como deseja efetuar o pagamento?");
    	System.out.println("1 - Pagar na entrega");
    	System.out.println("2 - Cartão Débito");
    	System.out.println("3 - Cartão de crédito");
    	
    	meioPagamento = bf.nextInt();
    	
    	while (meioPagamento != 1 && meioPagamento != 2 && meioPagamento != 3) {//evitar escolhas além do 1, 2 ou 3

            System.out.println("Método de pagamento inválido, tente novamente");
            meioPagamento = bf.nextInt();

        }
    	
    	switch (meioPagamento) {
    	case 1:
    		troco = calcular_troco();
    		System.out.print("\n______________________________________________________________________________\n\n"); // separa informacoes
        	System.out.println("Tipo de pagamento: Pagar na entrega");
        	System.out.println("Resumo do pagamento");
        	System.out.println("Valor: R$" + valor);
        	System.out.println("Troco: R$" + troco);
        	
        	System.out.println("Pedido efetuado com sucesso!");
    		break;
    	case 2:
    		if (!gerenciarCartaoDebito()) {
    			efetuar_pagamento();
    			return;
    		}
    		
    		break;
    	case 3:
    		// Pede
    		if (!gerenciarCartaoCredito()) {
    			efetuar_pagamento();
    			return;
    		}
    		break;
    	}
    	
    	// Pedido concluido com sucesso
    }
    
    
    public static int calcular_troco() {
    	Scanner bf = new Scanner(System.in);
    	
    	int precisaTroco = 0;
    	
    	System.out.println("Será necessário troco? \n 1 - SIM \n 2 - NAO");
    	precisaTroco = bf.nextInt();
    	
    	while (precisaTroco != 1 && precisaTroco != 2) {//evitar escolhas além do 1, 2 ou 3
            System.out.println("Método de pagamento inválido, tente novamente");
            precisaTroco = bf.nextInt();
        }
    	
    	if (precisaTroco == 1) {
    		System.out.println("Lembrete: O valor do pedido ficou: " + valor);
    		System.out.print("Será necessário troco para quanto?");
    		int troco = bf.nextInt();
    		boolean trocoValido = (troco >= valor) ? true : false;
    		
    		while (!trocoValido) {
    			System.out.println("O valor do pagamento deve ser maior que o valor do pedido, tente novamente");
    			troco = bf.nextInt();
    			trocoValido = (troco >= valor) ? true : false;
    		}
    		
    		return troco - valor;
    	}
    	
    	return 0;
    }
    
    public static boolean gerenciarCartaoCredito() {
    	Scanner bf = new Scanner(System.in);
    	Scanner bf2 = new Scanner(System.in);
    	
    	System.out.println("Deseja parcelar em quantas vezes? (Insira apenas o número de parcelas) \n 1x de " + valor + "\n 2x de " + valor / 2);
    	int parcelas = bf2.nextInt();
    	
    	while (parcelas != 1 && parcelas != 2) {
            System.out.println("Número de parcelas não permitido, tente novamente");
            parcelas = bf2.nextInt();
        }
    	
    	System.out.println("Insira o número do cartão sem ponto e traços");
    	String numeroCartao = bf.nextLine();
    	
    	while (!numeroCartao.matches("^[0-9]*$")) {
    		System.out.println("Insira apenas os números do cartão sem pontuação");
    		numeroCartao = bf.nextLine();
    	}
    	
    	System.out.println("Insira o código de verificação do cartão");
    	String codigoVerificao = bf.nextLine();
    	while (!codigoVerificao.matches("^[0-9]*$") || codigoVerificao.length() != 3) {
    		System.out.println("Código inválido, tente novamente");
    		numeroCartao = bf.nextLine();
    	}
    	
    	System.out.println("Insira a data de expiração do cartão (MM/yyyy) \n");
    	String stringDataExpiracao = bf.nextLine();
    	
    	boolean dataInvalida = false; 
    	do {
    		try {
    			// Convertendo a string do usuário para um objeto Date
    			Date dataExpiracao = new SimpleDateFormat("MM/yyyy").parse(stringDataExpiracao);
    			
    			// Mudando o dia da expiração para o último dia do mês
    			dataExpiracao.setDate(30);
    			
    			// Obtendo a data atual
    		    Date now = new Date();
    		    
    		    // Alterando a data para o primeiro dia do mês seguinte, assim é possível subtrair as duas datas e 
    		    // obter um valor negativo e positivo que indicara o vencimento do cartão
    		    now.setMonth(now.getMonth() + 1);
    		    now.setDate(1);
    		    
    			if ( dataExpiracao.getTime() - now.getTime() < 0 ) {
    				System.out.println("Este cartão está expirado!");
    				return false;
    			}
    			
    		} catch (ParseException e) {
    			// TODO Auto-generated catch block
    			System.out.println("A data inserida é inválida");
    			dataInvalida = true;
    		}
    	} while (dataInvalida);
    	
    	System.out.print("\n______________________________________________________________________________\n\n"); // separa informacoes
    	System.out.println("Resumo do pagamento");
    	System.out.println("Tipo de pagamento: Cartão de crédito");
    	System.out.println("N° do cartão: " + numeroCartao);
    	System.out.println("Valor total: " + valor);
    	System.out.println("Parcelas: " + parcelas + "x de R$" + valor/parcelas);
    	
    	System.out.println("Pedido efetuado com sucesso!");
    	
    	return true;
    	
    }
    
    public static boolean gerenciarCartaoDebito() {
    	Scanner bf = new Scanner(System.in);
    	
    	System.out.println("Insira o número do cartão sem ponto e traços");
    	String numeroCartao = bf.nextLine();
    	
    	while (!numeroCartao.matches("^[0-9]*$")) {
    		System.out.println("Insira apenas os números do cartão sem pontuação");
    		numeroCartao = bf.nextLine();
    	}
    	
    	System.out.println("Insira o código de verificação do cartão");
    	String codigoVerificao = bf.nextLine();
    	while (!codigoVerificao.matches("^[0-9]*$") || codigoVerificao.length() != 3) {
    		System.out.println("Código inválido, tente novamente");
    		numeroCartao = bf.nextLine();
    	}
    	
    	System.out.println("Insira a data de expiração do cartão (MM/yyyy) \n");
    	String stringDataExpiracao = bf.nextLine();
    	
    	boolean dataInvalida = false; 
    	do {
    		try {
    			// Convertendo a string do usuário para um objeto Date
    			Date dataExpiracao = new SimpleDateFormat("MM/yyyy").parse(stringDataExpiracao);
    			
    			// Mudando o dia da expiração para o último dia do mês
    			dataExpiracao.setDate(30);
    			
    			// Obtendo a data atual
    		    Date now = new Date();
    		    
    		    // Alterando a data para o primeiro dia do mês seguinte, assim é possível subtrair as duas datas e 
    		    // obter um valor negativo e positivo que indicara o vencimento do cartão
    		    now.setMonth(now.getMonth() + 1);
    		    now.setDate(1);
    		    
    			if ( dataExpiracao.getTime() - now.getTime() < 0 ) {
    				System.out.println("Este cartão está expirado!");
    				return false;
    			}
    			
    		} catch (ParseException e) {
    			// TODO Auto-generated catch block
    			System.out.println("A data inserida é inválida");
    			dataInvalida = true;
    		}
    	} while (dataInvalida);
    	
    	System.out.print("\n______________________________________________________________________________\n\n"); // separa informacoes
    	System.out.println("Resumo do pagamento");
    	System.out.println("Tipo de pagamento: Cartão de débito");
    	System.out.println("N° do cartão: " + numeroCartao);
    	System.out.println("Valor total: " + valor);
    	
    	System.out.println("Pedido efetuado com sucesso!");
    	
    	return true;
    }
}
