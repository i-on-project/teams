# API Sketches

## Table of contents

- [API Sketches](#api-sketches)
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

- Organization
  - Content
    - Name
    - Classrooms list that the student is apart of (sorted by name or added)
  - Links
    - Home page
    - Individual classroom
    - Logout
  - Actions
    - No actions

- Classroom
  - Content
    - Name
    - Description
    - State
    - List of assignments
    - Upcoming delivery information
    - Team(s) information
    - Teacher(s) information
  - Links
    - Home page
    - Individual assignments
    - Individual Team
    - Logout
    - Individual Teacher (to teacher(s) information)
    - Organization
  - Actions
    - No actions

- Team
  - Content
    - Repo or list of repos
    - Upcoming delivery information
  - Links
    - Home page
    - Classroom
    - Individual Repository
    - Assignment
    - Logout
  - Actions
    - No actions

- Repo
  - Content
    - Name
    - Assignment
    - List of tags
  - Links
    - Home page
    - Team
    - GitHub Repo URL
    - Individual Tags
    - Logout
  - Actions
    - No actions

- Assignment
  - Content
    - Description
    - Deliveries
  - Links
    - Home page
    - Classroom
    - Logout
  - Actions
    - No actions

#### Teacher (Desktop App)

- Home page
  - Content
    - Organization list that the teacher is apart of (sorted by name or added)
    - Classroom list that the teacher is apart of (sorted by name or added)
  - Links
    - Individual classroom
    - Individual team
    - Logout
  - Actions
    - Create a new organization
    - Delete an organization

- Organization
  - Content
    - Name
    - Classroom list
  - Links
    - Home page
    - Individual classroom
    - Logout
  - Actions
    - Create a new classroom
    - Delete an existing classroom
    - Update organization

- Classroom
  - Content
    - Name
    - Description
    - State
    - List of teams
    - List of students
    - List of assignments
    - List of requests
  - Links
    - Home page
    - Organization
    - Individual team
    - Individual assignment
    - Logout
  - Actions
    - Create assignment
    - Delete assignment
    - Generate invite link (Should it be an action?) [ ]
    - Accept request
    - Update classroom

- Team
  - Content
    - Repo or list of repos
    - Tags
    - Comments
  - Links
    - Home page
    - Classroom
    - Individual Repository
    - list of notes
    - Assignment
    - Logout
  - Actions
    - Delete team
    - Update team
    - Add comment
    - Delete note

- Note
  - Content
    - Description
  - Links
    - Home page
    - Team
    - Classroom
    - Logout
  - Actions
    - Update note

- Repo
  - Content
    - Name
    - Assignment
    - List of tags
  - Links
    - Home page
    - Team
    - GitHub Repo URL
    - Individual Tags
    - Logout
  - Actions
    - Update Repo
    - Create Repo

- Assignment
  - Content
    - Description
    - Deliveries
  - Links
    - Home page
    - Classroom
    - Logout
  - Actions
    - Add delivery
    - Remove delivery
    - Update assignment

## Notes (changes in the DB)

[x] Add the notion of _archived_ (or _inactive_)

- Archived is a thing that is available, probably just for reading, on which a user is not actively working
- Applies to: classroom and team
