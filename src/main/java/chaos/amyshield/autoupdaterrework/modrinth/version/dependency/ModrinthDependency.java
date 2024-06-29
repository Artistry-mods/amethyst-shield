package chaos.amyshield.autoupdaterrework.modrinth.version.dependency;

public class ModrinthDependency {
    public String version_id;
    public String project_id;
    public String filename;
    public DependencyType dependency_type;

    public enum DependencyType {
        required,
        optional,
    }
}
