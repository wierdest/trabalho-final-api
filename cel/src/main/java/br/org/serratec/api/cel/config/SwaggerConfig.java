package br.org.serratec.api.cel.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
	@Value("${ecommerce.swagger.dev-url}")
	private String devUrl;
	
	@Bean
	public OpenAPI myOpenAPI() {
		Server devServer = new Server();
		devServer.setUrl(devUrl);
		devServer.setDescription("Server URL - Ambiente Desenvolvimento");
		
		Contact contact = new Contact();
		contact.setName("GRUPO 2 ");
		contact.setEmail("andrlzpt@protonmail.com");
		contact.setUrl("https://github.com/wierdest/trabalho-final-api/");
		
		License license = new License()
				.name("Apache license version 2.0")
				.url("https://www.apache.org/license/LICENSE-2.0");
		
		Info info = new Info()
				.title("Documentação API - ECOMMERCE")
				.version("1.0.0")
				.contact(contact)
				.license(license)
				.termsOfService("https://www.example.com/terms");
		
		
		return new OpenAPI().info(info).servers(List.of(devServer));
				
		
	}
	
	@SuppressWarnings("deprecation")
	@Bean
    public Docket api() {
        List<springfox.documentation.service.Parameter> globalParameters = new ArrayList<>();
        globalParameters.add(new ParameterBuilder()
                .name("page")
                .description("Page number")
                .modelRef(new ModelRef("int"))
                .parameterType("query")
                .required(false)
                .defaultValue("2")
                .build());
        globalParameters.add(new ParameterBuilder()
                .name("size")
                .description("Page size")
                .modelRef(new ModelRef("int"))
                .parameterType("query")
                .required(false)
                .defaultValue("20")
                .build());
        globalParameters.add(new ParameterBuilder()
                .name("sort")
                .description("Sort criteria")
                .modelRef(new ModelRef("string"))
                .parameterType("query")
                .required(false)
                .build());

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.org.serratec.api.cel.controller"))
                .build()
                .globalOperationParameters(globalParameters);
    }
}