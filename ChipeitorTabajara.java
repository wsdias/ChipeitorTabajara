/*
 *  T4 - Projeto e Otimização de Algoritmos
 *
 *  Willian Schmiele Dias
 *
 *  2019/2
 *
 */

import java.util.ArrayList;
import java.util.Random;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.Math;

public class ChipeitorTabajara{

    private static int[][] placa;
    private static ArrayList<Tupla> conexoes;
    private static int n, nConexoes;
    private static String arquivoSaida;

    public static void main(String[] args){
        
        if (args.length == 1){
            
            Carregar(args[0]);
            BuscarSolucao();
        }
        else System.out.println("Formato: java ChipeitorTabajara arquivoEntrada");
    }

    // -------------------------------------------------------------------------------- //
    
    private static void BuscarSolucao(){

        double res, oldRes;
        int[][] aux;
        
        res = oldRes = SomarDistancias(placa);
        while (true){

            aux = placa;
            aux = ModificarPlaca(aux);
            res = SomarDistancias(aux);
            if (res < oldRes){

                oldRes = res;
                placa = aux;
                System.out.printf("RES: %.4f\n", res);
                //Imprimir();
                Salvar(arquivoSaida);
            }
        }
    }
    
    // -------------------------------------------------------------------------------- //
    
    private static int[][] ModificarPlaca(int[][] aux){

        int a, b, c, op;
        Tupla t1, t2, t3;
        Random r;

        r = new Random();
        op = r.nextInt(6);
        
        switch(op){
            
            case 0:
                do
                {
                    a = r.nextInt(n*n);
                    b = r.nextInt(n*n); 
                } while (a == b);
                
                t1 = BuscarComponente(a, placa);
                t2 = BuscarComponente(b, placa);                
                aux = Swap(t1, t2, aux);
                break;
                
            case 1:
                do
                {
                    a = r.nextInt(n*n);
                    b = r.nextInt(n*n);
                    c = r.nextInt(n*n);
                } while (!(a != b && b != c && c != a));

                t1 = BuscarComponente(a, aux);
                t2 = BuscarComponente(b, aux);
                t3 = BuscarComponente(c, aux);
                aux = SwapTriplo(t1, t2, t3, aux);
                break;
                
            case 2:
                do
                {
                    a = r.nextInt(n);
                    b = r.nextInt(n);
                } while (a == b);

                aux = TrocarLinhas(a, b, aux);
                break;

            case 3:
                do
                {
                    a = r.nextInt(n);
                    b = r.nextInt(n);
                } while (a == b);

                aux = TrocarColunas(a, b, aux);
                break;

            case 4:
                aux = FlipHorizontal(aux);
                break;

            case 5:
                aux = FlipVertical(aux);
                break;

            default:
                System.out.println("NOP");
        }
        
        return aux;
    }

    // -------------------------------------------------------------------------------- //

    private static int[][] FlipHorizontal(int[][] aux){

        int i, j, k;
        int[] tmp;

        tmp = new int[n];
        for (i = 0; i < n; i++){

            for (j = 0; j < n; j++)
                tmp[j] = aux[i][j];

            k = 0;
            for (j = n - 1; j >= 0; j--){

                aux[i][k] = tmp[j];
                k++;
            }
        }

        return aux;
    }

    // -------------------------------------------------------------------------------- //

    private static int[][] FlipVertical(int[][] aux){

        int i, j, k;
        int[] tmp;

        tmp = new int[n];
        for (i = 0; i < n; i++){

            for (j = 0; j < n; j++)
                tmp[j] = aux[j][i];

            k = 0;
            for (j = n - 1; j >= 0; j--){

                aux[k][i] = tmp[j];
                k++;
            }
        }

        return aux;
    }

    // -------------------------------------------------------------------------------- //

    private static int[][] TrocarColunas(int c1, int c2, int[][] aux){

        int i, tmp;

        for (i = 0; i < n; i++){

            tmp = aux[i][c2];
            aux[i][c2] = aux[i][c1];
            aux[i][c1] = tmp;
        }

        return aux;
    }

    // -------------------------------------------------------------------------------- //
    
    private static int[][] TrocarLinhas(int l1, int l2, int[][] aux){
        
        int i, tmp;
        
        for (i = 0; i < n; i++){
        
            tmp = aux[l1][i];
            aux[l1][i] = aux[l2][i];
            aux[l2][i] = tmp;
        }
        
        return aux;
    }
    
    // -------------------------------------------------------------------------------- //
    
    private static int[][] SwapTriplo(Tupla a, Tupla b, Tupla c, int[][] aux){
        
        int tmp, l1, c1, l2, c2, l3, c3;
        
        l1 = a.a(); c1 = a.b();
        l2 = b.a(); c2 = b.b();
        l3 = c.a(); c3 = c.b();
        
        tmp = aux[l1][c1];
        aux[l1][c1] = aux[l2][c2];
        aux[l2][c2] = aux[l3][c3];
        aux[l3][c3] = tmp;
        
        return aux;
    }
    
    // -------------------------------------------------------------------------------- //
    
    private static int[][] Swap(Tupla a, Tupla b, int[][] aux){
        
        int tmp, l1, c1, l2, c2;

        l1 = a.a(); c1 = a.b();
        l2 = b.a(); c2 = b.b();
        
        tmp = aux[l1][c1];
        aux[l1][c1] = aux[l2][c2];
        aux[l2][c2] = tmp;

        return aux;
    }

    // -------------------------------------------------------------------------------- //

    private static double SomarDistancias(int[][] aux){

        double resultado;
        Tupla t1, t2;
        int a, b;
        
        resultado = 0.0;
        for (Tupla t : conexoes){

            a = t.a();
            b = t.b();

            t1 = BuscarComponente(a, aux);
            t2 = BuscarComponente(b, aux);

            resultado += CalcularDistancia(t1, t2);
        }
        
        return resultado;
    }

    // -------------------------------------------------------------------------------- //

    private static Tupla BuscarComponente(int componente, int[][] aux){

        int i, j;

        for (i = 0; i < n; i++){

            for (j = 0; j < n; j++){

                if (aux[i][j] == componente)
                    return new Tupla(i, j);
            }
        }

        return null;
    }

    // -------------------------------------------------------------------------------- //
    
    private static double CalcularDistancia(Tupla a, Tupla b){
        
        double resultado;
        int l1, c1, l2, c2;

        l1 = a.a(); c1 = a.b();
        l2 = b.a(); c2 = b.b();
        
        resultado = Math.sqrt(Math.pow(l2 - l1, 2) + Math.pow(c2 - c1, 2));
        return resultado;
    }

    // -------------------------------------------------------------------------------- //
    
    private static void Carregar(String nomeArquivo){
        
        BufferedReader buffer;
        FileReader arquivo;
        String[] linha;
        int i, j, a, b;

        try{

            arquivo = new FileReader(nomeArquivo);
            buffer = new BufferedReader(arquivo);

            linha = buffer.readLine().split(" ");
            n = Integer.valueOf(linha[0]);
            nConexoes = Integer.valueOf(linha[1]);
            arquivoSaida = nomeArquivo + "_currentState";
            
            placa = new int[n][n];
            conexoes = new ArrayList<Tupla>();
            
            for (i = 0; i < n; i++){
                
                linha = buffer.readLine().trim().split("\\s+");
                for (j = 0; j < n; j++)
                    placa[i][j] = Integer.valueOf(linha[j]);
            }
            
            for (i = 0; i < nConexoes; i++){

                linha = buffer.readLine().split(" ");
                a = Integer.valueOf(linha[0]);
                b = Integer.valueOf(linha[1]);
                conexoes.add(new Tupla(a, b));
            }
            
            buffer.close();
            arquivo.close();
        }
        catch (IOException e){

            System.err.println (e.getMessage ());
        }
    }

    // -------------------------------------------------------------------------------- //
    
    private static void Salvar(String nomeArquivo){
        
        BufferedWriter buffer;
        FileWriter arquivo;
        String aux;
        int i, j;

        try{

            arquivo = new FileWriter(nomeArquivo);
            buffer = new BufferedWriter(arquivo);

            aux = String.valueOf(n) + " " + String.valueOf(nConexoes) + "\n";
            buffer.write(aux);

            for (i = 0; i < n; i++){

                aux = "";
                for (j = 0; j < n; j++)
                    aux += " " + placa[i][j];
                buffer.write(aux + "\n");
            }

            for (Tupla t : conexoes){

                aux = String.valueOf(t.a()) + " " + String.valueOf(t.b()) + "\n";
                buffer.write(aux);
            }

            buffer.close();
            arquivo.close();
        }
        catch (IOException e){

            System.err.println (e.getMessage ());
        }
    }

    // -------------------------------------------------------------------------------- //
    
    private static void Imprimir(){
        
        int i, j;
        
        for (i = 0; i < n; i++){
            
            for (j = 0; j < n; j++)
                System.out.print(placa[i][j] + " ");
            System.out.println();
        }

        //for (Tupla t : conexoes)
        //    System.out.println(t.a() + " --> " + t.b());
    }
}

class Tupla{

    private int a;
    private int b;

    public Tupla(int a, int b){

        this.a = a;
        this.b = b;
    }

    public int a(){
        return a;
    }

    public int b(){
        return b;
    }
}


