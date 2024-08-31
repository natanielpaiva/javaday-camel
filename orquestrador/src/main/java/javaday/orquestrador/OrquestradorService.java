package javaday.orquestrador;

import lombok.AllArgsConstructor;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrquestradorService {

    private final ProducerTemplate producerTemplate;

    public String chamarRotaCamel(){
        return producerTemplate.requestBody("direct:rotaInicial", null, String.class);
    }

}
