package ProyectoEjecutable;


import javax.swing.*;

public class Main {
    public static void main(String[] args) throws Exception{
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Interfaz principal= new Interfaz();
                principal.setVisible(true);
            }
        });


    }

}