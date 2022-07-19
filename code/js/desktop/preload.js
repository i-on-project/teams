const { ipcRenderer, contextBridge } = require('electron')

contextBridge.exposeInMainWorld(
    'electron', {
    
    //Renderer to main
    notificationApi: {
        sendNotification(notification) {
            ipcRenderer.send('notify', notification)
        }
    },
    clipboardApi: {
        copy(value) {
            ipcRenderer.send('copy', value)
        }
    },
    externalBrowserApi: {
        open(url) {
            ipcRenderer.send('openBrowser', url)
        }
    },

    //Main to renderer
    customProtocolUrl: (callback) => ipcRenderer.on('url', callback),

    //Renderer to main to renderer
}
)