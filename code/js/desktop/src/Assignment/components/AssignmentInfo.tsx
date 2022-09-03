import * as React from 'react';
import { Grid, Header, Segment } from 'semantic-ui-react';
import { ActionsSegment } from '../../common/components/ActionsSegment';
import { Resource } from '../../common/types/siren';

/**
 * Function used to display the relevant assignment information.
 */
export function AssignmentInfo({ resource }: { resource: Resource }) {

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
        </Grid >
    )
}