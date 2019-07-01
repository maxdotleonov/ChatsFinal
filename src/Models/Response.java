package Models;

import Models.Package;
import Models.PackageType;

import java.util.HashMap;

public class Response extends Package {
    public Response(String message) {
        this.setType(PackageType.RESPONSE);
        HashMap<String, String> payload = new HashMap<>();
        payload.put("message", message);
        this.setPayload(payload);
    }
}
