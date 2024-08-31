package javaday.orquestrador;

import lombok.AllArgsConstructor;
import org.apache.camel.ProducerTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("inicio")
@AllArgsConstructor
public class OrquestradorController {

    private final ProducerTemplate producerTemplate;

    @PostMapping("/vamos")
    public ResponseEntity<Object> buscarClienteProduto(@RequestBody Map<String, Long> payload) {
        try {
            // Envia o payload para a rota Camel 'direct:buscarClienteProduto'
            Object response = producerTemplate.requestBody("direct:buscarClienteProduto", payload);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Retorna uma resposta de erro se ocorrer alguma exceção
            return ResponseEntity.status(500).body("Erro ao buscar cliente e produto: " + e.getMessage());
        }
    }


}
