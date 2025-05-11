package com.example.cuenta.movimiento.api.controller;

import com.example.cuenta.movimiento.api.dto.EstadoCuentaReporteDto;
import com.example.cuenta.movimiento.api.dto.MovimientoDto;
import com.example.cuenta.movimiento.application.service.MovimientoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/movimientos")
@RequiredArgsConstructor
public class MovimientoController {

    private final MovimientoService movimientoService;

    @PostMapping
    public ResponseEntity<MovimientoDto> registrar(@RequestBody @Valid MovimientoDto dto) {
        return ResponseEntity.ok(movimientoService.registrarMovimiento(dto));
    }

    @GetMapping("/{cuentaId}")
    public ResponseEntity<List<MovimientoDto>> obtenerMovimientos(@PathVariable Long cuentaId) {
        return ResponseEntity.ok(movimientoService.obtenerMovimientos(cuentaId));
    }

    @GetMapping("/reportes")
    public ResponseEntity<EstadoCuentaReporteDto> reporteCompleto(
            @RequestParam String clienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta
    ) {
        return ResponseEntity.ok(movimientoService.generarReporteEstadoCuenta(clienteId, desde, hasta));
    }
}
