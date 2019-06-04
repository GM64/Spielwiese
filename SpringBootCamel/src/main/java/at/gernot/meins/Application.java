package at.gernot.meins;

import javax.ws.rs.core.MediaType;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.ehcache.EhcacheConstants;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ComponentScan(basePackages="at.gernot.meins")
public class Application{

    @Value("${server.port}")
    String serverPort;
    
    @Value("${gernot.api.path}")
    String contextPath;
    
    private static final String EHCACHE_ENDPOINT = "ehcache://dataServiceCache"
            + "?configurationUri=/ehcache.xml"
            + "&keyType=java.lang.Long"
            + "&valueType=java.io.Serializable";
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    


    @Component
    class RestApi extends RouteBuilder {

        @Override
        public void configure() {
            restConfiguration().contextPath(contextPath) //
                .port(serverPort)
                .enableCORS(true)
                .apiContextPath("/api-doc")
                .apiProperty("api.title", "Test REST API")
                .apiProperty("api.version", "v1")
                .apiProperty("cors", "true") // cross-site
                .apiContextRouteId("doc-api")
                .component("servlet")
                .bindingMode(RestBindingMode.json)
                .dataFormatProperty("prettyPrint", "true");
           
            rest("/api/").description("Teste REST Service")
                .id("api-route")
                .get("/{id}")
                .produces(MediaType.APPLICATION_JSON)
                .consumes(MediaType.APPLICATION_JSON)
                .bindingMode(RestBindingMode.json)
                .enableCORS(true)
                .to("direct:remoteService");
            
          
            from("direct:remoteService")
            .log("ID = ${header.id}")
            .setHeader(EhcacheConstants.ACTION, constant(EhcacheConstants.ACTION_GET))
            .setHeader(EhcacheConstants.KEY, header("id").convertTo(Long.class))
            .to(EHCACHE_ENDPOINT)
            .choice()
                .when(header(EhcacheConstants.ACTION_HAS_RESULT).isNotEqualTo("true"))
                    .log("No cache hit. Fetching data from external service")
                    .to("bean:dataService?method=getData(${header.id})")
                    .log("body :${body}")
                    .setHeader(EhcacheConstants.ACTION, constant(EhcacheConstants.ACTION_PUT))
                    .setHeader(EhcacheConstants.KEY, header("id").convertTo(Long.class))
                    .to(EHCACHE_ENDPOINT)
                .otherwise()
                    .log("Cache hit. Returning")
            .endChoice();
        }
    }
}