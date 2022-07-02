package main;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import indice.ItemIndiceInvertido;
import indice.ParQtdId;
import tads.*;
import leitor.LeitorCsv;
public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		HashEncadeado listaHash = new HashEncadeado(700000);
		
		listaHash.calcularHash("inch");
		
		ArvoreRN a1 = new ArvoreRN();
		ArvoreRN a2 = a1;
		listaHash.buscar("sexy");
		//System.out.println(a2 == a1);
		
		// a2.inserir(new ItemIndiceInvertido("4"));
		// a2.inserir(new ItemIndiceInvertido("1"));
		// a2.inserir(new ItemIndiceInvertido("2"));
		// a2.inserir(new ItemIndiceInvertido("5"));
		
		// a1.listar();
		// a2.inserir(new ItemIndiceInvertido("5"));
		// a1.listar();
		// System.out.println(a2 == a1);
		// System.out.println(a1.buscar("1").getPalavra());
		
		// a2.remover("1");
		// a1.listar();
		//System.out.println(a1.buscar("1").getPalavra());
		
		//System.out.println(a1.buscar("3").getPalavra());
		
		ArvoreAvl a3 = new ArvoreAvl();
		
		// a3.inserir(new ItemIndiceInvertido("4"));
		// a3.inserir(new ItemIndiceInvertido("1"));
		// a3.inserir(new ItemIndiceInvertido("2"));
		// a3.inserir(new ItemIndiceInvertido("5"));

		// listaHash.inserir(new ItemIndiceInvertido("4"));
		// listaHash.inserir(new ItemIndiceInvertido("1"));
		// listaHash.inserir(new ItemIndiceInvertido("2"));
		// listaHash.inserir(new ItemIndiceInvertido("5"));
		
		//a3.listar();
		
		// a3.remover("4");
		// a3.listar();
		
		final File folder = new File("/Users/pvborges/IdeaProjects/Trab02ED2-PauloVictorBorges/src/datasets/amazon_com.csv");
	    // final List<File> fileList = Arrays.asList(folder.listFiles());
	    

		HashMap hashItens = new HashMap();
		int op = 1;
		do{
			Scanner leitor = new Scanner(System.in);
			System.out.println("digite qual estrutura de dados: \n"+
								" 1 - HASH\n"+
								" 2 - HASH\n"+
								" 3 - HASH\n"+
								" 0 - SAIR");
			op = leitor.nextInt();
			if (op != 1 && op!= 0) op = 1;
			switch(op){
				case 0:
					System.out.println("Obrigado por utilizar o programa!");
					break;
				case 1:	    
					try {
						LeitorCsv.criarIndiceInvertido(listaHash);
						hashItens = listaHash.listar();
					} catch (IOException e) {
						System.out.println(e.getMessage());
					}
					criarRecomendacao(listaHash, "with");
					}
					break;
		}while(op != 0);
		// criarIndiceInvertido(a3,"/Users/pvborges/IdeaProjects/Trab02ED2-PauloVictorBorges/src/datasets/amazon_com.csv");
	}

	public static void criarRecomendacao(HashEncadeado hash, String termo){
		int count = 0;
		Double peso = 0.0;
		for (int i=0; i<hash.getTamanhoAtual(); i++){
			ItemIndiceInvertido item = hash.buscar(termo);
			if(item != null) count ++;
			
			peso = calculaPeso(hash.getTamanhoAtual(), count, 1);
			calculaRelevancia(termo, peso, 2); //Onde numTermosDistintos é o número de termos distintos da descrição i
		}
		System.out.println();
	}

	public static Double calculaRelevancia(String termo, Double peso, Integer numTermosDistintos){ 
		Double relevancia = 0.0;
		relevancia = 1/ numTermosDistintos * (peso);
		return relevancia;
	}

	public static Double calculaPeso(Integer numProdutosDS, Integer numOcorrencias, Integer qtdProdutosComTermo){
		Double peso = 0.0;

		peso = numOcorrencias * Math.log(numProdutosDS)/qtdProdutosComTermo;
		return peso;
	}
}
