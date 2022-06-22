import * as React from 'react';
import { Button, Divider, Grid, Header, Icon, List, ListHeader, ListItem, Loader, Popup, Segment } from 'semantic-ui-react';
import { BuildForm, BuildFormInModal, BuildModal } from '../../common/components/BuildForm';
import { Fetch } from '../../common/components/fetch';
import { Action, Collection, Entity, Resource } from '../../common/types/siren';
import { makeInviteLinks } from '../../common/Uris';

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
                            <Divider />
                            <BuildModal key={'invite-links'} trigger={<Button fluid color='blue' >Invite Links</Button>}>
                                {
                                    <Fetch
                                        url={`/api${makeInviteLinks(orgId, classId)}`}
                                        renderBegin={() => <p>Waiting for URL...</p>}
                                        renderOk={(payload: Collection) =>
                                             <Segment.Group>
                                                {
                                                    payload.entities.map( (entity: Entity) => 
                                                        <Segment key={entity.properties.code}>
                                                            {entity.properties.code}
                                                            <Button size='mini' floated='right' inverted color='blue'>Copy</Button>
                                                        </Segment>
                                                    )
                                                }
                                             </Segment.Group>
                                        }
                                        renderLoading={() => <Loader />}
                                    />
                                }
                            </BuildModal>
                        </List>
                </Segment>
            </Grid.Column>
        </Grid.Row>
        </Grid >
    )
}