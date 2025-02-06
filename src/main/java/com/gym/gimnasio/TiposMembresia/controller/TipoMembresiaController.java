package com.gym.gimnasio.TiposMembresia.controller;

import com.gym.gimnasio.TiposMembresia.entity.TipoMembresia;
import com.gym.gimnasio.TiposMembresia.service.TipoMembresiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tipos-membresia")
public class TipoMembresiaController {

    @Autowired
    private TipoMembresiaService tipoMembresiaService;

    // GET: Obtener todos los tipos de membresía
    @GetMapping
    public List<TipoMembresia> obtenerTodosLosTiposMembresia() {
        return tipoMembresiaService.obtenerTodosLosTiposMembresia();
    }

    // GET: Obtener un tipo de membresía por ID
    @GetMapping("/{id}")
    public ResponseEntity<TipoMembresia> obtenerTipoMembresiaPorId(@PathVariable Long id) {
        Optional<TipoMembresia> tipoMembresia = tipoMembresiaService.obtenerTipoMembresiaPorId(id);
        return tipoMembresia.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST: Crear un nuevo tipo de membresía
    @PostMapping
    public TipoMembresia crearTipoMembresia(@RequestBody TipoMembresia tipoMembresia) {
        return tipoMembresiaService.crearTipoMembresia(tipoMembresia);
    }

    // PUT: Actualizar un tipo de membresía existente
    @PutMapping("/{id}")
    public ResponseEntity<TipoMembresia> actualizarTipoMembresia(@PathVariable Long id, @RequestBody TipoMembresia tipoMembresiaActualizado) {
        TipoMembresia tipoMembresia = tipoMembresiaService.actualizarTipoMembresia(id, tipoMembresiaActualizado);
        return ResponseEntity.ok(tipoMembresia);
    }

    // DELETE: Eliminar un tipo de membresía por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTipoMembresia(@PathVariable Long id) {
        tipoMembresiaService.eliminarTipoMembresia(id);
        return ResponseEntity.noContent().build();
    }
}
