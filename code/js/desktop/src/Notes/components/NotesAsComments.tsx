import { Action, Collection } from "../../common/types/siren";
import { Button, Comment, Divider, Form } from "semantic-ui-react";
import * as React from "react";
import { BuildForm, BuildModal } from "../../common/components/BuildForm";
import { useContext } from "react";
import { ChangedContext } from "../../common/components/changedStatus";

export function NotesAsComments({ collection }: { collection: Collection }) {

    return (
        <Comment.Group>
            <h3>Notes</h3>
            {
                collection.entities.map( (entity) =>
                    //TODO <Comment.Author> ... </Comment.Author>
                    <BuildModal  trigger={
                        <Comment key={entity.properties.id}>
                                <Comment.Content>
                                    <Comment.Metadata>
                                        {entity.properties.date}
                                    </Comment.Metadata>
                                    <Comment.Text>
                                        {entity.properties.description}
                                    </Comment.Text>
                                </Comment.Content>
                        </Comment>
                    }>
                    {
                        entity.actions.map( (action) => 
                            <div>
                                <BuildForm key={action.name} action={action} divider={false}/>
                                <Divider hidden/>
                            </div>
                        )
                    }
                    </BuildModal>
                )
            }
            
        </Comment.Group>
//TODO create notes missing
    )
}