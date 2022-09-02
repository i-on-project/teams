package pt.isel.ion.teams.authentication

import com.sendgrid.Method
import com.sendgrid.Request
import com.sendgrid.Response
import com.sendgrid.SendGrid
import com.sendgrid.helpers.mail.Mail
import com.sendgrid.helpers.mail.objects.Content
import com.sendgrid.helpers.mail.objects.Email
import org.springframework.stereotype.Component
import java.io.IOException

@Component
class EmailService {
    companion object {
        val SENDGRID_API_KEY = SendGrid(System.getenv("SENDGRID_API_KEY"))
        val FROM = Email("ion.teams@outlook.pt")
        const val SUBJECT = "i-on Teams Account Verification"
        const val CONTENT_TYPE = "text/plain"
        const val ENDPOINT = "mail/send"
    }

    fun sendVerificationEmail(name: String, email: String, verificationId: String): Response {

        val to = Email(email)
        val content = Content(CONTENT_TYPE, "Welcome to i-on Teams, $name! \nPlease verify your identity through the following link: https://ion-teams-service.herokuapp.com/auth/verify/$verificationId")
        val mail = Mail(FROM, SUBJECT, to, content);
        val request = Request()
        try {
            request.method = Method.POST
            request.endpoint = ENDPOINT
            request.body = mail.build()

            return SENDGRID_API_KEY.api(request)
        } catch (ex: IOException) {
            throw ex
        }
    }
}