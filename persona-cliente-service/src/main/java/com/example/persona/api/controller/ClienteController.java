package com.example.persona.api.controller;

import com.example.persona.api.dto.ClienteDto;
import com.example.persona.application.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteDto> crear(@RequestBody @Valid ClienteDto dto) {
        return ResponseEntity.ok(clienteService.crearCliente(dto));
    }

    @GetMapping
    public ResponseEntity<List<ClienteDto>> listar() {
        return ResponseEntity.ok(clienteService.listarClientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDto> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.obtenerPorId(id));

    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDto> actualizar(@PathVariable Long id, @RequestBody @Valid ClienteDto dto) {
        return ResponseEntity.ok(clienteService.actualizarCliente(id, dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClienteDto> actualizarParcial(@PathVariable Long id, @RequestBody ClienteDto parcialDto) {
        ClienteDto actualizado = clienteService.actualizarParcial(id, parcialDto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }
}
