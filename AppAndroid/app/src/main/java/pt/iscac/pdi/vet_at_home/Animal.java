package pt.iscac.pdi.vet_at_home;


public class Animal {
    private int id;
    private String nome;
    private int id_cliente;
    private String raca;
    private int idade;

    public Animal(int id, String nome, int id_cliente, String raca, int idade) {
        this.id = id;
        this.nome = nome;
        this.id_cliente = id_cliente;
        this.raca = raca;
        this.idade = idade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId_cliente() { return id_cliente; }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }



}