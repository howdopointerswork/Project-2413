# Project-2413
## For class INFO-2413

- Using git commands
	- switching branches
	- creating, renaming, moving files
	- fetching, merging
	- pulling, pushing

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

***ALWAYS USE git fetch origin WHEN STARTING YOUR WORK TO SEE IF ANY CHANGES HAVE BEEN MADE TO THE REPO BY OTHERS**

**IF YOU SEE CHANGES, USE git merge origin/main**

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


