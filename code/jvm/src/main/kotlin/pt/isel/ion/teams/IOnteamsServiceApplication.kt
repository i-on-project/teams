    package pt.isel.ion.teams

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.postgres.PostgresPlugin
import org.jdbi.v3.sqlobject.SqlObjectPlugin
import org.postgresql.ds.PGSimpleDataSource
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import javax.sql.DataSource

@SpringBootApplication
@ConfigurationPropertiesScan
class IOnteamsServiceApplication(
		private val configProperties: ConfigProperties
) {
    //data source configuration
    @Bean
    fun dataSource() = PGSimpleDataSource().apply {
        setURL(configProperties.dbConnString)
    }

    //jdbi configuration
    @Bean
    fun jdbi(dataSource: DataSource) = Jdbi.create(dataSource).apply {
        installPlugin(KotlinPlugin())
        installPlugin(PostgresPlugin())
        installPlugin(SqlObjectPlugin())
    }
}

fun main(args: Array<String>) {
    //Verification if an environment variable exists for the PORT, if not use default port 8080
    runApplication<IOnteamsServiceApplication>(*args) {
        setDefaultProperties(mapOf("server.port" to (System.getenv("PORT") ?: "8080")))
    }
}

@Configuration
@EnableWebMvc
class WebConfig : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        //Allows Cross Origin With the Web Application
        registry
            .addMapping("/**")
            .allowedOrigins("http://localhost:3000", "http://127.0.0.1:3000","https://ion-teams.herokuapp.com")
            .allowCredentials(true)
    }
}