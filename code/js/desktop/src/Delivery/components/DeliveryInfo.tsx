import * as React from 'react';
import { Grid, Header, Segment } from 'semantic-ui-react';
import { ActionsSegment } from '../../common/components/ActionsSegment';
import { Resource } from '../../common/types/siren';

/**
 * Function is responsible for displaying the informatuion of a delivery.
 */
export function DeliveryInfo({ resource }: { resource: Resource }) {

    return (
        <Grid divided='vertically' columns='equal'>
            <Grid.Row columns={2}>
                <Grid.Column>
                    <Segment>
                        <Header as='h3'>Name: {resource.properties.name}</Header>
                        Date: {resource.properties.date}
                    </Segment>
                </Grid.Column>
                <Grid.Column width={3}>
                    <ActionsSegment actions={resource.actions}></ActionsSegment>
                </Grid.Column>
            </Grid.Row>
        </Grid >
    )
}