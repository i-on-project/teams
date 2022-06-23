const {ipcRenderer, contextBridge} = require('electron')

contextBridge.exposeInMainWorld(
    'electron', {
        notificationApi: {
            sendNotification(notification) {
                ipcRenderer.send('notify', notification)
            }
        },
        clipboardApi: {
            copy(value) {
                ipcRenderer.send('copy', value)
            }
        }
    }
)