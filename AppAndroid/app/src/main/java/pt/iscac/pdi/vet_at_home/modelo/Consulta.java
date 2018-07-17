package pt.iscac.pdi.vet_at_home.modelo;

import java.util.Date;

public class Consulta {

    private String idCliente;
    private String idAnimal;
    private Date dataHora;
    private String idLocalidade;
    private String idVet;

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(String idAnimal) {
        this.idAnimal = idAnimal;
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

    public String getIdVet() {
        return idVet;
    }

    public void setIdVet(String idVet) {
        this.idVet = idVet;
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
