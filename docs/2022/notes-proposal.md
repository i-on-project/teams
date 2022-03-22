# Notes for the project proposal

- Use LaTeX to create all documents that need to be in PDF format
  - Consider using overleaf
- PS documents can be in PT or EN

## TODO

[x] Define document structure.

[x] How this project distinguishes it self from CodeGarten 2021 (marked with #)

- Existence of a desktop client
  - Features that are more student oriented
    - Features that are "classroom" specific and not available on other Git clients.
    - Features that make Git more easier to use by first-year students.
  - Avoid storing secrets on cloud servers.           #(token stored on the teachers app -> request to create)
  - Have minimum requirements for the backend         #(try to have minimum server usage for viable deploy)

[x] Characterize functionality

- User stories

[x] High-level organization/architecture

- Identify components
- Identify interactions between components (e.g. desktop app -> GitHub API) and between components and users
- Identify technologies

[ ] Weekly planning

[ ] Add references to proposal

## Document structure (4 pages max)

- Introduction
  - i-on initiative description
  - our project
  - difference between CodeGarten 2021 and teams 2022
- Analysis
  - Components
  - Interactions between components
  - High Level organization/architecture
- Weekly plan

## Features and Functionality (step-by-step)

- Web App - Students (from here on out refered as "user")
  - The user must be loggedin (if an account needs to be created, it will be done so through GitHub)
  - After logging in the user has the following options:
    - Create a group through a link shared by the teacher:
      - After using the link a form will appear:
        - User provides the name and student number
          - User may then select an already existing group or create a new one (this action will work as a pending request waiting for the teacher to accept)
    - Access to active assignments:
      - Select the class:
        - access to project description;
        - access to delivery dates.
  - A small git tutorial is going to be provided (for first year students, does not need an authenticated user) -> OPTIONAL.

- Desktop app - Teacher (from here on out refered as "user")
  - When the user runs the app for the first time he must create an account through GitHub;
  - After creating an account the user has the following options:
    - Create a new class (in a organization)
      - load the assignments and respective due dates (some of then can be invisible for the student)
      - user can set a maximum number of groups to be generated
      - must load a template repository from the generation
      - at the end of class setup the app will generate a sharable link for group creation/selection
    - Management of a group and is members (delete a member)
    - Inside of an existing class the user will have the following options:
      - Pull groups repositories (which can be achieved in one of the following patterns):                  #
        - All repos of a class;
        - All repos that have delivered on time;
        - All repos that haven't delivered on time;
        - Selection of specific repos.
      - Delivery statistics
      - Group creation requests -> can accept/deny all or individually accept/deny
      - Students information (full name and number)
      - When visiting a specific group the user my add/edit a private note(only visible by to teacher).     #

- Some additional functionality has been identified (with OPTIONAL status):
  - teacher can give feedback
  - teacher can grade assignments
  
## High Level organization/architecture

### Components

- Desktop App (Teacher)
- Web App (Students)
- Web Server (Exposes API)
- Database* (User data)

### Interactions between components

![](https://github.com/i-on-project/teams/blob/main/docs/2022/interactions_between_components.png)

### Tecnologies

- ElectronJS -> Used for the development of the Desktop App
- ReactJS -> Used for building the UI for both the Desktop and Web Apps
- Spring Framework -> Used for building the server component
- PostgreSQL -> Used for the database

## Weekly Planning

*questions
