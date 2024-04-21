import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Administrador extends PersonaBase {
    private static final String HABITACIONES_FILE = "credenciales/habitaciones.txt";
    private static final String ADMINISTRADORES_FILE = "credenciales/administradores.txt";
    private static final String RESERVAS_FILE = "credenciales/reservas.txt";

    public void IniciarSesion() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese su nombre de administrador:");
        String administrador = scanner.nextLine();
        System.out.println("Ingrese su correo electrónico:");
        String correo = scanner.nextLine();
        System.out.println("Ingrese su contraseña:");
        String contraseña = scanner.nextLine();
    
        try (BufferedReader br = new BufferedReader(new FileReader(ADMINISTRADORES_FILE))) {
            String linea;
            boolean encontrado = false;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes[0].equals(administrador) && partes[1].equals(correo) && partes[2].equals(contraseña)) {
                    encontrado = true;
                    break;
                }
            }
            if (encontrado) {
                System.out.println("Inicio de sesión como administrador exitoso.");
            } else {
                System.out.println("Administrador no encontrado o credenciales incorrectas. Por favor, regístrese primero.");
            }
        } catch (IOException e) {
            System.out.println("Error al iniciar sesión como administrador: " + e.getMessage());
        }
    }
    
    public void añadirHabitacion(Habitacion nuevaHabitacion) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(HABITACIONES_FILE, true))) {
            writer.println(nuevaHabitacion.getTipo() + "," + nuevaHabitacion.getPrecioPorNoche() + "," + nuevaHabitacion.getNumMaxHuespedes() + "," + String.join(",", nuevaHabitacion.getComodidades()));
            System.out.println("Se ha añadido una nueva habitación correctamente.");
        } catch (IOException e) {
            System.out.println("Error al añadir la nueva habitación: " + e.getMessage());
        }
    }

    public void eliminarHabitacion(Habitacion habitacion) {
        try {
            List<String> lineas = Files.readAllLines(Paths.get(HABITACIONES_FILE));
            List<String> nuevasLineas = new ArrayList<>();
            for (String linea : lineas) {
                if (!linea.startsWith(habitacion.getTipo())) {
                    nuevasLineas.add(linea);
                }
            }
            try (PrintWriter writer = new PrintWriter(new FileWriter(HABITACIONES_FILE))) {
                for (String nuevaLinea : nuevasLineas) {
                    writer.println(nuevaLinea);
                }
                System.out.println("La habitación ha sido eliminada correctamente.");
            } catch (IOException e) {
                System.out.println("Error al eliminar la habitación: " + e.getMessage());
            }
        } catch (IOException ex) {
            System.out.println("Error al leer el archivo de habitaciones: " + ex.getMessage());
        }
    }

    public void modificarDetallesHabitacion(Habitacion habitacion, double nuevoPrecioPorNoche, int nuevoNumMaxHuespedes, List<String> nuevasComodidades) {
        try {
            List<String> lineas = Files.readAllLines(Paths.get(HABITACIONES_FILE));
            List<String> nuevasLineas = new ArrayList<>();
            for (String linea : lineas) {
                if (linea.startsWith(habitacion.getTipo())) {
                    String[] partes = linea.split(",");
                    partes[1] = Double.toString(nuevoPrecioPorNoche);
                    partes[2] = Integer.toString(nuevoNumMaxHuespedes);
                    // Aquí podrías agregar lógica para actualizar otras propiedades de la habitación si es necesario
                    if (!nuevasComodidades.isEmpty()) {
                        partes[3] = String.join(",", nuevasComodidades);
                    }
                    linea = String.join(",", partes);
                }
                nuevasLineas.add(linea);
            }
            try (PrintWriter writer = new PrintWriter(new FileWriter(HABITACIONES_FILE))) {
                for (String nuevaLinea : nuevasLineas) {
                    writer.println(nuevaLinea);
                }
                System.out.println("Los detalles de la habitación han sido modificados correctamente.");
            } catch (IOException e) {
                System.out.println("Error al modificar los detalles de la habitación: " + e.getMessage());
            }
        } catch (IOException ex) {
            System.out.println("Error al leer el archivo de habitaciones: " + ex.getMessage());
        }
    }

    public void verListaReservas() {
        try (BufferedReader br = new BufferedReader(new FileReader(RESERVAS_FILE))) {
            String linea;
            System.out.println("Lista de reservas:");
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al leer las reservas: " + e.getMessage());
        }
    }
}