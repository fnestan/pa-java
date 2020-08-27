package fr.core.ui.Controller;

import fr.core.model.customModel.PluginModelData;
import fr.core.model.customModel.Session;
import fr.core.model.databaseModel.Ticket;
import fr.core.plugin.run.RunPlugin;
import fr.core.service.config.ConfigService;
import fr.core.service.inter.IAnnexService;
import fr.core.service.inter.ITicketService;
import fr.core.ui.Controller.manager.GetTicketController;
import fr.core.ui.Controller.manager.HomeController;
import fr.core.ui.Controller.manager.TicketController;
import fr.core.ui.ControllerRouter;
import fr.core.ui.Router;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class MenuBarLoader {
    RunPlugin runPlugin = new RunPlugin();
    private String basePath = System.getProperty("user.home") + "/pluginsLoads";
    public static Object currentData;


    public MenuBar LoadMenuBar(MenuBar menuBar, Router router) throws Exception {
        boolean fileExist = Files.exists(Path.of(basePath + "/plugins.csv"));
        if (!fileExist) {
            Files.createDirectory(Path.of(basePath));
            Files.createFile(Path.of(basePath + "/plugins.csv"));
        }
        BufferedReader csvReader = new BufferedReader(new FileReader(basePath + "/plugins.csv"));
        String row = null;
        Stage stage = new Stage();
        final FileChooser fileChooser = new FileChooser();
        Menu plugin = new Menu("Plugins");
        Menu viewMenu = new Menu("Menu");
        MenuItem Home = new MenuItem("Home");
        MenuItem myTickets = null;
        if (Session.user.getRoleId() == 3) {
            myTickets = new MenuItem("Tous les tickets");
        } else {
            myTickets = new MenuItem("Mes tickets");
        }
        MenuItem newTicket = new MenuItem("Nouveau Ticket");
        viewMenu.getItems().addAll(Home, myTickets, newTicket);
        long lines = Files.lines(Path.of(basePath + "/plugins.csv")).count();
        if (lines > 0) {
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(";");
                Menu menu = new Menu(data[1]);
                runPlugin.load(data[0]);
                if (data[3].equals("ManRun")) {
                    MenuItem exec = new MenuItem("Exécuter");
                    menu.getItems().add(exec);
                    exec.setOnAction(actionEvent -> {
                        try {
                            csvReader.close();
                            runPlugin.runplugin(menu.getText(), currentData.toString());
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (ReflectiveOperationException e) {
                            e.printStackTrace();
                        }
                    });
                }
                if (data[2].equals("ON")){
                    MenuItem desactivate = new MenuItem("Désactiver");
                    menu.getItems().add(desactivate);
                    desactivate.setOnAction(event -> {
                        try {
                            runPlugin.setPluginStatus(data[0],"OFF");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }else {
                    MenuItem activate = new MenuItem("Activer");
                    menu.getItems().add(activate);
                    activate.setOnAction(event -> {
                        try {
                            runPlugin.setPluginStatus(data[0],"ON");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
                plugin.getItems().add(menu);
            }
        }
        csvReader.close();
        MenuItem addPlugin = new MenuItem("Ajouter un plugin");
        plugin.getItems().addAll(addPlugin);
        menuBar.getMenus().addAll(viewMenu, plugin);
        Home.setOnAction(event -> {
            Optional<Object> iAnnexService = ConfigService.listService.stream().filter(o -> o.toString().contains("AnnexService")).findFirst();
            router.<HomeController>goTo("view/manager/Home", controller -> {
                try {
                    controller.setAnnexService((IAnnexService) iAnnexService.get());
                    controller.setRouter(router);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });
        myTickets.setOnAction(event -> {
            try {
                ControllerRouter.geneRouter(router, TicketController.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
        });
        newTicket.setOnAction(event -> {
            Optional<Object> ticketService = ConfigService.listService.stream().filter(o -> o.toString().contains("TicketService")).findFirst();
            Stage stage1 = new Stage();
            Pane pane = new Pane();
            VBox vBox = new VBox();
            Label label = new Label("Nom du ticket : ");
            TextField ticketName = new TextField();
            Button button = new Button("Créer");
            vBox.getChildren().add(label);
            vBox.getChildren().add(ticketName);
            vBox.getChildren().add(button);
            pane.getChildren().add(vBox);
            Scene scene = new Scene(pane);
            stage1.setTitle("Nouveau ticket");
            stage1.setScene(scene);
            stage1.show();
            button.setOnAction(event1 -> {
                ITicketService iTicketService = (ITicketService) ticketService.get();
                Optional<Ticket> ticket = iTicketService.createTicket(label.getText());
                GetTicketController.ticketId = ticket.get().getId();
                try {
                    ControllerRouter.geneRouter(router, GetTicketController.class);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }
                stage1.close();

            });
        });

        addPlugin.setOnAction(ActionEvent -> {
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                try {
                    csvReader.close();
                    this.addPlugin(file);
                    menuBar.getMenus().clear();
                    this.LoadMenuBar(menuBar, router);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return menuBar;
    }


    private void addPlugin(File file) throws IOException, ReflectiveOperationException {
        try {
            RunPlugin runPlugin = new RunPlugin();
            String load = runPlugin.load(file.getAbsolutePath());
            File f = file;
            String type = runPlugin.getPluginType();
            File fif = new File(basePath);
            FileWriter csvWriter = new FileWriter(basePath + "/plugins.csv", true);
            csvWriter.append(fif.getAbsolutePath() + "/" + f.getName() + ";" + load + ";OFF" + ";" + type + "\n");
            csvWriter.close();
            Files.copy(Path.of(f.getAbsolutePath()), Path.of(fif.getAbsolutePath() + "/" + f.getName()));
        } catch (ClassNotFoundException classNotFoundException) {
            System.out.println(classNotFoundException.getMessage());
        }

    }
}
