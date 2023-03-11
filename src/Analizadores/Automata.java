package Analizadores;

public class Automata {
    private Nodo_binario arbol_expresion;
    private int conteo = 1;
    public Automata(Nodo_binario arbol_expresion) {
        Nodo_binario primero = new Nodo_binario(".");
        Nodo_binario hash = new Nodo_binario("#");
        hash.setHoja(true);
        primero.setHijo_derecho(hash);
        primero.setHijo_izquierdo(arbol_expresion);
        this.arbol_expresion = primero;
        asignacion(this.arbol_expresion);
        conteo=0;
        System.out.println(crear_arbol(this.arbol_expresion,conteo));
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
                    + "<TD colspan=\"3\"></TD>\n"
                    + "</TR>\n"
                    + "<TR>\n"
                    + "<TD></TD>\n"
                    + "<TD >"+actual.getDato()+"</TD>\n"
                    + "<TD></TD>\n"
                    + "</TR>\n"
                    + "<TR>\n"
                    + "<TD colspan=\"3\">"+actual.getCabecera()+"</TD>\n"
                    + "</TR>\n"
                    + "</TABLE>>];";
        }else{
            graficar+= "N_"+aux+"[shape = none label=<\n"
                    + "<TABLE border=\"1\" cellspacing=\"2\" cellpadding=\"10\" > \n"
                    + "<TR>\n"
                    + "<TD colspan=\"3\"></TD>\n"
                    + "</TR>\n"
                    + "<TR>\n"
                    + "<TD ></TD>\n"
                    + "<TD >" + actual.getDato() +"</TD>\n"
                    + "<TD ></TD>\n"
                    + "</TR>\n"
                    + "</TABLE>>];";
        }

        if (padre != 0 ){
            graficar+="N_"+padre+ " -> N_" + aux +";\n";
        }
        graficar+= crear_arbol(actual.getHijo_izquierdo(), aux);
        graficar+= crear_arbol(actual.getHijo_derecho(),aux);

        return graficar;
    }
}
