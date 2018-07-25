package pt.iscac.pdi.vet_at_home;

public class Cliente {
    private int id;
    private String username;
    private String email;
    private String nif;
    private String morada;
    private String urlImagem;

    public Cliente(int id, String username, String email, String nif, String morada, String urlImagem) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.nif = nif;
        this.morada = morada;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }


}
