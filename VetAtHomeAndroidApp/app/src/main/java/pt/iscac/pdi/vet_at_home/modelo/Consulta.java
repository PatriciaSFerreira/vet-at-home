package pt.iscac.pdi.vet_at_home.modelo;

import java.util.Date;

public class Consulta {

    private String idCliente;
    private String animal;
    private Date dataHora;
    private String idLocalidade;
    private String veterinario;

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public String getIdLocalidade() {
        return idLocalidade;
    }

    public void setIdLocalidade(String idLocalidade) {
        this.idLocalidade = idLocalidade;
    }

    public String getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(String veterinario) {
        this.veterinario = veterinario;
    }

    @Override
    public String toString() {
        Object idAnimal = null;
        Object idVet = null;
        Object dataHora = null;

        return "Animal: " + idAnimal + " Veterinário: " +
                idVet + " Data: " + dataHora;
    }
}
