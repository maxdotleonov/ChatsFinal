package Models;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Package {
    final String METHOD_DELIMITER = ":";
    final String PAYLOAD_DELIMITER = ";";
    final String KEYVALUE_DELIMITER = "=";

    PackageType type = PackageType.EMPTY;
    Map<String,String> payload;

    public Package(){

    }
    public Package(PackageType type){
        this.type = type;
    }
    public Package(PackageType type, String message){
        setType(type);
        HashMap<String, String> payload = new HashMap<>();
        payload.put("message", message);
        this.setPayload(payload);
    }

    /**
     * Nimmt serialisiertes Package und deserialisiert es.
     *
     * @param string LOGIN:username=max,password=max
     */
    public Package(String string){
        if  (string != null && string.contains(METHOD_DELIMITER)) {
            String[] pkg = string.split(METHOD_DELIMITER);
            this.setType(PackageType.findByString(pkg[0]));

            HashMap<String, String> payload = new HashMap<>();
            if (pkg.length == 2) {
                Arrays.stream(pkg[1].split(PAYLOAD_DELIMITER))
                        .forEach((pair) -> {
                            String[] p = pair.split(KEYVALUE_DELIMITER);
                            payload.put(p[0], p[1]);
                        });
                this.setPayload(payload);
            }

        } else {
            throw new RuntimeException("Fehlerhaftes Packet");
        }
    }

    public String get(String key) {
        try {
            return this.payload.get(key);
        }catch (NullPointerException e) {
            return null;
        }
    }

    public Package put(String key, String value) {
        this.payload.put(key, value);
        return this;
    }

    public PackageType getType() {
        return type;
    }

    public void setType(PackageType type) {
        this.type = type;
    }

    public Map<String, String> getPayload() {
        return payload;
    }

    public void setPayload(Map<String, String> payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return getType().toString() + METHOD_DELIMITER + (payload != null ? serializePayload() : ""); //LOGIN:
    }

    private String serializePayload() {
       return payload.entrySet().stream()
               .map((Map.Entry<String,String> entry) -> entry.getKey() + KEYVALUE_DELIMITER + entry.getValue())
               .collect(Collectors.joining(PAYLOAD_DELIMITER));
    }
}
