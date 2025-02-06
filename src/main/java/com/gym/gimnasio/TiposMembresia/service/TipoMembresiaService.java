package com.gym.gimnasio.TiposMembresia.service;

import com.gym.gimnasio.TiposMembresia.entity.TipoMembresia;
import com.gym.gimnasio.TiposMembresia.repository.TipoMembresiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoMembresiaService {

    @Autowired
    private TipoMembresiaRepository tipoMembresiaRepository;

    // Obtener todos los tipos de membresía
    public List<TipoMembresia> obtenerTodosLosTiposMembresia() {
        return tipoMembresiaRepository.findAll();
    }

    // Obtener un tipo de membresía por ID
    public Optional<TipoMembresia> obtenerTipoMembresiaPorId(Long id) {
        return tipoMembresiaRepository.findById(id);
    }

    // Crear un nuevo tipo de membresía
    public TipoMembresia crearTipoMembresia(TipoMembresia tipoMembresia) {
        return tipoMembresiaRepository.save(tipoMembresia);
    }

    // Actualizar un tipo de membresía existente
    public TipoMembresia actualizarTipoMembresia(Long id, TipoMembresia tipoMembresiaActualizado) {
        return tipoMembresiaRepository.findById(id)
                .map(tipoMembresia -> {
                    tipoMembresia.setNombre(tipoMembresiaActualizado.getNombre());
                    tipoMembresia.setDuracionDias(tipoMembresiaActualizado.getDuracionDias());
                    tipoMembresia.setPrecio(tipoMembresiaActualizado.getPrecio());
                    tipoMembresia.setDescripcion(tipoMembresiaActualizado.getDescripcion());
                    tipoMembresia.setEstado(tipoMembresiaActualizado.getEstado());
                    return tipoMembresiaRepository.save(tipoMembresia);
                })
                .orElseThrow(() -> new RuntimeException("Tipo de membresía no encontrado con ID: " + id));
    }

    // Eliminar un tipo de membresía por ID
    public void eliminarTipoMembresia(Long id) {
        tipoMembresiaRepository.deleteById(id);
    }
}
