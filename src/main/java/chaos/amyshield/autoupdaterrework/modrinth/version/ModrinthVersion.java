package chaos.amyshield.autoupdaterrework.modrinth.version;

import chaos.amyshield.autoupdaterrework.modrinth.version.dependency.ModrinthDependency;
import chaos.amyshield.autoupdaterrework.modrinth.version.file.ModrinthFile;

public class ModrinthVersion {
    public String[] game_versions;
    public String[] loaders;
    public String id;
    public String project_id;
    public String author_id;
    public boolean featured;
    public String name;
    public String version_number;
    public String changelog;
    public String changelog_url;
    public String data_published;
    public int downloads;
    public VersionType version_type;
    public String status;
    public ModrinthFile[] files;
    public ModrinthDependency[] dependencies;

    public ModrinthFile getPrimary() {
        for (ModrinthFile file : this.files) {
            if (file.primary) {
                return file;
            }
        }
        return null;
    }

    public enum VersionType {
        release,
        alpha,
        beta,
    }
}
