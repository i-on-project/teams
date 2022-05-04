package pt.isel.ion.teams.deliveries

import java.util.*

/**
 * For internal use only.
 */
data class DeliveryDbRead(
    val id: Int,
    val assId: Int,
    val name: Int,
    val date: Date,
)

data class DeliveryDbWrite(
    val assId: Int,
    val name: Int,
    val date: Date
)

data class DeliveryDbUpdate(
    val id: Int,
    val name: Int?,
    val date: Date?
)

/**
 * For external use only.
 */

data class DeliveryOutputModel(
    val id: Int,
    val date: Date,
    val name: Int
)

data class DeliveryCompactOutputModel(
    val id: Int,
    val date: Date,
)

data class DeliveryInputModel(
    val assId: Int,
    val name: Int,
    val date: Date
)

data class DeliveryUpdateModel(
    val name: Int?,
    val date: Date?
)

/**
 * Functions to transition from external to internal, or vice-versa.
 */

fun DeliveryInputModel.toDb(assId: Int) =
    DeliveryDbWrite(assId, this.name, this.date)
fun DeliveryUpdateModel.toDb(id: Int) = DeliveryDbUpdate(id, this.name, this.date)
fun DeliveryDbRead.toOutput() = DeliveryOutputModel(id, date, name)
fun DeliveryDbRead.toCompactOutput() = DeliveryCompactOutputModel(this.id, this.date)