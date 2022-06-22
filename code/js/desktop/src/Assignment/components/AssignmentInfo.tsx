import * as React from 'react';
import { Grid, Header, List, ListHeader, ListItem, Segment } from 'semantic-ui-react';
import { BuildFormInModal } from '../../common/components/BuildForm';
import { Action, Resource } from '../../common/types/siren';

export function AssignmentInfo({ resource }: { resource: Resource }) {


    return (
        <Grid divided='vertically' columns='equal'>
            <Grid.Row columns={2}>
                <Grid.Column>
                    <Segment>
                        <Header as='h3'>Assignment: {resource.properties.id}</Header>
                        {resource.properties.description}
                    </Segment>
                </Grid.Column>
                <Grid.Column width={3}>
                    <Segment color="blue">
                        <List>
                            <ListHeader as='h3'>Actions</ListHeader>
                            {
                                resource.actions.map((action: Action) =>
                                    <BuildFormInModal action={action} key={action.name} >
                                        {
                                            <ListItem key={action.name}> {action.title} </ListItem>
                                        }
                                    </BuildFormInModal>
                                )
                            }                            
                        </List>
                </Segment>
            </Grid.Column>
        </Grid.Row>
        </Grid >
    )
}