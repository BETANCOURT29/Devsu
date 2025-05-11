package com.example.persona.application.service;

import com.example.persona.api.dto.ClienteDto;
import com.example.persona.domain.model.Cliente;
import com.example.persona.domain.repository.ClienteRepository;
import com.example.persona.exception.NotFoundException;
import com.example.persona.infrastructure.messaging.ClienteCreadoEvent;
import com.example.persona.infrastructure.messaging.RabbitProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private static final String CLIENTE_NO_ENCONTRADO = "Cliente no encontrado";
    private final ClienteRepository clienteRepository;
    private final RabbitProducer rabbitProducer;

    public ClienteDto crearCliente(ClienteDto dto) {
        Cliente cliente = new Cliente();
        cliente.setNombre(dto.getNombre());
        cliente.setGenero(dto.getGenero());
        cliente.setEdad(dto.getEdad());
        cliente.setIdentificacion(dto.getIdentificacion());
        cliente.setDireccion(dto.getDireccion());
        cliente.setTelefono(dto.getTelefono());

        cliente.setClienteId(dto.getClienteId());
        cliente.setContrasena(dto.getContrasena());
        cliente.setEstado(dto.getEstado());

        Cliente guardado = clienteRepository.save(cliente);

        ClienteCreadoEvent event = ClienteCreadoEvent.builder()
                .clienteId(guardado.getClienteId())
                .nombre(guardado.getNombre())
                .identificacion(guardado.getIdentificacion())
                .build();

        rabbitProducer.enviarClienteCreado(event);

        ClienteDto respuesta = new ClienteDto();
        respuesta.setId(guardado.getId());
        respuesta.setNombre(guardado.getNombre());
        respuesta.setGenero(guardado.getGenero());
        respuesta.setEdad(guardado.getEdad());
        respuesta.setIdentificacion(guardado.getIdentificacion());
        respuesta.setDireccion(guardado.getDireccion());
        respuesta.setTelefono(guardado.getTelefono());
        respuesta.setClienteId(guardado.getClienteId());
        respuesta.setContrasena(guardado.getContrasena());
        respuesta.setEstado(guardado.getEstado());

        return respuesta;
    }

    public List<ClienteDto> listarClientes() {
        return clienteRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public ClienteDto obtenerPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(CLIENTE_NO_ENCONTRADO));

        return mapToDto(cliente);
    }

    public ClienteDto actualizarCliente(Long id, ClienteDto dto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(CLIENTE_NO_ENCONTRADO));
        cliente.setClienteId(dto.getClienteId());
        cliente.setContrasena(dto.getContrasena());
        cliente.setEstado(dto.getEstado());

        cliente.setNombre(dto.getNombre());
        cliente.setGenero(dto.getGenero());
        cliente.setEdad(dto.getEdad());
        cliente.setIdentificacion(dto.getIdentificacion());
        cliente.setDireccion(dto.getDireccion());
        cliente.setTelefono(dto.getTelefono());

        return mapToDto(clienteRepository.save(cliente));
    }

    public ClienteDto actualizarParcial(Long id, ClienteDto parcialDto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(CLIENTE_NO_ENCONTRADO));

        if (parcialDto.getClienteId() != null) cliente.setClienteId(parcialDto.getClienteId());
        if (parcialDto.getContrasena() != null) cliente.setContrasena(parcialDto.getContrasena());
        if (parcialDto.getEstado() != null) cliente.setEstado(parcialDto.getEstado());
        if (parcialDto.getNombre() != null) cliente.setNombre(parcialDto.getNombre());
        if (parcialDto.getGenero() != null) cliente.setGenero(parcialDto.getGenero());
        if (parcialDto.getEdad() != null) cliente.setEdad(parcialDto.getEdad());
        if (parcialDto.getIdentificacion() != null) cliente.setIdentificacion(parcialDto.getIdentificacion());
        if (parcialDto.getDireccion() != null) cliente.setDireccion(parcialDto.getDireccion());
        if (parcialDto.getTelefono() != null) cliente.setTelefono(parcialDto.getTelefono());

        return mapToDto(clienteRepository.save(cliente));
    }

    public void eliminarCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new NotFoundException(CLIENTE_NO_ENCONTRADO);
        }
        clienteRepository.deleteById(id);
    }

    private ClienteDto mapToDto(Cliente cliente) {
        ClienteDto dto = new ClienteDto();
        dto.setId(cliente.getId());
        dto.setClienteId(cliente.getClienteId());
        dto.setContrasena(cliente.getContrasena());
        dto.setEstado(cliente.getEstado());
        dto.setNombre(cliente.getNombre());
        dto.setGenero(cliente.getGenero());
        dto.setEdad(cliente.getEdad());
        dto.setIdentificacion(cliente.getIdentificacion());
        dto.setDireccion(cliente.getDireccion());
        dto.setTelefono(cliente.getTelefono());
        return dto;
    }


}
