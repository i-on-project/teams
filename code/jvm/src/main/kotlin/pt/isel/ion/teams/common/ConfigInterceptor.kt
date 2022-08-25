package pt.isel.ion.teams.common

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * Configuration of interceptor and routes that an interceptor will run.
 */
@Configuration
class ConfigInterceptor: WebMvcConfigurer {
    @Autowired
    private val interceptor: AuthInterceptor? = null
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry
            .addInterceptor(interceptor as HandlerInterceptor)
            .excludePathPatterns(Uris.Login.PATH,Uris.Register.PATH)
    }
}