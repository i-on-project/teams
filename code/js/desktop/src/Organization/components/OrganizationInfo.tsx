import * as React from 'react';
import { Grid, Header, List, ListHeader, ListItem, Popup, Segment } from 'semantic-ui-react';
import { ActionsSegment } from '../../common/components/ActionsSegment';
import { DefaultForm, FormInModal } from '../../common/components/DefaultForm';
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
                    <ActionsSegment actions={resource.actions}></ActionsSegment>
                </Grid.Column>
            </Grid.Row>
        </Grid>
    )
}