package Analizadores;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Automata {
    private Nodo_binario arbol_expresion;
    private ArrayList<Tabla> p_tabla = new ArrayList<>();
    private ArrayList<Tabla> aux_tabla = new ArrayList<>();
    private ArrayList<Tabla> aux_nombre = new ArrayList<>();

    private int conteo = 1;
    private int contador = 1;
    private String aa = "";
    public Automata(Nodo_binario arbol_expresion) {
        Nodo_binario primero = new Nodo_binario(".");
        Nodo_binario hash = new Nodo_binario("#");
        hash.setHoja(true);
        hash.setAnulable(false);
        primero.setHijo_derecho(hash);
        primero.setHijo_izquierdo(arbol_expresion);
        this.arbol_expresion = primero;
        asignacion(this.arbol_expresion);
        conteo=0;
        metodo(this.arbol_expresion);
        aa= "";
        System.out.println("digraph G {\n"+crear_arbol(this.arbol_expresion,conteo)+"}");
        //System.out.println("---------------------------------------");

        tabla_siguientes();
        System.out.println(aa);
        contador=0;
    }
    public void tabla_siguientes (){

        for (int i=0;i<this.aux_tabla.size();i++){
            Tabla hola = this.aux_tabla.get(i);
            ArrayList<Integer> numero = hola.getNumero();
            //System.out.println(numero.size());
            if (numero.size() >1){
                this.aux_tabla.remove(i);
                for(Integer num: numero){
                    ArrayList<Integer> lista = new ArrayList<Integer>();
                    lista.add(num);
                    this.aux_tabla.add(i, new Tabla(lista,hola.getLexema(),hola.getSiguiente()));
                }
            }
        }

        for (int i=0; i<this.aux_tabla.size();i++){
            ArrayList<Integer> numero = this.aux_tabla.get(i).getNumero();
            for(int j=i+1; j<this.aux_tabla.size();j++){
                Tabla hola = this.aux_tabla.get(j);
                if (numero.equals(hola.getNumero())){
                    this.aux_tabla.remove(j);
                    this.aux_tabla.get(i).getSiguiente().addAll(hola.getNumero());

                }
            }
        }

        for (int i=0;i<this.aux_nombre.size();i++){
            ArrayList<Integer> numero = this.aux_nombre.get(i).getNumero();
            for (int j=0;j<this.aux_tabla.size();j++){
                if (numero.equals(this.aux_tabla.get(j).getNumero())){
                    Tabla hola = this.aux_tabla.get(j);
                    this.aux_tabla.remove(j);
                    Integer [] arreglo = hola.getSiguiente().toArray(new Integer[0]);
                    Arrays.sort(arreglo);
                    ArrayList<Integer> aaa = new ArrayList<Integer>(Arrays.asList(arreglo));
                    this.aux_tabla.add(j, new Tabla(hola.getNumero(),this.aux_nombre.get(i).getLexema(),aaa));
                }
            }
        }

        Tabla hola = this.aux_tabla.get(0);
        this.aux_tabla.remove(0);
        this.aux_tabla.add(hola);

        ArrayList<Integer> le = new ArrayList<>();
        this.aux_tabla.add(new Tabla(le,"#",le));

        aa +=  "digraph G{\n"
                + "graph [ratio=fill];\n"
                + "node [label=\"\\N\", fontsize=15, shape=plaintext];\n"
                + "graph [bb=\"0,0,352,154\"];\n"
                + "arset [label=<\n"
                + "<TABLE ALIGN=\"LEFT\">\n"
                + "<TR><TD colspan=\"2\"  bgcolor=\"lemonchiffon4\">Hoja</TD>\n"
                + "<TD bgcolor=\"lemonchiffon4\">Siguientes</TD></TR>\n"
                + "<TR><TD> Numero </TD>\n"
                + "<TD> Lexema</TD>\n"
                + "<TD> Siguiente </TD></TR>\n";
        for (Tabla este : this.aux_tabla){
            aa += "<TR><TD>"+contador+"</TD>\n"
                    + "<TD>"+este.getLexema()+"</TD>\n"
                    + "<TD>"+este.getSiguiente()+"</TD></TR>\n";
            contador++;
            System.out.println("2 Primeros: "+este.getNumero() +" Lexema: "+este.getLexema()+ " Siguientes: "+este.getSiguiente());
        }
        aa+="</TABLE>>];\n}";
    }
    public void tabla_trans(){
        
    }
    public void metodo (Nodo_binario aux){
        if (aux ==null){
            return;
        }

        if (aux.isHoja()){
            aux.getPrimeros().add(aux.getCabecera());
            aux.getUltimos().add(aux.getCabecera());
            return;
        }

        metodo(aux.getHijo_izquierdo());
        metodo(aux.getHijo_derecho());
        if (aux.getDato().equals("*")){
            aux.setAnulable(true);
            aux.getPrimeros().addAll(aux.getHijo_izquierdo().getPrimeros());
            aux.getUltimos().addAll(aux.getHijo_izquierdo().getUltimos());

        }else if (aux.getDato().equals("+")){
            aux.setAnulable(aux.getHijo_izquierdo().isAnulable());
            aux.getPrimeros().addAll(aux.getHijo_izquierdo().getPrimeros());
            aux.getUltimos().addAll(aux.getHijo_izquierdo().getUltimos());
        }else if (aux.getDato().equals("?")){
            aux.setAnulable(true);
            aux.getPrimeros().addAll(aux.getHijo_izquierdo().getPrimeros());
            aux.getUltimos().addAll(aux.getHijo_izquierdo().getUltimos());
        }else if (aux.getDato().equals("|")){
            aux.setAnulable(aux.getHijo_izquierdo().isAnulable() || aux.getHijo_derecho().isAnulable());
            aux.getPrimeros().addAll(aux.getHijo_izquierdo().getPrimeros());
            aux.getPrimeros().addAll(aux.getHijo_derecho().getPrimeros());
            aux.getUltimos().addAll(aux.getHijo_izquierdo().getUltimos());
            aux.getUltimos().addAll(aux.getHijo_derecho().getUltimos());
        }else if (aux.getDato().equals(".")){
            aux.setAnulable(aux.getHijo_izquierdo().isAnulable() && aux.getHijo_derecho().isAnulable());
            if (aux.getHijo_izquierdo().isAnulable()){
                aux.getPrimeros().addAll(aux.getHijo_izquierdo().getPrimeros());
                aux.getPrimeros().addAll(aux.getHijo_derecho().getPrimeros());
            }else{
                aux.getPrimeros().addAll(aux.getHijo_izquierdo().getPrimeros());
            }
            if (aux.getHijo_derecho().isAnulable()){
                aux.getUltimos().addAll(aux.getHijo_izquierdo().getUltimos());
                aux.getUltimos().addAll(aux.getHijo_derecho().getUltimos());
            }else{
                aux.getUltimos().addAll(aux.getHijo_derecho().getUltimos());
            }
        }
    }
    public void asignacion(Nodo_binario aux){
        if (aux == null){
            return;
        }
        if (aux.isHoja()){
            aux.setCabecera(conteo);
            conteo++;
            return;
        }
        asignacion(aux.getHijo_izquierdo());
        asignacion(aux.getHijo_derecho());
    }
    public String crear_arbol(Nodo_binario actual, int padre){
        String graficar = "";
        conteo += 1;
        int aux = conteo;
        if (actual == null){
            conteo -=1;
            return graficar;
        }
        if(actual.isHoja()){
            graficar+= "N_"+aux+ "[shape = none label =<\n"
                    + "<TABLE border=\"1\" cellspacing=\"2\" cellpadding=\"10\" > \n"
                    + "<TR>\n"
                    + "<TD colspan=\"3\">"+actual.isAnulable()+"</TD>\n"
                    + "</TR>\n"
                    + "<TR>\n"
                    + "<TD>"+actual.getPrimeros()+"</TD>\n"
                    + "<TD >"+actual.getDato()+"</TD>\n"
                    + "<TD>"+actual.getUltimos()+"</TD>\n"
                    + "</TR>\n"
                    + "<TR>\n"
                    + "<TD colspan=\"3\">"+actual.getCabecera()+"</TD>\n"
                    + "</TR>\n"
                    + "</TABLE>>];";
            int num = actual.getCabecera();
            ArrayList<Integer> lista = new ArrayList<Integer>();
            lista.add(num);
            Tabla nuevo = new Tabla(lista,actual.getDato(),actual.getUltimos());
            this.aux_nombre.add(nuevo);
        }else{
            graficar+= "N_"+aux+"[shape = none label=<\n"
                    + "<TABLE border=\"1\" cellspacing=\"2\" cellpadding=\"10\" > \n"
                    + "<TR>\n"
                    + "<TD colspan=\"3\">"+actual.isAnulable()+"</TD>\n"
                    + "</TR>\n"
                    + "<TR>\n"
                    + "<TD >"+actual.getPrimeros()+"</TD>\n"
                    + "<TD >" + actual.getDato() +"</TD>\n"
                    + "<TD >"+actual.getUltimos()+"</TD>\n"
                    + "</TR>\n"
                    + "</TABLE>>];";
            if(actual.getDato().equals(".")){
                Tabla nuevo = new Tabla(actual.getHijo_izquierdo().getUltimos(),actual.getDato(),actual.getHijo_derecho().getPrimeros());
                this.aux_tabla.add(nuevo);

            }else if (actual.getDato().equals("+") || actual.getDato().equals("*")){
                Tabla nuevo = new Tabla(actual.getHijo_izquierdo().getUltimos(),actual.getDato(),actual.getHijo_izquierdo().getPrimeros());
                this.aux_tabla.add(nuevo);
            }
        }

        if (padre != 0 ){
            graficar+="N_"+padre+ " -> N_" + aux +";\n";
        }
        graficar+= crear_arbol(actual.getHijo_izquierdo(), aux);
        graficar+= crear_arbol(actual.getHijo_derecho(),aux);

        return graficar;
    }
}
