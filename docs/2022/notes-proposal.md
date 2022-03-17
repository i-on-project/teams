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
  - Avoid storing secrets on cloud servers.           #
  - Have minimum requirements for the backend         #

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

## Features and Functionality

- Web App - Students
  - authentication:
    - through github;
    - link student number and name;                                        #
  - active projects
    - project description (loaded by teacher)*
    - delivery dates;
  - creation via link shared by teacher -> fill form of group members;
  - small git tutorial for new students (for first year students)*.

- Desktop app - Teacher
  - authentication via github;
  - list of groups and its students for each class;
  - Pull:                                                         #
    - All repos of a class;
    - All repos that have delivered on time;
    - All repos that haven't delivered on time;
    - Selection of specific repos.
  - creation of link per class -> teacher creates a class and automatically generates a link that can be shared with students for repos creation;
  - statistics of deliveries per class;                           #
  - private notes in each group(only visible for teacher)*.       #

- Optional:
  - display grades in github classroom.

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
