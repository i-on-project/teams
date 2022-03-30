# Notes about the project's data model:

- An ``organization`` is the representation of a given course, such as "PS" or "DAW". Is has the following characteristics:
  - An unique id
  - A name
  - A set of classrooms of that course (eg. LI61D or LI61N)

- A ``classroom`` represents a one or more teachers that teach students on a given schedule. It is characterized by:
  - An unique id
  - A name
  - The year it is being taught
  - A URI to its public repository
  - The maximum allowed number of teams and number of members per team
  - One or more teachers
  - Students
  - A set of invite links (for students to join the classroom and create their teams)
  - Student teams
  - A collection of assignments

- A ``Teacher`` is characterized by:
  - An unique number that identifies the teacher
  - A name
  - A set of classrooms

- A ``Student`` is characterized by:
  - An unique number that identifies the student
  - A name
  - One or more classrooms
  - One or more teams

- An ``invite link`` is used to invite students into the classroom and for them to create or join a team. It is characterized by:
  - An unique URI
  - It is linked to a specific classroom

- A ``team`` is a collective of students from a specific classroom that develop and deliver assignments. It is characterized by:
  -  An unique id
  -  The number of members (auto incremented)
  -  A classroom in whitch it is inserted
  -  Studends that form the team
  -  One or more repositories
  -  One or more private notes that only the teachers of that classroom can access

- A ``note`` is attributed to the team by a teacher and can only be accessed by the theachers of the classroom in which the team is inserted. It is characterized by:
  - A team id
  - The date it was created, that together with the team id form its key.
  - A description

- A ``repo`` is a GitHub repository where the development of the assignments done by each team is stored. It is characterized by:
  - An unique URL
  - A name
  - Zero or more tags, that are used to deliver the assignments
  - A team
  - One or more assignments

- A ``tag`` is used to identify a specific team delivery to an assignment. It is characterized by:
  - An unique name (*)
  - A date
  - A team
  - A delivery

- An ``assignmet`` is created by the teacher for their students to develop and deliver. It is characterized by:
  - An unique id
  - A release date
  - A due date
  - A description
  - A classroom id

- A ``delivery`` is created by a teacher, reprensents a delivery from a specific assignment. It's possible for an assignment to have multiple deliveries. It's characterized by:
  - An unique id
  - A assId
  - A date
