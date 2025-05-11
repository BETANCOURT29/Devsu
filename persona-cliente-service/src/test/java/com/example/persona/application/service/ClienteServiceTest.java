package com.example.persona.application.service;

import com.example.persona.api.dto.ClienteDto;
import com.example.persona.domain.model.Cliente;
import com.example.persona.domain.repository.ClienteRepository;
import com.example.persona.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    private Cliente cliente;
    private ClienteDto dto;

    @BeforeEach
    void setUp() {
        dto = new ClienteDto();
        dto.setNombre("Juan");
        dto.setGenero("Masculino");
        dto.setEdad(30);
        dto.setIdentificacion("123456");
        dto.setDireccion("Av Siempre Viva");
        dto.setTelefono("3101234567");
        dto.setClienteId("juan.perez");
        dto.setContrasena("pass123");
        dto.setEstado(true);

        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre(dto.getNombre());
        cliente.setGenero(dto.getGenero());
        cliente.setEdad(dto.getEdad());
        cliente.setIdentificacion(dto.getIdentificacion());
        cliente.setDireccion(dto.getDireccion());
        cliente.setTelefono(dto.getTelefono());
        cliente.setClienteId(dto.getClienteId());
        cliente.setContrasena(dto.getContrasena());
        cliente.setEstado(dto.getEstado());
    }

    @Test
    void crearCliente_deberiaRetornarDtoGuardado() {
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        ClienteDto resultado = clienteService.crearCliente(dto);

        assertNotNull(resultado);
        assertEquals("juan.perez", resultado.getClienteId());
        verify(clienteRepository).save(any(Cliente.class));
    }

    @Test
    void listarClientes_deberiaRetornarLista() {
        when(clienteRepository.findAll()).thenReturn(List.of(cliente));

        List<ClienteDto> resultado = clienteService.listarClientes();

        assertEquals("Juan", resultado.get(0).getNombre());
        verify(clienteRepository).findAll();
    }

    @Test
    void obtenerPorId_existente_deberiaRetornarCliente() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        ClienteDto resultado = clienteService.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
        verify(clienteRepository).findById(1L);
    }

    @Test
    void actualizarCliente_existente_deberiaActualizarCampos() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        dto.setNombre("Juan Actualizado");
        ClienteDto actualizado = clienteService.actualizarCliente(1L, dto);

        assertEquals("Juan Actualizado", actualizado.getNombre());
        verify(clienteRepository).save(any(Cliente.class));
    }

    @Test
    void actualizarParcial_deberiaActualizarSoloCamposNoNulos() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        ClienteDto parcial = new ClienteDto();
        parcial.setTelefono("999999999");
        parcial.setDireccion("Nueva dirección");

        ClienteDto resultado = clienteService.actualizarParcial(1L, parcial);

        assertEquals("999999999", resultado.getTelefono());
        assertEquals("Nueva dirección", resultado.getDireccion());
        verify(clienteRepository).save(any(Cliente.class));
    }

    @Test
    void eliminarCliente_deberiaInvocarDelete() {
        clienteService.eliminarCliente(1L);
        verify(clienteRepository).deleteById(1L);
    }

    @Test
    void actualizarCliente_noExistente_deberiaLanzarExcepcion() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () ->
                clienteService.actualizarCliente(1L, dto));

        assertEquals("Cliente no encontrado", ex.getMessage());
    }

}