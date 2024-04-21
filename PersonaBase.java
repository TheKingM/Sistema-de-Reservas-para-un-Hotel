import java.util.List;
import java.util.Scanner;

public abstract class PersonaBase implements OperacionesReserva {
    private String nombre;
    private String email;
    private String contraseña;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    @Override
    public void cancelarReserva(Scanner scanner) {
        
    }

    @Override
    public void hacerReserva(List<Habitacion> habitaciones, Scanner scanner) {
        
    }

    @Override
    public void verDetallesReserva() {
        
    }
}