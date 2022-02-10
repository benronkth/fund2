package fund2.tasks;

public class Systemd {

    public static Task start(String service) {
        return new Task("systemctl start " + service);
    }

    public static Task stop(String service) {
        return new Task("systemctl stop " + service);
    }
    
    public static Task restart(String service) {
        return new Task("systemctl restart " + service);
    }
}
