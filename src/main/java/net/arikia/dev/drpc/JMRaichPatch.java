package net.arikia.dev.drpc;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.lwjgl.Sys;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

public class JMRaichPatch {
    public static String libName;

    public static void loadLib() {
        loadLib("RAVEN_DISCORD_RPC" + new Date().getTime(), new JMRaichPatch().getLibName());
    }

    public String getLibName() {
        OSUtil os = new OSUtil();
        String libName;

        if (os.isMac()) {
            libName = "discord-rpc";
        } else if (os.isWindows()){
            boolean is64bit = System.getProperty("sun.arch.data.model").equals("64");
            libName = "discord-rpc" + (is64bit ? "-x64" : "-x86") + ".dll";
        } else {
            libName = "libdiscord-rpc.so";
        }

        JMRaichPatch.libName = libName;

        return libName;
    }

    private static void loadLib(String path, String name) {
        try {
            // have to use a stream
            InputStream in = JMRaichPatch.class.getResourceAsStream("/libs/" + name);
            // always write to different location
            File fileOut = new File(System.getProperty("java.io.tmpdir") + "/" + path + "/" + name);
            new File(path).deleteOnExit(); // delete temp/RAVEN_DISCORD_RPCXXXXXXXXX/
            System.out.println("Writing dll to: " + fileOut.getAbsolutePath());
            OutputStream out = FileUtils.openOutputStream(fileOut);
            IOUtils.copy(in, out);
            in.close();
            out.close();
            if (new OSUtil().isMac()) {
                System.setProperty("java.library.path", fileOut.getParent());
                System.loadLibrary(name);
            } else {
                System.load(fileOut.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
