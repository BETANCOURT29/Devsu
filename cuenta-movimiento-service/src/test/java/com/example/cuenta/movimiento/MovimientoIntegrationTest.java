package com.example.cuenta.movimiento;

import com.example.cuenta.movimiento.api.dto.MovimientoDto;
import com.example.cuenta.movimiento.domain.model.TipoMovimiento;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class MovimientoIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    private final String BASE_URL = "/movimientos";

    @Test
    void registrarMovimiento_deposito_exitoso() throws Exception {
        MovimientoDto request = MovimientoDto.builder()
                .cuentaId(1L)
                .tipoMovimiento(TipoMovimiento.DEPOSITO)
                .valor(500.0)
                .build();

        mvc.perform(post(BASE_URL)
                        .content(mapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cuentaId").value(1L))
                .andExpect(jsonPath("$.valor").value(500.0))
                .andExpect(jsonPath("$.tipoMovimiento").value("DEPOSITO"));
    }

    @Test
    void registrarMovimiento_falla_por_cuentaId_faltante() throws Exception {
        MovimientoDto request = MovimientoDto.builder()
                .tipoMovimiento(TipoMovimiento.DEPOSITO)
                .valor(100.0)
                .build();

        mvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.cuentaId").value("Debe especificar la cuenta"));
    }

    @Test
    void registrarMovimiento_falla_por_saldo_insuficiente() throws Exception {
        MovimientoDto request = MovimientoDto.builder()
                .cuentaId(1L)
                .tipoMovimiento(TipoMovimiento.RETIRO)
                .valor(1000000.0)
                .build();

        mvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Saldo no disponible"));
    }


}
