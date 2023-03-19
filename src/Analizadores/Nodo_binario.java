package Analizadores;


import java.util.ArrayList;

public class Nodo_binario {
    private String dato;
    private Nodo_binario hijo_izquierdo;
    private Nodo_binario hijo_derecho;
    private int cabecera;
    private boolean hoja = false;
    private boolean anulable;
    private ArrayList<Integer> primeros = new ArrayList<>();
    private ArrayList<Integer> ultimos = new ArrayList<>();

    public Nodo_binario(String dato) {
        this.dato = dato;
    }
    public ArrayList<Integer> getPrimeros() {
        return primeros;
    }

    public ArrayList<Integer> getUltimos() {
        return ultimos;
    }

    public void setUltimos(ArrayList<Integer> ultimos) {
        this.ultimos = ultimos;
    }

    public void setPrimeros(ArrayList<Integer> primeros) {
        this.primeros = primeros;
    }
    public String getDato() {
        return dato;
    }
    public boolean isAnulable() {
        return anulable;
    }
    public void setAnulable(boolean anulable) {
        this.anulable = anulable;
    }




    public int getCabecera() {
        return cabecera;
    }

    public void setCabecera(int cabecera) {
        this.cabecera = cabecera;
    }


    public Nodo_binario getHijo_izquierdo() {
        return hijo_izquierdo;
    }
    public void setHijo_izquierdo(Nodo_binario hijo_izquierdo) {
        this.hijo_izquierdo = hijo_izquierdo;
    }
    public Nodo_binario getHijo_derecho() {
        return hijo_derecho;
    }

    public void setHijo_derecho(Nodo_binario hijo_derecho) {
        this.hijo_derecho = hijo_derecho;
    }

    public boolean isHoja() {
        return hoja;
    }

    public void setHoja(boolean hoja) {
        this.hoja = hoja;
    }


}
