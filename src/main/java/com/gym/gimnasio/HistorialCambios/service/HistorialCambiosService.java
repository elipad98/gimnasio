package com.gym.gimnasio.HistorialCambios.service;

import com.gym.gimnasio.HistorialCambios.entity.HistorialCambios;
import com.gym.gimnasio.HistorialCambios.model.HistorialCambiosDTO;
import com.gym.gimnasio.HistorialCambios.repository.HistorialCambiosRepository;
import com.gym.gimnasio.Miembro.entity.Miembro;
import com.gym.gimnasio.Miembro.repository.MiembroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class HistorialCambiosService {
    @Autowired
    private HistorialCambiosRepository historialCambiosRepository;

    @Autowired
    private MiembroRepository miembroRepository;

    // Obtener todos los registros del historial de cambios
    public List<HistorialCambios> obtenerTodosLosHistoriales() {
        return historialCambiosRepository.findAll();
    }

    // Obtener un registro del historial por ID
    public Optional<HistorialCambios> obtenerHistorialPorId(Long id) {
        return historialCambiosRepository.findById(id);
    }

    // Crear un nuevo registro en el historial de cambios
    public HistorialCambios crearHistorial(HistorialCambiosDTO historialDTO) {
        HistorialCambios historial = new HistorialCambios();

        // Buscar el miembro
        Miembro miembro = miembroRepository.findById(historialDTO.getMiembroId())
                .orElseThrow(() -> new RuntimeException("Miembro no encontrado"));

        // Asignar valores
        historial.setMiembro(miembro);
        historial.setTipoCambio(historialDTO.getTipoCambio());
        historial.setFechaCambio(historialDTO.getFechaCambio()!= null ? historialDTO.getFechaCambio() : LocalDateTime.now());
        historial.setDescripcion(historialDTO.getDescripcion());
        historial.setUsuarioCambio(historialDTO.getUsuarioCambio());

        return historialCambiosRepository.save(historial);
    }

    // Actualizar un registro existente en el historial de cambios
    public HistorialCambios actualizarHistorial(Long id, HistorialCambiosDTO historialDTO) {
        return historialCambiosRepository.findById(id)
                .map(historial -> {
                    // Buscar el miembro
                    Miembro miembro = miembroRepository.findById(historialDTO.getMiembroId())
                            .orElseThrow(() -> new RuntimeException("Miembro no encontrado"));

                    // Actualizar valores
                    historial.setMiembro(miembro);
                    historial.setTipoCambio(historialDTO.getTipoCambio());
                    historial.setFechaCambio(historialDTO.getFechaCambio()!= null ? historialDTO.getFechaCambio() : LocalDateTime.now());
                    historial.setDescripcion(historialDTO.getDescripcion());
                    historial.setUsuarioCambio(historialDTO.getUsuarioCambio());

                    return historialCambiosRepository.save(historial);
                })
                .orElseThrow(() -> new RuntimeException("Historial no encontrado con ID: " + id));
    }

    // Eliminar un registro del historial por ID
    public void eliminarHistorial(Long id) {
        historialCambiosRepository.deleteById(id);
    }
}
