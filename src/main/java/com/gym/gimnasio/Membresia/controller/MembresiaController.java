package com.gym.gimnasio.Membresia.controller;

import com.gym.gimnasio.Membresia.entity.Membresia;
import com.gym.gimnasio.Membresia.model.MembresiaDTO;
import com.gym.gimnasio.Membresia.service.MembresiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/membresias")
public class MembresiaController {
    @Autowired
    private MembresiaService membresiaService;

    // GET: Obtener todas las membresías
    @GetMapping
    public List<Membresia> obtenerTodasLasMembresias() {
        return membresiaService.obtenerTodasLasMembresias();
    }

    // GET: Obtener una membresía por ID
    @GetMapping("/{id}")
    public ResponseEntity<Membresia> obtenerMembresiaPorId(@PathVariable Long id) {
        Optional<Membresia> membresia = membresiaService.obtenerMembresiaPorId(id);
        return membresia.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST: Crear una nueva membresía
    @PostMapping
    public Membresia crearMembresia(@RequestBody MembresiaDTO membresiaDTO) {
        return membresiaService.crearMembresia(membresiaDTO);
    }

    // PUT: Actualizar una membresía existente
    @PutMapping("/{id}")
    public ResponseEntity<Membresia> actualizarMembresia(@PathVariable Long id, @RequestBody MembresiaDTO membresiaDTO) {
        Membresia membresia = membresiaService.actualizarMembresia(id, membresiaDTO);
        return ResponseEntity.ok(membresia);
    }

    // DELETE: Eliminar una membresía por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMembresia(@PathVariable Long id) {
        membresiaService.eliminarMembresia(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/distribucion")
    public List<Object[]> obtenerDistribucionPorTipoYMes(
            @RequestParam int anio,
            @RequestParam int mes) {
        return membresiaService.obtenerDistribucionPorTipoYMes(anio, mes);
    }
    @GetMapping("/distribucion-tipos")
    public Map<String, Long> getDistribucionTiposMembresia() {
        return membresiaService.getDistribucionTiposMembresia();
    }

    @GetMapping("/conteo-proximas-a-vencer/{dias}")
    public ResponseEntity<Long> obtenerConteoMembresiasProximasAVencer(@PathVariable int dias) {
        Long conteo = membresiaService.obtenerConteoMembresiasProximasAVencer(dias);
        return ResponseEntity.ok(conteo);
    }

    @GetMapping("/ingresos-mes")
    public ResponseEntity<BigDecimal> obtenerIngresosMesActual() {
        BigDecimal ingresos = membresiaService.obtenerIngresosMesActual();
        return ResponseEntity.ok(ingresos);
    }

    @GetMapping("/proximas-a-vencer")
    public ResponseEntity<List<MembresiaService.MembresiaProximaAVencer>> obtenerMembresiasProximasAVencer() {
        List<MembresiaService.MembresiaProximaAVencer> resultado = membresiaService.obtenerMembresiasProximasAVencer();
        return ResponseEntity.ok(resultado);
    }
}
