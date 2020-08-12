package fr.core.ui;

import fr.core.service.config.ConfigService;
import fr.core.service.inter.*;
import fr.core.ui.Controller.LoginController;
import fr.core.ui.Controller.admin.UserController;
import fr.core.ui.Controller.manager.*;
import fr.core.service.inter.*;
import fr.core.ui.Controller.manager.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public class ControllerRouter {
    public static <T> void geneRouter(Router router, T controllerN) throws NoSuchMethodException, IllegalAccessException, InstantiationException, FileNotFoundException, InvocationTargetException, ClassNotFoundException {
        Optional<Object> iAnnexService = ConfigService.listService.stream().filter(o -> o.toString().contains("AnnexService")).findFirst();
        Optional<Object> ticketService = ConfigService.listService.stream().filter(o -> o.toString().contains("TicketService")).findFirst();
        String[] controllerNameSplit = controllerN.toString().split(" ");
        controllerNameSplit = controllerNameSplit[1].split("\\.");
        String controllerName = controllerNameSplit[controllerNameSplit.length - 1];
        System.out.printf(controllerName);
        switch (controllerName) {
            case "LoginController":
                Optional<Object> iAuthService = ConfigService.listService.stream().filter(o -> o.toString().contains("AuthService")).findFirst();
                router.<LoginController>goTo("view/Login", controller -> {
                    controller.setRouter(router);
                    controller.setAuthService((IAuthService) iAuthService.get());
                });
                break;
            case "HomeController":
                router.<HomeController>goTo("view/manager/Home", controller -> {
                    try {
                        controller.setRouter(router);
                        controller.setAnnexService((IAnnexService) iAnnexService.get());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "TicketController":
                router.<TicketController>goTo("view/manager/Ticket", controller -> {
                    try {
                        controller.setTicketService((ITicketService) ticketService.get());
                        controller.setRouter(router);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "GetTicketController":
                router.<GetTicketController>goTo("view/manager/getTicket", controller -> {
                    try {
                        controller.setiTicketService((ITicketService) ticketService.get());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        controller.setRouter(router);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "AnnexDetailController":
                Optional<Object> typeService = ConfigService.listService.stream().filter(o -> o.toString().contains("TypeService")).findFirst();
                Optional<Object> productService = ConfigService.listService.stream().filter(o -> o.toString().contains("ProductService")).findFirst();

                router.<AnnexDetailController>goTo("view/manager/AnnexDetail", controller -> {
                    try {
                        controller.setRouter(router);
                        controller.setAnnexService((IAnnexService) iAnnexService.get());
                        controller.setProductService((IProductService) productService.get());
                        controller.setTypeService((ITypeService) typeService.get());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "UserController":
                Optional<Object> userService = ConfigService.listService.stream().filter(o -> o.toString().contains("UserService")).findFirst();
                router.<UserController>goTo("view/admin/User", controller -> {
                    try {
                        controller.setUserService((IUserService) userService.get());
                        controller.setRouter(router);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "GetDonationController":
                router.<GetDonationController>goTo("view/manager/getDonation", controller -> {
                    try {
                        controller.setiAnnexService((IAnnexService) iAnnexService.get());
                        controller.setRouter(router);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "GetServiceController":
                router.<GetServiceController>goTo("view/manager/getService", controller -> {
                    try {
                        controller.setiAnnexService((IAnnexService) iAnnexService.get());
                        controller.setRouter(router);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                break;

            case "ParticipateUserController":
                router.<ParticipateUserController>goTo("view/manager/ParticipateUserAction", controller -> {
                    try {
                        controller.setRouter(router);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                break;
        }
    }
}
