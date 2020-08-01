package fr.core.service.config;

import fr.core.IMapper;
import fr.core.service.inter.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;
import java.util.jar.JarFile;

public class ConfigService {

    public static List<Object> listService = new ArrayList<>();

    public void Config() throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String path = System.getProperty("user.home");
        File file = new File( path + "/ioc/c.txt");
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

        Method methodmapper = iRestConnector.getClass().getMethod("setMapper", new Class[]{IMapper.class});
        methodmapper.invoke(iRestConnector, new Object[]{iMapper});


        this.bind(iAuthService.getClass(), iAuthService, iRestConnector);
        this.bind(iAnnexService.getClass(), iAnnexService, iRestConnector);
        this.bind(iProductService.getClass(), iProductService, iRestConnector);
        this.bind(iTypeService.getClass(), iTypeService, iRestConnector);
        this.bind(iUserService.getClass(), iUserService, iRestConnector);
        this.bind(iTicketService.getClass(), iTicketService, iRestConnector);

        ConfigService.listService.add(iAuthService);
        ConfigService.listService.add(iAnnexService);
        ConfigService.listService.add(iProductService);
        ConfigService.listService.add(iTypeService);
        ConfigService.listService.add(iUserService);
        ConfigService.listService.add(iTicketService);
    }

    private <T> void bind(Class<T> tClass, Object o, IRestConnector iRestConnector) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = tClass.getMethod("setRestConnector", new Class[]{IRestConnector.class});
        method.invoke(o, new Object[]{iRestConnector});
    }
}
