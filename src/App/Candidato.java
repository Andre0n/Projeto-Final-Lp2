package App;
public class Candidato implements Comparable<Candidato> {
    private Integer inscricao;
    private String nome;
    private Integer idade;
    private Integer nota;

    //Construtores.
    public Candidato (){}

    public Candidato ( Integer inscricao, String nome, Integer idade, Integer nota) {
        this.inscricao = inscricao;
        this.nome = nome;
        this.idade = idade;
        this.nota = nota;
    }
    //Metdodos de Acesso.
    public Integer getInscricao() {
        return inscricao;
    }

    public String getNome() {
        return nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public Integer getNota() {
        return nota;
    }

    // Outros metodos (Ordenacao).
    @Override
    public int compareTo(Candidato candidato) { //Compara candidatos.
        if (!this.nota.equals(candidato.getNota()))
            return this.nota > candidato.getNota() ? -1 : 1;
        else
            return this.getIdade() > candidato.getIdade() ? -1 : 1;
    }
}