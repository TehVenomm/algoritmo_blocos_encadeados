//Trabalho Sistema de Arquivos - Sistemas Operacionais
//Gabriel Braz e Santos - 260569

import java.util.ArrayList;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int qtd_blocos = 500;
        int i;
        char nome_arquivo;
        char temp_nome;
        int temp_size;
        String temp_id_bloco;
        int tamanho;
        boolean error = false;
        boolean deleted = false;
        int escolha;

        Bloco[] blocos_array = new Bloco[qtd_blocos];

        //Inicializar os blocos...
        for (i = 0; i < qtd_blocos; i++)
        {
            blocos_array[i] = new Bloco(' ', 0);
        }

        //Logica de display e logica de controle de dados.
        while (true){
            temp_nome = ' ';
            temp_size = 0;
            temp_id_bloco = "";
            error = false;
            deleted = false;

            //Limpa a tela com quebras de linha e printa a memoria como esta atualmente.
            System.out.println(new String(new char[50]).replace("\0", "\n"));

            for (i = 0; i < qtd_blocos; i++)
            {
                if (i % 50 == 0)
                {
                    System.out.print("\n");
                }

                blocos_array[i].printBlock();

            }

            System.out.println("\nEscolha uma opcao:");
            System.out.println("1 - Adicionar dados");
            System.out.println("2 - Apagar dados");
            escolha = scan.nextInt();

            switch (escolha)
            {
                case 1:
                    System.out.println("Insira o nome do arquivo:");
                    System.out.println("-> Um caractere! Exemplos: 1, 2, a, A, b, C <-");
                    nome_arquivo = scan.next().trim().charAt(0);

                    System.out.println("Insira o tamanho do arquivo em bytes:");
                    tamanho = scan.nextInt();

                    //Varre a memoria de inicio ao fim para ver se existe como gravar
                    //Quando acha um nome duplicado aborta
                    //Quando a soma dos espacos vazios nao equivale ou excede o tamanho do arquivo aborta
                    for (i = 0; i < qtd_blocos ; i++)
                    {
                        temp_nome = blocos_array[i].getNome();

                        if (temp_nome == nome_arquivo){
                            System.out.println("Arquivo ja existe! Abortando.");
                            error = true;
                            break;
                        }

                        if (temp_nome == ' '){
                            temp_size += blocos_array[i].getMax_size();
                        }
                    }

                    if (temp_size < tamanho && error == false){
                        System.out.println("Nao ha espaco o suficiente no sistema de arquivos, abortando!");
                        error = true;
                    }

                    //Se a verificacao deu OK, grava os dados.
                    if (error == false){
                        System.out.println("Armazenando dados...");
                        temp_size = tamanho;

                        //Varre a memoria do inicio ao fim, e quando encontra um bloco vazio, grava o arquivo em cima.
                        //Subtrai o tamanho total com cada bloco para saber quando parar.
                        for(i = 0; i < qtd_blocos; i++){
                            if (blocos_array[i].getNome() == ' '){
                                if (temp_id_bloco == ""){
                                    temp_size = blocos_array[i].setBlock(nome_arquivo, temp_size, "");
                                    temp_id_bloco = Integer.toString(i);
                                } else {
                                    temp_size = blocos_array[i].setBlock(nome_arquivo, temp_size, temp_id_bloco);
                                    temp_id_bloco = Integer.toString(i);
                                }

                                if (temp_size == 0){
                                    System.out.println("Bytes faltando: " + temp_size + "/" + tamanho);
                                    System.out.println("Completo!");
                                    break;
                                } else {
                                    System.out.println("Bytes faltando: " + temp_size + "/" + tamanho);
                                }
                            }
                        }
                    }
                    break;
                case 2:
                    //Logica de apagar arquivo
                    System.out.println("Insira o nome do arquivo:");
                    System.out.println("-> Um caractere! Exemplos: 1, 2, a, A, b, C <-");
                    nome_arquivo = scan.next().trim().charAt(0);

                    //Varre a memoria de fim ao comeco para encontrar o ultimo bloco do arquivo desejado
                    //Apos encontrar, zera o bloco atual e utiliza do encadeamento de lista presente no bloco para ir direto no proximo bloco para apagar
                    //Ao inves de ir bloco por bloco para apagar.
                    //Economiza uns milisegundos, eu devia ter medido mas preguica.
                    i = qtd_blocos - 1;
                    while (i >= 0)
                    {
                        if (blocos_array[i].getNome() == nome_arquivo)
                        {
                            deleted = true;
                            temp_id_bloco = blocos_array[i].getPrevious_block_id();
                            temp_size += blocos_array[i].getOccupied_size();

                            System.out.println("Apagando bloco\t\t\t\tID #" + i);
                            System.out.println("Apagado: \t" + temp_size + " bytes...");
                            blocos_array[i].clearBlock();

                            if (temp_id_bloco == ""){
                                System.out.println("Ultimo bloco!");
                                break;
                            } else {
                                System.out.println("Proximo bloco deste arquivo\tID #" + temp_id_bloco + " ->");
                                i = Integer.parseInt(temp_id_bloco);
                            }

                            continue;
                        }
                        i--;
                    }

                    //Se o loop anterior nao encontrou o arquivo. avisa para o usuario.
                    if (deleted){
                        System.out.println("\nArquivo apagado com sucesso!");
                        System.out.println("Total: " + temp_size + " bytes.");
                    } else {
                        System.out.println("Arquivo nao existe! Nada foi apagado.");
                    }

                    break;
                default:
                    System.out.println("Opcao invalida!");
                    break;
            }

            //Um pause para o usuario poder ler o console.
            try
            {
                Thread.sleep(3000);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}