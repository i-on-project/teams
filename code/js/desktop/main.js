const { app, BrowserWindow, ipcMain, Notification, clipboard, shell, webContents } = require('electron')
const path = require('path')

/* ****************************** WINDOW CONFIGURATION ******************************

 * Relevant electron ipc documentation:
 * https://www.electronjs.org/docs/latest/tutorial/quick-start
 * https://www.electronjs.org/docs/latest/api/app
 */

/**
 * Defining window
 */
const createWindow = () => {
    const win = new BrowserWindow({
        title: 'i-on Teams Desktop',
        width: 1280,
        height: 720,
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

/* ****************************** INTER-PROCESS COMMUNICATION (IPC) ******************************

 * Relevant electron ipc documentation:
 * https://www.electronjs.org/docs/latest/tutorial/ipc
 */

/**
 * IPC event handling (renderer to main)
 */
ipcMain.on('notify', (_, obj) => {
    new Notification({ title: obj.t, body: obj.m }).show()
})

ipcMain.on('copy', (_, value) => {
    clipboard.writeText(value)
})

ipcMain.on('openBrowser', (_, url) => {
    console.log('Trying to open browser to this url: ' + url)
    shell.openExternal(url)
})

/**
 * IPC messages to renderer (main to renderer)
 */
function urlToRenderer(url) {
    console.log('ATENÇÃO' + url)
    const wContents = webContents.getFocusedWebContents()

    if (wContents) {
        return wContents.send('url', url)
    }
}
 

/* ****************************** DEEP LINKING AND CUSTOM PROTOCOL ******************************

 * Relevant electron deep linking and custom protocol documentation:
 * https://www.electronjs.org/docs/latest/tutorial/launch-app-from-url-in-another-app
 * https://www.electronjs.org/docs/latest/api/protocol#protocolregisterschemesasprivilegedcustomschemes
 * 
 * TODO: Review packaging instructions
 */

//Register application to handle custom protocol, in this case the protocol is "i-on-teams://"
if (process.defaultApp) {
    if (process.argv.length >= 2) {
        app.setAsDefaultProtocolClient('i-on-teams', process.execPath, [path.resolve(process.argv[1])])
    }
} else {
    app.setAsDefaultProtocolClient('i-on-teams')
}

//Handle protocol events on windows. This is different that MacOS and Linux because windows needs extra instructions
//in order to use the already existing instance of the app instead of creating a new one.
const gotTheLock = app.requestSingleInstanceLock()

if (!gotTheLock) {
    app.quit()
} else {
    app.on('second-instance', (event, commandLine, workingDirectory) => {
        // Someone tried to run a second instance, we should focus our window.
        if (createWindow) {
            if (createWindow.isMinimized()) createWindow.restore()
            createWindow.focus()
        }
    })

    // Create mainWindow, load the rest of the app, etc...
    app.whenReady().then(() => {
        createWindow()
    })

    // Handle the protocol. In this case, we choose to show an Error Box.
    app.on('open-url', (event, url) => {
        //TODO: Handle event and notify renderer
        urlToRenderer(url)
    })
}

//Handle protocol events on MacOS and Linux.

// This method will be called when Electron has finished
// initialization and is ready to create browser windows.
// Some APIs can only be used after this event occurs.
app.whenReady().then(() => {
    createWindow()
})

// Handle the protocol. In this case, we choose to show an Error Box.
app.on('open-url', (event, url) => {
    //TODO: Handle event and notify renderer
    urlToRenderer(url)
})

