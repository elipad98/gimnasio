package com.gym.gimnasio.Asistencia.controller;

import com.gym.gimnasio.Asistencia.entity.Asistencia;
import com.gym.gimnasio.Asistencia.model.AsistenciaDTO;
import com.gym.gimnasio.Asistencia.service.AsistenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/asistencia")
public class AsistenciaController {

    @Autowired
    private AsistenciaService asistenciaService;

    // GET: Obtener todas las asistencias
    @GetMapping
    public List<Asistencia> obtenerTodasLasAsistencias() {
        return asistenciaService.obtenerTodasLasAsistencias();
    }

    // GET: Obtener una asistencia por ID
    @GetMapping("/{id}")
    public ResponseEntity<Asistencia> obtenerAsistenciaPorId(@PathVariable Long id) {
        Optional<Asistencia> asistencia = asistenciaService.obtenerAsistenciaPorId(id);
        return asistencia.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST: Crear una nueva asistencia
    @PostMapping
    public Asistencia crearAsistencia(@RequestBody AsistenciaDTO asistenciaDTO) {
        return asistenciaService.crearAsistencia(asistenciaDTO);
    }

    // PUT: Actualizar una asistencia existente
    @PutMapping("/{id}")
    public ResponseEntity<Asistencia> actualizarAsistencia(@PathVariable Long id, @RequestBody AsistenciaDTO asistenciaDTO) {
        Asistencia asistencia = asistenciaService.actualizarAsistencia(id, asistenciaDTO);
        return ResponseEntity.ok(asistencia);
    }

    // DELETE: Eliminar una asistencia por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAsistencia(@PathVariable Long id) {
        asistenciaService.eliminarAsistencia(id);
        return ResponseEntity.noContent().build();
    }
    // GET: Obtener estadisticas de asistencia por mes, semana y dia
    @GetMapping("/estadisticas")
    public Map<String, Object> obtenerEstadisticas(@RequestParam String rango) {
        return asistenciaService.obtenerEstadisticas(rango);
    }
}
