import { stat } from "original-fs"
import * as React from "react"
import { useReducer, useEffect} from "react"
import { useChangedState } from "../../../out/ion-teams-desktop-darwin-arm64/ion-teams-desktop.app/Contents/Resources/app/src/common/components/changedStatus"
import { ChangedContext } from "./changedStatus"
import { Error, ErrorNOk } from "./error"

const apiUrl: string = 'https://ion-teams-service.herokuapp.com'

export type FetchProps = {
    url: string,
    renderBegin: () => React.ReactElement,
    renderLoading: () => React.ReactElement,
    renderOk: (payload: any) => React.ReactElement,
    renderNok?: (response: Response) => React.ReactElement,
    renderError?: (error: Error) => React.ReactElement,
}

type State =
    | { state: 'begin' }
    | { state: 'loading', url: string }
    | { state: 'response-received', response: Response }
    | { state: 'payload-receive', payload: any }
    | { state: 'error-receive', error: Error }

type Action =
    | { type: 'fetch-started', url: string }
    | { type: 'error', error: Error }
    | { type: 'response', response: any}
    | { type: 'payload', payload: any }
    | { type: 'reset' }

function reducer(state: State, action: Action): State {
    switch (action.type) {
        case 'fetch-started': return { state: 'loading', url: action.url }

        case 'error': return { state: 'error-receive', error: action.error }

        case 'response': return { state: 'response-received', response: action.response }

        case 'payload': return { state: 'payload-receive', payload: action.payload }

        case 'reset': return { state: 'begin' }
    }
}

async function doFetch(uri: string, dispatcher: (action: Action) => void, signal: AbortSignal) {
    const url = buildUrl(uri)

    if (url === '') {
        dispatcher({ type: 'reset' })
        return
    }
    dispatcher({ type: 'fetch-started', url: url })
    try {
        const response = await fetch(url, { signal, credentials: 'include' })
        dispatcher({ type: 'response', response: response })
        if (response.ok) {
            const payload = await response.json()
            dispatcher({ type: 'payload', payload: payload })
        }
    } catch (error: any) {
        console.log(`error '${error.message}'`)
        dispatcher({ type: 'error', error: error })
    }
}

function fetchEffect(url: string, dispatcher: (action: Action) => void) {
    let isCancelled = false
    const abortController = new AbortController()
    const filteredDispatcher = (action: Action) => {
        if (!isCancelled) {
            dispatcher(action)
        } else {
            //Actions needed when action is ignores
        }
    }
    doFetch(url, filteredDispatcher, abortController.signal)
    return () => {
        // cancel
        isCancelled = true
        abortController.abort()
    }
}

function buildUrl(uri: string): string {
    return apiUrl + uri
}

export function Fetch(props: FetchProps) {

    const { changed } = React.useContext(ChangedContext)
    const [state, dispatcher] = useReducer(reducer, { state: 'begin' })
    useEffect(() => fetchEffect(props.url, dispatcher), [props.url, dispatcher, changed])

    useEffect(() => {
        console.log("Changed status update")
    }, [changed])


    switch (state.state) {
        case 'begin': return props.renderBegin()

        case 'loading': return props.renderLoading()

        case 'error-receive': return props.renderError ? props.renderError(state.error) : <Error error={state.error} />

        case 'response-received': return state.response.ok ? props.renderLoading() : (props.renderNok ? props.renderNok(state.response) : <ErrorNOk resp={state.response} />)

        case 'payload-receive': return props.renderOk(state.payload) 
    }
}