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
	private static ArrayList<Conexao> conexoes;
	private static int n, nConexoes;

	public static void main(String[] args){
		
		if (args.length == 1){
			
			Carregar(args[0]);
			ImprimirPlaca();
			for (Conexao c : conexoes)
				System.out.println(c.getOrigem() + " --> " + c.getDestino());
			//BuscarSolucao();
		}
		else System.out.println("Formato: java ChipeitorTabajara arquivoEntrada");
	}
	
	private static void BuscarSolucao(){
		
		boolean continuar;
		int comprimento;
		
		continuar = !true;
		while (continuar){
			
			
		}
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
	
	private static double SomaDistancias(){
		
		double resultado;
		
		resultado = 0.0;
		/*for (Conexao c : conexoes){
			
			resultado += 
		}*/
		
		return resultado;
	}
	
	private static double CalcularDistancia(int l1, int c1, int l2, int c2){
		
		double resultado;
		
		resultado = Math.sqrt(Math.pow(l2 - l1, 2) + Math.pow(c2 - c1, 2));		
		return resultado;
	}
	
	private static void Carregar(String nomeArquivo){
		
		int i, j, origem, destino;
		BufferedReader buffer;
        FileReader arquivo;
        String[] linha;

        try{

            arquivo = new FileReader(nomeArquivo);
            buffer = new BufferedReader(arquivo);

            linha = buffer.readLine().split(" ");
			n = Integer.valueOf(linha[0]);
			nConexoes = Integer.valueOf(linha[1]);
			
			placa = new int[n][n];
			conexoes = new ArrayList<Conexao>();
			
			for (i = 0; i < n; i++){
				
                linha = buffer.readLine().trim().split("\\s+");
				for (j = 0; j < n; j++)
					placa[i][j] = Integer.valueOf(linha[j]);
			}
			
            for (i = 0; i < nConexoes; i++){

                linha = buffer.readLine().split(" ");
				origem = Integer.valueOf(linha[0]);
				destino = Integer.valueOf(linha[1]);
				conexoes.add(new Conexao(origem, destino));
            }
			
            arquivo.close ();
        }
        catch (IOException e){

            System.err.println (e.getMessage ());
        }
	}
	
	private static void Salvar(String nomeArquivo){
		
		
	}
	
	private static void ImprimirPlaca(){
		
		int i, j;
		
		for (i = 0; i < n; i++){
			
			for (j = 0; j < n; j++)
				System.out.print(placa[i][j] + " ");
			System.out.println();
		}
	}
}

class Conexao{
	
	int origem, destino;
	
	public Conexao(int origem, int destino){
		
		this.origem = origem;
		this.destino = destino;
	}
	
	public int getOrigem(){
		return origem;
	}
	
	public int getDestino(){
		return destino;
	}
}
