package main;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
		
		//System.out.println(a2 == a1);
		
		// a2.inserir(new ItemIndiceInvertido("4"));
		// a2.inserir(new ItemIndiceInvertido("1"));
		// a2.inserir(new ItemIndiceInvertido("2"));
		// a2.inserir(new ItemIndiceInvertido("5"));
		
		a1.listar();
		a2.inserir(new ItemIndiceInvertido("5"));
		a1.listar();
		// System.out.println(a2 == a1);
		// System.out.println(a1.buscar("1").getPalavra());
		
		a2.remover("1");
		a1.listar();
		//System.out.println(a1.buscar("1").getPalavra());
		
		//System.out.println(a1.buscar("3").getPalavra());
		
		ArvoreAvl a3 = new ArvoreAvl();
		
		// a3.inserir(new ItemIndiceInvertido("4"));
		// a3.inserir(new ItemIndiceInvertido("1"));
		// a3.inserir(new ItemIndiceInvertido("2"));
		// a3.inserir(new ItemIndiceInvertido("5"));

		listaHash.inserir(new ItemIndiceInvertido("4"));
		listaHash.inserir(new ItemIndiceInvertido("1"));
		listaHash.inserir(new ItemIndiceInvertido("2"));
		listaHash.inserir(new ItemIndiceInvertido("5"));
		
		//a3.listar();
		
		a3.remover("4");
		a3.listar();
		
		final File folder = new File("/Users/pvborges/IdeaProjects/Trab02ED2-PauloVictorBorges/src/datasets/amazon_com.csv");
	    // final List<File> fileList = Arrays.asList(folder.listFiles());
	    
	    // for(File file: fileList) {
	    // 	System.out.println(file.getName());
	    // }
	    
	    try {
	    	LeitorCsv.criarIndiceInvertido(listaHash);
	    } catch (IOException e) {
	    	System.out.println(e.getMessage());
	    }
		criarRecomendacao(listaHash, "with");

		int op = 1;
		do{
			Scanner leitor = new Scanner(System.in);
			System.out.println("digite qual estrutura de dados: \n"+
								" 1 -  \n"+
								" 0 - SAIR");
			op = leitor.nextInt();

			switch(op){
				case 1:
					System.out.println(" O documento"  + " possui "  + " termos");
			}
		}while(op != 0);
		// criarIndiceInvertido(a3,"/Users/pvborges/IdeaProjects/Trab02ED2-PauloVictorBorges/src/datasets/amazon_com.csv");
	}

	/**
	 * @param av
	 * @throws FileNotFoundException
	 */
	public static void criarIndiceInvertido(ArvoreAvl av, String filePath) throws FileNotFoundException{
		Scanner sc = new Scanner(new File(filePath));
        sc.useDelimiter(",");
		ArrayList<String> linha = new ArrayList<>();
		//14 colunas o dataset tem 
		int cont=0;
        while (sc.hasNext()){
			linha.add(sc.next());
			cont++;
        }
		// System.out.println(cont + " "+ linha.size());
	}

	public static void criarRecomendacao(HashEncadeado hash, String termo){
		int count = 0;
		for (int i=0; i<hash.getTamanhoAtual(); i++){
			ItemIndiceInvertido item = hash.buscar(termo);
			if(item != null) count ++;
			
			calculaPeso(hash.getTamanhoAtual(), count, 1);
			Integer relevanciaItem = 0;
			if (item != null){
				relevanciaItem++;
			}
		}
	}

	public static Double calculaRelevancia(String termo, Double peso, Integer numTermosDistintos){ //Onde numTermosDistintos é o número de termos distintos da descrição i
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
