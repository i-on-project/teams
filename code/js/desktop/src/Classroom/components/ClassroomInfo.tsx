import * as React from 'react';
import { Button, Container, Divider, Grid, Header, Loader, Segment } from 'semantic-ui-react';
import { ActionsSegment } from '../../common/components/ActionsSegment';
import { Fetch } from '../../common/components/fetch';
import {  Collection, Entity, Resource } from '../../common/types/siren';
import { makeInviteCodes } from '../../common/Uris';

/**
 * Electron IPC API declaration to be used in this file.
 */
declare const electron: {
    clipboardApi: {
        copy: (value: string) => undefined
    }
}

/**
 * Function used to display the relevant information about a classroom.
 */
export function ClassroomInfo({ resource, orgId, classId }: { resource: Resource, orgId: number, classId: number }) {


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
            <Grid.Row columns={1}>
                <Divider />
                <Container>
                <Header as={'h2'}>Invite Codes</Header>
                    <Fetch
                        url={`/api${makeInviteCodes(orgId, classId)}`}
                        renderBegin={() => <p>Waiting for URL...</p>}
                        renderOk={(payload: Collection) =>
                            <Segment.Group>
                                {
                                    payload.entities.map((entity: Entity) =>
                                        <Segment key={entity.properties.code}>
                                            {entity.properties.code}
                                            <Button size='mini' floated='right' color='blue' onClick={() => { electron.clipboardApi.copy(entity.properties.code) }}>Copy</Button>
                                        </Segment>
                                    )
                                }
                            </Segment.Group>
                        }
                        renderLoading={() => <Loader />}
                    />
                </Container>
            </Grid.Row>
        </Grid >
    )
}