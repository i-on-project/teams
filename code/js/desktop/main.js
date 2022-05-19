const { app, BrowserWindow } = require('electron')
const path = require('path')

/**
 * Defining window
 */
const createWindow = () => {
    const win = new BrowserWindow({
        width: 800,
        height: 600,
        webPreferences: {
            nodeIntegration: false,
            worldSafeExecuteJavaScript: true,
            contextIsolation: true
        }
    })

    win.loadFile('./index.html')
}

require('electron-reload')(__dirname, {
    electron: require(`${__dirname}/node_modules/electron`)
})

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