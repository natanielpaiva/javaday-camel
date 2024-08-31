package javaday.orquestrador.camel;

import javaday.orquestrador.model.Cliente;
import javaday.orquestrador.model.Product;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
public class ClienteProdutoRoute extends RouteBuilder {

    private final CombineClienteProdutoProcessor combineProcessor;

    public ClienteProdutoRoute(CombineClienteProdutoProcessor combineProcessor) {
        this.combineProcessor = combineProcessor;
    }

    @Override
    public void configure() throws Exception {
        from("direct:buscarClienteProduto")
                .routeId("buscarClienteProdutoRoute")
                .log("Recebendo requisição com IDs: ${body}")
                .process(exchange -> {
                    // Extrai os IDs de cliente e produto do body
                    Map<String, Long> ids = exchange.getIn().getBody(Map.class);
                    exchange.setProperty("clienteId", ids.get("clienteId"));
                    exchange.setProperty("produtoId", ids.get("produtoId"));
                })
                .to("direct:buscarCliente")
                .to("direct:buscarProduto")
                .end()
                .process(combineProcessor)
                .log("Resposta combinada: ${body}");

        // Sub-rota para buscar cliente
        from("direct:buscarCliente")
                .routeId("buscarClienteRoute")
                .log("Buscando cliente com ID: ${exchangeProperty.clienteId}")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .toD("http://localhost:8082/clientes/${exchangeProperty.clienteId}")
                .log("Resposta cliente: ${body}")
                .unmarshal().json(JsonLibrary.Jackson, Cliente.class)
                .setProperty("cliente", body());

        // Sub-rota para buscar produto
        from("direct:buscarProduto")
                .routeId("buscarProdutoRoute")
                .log("Buscando produto com ID: ${exchangeProperty.produtoId}")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .toD("http://localhost:8081/products/${exchangeProperty.produtoId}")
                .unmarshal().json(JsonLibrary.Jackson, Product.class)
                .log("Resposta produto: ${body}")
                .setProperty("produto", body());
    }
}