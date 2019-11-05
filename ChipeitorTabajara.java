/*
 *  T4 - Projeto e Otimização de Algoritmos
 *
 *  Willian Schmiele Dias
 *
 *  2019/2
 *
 */

import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.Math;

public class ChipeitorTabajara{

	private static int[][] placa;
	private static ArrayList<Tupla> conexoes;
	private static int n, nConexoes;

	public static void main(String[] args){
		
		if (args.length == 1){
			
			Carregar(args[0]);
			Imprimir();
    		BuscarSolucao();
		}
		else System.out.println("Formato: java ChipeitorTabajara arquivoEntrada");
	}
	
	private static void BuscarSolucao(){
		
		boolean continuar;
		int comprimento;
		
		/*continuar = !true;
		while (continuar){
			
			
		}*/
        System.out.printf("%.4f\n", SomarDistancias());
	}
	
	private static boolean Avaliar(){
		
		return true;
	}
	
	private static void Swap(int pos1x, int pos1y, int pos2x, int pos2y){
		
		int aux;
		
		aux = placa[pos1x][pos1y];
		placa[pos1x][pos1y] = placa[pos2x][pos2y];
		placa[pos2x][pos2y] = aux;
	}

	private static double SomarDistancias(){

		double resultado;
        int a, b;
		
		resultado = 0.0;
		for (Tupla t : conexoes){

            a = t.a();
            b = t.b();
			resultado += CalcularDistancia(BuscarComponente(a), BuscarComponente(b));
		}
		
		return resultado;
	}

    private static Tupla BuscarComponente(int componente){

        int i, j;

        for (i = 0; i < n; i++){

            for (j = 0; j < n; j++){

                if (placa[i][j] == componente)
                    return new Tupla(i, j);
            }
        }

        return null;
    }
	
	private static double CalcularDistancia(Tupla a, Tupla b){
		
		double resultado;
        int l1, c1, l2, c2;

        l1 = a.a(); c1 = a.b();
        l2 = b.a(); c2 = b.b();
		
		resultado = Math.sqrt(Math.pow(l2 - l1, 2) + Math.pow(c2 - c1, 2));
        System.out.println("RESULTADO_EUCLIDIANA: " + resultado);
		return resultado;
	}
	
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
			
            arquivo.close ();
        }
        catch (IOException e){

            System.err.println (e.getMessage ());
        }
	}
	
	private static void Salvar(String nomeArquivo){
		
		
	}
	
	private static void Imprimir(){
		
		int i, j;
		
		for (i = 0; i < n; i++){
			
			for (j = 0; j < n; j++)
				System.out.print(placa[i][j] + " ");
			System.out.println();
		}

		for (Tupla t : conexoes)
			System.out.println(t.a() + " --> " + t.b());
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


