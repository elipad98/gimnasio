package com.gym.gimnasio.Asistencia.service;

import com.gym.gimnasio.Asistencia.entity.Asistencia;
import com.gym.gimnasio.Asistencia.model.AsistenciaDTO;
import com.gym.gimnasio.Asistencia.repository.AsistenciaRepository;
import com.gym.gimnasio.Miembro.entity.Miembro;
import com.gym.gimnasio.Miembro.repository.MiembroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
}
