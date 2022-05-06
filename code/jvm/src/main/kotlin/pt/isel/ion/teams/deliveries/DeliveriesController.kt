package pt.isel.ion.teams.deliveries

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.isel.daw.project.common.siren.CollectionModel
import pt.isel.daw.project.common.siren.SIREN_MEDIA_TYPE
import pt.isel.ion.teams.common.Uris
import pt.isel.ion.teams.tags.TagsService

@RestController
@RequestMapping(Uris.Deliveries.MAIN_PATH)
class DeliveriesController(
    val deliveriesService: DeliveriesService,
    val tagsService: TagsService
) {

    @GetMapping
    fun getAllDeliveriesOfAssignment(
        @PathVariable orgId: Int,
        @PathVariable classId: Int,
        @PathVariable assId: Int,
        @RequestParam(defaultValue = "0") pageIndex: Int,
        @RequestParam(defaultValue = "10") pageSize: Int
    ) {
        ResponseEntity
            .ok()
            .contentType(MediaType.parseMediaType(SIREN_MEDIA_TYPE))
            .body(
                CollectionModel(pageIndex, pageSize).toDeliveriesSirenObject(
                    deliveriesService.getAllDeliveriesOfAssignment(pageSize, pageIndex, assId)
                        .map { it.toCompactOutput() },
                    orgId,
                    classId,
                    assId
                )
            )
    }

    @GetMapping(Uris.Deliveries.Delivery.PATH)
    fun getDelivery(
        @PathVariable orgId: Int,
        @PathVariable classId: Int,
        @PathVariable assId: Int,
        @PathVariable deliveryId: Int,
    ): ResponseEntity<Any> {
        val team = deliveriesService.getDelivery(deliveryId).toOutput()
        val tags = tagsService.getAllTagsWithRepoAndView(deliveryId)

        //TODO Detect if user is student or teacher

        return ResponseEntity
            .ok()
            .contentType(MediaType.parseMediaType(SIREN_MEDIA_TYPE))
            .body(team.toTeacherSirenObject(tags, orgId, classId, assId))
    }

    @PostMapping
    fun createDelivery(
        @RequestBody delivery: DeliveryInputModel,
        @PathVariable orgId: Int,
        @PathVariable classId: Int,
        @PathVariable assId: Int,
    ): ResponseEntity<Any> {
        val createdDelivery = deliveriesService.createDelivery(delivery.toDb(classId))

        return ResponseEntity
            .created(Uris.Deliveries.Delivery.make(orgId, classId, assId, createdDelivery.id))
            .contentType(MediaType.APPLICATION_JSON)
            .body(createdDelivery)
    }


    @PutMapping(Uris.Deliveries.Delivery.PATH)
    fun updateDelivery(
        @RequestBody delivery: DeliveryUpdateModel,
        @PathVariable orgId: Int,
        @PathVariable classId: Int,
        @PathVariable assId: Int,
        @PathVariable deliveryId: Int,
    ) = ResponseEntity
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(deliveriesService.updateDelivery(delivery.toDb(deliveryId)).toOutput())

    @DeleteMapping(Uris.Deliveries.Delivery.PATH)
    fun deleteDelivery(@PathVariable deliveryId: Int): ResponseEntity<Any> {
        deliveriesService.deleteDelivery(deliveryId)

        return ResponseEntity
            .ok()
            .body(null)
    }
}