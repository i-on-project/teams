import * as React from 'react';
import { useContext, useState } from 'react'
import { Button, Container, Divider, Dropdown, Form, Header, Message, Modal } from "semantic-ui-react";
import { Action, Field } from "../types/siren";
import { ChangedContext } from './changedStatus';

interface FormData {
    [str: string]: any
}

export function DefaultForm({ action, divider = false }: { action: Action, divider?: boolean }) {

    const { setChanged } = useContext(ChangedContext)
    const [state, setState] = useState<FormData>({})

    function onSubmit(event: any) {
        event.preventDefault();

        fetch(`http://localhost:8080${action.href}`, {
            method: action.method,
            headers: {
                'Accept': action.type,
                'Content-Type': action.type
            },
            credentials: 'include',
            body: JSON.stringify(state)
        })
            .then(() => {
                setChanged(true)
            })
    }

    return (
        <div>
            <Header as='h2'>{action.title}</Header>
            <Form onSubmit={(event) => onSubmit(event)}>
                {buildFields()}
                <Button color='blue' fluid type='submit' onClick={(event) => onSubmit(event)}>{action.title}</Button>
                {
                    (divider) ? <Divider hidden></Divider> : ''
                }
            </Form>
        </div>
    )

    function buildFields() {

        function defaultFormField(field: Field) {
            return (
                <React.Fragment>
                <Form.Field key={field.name} required>
                    <label>{field.name}</label>
                    <input placeholder={`Write ${field.name} here...`} onChange={(event) => setState({ ...state, [field.name]: event.target.value })} />
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

