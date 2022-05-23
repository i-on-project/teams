import * as React from 'react'
import { Outlet, Link } from "react-router-dom";
import Button from 'react-bootstrap/Button';
import { Navbar, Nav, Container } from 'react-bootstrap';

export default function App() {
  return (
    <div>
      <div>
        <Navbar bg='dark'>
          <Container>
            <Navbar.Brand href="#home">Navbar</Navbar.Brand>
            <Nav className="me-auto">
              <Nav.Link href="#home">Home</Nav.Link>
              <Nav.Link href="#features">Features</Nav.Link>
              <Nav.Link href="#pricing">Pricing</Nav.Link>
            </Nav>
          </Container>
        </Navbar>
      </div>
      <h1>Hello from react component!</h1>
      <Button onClick={() => {
        electron.notificationApi.sendNotification({ t: 'SOME TITLE', m: 'YEEEEET!' })
      }}>Notify</Button>
    </div>
  )
}