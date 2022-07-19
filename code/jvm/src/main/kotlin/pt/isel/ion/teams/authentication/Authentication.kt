package pt.isel.ion.teams.authentication

data class ClientToken(
    val access_token: String,
    val scope: String,
    val token_type: String
)