package uz.kapitalbank.bus.configs;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;
/**
 * @author Rustam Khalmatov
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {
@Bean
public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
            .globalOperationParameters(getGlobalHeadersParameters())
            .select()
            .apis(RequestHandlerSelectors.basePackage("uz.kapitalbank.bus"))
            .paths(Predicates.not(PathSelectors.regex("/error")))
            .build()
            .apiInfo(metaData()).enable(true);
}

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("Kapitalbank integration bus")
                .description("Kapitalbank integration bus api documentation")
                .version("1.0")
                .license("Apache License version 2.0")
                .contact(new Contact("Rustam Khalmatov", "rustam.org"
                        , "rusya.khalmatov@gmail.com"))
                .build();
    }

    private List<Parameter> getGlobalHeadersParameters() {
        Parameter tokenParameter = new ParameterBuilder()
                .name("Authorization")
                .description("Authorization token")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();

        return Arrays.asList(tokenParameter);
    }
}
