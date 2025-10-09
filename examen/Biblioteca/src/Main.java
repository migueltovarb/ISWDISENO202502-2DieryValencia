import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Sala> salas = new ArrayList<>();
    private static List<Estudiante> estudiantes = new ArrayList<>();
    private static List<Reserva> reservas = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;
        do {
            System.out.println("\nSistema de Reserva de Salas de Estudio");
            System.out.println("1. Registrar Sala");
            System.out.println("2. Registrar Estudiante");
            System.out.println("3. Reservar Sala");
            System.out.println("4. Consultar Historial de Reservas de un Estudiante");
            System.out.println("5. Mostrar Salas Disponibles");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir newline

            switch (opcion) {
                case 1:
                    registrarSala();
                    break;
                case 2:
                    registrarEstudiante();
                    break;
                case 3:
                    reservarSala();
                    break;
                case 4:
                    consultarHistorial();
                    break;
                case 5:
                    mostrarSalasDisponibles();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private static void registrarSala() {
        System.out.print("Número de sala: ");
        int numero = scanner.nextInt();
        System.out.print("Capacidad máxima: ");
        int capacidad = scanner.nextInt();
        System.out.print("Disponible (true/false): ");
        boolean disponible = scanner.nextBoolean();
        scanner.nextLine();

        if (numero <= 0 || capacidad <= 0) {
            System.out.println("Número de sala y capacidad deben ser positivos.");
            return;
        }

        salas.add(new Sala(numero, capacidad, disponible));
        System.out.println("Sala registrada exitosamente.");
    }

    private static void registrarEstudiante() {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Código institucional: ");
        int codigo = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Programa académico: ");
        String programa = scanner.nextLine().trim();

        if (nombre.isEmpty() || programa.isEmpty() || codigo <= 0) {
            System.out.println("Todos los campos son obligatorios y código debe ser positivo.");
            return;
        }

        estudiantes.add(new Estudiante(nombre, codigo, programa));
        System.out.println("Estudiante registrado exitosamente.");
    }

    private static void reservarSala() {
        System.out.print("Código del estudiante: ");
        int codigoEst = scanner.nextInt();
        scanner.nextLine();
        Estudiante est = buscarEstudiantePorCodigo(codigoEst);
        if (est == null) {
            System.out.println("Estudiante no encontrado.");
            return;
        }

        System.out.print("Número de sala: ");
        int numSala = scanner.nextInt();
        scanner.nextLine();
        Sala sala = buscarSalaPorNumero(numSala);
        if (sala == null || !sala.isDisponible()) {
            System.out.println("Sala no encontrada o no disponible.");
            return;
        }

        System.out.print("Año: ");
        int year = scanner.nextInt();
        System.out.print("Mes: ");
        int month = scanner.nextInt();
        System.out.print("Día: ");
        int day = scanner.nextInt();
        System.out.print("Hora: ");
        int hour = scanner.nextInt();
        System.out.print("Minuto: ");
        int minute = scanner.nextInt();
        scanner.nextLine();

        LocalDateTime fechaHora = LocalDateTime.of(year, month, day, hour, minute);

        // Validar no duplicar reserva
        for (Reserva r : reservas) {
            if (r.getEstudiante().getCodigo() == codigoEst && r.getSala().getNumeroSala() == numSala && r.getFechaHora().equals(fechaHora)) {
                System.out.println("Ya existe una reserva para este estudiante, sala y fecha/hora.");
                return;
            }
        }

        // Verificar si la sala está ocupada en esa fecha/hora
        for (Reserva r : reservas) {
            if (r.getSala().getNumeroSala() == numSala && r.getFechaHora().equals(fechaHora)) {
                System.out.println("La sala ya está reservada en esa fecha y hora.");
                return;
            }
        }

        reservas.add(new Reserva(est, sala, fechaHora));
        System.out.println("Reserva realizada exitosamente.");
    }

    private static void consultarHistorial() {
        System.out.print("Código del estudiante: ");
        int codigo = scanner.nextInt();
        scanner.nextLine();
        Estudiante est = buscarEstudiantePorCodigo(codigo);
        if (est == null) {
            System.out.println("Estudiante no encontrado.");
            return;
        }

        System.out.println("Historial de reservas para " + est.getNombre() + ":");
        boolean tieneReservas = false;
        for (Reserva r : reservas) {
            if (r.getEstudiante().getCodigo() == codigo) {
                System.out.println(r);
                tieneReservas = true;
            }
        }
        if (!tieneReservas) {
            System.out.println("No hay reservas.");
        }
    }

    private static void mostrarSalasDisponibles() {
        System.out.println("Salas disponibles:");
        boolean hayDisponibles = false;
        for (Sala s : salas) {
            if (s.isDisponible()) {
                System.out.println(s);
                hayDisponibles = true;
            }
        }
        if (!hayDisponibles) {
            System.out.println("No hay salas disponibles.");
        }
    }

    private static Estudiante buscarEstudiantePorCodigo(int codigo) {
        for (Estudiante e : estudiantes) {
            if (e.getCodigo() == codigo) {
                return e;
            }
        }
        return null;
    }

    private static Sala buscarSalaPorNumero(int numero) {
        for (Sala s : salas) {
            if (s.getNumeroSala() == numero) {
                return s;
            }
        }
        return null;
    }
}
