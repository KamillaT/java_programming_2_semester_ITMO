package processing.specificcommands;

public abstract class AbstractCommand {
    private final String name;

    private final String description;

    public AbstractCommand(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public abstract String execute(String string, Object object, Integer userId);

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public abstract String getCommandInformation();

    @Override
    public String toString() {
        return this.getName() + " (" + this.getDescription() + ")";
    }

    @Override
    public int hashCode() {
        return name.hashCode() + description.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AbstractCommand other = (AbstractCommand) obj;
        return name.equals(other.name) && description.equals(other.description);
    }
}
