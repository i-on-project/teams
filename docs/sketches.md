# Sketches

## Design the API guided by application requirements

- User opens application
    - What is shown?
    - What representations are needed for what is shown (remember that the user is not authenticated)?
    - One of the options is sign-in
        - What is needed in the API to perform a sign-in?

- User signed-in and is that the user home
    - What is shown?
        - Organization list, for which the user is a member
            - A: obtain the list of all organizations of a teacher
                - sorted by?
            - A: obtain the list of all classrooms of a student    
                - sorted by?
            - Q: a student sees organizations or classrooms? Probably classrooms.
            - Q: a teacher sees organizations or classrooms? 
        - Team list, for which the user is a member
            - R: obtain the list of all teams of a user
                - sorted by?
            -

## Notes

- Add the notion of _archived_ (or _inactive_)
    - Archived is a thing that is available, probably just for reading, on which a user is not actively working
    - Applies to: classroom and team 



