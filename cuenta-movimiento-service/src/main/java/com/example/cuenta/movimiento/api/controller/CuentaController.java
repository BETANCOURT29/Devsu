package com.example.cuenta.movimiento.api.controller;


import com.example.cuenta.movimiento.api.dto.CuentaDto;
import com.example.cuenta.movimiento.application.service.CuentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
@RequiredArgsConstructor
public class CuentaController {

    private final CuentaService cuentaService;

    @PostMapping
    public ResponseEntity<CuentaDto> crear(@RequestBody @Valid CuentaDto dto) {
        return ResponseEntity.ok(cuentaService.crearCuenta(dto));
    }

    @GetMapping
    public ResponseEntity<List<CuentaDto>> listar() {
        return ResponseEntity.ok(cuentaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaDto> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(cuentaService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CuentaDto> actualizar(@PathVariable Long id, @RequestBody @Valid CuentaDto dto) {
        return ResponseEntity.ok(cuentaService.actualizarCuenta(id, dto));
    }
}
