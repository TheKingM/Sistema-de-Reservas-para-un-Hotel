import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Hotel {
    private static final String USUARIOS_FILE = "credenciales/usuarios.txt";
    private static final String ADMINISTRADORES_FILE = "credenciales/administradores.txt";
    private static final String HABITACIONES_FILE = "credencial/habitaciones.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;
        while (!salir) {
            System.out.println("¡Bienvenido al sistema de reservas del hotel!");
            System.out.println("Por favor, seleccione una opción:");
            System.out.println("1. Iniciar Sesión como Usuario");
            System.out.println("2. Iniciar Sesión como Administrador");
            System.out.println("3. Registrarse como Usuario");
            System.out.println("4. Registrarse como Administrador");
            System.out.println("5. Salir");

            int opcion = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcion) {
                case 1:
                Usuario usuario = new Usuario();
                usuario.IniciarSesion();
                menuUsuario(usuario);
                break;
                case 2:
                    Administrador administrador = new Administrador();
                    administrador.IniciarSesion();
                    menuAdministrador(administrador);
                    break;
                case 3:
                registrarseUsuario();
                break;
                case 4:
                registrarseAdmin();
                    break;
                case 5:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
            }
        }

        System.out.println("¡Gracias por usar nuestro sistema de reservas del hotel!");
        scanner.close();
    }

    private static void registrarseAdmin() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese su nombre de administrador:");
        String nuevoAdmin = scanner.nextLine();
        System.out.println("Ingrese su correo electrónico:");
        String correo = scanner.nextLine();
        System.out.println("Ingrese su contraseña:");
        String contraseña = scanner.nextLine();
    
        try (PrintWriter writer = new PrintWriter(new FileWriter(ADMINISTRADORES_FILE, true))) {
            writer.println(nuevoAdmin + "," + correo + "," + contraseña);
            System.out.println("Registro como administrador exitoso.");
        } catch (IOException e) {
            System.out.println("Error al registrar administrador: " + e.getMessage());
        }
    }

    private static void registrarseUsuario() {
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

    private static void menuUsuario(Usuario usuario) {
        List<Habitacion> habitaciones = cargarHabitacionesDesdeArchivo();

        Scanner scanner = new Scanner(System.in);
        boolean salir = false;
        while (!salir) {
            System.out.println("\nMenú de Usuario:");
            System.out.println("1. Buscar habitaciones disponibles por fechas");
            System.out.println("2. Reservar una habitación");
            System.out.println("3. Cancelar sus propias reservas");
            System.out.println("4. Ver detalles de sus reservas pasadas y activas");
            System.out.println("5. Cerrar sesión");
            System.out.println("Por favor, seleccione una opción:");

            int opcion = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcion) {
                case 1:
                    usuario.BuscarHabitaciones();
                    break;
                case 2:
                    usuario.hacerReserva(habitaciones, scanner);
                    break;
                case 3:
                    usuario.cancelarReserva(scanner);
                    break;
                case 4:
                    usuario.verDetallesReserva();;
                    break;
                case 5:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
            }
        }
        System.out.println("Ha cerrado sesión como usuario.");
    }
    private static List<Habitacion> cargarHabitacionesDesdeArchivo() {
        List<Habitacion> habitaciones = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(HABITACIONES_FILE))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                String tipo = partes[0];
                double precioPorNoche = Double.parseDouble(partes[1]);
                int numMaxHuespedes = Integer.parseInt(partes[2]);
                String[] comodidades = partes[3].split(";");

                Habitacion habitacion = new Habitacion(tipo, precioPorNoche, numMaxHuespedes, comodidades);
                habitaciones.add(habitacion);
            }
        } catch (IOException e) {
            System.out.println("Error al cargar las habitaciones desde el archivo: " + e.getMessage());
        }

        return habitaciones;
    }


    private static void menuAdministrador(Administrador administrador) {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;
        while (!salir) {
            System.out.println("\nMenú de Administradores del Hotel:");
            System.out.println("1. Añadir o eliminar habitaciones");
            System.out.println("2. Cambiar detalles de las habitaciones");
            System.out.println("3. Ver listas de todas las reservas de los usuarios");
            System.out.println("4. Cerrar sesión");
            System.out.println("Por favor, seleccione una opción:");

            int opcion = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcion) {
                case 1:
                administrador.añadirHabitacion(null);;
                break;
                case 2:
                    administrador.modificarDetallesHabitacion(null, opcion, opcion, null);;
                    break;
                case 3:
                    administrador.verListaReservas();
                    break;
                case 4:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
            }
        }
        System.out.println("Ha cerrado sesión como administrador.");
    }

}

