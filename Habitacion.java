import java.util.List;

class Habitacion {
    private String tipo;
    private double precioPorNoche;
    private int numMaxHuespedes;
    private List<String> comodidades;

    public Habitacion(){}
    public Habitacion(String tipo, double precioPorNoche, int numMaxHuespedes, List<String> comodidades) {
        this.tipo = tipo;
        this.precioPorNoche = precioPorNoche;
        this.numMaxHuespedes = numMaxHuespedes;
        this.comodidades = comodidades;
    }
    public Habitacion(String tipo2, double precioPorNoche2, int numMaxHuespedes2, String[] comodidades2) {
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public double getPrecioPorNoche() {
        return precioPorNoche;
    }
    public void setPrecioPorNoche(double precioPorNoche) {
        this.precioPorNoche = precioPorNoche;
    }
    public int getNumMaxHuespedes() {
        return numMaxHuespedes;
    }
    public void setNumMaxHuespedes(int numMaxHuespedes) {
        this.numMaxHuespedes = numMaxHuespedes;
    }
    public List<String> getComodidades() {
        return comodidades;
    }
    public void setComodidades(List<String> comodidades) {
        this.comodidades = comodidades;
    }

}