package de.twometer.arduleucht.codegen;

import java.util.HashMap;

public class SourceBuilder {

    private CodeEmitter headScope = new CodeEmitter();

    private CodeEmitter globalScope = new CodeEmitter();

    private HashMap<String, CodeEmitter> privateScopes = new HashMap<>();

    public CodeEmitter headScope() {
        return headScope;
    }

    public CodeEmitter globalScope() {
        return globalScope;
    }

    public CodeEmitter privateScope(String name) {
        if (privateScopes.containsKey(name))
            return privateScopes.get(name);
        CodeEmitter scope = new CodeEmitter();
        privateScopes.put(name, scope);
        return scope;
    }

    public String build() {
        CodeEmitter fullEmitter = new CodeEmitter();
        headScope.pipe(fullEmitter);
        globalScope.pipe(fullEmitter);

        for (HashMap.Entry<String, CodeEmitter> scope : privateScopes.entrySet()) {
            fullEmitter.openVoid(scope.getKey());
            scope.getValue().pipe(fullEmitter);
            fullEmitter.closeBlock();
        }

        return fullEmitter.getCode();
    }

}
