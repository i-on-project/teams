import * as React from "react"
import { useContext } from "react";
import { Button, Message, Table } from 'semantic-ui-react';
import { useLoggedInState } from "../../common/components/loggedStatus";
import { ChangedContext } from "../../common/components/changedStatus";
import { DefaultTable } from '../../common/components/Table'
import { Action, Entity } from '../../common/types/siren';
import { useServiceLocation } from "../../common/components/ServiceLocationContext";

type MessageState = { hidden: boolean, success: boolean, error: boolean, status: number, message: string }

type MessageAction =
    | { type: 'error', status: number, message: string }
    | { type: 'info', message: string }
    | { type: 'success' }
    | { type: 'reset' }

/**
 * Function represents a reducer for popup messages of information to the user.
 */
function messageReducer(state: MessageState, action: MessageAction): MessageState {
    switch (action.type) {
        case 'success': return { hidden: false, success: true, error: false, status: 200, message: 'Successful!' }

        case 'error': return { hidden: false, success: false, error: true, status: action.status, message: action.message }

        case 'info': return { hidden: false, success: true, error: false, status: null, message: action.message }

        case 'reset': return { hidden: true, success: false, error: false, status: null, message: null }

        default: return state
    }
}

/**
 * Function responsible for building a table of team requests.
 */
export function RequestsTable({ entities, orgName }: { entities: Entity[], orgName: string }) {

    const { setChanged } = useContext(ChangedContext)
    const apiUrl = useServiceLocation().url
    const access_token = useLoggedInState().loggedInState.access_token
    const [messageState, messageDispatch] = React.useReducer(messageReducer,
        { hidden: true, success: false, error: false, status: null, message: null })

    /**
     * Function should be appart of github integration, this is not implemented due to a lack of time.
     */
    function onClickAccept(action: Action, teamName: string) {
        /*
        fetch(` https://api.github.com/orgs/${orgName}/repos`,
            {
                method: 'POST',
                headers: {
                    Authorization: `token ${access_token}`,
                    Accept: "application/vnd.github+json"
                },
                body: JSON.stringify({ name: "project-"+teamName, description: "", visibility: "private", "has_issues":true,"has_projects":true,"has_wiki":true})
            }
        )
            .then((resp) => {
                if (resp.ok) {
                    fetch(` https://api.github.com/orgs/ORG${orgName}/teams`,
                        {
                            method: 'POST',
                            headers: {
                                Authorization: `token ${access_token}`,
                                Accept: "application/vnd.github+json"
                            },
                            body: JSON.stringify({
                                 name: teamName, description: "", permission: "push", 
                                 privacy: "closed", repo_names: [`${orgName}/"project-"+teamName`],
                                 maintainers:["github ids"] 
                                })
                        }
                    ).then((resp) => {
                        if (resp.ok) fetchAction(action)
                    })
                }
            })
            .catch((err) => {
                messageDispatch({ type: 'error', status: err.status, message: err.detail })
            })
        */
    }

    /**
     * Function used to send requests to the service API to either accept or deny now teams.
     */
    function fetchAction(action: Action) {

        fetch(`${apiUrl}${action.href}`, {
            method: action.method,
            headers: {
                'Accept': action.type,
                'Content-Type': action.type
            },
            credentials: 'include'
        })
            .then((resp) => {
                if (resp.ok) {
                    messageDispatch({ type: 'success' })
                    setChanged(true)
                }
            })
            .catch((err) => {
                messageDispatch({ type: 'error', status: err.status, message: err.detail })
            })
    }

    /**
     * Function responsible for build the table rows.
     */
    function rowSpan() {
        const projects = entities.map((entity: Entity) => {
            return {
                id: entity.properties.tid,
                name: entity.properties.teamName,
                actions: entity.actions,
                link: entity.links[0].href
            }
        })

        return projects.map(item =>
            <Table.Row key={item.id} >
                <Table.Cell key={item.id + '_name'}> {item.name} </Table.Cell>
                <Table.Cell textAlign="right" key={item.id + '_buttons'}>
                    <Button.Group>
                        <Button positive onClick={() => fetchAction(item.actions.find((it) => it.name == "accept-request"))} key={'Accept_' + item.id}>Accept</Button>
                        <Button negative onClick={() => fetchAction(item.actions.find((it) => it.name == "decline-request"))} key={'Decline_' + item.id}>Decline</Button>
                    </Button.Group>
                </Table.Cell>
            </Table.Row>
        )
    }

    return (
        <React.Fragment>
            <Message
                hidden={messageState.hidden}
                positive={messageState.success}
                error={messageState.error}
                header={messageState.status}
                content={messageState.message}
                onDismiss={() => { messageDispatch({ type: 'reset' }) }}
            />
            <DefaultTable propNames={["Name", ""]}>{rowSpan()}</DefaultTable>
        </React.Fragment>
    )
}