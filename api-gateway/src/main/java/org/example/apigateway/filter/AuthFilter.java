package org.example.apigateway.filter;

import org.apache.http.HttpHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;



@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    private final WebClient.Builder webClientBuilder;

    public AuthFilter(WebClient.Builder webClientBuilder){
        super(Config.class);
        this.webClientBuilder=webClientBuilder;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                throw new RuntimeException("missing token");
            }
            String token = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0).substring(7);

            return webClientBuilder.build()
                    .get().uri("http://authorization-service/api/validate",uriBuilder ->
                            uriBuilder.queryParam("token",token).build())
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .flatMap(isValid -> {
                        if(isValid){
                            return chain.filter(exchange);
                        }else{
                            //throw new runtimeException
                            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                            return exchange.getResponse().setComplete();
                        }
                    });
        }
        );
    }
    public static class Config {
    }
}
