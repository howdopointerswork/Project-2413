# Project-2413
## For class INFO-2413


## Breakdown: 

- Using git commands
	- switching branches
	- creating, renaming, moving files
	- fetching, merging
	- pulling, pushing

- Modifying java files/project (eclipse)


- GUI role
	- java and git
	- should be in same dir as other files

- Backend role
	- class files
	- java and git
	- main file

- Db role
	- softwares to use
	- sqlite3

## Using Git

**ALWAYS USE git fetch origin WHEN STARTING YOUR WORK TO SEE IF ANY CHANGES HAVE BEEN MADE TO THE REPO BY OTHERS**

**IF YOU SEE CHANGES, USE git merge origin/main**

**NOTE: Eclipse has a built-in git feature. You can switch branches and put files this way as well, if you prefer it over using Terminal**

### Summary:
- Clone the repo if you do not have it already (do not clone more than once)
- fetch changes
- merge changes
- do your work
- add the files for staging
- switch to main branch
- commit changes
- push changes


### Cloning
- For getting the repo to your local machine
- Here, you can make changes/modify files locally before committing or pushing these to the repo
- What you do is go to our repo here under the 'code' tab (if you're reading this, you're porbably there already)
- You should see a green button '<> Code', click it
- You will see three tabs, for simplicity, select SSH (you can use HTTPS if you prefer, I've only used SSH before)
- Copy the link
- Go into your home (not root)  directory in terminal (should be there as soon as you open it, if not, use cd ~)
- Now, use the following command: git clone git@github.com:howdopointerswork/Project-2413.git
- It should create a directory in your home folder called Project-2413
- Go into it by using cd Project-2413
- You now now have access to the repo locally


### Branches
- We will each do our work on the respective branches
- GUI branch for GUI
- db branch for database
- misc branch for everything else
- main is where everything is gathered
- To switch branches (you'll likely be on main by default), use: git checkout branchname
- For example, git checkout misc to switch to misc branch
- Now, say I wanted to edit the README.md file, it can be done here
- After you're done your work, use: git add .
- Remember to include the '.' above after add
- Then, go: git checkout main
- Then you can commit: git commit -m "I made changes"
- Then go: git push origin main
- To check which branch you're currently on, use git status
- This will also tell you about merge conflicts
- If you have conflicts on your branch, do the following:
- git fetch origin
- git merge origin/main


### Pulling
- We generally will use fetch and merge over pull
- git pull just combines fetch and merge into one
- By separating them, we have more control over what changes we are making


### Useful commands
- To move a file, use mv with a directory
- Say we had: file.txt and  directory1/ in our folder
- To move it in there, use: mv file.txt directory1/
- To move it back out, use the parent directory notation (../): mv file.txt ../
- Renaming uses mv as well: mv file.txt newname.txt
- To create a new file, use touch: touch file2.txt
- To delete, use rm: rm file2.txt
- To view contents of a file, use cat: cat file.txt
- To list all files in current directory, use ls: ls
- To include hidden files, such as .gitignore, use the -a flag: ls -a



## Java files/project

- File HMS.zip is the latest project that you can import in Eclipse
- File HMS.jar is the runnable .jar file that gives output
- Right now .jar file just has a single test class file and one with a mainfunction that initializes the class in the class file

- To get into source code (.java files, GUI, etc. that we edit in Eclipse)
- Go into Eclipse
- Go up to the Toolbar: File > Import > General > Existing Projects Into WorkSpace 
- Choose 'Select Archive File' and click Browse...
- Select HMS.zip
- It should open the project without errors*

- When exporting, you can either name it something different like HMS1.jar
- Or just overwrite it with same name
- So when  you are done making changes, go to the Toolbar
- Go Export > Java > Runnable JAR File 
- And export it to the Project-2413 folder
- Export it again as an archive file (.zip) and export this to Project-2413 folder



## GUI
- Start a java file or file(s) with the window builder
- From Eclipse market place
- Maybe add some labels, buttons, forms, etc. for different pages
- Suggestions:
- Log in page with labels for each field/form
- Pop up window for alerts (small window)
- Main page with buttons for adding, etc. tests
- Different page for seeing inputted results


## Database
- Start with putting the database together using commands
- Can use Oracle, Access, or any other software if you prefer
- You could also do: touch database.db
- Then you could use: sqlite3 database.db
- And you can put commands in this way (don't forget to end statements witha ;)
- To quit, go .quit or .exit
- Use SELECT statements and such to see if the information you inputted is correct
- If you finish this step, try connecting mysql with java
- For example, try printing data from any column/field
- Or multiple into an array and print them


## Backend
- Start by making the classes with variables and functions
- Make stub functions for now by making return types void
- "Implement" them with just return;
- Start simple, with User class for example
- And give it a string name, and other attributes in our diagrams
