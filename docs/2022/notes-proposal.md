# Notes for the project proposal

- Use LaTeX to create all documents that need to be in PDF format
  - Consider using overleaf
- PS documents can be in PT or EN

## TODO

[ ] Define document structure.

[ ] How this project distinguishes it self from CodeGarten 2021

- Existence of a desktop client
  - Features that are more student oriented
    - Features that are "classroom" specific and not available on other Git clients.
    - Features that make Git more easier to use by first-year students.
  - Avoid storing secrets on cloud servers.
  - Have minimum requirements for the backend

[x] Characterize functionality

- User stories

[ ] High-level organization/architecture

- Identify components [x]
- Identify interactions between components [ ] (e.g. desktop app -> GitHub API) and between components and users
- Identify technologies [x]

[ ] Weekly planning

## Features and Functionality

- Web App - Students
  - authentication - user github(or ipl*);
  - active projects
    - project description (loaded by teacher)*
    - delivery dates;
    - feedback (Simpler view of a pull request).
  - creation via link shared by teacher -> fill form of group members;
  - small git tutorial for new students (for first year students)*.

- Desktop app - Teacher
  - authentication via github;
  - list of groups and its students;
  - Pull:
    - All repos of a class;
    - All repos that have delivered on time;
    - All repos that haven't delivered on time;
    - Selection of specific repos.
  - creation of link per class -> teacher creates a class and automatically generates a link that can be shared with students for repos creation;
  - statistics of deliveries per class;
  - private notes in each group*.

## High Level organization/architecture

### Components
- Desktop App (Teacher)
- Web App (Students)
- Web Server (Exposes API)
- Database* (User data)

### Interactions between components


### Tecnologies
- ElectronJS -> Used for the development of the Desktop App
- ReactJS -> Used for building the UI for both the Desktop and Web Apps
- Spring Framework -> Used for building the server component
- PostgreSQL -> Used for the database

## Weekly Planning


*questions
