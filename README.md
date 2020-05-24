# ToDoApplication_Using_RoomDatabase
* This is Todo Android Application .It Mainly focuses on using Room Database , LiveData, ViewModel .
* I used Concepts like Room Database because it is a persistace database which provides many benifits to application .It provides
  advantages like 
     * CompileTime checking SQL statements
     * Reduces Boiler plate code 
     * No using raw queries. 
* I used LiveData to reduce requerying to Database . LiveData while observe changes and update the UI . No need of calling database 
  again and again
* I used ViewModel to handle configuration changes in application. Whenever configuaration changes, acitivity is distroyed and recreated.
  This will recall the database again and again.So, to reduce that ViewModel is used.
