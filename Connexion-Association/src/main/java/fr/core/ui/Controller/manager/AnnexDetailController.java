package fr.core.ui.Controller.manager;

import fr.core.model.customModel.*;
import fr.core.model.databaseModel.*;
import fr.core.service.inter.IAnnexService;
import fr.core.service.inter.IProductService;
import fr.core.service.inter.ITypeService;
import fr.core.ui.Controller.MenuBarLoader;
import fr.core.ui.ControllerRouter;
import fr.core.ui.Router;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class AnnexDetailController {

    @FXML
    Button stock;
    private Router router;

    public static Integer idAnnex;

    @FXML
    MenuBar menuBar;

    @FXML
    BorderPane border;

    @FXML
    Button detail;

    @FXML
    Button gerant;

    @FXML
    Button horaire;

    @FXML
    Button donation;

    @FXML
    TextField name;

    @FXML
    TextArea description;
    @FXML
    TextField street;
    @FXML
    TextField city;
    @FXML
    TextField zipcode;
    @FXML
    TextField email;
    @FXML
    TextField phone;

    Optional<Annex> annex;

    @FXML
    VBox vBox;

    @FXML
    HBox lundi;
    @FXML
    HBox mardi;
    @FXML
    HBox mercredi;
    @FXML
    HBox jeudi;
    @FXML
    HBox vendredi;
    @FXML
    HBox samedi;
    @FXML
    HBox dimanche;
    @FXML
    VBox vboxDay;
    @FXML
    TextField serviceName;
    @FXML
    TextField serviceQuantite;
    @FXML
    TextArea serviceDescription;
    @FXML
    Button create;
    @FXML
    DatePicker date;
    @FXML
    ChoiceBox hour;
    @FXML
    ChoiceBox min;
    @FXML
    TextField donationName;
    @FXML
    TextArea donationDescription;
    @FXML
    Button createDonation;
    @FXML
    TableView tableProduct;
    @FXML
    ChoiceBox<Product> productList;
    @FXML
    ChoiceBox productQuantity;
    @FXML
    Button addProduct;
    @FXML
    TextField search;
    @FXML
    Button newPrd;
    @FXML
    ChoiceBox<Type> productType;
    @FXML
    Button saveProduct;

    @FXML
    Button update;
    @FXML
    TextField prdName;

    @FXML
    Button service;

    @FXML
    VBox servicelistVbox;

    @FXML
    VBox donationlistVbox;


    @FXML
    Button callServiceformButton;

    @FXML
    Button mondayAdd;
    @FXML
    Button tuesdayAdd;
    @FXML
    Button wednesdayAdd;
    @FXML
    Button thursdayAdd;
    @FXML
    Button fridayAdd;
    @FXML
    Button saturdayAdd;
    @FXML
    Button sundayAdd;

    @FXML
    TextField ouverture;
    @FXML
    TextField fermeture;

    @FXML
    Button creerHoraire;

    Integer nbStageCreationProduct = 0;

    List<Integer> hours = new ArrayList<>();
    List<Integer> mins = new ArrayList<>();

    IAnnexService annexService;

    ITypeService typeService;

    IProductService productService;


    @FXML
    public void initialize() throws Exception {
        for (int i = 0; i < 24; i++) {
            hours.add(i);
        }
        for (int i = 0; i < 60; i++) {
            mins.add(i);
        }
    }

    public void setAnnexService(IAnnexService annexService) throws Exception {
        this.annexService = annexService;
        init();
    }

    public void setTypeService(ITypeService typeService) {
        this.typeService = typeService;
    }

    public void setProductService(IProductService productService) {
        this.productService = productService;
    }

    public void init() throws Exception {
        this.findAnnex();
        this.genericDetail();
        this.detail.setOnAction(event -> {
            try {
                this.genericDetail();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
        this.gerant.setOnAction(event -> {
            try {
                this.managersIntel();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        this.horaire.setOnAction(event -> {
            try {
                this.availabilities();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        this.stock.setOnAction(event -> {
            try {
                AnnexStockController.AnnexId = annex.get().getId();
                ControllerRouter.geneRouter(router,AnnexStockController.class);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
        });
    }

    /**
     * methode pour initialiser la fenetre des horaires
     *
     * @throws IOException
     */
    private void availabilities() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/manager/AvailabilityView.fxml"));
        Pane splitPane = (Pane) fxmlLoader.load();
        border.setCenter(splitPane);
        Pane p = (Pane) border.getChildren().get(0);
        List box = (List) p.getChildren();
        vboxDay = (VBox) box.get(0);
        lundi = (HBox) vboxDay.getChildren().get(0);
        mardi = (HBox) vboxDay.getChildren().get(1);
        mercredi = (HBox) vboxDay.getChildren().get(2);
        jeudi = (HBox) vboxDay.getChildren().get(3);
        vendredi = (HBox) vboxDay.getChildren().get(4);
        samedi = (HBox) vboxDay.getChildren().get(5);
        dimanche = (HBox) vboxDay.getChildren().get(6);
        mondayAdd = (Button) lundi.getChildren().get(1);
        tuesdayAdd = (Button) mardi.getChildren().get(1);
        wednesdayAdd = (Button) mercredi.getChildren().get(1);
        thursdayAdd = (Button) jeudi.getChildren().get(1);
        fridayAdd = (Button) vendredi.getChildren().get(1);
        saturdayAdd = (Button) samedi.getChildren().get(1);
        sundayAdd = (Button) dimanche.getChildren().get(1);
        mondayAdd.setOnAction(event -> {
            try {
                createAvailability(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        tuesdayAdd.setOnAction(event -> {
            try {
                createAvailability(2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        wednesdayAdd.setOnAction(event -> {
            try {
                createAvailability(3);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thursdayAdd.setOnAction(event -> {
            try {
                createAvailability(4);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        fridayAdd.setOnAction(event -> {
            try {
                createAvailability(5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        saturdayAdd.setOnAction(event -> {
            try {
                createAvailability(6);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        sundayAdd.setOnAction(event -> {
            try {
                createAvailability(7);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        lundi.setSpacing(20);
        mardi.setSpacing(20);
        mercredi.setSpacing(20);
        jeudi.setSpacing(20);
        vendredi.setSpacing(20);
        samedi.setSpacing(20);
        dimanche.setSpacing(20);
        this.getAvailabilities();
    }

    private void createAvailability(int i) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/manager/createAvailabilityView.fxml"));
        Parent view = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Créer un horaire");
        Scene scene = new Scene(view);
        stage.setScene(scene);
        stage.show();
        ouverture = (TextField) view.getChildrenUnmodifiable().get(3);
        fermeture = (TextField) view.getChildrenUnmodifiable().get(4);
        creerHoraire = (Button) view.getChildrenUnmodifiable().get(5);
        creerHoraire.setOnAction(event -> {
            if (ouverture.getText().matches("\\d{2}:\\d{2}") &&
                    fermeture.getText().matches("\\d{2}:\\d{2}")) {
                AnnexAvailability annexAvailability = new AnnexAvailability();
                annexAvailability.setOpeningTime(Time.valueOf(LocalTime.parse(ouverture.getText())));
                annexAvailability.setClosingTime(Time.valueOf(LocalTime.parse(fermeture.getText())));
                annexAvailability.setDayId(i);
                try {
                    annex = annexService.createAvailability(annex.get().getId(), annexAvailability);
                    stage.close();
                    this.availabilities();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Veuiller respecter le format s'il vous plait");
                alert.showAndWait();
            }
        });
    }

    /**
     * affichage des horaire pour une annexe
     */
    private void getAvailabilities() {
        for (AnnexAvailability annexAvailability : annex.get().getAnnexAvailabilities()) {
            String day = annexAvailability.getDay().getName();
            Label openning = new Label(annexAvailability.getOpeningTime().toString());
            Label closing = new Label(annexAvailability.getClosingTime().toString());
            Button delete = new Button("-");
            HBox.setMargin(openning, new Insets(25, 1, 1, 1));
            HBox.setMargin(closing, new Insets(25, 1, 1, 1));
            HBox.setMargin(delete, new Insets(25, 1, 1, 1));

            switch (day) {
                case "Lundi":
                    this.lundi.getChildren().add(openning);
                    this.lundi.getChildren().add(closing);
                    this.lundi.getChildren().add(delete);
                    break;
                case "Mardi":
                    this.mardi.getChildren().add(openning);
                    this.mardi.getChildren().add(closing);
                    this.mardi.getChildren().add(delete);
                    break;
                case "Mercredi":
                    this.mercredi.getChildren().add(openning);
                    this.mercredi.getChildren().add(closing);
                    this.mercredi.getChildren().add(delete);
                    break;
                case "Jeudi":
                    this.jeudi.getChildren().add(openning);
                    this.jeudi.getChildren().add(closing);
                    this.jeudi.getChildren().add(delete);
                    break;
                case "Vendredi":
                    this.vendredi.getChildren().add(openning);
                    this.vendredi.getChildren().add(closing);
                    this.vendredi.getChildren().add(delete);
                    break;
                case "Samedi":
                    this.samedi.getChildren().add(openning);
                    this.samedi.getChildren().add(closing);
                    this.samedi.getChildren().add(delete);
                    break;
                case "Dimanche":
                    this.dimanche.getChildren().add(openning);
                    this.dimanche.getChildren().add(closing);
                    this.dimanche.getChildren().add(delete);
                    break;
            }
            delete.setOnAction(event -> {
                try {
                    annex = annexService.deleteAvailability(annexAvailability.getId());
                    this.availabilities();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * recuperer l'annexe via son id
     *
     * @throws Exception
     */
    private void findAnnex() throws Exception {
        annex = annexService.getAnnexById(idAnnex);

    }

    /**
     * @throws IOException
     */
    private void managersIntel() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/manager/managerView.fxml"));
        Pane splitPane = (Pane) fxmlLoader.load();
        border.setCenter(splitPane);
        this.getManagers();

    }

    private void getManagers() {
        Pane p = (Pane) border.getChildren().get(0);
        List box = (List) p.getChildren();
        vBox = (VBox) box.get(0);
        vBox.setSpacing(20);
        for (User user : annex.get().getUsers()) {
            HBox hBox = new HBox();
            hBox.setSpacing(10);
            Label label = new Label(user.getFirstname() + " " + user.getLastname());
            label.setTextAlignment(TextAlignment.JUSTIFY);
            hBox.getChildren().add(label);
            if (Session.user.getId() != user.getId()) {
                Button supprimer = new Button("Supprimer");
                supprimer.setId(String.valueOf(user.getId()));
                hBox.getChildren().add(supprimer);
                supprimer.setOnAction(event -> {
                    Integer idManager = Integer.parseInt(supprimer.getId());
                    try {
                        deleteManager(idManager);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
            vBox.getChildren().add(hBox);
        }
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        TextField manager = new TextField();
        Button ajouter = new Button("Ajouter");
        ajouter.setDisable(true);
        hBox.getChildren().addAll(manager, ajouter);
        vBox.getChildren().add(hBox);
        manager.setOnKeyTyped(keyEvent -> {
            if (manager.getText().equals("")) {
                ajouter.setDisable(true);
            } else {
                ajouter.setDisable(false);
            }
        });
        ajouter.setOnAction(event -> {
            try {
                this.addManager(manager.getText());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void deleteManager(Integer idManager) throws Exception {
        Optional<Information> res = annexService.removeManager(annex.get().getId(), idManager);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Suppression d'un manager");
        alert.setContentText(res.get().message);
        alert.showAndWait();
        ControllerRouter.geneRouter(router, AnnexDetailController.class);
    }

    private void addManager(String text) throws Exception {
        Manager manager = new Manager();
        manager.email = text;
        Optional<Information> res = annexService.addManager(annex.get().getId(), manager);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ajout d'un manager");
        alert.setContentText(res.get().message);
        alert.showAndWait();
        if (res.equals("Le manager à bien été ajouter")) {
            ControllerRouter.geneRouter(router, AnnexDetailController.class);
        }
    }

    public void setRouter(Router router) throws Exception {
        this.router = router;
        MenuBarLoader menuBarLoader = new MenuBarLoader();
        menuBarLoader.LoadMenuBar(menuBar, router);
    }

    private void genericDetail() throws IOException, InterruptedException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/manager/annexView.fxml"));
        Pane splitPane = fxmlLoader.load();
        border.setCenter(splitPane);
        this.getGenericDetail();

    }

    private void getGenericDetail() {
        Pane p = (Pane) border.getChildren().get(0);
        List textField = (List) p.getChildren();
        name = (TextField) textField.get(0);
        name.setDisable(true);
        phone = (TextField) textField.get(1);
        phone.setDisable(true);
        email = (TextField) textField.get(2);
        email.setDisable(true);
        street = (TextField) textField.get(3);
        street.setDisable(true);
        city = (TextField) textField.get(4);
        city.setDisable(true);
        zipcode = (TextField) textField.get(5);
        zipcode.setDisable(true);
        description = (TextArea) textField.get(6);
        description.setWrapText(true);
        description.setDisable(true);
        update = (Button) textField.get(7);
        donation = (Button) textField.get(8);
        service = (Button) textField.get(9);
        name.setText(annex.get().getName());
        phone.setText(annex.get().getPhone());
        email.setText(annex.get().getEmail());
        street.setText(annex.get().getStreet());
        city.setText(annex.get().getCity());
        zipcode.setText(annex.get().getZipCode());
        description.setText(annex.get().getDescription());
        update.setOnAction(event -> {
            name.setDisable(false);
            email.setDisable(false);
            phone.setDisable(false);
            street.setDisable(false);
            city.setDisable(false);
            zipcode.setDisable(false);
            description.setDisable(false);
            update.setOnAction(event1 -> {
                String regex = "^[-a-z0-9~!$%^&*_=+}{\'?]+(\\.[-a-z0-9~!$%^&*_=+}{\'?]+)*@([a-z0-9_][-a-z0-9_]*(\\.[-a-z0-9_]+)*\\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,5})?$";
                if (!phone.getText().matches("\\d{10}")) {
                    Alert alert = new Alert(null);
                    alert.setTitle("Modification d'une annexe");
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText("veuiller rentrer un telephone valide");
                    alert.showAndWait();
                   /* */
                } else if (!email.getText().matches(regex)){
                    Alert alert = new Alert(null);
                    alert.setTitle("Modification d'une annexe");
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText("Veuillez rentrer un email valide");
                    alert.showAndWait();
                } else if (!zipcode.getText().matches("\\d{5}")){
                    Alert alert = new Alert(null);
                    alert.setTitle("Modification d'une annexe");
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setContentText("Veuillez rentrer un code postal valide");
                    alert.showAndWait();
                }else {
                    annex.get().setDescription(description.getText());
                    annex.get().setEmail(email.getText());
                    annex.get().setCity(city.getText());
                    annex.get().setName(name.getText());
                    annex.get().setZipCode(zipcode.getText());
                    annex.get().setStreet(street.getText());
                    annex.get().setPhone(phone.getText());
                    Optional<Information> information = this.annexService.updateAnnex(annex.get());
                    Alert alert = new Alert(null);
                    alert.setTitle("Modification d'une annexe");
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setContentText(information.get().message);
                    alert.showAndWait();
                    try {
                        this.genericDetail();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        });
        service.setOnAction(event -> {
            try {
                this.listServices();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        donation.setOnAction(event -> {
            try {
                this.listDonation();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void listServices() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/manager/serviceListView.fxml"));
        Pane splitPane = fxmlLoader.load();
        border.setCenter(splitPane);
        Pane p = (Pane) border.getChildren().get(0);
        Optional<List<Service>> optionalServices = annexService.listServices(this.idAnnex);
        ScrollPane scrollPane = (ScrollPane) p.getChildren().get(0);
        Button newService = (Button) p.getChildren().get(1);
        newService.setOnAction(event -> {
            try {
                this.createService();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        this.servicelistVbox = (VBox) scrollPane.getContent();
        if (optionalServices.isPresent()) {
            if (optionalServices.get().size() > 0) {
                for (Service service : optionalServices.get()) {
                    HBox hBox = new HBox();
                    hBox.setSpacing(20.0);
                    servicelistVbox.setSpacing(10.0);
                    Label label = new Label(service.getNom());
                    Button read = new Button("Consulter");
                    Button delete = new Button("Supprimer");
                    read.setOnAction(event -> {
                        Integer idService = service.getId();
                        try {
                            GetServiceController.serviceId = idService;
                            ControllerRouter.geneRouter(router,GetServiceController.class);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    delete.setOnAction(event -> {
                        Integer idService = service.getId();
                        try {
                            deleteService(idService);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                    hBox.getChildren().add(label);
                    hBox.getChildren().add(read);
                    hBox.getChildren().add(delete);
                    servicelistVbox.getChildren().add(hBox);
                }
            } else {
                HBox hBox = new HBox();
                hBox.getChildren().add(new Label("Vous n'avez aucun service consultable"));
                servicelistVbox.getChildren().add(hBox);
            }
        }
    }

    public void createService() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/manager/createServiceView.fxml"));
        Pane splitPane = fxmlLoader.load();
        border.setCenter(splitPane);
        Pane p = (Pane) border.getChildren().get(0);
        serviceName = (TextField) p.getChildren().get(1);
        serviceDescription = (TextArea) p.getChildren().get(4);
        serviceDescription.setWrapText(true);
        date = (DatePicker) p.getChildren().get(6);
        create = (Button) p.getChildren().get(7);
        serviceQuantite = (TextField) p.getChildren().get(9);
        hour = (ChoiceBox) p.getChildren().get(10);
        min = (ChoiceBox) p.getChildren().get(11);
        hour.setItems(FXCollections.observableList(hours));
        min.setItems(FXCollections.observableList(mins));
        create.setDisable(true);
        serviceName.setOnKeyTyped(keyEvent -> {
            if (!serviceName.getText().equals("") && !serviceDescription.getText().equals("")
                    && date.getValue() != null && !serviceQuantite.getText().equals("")
                    && serviceQuantite.getText().matches("[1-9]*")) {
                create.setDisable(false);
            }
        });
        serviceDescription.setOnKeyTyped(keyEvent -> {
            if (!serviceName.getText().equals("") && !serviceDescription.getText().equals("")
                    && date.getValue() != null && !serviceQuantite.getText().equals("")
                    && serviceQuantite.getText().matches("[1-9]*")) {
                create.setDisable(false);
            }
        });
        serviceQuantite.setOnKeyTyped(keyEvent -> {
            if (!serviceName.getText().equals("") && !serviceDescription.getText().equals("")
                    && date.getValue() != null && !serviceQuantite.getText().equals("")
                    && serviceQuantite.getText().matches("[1-9]*")) {
                create.setDisable(false);
            }
        });
        date.setOnAction(keyEvent -> {
            if (!serviceName.getText().equals("") && !serviceDescription.getText().equals("")
                    && date.getValue() != null && !serviceQuantite.getText().equals("")
                    && serviceQuantite.getText().matches("[1-9]*")) {
                create.setDisable(false);
            }
        });
        create.setOnAction(event -> {
            LocalDateTime localDateTime;
            LocalDate local = date.getValue();
            if (hour.getSelectionModel().getSelectedItem() == null || min.getSelectionModel().getSelectedItem() == null) {
                LocalTime localTime = LocalTime.of(2, 0);
                localDateTime = LocalDateTime.of(local, localTime);
            } else {
                int h = Integer.parseInt(hour.getSelectionModel().getSelectedItem().toString());
                int m = Integer.parseInt(min.getSelectionModel().getSelectedItem().toString());
                LocalTime localTime = LocalTime.of(h + 2, m);
                localDateTime = LocalDateTime.of(local, localTime);

            }
            Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            Service service = new Service();
            service.setAnnexId(annex.get().getId());
            service.setDate_service(date);
            service.setDescription(serviceDescription.getText());
            service.setNom(serviceName.getText());
            service.setQuantite(Integer.parseInt(serviceQuantite.getText()));
            try {
                Optional<Service> s = annexService.createService(service);
                Alert alert = new Alert(null);
                alert.setTitle("Création d'un service");
                if (s.isPresent()) {
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setContentText("Votre service " + s.get().getNom() + " a bien été créé");
                    alert.showAndWait();
                    ControllerRouter.geneRouter(router, AnnexDetailController.class);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void createDonationForm() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/manager/createGiftView.fxml"));
        Pane splitPane = fxmlLoader.load();
        border.setCenter(splitPane);
        Pane p = (Pane) border.getChildren().get(0);
        donationName = (TextField) p.getChildren().get(1);
        donationDescription = (TextArea) p.getChildren().get(4);
        donationDescription.setWrapText(true);
        tableProduct = (TableView) p.getChildren().get(6);
        createDonation = (Button) p.getChildren().get(5);
        productList = (ChoiceBox<Product>) p.getChildren().get(7);
        productQuantity = (ChoiceBox) p.getChildren().get(10);
        addProduct = (Button) p.getChildren().get(11);
        search = (TextField) p.getChildren().get(12);
        newPrd = (Button) p.getChildren().get(13);
        this.createDonation();
    }

    private void createDonation() {
        productQuantity.setItems(FXCollections.observableList(mins));
        productQuantity.getItems().remove(0);
        AtomicReference<List<Product>> productRequests = new AtomicReference<List<Product>>();
        search.setOnKeyTyped(keyEvent -> {
            SearchProduct searchProduct = new SearchProduct();
            searchProduct.name = search.getText();
            productRequests.set(productService.getProductByName(searchProduct).get());
            productList.setItems(FXCollections.observableList(productRequests.get()));

        });
        TableColumn productName = new TableColumn<ProductRequest, String>("nom");
        TableColumn productQuantityCol = new TableColumn<ProductRequest, Integer>("quantité");
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productQuantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tableProduct.getColumns().addAll(productName, productQuantityCol);
        productList.setConverter(new StringConverter<>() {
            @Override
            public String toString(Product productRequest) {
                return productRequest.getName();
            }

            @Override
            public Product fromString(String s) {
                return productList.getItems().stream().filter(ap ->
                        ap.getName().equals(s)).findFirst().orElse(null);
            }
        });
        newPrd.setOnAction(event -> {
            if (nbStageCreationProduct == 0) {
                try {
                    this.createProduct();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        addProduct.setOnAction(event -> {
            if (productList.getSelectionModel().getSelectedItem() == null
                    || productQuantity.getSelectionModel().getSelectedItem() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ajout d'un produit");
                alert.setContentText("La quantité ou le produit set null");
                alert.showAndWait();
            } else {
                Product product = productList.getSelectionModel().getSelectedItem();
                Integer quantyty = Integer.parseInt(productQuantity.getSelectionModel().getSelectedItem().toString());
                ProductRequest productRequest = new ProductRequest();
                productRequest.setName(product.getName());
                productRequest.setProductId(product.getId());
                productRequest.setQuantity(quantyty);
                tableProduct.getItems().add(productRequest);
            }

        });
        tableProduct.setOnMouseClicked(mouseEvent -> {
            tableProduct.getItems().remove(tableProduct.getSelectionModel().getSelectedIndex());
        });
        createDonation.setOnAction(event -> {
            if (donationName.getText() == null || donationDescription.getText() == null || tableProduct.getItems().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ajout d'une donation");
                alert.setContentText("L'un des champs obligatoire n'est pas renseigné");
                alert.showAndWait();
            } else {
                Donation donation = new Donation();
                System.out.println(donationName.getText());
                donation.setDescription(donationDescription.getText());
                donation.setNom(donationName.getText());
                donation.setAnnexId(this.annex.get().getId());
                tableProduct.getItems().forEach(request -> {
                    ProductRequest pr = (ProductRequest) request;
                    donation.getProductRequests().add(pr);
                });
                Optional<Donation> donation1 = annexService.createDonation(donation);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Ajout d'une donation");
                alert.setContentText("La donation " + donation1.get().getNom() + " a été ajoutée");
                alert.showAndWait();
                try {
                    ControllerRouter.geneRouter(router, AnnexDetailController.class);
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
            }

        });
    }


    private void listDonation() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/manager/donationListView.fxml"));
        Pane splitPane = fxmlLoader.load();
        border.setCenter(splitPane);
        Pane p = (Pane) border.getChildren().get(0);
        Optional<List<Donation>> optionalDonation = annexService.listDonations(idAnnex);
        this.donationlistVbox = (VBox) p.getChildren().get(0);
        Button newDonation = (Button) p.getChildren().get(1);
        newDonation.setOnAction(event -> {
            try {
                this.createDonationForm();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        if (optionalDonation.isPresent()) {
            if (optionalDonation.get().size() == 0) {
                HBox hBox = new HBox();
                hBox.getChildren().add(new Label("Il n'y aucune donation disponible"));
                donationlistVbox.getChildren().add(hBox);
            }
            for (Donation donation : optionalDonation.get()) {
                HBox hBox = new HBox();
                hBox.setSpacing(20.0);
                donationlistVbox.setSpacing(10.0);
                Label label = new Label(donation.getNom());
                Button read = new Button("Consulter");
                Button delete = new Button("Supprimer");
                delete.setOnAction(event -> {
                    Integer idDonation = donation.getId();
                    try {
                        deleteDonation(idDonation);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                hBox.getChildren().add(label);
                hBox.getChildren().add(read);
                hBox.getChildren().add(delete);
                donationlistVbox.getChildren().add(hBox);
                read.setOnAction(actionEvent -> {
                    GetDonationController.donationId = donation.getId();
                    try {
                        ControllerRouter.geneRouter(router, GetDonationController.class);
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
            }
        }
    }

    private void deleteDonation(Integer idDonation) throws Exception {
        Optional<List<Donation>> donations = annexService.removeDonation(idDonation);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Suppression d'une donation");
        alert.setContentText("L'annexe a bien été supprimée");
        alert.showAndWait();
        this.listDonation();
    }


    private void createProduct() throws Exception {
        Optional<List<Type>> types = typeService.getAllTypes();
        this.nbStageCreationProduct = 1;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/manager/createProductView.fxml"));
        Parent view = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Créer un produit");
        Scene scene = new Scene(view);
        stage.setScene(scene);
        stage.show();
        productType = (ChoiceBox) view.getChildrenUnmodifiable().get(3);
        productType.setItems(FXCollections.observableList(types.get()));
        prdName = (TextField) view.getChildrenUnmodifiable().get(4);
        productType.setConverter(new StringConverter<>() {
            @Override
            public String toString(Type type) {
                return type.getName();
            }

            @Override
            public Type fromString(String s) {
                return productType.getItems().stream().filter(ap ->
                        ap.getName().equals(s)).findFirst().orElse(null);
            }
        });
        saveProduct = (Button) view.getChildrenUnmodifiable().get(5);
        saveProduct.setOnAction(event -> {
            if (prdName.getText() != null && productType.getSelectionModel().getSelectedItem() != null) {
                Product product = new Product();
                product.setName(prdName.getText());
                product.setTypeId(productType.getSelectionModel().getSelectedItem().getId());
                try {
                    Optional<Product> pres = productService.createProduct(product);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Ajout d'un produit");
                    alert.setContentText("Le produit " + pres.get().getName() + " a été ajouté");
                    alert.showAndWait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                stage.close();
                nbStageCreationProduct = 0;
            } else {
                //alert d'erreur
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ajout d'un produit");
                alert.setContentText("L'un des champs obligatoire n'est pas renseigné");
                alert.showAndWait();
            }

        });
        stage.setOnCloseRequest(windowEvent -> {
            nbStageCreationProduct = 0;
        });
    }

    private void deleteService(Integer idService) throws Exception {
        Optional<List<Service>> services = annexService.removeService(idService);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Suppression d'un service");
        alert.setContentText("Service supprimé");
        alert.showAndWait();
        this.listServices();
    }
}
