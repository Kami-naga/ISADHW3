package sjtu.isad.sprincloudgateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SprinCloudGatewayApplication {
//    @Value("${test.uri}")
//    private String uri;
//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
//        return builder.routes()
//                .route(r-> r.path("/test/**").uri(uri)).build();
//    }
    public static void main(String[] args) {
        SpringApplication.run(SprinCloudGatewayApplication.class, args);
    }

}
