const { app, BrowserWindow, ipcMain, Notification, clipboard, shell, webContents, dialog } = require('electron')
const path = require('path')

/* ****************************** WINDOW CONFIGURATION ******************************

 * Relevant electron ipc documentation:
 * https://www.electronjs.org/docs/latest/tutorial/quick-start
 * https://www.electronjs.org/docs/latest/api/app
 */

let mainWindow

/**
 * Defining window
 */
const createWindow = () => {
    mainWindow = new BrowserWindow({
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

    mainWindow.loadFile('./index.html')
}

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
    shell.openExternal(url)
})

/**
 * IPC messages from renderer, that expect a response (renderer to main to renderer)
 */

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

//Register application to handle custom protocol, in this case the protocol is "ion-teams://"

if (process.defaultApp) {
    if (process.argv.length >= 2) {
        app.setAsDefaultProtocolClient('ion-teams', process.execPath, [path.resolve(process.argv[1])])
    }
} else {
    app.setAsDefaultProtocolClient('ion-teams')
}

//Handle protocol events on windows. This is different that MacOS and Linux because windows needs extra instructions
//in order to use the already existing instance of the app instead of creating a new one.
const gotTheLock = app.requestSingleInstanceLock()

if (!gotTheLock) {
    app.quit()
} else {
    app.on('second-instance', (event, commandLine, workingDirectory) => {

        // Someone tried to run a second instance, we should focus our window.
        if (mainWindow) {
            if (mainWindow.isMinimized()) mainWindow.restore()
            mainWindow.focus()
        }

        //Hanle the protocol on Windows
        if (process.platform == 'win32') {
            urlToRenderer(commandLine[2])
        }
    })

    // Create mainWindow, load the rest of the app, etc...
    app.whenReady()
        .then(() => {
            createWindow()

            //IPC both ways

            app.on('activate', () => {
                if (BrowserWindow.getAllWindows().length === 0) createWindow()
            })
        })
}

// Handle the protocol on MacOS/Linux.
app.on('open-url', (event, url) => {
    dialog.showErrorBox('Welcome Back', `You arrived from ${url}`)
    urlToRenderer(url)
})

/* ****************************** PACKAGING REQUIREMENTS ****************************** */

if (require('electron-squirrel-startup')) return app.quit();