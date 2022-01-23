package duke;
import java.io.*;
import java.util.*;
import java.time.*;
import java.time.format.*;
import duke.tasks.*;


public class Pikachu {
    public ArrayList<Task> inputList;

    //Constructor
    public Pikachu() {
        inputList = new ArrayList<Task>();
    }

    //Constructor for existing tasklist
    public Pikachu(ArrayList<Task> inputList) {
        this.inputList = inputList;
    }

    public void parseInput(String str) {
        String[] split = str.split(" ");

        if (str.toLowerCase().equals("list")) {                        //LIST
            //System.out.println("list command reached!");
            System.out.println("Here are the tasks in your list:");
            int count = 1;
            String currBool = "[error]"; 
            String currTask = "[error]";
            for (Task t : inputList) {
                if (t.isDone) { currBool = "[X]";
                } else { currBool = "[ ]"; }

                if (t.getClass().getSimpleName().equals("ToDo")) { currTask = "[T]";
                } else if (t.getClass().getSimpleName().equals("Deadline")) { currTask = "[D]";
                } else if (t.getClass().getSimpleName().equals("Event")) { currTask = "[E]"; }

                System.out.println("   " + count + ". " + currTask + currBool + " " + t);
                count += 1;
            }
            count -= 1;
            System.out.println("Now you have "+count+" task(s) in the list.");
            return;
        }

        if (split[0].toLowerCase().equals("mark")) {                  //MARK
            int index = 0;
            try { index = Integer.parseInt(split[1]) - 1;
            } catch (Exception e) { System.out.println("Invalid input for mark!"); return; }
            if (index >= inputList.size() || index <= -1) {                          //Prevent invalid array accesses
                System.out.println("Invalid task number!");
                return;
            }
            Task t = inputList.get(index);
            t.mark();

            String currBool = "[error]"; 
            String currTask = "[error]";
            if (t.isDone) { currBool = "[X]";
            } else { currBool = "[ ]"; }
            if (t.getClass().getSimpleName().equals("ToDo")) { currTask = "[T]";
            } else if (t.getClass().getSimpleName().equals("Deadline")) { currTask = "[D]";
            } else if (t.getClass().getSimpleName().equals("Event")) { currTask = "[E]"; }
            System.out.println("Pikachu has marked this task as done!\n   > " + currTask + currBool + " " + t);
            return;
        }

        if (split[0].toLowerCase().equals("unmark")) {                //UNMARK
            int index = 0;
            try { index = Integer.parseInt(split[1]) - 1;
            } catch (Exception e) { System.out.println("Invalid input for unmark!"); return; }
            if (index >= inputList.size() || index <= -1) {                          //Prevent invalid array accesses
                System.out.println("Invalid task number!");
                return;
            }
            Task t = inputList.get(index);
            t.unmark();

            String currBool = "[error]"; 
            String currTask = "[error]";
            if (t.isDone) { currBool = "[X]";
            } else { currBool = "[ ]"; }
            if (t.getClass().getSimpleName().equals("ToDo")) { currTask = "[T]";
            } else if (t.getClass().getSimpleName().equals("Deadline")) { currTask = "[D]";
            } else if (t.getClass().getSimpleName().equals("Event")) { currTask = "[E]"; }
            System.out.println("Pikachu has marked this task as not done yet!\n   > " + currTask + currBool + " " + t);
            return;
        }

        if (split[0].toLowerCase().equals("delete")) {                //DELETE
            int index = 0;
            try { index = Integer.parseInt(split[1]) - 1;
            } catch (Exception e) { System.out.println("Invalid input for delete!"); return; }
            if (index >= inputList.size() || index <= -1) {                          //Prevent invalid array accesses
                System.out.println("Invalid task number!");
                return;
            }
            Task t = inputList.remove(index);

            String currBool = "[error]"; 
            String currTask = "[error]";
            if (t.isDone) { currBool = "[X]";
            } else { currBool = "[ ]"; }
            if (t.getClass().getSimpleName().equals("ToDo")) { currTask = "[T]";
            } else if (t.getClass().getSimpleName().equals("Deadline")) { currTask = "[D]";
            } else if (t.getClass().getSimpleName().equals("Event")) { currTask = "[E]"; }
            System.out.println("Pikachu has deleted this task!\n   > " + currTask + currBool + " " + t);
            System.out.println("You now have " + inputList.size() + " tasks in the list.");
            return;
        }

        if (split[0].toLowerCase().equals("todo")) {                    //TODO
            String[] split2 = str.split(" ", 2);
            //System.out.printf("For debugging. split2[1] = %s\n", split2[1]);
            try {
            ToDo t = new ToDo(split2[1]);
            inputList.add(t);
            System.out.println("Pikachu has added this task to the list!");
            System.out.println("   > [T][ ] " + t);
            System.out.println("You now have " + inputList.size() + " tasks in the list.");
            return;
            } catch (Exception e) {
                System.out.println("Task description is empty!");
                return;
            }
        }

        if (split[0].toLowerCase().equals("deadline")) {               //DEADLINE
            try {
            String[] split2 = str.split("/");
            String[] split3 = split2[1].split(" ");
            String[] split4 = split2[0].split(" ", 2);
            String name = split4[1];
            String date = split3[0];
            String time = split3[1];
            String[] yymmdd = date.split("-");
            LocalDateTime deadline = LocalDateTime.of(Integer.parseInt(yymmdd[0]), Integer.parseInt(yymmdd[1]), 
                    Integer.parseInt(yymmdd[2]), Integer.parseInt(time.substring(0,2)), Integer.parseInt(time.substring(2,4)));
            //System.out.printf("For debugging. split2[1] = %s, split3[1] = %s\n", split2[1], split3[1]);
            Deadline d = new Deadline(name, deadline);
            inputList.add(d);
            System.out.println("Pikachu has added this task to the list!");
            System.out.println("   > [D][ ] " + d);
            System.out.println("You now have " + inputList.size() + " tasks in the list.");
            return;
            } catch (Exception e) {
                System.out.println("Task description is empty/Empty or invalid deadline timing has been specified!");
                return;
            }
        }

        if (split[0].toLowerCase().equals("event")) {                  //EVENT
            try {
            String[] split2 = str.split("/");
            String[] split3 = split2[1].split(" ");
            String[] split4 = split2[0].split(" ", 2);
            String name = split4[1];
            String dateStart = split3[0];
            String timeStart = split3[1];
            String dateEnd = split3[2];
            String timeEnd = split3[3];
            String[] yymmddStart = dateStart.split("-");
            String[] yymmddEnd = dateEnd.split("-");
            LocalDateTime start = LocalDateTime.of(Integer.parseInt(yymmddStart[0]), Integer.parseInt(yymmddStart[1]), 
                    Integer.parseInt(yymmddStart[2]), Integer.parseInt(timeStart.substring(0,2)), Integer.parseInt(timeStart.substring(2,4)));
            LocalDateTime end = LocalDateTime.of(Integer.parseInt(yymmddEnd[0]), Integer.parseInt(yymmddEnd[1]), 
                    Integer.parseInt(yymmddEnd[2]), Integer.parseInt(timeEnd.substring(0,2)), Integer.parseInt(timeEnd.substring(2,4)));
            if (end.isBefore(start)) {
                System.out.println("End time cannot be earlier than start time!");
                return;
            }        
            //System.out.printf("For debugging. split2[1] = %s, split3[1] = %s\n", split2[1], split3[1]);
            Event e = new Event(name, start, end);
            inputList.add(e);
            System.out.println("Pikachu has added this task to the list!");
            System.out.println("   > [E][ ] " + e);
            System.out.println("You now have " + inputList.size() + " tasks in the list.");
            return;
            } catch (Exception e) {
                System.out.println("Task description is empty/Empty or invalid event duration has been specified!");
                return;
            }
        }
        
        //For non-recognizable inputs
        System.out.println("Pikachu does not understand...");
    }
}
