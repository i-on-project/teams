package pt.isel.ion.teams.notes

import pt.isel.daw.project.common.siren.*
import pt.isel.ion.teams.common.Uris

fun CollectionModel.toNotesSirenObject(
    notesList: List<NotesCompactOutputModel>,
    orgId: Int,
    classId: Int,
    teamId: Int
): SirenEntity<CollectionModel> {
    val list = if (notesList.size > this.pageSize) notesList.subList(0, this.pageSize) else notesList
    val pageSize = this.pageSize
    this.pageSize = list.size

    return SirenEntity(
        properties = this,
        clazz = listOf(SirenClasses.COLLECTION, SirenClasses.NOTE),
        entities = list.map {
            EmbeddedEntity(
                properties = it,
                clazz = listOf(SirenClasses.NOTE),
                rel = listOf(SirenRelations.ITEM),
                links = listOf(selfLink(Uris.Notes.Note.make(orgId, classId, teamId, it.id)))
            )
        },
        links = listOfNotNull(
            selfLink(Uris.Notes.make(orgId, classId, teamId)),
            if (notesList.size > pageSize)
                nextLink(Uris.Notes.make(orgId, classId, teamId))
            else null,
            if (pageIndex > 0)
                prevLink(Uris.Notes.make(orgId, classId, teamId))
            else null,
            SirenLink(SirenRelations.TEAM, Uris.Teams.Team.make(orgId, classId, teamId)),
            homeLink(),
            logoutLink()
        )
    )
}

fun NotesOutputModel.toSirenObject(
    orgId: Int,
    classId: Int,
    teamId: Int
) = SirenEntity(
    properties = this,
    links = listOf(
        selfLink(Uris.Notes.Note.make(orgId, classId, teamId, id)),
        homeLink(),
        SirenLink(SirenRelations.NOTES, Uris.Notes.make(orgId, classId, teamId)),
        logoutLink()
    )
)