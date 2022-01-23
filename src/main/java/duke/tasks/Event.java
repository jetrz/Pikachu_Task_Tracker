package duke.tasks;
import java.time.*;
import java.time.format.*;

public class Event extends Task {
    public LocalDateTime start;
    public LocalDateTime end;

    public Event (String name, LocalDateTime start, LocalDateTime end) {
        super(name);
        this.start = start;
        this.end = end;
        this.info = "E,0," + name + "," + start.getYear() + "," + start.getMonthValue() + "," + start.getDayOfMonth()
                + "," + start.getHour() + "," + start.getMinute() + "," + end.getYear() + "," + end.getMonthValue() + "," + 
                end.getDayOfMonth() + "," + end.getHour() + "," + end.getMinute();
    }

    @Override
    public void mark() {
        this.isDone = true;
        this.info = "E,1," + name + "," + start.getYear() + "," + start.getMonthValue() + "," + start.getDayOfMonth()
                + "," + start.getHour() + "," + start.getMinute() + "," + end.getYear() + "," + end.getMonthValue() + "," + 
                end.getDayOfMonth() + "," + end.getHour() + "," + end.getMinute();
    }

    @Override
    public void unmark() {
        this.isDone = false;
        this.info = "E,0," + name + "," + start.getYear() + "," + start.getMonthValue() + "," + start.getDayOfMonth()
                + "," + start.getHour() + "," + start.getMinute() + "," + end.getYear() + "," + end.getMonthValue() + "," + 
                end.getDayOfMonth() + "," + end.getHour() + "," + end.getMinute();
    }

    @Override
    public String toString() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"); 
        return this.name + "(From " + this.start.format(format) + " to " + this.end.format(format) + ")";
    }
}
