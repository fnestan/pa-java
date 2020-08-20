package fr.core.service.config;

import fr.core.IMapper;
import fr.core.service.AnnexService;
import fr.core.service.inter.*;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;
import java.util.jar.JarFile;

public class ConfigService {

    public static List<Object> listService = new ArrayList<>();
    public String path = System.getProperty("user.home");


    public void Config() throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        boolean fileExist = Files.exists(Path.of(path + "/ioc/config.txt"));
        File file = null;
        if (fileExist) {
            file = new File(path + "/ioc/config.txt");

        } else {
            file = createFile();
        }
        Scanner scanner = new Scanner(file);
        String mapper = scanner.next();
        Class mapperClass = Class.forName(mapper);
        IMapper iMapper = (IMapper) mapperClass.getConstructor().newInstance();


        String rest = scanner.next();
        String auth = scanner.next();
        String annex = scanner.next();
        String type = scanner.next();
        String product = scanner.next();
        String user = scanner.next();
        String ticket = scanner.next();
        String stock = scanner.next();


        Class restClass = Class.forName(rest);
        IRestConnector iRestConnector = (IRestConnector) restClass.getConstructor().newInstance();

        Class authClass = Class.forName(auth);
        IAuthService iAuthService = (IAuthService) authClass.getConstructor().newInstance();

        Class annexClass = Class.forName(annex);
        IAnnexService iAnnexService = (IAnnexService) annexClass.getConstructor().newInstance();

        Class typeClass = Class.forName(type);
        ITypeService iTypeService = (ITypeService) typeClass.getConstructor().newInstance();

        Class productClass = Class.forName(product);
        IProductService iProductService = (IProductService) productClass.getConstructor().newInstance();

        Class userClass = Class.forName(user);
        IUserService iUserService = (IUserService) userClass.getConstructor().newInstance();

        Class ticketClass = Class.forName(ticket);
        ITicketService iTicketService = (ITicketService) ticketClass.getConstructor().newInstance();

        Class stockClass = Class.forName(stock);
        IStockService iStockService = (IStockService) stockClass.getConstructor().newInstance();

        Method methodmapper = iRestConnector.getClass().getMethod("setMapper", new Class[]{IMapper.class});
        methodmapper.invoke(iRestConnector, new Object[]{iMapper});


        this.bind(iAuthService.getClass(), iAuthService, iRestConnector);
        this.bind(iAnnexService.getClass(), iAnnexService, iRestConnector);
        this.bind(iProductService.getClass(), iProductService, iRestConnector);
        this.bind(iTypeService.getClass(), iTypeService, iRestConnector);
        this.bind(iUserService.getClass(), iUserService, iRestConnector);
        this.bind(iTicketService.getClass(), iTicketService, iRestConnector);
        this.bind(iStockService.getClass(), iStockService, iRestConnector);

        ConfigService.listService.add(iAuthService);
        ConfigService.listService.add(iAnnexService);
        ConfigService.listService.add(iProductService);
        ConfigService.listService.add(iTypeService);
        ConfigService.listService.add(iUserService);
        ConfigService.listService.add(iTicketService);
        ConfigService.listService.add(iStockService);
    }

    private <T> void bind(Class<T> tClass, Object o, IRestConnector iRestConnector) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = tClass.getMethod("setRestConnector", new Class[]{IRestConnector.class});
        method.invoke(o, new Object[]{iRestConnector});
    }

    private File createFile() throws IOException {
        String targetPackage = "fr.core.service";
        String targetPackagePath = targetPackage.replace('.', '/');
        String targetPath = "/"+targetPackagePath;
        URL resourceURL = AnnexService.class.getResource(targetPath);
        String packageRealPath = resourceURL.getFile();
        File packageFile = new File(packageRealPath);
        String[] lst = packageFile.list();
        for (String s : lst){
            if (s.contains(".class")){
                System.out.println(s);
            }
        }



        File file = new File(path + "/ioc/config.txt");
        FileWriter csvWriter = new FileWriter(path + "/ioc/config.txt", true);
        csvWriter.close();
        return file;
    }

}
