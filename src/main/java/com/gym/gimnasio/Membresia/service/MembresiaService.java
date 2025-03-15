package com.gym.gimnasio.Membresia.service;

import com.gym.gimnasio.Membresia.entity.Membresia;
import com.gym.gimnasio.Membresia.model.MembresiaDTO;
import com.gym.gimnasio.Membresia.repository.MembresiaRepository;
import com.gym.gimnasio.Miembro.entity.Miembro;
import com.gym.gimnasio.Miembro.repository.MiembroRepository;
import com.gym.gimnasio.TiposMembresia.entity.TipoMembresia;
import com.gym.gimnasio.TiposMembresia.repository.TipoMembresiaRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
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

    public Long obtenerConteoMembresiasProximasAVencer(int dias) {
        LocalDate hoy = LocalDate.now();
        LocalDate fechaLimite = hoy.plusDays(dias);
        return membresiaRepository.countMembresiasProximasAVencer(hoy, fechaLimite);
    }

    public BigDecimal obtenerIngresosMesActual() {
        // Obtener el primer y último día del mes actual
        LocalDate hoy = LocalDate.now();
        YearMonth mesActual = YearMonth.from(hoy);
        LocalDateTime inicioMes = mesActual.atDay(1).atStartOfDay();
        LocalDateTime finMes = mesActual.atEndOfMonth().atTime(23, 59, 59);

        return membresiaRepository.sumIngresosMesActual(inicioMes, finMes);
    }

    public List<MembresiaProximaAVencer> obtenerMembresiasProximasAVencer() {
        List<Object[]> resultados = membresiaRepository.obtenerMembresiasProximasAVencer();
        return resultados.stream().map(resultado -> {
            Long membresiaId = ((Number) resultado[0]).longValue();
            String nombre = (String) resultado[1];
            String apellido = (String) resultado[2];
            String tipoMembresia = (String) resultado[3];
            LocalDate fechaInicio = ((java.sql.Date) resultado[4]).toLocalDate();
            LocalDate fechaFin = ((java.sql.Date) resultado[5]).toLocalDate();
            BigDecimal montoPagado = (BigDecimal) resultado[6];
            String estadoPago = (String) resultado[7];
            return new MembresiaProximaAVencer(membresiaId, nombre, apellido, tipoMembresia, fechaInicio, fechaFin, montoPagado, estadoPago);
        }).collect(Collectors.toList());
    }

    @Data
    public static class MembresiaProximaAVencer {
        private Long membresiaId;
        private String nombre;
        private String apellido;
        private String tipoMembresia;
        private LocalDate fechaInicio;
        private LocalDate fechaFin;
        private BigDecimal montoPagado;
        private String estadoPago;

        public MembresiaProximaAVencer(Long membresiaId, String nombre, String apellido, String tipoMembresia,
                                       LocalDate fechaInicio, LocalDate fechaFin, BigDecimal montoPagado, String estadoPago) {
            this.membresiaId = membresiaId;
            this.nombre = nombre;
            this.apellido = apellido;
            this.tipoMembresia = tipoMembresia;
            this.fechaInicio = fechaInicio;
            this.fechaFin = fechaFin;
            this.montoPagado = montoPagado;
            this.estadoPago = estadoPago;
        }
    }
}
