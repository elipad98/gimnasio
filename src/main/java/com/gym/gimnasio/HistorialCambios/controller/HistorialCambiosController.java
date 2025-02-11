package com.gym.gimnasio.HistorialCambios.controller;

import com.gym.gimnasio.HistorialCambios.entity.HistorialCambios;
import com.gym.gimnasio.HistorialCambios.model.HistorialCambiosDTO;
import com.gym.gimnasio.HistorialCambios.service.HistorialCambiosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/historial-cambios")
public class HistorialCambiosController {

    @Autowired
    private HistorialCambiosService historialCambiosService;

    // GET: Obtener todos los registros del historial de cambios
    @GetMapping
    public List<HistorialCambios> obtenerTodosLosHistoriales() {
        return historialCambiosService.obtenerTodosLosHistoriales();
    }

    // GET: Obtener un registro del historial por ID
    @GetMapping("/{id}")
    public ResponseEntity<HistorialCambios> obtenerHistorialPorId(@PathVariable Long id) {
        Optional<HistorialCambios> historial = historialCambiosService.obtenerHistorialPorId(id);
        return historial.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST: Crear un nuevo registro en el historial de cambios
    @PostMapping
    public HistorialCambios crearHistorial(@RequestBody HistorialCambiosDTO historialDTO) {
        return historialCambiosService.crearHistorial(historialDTO);
    }

    // PUT: Actualizar un registro existente en el historial de cambios
    @PutMapping("/{id}")
    public ResponseEntity<HistorialCambios> actualizarHistorial(@PathVariable Long id, @RequestBody HistorialCambiosDTO historialDTO) {
        HistorialCambios historial = historialCambiosService.actualizarHistorial(id, historialDTO);
        return ResponseEntity.ok(historial);
    }

    // DELETE: Eliminar un registro del historial por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarHistorial(@PathVariable Long id) {
        historialCambiosService.eliminarHistorial(id);
        return ResponseEntity.noContent().build();
    }
}
