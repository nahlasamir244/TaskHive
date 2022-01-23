# TaskHive
Task-Hive is Android To-do list app 

## The app works as follow :
1.	The main screen which displays the user saved tasks in room offline sqlite database as recyclerview with each task name and checkbox to represent task status (done or not). With floating action plus button which navigates to add task screen 
2.	you can swipe to delete any task and then a snack bar will be displayed with undo option 
3.	clicking on a task will open add/edit task screen on the edit mode with the task information , clicking the done fab will save your changes and update the task in the room database
4.	clicking the add fab will open the add/edit task screen on the add mode with empty cells to be filled with the task information , clicking the done fab will save your changes and add new task in the room database
5.	the main screen has options menu include different options for task sort and display like : hide completed , delete completed , sort by name or date and live search functionality

## Architecture pattern: 
MVVM single activity architecture with Helper class Resource to map viewmodel data into resource object holds both of data and ui state with repository pattern for data as a layer over different offline/online data sources

## Libraries and dependencies:
1. Constraint Layout for flexible relative positioning and sizing of views
2. Support for different English/Arabic local
3. Coroutines and flow for asynchronous operations (networking or data store operations)
4. Both of coroutines channels and livedata for communication from viewmodel to views ex: navigation delegation cases
5. Datastore for saving user settings and preferences
6. Livedata to hold view data to be observed in the views
7. view binding for binding viewmodel data to views
8. Room offline database
9. Navigation component for handling transitions between fragments
10. Savedstatehandle module for holding state variables like search query
11. Recyclerview with ListAdapter better choice for reactive data collections
12. Dagger-Hilt for dependency injection
13. Retrofit/okhttp for networking

## Screenshots

![Screenshot_1642894336](https://user-images.githubusercontent.com/46246511/150660627-3134a857-b62b-4ea5-853b-a6d9c609256b.png)
![Screenshot_1642894357](https://user-images.githubusercontent.com/46246511/150660628-c6378a4a-ac92-4f5b-80d2-a5fb7f0baf26.png)
![Screenshot_1642894366](https://user-images.githubusercontent.com/46246511/150660629-db906f1a-9f33-4ef3-a2e1-aa06549c4ecc.png)
![Screenshot_1642894399](https://user-images.githubusercontent.com/46246511/150660630-9148a8bf-7f91-494d-bfcb-7196716cbf6a.png)
![Screenshot_1642894408](https://user-images.githubusercontent.com/46246511/150660631-786850f2-2d10-42be-8423-e5bf80db7d80.png)
![Screenshot_1642894426](https://user-images.githubusercontent.com/46246511/150660633-41870018-b7f2-4138-8ac2-3f76039f3d1b.png)
![Screenshot_1642894573](https://user-images.githubusercontent.com/46246511/150660634-8424d9b2-07c2-47c6-a308-2e0b4b4385ea.png)
![Screenshot_1642894295](https://user-images.githubusercontent.com/46246511/150660635-0e272bff-fef9-450d-8842-0b3effe89df3.png)
![Screenshot_1642894326](https://user-images.githubusercontent.com/46246511/150660636-60f15b3e-d4b0-401e-b827-41bbc56f7424.png)

