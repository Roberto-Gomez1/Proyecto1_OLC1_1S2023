package Analizadores;

import java.util.ArrayList;

public class Trans {
    private ArrayList<Integer> inicial = new ArrayList<>();
    private String estado_inicial;
    private String lexema;
    private ArrayList<Integer> siguientes = new ArrayList<>();
    private String estado_final;

    public Trans(ArrayList<Integer> inicial, String estado_inicial, String lexema, ArrayList<Integer> siguientes, String estado_final) {
        this.inicial = inicial;
        this.estado_inicial = estado_inicial;
        this.lexema = lexema;
        this.siguientes = siguientes;
        this.estado_final = estado_final;
    }

    public ArrayList<Integer> getInicial() {
        return inicial;
    }

    public void setInicial(ArrayList<Integer> inicial) {
        this.inicial = inicial;
    }

    public String getEstado_inicial() {
        return estado_inicial;
    }

    public void setEstado_inicial(String estado_inicial) {
        this.estado_inicial = estado_inicial;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public ArrayList<Integer> getSiguientes() {
        return siguientes;
    }

    public void setSiguientes(ArrayList<Integer> siguientes) {
        this.siguientes = siguientes;
    }

    public String getEstado_final() {
        return estado_final;
    }

    public void setEstado_final(String estado_final) {
        this.estado_final = estado_final;
    }
}
