import * as React from 'react';
import { Grid, Header, List, ListHeader, ListItem, Popup, Segment } from 'semantic-ui-react';
import { BuildForm } from '../../commons/components/BuildForm';
import { Action, Resource } from '../../commons/types/siren';

export type Props = {
    resource: Resource
}

export function OrganizationInfo(props: Props) {
    return (
        <Grid divided='vertically' columns='equal'>
            <Grid.Row columns={2}>
                <Grid.Column>
                    <Segment>
                        <Header as='h3'>{props.resource.properties.name}</Header>
                        {props.resource.properties.description}
                    </Segment>
                </Grid.Column>
                <Grid.Column width={3}>
                    <Segment color="blue">
                        <List>
                            <ListHeader as='h3'>Actions</ListHeader>
                            {
                                props.resource.actions.map( (action: Action) =>
                                    <Popup trigger={<ListItem> {action.title} </ListItem>} key={action.name} flowing hoverable>
                                        <Popup.Content>
                                            <BuildForm action={action}></BuildForm>
                                        </Popup.Content>
                                    </Popup>
                                )
                            }
                        </List>
                    </Segment>
                </Grid.Column>
            </Grid.Row> 
        </Grid>
    )
}