package pt.isel.ion.teams

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("app")
data class ConfigProperties(
        val dbConnString: String
)