# My Personal Project: Video Game Gallery

## An explorer for a collection of video games downloaded and their info

A brief **introduction** of this application:
- This application stores information of all the video games downloaded to the device or the account. One can use it 
to view each game's name, category, hours estimated to complete, percentage of progress the player has made so 
far, trophies earned, review ratings, age ratings etc.
earned,
- People who have gaming consoles or gaming accounts will use it.
- This project is of interest to me because I have a PS4 and sometimes play games with my friends especially during 
COVID. I am not a hardcore gamer as my favourite game is Overcooked 2, but it would be interesting to design and make 
my own version of game gallery using Java.


## User Stories
- As a user, I want to be able to *add* a game to my game gallery
- As a user, I want to be able to *delete* a game from my game gallery
- As a user, I want to be able to mark a game as *favourite* in my gallery
- As a user, I want to be able to *view* a list of games in my game gallery
- As a user, I want to be able to *select a game* in my gallery and view the information in detail
- As a user, I want to be able to *select a category* and view all games that are in that category
- As a user, I want to be able to *play* a game and spend some hours on it
- As a user, I want to be able to *save* my game gallery to file.
- As a user, I want to be able to *reload* my game gallery from file and resume exactly where I left off earlier.


## Phase 4: Task 2
Sun Nov 21 16:14:08 PST 2021\
Overcooked 2 added to Game Gallery!

Sun Nov 21 16:14:08 PST 2021\
WipEout added to Game Gallery!

Sun Nov 21 16:14:36 PST 2021\
It Takes Two added to Game Gallery!

Sun Nov 21 16:14:48 PST 2021\
WipEout deleted from Game Gallery!



## Phase 4: Task 3
If I had more time, I would try the following refactoring:
- Instead of defining each panel in GameGalleryUI class, I would make a separate class for each kind of 
panel. That way it would be more clear what panels are in the GUI just from looking at the project files. In addition, 
if the project becomes larger scale, the main GameGalleryUI class wouldn't get too long, and it is easier to add,  
remove or make changes to the panels.
- Make EventLog implement Observer and GameGallery extend Observable to reduce coupling. This would make more sense if
more observers became needed in the project.
- In the GameGallery class, make *getCategory()* return a list of Strings (game names) instead of a new Game Gallery.



