package javaday.orquestrador.camel;


import javaday.orquestrador.model.Cliente;
import javaday.orquestrador.model.Product;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CombineClienteProdutoProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        // Extrai os dados do cliente e produto do exchange
        Cliente cliente = exchange.getProperty("cliente", Cliente.class);
        Product produto = exchange.getProperty("produto", Product.class);

        // Combina os dados e define no body da resposta
        exchange.getIn().setBody(Map.of(
                "cliente", cliente,
                "produto", produto
        ));
    }
}
