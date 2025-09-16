
# Chalk User Guide

Chalk is a desktop application for managing tasks, optimized for use via a Command Line Interface (CLI) while still offering the benefits of a Graphical User Interface (GUI). If you can type fast, Chalk will help you complete your daily tasks quicker than traditional apps.

## Quick Start

1. Ensure you have Java 17 or above installed in your Computer.

2. Download the latest .jar file from [here](https://github.com/Nsohko/ip/releases).

3. Copy the file to the folder you want to use as the home folder for Chalk.

4. Open a command terminal, `cd` into the folder you put the jar file in, and run:  
   ```
   java -jar ip.jar
   ```

5. A GUI should appear within a few seconds. Type commands into the command box and press Enter to execute them.

## Commands

```
ℹ️ Notes on command format

Words in UPPER_CASE are parameters supplied by the user.
Example: todo TASK_NAME → todo buy milk

All commands are case insensitive

All dates should be provided in the following format: d/M/uuuu HHmm (e.g. 31/10/2025 1800 for 31 October 2025 6:00pm)
```

### Adding Tasks

Tasks of different types can be added via their corresponding commands:

1. Todo

Todos are basic tasks which have no due date or duration. They can be added via the ```todo``` command.
```
todo TASK_NAME
```
For example, to create a todo called ```buy milk```
```
todo buy milk
```

2. Deadline

Deadlines are tasks which have a due-by date. They can be created via the ```deadline``` command, and passsing in a ```/by``` parameter to specify the deadline.
```
deadline TASK_NAME /by DEADLINE_DATETIME
```
For example, to create a deadline called ```submit project``` due on ```20 Sep 2024, 7:00pm```
```
deadline submit project /by 20/9/2024 1900 
```

3. Event 

Events are tasked which have a specific start and end time. They can be created via the ```event``` command, and passing in a ```/from``` and ```/to``` parameter to specificy the start and end time respectively.
```
event TASK_NAME /from START_DATETIME /to END_DATETIME 
```
For example, to create can event called ```cs2102 group meeting``` that starts on ```31 October 2025 5:00pm``` and ends at ```1 November 2025 6:00 am```
```
event cs2102 group meeting /from 31/10/2025 1700 /to 1/11/2025 0600
```

Note that the order of the ```\from``` and ```\to``` parameter can be swapped. However, the end time must be *after* the start time.

Also,  the system will not let you create events that clash with other events.

### Listing Tasks

To view all tasks currently stored by Chalk, simply use the ```list``` command.

### Deleting Tasks

To delete a task, you can use the ```delete``` command.
```
delete TASK_NUMBER
```

TASK_NUMBER is the 1-indexed number of the task, as displayed when using the ```list``` command.

For example, to delete the 3rd task:
```
delete 3
```

### Tracking Task Status

Chalk supports tracking the status of tasks (i.e. whether or not they are done). To mark a task as done, use the ```mark``` command.
```
mark TASK_NUMBER
```
TASK_NUMBER is the 1-indexed number of the task, as displayed when using the ```list``` command.

For example, to mark the 4th task as done:
```
mark 3
```

To unmark a task as done, use the ```unmark``` command.
```
unmark TASK_NUMBER
```
For example, to unmark the 5th task as done:
```
unmark 5
```

### Searching For Tasks

To search for a particular task by its name, use the ```find``` command.
```
find SEARCH_PARAM_1 SEARCH_PARAM_2 ....
```
This command will return a list of all tasks whose names contain any one of the provided search parameters.

For example, to find all tasks whose names contain either milk, bread or eggs:
```
find milk bread eggs
```

### Exit

To end Chalk, simply use the ```bye``` command.

## Persistent Storage

Chalk will automatically store information locally, so that all information persists across usages.
