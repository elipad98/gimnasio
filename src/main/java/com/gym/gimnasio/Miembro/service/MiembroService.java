package com.gym.gimnasio.Miembro.service;

import com.gym.gimnasio.Miembro.entity.Miembro;
import com.gym.gimnasio.Miembro.model.MiembroDTO;
import com.gym.gimnasio.Miembro.repository.MiembroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MiembroService {

    @Autowired
    private MiembroRepository miembroRepository;

    // Obtener todos los miembros
    public List<MiembroDTO> obtenerTodosLosMiembros() {
        return miembroRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Obtener un miembro por ID
    public MiembroDTO obtenerMiembroPorId(Long id) {
        Optional<Miembro> miembro = miembroRepository.findById(id);
        return miembro.map(this::convertirADTO).orElse(null);
    }

    // Crear un nuevo miembro
    public MiembroDTO crearMiembro(MiembroDTO miembroDTO) {
        Miembro miembro = convertirAEntidad(miembroDTO);
        miembro = miembroRepository.save(miembro);
        return convertirADTO(miembro);
    }

    // Actualizar un miembro existente
    public MiembroDTO actualizarMiembro(Long id, MiembroDTO miembroDTO) {
        return miembroRepository.findById(id)
                .map(miembro -> {
                    miembro.setNombre(miembroDTO        .getNombre());
                    miembro.setApellido(miembroDTO.getApellido());
                    miembro.setFechaNacimiento(miembroDTO.getFechaNacimiento());
                    miembro.setTelefono(miembroDTO.getTelefono());
                    miembro.setEmail(miembroDTO.getEmail());
                    miembro.setDireccion(miembroDTO.getDireccion());
                    miembro.setEstado(miembroDTO.getEstado());
                    miembro.setNotas(miembroDTO.getNotas());
                    miembro = miembroRepository.save(miembro);
                    return convertirADTO(miembro);
                })
                .orElse(null);
    }

    // Eliminar un miembro por ID
    public void eliminarMiembro(Long id) {
        miembroRepository.deleteById(id);
    }

    // Métodos auxiliares para convertir entre Entity y DTO
    private MiembroDTO convertirADTO(Miembro miembro) {
        MiembroDTO dto = new MiembroDTO();
        dto.setMiembroId(miembro.getMiembroId());
        dto.setNombre(miembro.getNombre());
        dto.setApellido(miembro.getApellido());
        dto.setFechaNacimiento(miembro.getFechaNacimiento());
        dto.setTelefono(miembro.getTelefono());
        dto.setEmail(miembro.getEmail());
        dto.setDireccion(miembro.getDireccion());
        dto.setFechaRegistro(miembro.getFechaRegistro());
        dto.setEstado(miembro.getEstado());
        dto.setNotas(miembro.getNotas());
        return dto;
    }

    private Miembro convertirAEntidad(MiembroDTO dto) {
        Miembro miembro = new Miembro();
        miembro.setNombre(dto.getNombre());
        miembro.setApellido(dto.getApellido());
        miembro.setFechaNacimiento(dto.getFechaNacimiento());
        miembro.setTelefono(dto.getTelefono());
        miembro.setEmail(dto.getEmail());
        miembro.setDireccion(dto.getDireccion());
        miembro.setEstado(dto.getEstado());
        miembro.setNotas(dto.getNotas());
        return miembro;
    }
}