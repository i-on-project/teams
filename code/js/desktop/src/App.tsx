import * as React from 'react'
import { Outlet, Link } from "react-router-dom";

declare const electron: {
  notificationApi: {
      sendNotification: (notification: { t: string, m: string }) => undefined
  }
}

export default function App() {
  return (
    <div>
      <h1>Hello from react component!</h1>
      <button className='ui simple button' onClick={() => {
        electron.notificationApi.sendNotification({ t: 'SOME TITLE', m: 'YEEEEET!' })
      }}>Notify</button>
    </div>
  )
}