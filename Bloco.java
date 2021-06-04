import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Bloco
{
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[38m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    private char nome;
    private int occupied_size;
    private int max_size = 512;
    private String previous_block_id;

    public char getNome()
    {
        return nome;
    }

    public void setNome(char nome)
    {
        this.nome = nome;
    }

    public int getOccupied_size()
    {
        return occupied_size;
    }

    public void setOccupied_size(int occupied_size)
    {
        this.occupied_size = occupied_size;
    }

    public int getMax_size()
    {
        return max_size;
    }

    public void setMax_size(int max_size)
    {
        this.max_size = max_size;
    }

    public String getPrevious_block_id()
    {
        return previous_block_id;
    }

    public void setPrevious_block_id(String previous_block_id)
    {
        this.previous_block_id = previous_block_id;
    }

    Bloco(char nome, int occupied_size){
        this.nome = nome;
        this.occupied_size = occupied_size;
        this.previous_block_id = "";
    }

    Bloco(){

    }

    //Printa com cor o tipo do bloco.
    public void printBlock(){
        if (this.nome == ' ') //Blank -> Green
        {
            System.out.print(ANSI_WHITE_BACKGROUND + ANSI_BLACK + "[" + this.nome + "]" + ANSI_RESET );
        }

        if (this.previous_block_id == "" && this.nome != ' ') {
            System.out.print(ANSI_YELLOW_BACKGROUND + ANSI_BLACK + "[" + this.nome + "]" + ANSI_RESET );
        }

        if (this.previous_block_id != "" && this.nome != ' ') {
            System.out.print(ANSI_RED_BACKGROUND + ANSI_BLACK + "[" + this.nome + "]" + ANSI_RESET );
        }
    }

    //Seta os dados do bloco, retorna o tamanho restante.
    public int setBlock(char nome, int size, String previous_block_id){
        this.nome = nome;

        if (size <= 512){
            this.occupied_size = size;
            size = 0;
        } else {
            this.occupied_size = 512;
            size = size - 512;
        }

        this.previous_block_id = previous_block_id;

        return size;
    }

    //Zera o bloco.
    public void clearBlock(){
        this.nome = ' ';
        this.occupied_size = 0;
        previous_block_id = "";
    }
}