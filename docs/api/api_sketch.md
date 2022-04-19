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
    - Classrooms list that the student is a part of (sorted by name or added)
    - Teams list
    - Upcoming deliveries
  - Links
    - Organizations that the student is apart of
    - Individual classroom
    - List of teams
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
    - Logout
    - Individual classroom
    - List of classrooms
    - List of organizations
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
    - Logout
    - Individual organization
    - Individual Team
    - Individual Teacher (to teacher(s) information)
    - List of assignments
  - Actions
    - No actions

- Team
  - Content
    - Repo or list of repos 
    - Upcoming delivery information
  - Links
    - Home page
    - Logout
    - Classroom
    - Individual Repository
    - List of Assignments
  - Actions
    - No actions

- Repo
  - Content
    - Name
    - Assignment
  - Links
    - Home page
    - Logout
    - GitHub Repo URL
    - Team
    - Individual assignment
    - Tags GitHub
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
    - List of classroom
    - List of team
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
    - Logout
    - Individual classroom
    - List of organizations
    - List of classrooms
  - Actions
    - Create a new classroom
    - Delete an existing classroom
    - Update organization

- Classroom
  - Content
    - Name
    - Description
    - State
    - Requests
    - Invite Link
    - List of teams (not in the pending state)
    - List of students
    - List of assignments
    - List of requests
  - Links
    - Home page
    - Logout
    - Organization
    - List of Requests
    - List of teams
    - List of assignment
  - Actions
    - Create assignment
    - Delete assignment
    - Update assignment
    - Update classroom
    - Generate invite link

- Team
  - Content
    - Repo or list of repos
    - Tags
    - Comments
  - Links
    - Home page
    - Logout
    - Individual Repository
    - Classroom
    - List of notes
  - Actions
    - Delete team
    - Update team
    - Create note
    - Delete note

- Note
  - Content
    - Description
  - Links
    - Home page
    - Logout
    - Team
    - Classroom
    - Assignment
  - Actions
    - Update note

- Repo
  - Content
    - Name
    - Assignment
    - List of tags
  - Links
    - Home page
    - Logout
    - Team
    - GitHub Repo URL
    - Assignment
    - List of Tags
  - Actions
    - Update Repo
    - Delete Repo

- Assignment
  - Content
    - Description
    - Deliveries
  - Links
    - Home page
    - Classroom
    - Logout
  - Actions
    - Create delivery
    - Remove delivery
    - Update assignment

- Requests 
  - Content
    - List of teams in the 'pending' state
      - Can accept/decline a team
  - Links
    - Classroom
    - Team
  - Actions
    - Create Repo (Service automatically does this action when a resquest is accepted)
    - Accepting requests

- Invite
  - Content
    - sing up for student
    - team selection
  - Links
    - Classroom
  - Actions
    - Create a team
    - Create Student **Missing** (how to represent with sign up?)

- Students
  - Content
    - list of students
  - Links 
    - Classroom
  - Actions
    - No actions
    
## Notes (changes in the DB)

[x] Add the notion of _archived_ (or _inactive_)

- Archived is a thing that is available, probably just for reading, on which a user is not actively working
- Applies to: classroom and team
