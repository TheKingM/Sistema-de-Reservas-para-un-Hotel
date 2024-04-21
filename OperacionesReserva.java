import java.util.List;
import java.util.Scanner;

public interface OperacionesReserva {
    void hacerReserva(List<Habitacion> habitaciones, Scanner scanner);
    void cancelarReserva(Scanner scanner);
    void verDetallesReserva();
}
