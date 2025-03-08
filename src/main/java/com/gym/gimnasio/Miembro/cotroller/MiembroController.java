package com.gym.gimnasio.Miembro.cotroller;

import com.gym.gimnasio.Miembro.model.MiembroDTO;
import com.gym.gimnasio.Miembro.service.MiembroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/miembros")
public class MiembroController {

    @Autowired
    private MiembroService miembroService;

    // GET: Obtener todos los miembros
    @GetMapping
    public List<MiembroDTO> obtenerTodosLosMiembros() {
        return miembroService.obtenerTodosLosMiembros();
    }

    // GET: Obtener un miembro por ID
    @GetMapping("/{id}")
    public ResponseEntity<MiembroDTO> obtenerMiembroPorId(@PathVariable Long id) {
        MiembroDTO miembro = miembroService.obtenerMiembroPorId(id);
        return miembro != null ? ResponseEntity.ok(miembro) : ResponseEntity.notFound().build();
    }

    // POST: Crear un nuevo miembro
    @PostMapping
    public MiembroDTO crearMiembro(@RequestBody MiembroDTO miembroDTO) {
        return miembroService.crearMiembro(miembroDTO);
    }

    // PUT: Actualizar un miembro existente
    @PutMapping("/{id}")
    public ResponseEntity<MiembroDTO> actualizarMiembro(@PathVariable Long id, @RequestBody MiembroDTO miembroDTO) {
        MiembroDTO miembroActualizado = miembroService.actualizarMiembro(id, miembroDTO);
        return miembroActualizado != null ? ResponseEntity.ok(miembroActualizado) : ResponseEntity.notFound().build();
    }

    // DELETE: Eliminar un miembro por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMiembro(@PathVariable Long id) {
        miembroService.eliminarMiembro(id);
        return ResponseEntity.noContent().build();
    }
    //Obtener miembros activos y inactivos
    @GetMapping("/estadisticas-activos-inactivos")
    public Map<String, Double> getEstadisticasActivosInactivos() {
        return miembroService.calcularEstadisticasActivosInactivos();
    }

    @GetMapping("/conteo-por-sexo")
    public ResponseEntity<Map<String, Long>> obtenerConteoPorSexo() {
        Map<String, Long> conteo = miembroService.obtenerConteoPorSexo();
        return ResponseEntity.ok(conteo);
    }
}