# Sketches

## Table of contents

- [Sketches](#sketches)
  - [Table of contents](#table-of-contents)
  - [Design the API guided by application requirements](#design-the-api-guided-by-application-requirements)
    - [Before authentication](#before-authentication)
    - [After authentication](#after-authentication)
      - [Student (Web App)](#student-web-app)
      - [Teacher (Desktop App)](#teacher-desktop-app)
  - [Notes (changes in the DB)](#notes-changes-in-the-db)

## Design the API guided by application requirements

### Before authentication

- What is shown?
  - Sign-in or sign-up
  - Introduction to application
  - Download desktop app for teacher (only)

### After authentication

#### Student (Web App)

- Home page
  - Content
    - Classrooms list that the student is apart of (sorted by name or added)
    - Teams list
    - Upcoming deliveries
  - Links
    - Organizations that the student is apart of
    - Individual classroom
    - Individual team
    - Individual upcoming assignment
    - Logout
  - Actions
    - No actions

- Classroom
  - Content
    - List of assignments
    - Upcoming delivery information
    - Team(s) information
    - Teacher(s) information (discuss w/ Afonso)
  - Links
    - Home page
    - Individual assignments
    - Individual Team (and or Repo)
    - Logout
    - Individual Teacher (discuss w/ Afonso)
  - Actions
    - No actions

- Team
  - Repo or list of repos
  - tags made

- Repo
  - Content

- Assignment
  - description
  - next deliveries
  - create delivery

#### Teacher (Desktop App)

- Home page
  - Organization list, for which the teacher is a member (sorted by name or recent)

- Organization
  - Classroom list (sorted by name or recent)
    - with next delivery
    - link to assignment or list of assignments

- Classroom
  - Team list, for which the teacher owens  (sorted by )
    - link to assignment or list of assignments
    - with next delivery

- Team
  - Repo or list of repos
  - tags made

- Assignment
  - description
  - next deliveries

- Repo
  - Notes
  - Content

- Note
  - editable

## Notes (changes in the DB)

[x] Add the notion of _archived_ (or _inactive_)

- Archived is a thing that is available, probably just for reading, on which a user is not actively working
- Applies to: classroom and team
