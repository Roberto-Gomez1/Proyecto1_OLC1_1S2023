package Analizadores;

import java.util.ArrayList;

public class Tabla {
    private ArrayList<Integer> numero = new ArrayList<>();
    private String lexema;
    private ArrayList<Integer> siguiente = new ArrayList<>();

    public Tabla(ArrayList<Integer> numero, String lexema, ArrayList<Integer> siguiente) {
        this.numero = numero;
        this.lexema = lexema;
        this.siguiente = siguiente;

    }

    public ArrayList<Integer> getNumero() {
        return numero;
    }

    public void setNumero(ArrayList<Integer> numero) {
        this.numero = numero;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public ArrayList<Integer> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(ArrayList<Integer> siguiente) {
        this.siguiente = siguiente;
    }

}
