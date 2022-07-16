import { Collection } from "../../common/types/siren";
import { Comment, Divider, Form } from "semantic-ui-react";
import * as React from "react";
import { DefaultForm, DefaultModal } from "../../common/components/DefaultForm";

export function NotesAsComments({ collection }: { collection: Collection }) {

    return (
        <Comment.Group>
            <h3>Notes</h3>
            {
                collection.entities.map((entity) =>
                    //TODO: get current teacher name
                    <DefaultModal
                        trigger={
                            <Comment key={entity.properties.id}>
                                <Comment.Content>
                                    <Comment.Author as='a'> Teacher </Comment.Author>
                                    <Comment.Metadata>
                                        <div>{entity.properties.date}</div>
                                    </Comment.Metadata>
                                    <Comment.Text>
                                        {entity.properties.description}
                                    </Comment.Text>
                                </Comment.Content>
                            </Comment>
                        }>
                        {
                            entity.actions.map((action) =>
                                <div>
                                    <DefaultForm key={action.name} action={action} divider={false} />
                                    <Divider hidden />
                                </div>
                            )
                        }
                    </DefaultModal>
                )
            }

        </Comment.Group>
        //TODO create notes missing
    )
}