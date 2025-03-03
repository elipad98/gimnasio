package com.gym.gimnasio.Membresia.service;

import com.gym.gimnasio.Membresia.entity.Membresia;
import com.gym.gimnasio.Membresia.model.MembresiaDTO;
import com.gym.gimnasio.Membresia.repository.MembresiaRepository;
import com.gym.gimnasio.Miembro.entity.Miembro;
import com.gym.gimnasio.Miembro.repository.MiembroRepository;
import com.gym.gimnasio.TiposMembresia.entity.TipoMembresia;
import com.gym.gimnasio.TiposMembresia.repository.TipoMembresiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MembresiaService {

    @Autowired
    private MembresiaRepository membresiaRepository;

    @Autowired
    private MiembroRepository miembroRepository;

    @Autowired
    private TipoMembresiaRepository tipoMembresiaRepository;

    // Obtener todas las membresías
    public List<Membresia> obtenerTodasLasMembresias() {
        return membresiaRepository.findAll();
    }

    // Obtener una membresía por ID
    public Optional<Membresia> obtenerMembresiaPorId(Long id) {
        return membresiaRepository.findById(id);
    }

    // Crear una nueva membresía
    public Membresia crearMembresia(MembresiaDTO membresiaDTO) {
        Membresia membresia = new Membresia();

        // Buscar el miembro y el tipo de membresía
        Miembro miembro = miembroRepository.findById(membresiaDTO.getMiembroId())
                .orElseThrow(() -> new RuntimeException("Miembro no encontrado"));
        TipoMembresia tipoMembresia = tipoMembresiaRepository.findById(membresiaDTO.getTipoId())
                .orElseThrow(() -> new RuntimeException("Tipo de membresía no encontrado"));

        // Asignar valores
        membresia.setMiembro(miembro);
        membresia.setTipoMembresia(tipoMembresia);
        membresia.setFechaInicio(membresiaDTO.getFechaInicio());
        membresia.setFechaFin(membresiaDTO.getFechaFin());
        membresia.setMontoPagado(membresiaDTO.getMontoPagado());
        membresia.setMetodoPago(membresiaDTO.getMetodoPago());
        membresia.setNumeroFactura(membresiaDTO.getNumeroFactura());
        membresia.setEstadoPago(membresiaDTO.getEstadoPago());
        membresia.setFechaPago(membresiaDTO.getFechaPago()!= null ? membresiaDTO.getFechaPago() : LocalDateTime.now());
        membresia.setNotas(membresiaDTO.getNotas());

        return membresiaRepository.save(membresia);
    }

    // Actualizar una membresía existente
    public Membresia actualizarMembresia(Long id, MembresiaDTO membresiaDTO) {
        return membresiaRepository.findById(id)
                .map(membresia -> {
                    // Buscar el miembro y el tipo de membresía
                    Miembro miembro = miembroRepository.findById(membresiaDTO.getMiembroId())
                            .orElseThrow(() -> new RuntimeException("Miembro no encontrado"));
                    TipoMembresia tipoMembresia = tipoMembresiaRepository.findById(membresiaDTO.getTipoId())
                            .orElseThrow(() -> new RuntimeException("Tipo de membresía no encontrado"));

                    // Actualizar valores
                    membresia.setMiembro(miembro);
                    membresia.setTipoMembresia(tipoMembresia);
                    membresia.setFechaInicio(membresiaDTO.getFechaInicio());
                    membresia.setFechaFin(membresiaDTO.getFechaFin());
                    membresia.setMontoPagado(membresiaDTO.getMontoPagado());
                    membresia.setMetodoPago(membresiaDTO.getMetodoPago());
                    membresia.setNumeroFactura(membresiaDTO.getNumeroFactura());
                    membresia.setEstadoPago(membresiaDTO.getEstadoPago());
                    membresia.setFechaPago(membresiaDTO.getFechaPago()!= null ? membresiaDTO.getFechaPago() : LocalDateTime.now());
                    membresia.setNotas(membresiaDTO.getNotas());

                    return membresiaRepository.save(membresia);
                })
                .orElseThrow(() -> new RuntimeException("Membresía no encontrada con ID: " + id));
    }

    // Eliminar una membresía por ID
    public void eliminarMembresia(Long id) {
        membresiaRepository.deleteById(id);
    }

    public List<Object[]> obtenerDistribucionPorTipoYMes(int anio, int mes) {
        return membresiaRepository.findDistribucionPorTipoYMes(anio, mes);
    }
    public Map<String, Long> getDistribucionTiposMembresia() {
        return membresiaRepository.countByTipoMembresia()
                .stream()
                .collect(Collectors.toMap(
                        result -> (String) result[0],  // Nombre del tipo de membresía
                        result -> (Long) result[1]    // Cantidad de membresías de este tipo
                ));
    }

}
