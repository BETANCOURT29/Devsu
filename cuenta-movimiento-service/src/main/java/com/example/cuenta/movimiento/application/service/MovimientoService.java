package com.example.cuenta.movimiento.application.service;

import com.example.cuenta.movimiento.api.dto.CuentaResumenDto;
import com.example.cuenta.movimiento.api.dto.EstadoCuentaReporteDto;
import com.example.cuenta.movimiento.api.dto.MovimientoDto;
import com.example.cuenta.movimiento.api.dto.MovimientoResumenDto;
import com.example.cuenta.movimiento.domain.model.Cuenta;
import com.example.cuenta.movimiento.domain.model.Movimiento;
import com.example.cuenta.movimiento.domain.model.TipoMovimiento;
import com.example.cuenta.movimiento.domain.repository.CuentaRepository;
import com.example.cuenta.movimiento.domain.repository.MovimientoRepository;
import com.example.cuenta.movimiento.exception.NotFoundException;
import com.example.cuenta.movimiento.exception.SaldoInsuficienteException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final CuentaRepository cuentaRepository;
    private static final String CUENTA_NO_ENCONTRADA = "Cuenta no encontrada";
    private static final String SALDO_NO_DISPONIBLE = "Saldo no disponible";

    public MovimientoDto registrarMovimiento(MovimientoDto dto) {
        Cuenta cuenta = cuentaRepository.findById(dto.getCuentaId())
                .orElseThrow(() -> new NotFoundException(CUENTA_NO_ENCONTRADA));

        Double nuevoSaldo = calcularNuevoSaldo(cuenta.getSaldoInicial(), dto.getTipoMovimiento(), dto.getValor());

        if (dto.getTipoMovimiento() == TipoMovimiento.RETIRO && cuenta.getSaldoInicial() < dto.getValor()) {
            throw new SaldoInsuficienteException(SALDO_NO_DISPONIBLE);
        }

        Movimiento movimiento = Movimiento.builder()
                .fecha(LocalDateTime.now())
                .tipoMovimiento(dto.getTipoMovimiento())
                .valor(dto.getValor())
                .saldo(nuevoSaldo)
                .cuenta(cuenta)
                .build();

        cuenta.setSaldoInicial(nuevoSaldo);
        cuentaRepository.save(cuenta);

        return mapToDto(movimientoRepository.save(movimiento));
    }

    public List<MovimientoDto> obtenerMovimientos(Long cuentaId) {
        Cuenta cuenta = cuentaRepository.findById(cuentaId)
                .orElseThrow(() -> new NotFoundException(CUENTA_NO_ENCONTRADA));

        return movimientoRepository.findByCuenta(cuenta)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<MovimientoDto> obtenerReporte(Long cuentaId, LocalDateTime desde, LocalDateTime hasta) {
        Cuenta cuenta = cuentaRepository.findById(cuentaId)
                .orElseThrow(() -> new NotFoundException(CUENTA_NO_ENCONTRADA));

        return movimientoRepository.findByCuentaAndFechaBetween(cuenta, desde, hasta)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public EstadoCuentaReporteDto generarReporteEstadoCuenta(String clienteId, LocalDateTime desde, LocalDateTime hasta) {
        List<Cuenta> cuentas = cuentaRepository.findAll()
                .stream()
                .filter(c -> c.getClienteId().equals(clienteId))
                .toList();

        List<CuentaResumenDto> cuentasResumen = cuentas.stream().map(cuenta -> {
            List<Movimiento> movimientos = movimientoRepository.findByCuentaAndFechaBetween(cuenta, desde, hasta);

            List<MovimientoResumenDto> movimientosDto = movimientos.stream()
                    .map(m -> MovimientoResumenDto.builder()
                            .fecha(m.getFecha())
                            .tipoMovimiento(m.getTipoMovimiento())
                            .valor(m.getValor())
                            .saldo(m.getSaldo())
                            .build())
                    .toList();

            return CuentaResumenDto.builder()
                    .numeroCuenta(cuenta.getNumeroCuenta())
                    .tipoCuenta(cuenta.getTipoCuenta())
                    .saldoDisponible(cuenta.getSaldoInicial())
                    .movimientos(movimientosDto)
                    .build();
        }).toList();

        return EstadoCuentaReporteDto.builder()
                .clienteId(clienteId)
                .cuentas(cuentasResumen)
                .build();
    }


    private Double calcularNuevoSaldo(Double saldoActual, TipoMovimiento tipo, Double valor) {
        return tipo == TipoMovimiento.DEPOSITO
                ? saldoActual + valor
                : saldoActual - valor;
    }

    private MovimientoDto mapToDto(Movimiento m) {
        return MovimientoDto.builder()
                .id(m.getId())
                .fecha(m.getFecha())
                .tipoMovimiento(m.getTipoMovimiento())
                .valor(m.getValor())
                .saldo(m.getSaldo())
                .cuentaId(m.getCuenta().getId())
                .build();
    }
}
