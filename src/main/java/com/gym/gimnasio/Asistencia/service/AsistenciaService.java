package com.gym.gimnasio.Asistencia.service;

import com.gym.gimnasio.Asistencia.entity.Asistencia;
import com.gym.gimnasio.Asistencia.model.AsistenciaDTO;
import com.gym.gimnasio.Asistencia.repository.AsistenciaRepository;
import com.gym.gimnasio.Miembro.entity.Miembro;
import com.gym.gimnasio.Miembro.repository.MiembroRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AsistenciaService {

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    @Autowired
    private MiembroRepository miembroRepository;

    // Obtener todas las asistencias
    public List<Asistencia> obtenerTodasLasAsistencias() {
        return asistenciaRepository.findAll();
    }

    // Obtener una asistencia por ID
    public Optional<Asistencia> obtenerAsistenciaPorId(Long id) {
        return asistenciaRepository.findById(id);
    }

    // Crear una nueva asistencia
    public Asistencia crearAsistencia(AsistenciaDTO asistenciaDTO) {
        Asistencia asistencia = new Asistencia();

        // Buscar el miembro
        Miembro miembro = miembroRepository.findById(asistenciaDTO.getMiembroId())
                .orElseThrow(() -> new RuntimeException("Miembro no encontrado"));

        // Asignar valores
        asistencia.setMiembro(miembro);
        asistencia.setFechaEntrada(asistenciaDTO.getFechaEntrada()!= null ? asistenciaDTO.getFechaEntrada() : LocalDateTime.now());
        asistencia.setFechaSalida(asistenciaDTO.getFechaSalida());
        asistencia.setDuracionMinutos(asistenciaDTO.getDuracionMinutos());
        asistencia.setNotas(asistenciaDTO.getNotas());

        return asistenciaRepository.save(asistencia);
    }

    // Actualizar una asistencia existente
    public Asistencia actualizarAsistencia(Long id, AsistenciaDTO asistenciaDTO) {
        return asistenciaRepository.findById(id)
                .map(asistencia -> {
                    // Buscar el miembro
                    Miembro miembro = miembroRepository.findById(asistenciaDTO.getMiembroId())
                            .orElseThrow(() -> new RuntimeException("Miembro no encontrado"));

                    // Actualizar valores
                    asistencia.setMiembro(miembro);
                    asistencia.setFechaEntrada(asistenciaDTO.getFechaEntrada()!= null ? asistenciaDTO.getFechaEntrada() : LocalDateTime.now());
                    asistencia.setFechaSalida(asistenciaDTO.getFechaSalida());
                    asistencia.setDuracionMinutos(asistenciaDTO.getDuracionMinutos());
                    asistencia.setNotas(asistenciaDTO.getNotas());

                    return asistenciaRepository.save(asistencia);
                })
                .orElseThrow(() -> new RuntimeException("Asistencia no encontrada con ID: " + id));
    }

    // Eliminar una asistencia por ID
    public void eliminarAsistencia(Long id) {
        asistenciaRepository.deleteById(id);
    }

    // Obtener estadísticas de asistencia por rango de tiempo
    public Map<String, Object> obtenerEstadisticas(String rango) {
        Map<String, Object> estadisticas = new HashMap<>();
        LocalDateTime ahora = LocalDateTime.now();

        switch (rango.toLowerCase()) {
            case "dia":
                estadisticas.put("data", obtenerAsistenciasPorHora(ahora));
                break;
            case "semana":
                estadisticas.put("data", obtenerAsistenciasPorDiaSemana(ahora));
                break;
            case "mes":
                estadisticas.put("data", obtenerAsistenciasPorMes(ahora));
                break;
            default:
                throw new IllegalArgumentException("Rango no válido. Use 'dia', 'semana' o 'mes'.");
        }

        return estadisticas;
    }

    // Obtener asistencias por hora del día
    private List<Integer> obtenerAsistenciasPorHora(LocalDateTime fecha) {
        List<Object[]> resultados = asistenciaRepository.countAsistenciasPorHora(fecha);
        Map<Integer, Integer> asistenciasPorHora = new HashMap<>();

        // Inicializar todas las horas con 0
        for (int i = 0; i < 24; i++) {
            asistenciasPorHora.put(i, 0);
        }

        // Llenar con los datos de la base de datos
        for (Object[] resultado : resultados) {
            int hora = (int) resultado[0];
            int cantidad = ((Number) resultado[1]).intValue();
            asistenciasPorHora.put(hora, cantidad);
        }

        // Convertir a lista
        return asistenciasPorHora.values().stream().toList();
    }

    // Obtener asistencias por día de la semana
    private List<Integer> obtenerAsistenciasPorDiaSemana(LocalDateTime fecha) {
        List<Object[]> resultados = asistenciaRepository.countAsistenciasPorDiaSemana(fecha);
        Map<Integer, Integer> asistenciasPorDia = new HashMap<>();

        // Inicializar todos los días con 0
        for (int i = 1; i <= 7; i++) {
            asistenciasPorDia.put(i, 0);
        }

        // Llenar con los datos de la base de datos
        for (Object[] resultado : resultados) {
            int dia = (int) resultado[0]; // Valor devuelto por DAYOFWEEK (1=Domingo, 2=Lunes, ..., 7=Sábado)
            int cantidad = ((Number) resultado[1]).intValue();

            // Mapear el día al orden correcto (Lunes=1, Domingo=7)
            int diaAjustado = dia == 1 ? 7 : dia - 1; // Domingo=7, Lunes=1, ..., Sábado=6
            asistenciasPorDia.put(diaAjustado, cantidad);
        }

        // Convertir a lista en el orden correcto (Lunes=1, Domingo=7)
        List<Integer> asistenciasOrdenadas = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            asistenciasOrdenadas.add(asistenciasPorDia.get(i));
        }

        return asistenciasOrdenadas;
    }

    // Obtener asistencias por mes del año
    private List<Integer> obtenerAsistenciasPorMes(LocalDateTime fecha) {
        List<Object[]> resultados = asistenciaRepository.countAsistenciasPorMesdelAno(fecha);
        Map<Integer, Integer> asistenciasPorMes = new HashMap<>();

        // Inicializar todos los meses con 0
        for (int i = 1; i <= 12; i++) {
            asistenciasPorMes.put(i, 0);
        }

        // Llenar con los datos de la base de datos
        for (Object[] resultado : resultados) {
            int mes = (int) resultado[0];
            int cantidad = ((Number) resultado[1]).intValue();
            asistenciasPorMes.put(mes, cantidad);
        }

        // Convertir a lista
        return asistenciasPorMes.values().stream().toList();
    }

    public Long obtenerAsistenciasDiaActual() {
        // Obtener el inicio y fin del día actual
        LocalDate hoy = LocalDate.now();
        LocalDateTime inicioDia = hoy.atStartOfDay();
        LocalDateTime finDia = hoy.atTime(23, 59, 59);

        return asistenciaRepository.countAsistenciasDiaActual(inicioDia, finDia);
    }

    public List<TopMiembroFrecuente> obtenerTopMiembrosFrecuentes() {
        List<Object[]> resultados = asistenciaRepository.obtenerTopMiembrosFrecuentes();
        return resultados.stream().map(resultado -> {
            Long miembroId = ((Number) resultado[0]).longValue();
            String nombre = (String) resultado[1];
            String apellido = (String) resultado[2];
            Long totalAsistencias = ((Number) resultado[3]).longValue();
            return new TopMiembroFrecuente(miembroId, nombre, apellido, totalAsistencias.intValue());
        }).collect(Collectors.toList());
    }

    @Data
    public static class TopMiembroFrecuente {
        private Long miembroId;
        private String nombre;
        private String apellido;
        private int totalAsistencias;

        public TopMiembroFrecuente(Long miembroId, String nombre, String apellido, int totalAsistencias) {
            this.miembroId = miembroId;
            this.nombre = nombre;
            this.apellido = apellido;
            this.totalAsistencias = totalAsistencias;
        }
    }
}
