package pt.isel.ion.teams.common.siren

/**
 * Model used for paging in the siren responses.
 */
data class CollectionModel(
    val pageIndex: Int,
    var pageSize: Int
)
