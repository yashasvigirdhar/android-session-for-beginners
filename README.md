# walmart-android-session
This repository contains the demo project that I have used for the android session that I took in walmart. The targeted audience for the session are the people who are completely new to android. 


I have covered the followign topics :

- Activities
- Intents
- Services
- Broadcast Receiver
- Recycler View
- Storage in Android
- Async Task
- floating action button


The respository contains different branches, each corresponding to a particular functionality. So, if you want to see the code changes for a particular functionality, you can just see the diff between this branch and the its previous branch. 

Here's the order in which I have prepared the branches :

1. *helloworld* : basic hello world project 
2. *listview* : added a simple listview
3. *addnewcontacts* : added a floating action button to add a new contact to the list
4. *addeddatabase* : integrated SQLite database, so that the contacts storage is persistent.
5. *syncusingasync* : syncing the contacts from the server using AsyncTask
6. *syncusingservice* : instead of asynctask, syncing with the server using an InterService.

So, for example, if you want to see how to move from async task to an intentservice, you can run :

`git diff syncusingasync..syncusingservice`

Feel free to report any issues.

Cheers !


