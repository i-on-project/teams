package pt.isel.ion.teams.deliveries

import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component
import pt.isel.ion.teams.common.errors.sqlExceptionHandler

/**
 * Service for the Deliveries resource. The service is responsible for implementing business logic regarding its
 * resource, in practical terms this is, among other things, accessing data through the methods defined in the resource
 * DAO.
 */
@Component
class DeliveriesService(val jdbi: Jdbi) {

    fun getAllDeliveriesOfAssignment(pageSize: Int, pageIndex: Int, assId: Int) =
        sqlExceptionHandler {
            jdbi
                .onDemand(DeliveriesDAO::class.java)
                .getAllDeliveriesByAssignment(pageSize + 1, pageIndex * pageSize, assId)
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