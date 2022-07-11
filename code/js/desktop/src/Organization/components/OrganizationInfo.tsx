import * as React from 'react';
import { Grid, Header, List, ListHeader, ListItem, Popup, Segment } from 'semantic-ui-react';
import { DefaultForm, FormInModal } from '../../common/components/BuildForm';
import { Action, Resource } from '../../common/types/siren';

export function OrganizationInfo({ resource }: { resource: Resource }) {
    return (
        <Grid divided='vertically' columns='equal'>
            <Grid.Row columns={2}>
                <Grid.Column>
                    <Segment>
                        <Header as='h3'>{resource.properties.name}</Header>
                        {resource.properties.description}
                    </Segment>
                </Grid.Column>
                <Grid.Column width={3}>
                    <Segment color="blue">
                        <List>
                            <ListHeader as='h3'>Actions</ListHeader>
                            {
                                resource.actions.map((action: Action) =>
                                    <FormInModal key={action.name} action={action}>
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
        </Grid>
    )
}