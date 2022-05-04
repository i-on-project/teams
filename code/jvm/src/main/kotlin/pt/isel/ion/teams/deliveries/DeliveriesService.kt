package pt.isel.ion.teams.deliveries

import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component
import pt.isel.daw.project.common.errors.sqlExceptionHandler

@Component
class DeliveriesService(val jdbi: Jdbi) {

    fun getAllDeliveriesOfAssignment(pageSize: Int, pageIndex: Int, assId: Int) =
        sqlExceptionHandler {
            jdbi
                .onDemand(DeliveriesDAO::class.java)
                .getAllDeliveriesByAssignment(pageSize + 1, pageIndex * pageSize, assId)
        }

    fun getDeliveryFromAssignment(deliveryId: Int, assId: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(DeliveriesDAO::class.java).getDeliveryFromAssignment(deliveryId, assId)
        }

    fun getDelivery(deliveryId: Int) =
        sqlExceptionHandler {
            jdbi.onDemand(DeliveriesDAO::class.java).getDelivery(deliveryId)
        }

    fun createDelivery(delivery: DeliveryDbWrite) =
        sqlExceptionHandler {
            jdbi.onDemand(DeliveriesDAO::class.java).createDelivery(delivery)
        }

    fun updateDelivery(delivery: DeliveryDbUpdate) =
        sqlExceptionHandler {
            jdbi.onDemand(DeliveriesDAO::class.java).updateDelivery(delivery)
        }

    fun deleteDelivery(deliveryId: Int) {
        sqlExceptionHandler {
            jdbi.onDemand(DeliveriesDAO::class.java).deleteDelivery(deliveryId)
        }
    }
}