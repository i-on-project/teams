const {ipcRenderer, contextBridge} = require('electron')

contextBridge.exposeInMainWorld(
    'electron', {
        notificationApi: {
            sendNotification(notification) {
                console.log(`TITLE: ${notification.t}, MESSAGE: ${notification.m}`)
                ipcRenderer.send('notify', notification)
            }
        }
    }
)