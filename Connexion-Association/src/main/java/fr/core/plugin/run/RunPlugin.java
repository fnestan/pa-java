package fr.core.plugin.run;

import fr.plugin.IPluginCA;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.jar.JarFile;

public class RunPlugin {
    public static List<Object> iPluginCAS = new ArrayList<>();
    public static List<URL> urls = new ArrayList<>();
    private String basePath = System.getProperty("user.home") + "/pluginsLoads";


    public <T> void runplugin(String text, T t) throws IOException, ReflectiveOperationException {
        Optional<Object> p = RunPlugin.iPluginCAS.stream().filter(ca -> ca.toString().contains(text)).findFirst();

        if (p.get().toString().contains("Controller")) {
            Object controller = p.get();
            Optional<URL> url = RunPlugin.urls.stream().filter(ca -> ca.toString().toLowerCase().contains(text.split("Controller")[0].toLowerCase())).findFirst();
            run(controller, url.get());
        } else {
            runP(p.get(), t);
        }
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
                if (tmp.contains("Controller")) {
                    pluginName = tmpClass.toString().split(" ")[1];
                    cl = tmpClass;
                    String[] tmpPName = pluginName.split("\\.");
                    pluginName = tmpPName[tmpPName.length - 1];
                    pluginName = pluginName.split("Controller")[0];
                    RunPlugin.iPluginCAS.add((Object) cl.getConstructor().newInstance());
                    String v = "/" + pluginName + "View.fxml";
                    URL url = cl.getConstructor().newInstance().getClass().getResource(v);
                    if (url == null) {
                        throw new ClassNotFoundException("FXML view not found");
                    }
                    RunPlugin.urls.add(cl.getConstructor().newInstance().getClass().getResource("/Todo.fxml"));
                    loader.close();
                    jar.close();
                    return pluginName;
                }
                for (int i = 0; i < tmpClass.getInterfaces().length; i++) {
                    if (tmpClass.getInterfaces()[i].getName().toString().equals("fr.plugin.IPluginCA")) {
                        cl = tmpClass;
                        pluginName = tmpClass.toString().split(" ")[1];
                        RunPlugin.iPluginCAS.add((Object) cl.getConstructor().newInstance());
                        loader.close();
                        jar.close();
                        return pluginName;
                    }
                }
            }
        }
        throw new ClassNotFoundException("Class not found");
    }

    public void run(Object controller, URL url) {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            fxmlLoader.setController(controller);
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public <T> T runP(Object c, T t) throws ReflectiveOperationException {
        IPluginCA tmpPlugins = (IPluginCA) c;
        tmpPlugins.pluginProcessing(t);
        return null;
    }

    public void deletePlugin(String text) throws IOException, URISyntaxException {
        File file = new File(basePath + "/plugins.csv");
        Files.copy(Path.of(file.getAbsolutePath()), Path.of(basePath + "//pluginsold.csv"));
        Files.deleteIfExists(Path.of(basePath + "/pluginsLoads/plugins.csv"));
        Files.createFile(Path.of(file.getAbsolutePath()));
        FileWriter csvWriter = new FileWriter(basePath + "/pluginsLoads/plugins.csv", true);
        BufferedReader csvReader = new BufferedReader(new FileReader(basePath + "/pluginsLoads/pluginsold.csv"));
        String row = null;
        long lines = Files.lines(Path.of(basePath + "/pluginsLoads/pluginsold.csv")).count();
        if (lines > 0) {
            long line = 0;
            while (line < lines) {
                try {
                    while ((row = csvReader.readLine()) != null) {
                        String[] data = row.split(";");
                        if (!data[1].equals(text)) {
                            csvWriter.append(row + "\n");
                        }
                        if (data[1].equals(text)) {
                            Path path = Path.of(data[0]);
                            Files.deleteIfExists(path);
                        }
                        line++;
                    }
                } catch (NullPointerException | FileSystemException e) {
                    e.getStackTrace();
                }

            }
        }
        csvReader.close();
        Files.delete(Path.of(basePath + "/pluginsLoads/pluginsold.csv"));
        csvWriter.close();

    }
}
