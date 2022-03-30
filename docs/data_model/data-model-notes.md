# Notes about the project's data model:

- An organization is the representation of a given course, such as "PS" or "DAW". Is has the following characteristics:
  - An unique id
  - A name
  - A set of classes of that course (eg. LI61D or LI61N)

- A class represents a one or more teachers that teach students on a given schedule. It is characterized by:
  - An unique id
  - A name
  - The year it is being taught
  - A URI to its public repository
  - The maximum allowed number of groups and number of members per group
  - One or more teachers
  - Students
  - A set of invite links (for students to join the class and create their groups)
  - Student groups
  - A collection of assignments

- A Teacher is characterized by:
  - An unique number that identifies the teacher
  - A name
  - A set of classes

- A Student is characterized by:
  - An unique number that identifies the student
  - A name
  - One or more classes
  - One or more groups

- An invite link is used to invite students into the class and for them to create or join a group. It is characterized by:
  - An unique URI
  - It is linked to a specific class

- A group is a collective of students from a specific class that develop and deliver assignments. It is characterized by:
  -  An unique id
  -  The number of members
  -  A class in whitch it is inserted
  -  Studends that form the group
  -  One or more repositories
  -  One or more private notes that only the teachers of that class can access

- A note is attributed to the group by a teacher and can only be accessed by the theachers of the class in which the group is inserted. It is characterized by:
  - A group id
  - The date it was created, that together with the group id form its key.
  - A description

- A repo is a GitHub repository where the development of the assignments done by each group is stored. It is characterized by:
  - An unique URL
  - A name
  - Zero or more tags, that are used to deliver the assignments
  - A group
  - One or more assignments

- A TAG is used to identify a specific group delivery to an assignment. It is characterized by:
  - An unique name (*)
  - A date
  - A group
  - A delivery

- An assignment is created by the teacher for their students to develop and deliver. It is characterized by:
  - An unique id
  - A release date
  - A due date
  - A description
  - 

(to be concluded)
