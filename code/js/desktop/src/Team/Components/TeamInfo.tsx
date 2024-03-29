import * as React from 'react';
import { Grid, List, ListHeader, ListItem, Segment } from 'semantic-ui-react';
import { FormInModal } from '../../common/components/DefaultForm';
import { Action, Resource } from '../../common/types/siren';
import { SimpleStudentsTable } from '../../Students/components/SimpleStudentsTable';

/**
 * Function responsible for displaying the team information.
 */
export function TeamInfo({ resource }: { resource: Resource }) {

    return (
        <Grid divided='vertically' columns='equal'>
            <Grid.Row columns={2}>
                <Grid.Column>
                    <h2>{resource.properties.name}</h2>
                    <h3>Students in this team:</h3>
                    <SimpleStudentsTable entities={resource.entities} />
                </Grid.Column>
                <Grid.Column width={3}>
                    <Segment color="blue">
                        <List>
                            <ListHeader as='h3'>Actions</ListHeader>
                            {
                                resource.actions.map((action: Action) =>
                                    <FormInModal action={action} key={action.name} >
                                        {
                                            <ListItem key={action.name}> {action.title} </ListItem>
                                        }
                                    </FormInModal>
                                )
                            }
                        </List>
                    </Segment>
                </Grid.Column>
            </Grid.Row>
        </Grid >
    )
}

