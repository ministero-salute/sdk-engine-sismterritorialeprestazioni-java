package it.mds.sdk.flusso.sism.territoriale;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"it.mds.sdk.flusso.sism.territoriale.controller", "it.mds.sdk.flusso.sism.territoriale", "it.mds.sdk.rest.persistence.entity",
        "it.mds.sdk.libreriaregole.validator",
        "it.mds.sdk.flusso.sism.territoriale.service", "it.mds.sdk.flusso.sism.territoriale.tracciato",
        "it.mds.sdk.gestoreesiti", "it.mds.sdk.flusso.sism.territoriale.parser.regole", "it.mds.sdk.flusso.sism.territoriale.parser.regole.conf",
        "it.mds.sdk.connettoremds"})

@OpenAPIDefinition(info = @Info(title = "SDK Ministero Della Salute - Flusso PST", version = "0.0.5-SNAPSHOT", description = "Flusso Sism Territoriale"))
public class FlussoSismTerritoriale {
    public static void main(String[] args) {
        SpringApplication.run(FlussoSismTerritoriale.class, args);
    }

}
