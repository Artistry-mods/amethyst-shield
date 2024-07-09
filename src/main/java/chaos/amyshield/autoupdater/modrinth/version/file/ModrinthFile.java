package chaos.amyshield.autoupdater.modrinth.version.file;

public class ModrinthFile {
    public Hashes hashes;
    public String url;
    public String filename;
    public boolean primary;
    public int size;
    public String file_type;

    public ModrinthFile() {

    }

    public class Hashes {
        public String sha1;
        public String sha512;

        public Hashes() {

        }
    }
}
