package leitor;
import indice.*;
import interfaces.Dicionario;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileInputStream;

public abstract class LeitorCsv {
	
	private static ArrayList<String> stopwords = new ArrayList<>();
	private static int qtdProdutos = 0;
	private static TreeSet<String> palavrasProduto = new TreeSet<>();
	
	public static void inserirStopWords() {
		String linha;
		try {
			BufferedReader leitor = new BufferedReader(new InputStreamReader(new FileInputStream("stopwords.csv"), "UTF-8"));
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
			if(word.compareTo(palavra) == 0 || palavra.compareTo("") == 0 || palavra.length() <= 9 || palavra.isBlank()) {
				return true;
			}
		}
		
		return false;
	}
	
	public static void criarIndiceInvertido(Dicionario estrutura) throws IOException { // Recebe como argumento 
		inserirStopWords();
		
		// final File folder = new File("csv/.");
	    // final List<File> fileList = Arrays.asList(folder.listFiles());
		
	    // for(File file: fileList) {
	    	// BufferedReader leitor = new BufferedReader(new InputStreamReader(new FileInputStream("/Users/pvborges/IdeaProjects/Trab02ED2-PauloVictorBorges/src/datasets/amazon_com.csv"), "UTF-8"));
			BufferedReader leitor = new BufferedReader(new InputStreamReader(new FileInputStream("csv/amz.csv"), "UTF-8"));
			String linha = leitor.readLine();
			linha = leitor.readLine();
			
			while(linha != null) {
				qtdProdutos++;
				String[] colunas = linha.split(",");
				String[] palavras = null;
				
				String regex = "\"([^\"]*)\"";
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(linha); // linha ï¿½ a variï¿½vel que contï¿½m a linha que foi lida do arquivo
				if (matcher.find()) {
				    String textoEntreAspas = matcher.group(1); // obtï¿½m o grupo lido da regex
				    palavras = textoEntreAspas.split(" ");
				} else {
					palavras = colunas[2].split(" ");					
				}
				
				for(String palavra: palavras) {
					String palavraAux = palavra.replaceAll("[\\|\\!\\/\\#\\Â½\\â€�\\â€™\\'\\â€œ\\\"\\+\\.\\^:,]","").toLowerCase();

					if(palavraAux.replaceAll("\\u00A0"," ").toLowerCase().split(" ").length > 1) {
						palavraAux = palavraAux.replaceAll("\\u00A0"," ").toLowerCase().split(" ")[0];
					}/* else if(palavraAux.replaceAll("\\-"," ").toLowerCase().split(" ").length > 1) {
						palavraAux = palavraAux.replaceAll("\\u00A0"," ").toLowerCase().split(" ")[1];
					}*/
					if(!isStopWord(palavraAux)) {
						//System.out.println(palavraAux);						
						ItemIndiceInvertido novoItem = new ItemIndiceInvertido(palavraAux);
						String idProduto = colunas[0];
						novoItem.addParQtdId(Integer.parseInt(idProduto), 1);
						estrutura.inserir(novoItem);
						
					} else {
						continue;
					}
				}
				//System.out.println(linha);
				
				linha = leitor.readLine();
				
			}
			leitor.close();
	    // }
	}
	
	public static int getQtdProdutos() {
		return qtdProdutos;
	}
	
	public static String getNomeProduto(int idProduto) {
		String nome = null;
		try {
			BufferedReader leitor = new BufferedReader(new InputStreamReader(new FileInputStream("csv/amz.csv"), "UTF-8"));
			String linha = leitor.readLine();
			linha = leitor.readLine();
			
			while((linha = leitor.readLine()) != null) {
				String[] colunas = linha.split(",");
				
				if(idProduto == Integer.parseInt(colunas[0])) {
					//System.out.println(colunas[1]);
					nome = colunas[1];
				}
			}
			leitor.close();
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
		return nome;
	}
	
	public static TreeSet<String> getQtdPalavrasProduto(int idProduto) {
		try {
			BufferedReader leitor = new BufferedReader(new InputStreamReader(new FileInputStream("csv/amz.csv"), "UTF-8"));
			String linha = leitor.readLine();
			linha = leitor.readLine();
			
			while((linha = leitor.readLine()) != null) {
				String[] colunas = linha.split(",");
				String[] palavras = null;
				
				if(idProduto == Integer.parseInt(colunas[0])) {
					String regex = "\"([^\"]*)\"";
					Pattern pattern = Pattern.compile(regex);
					Matcher matcher = pattern.matcher(linha);
					
					if (matcher.find()) {
					    String textoEntreAspas = matcher.group(1); // obtï¿½m o grupo lido da regex
					    //System.out.println(textoEntreAspas);
					    palavras = textoEntreAspas.split(" ");
					} else {
						palavras = colunas[2].split(" ");
					}

					for(String palavra: palavras) {
						String palavraAux1 = palavra.replaceAll("[\\|\\!\\/\\#\\Â½\\â€�\\â€™\\'\\â€œ\\\"\\+\\.\\^:,]","").toLowerCase();
						if(palavraAux1.replaceAll("\\u00A0"," ").toLowerCase().split(" ").length > 1) {
							palavraAux1 = palavraAux1.replaceAll("\\u00A0"," ").toLowerCase().split(" ")[0];
						}
							
						palavrasProduto.add(palavra);
					}
				}
			}
			leitor.close();
			return palavrasProduto;
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
		return palavrasProduto;
	}
}
