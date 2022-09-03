package pt.isel.ion.teams

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

/**
 * Connection string configuration
 */
@ConstructorBinding
@ConfigurationProperties("app")
data class ConfigProperties(
        val dbConnString: String
)