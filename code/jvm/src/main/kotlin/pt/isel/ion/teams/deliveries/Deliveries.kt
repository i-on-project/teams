package pt.isel.ion.teams.deliveries

import java.sql.Timestamp

/**
 * For internal use only.
 */
data class DeliveryDbRead(
    val id: Int,
    val assId: Int,
    val name: String,
    val date: String
)

data class DeliveryDbWrite(
    val assId: Int,
    val name: String,
    val date: Timestamp
)

data class DeliveryDbUpdate(
    val id: Int,
    val name: String?,
    val date: Timestamp?
)

/**
 * For external use only.
 */

data class DeliveryOutputModel(
    val id: Int,
    val name: String,
    val date: String
)

data class DeliveryCompactOutputModel(
    val id: Int,
    val name: String,
    val date: String
)

data class DeliveryInputModel(
    val name: String,
    val date: String
)

data class DeliveryUpdateModel(
    val name: String?,
    val date: String?
)

/**
 * Functions to transition from external to internal, or vice-versa.
 */
fun DeliveryInputModel.toDb(assId: Int) = DeliveryDbWrite(assId, this.name, Timestamp.valueOf(this.date))


fun DeliveryUpdateModel.toDb(id: Int) = DeliveryDbUpdate(id, this.name,  Timestamp.valueOf(this.date))

fun DeliveryDbRead.toOutput() = DeliveryOutputModel(id, name, date)

fun DeliveryDbRead.toCompactOutput() = DeliveryCompactOutputModel(this.id, this.name, this.date)