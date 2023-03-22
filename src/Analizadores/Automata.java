package Analizadores;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class Automata {
    private Nodo_binario arbol_expresion;
    private ArrayList<Trans> transiciones = new ArrayList<>();
    private ArrayList<Tabla> estados = new ArrayList<>();
    private ArrayList<Tabla> aux_tabla = new ArrayList<>();
    private ArrayList<Tabla> aux_nombre = new ArrayList<>();

    private int conteo = 1;
    private int contador_arbol,contador_siguientes,contador_transiciones,contador_afd,contador_afnd,contador_html =1;
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
        String cadena= "digraph G {\n"+crear_arbol(this.arbol_expresion,conteo)+"}";
        generar(cadena);
        this.estados.add(new Tabla(this.arbol_expresion.getPrimeros(),"S0",this.arbol_expresion.getUltimos()));
        tabla_siguientes();
        contador=0;
        aa="";
        tabla_trans();
        contador=0;
        aa="";
        afd();
        ingresar_error();
    }
    public void tabla_siguientes (){
        //this.estados.add(new Tabla(this.aux_tabla.get(0).getNumero(),"S1",this.aux_tabla.get(0).getSiguiente()));
        //disyuncion de primeros
        for (int i=0;i<this.aux_tabla.size();i++){
            Tabla hola = this.aux_tabla.get(i);
            ArrayList<Integer> numero = hola.getNumero();

            if (numero.size() >1){
                this.aux_tabla.remove(i);
                for(Integer num: numero){
                    ArrayList<Integer> lista = new ArrayList<Integer>();
                    lista.add(num);
                    this.aux_tabla.add(i, new Tabla(lista,hola.getLexema(),hola.getSiguiente()));
                }
            }
        }
        //concatenacion de siugientes
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
        //ordenamiento de siguientes
        for (int i=0;i<this.aux_nombre.size();i++){
            ArrayList<Integer> numero = this.aux_nombre.get(i).getNumero();
            for (int j=0;j<this.aux_tabla.size();j++){
                if (numero.equals(this.aux_tabla.get(j).getNumero())){
                    Tabla hola = this.aux_tabla.get(j);
                    this.aux_tabla.remove(j);
                    Integer [] arreglo = hola.getSiguiente().toArray(new Integer[0]);
                    Arrays.sort(arreglo);
                    ArrayList<Integer> auxilio = new ArrayList<Integer>(Arrays.asList(arreglo));
                    this.aux_tabla.add(j, new Tabla(hola.getNumero(),this.aux_nombre.get(i).getLexema(),auxilio));
                }
            }
        }

        //ordenamiento
        for (int i =0;i<this.aux_tabla.size()-1;i++){
            for (int j =0;j<this.aux_tabla.size()-i-1;j++){
                Tabla hola = this.aux_tabla.get(j);
                ArrayList<Integer> numero = hola.getNumero();
                int mmm = numero.get(0);
                Tabla hola1 = this.aux_tabla.get(j+1);
                ArrayList<Integer> numero1 = hola1.getNumero();
                int mmm1 = numero1.get(0);
                if(mmm>mmm1){
                    this.aux_tabla.remove(j);
                    this.aux_tabla.add(j, hola1);
                    this.aux_tabla.remove(j+1);
                    this.aux_tabla.add(j+1,hola);
                }
            }
        }
        for (int i = 0; i < this.aux_tabla.size(); i++) {
            Tabla numero = this.aux_tabla.get(i);
            boolean duplicado = false;
            for (int j = i + 1; j < this.aux_tabla.size(); j++) {
                Tabla hola = this.aux_tabla.get(j);
                if (numero.getSiguiente().equals(hola.getSiguiente())) {
                    duplicado = true;
                    break;
                }
            }
            if (!duplicado) {
                String mm = "S" + contador;
                this.estados.add(new Tabla(numero.getNumero(), mm, numero.getSiguiente()));
                contador++;
            }
        }
        contador=1;



        ArrayList<Integer> le = new ArrayList<>();
        this.aux_tabla.add(new Tabla(this.aux_nombre.get(this.aux_nombre.size()-1).getNumero(),this.aux_nombre.get(this.aux_nombre.size()-1).getLexema(),le));

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
        }
        aa+="</TABLE>>];\n}";

        FileWriter fichero = null;
        try {
            File directory = new File("Imagenes\\SIGUIENTES_202000544");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            File file = new File("Imagenes\\SIGUIENTES_202000544\\TablaSiguientes" + contador_siguientes + ".dot");
            while (file.exists()) {
                contador_siguientes++;
                file = new File("Imagenes\\SIGUIENTES_202000544\\TablaSiguientes" + contador_siguientes + ".dot");
            }
            fichero = new FileWriter(file);
            PrintWriter pw = null;
            pw = new PrintWriter(fichero);
            pw.println(aa);
            pw.close();
            try {
                ProcessBuilder proceso;
                proceso = new ProcessBuilder("dot", "-Tjpg", "-o", "Imagenes\\SIGUIENTES_202000544\\TablaSiguientes"+contador_siguientes+".jpg", "Imagenes\\SIGUIENTES_202000544\\TablaSiguientes"+contador_siguientes+".dot");
                proceso.start();
                contador_siguientes++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
    public void tabla_trans() {

        for (int i = 0; i < this.estados.size(); i++) {
            Tabla hola = this.estados.get(i);
            ArrayList<Integer> numero = (i == 0) ? hola.getNumero() : hola.getSiguiente();
            if (numero.size() > 1) {
                for (Integer num : numero) {
                    ArrayList<Integer> lista = new ArrayList<Integer>();
                    lista.add(num);
                    this.transiciones.add(new Trans(lista, hola.getLexema(), "", lista, ""));
                }
            } else {
                ArrayList<Integer> lista = new ArrayList<Integer>();
                lista.add(numero.get(0));
                Trans nose = new Trans(lista, hola.getLexema(), "", lista, "");
                boolean addTransition = true;
                for (Trans t : this.transiciones) {
                    if (t.getInicial().equals(nose.getInicial()) && t.getSiguientes().equals(nose.getSiguientes())) {
                        addTransition = false;
                        break;
                    }
                }
                if (addTransition) {
                    this.transiciones.add(nose);
                }
            }
        }


        for (int i = 0; i < this.transiciones.size(); i++) {
            Trans numero = this.transiciones.get(i);
            for (int j = 0; j < this.aux_nombre.size(); j++) {
                Tabla hola = this.aux_nombre.get(j);
                for (int k = 0; k < this.aux_tabla.size(); k++) {
                    Tabla queso = this.aux_tabla.get(k);
                    if (numero.getInicial().equals(hola.getNumero()) && numero.getInicial().equals(queso.getNumero())) {
                        this.transiciones.remove(i);
                        this.transiciones.add(i, new Trans(numero.getInicial(), numero.getEstado_inicial(), hola.getLexema(), queso.getSiguiente(), numero.getEstado_final()));
                    }
                }
            }
        }
        for (int i = 0; i < this.transiciones.size(); i++) {
            Trans numero = this.transiciones.get(i);
            for (int j = 0; j < this.estados.size(); j++) {
                Tabla hola = this.estados.get(j);
                if (numero.getSiguientes().equals(hola.getSiguiente())) {
                    this.transiciones.remove(i);
                    this.transiciones.add(i, new Trans(numero.getInicial(), numero.getEstado_inicial(), numero.getLexema(), numero.getSiguientes(), hola.getLexema()));
                }
            }
        }
        
        aa += "digraph G{\n" +
                "graph [ratio=fill];\n" +
                "node [label=\"\\N\", fontsize=15, shape=plaintext];\n" +
                "graph [bb=\"0,0,352,154\"];\n" +
                "arset [label=<\n" +
                "<TABLE ALIGN=\"LEFT\">\n" +
                "<TR><TD bgcolor=\"lemonchiffon4\">Estado</TD>\n";
        for (int i = 0; i < this.aux_tabla.size(); i++) {
            aa += "<TD bgcolor =\"lemonchiffon4\"> " + this.aux_tabla.get(i).getLexema() + "</TD>\n";
        }
        aa += "</TR>\n";
        int total = this.estados.size()+this.aux_nombre.size();
        int temporal=0;
        for (int i = 0; i < this.estados.size(); i++) {
        int temp= temporal;
        if(i==0) {
            aa += "<TR><TD bgcolor=\"lemonchiffon4\">" + this.estados.get(i).getLexema() + " " + this.estados.get(i).getNumero() + "</TD>\n";
        }else{
            aa += "<TR><TD bgcolor=\"lemonchiffon4\">" + this.estados.get(i).getLexema() + " " + this.estados.get(i).getSiguiente() + "</TD>\n";
        }
            for (int j = 0; j < this.aux_nombre.size(); j++) {
                if(this.estados.get(i).getLexema().equals(this.transiciones.get(temp).getEstado_inicial()) && this.transiciones.get(temp).getLexema().equals(this.aux_nombre.get(j).getLexema())){
                    aa +="<TD>"+this.transiciones.get(temp).getEstado_final()+"</TD>\n";
                    if(temp <=total-2) {
                        temp++;
                    }
                    temporal=temp;
                } else if (!this.estados.get(i).getLexema().equals(this.transiciones.get(temp).getEstado_inicial()) || !this.transiciones.get(temp).getLexema().equals(this.aux_nombre.get(j).getLexema())) {
                    aa +="<TD>--</TD>\n";

                    }
                if(temp%total==0 && temp >1) {
                    temp += 1;
                }
                }
            aa += "</TR>\n";
        }
        aa += "</TABLE>>];\n}";
        FileWriter fichero = null;
        try {
            File directory = new File("Imagenes\\TRANSICIONES_202000544");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            File file = new File("Imagenes\\TRANSICIONES_202000544\\TablaTransiciones" + contador_transiciones + ".dot");
            while (file.exists()) {
                contador_transiciones++;
                file = new File("Imagenes\\TRANSICIONES_202000544\\TablaTransiciones" + contador_transiciones + ".dot");
            }
            fichero = new FileWriter(file);
            PrintWriter pw = null;
            pw = new PrintWriter(fichero);
            pw.println(aa);
            pw.close();
            try {
                ProcessBuilder proceso;
                proceso = new ProcessBuilder("dot", "-Tjpg", "-o", "Imagenes\\TRANSICIONES_202000544\\TablaTransiciones"+contador_transiciones+".jpg", "Imagenes\\TRANSICIONES_202000544\\TablaTransiciones"+contador_transiciones+".dot");
                proceso.start();
                contador_transiciones++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
    public void afd() {
        aa += "digraph G {\nrankdir=LR;\n";
        for (int i = 0; i < this.estados.size(); i++) {
            if(i==this.estados.size()-1) {
                aa+=this.estados.get(i).getLexema()+"[shape=doublecircle];\n";
            }else{
                aa+=this.estados.get(i).getLexema()+"[shape=circle];\n";
            }
        }
        for(int i=0;i<this.transiciones.size();i++){
            if(!this.transiciones.get(i).getLexema().equals("#")){
                String label = this.transiciones.get(i).getLexema().replaceAll("\"", "\\\\\"");
                aa+=this.transiciones.get(i).getEstado_inicial()+" -> "+this.transiciones.get(i).getEstado_final()+" [label=\""+label+"\"];\n";
            }
        }
        aa+="}";
        FileWriter fichero = null;
        try {
            File directory = new File("Imagenes\\AFD_202000544");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            File file = new File("Imagenes\\AFD_202000544\\AFD" + contador_afd + ".dot");
            while (file.exists()) {
                contador_afd++;
                file = new File("Imagenes\\AFD_202000544\\AFD" + contador_afd + ".dot");
            }
            fichero = new FileWriter(file);
            PrintWriter pw = null;
            pw = new PrintWriter(fichero);
            pw.println(aa);
            pw.close();
            try {
                ProcessBuilder proceso;
                proceso = new ProcessBuilder("dot", "-Tjpg", "-o", "Imagenes\\AFD_202000544\\AFD" + contador_afd + ".jpg", "Imagenes\\AFD_202000544\\AFD" + contador_afd + ".dot");
                proceso.start();
                contador_afd++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
    public void afnd(){

    }
    public void ingresar_error(){
        if(Lexico.errores.isEmpty()) {
            String cadena = "";
            cadena += "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head><title>Reporte de Errores</title><style>\n      " +
                    "table {\n" +
                    "border-collapse: collapse;\n" +
                    "width: 100%;\n" +
                    "}\n" +
                    "th, td {\n" +
                    "text-align: left;\n" +
                    "padding: 8px;\n" +
                    "border: 1px solid black;\n" +
                    "}\n" +
                    "th {\n" +
                    "background-color: #dddddd;\n" +
                    "}\n" +
                    "</style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<table>\n" +
                    "<caption>Reporte de Errores Léxicos</caption>\n" +
                    "<thead>\n" +
                    "<tr>\n" +
                    "<th>Numero</th>\n" +
                    "<th>Tipo de Error</th>\n" +
                    "<th>Descripcion</th>\n" +
                    "<th>Fila</th>\n" +
                    "<th>Columna</th>\n" +
                    "</tr>\n" +
                    "</thead>\n" +
                    "<tbody>\n";
            int con_aux = 1;
            for (int i = 0; i < Lexico.errores.size(); i++) {
                cadena += "<tr><td>" + con_aux + "</td>\n";
                cadena += "<td>" + Lexico.errores.get(i).getTipo() + "</td>\n";
                cadena += "<td>" + Lexico.errores.get(i).getDescripcion() + "</td>\n";
                cadena += "<td>" + Lexico.errores.get(i).getFila() + "</td>\n";
                cadena += "<td>" + Lexico.errores.get(i).getColumna() + "</td></tr>\n";
                con_aux++;
            }
            cadena += "</tbody>\n" +
                    "</table>\n" +
                    "</body>\n" +
                    "</html>";
            try {
                // Crear archivo HTML
                File directory = new File("Imagenes\\ERRORES_202000544");
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                File file = new File("Imagenes\\ERRORES_202000544\\Reporte" + contador_html + ".html");
                while (file.exists()) {
                    contador_html++;
                    file = new File("Imagenes\\ERRORES_202000544\\Reporte" + contador_html + ".html");
                }
                FileWriter fichero = new FileWriter(file);
                PrintWriter pw = new PrintWriter(fichero);
                pw.println(cadena);
                pw.close();

                // Abrir archivo HTML en el navegador por defecto
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().browse(file.toURI());
                }

                contador_afd++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public void generar(String cadena){
        FileWriter fichero = null;
        try {
            File directory = new File("Imagenes\\ARBOLES_202000544");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            File file = new File("Imagenes\\ARBOLES_202000544\\Arboles" + contador_arbol + ".dot");
            while (file.exists()) {
                contador_arbol++;
                file = new File("Imagenes\\ARBOLES_202000544\\Arboles" + contador_arbol + ".dot");
            }
            fichero = new FileWriter(file);
            PrintWriter pw = null;
            pw = new PrintWriter(fichero);
            pw.println(cadena);
            pw.close();
            try {
                ProcessBuilder proceso;
                proceso = new ProcessBuilder("dot", "-Tjpg", "-o", "Imagenes\\ARBOLES_202000544\\Arboles"+contador_arbol+".jpg", "Imagenes\\ARBOLES_202000544\\Arboles"+contador_arbol+".dot");
                proceso.start();
                contador_arbol++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
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
