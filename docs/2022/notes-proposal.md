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
  - Avoid storing secrets on cloud servers.           #(token stored on the server -> request to create)
  - Have minimum requirements for the backend         #(deploy)

[x] Characterize functionality

- User stories

[x] High-level organization/architecture

- Identify components
- Identify interactions between components (e.g. desktop app -> GitHub API) and between components and users
- Identify technologies

[ ] Weekly planning

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

- Web App - Students                                                                                   #
  - creation via link shared by teacher:
    - student clicks the link
      - associates is gut hub account and form will appear
        - student associates is student number and full name
        - he may select an already existing group or create another (this action will work as a pending request waiting for the teacher to accepted)
  - access to active projects
    - select the class
      - access to project description;
      - access to delivery dates.
  - small git tutorial for new students (for first year students) -> OPTIONAL.

- Desktop app - Teacher
  - when creating an account user must authenticate via github;
  - creates a class -> in the end of this action will automatically generate a sharable link for group selection
    - user can set a maximum number of groups to be generated
    - must load a template repository from the generation
  - when the user wants to pull the repositories he may select one of the above:                        #
    - All repos of a class;
    - All repos that have delivered on time;
    - All repos that haven't delivered on time;
    - Selection of specific repos.
  - when the user visits an existing class
    - as access to a statistics of the deliveries
    - as access to the requests for group creation -> can accept/deny all or individually
    - as access to all students information (full name and number)
  - when visiting a group the user my add/edit a private note(only visible by the teacher).            #

- OPTIONAL:
  - teacher can give feedback
  - teacher can give grades to each student
  
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
