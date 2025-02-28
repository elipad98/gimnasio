package com.gym.gimnasio.Membresia.controller;

import com.gym.gimnasio.Membresia.entity.Membresia;
import com.gym.gimnasio.Membresia.model.MembresiaDTO;
import com.gym.gimnasio.Membresia.service.MembresiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
}
