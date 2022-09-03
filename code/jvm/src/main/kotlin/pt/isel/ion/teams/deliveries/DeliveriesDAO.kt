package pt.isel.ion.teams.deliveries

import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

/**
 * Data Access Object for the Delivery resource. This is an interface responsible for defining the methods for
 * accessing data of the given resource.
 */
interface DeliveriesDAO {

    @SqlQuery("SELECT * FROM deliveries_view WHERE assid = :assId LIMIT :limit OFFSET :offset")
    fun getAllDeliveriesByAssignment(
        @Bind("limit") limit: Int,
        @Bind("offset") offset: Int,
        @Bind("assId") assId: Int,
    ): List<DeliveryDbRead>

    @SqlQuery("SELECT * FROM deliveries_view WHERE id = :id")
    fun getDelivery(
        @Bind("id") deliveryId: Int,
    ): DeliveryDbRead

    @SqlUpdate("INSERT INTO deliveries (name,assid, date) VALUES (:name,:assId, :date)")
    @GetGeneratedKeys
    fun createDelivery(@BindBean delivery: DeliveryDbWrite): DeliveryDbRead

    @SqlUpdate("UPDATE deliveries SET  date = coalesce(:date, date),name = coalesce(:name, name) WHERE id = :id")
    @GetGeneratedKeys
    fun updateDelivery(@BindBean delivery: DeliveryDbUpdate): DeliveryDbRead

    @SqlUpdate("UPDATE deliveries SET deleted = B'1' WHERE id = :id")
    fun deleteDelivery(@Bind("id") deliveryId: Int)
}