import java.io.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Usuario extends PersonaBase {
    private List<Reserva> reservas;
    private static final String USUARIOS_FILE = "credenciales/usuarios.txt";
    private static final String HABITACIONES_FILE = "credenciales/habitaciones.txt";
    private static final String RESERVAS_FILE = "credenciales/reservas.txt";


    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    public void Registrarse() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese su nombre de usuario:");
        String nuevoUsuario = scanner.nextLine();
        System.out.println("Ingrese su correo electrónico:");
        String correo = scanner.nextLine();
        System.out.println("Ingrese su contraseña:");
        String contraseña = scanner.nextLine();

        try (PrintWriter writer = new PrintWriter(new FileWriter(USUARIOS_FILE, true))) {
            writer.println(nuevoUsuario + "," + correo + "," + contraseña);
            System.out.println("Registro como usuario exitoso.");
        } catch (IOException e) {
            System.out.println("Error al registrar usuario: " + e.getMessage());
        }
    }

    public void IniciarSesion() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese su nombre de usuario:");
        String usuario = scanner.nextLine();
        System.out.println("Ingrese su correo electrónico:");
        String correo = scanner.nextLine();
        System.out.println("Ingrese su contraseña:");
        String contraseña = scanner.nextLine();

        try (BufferedReader br = new BufferedReader(new FileReader(USUARIOS_FILE))) {
            String linea;
            boolean encontrado = false;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes[0].equals(usuario) && partes[1].equals(correo) && partes[2].equals(contraseña)) {
                    encontrado = true;
                    break;
                }
            }
            if (encontrado) {
                System.out.println("Inicio de sesión como usuario exitoso.");
            } else {
                System.out.println("Usuario no encontrado o credenciales incorrectas. Por favor, regístrese primero.");
            }
        } catch (IOException e) {
            System.out.println("Error al iniciar sesión como usuario: " + e.getMessage());
        }
    }

    public void BuscarHabitaciones() {
        List<Habitacion> habitaciones = cargarHabitaciones();

        System.out.println("Habitaciones disponibles:");
        for (Habitacion habitacion : habitaciones) {
            System.out.println(habitacion);
        }
    }

    private List<Habitacion> cargarHabitaciones() {
        List<Habitacion> habitaciones = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(HABITACIONES_FILE))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                String tipo = partes[0];
                double precioPorNoche = Double.parseDouble(partes[1]);
                int numMaxHuespedes = Integer.parseInt(partes[2]);
                String[] comodidades = partes[3].split(",");
                Habitacion habitacion = new Habitacion(tipo, precioPorNoche, numMaxHuespedes, comodidades);
                habitaciones.add(habitacion);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar las habitaciones: " + e.getMessage());
        }
        return habitaciones;
    }


    @Override
    public void hacerReserva (List<Habitacion> habitaciones, Scanner scanner) {
        System.out.println("Ingrese el tipo de habitación que desea reservar:");
        String tipoHabitacion = scanner.nextLine();
        System.out.println("Ingrese la fecha de inicio de la reserva (YYYY-MM-DD):");
        String fechaInicioStr = scanner.nextLine();
        System.out.println("Ingrese la fecha de fin de la reserva (YYYY-MM-DD):");
        String fechaFinStr = scanner.nextLine();

        Date fechaInicio = Date.valueOf(fechaInicioStr);
        Date fechaFin = Date.valueOf(fechaFinStr);

        Habitacion habitacionSeleccionada = null;
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getTipo().equalsIgnoreCase(tipoHabitacion)) {
                habitacionSeleccionada = habitacion;
                break;
            }
        }
        if (habitacionSeleccionada != null) {
            Reserva reserva = new Reserva(this, habitacionSeleccionada, fechaInicio, fechaFin);
            reservas.add(reserva);
            guardarReserva(reserva);
            System.out.println("Reserva realizada con éxito.");
        } else {
            System.out.println("Lo sentimos, no se encontró una habitación disponible del tipo especificado.");
        }
    }

    private void guardarReserva(Reserva reserva) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(RESERVAS_FILE, true))) {
            writer.println(reserva.getUsuario().getNombre() + "," + reserva.getHabitacion().getTipo() + "," + reserva.getFechaInicio() + "," + reserva.getFechaFin());
        } catch (IOException e) {
            System.out.println("Error al guardar la reserva: " + e.getMessage());
        }
    }

    @Override
    public void cancelarReserva(Scanner scanner) {
        System.out.println("Ingrese la fecha de la reserva que desea cancelar (YYYY-MM-DD):");
        String fechaReservaStr = scanner.nextLine();

        Date fechaReserva = Date.valueOf(fechaReservaStr);

        boolean reservaEncontrada = false;
        for (Reserva reserva : reservas) {
            if (reserva.getFechaInicio().equals(fechaReserva)) {
                reservas.remove(reserva);
                reservaEncontrada = true;
                break;
            }
        }

        if (reservaEncontrada) {
            actualizarArchivoReservas();
            System.out.println("Reserva cancelada con éxito.");
        } else {
            System.out.println("No se encontró ninguna reserva para la fecha ingresada.");
        }
    }

    private void actualizarArchivoReservas() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(RESERVAS_FILE))) {
            for (Reserva reserva : reservas) {
                writer.println(reserva.getUsuario().getNombre() + "," + reserva.getHabitacion().getTipo() + "," + reserva.getFechaInicio() + "," + reserva.getFechaFin());
            }
        } catch (IOException e) {
            System.out.println("Error al actualizar el archivo de reservas: " + e.getMessage());
        }
    }

    @Override
    public void verDetallesReserva() {
        System.out.println("Reservas del usuario:");
        for (Reserva reserva : reservas) {
            System.out.println("Habitación: " + reserva.getHabitacion().getTipo() + 
                               ", Fecha de inicio: " + reserva.getFechaInicio() + 
                               ", Fecha de fin: " + reserva.getFechaFin());
        }
    }

}
