# journalDB
# journalDB

java -Xmx100m journalDB.UI
-1 means insert failed
0 means successful no new key created
1 or greater is the key

# SAM TODO
implement are you sure interface in retract.authro

# TODO Functions

## Bland Verbs

```
login(int id)
```
Need login to return a string that says that type of user they are. Options should be "author", "reviewer", "editor", "none"

##Author Verbs

```
submit(String title, String affil, int RICode, string secondaryAuthors, String filename)
```
Where secondary authors are structured properly already so all you have to do is insert it into the DB.

```
retract(Int manNum)
```

```
authorStatus(Int id)
```
Needs to return all the manuscripts in which the author is primary author.


##Editor Verbs
```
editorStatus(Int id)
```
Needs to list all manuscripts in the system sorted by status and then manuscript number

```
assign(Int manNum, int reviewerId)
```


