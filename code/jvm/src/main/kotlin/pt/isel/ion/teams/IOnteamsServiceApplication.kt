package pt.isel.ion.teams

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.postgres.PostgresPlugin
import org.jdbi.v3.sqlobject.SqlObjectPlugin
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.postgresql.ds.PGSimpleDataSource
import javax.sql.DataSource

@SpringBootApplication
@ConfigurationPropertiesScan
class IOnteamsServiceApplication(
		private val configProperties: ConfigProperties
) {
    @Bean
    fun dataSource() = PGSimpleDataSource().apply {
        setURL(configProperties.dbConnString)
    }

    @Bean
    fun jdbi(dataSource: DataSource) = Jdbi.create(dataSource).apply {
        installPlugin(KotlinPlugin())
        installPlugin(PostgresPlugin())
        installPlugin(SqlObjectPlugin())
    }
}

fun main(args: Array<String>) {
    runApplication<IOnteamsServiceApplication>(*args)
}
