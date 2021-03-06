package com.neueda.jetbrains.plugin.graphdb.test.integration.neo4j.data;

import java.util.HashMap;
import java.util.Map;

public class StoredProcedure {

    private final String name;
    private final String signature;
    private final String description;

    public StoredProcedure(String name, String signature) {
        this(name, signature, null);
    }

    public StoredProcedure(String name, String signature, String description) {
        this.name = name;
        this.signature = signature;
        this.description = description;
    }

    public Map<String, String> asMap() {
        Map<String, String> procedure = new HashMap<>();
        procedure.put("name", name);
        procedure.put("signature", signature);
        if (description != null) {
            procedure.put("description", description);
        }
        return procedure;
    }
}
