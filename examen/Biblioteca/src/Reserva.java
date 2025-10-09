import java.time.LocalDateTime;

public class Reserva {
    private Estudiante estudiante;
    private Sala sala;
    private LocalDateTime fechaHora;

    public Reserva() {
        super();
    }

    public Reserva(Estudiante estudiante, Sala sala, LocalDateTime fechaHora) {
        super();
        this.estudiante = estudiante;
        this.sala = sala;
        this.fechaHora = fechaHora;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    @Override
    public String toString() {
        return "Reserva [estudiante=" + estudiante.getNombre() + ", sala=" + sala.getNumeroSala() + ", fechaHora=" + fechaHora + "]";
    }
}
