package pt.isel.ion.teams.tags

import pt.isel.ion.teams.common.Uris
import pt.isel.ion.teams.common.siren.*
import pt.isel.ion.teams.deliveries.DeliveryDbRead

fun CollectionModel.toTagSirenObject(
    tagsList: List<TagCompactOutputModel>,
    orgId: Int,
    classId: Int,
    teamId: Int,
    repoId: Int
): SirenEntity<CollectionModel> {
    val list = if (tagsList.size > this.pageSize) tagsList.subList(0, this.pageSize) else tagsList
    val pageSize = this.pageSize
    this.pageSize = list.size

    return SirenEntity(
        properties = this,
        clazz = listOf(SirenClasses.COLLECTION, SirenClasses.TAG),
        entities = list.map {
            EmbeddedEntity(
                properties = it,
                clazz = listOf(SirenClasses.TAG),
                rel = listOf(SirenRelations.ITEM),
                links = listOf(selfLink(Uris.Tags.Tag.make(orgId, classId, teamId, repoId, it.id)))
            )
        },
        links = listOfNotNull(
            selfLink(Uris.Tags.make(orgId, classId, teamId, repoId)),
            if (tagsList.size > pageSize)
                nextLink(Uris.Tags.makePage(pageIndex + 1, pageSize, orgId, classId, teamId, repoId))
            else null,
            if (pageIndex > 0)
                prevLink(Uris.Tags.makePage(pageIndex - 1, pageSize, orgId, classId, teamId, repoId))
            else null,
            homeLink(),
            SirenLink(SirenRelations.REPO, Uris.Repos.Repo.make(orgId, classId, teamId, repoId)),
            logoutLink()
        )
    )
}

fun TagOutputModel.toSirenObject(
    orgId: Int,
    classId: Int,
    teamId: Int,
    repoId: Int,
    delivery: DeliveryDbRead
) = SirenEntity(
    properties = this,
    clazz = listOf(SirenClasses.TAG),
    links = listOf(
        selfLink(Uris.Tags.Tag.make(orgId, classId, teamId, repoId, id)),
        homeLink(),
        SirenLink(SirenRelations.TAGS, Uris.Tags.make(orgId, classId, teamId, repoId)),
        SirenLink(SirenRelations.DELIVERY, Uris.Deliveries.Delivery.make(orgId, classId, delivery.assId, delivery.id)),
        logoutLink()
    )
)