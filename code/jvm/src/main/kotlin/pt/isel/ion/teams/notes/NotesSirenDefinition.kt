package pt.isel.ion.teams.notes

import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import pt.isel.ion.teams.common.Uris
import pt.isel.ion.teams.common.siren.*

/* ******************* RESOURCE COLLECTION RESPONSES ******************** */

/**
 * Siren definition for an note collection response
 * @param notesList List of notes to display
 * @param orgId Note's organization id
 * @param classId Note's classroom id
 * @param teamId Note's assignment id
 */
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

/* ******************* INDIVIDUAL RESOURCE RESPONSES ******************** */

/**
 * Siren definition for a student's delivery resource response
 * @param orgId Delivery's organization id
 * @param classId Delivery's classroom id
 * @param assId Delivery's assignment id
 */
fun NotesOutputModel.toSirenObject(
    orgId: Int,
    classId: Int,
    teamId: Int
) = SirenEntity(
    properties = this,
    clazz = listOf(SirenClasses.NOTE),
    actions = listOf(
        SirenAction(
            name = "update-note",
            title = "Update Note",
            method = HttpMethod.PUT,
            href = Uris.Notes.Note.make(orgId, classId, teamId, id),
            type = MediaType.APPLICATION_JSON,
            fields = listOf(
                SirenAction.Field(name = "description", type = "string"),
            )
        ),
        SirenAction(
            name = "delete-note",
            title = "Delete Note",
            method = HttpMethod.PUT,
            href = Uris.Notes.Note.make(orgId, classId, teamId, id),
        ),
    ),
    links = listOf(
        selfLink(Uris.Notes.Note.make(orgId, classId, teamId, id)),
        homeLink(),
        SirenLink(SirenRelations.NOTES, Uris.Notes.make(orgId, classId, teamId)),
        logoutLink()
    )
)