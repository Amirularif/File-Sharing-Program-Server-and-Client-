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

# Compiling the program
 1. Download the whole project into Eclipse
 1. Run the classes according to this steps :
    - Serverconnector.java -> ClientFrame.java -> server.java -> client.java
 1. Run Serverconnector.java multiple times if in need of multiple clients

# Screenshots
_updating_
### Connecting to server
<img src="https://github.com/Amirularif/Server-Client-File-Sharing-Program/assets/57553676/88e4da4a-5ff3-4bf5-b60a-00cee667008c" width="400" height="250">
<img src="https://github.com/Amirularif/Server-Client-File-Sharing-Program/assets/57553676/37f756cc-57af-4db2-ba18-8e055e9a4397" width="400" height="180">

### Sending files to server
<img src="https://github.com/Amirularif/Server-Client-File-Sharing-Program/assets/57553676/c855a623-f0a7-4f93-a6ad-84b0d1fb02ed" width="400" height="250">
<img src="https://github.com/Amirularif/Server-Client-File-Sharing-Program/assets/57553676/e1692602-851c-4cf1-a1ca-d21ef2294802" width="550" height="250">
<img src="https://github.com/Amirularif/Server-Client-File-Sharing-Program/assets/57553676/d83a5105-229e-44ee-bcc3-f26241099be0" width="550" height="250">

### Server side file managing
<img src="https://github.com/Amirularif/Server-Client-File-Sharing-Program/assets/57553676/0936d21e-6681-43c6-aed1-91d32354e434" width="450" height="250">
<img src="https://github.com/Amirularif/Server-Client-File-Sharing-Program/assets/57553676/787aa568-240d-4b2c-929f-94588d2e5a71" width="450" height="250">
<img src="https://github.com/Amirularif/Server-Client-File-Sharing-Program/assets/57553676/86f00e49-fc1b-442c-833d-5e239c8517fd" width="450" height="250">

### Overall design
<img src="https://github.com/Amirularif/Server-Client-File-Sharing-Program/assets/57553676/487e44fd-0487-4801-bfda-c09f7076a24c" width="600" height="350">



