package com.gym.gimnasio.Miembro.service;

import com.gym.gimnasio.Miembro.entity.Miembro;
import com.gym.gimnasio.Miembro.model.MiembroDTO;
import com.gym.gimnasio.Miembro.model.Sexo;
import com.gym.gimnasio.Miembro.repository.MiembroRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
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
                    miembro.setNombre(miembroDTO.getNombre());
                    miembro.setApellido(miembroDTO.getApellido());
                    miembro.setFechaNacimiento(miembroDTO.getFechaNacimiento());
                    miembro.setTelefono(miembroDTO.getTelefono());
                    miembro.setDireccion(miembroDTO.getDireccion());
                    miembro.setEstado(miembroDTO.getEstado());
                    miembro.setNotas(miembroDTO.getNotas());
                    miembro.setSexo(miembroDTO.getSexo());
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
        dto.setDireccion(miembro.getDireccion());
        dto.setFechaRegistro(miembro.getFechaRegistro());
        dto.setSexo(miembro.getSexo());
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
        miembro.setDireccion(dto.getDireccion());
        miembro.setEstado(dto.getEstado());
        miembro.setSexo(dto.getSexo());
        miembro.setNotas(dto.getNotas());
        return miembro;
    }

    public Map<String, Double> calcularEstadisticasActivosInactivos() {
        long totalMiembros = miembroRepository.count();
        long totalActivos = miembroRepository.countByEstado(true);
        long totalInactivos = miembroRepository.countByEstado(false);

//        double porcentajeActivos = (totalMiembros > 0) ? (totalActivos * 100.0) / totalMiembros : 0;
//        double porcentajeInactivos = (totalMiembros > 0) ? (totalInactivos * 100.0) / totalMiembros : 0;
//
//        // Redondear a un número entero
//        porcentajeActivos = Math.round(porcentajeActivos);
//        porcentajeInactivos = Math.round(porcentajeInactivos);
        double activos = (double) totalActivos;
        double inactivos =(double) totalInactivos;
        double total = (double) totalMiembros;
        Map<String, Double> estadisticas = new HashMap<>();
        estadisticas.put("activos", activos);
        estadisticas.put("inactivos", inactivos);
        estadisticas.put("total",total);


        return estadisticas;
    }
    public Map<String, Long> obtenerConteoPorSexo() {
        List<Object[]> resultados = miembroRepository.contarPorSexo();
        Map<String, Long> conteoPorSexo = new HashMap<>();

        conteoPorSexo.put("MASCULINO", 0L);
        conteoPorSexo.put("FEMENINO", 0L);
        conteoPorSexo.put("OTRO", 0L);

        for (Object[] resultado : resultados) {
            Sexo sexo = (Sexo) resultado[0]; // Usa Sexo directamente, no Miembro.Sexo
            Long cantidad = (Long) resultado[1];
            conteoPorSexo.put(sexo.name(), cantidad);
        }

        return conteoPorSexo;
    }

    public Long obtenerTotalMiembrosActivos() {
        return miembroRepository.countMiembrosActivos();
    }

    public List<MiembroPorMes> obtenerMiembrosPorMes(Integer year) {
        // Usar el año actual si no se proporciona uno
        int selectedYear = (year != null) ? year : LocalDate.now().getYear();

        // Obtener los datos del repositorio
        List<Object[]> resultados = miembroRepository.contarMiembrosPorMes(selectedYear);

        // Crear una lista con todos los meses del año, inicializados en 0
        List<MiembroPorMes> miembrosPorMes = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            miembrosPorMes.add(new MiembroPorMes(selectedYear, month, 0L));
        }

        // Combinar los datos recibidos del repositorio
        for (Object[] resultado : resultados) {
            Integer month = ((Number) resultado[0]).intValue();
            Long count = ((Number) resultado[1]).longValue();
            miembrosPorMes.set(month - 1, new MiembroPorMes(selectedYear, month, count)); // Reemplazar el mes correspondiente
        }

        return miembrosPorMes;
    }

    public static class MiembroPorMes {
        private Integer year;
        private Integer month;
        private Long count;

        public MiembroPorMes(Integer year, Integer month, Long count) {
            this.year = year;
            this.month = month;
            this.count = count;
        }

        // Getters (necesarios para serialización a JSON)
        public Integer getYear() {
            return year;
        }

        public Integer getMonth() {
            return month;
        }

        public Long getCount() {
            return count;
        }
    }

    public List<RangoEdad> obtenerDistribucionEdades() {
        List<Object[]> resultados = miembroRepository.obtenerDistribucionEdades();
        List<RangoEdad> distribucion = new ArrayList<>();

        String[] rangos = {"15-20", "21-30", "31-40", "41-50", "50+"};
        int[] conteos = new int[rangos.length];

        for (int i = 0; i < rangos.length; i++) {
            conteos[i] = 0;
        }

        for (Object[] resultado : resultados) {
            String rango = (String) resultado[0];
            Long cantidad = (Long) resultado[1];
            for (int i = 0; i < rangos.length; i++) {
                if (rangos[i].equals(rango)) {
                    conteos[i] = cantidad.intValue();
                    break;
                }
            }
        }

        for (int i = 0; i < rangos.length; i++) {
            distribucion.add(new RangoEdad(rangos[i], conteos[i]));
        }

        return distribucion;
    }

    @Data
    public static class RangoEdad {
        private String rango;
        private int cantidad;

        public RangoEdad(String rango, int cantidad) {
            this.rango = rango;
            this.cantidad = cantidad;
        }
    }
}