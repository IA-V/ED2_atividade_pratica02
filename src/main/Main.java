package main;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import tads.*;
import leitor.LeitorCsv;

public class Main {
	public static void main(String[] args) {
		HashEncadeado listaHash = new HashEncadeado(700000);
		
		//listaHash.calcularHash("inch");
		
		/*ArvoreRN a1 = new ArvoreRN();
		ArvoreRN a2 = a1;
		
		System.out.println(a2 == a1);*/
		
		/*final File folder = new File("csv/.");
	    final List<File> fileList = Arrays.asList(folder.listFiles());*/
	    
	    /*for(File file: fileList) {
	    	System.out.println(file.getName());
	    }*/
	    
	    try {
	    	LeitorCsv.lerArquivos(listaHash);
	    } catch (IOException e) {
	    	System.out.println(e.getMessage());
	    }
	}
}
