import * as React from 'react';
import { useContext, useState } from 'react'
import { Button, Container, Divider, Dropdown, Form, Header, Message, Modal } from "semantic-ui-react";
import { Action, Field } from "../types/siren";
import { ChangedContext } from './changedStatus';
import { useServiceLocation } from './ServiceLocationContext';

interface FormData {
    [str: string]: any
}

declare type Problem = {
    type: string,
    title: string,
    status: number,
    detail: string
}

type MessageState = { hidden: boolean, success: boolean, error: boolean, status: number, message: string }

type MessageAction =
    | { type: 'error', status: number, message: string }
    | { type: 'success' }
    | { type: 'reset' }

function messageReducer(state: MessageState, action: MessageAction): MessageState {
    switch (action.type) {
        case 'success': return { hidden: false, success: true, error: false, status: 200, message: 'Successful!' }

        case 'error': return { hidden: false, success: false, error: true, status: action.status, message: action.message }

        case 'reset': return { hidden: true, success: false, error: false, status: null, message: null }

        default: return state
    }
}

/**
 * Function used to create forms with the information given by the API in the actions. All the forms are created dinamically.
 */
export function DefaultForm({ action, divider = false }: { action: Action, divider?: boolean }) {

    const { setChanged } = useContext(ChangedContext)
    const apiUrl = useServiceLocation().url
    const [state, setState] = useState<FormData>({})
    const [loadindState, setLoading] = React.useState(false)
    const [messageState, messageDispatch] = React.useReducer(messageReducer, { hidden: true, success: false, error: false, status: null, message: null })

    /**
     * onSubmit function for the form.
     */
    function onSubmit(event: any) {
        event.preventDefault();

        setLoading(true)

        fetch(`${apiUrl}${action.href}`, {
            method: action.method,
            headers: {
                'Accept': action.type,
                'Content-Type': action.type
            },
            credentials: 'include',
            body: JSON.stringify(state)
        })
            .then(async (resp: Response) => {
                setChanged(true)

                if (resp.ok) {
                    messageDispatch({ type: 'success' })
                    setLoading(false)
                    return
                }

                throw await resp.json()
            })
            .catch((err: Problem) => {
                setLoading(false)
                messageDispatch({ type: 'error', status: err.status, message: err.detail })
            })
    }

    return (
        <div>
            <Header as='h2'>{action.title}</Header>
            <Message
                hidden={messageState.hidden}
                positive={messageState.success}
                error={messageState.error}
                header={messageState.status}
                content={messageState.message}
                onDismiss={() => { messageDispatch({ type: 'reset' }) }}
            />
            <Form onSubmit={(event) => onSubmit(event)} loading={loadindState}>
                {buildFields()}
                <Button color='blue' fluid type='submit' onClick={(event) => onSubmit(event)}>{action.title}</Button>
                {
                    (divider) ? <Divider hidden></Divider> : ''
                }
            </Form>
        </div>
    )

    /**
     * Function used to build the individual fields of the form
     */
    function buildFields() {

        /**
         * Function used to build default fields. Deafault fields are those that do now represent deletes or do not have any possibilities.
         */
        function defaultFormField(field: Field) {
            return (
                <React.Fragment>
                    <Form.Field key={field.name} required>
                        <label>{field.name}</label>
                        <input placeholder={`Write ${field.name} here...`} type={field.type} onChange={(event) => setState({ ...state, [field.name]: event.target.value })} />
                    </Form.Field>
                    {field.name == "date" && <Message>Date format: YYYY-MM-DD hh:mm:ss</Message>}
                </React.Fragment>
            )
        }

        if (!action.fields) {
            //Action is DELETE
            return (<Header size='tiny' textAlign='center'>You sure?</Header>)
        } else if (action.possibilities) {
            //Action has possibilities
            return action.fields.map((field) =>

                action.possibilities?.find(possibility => field.name === possibility.field) ?
                    <div key={field.name}>
                        <Header size='tiny' >{field.name}</Header>
                        <Dropdown fluid
                            onChange={(event, { value }) => setState({ ...state, [field.name]: value })}
                            options={action.possibilities?.map(possibility => {
                                return {
                                    key: possibility.value,
                                    text: possibility.value,
                                    value: possibility.value
                                }
                            })}
                            placeholder={field.name}
                            selection
                            value={state[0]}
                            style={{ marginBottom: "1rem" }}
                        />
                    </div>
                    :
                    defaultFormField(field)
            )
        }

        //Ordinary action
        return action.fields.map((field) =>
            defaultFormField(field)
        )
    }
}

/**
 * Represents a loading form
 */
export function FormLoading({ name, fieldsName }: { name: string, fieldsName: string[] }) {
    return (
        <Form loading>
            {fieldsName.map((fieldName) =>
                <Form.Field key={fieldName}>
                    <label>fieldName</label>
                    <input placeholder={`Write ${fieldName} here...`} />
                </Form.Field>
            )}
            <Button positive type='submit'>{name}</Button>
        </Form>
    )
}

/**
 * Represents a form inside a modal. A modal is a semantic ui structure that is characterized by a pop up in the page.
 */
export function FormInModal({ children, action }: { children: React.ReactNode, action: Action }) {

    const [open, setOpen] = useState(false)

    return (
        <Modal
            onClose={() => setOpen(false)}
            onOpen={() => setOpen(true)}
            open={open}
            trigger={children}
        >
            <Modal.Content>
                <Modal.Description>
                    {
                        <DefaultForm action={action} divider={false}></DefaultForm>
                    }
                </Modal.Description>
            </Modal.Content>
            <Modal.Actions>
                <Button color='black' onClick={() => setOpen(false)}>
                    Cancel
                </Button>
            </Modal.Actions>
        </Modal>
    )
}

/**
 * Function used to build the modal.
 */
export function DefaultModal({ children, trigger }: { children: React.ReactNode, trigger: React.ReactNode }) {

    const [open, setOpen] = useState(false)

    return (
        <Container>
            <Modal
                onClose={() => setOpen(false)}
                onOpen={() => setOpen(true)}
                open={open}
                trigger={trigger ? trigger : children}
            >
                <Modal.Content>
                    <Modal.Description>
                        {
                            children
                        }
                    </Modal.Description>
                </Modal.Content>
                <Modal.Actions>
                    <Button color='black' onClick={() => setOpen(false)}>
                        Cancel
                    </Button>
                </Modal.Actions>
            </Modal>
        </Container>
    )
}

