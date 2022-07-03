package main;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import indice.ItemIndiceInvertido;
import indice.ParQtdId;
import tads.*;
import leitor.LeitorCsv;
public class Main {
	
	private static final double limiar = 0.01;
	
	public static void main(String[] args) throws IOException {
		HashEncadeado listaHash = new HashEncadeado(100);
		
		listaHash.calcularHash("inch");

		//listaHash.buscar("sexy");
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
		
		final File folder = new File("/csv/amz.csv");
	    // final List<File> fileList = Arrays.asList(folder.listFiles());
		HashMap<Integer, Double> parProdutoRelevancia = new HashMap<>();

		HashMap<Integer, ItemIndiceInvertido> hashItens = new HashMap();
		LeitorCsv.criarIndiceInvertido(listaHash);
		hashItens = listaHash.listar();
		int op = 1;
		do{
			parProdutoRelevancia.clear();
			Scanner leitor = new Scanner(System.in);
			System.out.println("digite qual estrutura de dados: \n"+
								" 1 - Buscar por termo\n"+
								" 2 - Buscar por relevancia\n"+
								" 3 - Listar Hash \n" + 
								" 0 - SAIR");
			op = leitor.nextInt();
			Scanner leitorString = new Scanner(System.in);
			switch(op){
				case 0:
					System.out.println("Obrigado por utilizar o programa!");
					break;
				case 3:
					System.out.println(hashItens);
					break;	
				case 2:
					System.out.println("Digite o limiar da relevancia: ");
					Double relevancia = leitor.nextDouble();
					Double relevanciaArmazenada = 0.0;
					hashItens = listaHash.listar();
					
					for (int i =0 ; i<hashItens.size(); i++){
						if(hashItens.get(i) != null){
							// System.out.println(" minha relevancia " + relevancia + " a que ta armazenada : " + hashItens.get(i).getRelevancia());
							relevanciaArmazenada = (hashItens.get(i).getRelevancia());
							if (relevanciaArmazenada.longValue() <= relevancia){
								System.out.println(hashItens.get(i));
							}
					}
					}
					break;		
				case 1:	    
						// LeitorCsv.criarIndiceInvertido(listaHash);
						// hashItens = listaHash.listar();
						System.out.println("Quantos termos serao inseridos? ");
						int qtdTermos = Integer.parseInt(leitorString.nextLine());
						System.out.println("Digite o(s) termo(s) desejado(s): ");
						String termoEscolhido = leitorString.nextLine();
						ArrayList<Integer> intersecao = new ArrayList<>(1);
						
						ItemIndiceInvertido item = null;
						String[] termos = termoEscolhido.split(" ");
						
					
						intersecao = calcularIntersecaoProdutos(termos, listaHash);
						
						
						for(Integer idProduto: intersecao) {
							parProdutoRelevancia.put(idProduto, criarRecomendacao(listaHash, termos, idProduto, intersecao.size()));
						}
						
						System.out.println("Produtos recomendados: ");
						for(Integer idProduto: parProdutoRelevancia.keySet()) {
							if(parProdutoRelevancia.get(idProduto) >= limiar) {
								String nomeProduto = LeitorCsv.getNomeProduto(idProduto);
								if(nomeProduto != null) {
									System.out.println(nomeProduto);
								}
							}
						}
						System.out.println();
						/*for (int i =0 ; i<hashItens.size(); i++){
							if(hashItens.get(i) != null)
							if (hashItens.get(i).getPalavra().compareTo(termoEscolhido) == 0){
								System.out.println(hashItens.get(i));
							}
						}*/
						break;
				}
					
				
		}while(op != 0);
	}

	public static double criarRecomendacao(HashEncadeado hash, String[] palavras, int idProduto, int qtdProdutosComTermo){
		int qtd = 0;
		Double peso = 0.0;
		for(String palavra: palavras) {
			ItemIndiceInvertido item = hash.buscar(palavra);
			if(item != null) {
				for(Integer chave: item.getParQtdId().keySet()) {
					if(chave == idProduto) {
						qtd = item.getParQtdId().get(chave);
						break;
					}
				}
				peso += calcularPeso(LeitorCsv.getQtdProdutos(), qtd, qtdProdutosComTermo);
				//System.out.println(peso);
			}
			
		}
		//System.out.println(calcularRelevancia(peso, LeitorCsv.getQtdPalavrasProduto(idProduto).size()));
		return calcularRelevancia(peso, LeitorCsv.getQtdPalavrasProduto(idProduto).size());
		//parProdutoRelevancia.put(idProduto, calcularRelevancia(peso, LeitorCsv.getQtdPalavrasProduto(idProduto).size()));
		/*for (int i=0; i<hash.getTamanhoAtual(); i++){
			ItemIndiceInvertido item = hash.buscar(termo);
			if(item != null) count ++;
			
			peso = calcularPeso(LeitorCsv.getQtdProdutos(), count, 1);
			calcularRelevancia(termo, peso, 2);
		}*/
		//System.out.println();
	}

	public static Double calcularRelevancia(double peso, Integer numTermosDistintos){ //Onde numTermosDistintos eh o numero de termos distintos da descricao i
		double relevancia = 0.0;
		double somaPesos = 0.0;
		relevancia = (1.0/ numTermosDistintos) * (peso);
		
		return relevancia;
	}

	public static Double calcularPeso(Integer numProdutosDS, Integer numOcorrencias, Integer qtdProdutosComTermo){
		Double peso = 0.0;

		peso = numOcorrencias * Math.log(numProdutosDS)/qtdProdutosComTermo;
		return peso;
	}
	
	private static ArrayList<Integer> calcularIntersecaoProdutos(String[] termos, HashEncadeado listaHash) {
		boolean listaCopiada = false;
        ArrayList<Integer> listaIntersecao = new ArrayList<>();
        ArrayList<Integer> listaAux = new ArrayList<>();
		HashMap<Integer, Integer> hashAux = new HashMap<>();
        
		for(String termo: termos) {
			if(listaHash.buscar(termo) != null) {
				hashAux = listaHash.buscar(termo).getParQtdId();
				if (!listaCopiada) {
	                for (Integer chavePar: hashAux.keySet()) { // Para cada lista de cada termo
	                    listaAux.add((Integer) chavePar); // Copiando a primeira lista de id's para a auxiliar
	                }
	                listaCopiada = true;
	            } else {
	                for (Integer chavePar: hashAux.keySet()) { // Para cada lista de cada termo
	                    for (Integer chaveListaAux: listaAux) {
	                        if (((Integer) chavePar).equals(chaveListaAux)) { // Se true é porque o par em questão existe nos dois conjuntos, adiciona-o na lista interseccao
	                        	listaIntersecao.add((Integer) chavePar);
	                        }
	                    }
	                }
	                listaAux.clear();
	                listaAux = new ArrayList<>(listaIntersecao);
	                listaIntersecao.clear();
	            }
			}
			
		}
		
		return listaAux;
	}
}
