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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileInputStream;

public abstract class LeitorCsv {
	
	private static ArrayList<String> stopwords = new ArrayList<>();
	
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
		
		final File folder = new File("csv/.");
	    final List<File> fileList = Arrays.asList(folder.listFiles());
		
	    for(File file: fileList) {
	    	BufferedReader leitor = new BufferedReader(new InputStreamReader(new FileInputStream("csv/" + file.getName()), "UTF-8"));
	    	System.out.println(file.getName());
			String linha = leitor.readLine();
			linha = leitor.readLine();
			
			while(linha != null) {
				//System.out.println("oi");
				String[] colunas = linha.split(",");
				String[] palavras = null;
				
				String regex = "\"([^\"]*)\"";
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(linha); // linha é a variável que contém a linha que foi lida do arquivo
				if (matcher.find()) {
				    String textoEntreAspas = matcher.group(1); // obtém o grupo lido da regex
				    //System.out.println(textoEntreAspas);
				    palavras = textoEntreAspas.split(" ");
				} else {
					palavras = colunas[2].split(" ");
					/*for(String palavra: palavras) {
						System.out.println(palavra);
					}*/
					
				}
				
				for(String palavra: palavras) {
					String palavraAux = palavra.replaceAll("[\\|\\!\\/\\#\\½\\”\\’\\'\\“\\\"\\+\\.\\^:,]","").toLowerCase();

					if(palavraAux.replaceAll("\\u00A0"," ").toLowerCase().split(" ").length > 1) {
						palavraAux = palavraAux.replaceAll("\\u00A0"," ").toLowerCase().split(" ")[0];
					}/* else if(palavraAux.replaceAll("\\-"," ").toLowerCase().split(" ").length > 1) {
						palavraAux = palavraAux.replaceAll("\\u00A0"," ").toLowerCase().split(" ")[1];
					}*/
					if(!isStopWord(palavraAux)) {
						//System.out.println(palavraAux);						
						ItemIndiceInvertido novoItem = new ItemIndiceInvertido(palavraAux);
						String idProduto = colunas[0];
						novoItem.addParQtdId(new ParQtdId(Integer.parseInt(idProduto)));
						estrutura.inserir(novoItem);
						
					} else {
						continue;
					}
				}
				//System.out.println(linha);
				
				linha = leitor.readLine();
				
			}
			leitor.close();
	    }
	}
}
