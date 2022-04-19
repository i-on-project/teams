package pt.isel.ion.teams.tags

import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import pt.isel.ion.teams.organizations.OrganizationDbRead

interface TagsDAO {

    @SqlQuery("SELECT * FROM tags WHERE teamid=:teamId")
    fun getAllTags(@Bind("teamId") teamId: Int): List<TagDbRead>

    @SqlUpdate("INSERT INTO tags (name,date,delId,teamId) VALUES (:name,:date,:delId,:teamId)")
    @GetGeneratedKeys
    fun createTag(@BindBean tag: TagDbWrite): OrganizationDbRead

    @SqlUpdate("UPDATE tags SET name=:name,date=:date,delId=:delId,teamId=:teamId WHERE id=:id")
    @GetGeneratedKeys
    fun updateTag(@BindBean tag: TagDbUpdate): Int
}