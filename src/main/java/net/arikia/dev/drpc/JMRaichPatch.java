package net.arikia.dev.drpc;

public class JMRaichPatch {
    public static String getLibName() {
        OSUtil os = new OSUtil();
        String libName;

        if (os.isMac()) {
            libName = "libdiscord-rpc.dylib";
        } else if (os.isWindows()){
            boolean is64bit = System.getProperty("sun.arch.data.model").equals("64");
            libName = "discord-rpc.dll";
        } else {
            libName = "libdiscord-rpc.so";
        }

        return libName;
    }
}
