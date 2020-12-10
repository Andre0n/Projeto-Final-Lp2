package App;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;

public class App extends JFrame {
    private JTextArea textArea;
    BufferedReader input = null;
    public ArrayList<Candidato> candidatos = new ArrayList<>();

    /*CONSTRUTOR*/
    public App (){
        initComponents();
    }

    /*METODO MAIN*/
    public static void main(String[] args) {
        new App().setVisible(true);
    }

    /*INICIO METODO INICIA COMPONENTES*/
    private void initComponents() {
        //Instancia os objetos.
        JButton btnEscolheArq = new JButton();
        JButton btnListar = new JButton();
        JButton btnGeraLista = new JButton();
        JLabel tituloApp = new JLabel();
        JPanel conteudo = new JPanel();
        JScrollPane scrollGeral = new JScrollPane();
        textArea = new JTextArea();
        //JLabel creditos = new JLabel();

        //Ajusta a janela principal.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Projeto Final");
        setSize(640, 480);
        setLocationRelativeTo(null);
        setResizable(false);

        //Configura o Panel.
        conteudo.setBackground(new Color(18, 18, 18));
        conteudo.setSize(640,480);
        conteudo.setLayout(null);

        //Configura os botoes.
        btnEscolheArq.setBackground(new Color(33, 150, 243));
        btnEscolheArq.setFont(new Font("Noto Sans", Font.BOLD, 13));
        btnEscolheArq.setForeground(new Color(255, 255, 255));
        btnEscolheArq.setText("ESCOLHER ARQUIVO");
        btnEscolheArq.setBorder(null);
        btnEscolheArq.setBorderPainted(false);
        btnEscolheArq.setFocusPainted(false);
        btnEscolheArq.setFocusable(false);
        btnEscolheArq.setBounds(30, 180, 150, 40);
        btnEscolheArq.addActionListener(evt -> btnEscolheActionPerformed());

        // Botao Lista Melhores
        btnListar.setBackground(new Color(33, 150, 243));
        btnListar.setFont(new Font("Noto Sans", Font.BOLD, 13));
        btnListar.setForeground(new Color(255, 255, 255));
        btnListar.setText("LISTAR MELHORES");
        btnListar.setBorder(null);
        btnListar.setBorderPainted(false);
        btnListar.setFocusPainted(false);
        btnListar.setFocusable(false);
        btnListar.setBounds(30, 240, 150, 40);
        btnListar.addActionListener(evt -> btnlistaActionPerformed());

        //Botao Gerar Lista
        btnGeraLista.setBackground(new Color(33, 150, 243));
        btnGeraLista.setFont(new Font("Noto Sans", Font.BOLD, 13));
        btnGeraLista.setForeground(new Color(255, 255, 255));
        btnGeraLista.setText("GERAR ARQUIVO");
        btnGeraLista.setBorder(null);
        btnGeraLista.setBorderPainted(false);
        btnGeraLista.setFocusPainted(false);
        btnGeraLista.setFocusable(false);
        btnGeraLista.setBounds(30, 300, 150, 40);
        btnGeraLista.addActionListener(evt -> btnGeraActionPerformed());

        //Configura o titulo
        tituloApp.setFont(new Font("Arial", Font.BOLD, 22));
        tituloApp.setForeground(new Color(255, 255, 255));
        tituloApp.setText("PROJETO FINAL");
        tituloApp.setBounds(20, 100, 183, 33);

        //Adiciona o campo de texto.
        textArea.setBackground(new Color(33, 33, 33));
        textArea.setColumns(20);
        textArea.setFont(new Font("Arial", Font.PLAIN, 20));
        textArea.setForeground(new Color(255, 255, 255));
        textArea.setRows(5);
        textArea.setBorder(null);
        textArea.setFocusable(false);
        textArea.setRequestFocusEnabled(false);
        scrollGeral.setViewportView(textArea);
        scrollGeral.setBorder(null);
        scrollGeral.setBounds(210, 0, 415, 442);
        scrollGeral.getAccessibleContext().setAccessibleParent(scrollGeral);

        //Adiciona os componentes na janela.
        conteudo.add(btnEscolheArq);
        conteudo.add(btnGeraLista);
        conteudo.add(btnListar);
        conteudo.add(tituloApp);
        conteudo.add(scrollGeral);
        add(conteudo);

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }catch (ClassNotFoundException | UnsupportedLookAndFeelException |
                InstantiationException | IllegalAccessException e) {
            System.err.println("Erro ao definir tema do Windows!");
        }
    } /*FIM METODO INICIA COMPONENTES*/

    private void btnEscolheActionPerformed() {
        try{
            //Abre a caixa de selecao de Arquivo.
            JFileChooser seletor = new JFileChooser();
            FileFilter filtro = new FileNameExtensionFilter("Arquivos de texto", "txt");
            seletor.setFileFilter(filtro);
            seletor.addChoosableFileFilter(filtro);
            seletor.setDialogTitle("Selecione um arquivo...");
            seletor.showOpenDialog(new JFrame(""));
            seletor.setVisible(true);
            String endereco = seletor.getSelectedFile().getAbsolutePath();
            input = new BufferedReader(new InputStreamReader(new FileInputStream(endereco), StandardCharsets.UTF_8));
            String conteudoLinha = input.readLine();
            while (conteudoLinha != null) {
                try {
                    String[] atributos = conteudoLinha.split("[;\\n]");
                    Integer inscricao = Integer.parseInt(atributos[0].replaceAll("\uFEFF", ""));
                    String nome = atributos[1];
                    Integer idade = Integer.parseInt(atributos[2]);
                    Integer nota = Integer.parseInt(atributos[3].replaceAll("[\n\r]", ""));
                    Candidato candidato = new Candidato(inscricao, nome, idade, nota);
                    candidatos.add(candidato);
                    conteudoLinha = input.readLine();
                } catch (Exception e) {
                    System.err.println("Erro ao cadastrar candidato!" + e);
                    mostararMensagem("Erro ao ler o arquivo!", 0);
                    input = null;
                    break;
                }
            }
            assert input != null;
            input.close();
            Collections.sort(candidatos); // Ordena seguindo as regras definidas.
        } catch ( IOException | NullPointerException e) {
            System.err.println("Ouve um erro ao ler o arquivo! " + e);
            input = null;
        }
    }

    private void btnlistaActionPerformed() {
        if (input != null) {
            try{
                textArea.setText("");
                textArea.append("Insc.\tNome\tNota\n");
                for (int i = 0; i<100; i++){
                    textArea.append(candidatos.get(i).getInscricao() + " ");
                    textArea.append(candidatos.get(i).getNome() + "\t" );
                    textArea.append(candidatos.get(i).getNota() + "\n");
                }
                textArea.append("------------------------------------------\n");
            }catch (Exception e){
                mostararMensagem("Houve um erro ao exibir o arquivo!", 0);
            }
        } else mostararMensagem("Nenhum arquivo foi selecionado!", 2);
    }

    private void btnGeraActionPerformed() {
        if (input==null){
            mostararMensagem("Nenhum arquivo foi selecionado!", 2);
        }else{
            try{
                FileWriter arq = new FileWriter("aprovados.txt");
                PrintWriter gravarArq = new PrintWriter(arq);
                for (int i = 0; i<100; i++){
                    gravarArq.print((i+1)+";"+candidatos.get(i).getInscricao()+";"+candidatos.get(i).getNome()+";"+
                            candidatos.get(i).getIdade()+";"+candidatos.get(i).getNota()+"\n");
                }
                mostararMensagem("Arquivo gerado com sucesso!", 1);
                arq.close();
            }catch (IOException e) {
                mostararMensagem("Não foi possível gerar o Arquivo!", 0);
                System.out.println("Não foi possível escrever no Arquivo" + e);
            }
        }
    }

    private void mostararMensagem(String mensagem, int messageType){
        JOptionPane.showMessageDialog(null,mensagem,
                "Informe", messageType);
    }
}
