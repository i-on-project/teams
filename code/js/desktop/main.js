'use strict'

const { app, BrowserWindow } = require('electron')

/**
 * Defining window
 */
const createWindow = () => {
    const win = new BrowserWindow({
        width: 800,
        height: 600
    })

    win.loadFile('./views/test.html')
}

/**
 * Creating window
 */
app.whenReady().then(() => {
    createWindow()

    app.on('activate', () => {
        if (BrowserWindow.getAllWindows().length === 0) createWindow()
      })
})

/**
 * Listener to stop app on windows when all windows are closed
 */
app.on('window-all-closed', () => {
    if (process.platform !== 'darwin') app.quit()
})