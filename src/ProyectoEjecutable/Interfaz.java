package ProyectoEjecutable;
import Analizadores.Lexico;
import Analizadores.parser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

public class Interfaz extends JFrame{
    public JPanel panel;
    public  JTextArea area1,area2;
    public JLabel imagen;
    public File archivo,generar_imagen;
    public JMenuItem abrir, guardar, guardar_c,abrir_imagen,cerrar;

    public Interfaz(){

        setSize(885,700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("ExRegan_Usac");
        setResizable(false);
        setLayout(null);
        Componentes();
        setLocationRelativeTo(null);

    }
    public void Componentes(){
        Panel();
        AreaTexto();
        Botones();
    }

    private void Panel(){
        panel = new JPanel();
        this.getContentPane().add(panel);
        this.getContentPane().setBackground(new Color(10, 20, 40));
        panel.setBackground(new Color(20, 120, 160));
        panel.setBounds(10,10,850,620);
        panel.setLayout(null);

        JMenuBar barra= new JMenuBar();
        setJMenuBar(barra);
        JMenu archivo_b = new JMenu("Archivo");
        archivo_b.setMnemonic('A');
        barra.add(archivo_b);
        archivo_b.add(abrir= new JMenuItem("Abrir"));
        abrir.setMnemonic('A');
        archivo_b.add(guardar= new JMenuItem("Guardar"));
        guardar.setMnemonic('G');
        archivo_b.add(guardar_c= new JMenuItem("Guardar Como"));
        guardar_c.setMnemonic('G');

        JMenu ver = new JMenu("Ver imagenes");
        ver.setMnemonic('V');
        barra.add(ver);
        ver.add(abrir_imagen= new JMenuItem("Abrir Imagen"));
        abrir_imagen.setMnemonic('A');

        JMenu salir = new JMenu("Salir");
        salir.setMnemonic('S');
        barra.add(salir);
        salir.add(cerrar= new JMenuItem("Cerrar"));
        cerrar.setMnemonic('C');

        abrir.addActionListener(abrir_accion);
        guardar.addActionListener(guardar_accion);
        guardar_c.addActionListener(guardarc_accion);
        abrir_imagen.addActionListener(crear_imagen);
        cerrar.addActionListener(cerrarAccion);
    }
    ActionListener abrir_accion = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            area1.setText("");
            area2.setText("");
            JFileChooser filec = new JFileChooser();
            filec.showOpenDialog(null);
            archivo = filec.getSelectedFile();
            String texto = "";
            String Linea = "";
            try {
                FileReader Fr = new FileReader(archivo);
                BufferedReader Br = new BufferedReader(Fr);
                while ((((Linea = Br.readLine()) != null))){
                    texto +=Linea+"\n";
                }
                JOptionPane.showMessageDialog(null,"Archivo cargado exitosamente");
            }catch (Exception evnt ){

            }
            area1.setText(texto);
            area2.setText("Se ha cargado el archivo para analizar\n"+area2.getText());
        }
    };
    ActionListener guardar_accion = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String Contenido = area1.getText();
            if (archivo != null){
                try {
                    FileWriter Fw = new FileWriter(archivo);
                    Fw.write(Contenido);
                    Fw.close();
                    area2.setText("Se ha hecho el cambio correctamente\n"+area2.getText());
                } catch (IOException ex) {
                }
            }else{
                area2.setText("No existe una ruta cargada, por favor utilice Guardar Como\n"+area2.getText());
            }
        }
    };
    ActionListener guardarc_accion = new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String contenido = area1.getText();
        JFileChooser fileChooser = new JFileChooser();
        int seleccion = fileChooser.showSaveDialog(null);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            archivo = fileChooser.getSelectedFile();
            try {
                FileWriter fw = new FileWriter(archivo);
                fw.write(contenido);
                fw.close();
                area2.setText("Se ha creado el archivo exitosamente\n"+area2.getText());
            } catch (IOException ex) {
                ex.printStackTrace();
                area2.setText("Ha ocurrido un error\n"+area2.getText());
            }
        }

    }

    };
    ActionListener cerrarAccion = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    };
    ActionListener generar_accion= new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            File documento = new File(String.valueOf(archivo));
            Scanner sca = null;
            try {
                sca = new Scanner(documento);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            String aux = "";
            while (sca.hasNextLine()){
                aux += sca.nextLine()+"\n";
            }
            interpretar(aux);
            area2.setText("Analisis finalizado\n"+area2.getText());
        }
    };
    ActionListener crear_imagen = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser filec = new JFileChooser();
            filec.showOpenDialog(null);
            generar_imagen = filec.getSelectedFile();
            ImageIcon icono = new ImageIcon(generar_imagen.getAbsolutePath());
            imagen.setIcon(icono);
        }
    };
    private void AreaTexto(){
        area1 = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(area1);
        scrollPane.setBounds(20, 50, 400, 300);
        panel.add(scrollPane);

        imagen = new JLabel();
        JScrollPane scrollPane1 = new JScrollPane(imagen);
        scrollPane1.setBounds(430, 50, 400, 300);
        scrollPane1.setBackground(Color.white);
        panel.add(scrollPane1);

        JLabel a = new JLabel();
        a.setText("Entrada");
        a.setBounds(20,20,80,30);
        panel.add(a);
        JLabel aa = new JLabel();
        aa.setText("Consola");
        aa.setBounds(20,420,80,30);
        panel.add(aa);

        area2 = new JTextArea();
        area2.setEditable(false);
        JScrollPane scrollPane2 = new JScrollPane(area2);
        scrollPane2.setBounds(20,450,800,150);
        panel.add(scrollPane2);

    }

    private static void interpretar(String aux){
        try{
            Lexico sca = new Lexico(new java.io.StringReader(aux));
            parser parser = new parser(sca);
            parser.parse();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void Botones(){
        JButton Boton1 = new JButton("Generar Automata");
        JButton Boton2 = new JButton("Analizar Entrada");

        Boton1.setBounds(150,380,200,30);
        Boton2.setBounds(450,380,200,30);

        Boton1.addActionListener(generar_accion);
        panel.add(Boton1);
        panel.add(Boton2);

    }

}