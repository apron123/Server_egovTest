package egovframework.cbm.config.swagger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class OpenApiConfig {

	private static final String API_NAME = "CBM+ standard platform Project API";
	private static final String API_VERSION = "4.2.0";
	private static final String API_DESCRIPTION = "CBM+ 표준플랫폼 프로젝트 명세서";

	@Bean
	public ModelResolver modelResolver(ObjectMapper objectMapper) {
		return new ModelResolver(objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE));
	}

	@Bean
	public OpenAPI api() {
		Schema<?> searchMap = new Schema<Map<String, String>>()
				.addProperty("pageIndex", new StringSchema().example("1"))
				.addProperty("searchCnd", new StringSchema().example("0"))
				.addProperty("searchWrd", new StringSchema().example(""));

		Schema<?> updateSttusCodeMap = new Schema<Map<String, String>>()
				.addProperty("userId", new StringSchema().example("admin"))
				.addProperty("sttusCode", new StringSchema().example("P"));

		return new OpenAPI()
				.info(new Info().title(API_NAME)
						.description(API_DESCRIPTION)
						.version(API_VERSION)
						.license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0")))
				.components(new Components()
						.addSchemas("searchMap", searchMap)
						.addSchemas("updateSttusCodeMap", updateSttusCodeMap)
						.addSecuritySchemes("Bearer Token",
								new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer")
										.bearerFormat("JWT")))
				.addSecurityItem(new SecurityRequirement().addList("Bearer Token"));
		// .externalDocs(new ExternalDocumentation()
		// .description("Wiki Documentation")
		// .url("https://github.com/eGovFramework/egovframe-template-simple-backend/wiki"));
	}
}
