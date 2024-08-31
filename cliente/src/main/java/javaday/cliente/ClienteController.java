package javaday.cliente;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    // Lista estática de clientes para simular um banco de dados
    private static final List<Cliente> clientes = new ArrayList<>();

    static {
        clientes.add(new Cliente(1L, "Cliente A", "clientea@example.com"));
        clientes.add(new Cliente(2L, "Cliente B", "clienteb@example.com"));
        clientes.add(new Cliente(3L, "Cliente C", "clientec@example.com"));
    }

    // Endpoint para listar todos os clientes
    @GetMapping
    public List<Cliente> getAllClientes() {
        return clientes;
    }

    // Endpoint para buscar um cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {
        Optional<Cliente> cliente = clientes.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
        return cliente.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
