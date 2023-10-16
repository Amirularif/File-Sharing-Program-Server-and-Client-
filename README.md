# File Sharing Program between Multiple Clients and Server Developed using JAVA Eclipse
File Sharing program that was developed using Java language on Eclipse IDE. Socket method is being utilized in order to transfer files from multiple clients to server. Clients first need to connect to server. After connection is successful, they
can transfer, modify, delete files to and from the server. On server side, the file can be downloaded or deleted from the server.

> [!WARNING]
> This Project is completed for strictly for University purposes. Feel free to use it for educational and learning purposes but not for business purposes!

# Development Programs and language used

- Eclipse IDE
* Java language
+ Socket Programming

# Developer

👦 [Amirul Arif](https://github.com/Amirularif/)

# Functions of each classes 

1. _client.java_ :
   > This class is used to connect the client to the server and send files to the server. It includes the option for the users to choose file and send them to the server. Updated version of this class also includes the list of clients that are  connected to the servers, when clicking the refresh button and have the function of deleting files on the client side when clicking the delete button.

1. _server.java_           :
   > This class is used to accept connections from clients and receive files from them. It includes File Receiver interface that shows all the files which are sent from client to the server. Updated version allows file up to 10MB including images to be received.

1. _ClientFrame.java_      :
   > This class is used by the client to connect to the server. It also open the file transfer method (client.java). Updated version allows the client side to view any modifications or events that is happening inside the client directory. Example is creation, deletion and modification of file in the client directory.

1. _Serverconnector.java_  :
   > This class is used by the server to manage and handle client connections and display information about them.

1. _MyFile.java_           :
   >This class is used by both the client and server to read and write files. 
