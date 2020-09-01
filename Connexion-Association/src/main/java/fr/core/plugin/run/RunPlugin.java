package fr.core.plugin.run;

import fr.plugin.IPluginCA;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.jar.JarFile;

public class RunPlugin {
    public static List<Object> iPluginCAS = new ArrayList<>();
    public String pluginType;
    private String basePath = System.getProperty("user.home") + "/pluginsLoads";


    public <T> void runplugin(String text, T t) throws IOException, ReflectiveOperationException {
        Optional<Object> p = RunPlugin.iPluginCAS.stream().filter(ca -> ca.toString().contains(text)).findFirst();
        runP(p.get(), t);
    }

    public String load(String path) throws IOException, ReflectiveOperationException {
        Class cl = null;
        String pluginName = null;
        File file = new File(path);
        URL u = null;
        try {
            u = file.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URLClassLoader loader = new URLClassLoader(new URL[]{u});
        JarFile jar = null;
        try {
            jar = new JarFile(file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Enumeration enumeration;
        enumeration = jar.entries();
        String tmp = "";
        Class tmpClass = null;
        while (enumeration.hasMoreElements()) {
            tmp = enumeration.nextElement().toString();
            if (tmp.length() > 6 && tmp.substring(tmp.length() - 6).compareTo(".class") == 0) {
                tmp = tmp.substring(0, tmp.length() - 6);
                tmp = tmp.replaceAll("/", ".");
                tmpClass = Class.forName(tmp, true, loader);
                for (int i = 0; i < tmpClass.getInterfaces().length; i++) {
                    if (tmpClass.getInterfaces()[i].getName().toString().equals("fr.plugin.IPluginCA")) {
                        cl = tmpClass;
                        pluginName = tmpClass.toString().split(" ")[1];
                        Object o = (Object) cl.getConstructor().newInstance();
                        IPluginCA tmpPlugins = (IPluginCA) o;
                        this.pluginType = tmpPlugins.getPluginType();
                        RunPlugin.iPluginCAS.add(o);
                        loader.close();
                        jar.close();
                        return pluginName;
                    }
                }
            }
        }
        throw new ClassNotFoundException("Class not found");
    }

    public <T> T runP(Object object, T t) throws ReflectiveOperationException {
        IPluginCA tmpPlugins = (IPluginCA) object;
        tmpPlugins.pluginProcessing(t);
        return null;
    }

    public String getPluginType() {
        return pluginType;
    }

    public void setPluginStatus(String datum, String status) throws IOException {
        BufferedReader csvReader = new BufferedReader(new FileReader(basePath + "/plugins.csv"));
        String row = null;
        String file = "";
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(";");
            if (data[0].equals(datum)) {
                data[2] = status;
            }
            file.concat(data[0]);
            file.concat(data[1]);
            file.concat(data[2]);
            file.concat(data[3]);
            file.concat("\n");

        }
        csvReader.close();
        FileWriter csvWriter = new FileWriter(basePath + "/plugins.csv", false);
        csvWriter.write(file);
        csvWriter.close();
    }
}
