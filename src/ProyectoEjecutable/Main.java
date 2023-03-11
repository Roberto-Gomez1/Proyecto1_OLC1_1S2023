package ProyectoEjecutable;

import Analizadores.Lexico;
import Analizadores.Nodo_binario;
import Analizadores.Automata;
import Analizadores.parser;
import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception{
        File documento = new File("src/Analizadores/prueba.olc");
        Scanner sca = new Scanner(documento);
        String aux = "";
        while (sca.hasNextLine()){
            aux += sca.nextLine()+"\n";
        }
        interpretar(aux);


    }
    private static void interpretar(String aux){
        try{
            Lexico sca = new Lexico(new java.io.StringReader(aux));
            parser parser = new parser(sca);
            parser.parse();
            System.out.println("Analisis finalizado");


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}