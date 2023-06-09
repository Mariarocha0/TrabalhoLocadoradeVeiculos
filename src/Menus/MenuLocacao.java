package Menus;
import java.util.Calendar;
import java.util.Scanner;

import Classes.Locacao;
import Listas.ListaCategoria;
import Listas.ListaCliente;
import Listas.ListaLocacao;
import Listas.ListaVeiculo;

public class MenuLocacao {

    private ListaLocacao listaL;
    private ListaCliente listaC;
    private ListaVeiculo listaV;
    private ListaCategoria listaCat;
    private Scanner entrada;

    public MenuLocacao(ListaLocacao listaL, ListaCliente listaC, ListaVeiculo listaV, ListaCategoria listaCat) {
        this.listaL = listaL;
        this.listaC = listaC;
        this.listaV = listaV;
        this.listaCat = listaCat;
        entrada = new Scanner(System.in);
    }

    public void show() throws Exception {
        int op = 0;
        do {

            System.out.print("|---------------------------------------|\n");
            System.out.print("|           Menu Locações               |\n");
            System.out.print("|---------------------------------------|\n");
            System.out.print("| Opção 1 - Cadastrar nova locação      |\n");
            System.out.print("| Opção 2 - Excluir locação             |\n");
            System.out.print("| Opção 3 - Listar locações             |\n");
            System.out.print("| Opção 4 - Filtros                     |\n");
            System.out.print("| Opção 5 - Voltar ao menu principal    |\n");
            System.out.print("|---------------------------------------|\n");
            System.out.print("Digite uma opção:");

            do{
                try{
                op = entrada.nextInt();
                }catch(Exception e){
                    System.out.println("Opção inválida! Escolha uma opção válida!");
            }
            }while(op < 1 || op > 5);

            switch (op) {
                case 1:
                    addLocacao();
                    break;
                case 2:
                    removerLocacao();
                    break;
                case 3:
                    verificarInfoLocacoes();
                    break;
                case 4:
                    menuFiltros();
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        } while (op != 5);

    }

    /**
     * Método para adicionar uma locação
     * Atributos obrigátorios: CPF e placa de veículo
     * @throws Exception
     */
    private void addLocacao() throws Exception {

        Calendar dataI = Calendar.getInstance();
        Calendar dataF = Calendar.getInstance();
        long CPF = 0;
        boolean aux2 = false;

        System.out.println("Nova Locação: ");

        do{
            try{
                System.out.println("Informe o CPF do cliente: ");
                CPF = entrada.nextLong();
                entrada.nextLine();
                aux2 = true;
                if (!listaC.existe(CPF)) {
                    System.out.println("Cliente não encontrado!");
                }else{
                    System.out.println("Cliente encontrado!");
                    break;
                }
                }catch(Exception e){
                    System.out.println("CPF inválido! Digite um CPF válido!");
                    aux2 = false;
                }

            }while(aux2 == false);

        System.out.println("Infome a placa do veículo: ");
        String placa = entrada.nextLine();
        if (!listaV.existe(placa)) {
            System.out.println("Veículo não cadastrado! Cadastre-o antes de inserir uma locação!");
            return;
        }

        try{
            if (listaL.existe(placa)) {
                System.out.println("Veículo já está locado!");
                return;
            }
        }finally{
            System.out.println("Veículo disponível para locação!");
        }


        double valor = 0;

        do{
        try{
        System.out.println("Informe o valor da diária da locação: ");
        valor = entrada.nextDouble();
        }catch(Exception e){
            System.out.println("Valor inválido! Digite apenas números positivos!");
        }
        }while(valor < 0);

        int diaI = 0;
        int mesI = 0;
        int anoI = 0;
        int diaF = 0;
        int mesF = 0;
        int anoF = 0;

        boolean op;
        do {
            System.out.println("Digite a data inicial da locação: ");
            do{
            try{
            System.out.println("Dia: ");
            diaI = entrada.nextInt();
            }catch(Exception e){
                System.out.println("Dia inválido! Digite um dia válido!");
            }
            }while(diaI < 1 || diaI > 31);

            do{
            try{
            System.out.println("Mês: ");
            mesI = entrada.nextInt();
            }catch(Exception e){
                System.out.println("Mês inválido! Digite um mês válido!");
            }
            }while(mesI < 1 || mesI > 12);

            do{
            try{
            System.out.println("Ano: ");
            anoI = entrada.nextInt();
            }catch(Exception e){
                System.out.println("Ano inválido! Digite um ano válido!");
            }
            }while(anoI < 2020);


            System.out.println("Digite a data final da locação: ");

            do{
            try{
            System.out.println("Dia: ");
            diaF = entrada.nextInt();
            }catch(Exception e){
                System.out.println("Dia inválido! Digite um dia válido!");
            }
            }while(diaF < 1 || diaF > 31);

            do{
            try{
            System.out.println("Mês: ");
            mesF = entrada.nextInt();
            }catch(Exception e){
                System.out.println("Mês inválido! Digite um mês válido!");
            }
            }while(mesF < 1 || mesF > 12);

            do{
            try{
            System.out.println("Ano: ");
            anoF = entrada.nextInt();
            }catch(Exception e){
                System.out.println("Ano inválido! Digite um ano válido!");
            }
            }while(anoF < 2020);

            dataI.set(anoI, mesI - 1, diaI);
            dataF.set(anoF, mesF - 1, diaF);

            int result = dataF.compareTo(dataI);
            if (result > 0) {
                op = true;
            } else {
                System.out.println("Digite uma data inicial menor que a data final!");
                op = false;
            }

        } while (op == false);

        long dias = (dataF.getTimeInMillis() - dataI.getTimeInMillis()) / 1000 / 60 / 60 / 24;
        double valorTotal = valor * dias;

        Locacao aux = new Locacao(listaC.getCliente(CPF), listaV.getVeiculo(placa), dataI, dataF, valorTotal);
        listaL.addFim(aux);
        System.out.println("Locação criada com sucesso! ");
        System.out.println(aux.toString());
        System.out.println("");
    }

    public void verificarInfoLocacoes() {
        if (listaL.getInfoInicio() != null) {
            System.out.println("\n Todas locações:");
            System.out.println(listaL.getInfoInicio());
        } else {
            System.out.println("Não existem locações");
        }
    }

    private void removerLocacao() {
        System.out.println("Digite a placa do veiculo da locação que deseja remover: ");
        String placa = entrada.nextLine();
        entrada.nextLine();

        if (listaL.getLocacaoPlaca(placa) != null) {
            listaL.remove(placa);
            System.out.println("Locação removida!");
        } else {
            System.out.println("Placa de veículo inválido.");
        }
    }

    private void menuFiltros() {
        Scanner sc = new Scanner(System.in);
        int opcao = 0;

        do {
            System.out.println("\n:: FILTRO ::");
            System.out.println("Escolha a opção desejada");
            System.out.println("1 - Listar por potência");
            System.out.println("2 - Listar por número de lugares");
            System.out.println("3 - Listar por categoria");
            System.out.println("0 - Voltar ");
            System.out.print("Sua opção: ");

            do{
                try{
                opcao = sc.nextInt();
                }catch(Exception e){
                    System.out.println("Opção inválida! Escolha uma opção válida!");
            }
            }while(opcao < 0 || opcao > 3);

            switch (opcao) {
                case 1:
                    filtroPotencia();
                    break;
                case 2:
                    filtroNLugares();
                    break;
                case 3:
                    filtroCategoria();
                    break;
                case 0:
                    return;
                default:
                    System.err.println("ERRO, digite uma opção válida");
            }
        } while (opcao != 0);
    }

    public void filtroPotencia() {
        int potencia = 0;
        System.out.println(listaV.getInfoInicioFree());

        do{
        try{
        System.out.print("Digite a potencia dos veículos: ");
        potencia = entrada.nextInt();
        }catch(Exception e){
            System.out.println("Potência inválida! Digite uma potência válida!");
        }
        }while(potencia < 0);
        System.out.println(listaV.getPotencia(potencia));
    }

    public void filtroNLugares() {
        int nLugares = 0;
        System.out.println(listaV.getInfoInicioFree());

        do{
        try{
        System.out.print("Digite o número de lugares dos veículos: ");
        nLugares = entrada.nextInt();
        }catch(Exception e){
            System.out.println("Número de lugares inválido! Digite um número de lugares válido!");
        }
        }while(nLugares < 0);

        System.out.println(listaV.getLugares(nLugares));
    }

    public void filtroCategoria() {
        int id = 0;
        System.out.println(listaV.getInfoInicioFree());

        do{
        try{
        System.out.print("Digite o id da categoria do veículo: ");
        id = entrada.nextInt();
        }catch(Exception e){
            System.out.println("Id inválido! Digite um id válido!");
        }
        }while(id < 0);
        System.out.println(listaV.getCategoria(listaCat.getCategoria(id)));
    }

}
