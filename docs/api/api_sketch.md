# Sketches

## Design the API guided by application requirements

### Before authentication

- What is shown?
  - Sign-in or sign-up
  - Introduction to application
  - Download desktop app for teacher (only) 
    
### After authentication

#### Student (Web App)

- Home page 
  - Classroom list, for which the student is a member (sorted by name or recent)
    - shortcut to assignment
  - Team list
    - shortcut to repo
  - link to organizations

- Classroom
  - Team list
    - shortcut to repo
  - with next delivery
  - link to assignment or list of assignments
  - Create Assignment
  - shareable link for teams creation

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