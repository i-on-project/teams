import { Action, Collection } from "../../common/types/siren";
import { Comment, Divider, Form } from "semantic-ui-react";
import * as React from "react";
import { DefaultForm, DefaultModal } from "../../common/components/DefaultForm";

/**
 * Function used to display the notes of a team as comments (a type of information structure provuided by semantic ui)
 */
export function NotesAsComments({ collection }: { collection: Collection }) {

    return (
        <React.Fragment>
            <Comment.Group>
                <h3>Notes</h3>
                {
                    collection.entities.map((entity) =>
                        <DefaultModal
                            trigger={
                                <Comment key={entity.properties.id}>
                                    <Comment.Content>
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
            <Divider />
            {
                collection.actions.map((action: Action) =>
                    <DefaultForm action={action} key={action.name}></DefaultForm>
                )
            }
        </React.Fragment>
    )
}