package leitor;
import tads.HashEncadeado;
import tads.ArvoreAvl;
import tads.ArvoreRN;
import indice.*;
import interfaces.Dicionario;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;

public abstract class LeitorCsv {
	
	private static ArrayList<String> stopwords = new ArrayList<>();
	/*private static HashEncadeado hash = new HashEncadeado(625000);
	private static ArvoreAvl avl;
	private static ArvoreRN rn;*/
	
	// calcula um hash para usar como idProduto. idProduto eh um hash porque nenhum arquivo tem um id associado a cada produto
	private static int calcularHash(String chave) {
		byte[] arrayBytes = chave.getBytes();
		
		int contador = 0;
		if(arrayBytes.length >= 25) {
			contador = 25;
		} else {
			contador = arrayBytes.length;
		}
		
		int soma = 0;
		for(int i = 0; i < contador; i++) {
			//System.out.println(arrayBytes[i]);
			soma += arrayBytes[i];
		}
		
		int hash = (int)Math.floor(700000*((soma*0.6180339887)%1));
		
		return hash;
	}
	
	public static void inserirStopWords() {
		String linha;
		try {
			BufferedReader leitor = new BufferedReader(new InputStreamReader(new FileInputStream("csv/stopwords.csv"), "UTF-8"));
			while((linha = leitor.readLine()) != null) {
				stopwords.add(linha);
			}
			
			leitor.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private static boolean isStopWord(String palavra) {
		for(String word: stopwords) {
			if(word.compareTo(palavra) == 0 || palavra.compareTo("") == 0) {
				return true;
			}
		}
		
		return false;
	}
	
	public static void lerArquivos(Dicionario estrutura) throws IOException { // Recebe como argumento 
		inserirStopWords();
		
		final File folder = new File("csv/.");
	    final List<File> fileList = Arrays.asList(folder.listFiles());
		
	    for(File file: fileList) {
	    	BufferedReader leitor = new BufferedReader(new InputStreamReader(new FileInputStream("csv/" + file.getName()), "UTF-8"));
	    	System.out.println(file.getName());
			String linha = leitor.readLine();
			while((linha = leitor.readLine()) != null) {
				String[] colunas = linha.split(",");
				String[] palavras = colunas[7].split(" ");
				
				for(String palavra: palavras) {
					String palavraAux = palavra.replaceAll("[\\\"\\-\\+\\.\\^:,]","").toLowerCase();
					if(!isStopWord(palavraAux)) {
						
						ItemIndiceInvertido item = estrutura.buscar(palavraAux);
						
						if(item != null) {
							ArrayList<ParQtdId> pares = item.getParQtdId();
							String nomeProduto = colunas[0];
							int idProduto = calcularHash(nomeProduto);
							
							item.incrementarQtd(1, idProduto);
						} else {
							ItemIndiceInvertido novoItem = new ItemIndiceInvertido(palavraAux);
							String nomeProduto = colunas[0];
							int idProduto = calcularHash(nomeProduto);
							novoItem.addParQtdId(new ParQtdId(idProduto));
							estrutura.inserir(novoItem);
						}
						
					} else {
						continue;
					}
				}
			}
			leitor.close();
	    }
	}
}
