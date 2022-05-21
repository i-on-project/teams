import * as React from 'react'
import { createRoot } from 'react-dom/client'
import { DesktopApp } from './desktop-app'

const root = createRoot(document.getElementById('root'))
root.render(<DesktopApp/>)
