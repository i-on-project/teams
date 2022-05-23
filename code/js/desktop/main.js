const { app, BrowserWindow, ipcMain, Notification } = require('electron')
const path = require('path')

/**
 * Defining window
 */
const createWindow = () => {
    const win = new BrowserWindow({
        title: 'i-on Teams Desktop',
        width: 800,
        height: 600,
        webPreferences: {
            nodeIntegration: false,
            worldSafeExecuteJavaScript: true,
            contextIsolation: true,
            sandbox: true,
            preload: path.join(__dirname, 'preload.js')
        }
    })

    win.loadFile('./index.html')
}

require('electron-reload')(__dirname, {
    electron: require(`${__dirname}/node_modules/electron`)
})

/**
 * Notification test
 */
ipcMain.on('notify', (_, obj) => {
    new Notification({title: obj.t, body: obj.m}).show()
})

/**
 * Creating window
 */
app.whenReady()
    .then(() => {
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