package pt.isel.ion.teams.tags

import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

interface TagsDAO {

    @SqlQuery("SELECT * FROM tags_view WHERE repoid = :repoId LIMIT :limit OFFSET :offset")
    fun getAllTags(
        @Bind("repoId") repoId: Int,
        @Bind("limit") limit: Int,
        @Bind("offset") offset: Int,
    ): List<TagDbRead>

    @SqlQuery("SELECT * FROM tags_view WHERE repoid = :repoId AND id = :id")
    fun getTag(
        @Bind("repoId") repoId: Int,
        @Bind("id") id: Int
    ): TagDbRead

    @SqlUpdate("INSERT INTO tags (name, date, delId, repoid) VALUES (:name, :date, :delId, :repoId)")
    @GetGeneratedKeys
    fun createTag(@BindBean tag: TagDbWrite): TagDbRead

    @SqlUpdate("UPDATE tags SET name = coalesce(:name, name), date = coalesce(:date, date), delId = coalesce(:delId, delid), repoid = coalesce(:repoId, repoid) WHERE id = :id")
    @GetGeneratedKeys
    fun updateTag(@BindBean tag: TagDbUpdate): TagDbRead

    @SqlUpdate("UPDATE tags SET deleted = B'1' WHERE id =: id")
    fun deleteTag(@Bind("id") tagId: Int)
}