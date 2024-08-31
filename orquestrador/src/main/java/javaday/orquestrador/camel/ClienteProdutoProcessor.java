package javaday.orquestrador.camel;

import javaday.orquestrador.model.Cliente;
import javaday.orquestrador.model.Product;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class ClienteProdutoProcessor implements Processor {

    // Listas estáticas para simular um banco de dados
    private static final List<Cliente> clientes = new ArrayList<>();
    private static final List<Product> products = new ArrayList<>();



    @Override
    public void process(Exchange exchange) throws Exception {
        // Recebe o body como um Map com os IDs do cliente e do produto
        Map<String, Long> ids = exchange.getIn().getBody(Map.class);
        Long clienteId = ids.get("clienteId");
        Long produtoId = ids.get("produtoId");

        // Busca o cliente pelo ID
        Optional<Cliente> cliente = clientes.stream()
                .filter(c -> c.getId().equals(clienteId))
                .findFirst();

        // Busca o produto pelo ID
        Optional<Product> product = products.stream()
                .filter(p -> p.getId().equals(produtoId))
                .findFirst();

        if (cliente.isPresent() && product.isPresent()) {
            // Retorna um Map com os dados do cliente e do produto
            exchange.getIn().setBody(Map.of(
                    "cliente", cliente.get(),
                    "produto", product.get()
            ));
        } else {
            // Se não encontrar, retorna 404
            exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, HttpStatus.NOT_FOUND.value());
            exchange.getIn().setBody("Cliente ou Produto não encontrado");
        }
    }
}
