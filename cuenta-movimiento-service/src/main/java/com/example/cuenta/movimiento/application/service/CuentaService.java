package com.example.cuenta.movimiento.application.service;

import com.example.cuenta.movimiento.api.dto.CuentaDto;
import com.example.cuenta.movimiento.domain.model.Cuenta;
import com.example.cuenta.movimiento.domain.repository.CuentaRepository;
import com.example.cuenta.movimiento.exception.NotFoundException;
import com.example.cuenta.movimiento.infrastructure.messaging.ClienteCreadoEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CuentaService {

    private final CuentaRepository cuentaRepository;

    private static final String CUENTA_NO_ENCONTRADA = "Cuenta no encontrada";

    public CuentaDto crearCuenta(CuentaDto dto) {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(dto.getNumeroCuenta());
        cuenta.setTipoCuenta(dto.getTipoCuenta());
        cuenta.setSaldoInicial(dto.getSaldoInicial());
        cuenta.setEstado(dto.getEstado());
        cuenta.setClienteId(dto.getClienteId());

        Cuenta guardada = cuentaRepository.save(cuenta);
        return mapToDto(guardada);
    }

    public CuentaDto actualizarCuenta(Long id, CuentaDto dto) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(CUENTA_NO_ENCONTRADA));

        cuenta.setTipoCuenta(dto.getTipoCuenta());
        cuenta.setSaldoInicial(dto.getSaldoInicial());
        cuenta.setEstado(dto.getEstado());

        return mapToDto(cuentaRepository.save(cuenta));
    }

    public List<CuentaDto> listar() {
        return cuentaRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public CuentaDto obtenerPorId(Long id) {
        return cuentaRepository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new NotFoundException(CUENTA_NO_ENCONTRADA));
    }

    public void  crearCuentaInicialParaCliente(ClienteCreadoEvent event) {
        if (cuentaRepository.existsByNumeroCuenta(event.getClienteId())) {
            System.out.println("Ya existe cuenta para cliente: " + event.getClienteId());
            return;
        }

        Cuenta cuenta = Cuenta.builder()
                .numeroCuenta(event.getClienteId())
                .tipoCuenta("Ahorros")
                .saldoInicial(0.0)
                .estado(true)
                .clienteId(event.getClienteId())
                .build();

        cuentaRepository.save(cuenta);

        System.out.println("Cuenta creada automáticamente para cliente: " + event.getClienteId());
    }

    private CuentaDto mapToDto(Cuenta cuenta) {
        return CuentaDto.builder()
                .id(cuenta.getId())
                .numeroCuenta(cuenta.getNumeroCuenta())
                .tipoCuenta(cuenta.getTipoCuenta())
                .saldoInicial(cuenta.getSaldoInicial())
                .estado(cuenta.getEstado())
                .clienteId(cuenta.getClienteId())
                .build();
    }
}
