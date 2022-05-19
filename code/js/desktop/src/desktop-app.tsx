import * as React from 'react'
//import electron from 'Electron'

export function DesktopApp() {
    return (
        <div>
            <h1>Hello from react component!</h1>
            <button onClick={() => {
                electron.notificationApi.sendNotification({t: 'SOME TITLE', m: 'YEEEEET!'})
            }}>Notify</button>
        </div>
    )
} 