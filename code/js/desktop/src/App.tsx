import * as React from 'react'
import { Route, Routes, HashRouter } from "react-router-dom";
import { MenuContainer } from './common/components/MenuStatus';
import { VerticalFixedMenu } from './common/components/Menu';
import * as Home from "./home/Page"
import * as Organizations from "./Organizations/Page"
import * as Organization from "./Organization/Page"
import * as Classroom from "./Classroom/Page"
import * as Teams from "./Teams/Page"
import * as Team from "./Team/Page"
import * as Students from "./Students/Page"
import * as Assignments from "./Assignments/Page"
import * as Assignment from "./Assignment/Page"
import * as Delivery from "./Delivery/Page"
import * as Requests from "./Requests/Page"
import { Grid } from 'semantic-ui-react';

declare const electron: {
  externalBrowserApi: {
    open: (value: string) => undefined
  },
  customProtocolUrl: (callback: (_event: any, value: string) => void) => void,
  getClientInfo: () => Promise<ClientInfo>
}

declare type ClientInfo = {
  clientId: string,
  clientSecret: string
}

declare type UrlObj = {
  protocol: string,
  code: string
}

function convertUrltoObj(url: string) {

  let split = url.split('://')
  const protocol = split[0]

  split = split[1].split('=')
  const code = split[1]

  const obj: UrlObj = { protocol: protocol, code: code }

  return obj
}

export default function App() {

  const [url, setUrl] = React.useState('*No URL yet*')

  electron.customProtocolUrl((_event, value) => {
    setUrl(value)
  })

  React.useEffect(() => {
    if (!url.includes('code=')) return

    const urlObj = convertUrltoObj(url)

    electron.getClientInfo()
    .then( clientInfo => {
      console.log( clientInfo)
    })

    /*const CLIENT_ID = process.env.CLIENT_ID
    const CLIENT_SECRET = process.env.CLIENT_SECRET

    const tokenEnpointUrl = `
        https://github.com/login/oauth/access_token?
        client_id=${CLIENT_ID}&
        client_secret=${CLIENT_SECRET}&
        code=${urlObj.code}
      `

    fetch(tokenEnpointUrl, {
      method: 'POST',
    })
      .then(resp => console.log(resp))*/
  }, [url])

  return (
    <div>
      THIS IS A TEST PAGE, TO SEE THE REAL PAGES UNCOMMENT THE ROUTER!

      <button onClick={() => { electron.externalBrowserApi.open('http://localhost:8080/auth/login?clientId=desktop') }}>Login</button>
      <div>
        The url is: {url}
      </div>
    </div>
  )
}

/*export default function App() {

  return (

    <HashRouter>
      <MenuContainer>
        <Grid columns={2}>
          <Grid.Column width={3}>
            <VerticalFixedMenu />
          </Grid.Column>
          <Grid.Column width={12} style={{ marginTop: "16px" }}>
            <Routes>
              <Route path='' element={<Home.Page />} />
              <Route path='/orgs' element={<Organizations.Page />} />
              <Route path='/orgs/:orgId' element={<Organization.Page />} />
              <Route path='/orgs/:orgId/classrooms/:classId' element={<Classroom.Page />} />
              <Route path='/orgs/:orgId/classrooms/:classId/teams' element={<Teams.Page />} />
              <Route path='/orgs/:orgId/classrooms/:classId/teams/:teamId' element={<Team.Page />} />
              <Route path='/orgs/:orgId/classrooms/:classId/students' element={<Students.Page />} />
              <Route path='/orgs/:orgId/classrooms/:classId/assignments' element={<Assignments.Page />} />
              <Route path='/orgs/:orgId/classrooms/:classId/assignments/:assId' element={<Assignment.Page />} />
              <Route path='/orgs/:orgId/classrooms/:classId/assignments/:assId/deliveries/:delId' element={<Delivery.Page />} />
              <Route path='/orgs/:orgId/classrooms/:classId/requests' element={<Requests.Page />} />
            </Routes>
          </Grid.Column>
        </Grid>
      </MenuContainer>
    </HashRouter >
  )
}*/