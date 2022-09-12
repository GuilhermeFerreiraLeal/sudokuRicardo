import java.util.Scanner;

public class sudoku {
    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);
        int linha = -1, coluna = -1, numero = -1;
        boolean numerosValidos = true;
        boolean[][] espacos = new boolean[9][9];
        int[][] tabuleiro = {{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0}};
        espacos = defineOcupacao(espacos, tabuleiro);

        for(int i = 0; i < 15; i++){
            numerosValidos = true;
            exibeTabuleiro(tabuleiro);

            System.out.println("Informe numeros de 1 a 9 para linha, coluna e numero a ser inserido no tabuleiro");
            do {
                System.out.print("Linha > ");
                linha = Integer.parseInt(leitor.next()) - 1;

                System.out.println("Coluna > ");
                coluna = Integer.parseInt(leitor.next()) - 1;

                System.out.println("Numero > ");
                numero = Integer.parseInt(leitor.next());
                if(linha < 0 || linha > 8 || coluna < 0 || coluna > 8 || numero < 1 || numero > 9){
                    System.out.println("Somente numeros de 1 a 9 são validos");
                }
                else{
                    numerosValidos = false;
                }
            }while(numerosValidos);
            numerosValidos = true;

            if(espacos[linha][coluna]){
                System.out.println("Essas posições são fixas, voce não pode altera-la");
                //exibirPosicoesFixas(espacos, tabuleiro);
            }else{
                if (PossivelNumero(tabuleiro, linha, coluna, numero)) {
                    tabuleiro[linha][coluna] = numero;
                } else {
                    MovimentoBloqueado(tabuleiro, linha, coluna, numero);
                }
            }

            if(verificaTabuleiroCompleto(tabuleiro)){
                numerosValidos = false;
            }
            pressioneEnter();
        }
        System.out.println("----------------------fim do primeiro jogador-----------------------------");

        do{
            numerosValidos = true;
            exibeTabuleiro(tabuleiro);

            System.out.println("Informe numeros de 1 a 9 para linha, coluna e numero a ser inserido no tabuleiro");
            do {
                System.out.print("Linha > ");
                linha = Integer.parseInt(leitor.next()) - 1;

                System.out.println("Coluna > ");
                coluna = Integer.parseInt(leitor.next()) - 1;

                System.out.println("Numero > ");
                numero = Integer.parseInt(leitor.next());
                if(linha < 0 || linha > 8 || coluna < 0 || coluna > 8 || numero < 1 || numero > 9){
                    System.out.println("Somente numeros de 1 a 9 são validos");
                }
                else{
                    numerosValidos = false;
                }
            }while(numerosValidos);
            numerosValidos = true;

            if(espacos[linha][coluna]){
                System.out.println("Essas posições são fixas, voce não pode altera-la");
                //exibirPosicoesFixas(espacos, tabuleiro);
            }else{
                if (PossivelNumero(tabuleiro, linha, coluna, numero)) {
                    tabuleiro[linha][coluna] = numero;
                } else {
                    MovimentoBloqueado(tabuleiro, linha, coluna, numero);
                }
            }

            if(verificaTabuleiroCompleto(tabuleiro)){
                numerosValidos = false;
            }
            pressioneEnter();
        }while(numerosValidos);
        System.out.println("FIM.");

    }

    private static boolean[][] defineOcupacao(boolean[][] espacos, int[][] tabuleiro){
        for(int i = 0; i < tabuleiro.length; i++){
            for (int j = 0; j < tabuleiro[0].length; j++) {
                if (tabuleiro[i][j] != 0)
                    espacos[i][j] = true;
            }
        }
        return espacos;
    }

    public static void exibeTabuleiro(int[][] tabuleiro){

        for (int i = 0; i < tabuleiro.length; i++) {
            System.out.print("L" + (i+1) + " - ");
            for (int j = 0; j < tabuleiro.length; j++) {
                System.out.print(tabuleiro[i][j] + " ");
                if (j == 2 || j == 5) System.out.print("| ");
            }
            System.out.println();
            if (i == 2 || i == 5)
                System.out.print("     ------|-------|------\n");
        }
    }

    //verifica a possibilidade de inserir um numero na tabela
    public static boolean PossivelNumero(int[][]tabuleiro, int linha, int coluna, int numero){
        //verifica linha
        for(int i = 0; i < tabuleiro[0].length; i++){
            if(tabuleiro[coluna][i] == numero){
                return false;
            }
        }

        //verifica coluna
        for (int i = 0; i < tabuleiro.length; i++){
            if(tabuleiro[i][coluna] == numero){
                return false;
            }
        }

        //verifica box
        int boxLinha = linha - linha % 3;
        int boxColuna = linha - linha % 3;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tabuleiro[boxLinha + i][boxColuna + j] == numero)
                    return false;
            }
        }

        //ta tudo certo
        return true;
    }

    public static void MovimentoBloqueado(int[][] tabuleiro, int linha, int coluna, int numero){
        int [][] novoTabuleiro = copiarTabuleiro(tabuleiro);
        String reset = "\u001B[0m";
        String vermelhoFundoAzul = "\u001B[31;46m";

        String brancoFundoVermelho = "\u001B[41;43m";

        int[] destaque = {linha, coluna, -1, -1, -1, -1, -1, -1};

        System.out.println("    " + brancoFundoVermelho + " movimento invalido" + reset);

        novoTabuleiro[linha][coluna] = numero;

        for (int i =0; i < tabuleiro.length; i++){
            if (tabuleiro[linha][i] == numero){
                destaque[2] = linha;
                destaque[3] = i;
            }
        }

        for (int i =0; i < tabuleiro.length; i++){
            if (tabuleiro[i][coluna] == numero){
                destaque[4] = i;
                destaque[5] = coluna;
            }
        }

        int boxLinha = linha - linha % 3;
        int boxColuna = coluna - coluna % 3;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tabuleiro[boxLinha + i][boxColuna + j] == numero) {
                    destaque[6] = boxLinha + i;
                    destaque[7] = boxColuna + j;
                }
            }
        }

        for(int i = 0; i < tabuleiro.length; i++){
            System.out.print((i+1) + " - ");
            for(int j = 0; j < tabuleiro.length; j++){
                boolean destaques = definirDestaques(destaque, i, j);

                if (destaques) {
                    System.out.print(vermelhoFundoAzul + novoTabuleiro[i][j] + reset);
                }else {
                    System.out.print(novoTabuleiro[i][j] + " ");
                }

                if (j == 2 || j == 5){
                    System.out.print("| ");
                }

            }
            System.out.println();
            if(i == 2 || i == 5){
                System.out.print("    ------|-------|-------\n");
            }
        }
    }

    public static boolean definirDestaques(int[] destaque, int i, int j){
        for (int k = 0; k < destaque.length; k++) {
            int linha = k; int coluna = k + 1;
            if(destaque[linha] == i && destaque[coluna] == j) {
                return true;
            }
            k++;
        }

        return false;
    }
    public static int[][] copiarTabuleiro(int[][] tabuleiro){
        int[][] novoTabuleiro = new int[tabuleiro.length][tabuleiro[0].length];

        for(int i = 0; i < tabuleiro.length; i++){
            for (int j = 0; j < tabuleiro[0].length; j++) {
                novoTabuleiro[i][j] = tabuleiro[i][j];
            }
        }

        return novoTabuleiro;
    }

    public static boolean verificaTabuleiroCompleto(int[][] tabuleiro){
        for(int i = 0; i < tabuleiro.length; i++){
            for(int j = 0; j < tabuleiro[0].length; j++){
                if(tabuleiro[i][j] == 0){
                    return  false;
                }
            }
        }
        return true;
    }
    public static void pressioneEnter(){
        Scanner leitor = new Scanner(System.in);
        System.out.println("Pressione Enter para continuar");
        leitor.nextLine();
    }

}
