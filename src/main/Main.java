package main;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import indice.ItemIndiceInvertido;
import tads.*;
import leitor.LeitorCsv;

public class Main {
	public static void main(String[] args) {
		HashEncadeado listaHash = new HashEncadeado(700);
		
		
		//listaHash.calcularHash("inch");
		
		//ArvoreRN a1 = new ArvoreRN();
		/*ArvoreRN a2 = a1;
		
		//System.out.println(a2 == a1);
		
		a2.inserir(new ItemIndiceInvertido("4"));
		a2.inserir(new ItemIndiceInvertido("1"));
		a2.inserir(new ItemIndiceInvertido("2"));
		a2.inserir(new ItemIndiceInvertido("5"));
		
		//a1.listar();
		//a2.inserir(new ItemIndiceInvertido("5"));
		//a1.listar();
		//System.out.println(a2 == a1);
		//System.out.println(a1.buscar("1").getPalavra());
		
		a2.remover("1");
		a1.listar();*/
		//System.out.println(a1.buscar("1").getPalavra());
		
		//System.out.println(a1.buscar("3").getPalavra());
		
		//ArvoreAvl a3 = new ArvoreAvl();
		
		/*a3.inserir(new ItemIndiceInvertido("4"));
		a3.inserir(new ItemIndiceInvertido("1"));
		a3.inserir(new ItemIndiceInvertido("2"));
		a3.inserir(new ItemIndiceInvertido("5"));
		
		//a3.listar();
		
		a3.remover("4");
		a3.listar();*/
		
		/*final File folder = new File("csv/.");
	    final List<File> fileList = Arrays.asList(folder.listFiles());*/
	    
	    /*for(File file: fileList) {
	    	System.out.println(file.getName());
	    }*/
	    
	    try {
	    	LeitorCsv.criarIndiceInvertido(listaHash);
	    	listaHash.listar();
	    } catch (IOException e) {
	    	System.out.println(e.getMessage());
	    }
	}
}
