package fr.core.ui.Controller.manager;

import fr.core.model.customModel.Manager;
import fr.core.model.customModel.ProductRequest;
import fr.core.model.customModel.SearchProduct;
import fr.core.model.customModel.Session;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class AnnexDetailController {


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
    Label name;

    @FXML
    Label description;
    @FXML
    Label street;
    @FXML
    Label city;
    @FXML
    Label zipcode;

    Annex annex;

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
    TextField prdName;

    @FXML
    Button service;

    @FXML
    VBox servicelistVbox;

    @FXML
    VBox donationlistVbox;


    @FXML
    Button callServiceformButton;

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
        test();
    }

    public void setTypeService(ITypeService typeService) {
        this.typeService = typeService;
    }

    public void setProductService(IProductService productService) {
        this.productService = productService;
    }

    public void test() throws Exception {
        this.findAnnex();
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
        lundi.setSpacing(20);
        mardi.setSpacing(20);
        mercredi.setSpacing(20);
        jeudi.setSpacing(20);
        vendredi.setSpacing(20);
        samedi.setSpacing(20);
        dimanche.setSpacing(20);
        this.getAvailabilities();
    }

    /**
     * affichage des horaire pour une annexe
     */
    private void getAvailabilities() {
        for (AnnexAvailability annexAvailability : annex.getAnnexAvailabilities()) {
            String day = annexAvailability.getDay().getName();
            Label openning = new Label(annexAvailability.getOpeningTime().toString());
            Label closing = new Label(annexAvailability.getClosingTime().toString());
            HBox.setMargin(openning, new Insets(25, 1, 1, 1));
            HBox.setMargin(closing, new Insets(25, 1, 1, 1));

            switch (day) {
                case "Lundi":
                    this.lundi.getChildren().add(openning);
                    this.lundi.getChildren().add(closing);
                    break;
                case "Mardi":
                    this.mardi.getChildren().add(openning);
                    this.mardi.getChildren().add(closing);
                    break;
                case "Mercredi":
                    this.mercredi.getChildren().add(openning);
                    this.mercredi.getChildren().add(closing);
                    break;
                case "Jeudi":
                    this.jeudi.getChildren().add(openning);
                    this.jeudi.getChildren().add(closing);
                    break;
                case "Vendredi":
                    this.vendredi.getChildren().add(openning);
                    this.vendredi.getChildren().add(closing);
                    break;
                case "Samedi":
                    this.samedi.getChildren().add(openning);
                    this.samedi.getChildren().add(closing);
                    break;
                case "Dimanche":
                    this.dimanche.getChildren().add(openning);
                    this.dimanche.getChildren().add(closing);
                    break;
            }
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
        for (User user : annex.getUsers()) {
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
        String res = annexService.removeManager(annex.getId(), idManager);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Suppression d'un manager");
        alert.setContentText(res);
        alert.showAndWait();
        ControllerRouter.geneRouter(router, AnnexDetailController.class);
    }

    private void addManager(String text) throws Exception {
        Manager manager = new Manager();
        manager.email = text;
        String res = annexService.addManager(annex.getId(), manager);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ajout d'un manager");
        alert.setContentText(res);
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
        List label = (List) p.getChildren();
        name = (Label) label.get(0);
        street = (Label) label.get(1);
        city = (Label) label.get(2);
        zipcode = (Label) label.get(3);
        description = (Label) label.get(4);
        donation = (Button) label.get(6);
        service = (Button) label.get(7);
        name.setText(annex.getName());
        street.setText(annex.getStreet());
        city.setText(annex.getCity());
        zipcode.setText(annex.getZipCode());
        description.setText(annex.getDescription());
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
        for (Service service : optionalServices.get()) {
            if (service.isActif()) {
                HBox hBox = new HBox();
                hBox.setSpacing(20.0);
                servicelistVbox.setSpacing(10.0);
                Label label = new Label(service.getNom());
                Button read = new Button("Consulter");
                Button delete = new Button("Supprimer");
                read.setOnAction(event -> {
                    Integer idService = service.getId();
                    try {
                        Service service1 = annexService.getServiceById(idService);
                        ServiceConsultationModal.service = service1;
                        ServiceConsultationModal.createModal();
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
        }

    }

    public void createService() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/manager/createServiceView.fxml"));
        Pane splitPane = fxmlLoader.load();
        border.setCenter(splitPane);
        Pane p = (Pane) border.getChildren().get(0);
        serviceName = (TextField) p.getChildren().get(1);
        serviceDescription = (TextArea) p.getChildren().get(4);
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
                    && serviceQuantite.getText().matches("[0-9]*")) {
                create.setDisable(false);
            }
        });
        serviceDescription.setOnKeyTyped(keyEvent -> {
            if (!serviceName.getText().equals("") && !serviceDescription.getText().equals("")
                    && date.getValue() != null && !serviceQuantite.getText().equals("")
                    && serviceQuantite.getText().matches("[0-9]*")) {
                create.setDisable(false);
            }
        });
        serviceQuantite.setOnKeyTyped(keyEvent -> {
            if (!serviceName.getText().equals("") && !serviceDescription.getText().equals("")
                    && date.getValue() != null && !serviceQuantite.getText().equals("")
                    && serviceQuantite.getText().matches("[0-9]*")) {
                create.setDisable(false);
            }
        });
        date.setOnAction(keyEvent -> {
            if (!serviceName.getText().equals("") && !serviceDescription.getText().equals("")
                    && date.getValue() != null && !serviceQuantite.getText().equals("")
                    && serviceQuantite.getText().matches("[0-9]*")) {
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
            service.setAnnexId(annex.getId());
            service.setDate_service(date);
            service.setDescription(serviceDescription.getText());
            service.setNom(serviceName.getText());
            service.setQuantite(Integer.parseInt(serviceQuantite.getText()));
            try {
                Service s = annexService.createService(service);
                Alert alert = new Alert(null);
                alert.setTitle("Création d'un service");
                if (s != null) {
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setContentText("Votre service " + s.getNom() + " a bien été créé");
                    alert.showAndWait();
                    ControllerRouter.geneRouter(router, AnnexDetailController.class);
                } else {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setTitle("Création d'un service");
                    alert.setContentText("Il semblerait que le service n'est pas été créé");
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
                donation.setAnnexId(this.annex.getId());
                tableProduct.getItems().forEach(request -> {
                    ProductRequest pr = (ProductRequest) request;
                    donation.getProductRequests().add(pr);
                });
                Donation donation1 = annexService.createDonation(donation);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Ajout d'une donation");
                alert.setContentText("La donation " + donation1.getNom() + " a été ajoutée");
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
        ScrollPane scrollPane = (ScrollPane) p.getChildren().get(0);
        Button newDonation = (Button) p.getChildren().get(1);
        newDonation.setOnAction(event -> {
            try {
                this.createDonationForm();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        this.donationlistVbox = (VBox) scrollPane.getContent();
        if (optionalDonation.isPresent()) {
            if (optionalDonation.get().size() ==0){
                HBox hBox = new HBox();
                hBox.getChildren().add(new Label("Il n'y aucune donations disponible"));
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
        annexService.removeDonation(idDonation);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Suppression d'une donation");
        alert.setContentText("Donation supprimée");
        alert.showAndWait();
        ControllerRouter.geneRouter(router, AnnexDetailController.class);
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
                    Product pres = productService.createProduct(product);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Ajout d'un produit");
                    alert.setContentText("Le produit " + pres.getName() + " a été ajouté");
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
        annexService.removeService(idService);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Suppression d'un service");
        alert.setContentText("Service supprimé");
        alert.showAndWait();
        ControllerRouter.geneRouter(router, AnnexDetailController.class);
    }
}
