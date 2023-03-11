package Analizadores;

public class Nodo_binario {
    private String dato;
    private Nodo_binario hijo_izquierdo;
    private Nodo_binario hijo_derecho;
    private int cabecera;
    private boolean hoja = false;
    public Nodo_binario(String dato) {
        this.dato = dato;
    }

    public Nodo_binario getHijo_izquierdo() {
        return hijo_izquierdo;
    }

    public int getCabecera() {
        return cabecera;
    }

    public void setCabecera(int cabecera) {
        this.cabecera = cabecera;
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

    public String getDato() {
        return dato;
    }
}
