/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hooks.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.components.ToastBar;
import com.codename1.io.ConnectionRequest;
import com.codename1.messaging.Message;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.events.DataChangedListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import edu.hooks.entities.Event;
import edu.hooks.entities.Session;
import edu.hooks.entities.user;
import edu.hooks.services.ServicesEvent;
import edu.hooks.services.UploadServices;
import edu.hooks.statistique.EventPieChart;

/**
 *
 * @author Ayadi
 */
public class Interfaceevent extends Form {

    static Form currentForm;

    private EncodedImage placeHolder;
        String FilenameInserver = "";
    UploadServices uploadservices = new UploadServices();
    user User = Session.getCurrentSession();
    boolean test=true;
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public Interfaceevent(Form previous, Resources theme) {

        currentForm = this;
        currentForm.setTitle("Event");
        currentForm.setLayout(BoxLayout.y());

        for (Event c : ServicesEvent.getInstance().getAllEvents()) {
            Container InfoContainer = new Container(BoxLayout.y());
            Label nomEvent = new Label(c.getNomEvent());
            Label AddEvent = new Label(c.getAdresse());
            Label typee = new Label(c.getType());
            Label prix = new Label(String.valueOf(c.getPrix()));
            Label Nombreplace = new Label(String.valueOf(c.getNbPlaces()));
            Label description = new Label(c.getDescription());
            //Label email = new Label(c.getEmail());
            //Label tel = new Label(String.valueOf(c.getTel()));
            //Label Date = new Label(c.getDate());

            InfoContainer.add(nomEvent);
            InfoContainer.add(AddEvent);
            InfoContainer.add(typee);
            InfoContainer.add(prix);
            InfoContainer.add(Nombreplace);
            InfoContainer.add(description);
            Container Container = new Container(BoxLayout.x());

            placeHolder = EncodedImage.createFromImage(theme.getImage("bla.jpg"), true);
            String url = "http://localhost/backt/web/" + c.getImage();
            ConnectionRequest connection = new ConnectionRequest();
            connection.setUrl(url);
            URLImage imgurl = URLImage.createToStorage(placeHolder, url, url);

            ImageViewer img = new ImageViewer(imgurl.scaled(imgurl.getWidth() * 1, imgurl.getHeight() * 1));
            Container.add(img);
            Container.add(InfoContainer);
            currentForm.add(Container);

            img.addPointerReleasedListener(ev -> {
                EventDetail(c, theme).show();
            });
        }

        currentForm.getToolbar().addCommandToOverflowMenu("Add Event", null, ev -> {
            AddEvent(theme).show();
        });

        currentForm.getToolbar().addCommandToOverflowMenu("Stat Events", null, ev -> {
            StatEvent(theme).show();

        });

        currentForm.getToolbar().addMaterialCommandToLeftBar("back", FontImage.MATERIAL_ARROW_BACK, ev -> {
            previous.showBack();
        });
         Style s = UIManager.getInstance().getComponentStyle("Title");

        TextField searchField = new TextField("", "Toolbar Search"); // <1>
        searchField.getHintLabel().setUIID("Title");
        searchField.setUIID("Title");
        searchField.getAllStyles().setAlignment(Component.LEFT);
        currentForm.getToolbar().setTitleComponent(searchField);
        FontImage searchIcon = FontImage.createMaterial(FontImage.MATERIAL_SEARCH, s);
        searchField.addDataChangeListener((i1, i2) -> { // <2>
            String t = searchField.getText();
            if (t.length() < 1) {
                for (Component cmp : currentForm.getContentPane()) {
                    cmp.setHidden(false);
                    cmp.setVisible(true);
                }
            } else {
                t = t.toLowerCase();
                for (Component cmp : currentForm.getContentPane()) {
                    String val = null;
                    if (cmp instanceof Label) {
                        val = ((Label) cmp).getText();
                    } else {
                        if (cmp instanceof TextArea) {
                            val = ((TextArea) cmp).getText();
                        } else {

                            val = (String) cmp.getPropertyValue("text");
                        }
                    }
                    boolean show = val != null && val.toLowerCase().indexOf(t) > -1;
                    cmp.setHidden(!show); // <3>
                    cmp.setVisible(show);
                }
            }
            currentForm.getContentPane().animateLayout(250);
        });

    }

    public Form AddEvent(Resources theme) {

        Form AddEvent = new Form("ADD", BoxLayout.y());

        Label nom = new Label("nom");
        Label address = new Label("adress");
        Label type = new Label("type");
        Label prix = new Label("prix");
        Label nombreplace = new Label("nombreplace");
        Label description = new Label("description");
        Label image = new Label("image");

        TextField nomeve = new TextField(null, "nomeve");
        TextField addresseve = new TextField(null, "addresseve");
        TextField typeeve = new TextField(null, "typeeve");
        TextField prixeve = new TextField(null, "prixeve");
        TextField nombreplaceeve = new TextField(null, "nombreplaceeve");
        TextField descriptioneve = new TextField(null, "descriptioneve");
        //TextField imageeve = new TextField(null, "imageeve");

       // Button Save = new Button("Save");
        Button imageeve = new Button("importer image");

        Button Save = new Button("ajouter evenement");
        imageeve.addActionListener(e -> {
            Display.getInstance().openGallery(new ActionListener() {

                public void actionPerformed(final ActionEvent evt) {
                    if (evt == null) {
                        ToastBar.Status s = ToastBar.getInstance().createStatus();
                        s.setMessage("User Cancelled Gallery");
                        s.setMessageUIID("Title");
                        Image i = FontImage.createMaterial(FontImage.MATERIAL_ERROR, UIManager.getInstance().getComponentStyle("Title"));
                        s.setIcon(i);
                        s.setExpires(2000);
                        s.show();
                        return;
                    }
                    String file = (String) evt.getSource();
                    System.out.println("pathhhh:" + file);
                    String path = file.substring(7);
                    System.out.println(path);
                    FilenameInserver = uploadservices.uploadImage(path);
                }
            }, Display.GALLERY_IMAGE);

        });   nomeve.addDataChangeListener(new DataChangedListener() {
            @Override
            public void dataChanged(int type, int index) {
             if(nomeve.getText().length()>0){
                if(test){
                   
                int indexa = nomeve.getText().length();
                String car = "a";
                car = car.replace(car.charAt(0), nomeve.getText().charAt(indexa - 1));
                if (isNumeric(car)) {
                    if (indexa > 1) {
                        test=false;
                        Dialog.show("alerte","le nom ne contient pas des chiffres",new Command("Ok"));
                        nomeve.setText(nomeve.getText().substring(0, indexa - 1));
                        
                        
                    } else {
                        test=false;
                        Dialog.show("alerte","le nom ne contient pas des chiffres",new Command("Ok"));
                        nomeve.setText("");
                        
                    }
                }}else{
                  test=true;
              }
            }
            }
        });
typeeve.addDataChangeListener(new DataChangedListener() {
            @Override
            public void dataChanged(int type, int index) {
             if(typeeve.getText().length()>0){
                if(test){
                   
                int indexa = typeeve.getText().length();
                String car = "a";
                car = car.replace(car.charAt(0), typeeve.getText().charAt(indexa - 1));
                if (isNumeric(car)) {
                    if (indexa > 1) {
                        test=false;
                        Dialog.show("alerte","le type ne contient pas des chiffres",new Command("Ok"));
                        typeeve.setText(typeeve.getText().substring(0, indexa - 1));
                        
                        
                    } else {
                        test=false;
                        Dialog.show("alerte","le type ne contient pas des chiffres",new Command("Ok"));
                        typeeve.setText("");
                        
                    }
                }}else{
                  test=true;
              }
            }
            }
        });

prixeve.addDataChangeListener(new DataChangedListener() {
            @Override
            public void dataChanged(int type, int index) {
             if(prixeve.getText().length()>0){
                if(test){
                   
                int indexa = prixeve.getText().length();
                String car = "a";
                car = car.replace(car.charAt(0), prixeve.getText().charAt(indexa - 1));
                if (!isNumeric(car)) {
                    if (indexa > 1) {
                        test=false;
                        Dialog.show("alerte","le prix ne contient pas des lettres",new Command("Ok"));
                        prixeve.setText(prixeve.getText().substring(0, indexa - 1));
                        
                        
                    } else {
                        test=false;
                        Dialog.show("alerte","le prix ne contient pas des lettres",new Command("Ok"));
                        prixeve.setText("");
                        
                    }
                }}else{
                  test=true;
              }
            }
            }
        });

nombreplaceeve.addDataChangeListener(new DataChangedListener() {
            @Override
            public void dataChanged(int type, int index) {
             if(nombreplaceeve.getText().length()>0){
                if(test){
                   
                int indexa = nombreplaceeve.getText().length();
                String car = "a";
                car = car.replace(car.charAt(0), nombreplaceeve.getText().charAt(indexa - 1));
                if (!isNumeric(car)) {
                    if (indexa > 1) {
                        test=false;
                        Dialog.show("alerte","le prix ne contient pas des lettres",new Command("Ok"));
                        nombreplaceeve.setText(nombreplaceeve.getText().substring(0, indexa - 1));
                        
                        
                    } else {
                        test=false;
                        Dialog.show("alerte","le nombre de places ne contient pas des lettres",new Command("Ok"));
                        nombreplaceeve.setText("");
                        
                    }
                }}else{
                  test=true;
              }
            }
            }
        });



        AddEvent.addAll(nom, nomeve, address, addresseve, type, typeeve, prix, prixeve, nombreplace, nombreplaceeve, description, descriptioneve, image, imageeve, Save);

        Save.addActionListener(ev -> {
            if ((nomeve.getText().length() == 0) || (addresseve.getText().length() == 0) || typeeve.getText().length() == 0
                    || prixeve.getText().length() == 0 || nombreplaceeve.getText().length() == 0 || (descriptioneve.getText().length() == 0) || (imageeve.getText().length() == 0)) {
                Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
            } else {
                try {
                    Event c = new Event();
                    c.setNomEvent(nomeve.getText());
                    c.setAdresse(addresseve.getText());
                    c.setType(typeeve.getText());

                    float price = Float.parseFloat(prixeve.getText());
                    c.setPrix(price);
                    int nombre = Integer.parseInt(nombreplaceeve.getText());
                    c.setNbPlaces(nombre);
                    c.setDescription(descriptioneve.getText());
                    c.setImage(imageeve.getText());
                 


                    if (ServicesEvent.getInstance().AddEvent(c)) {
                         ServicesEvent.getInstance().envoiemail(User);
                        Dialog.show("Success", "Event Added", new Command("OK"));
                        new Interfaceevent(Interfaceevent.currentForm, theme).show();
                    } else {
                        Dialog.show("ERROR", "Server error", new Command("OK"));
                    }
                } catch (NumberFormatException e) {
                    Dialog.show("ERROR", "Number places  must be a number", new Command("OK"));
                }

            }

        });

        AddEvent.getToolbar().addMaterialCommandToLeftBar("back", FontImage.MATERIAL_ARROW_BACK, ev -> {
            new Interfaceevent(Interfaceevent.currentForm, theme).show();
        });

        return AddEvent;
    }

    public Form EventDetail(Event c, Resources theme) {

        Form EventDetail = new Form(c.getNomEvent(), BoxLayout.y());

        placeHolder = EncodedImage.createFromImage(theme.getImage("7.jpg"), true);
        String url = "http://localhost/backt/web/" + c.getImage();
        ConnectionRequest connection = new ConnectionRequest();
        connection.setUrl(url);
        URLImage imgurl = URLImage.createToStorage(placeHolder, url, url);

        ImageViewer img = new ImageViewer(imgurl.scaled(imgurl.getWidth() * 1, imgurl.getHeight() * 1));

        Label nom = new Label("nom");
        Label address = new Label("adress");
        Label type = new Label("type");
        Label prix = new Label("prix");
        Label nombreplace = new Label("nombreplace");
        Label description = new Label("description");
        //Label image = new Label("image");

        //SpanLabel Message = new SpanLabel("Descrption: \n" + c.getDescription() + "\n" + "Created AT: " + c.getDate() + "\n" + "Members: " + c.getNombreplace());
        TextField EventNameField = new TextField(null, "Name");

        EventNameField.setText(c.getNomEvent());

        TextField nomeve = new TextField(null, "nom");
        nomeve.setText(c.getNomEvent());
        TextField addresseve = new TextField(null, "Adresse");
        addresseve.setText(String.valueOf(c.getAdresse()));
        TextField typeeve = new TextField(null, "Type");
        typeeve.setText(c.getType());
        TextField prixeve = new TextField(null, "Price");
        prixeve.setText(String.valueOf(c.getPrix()));
        TextField nombreplaceeve = new TextField(null, "places");
        nombreplaceeve.setText(String.valueOf(c.getNbPlaces()));
        TextField descriptioneve = new TextField(null, "description");
        descriptioneve.setText(c.getDescription());

        Container Container = new Container(new FlowLayout());
        Container.addAll(nom, nomeve, address, addresseve, type, typeeve, prix, prixeve, nombreplace, nombreplaceeve, description, descriptioneve);
        EventDetail.add(img);
        EventDetail.add(Container);

        Container ButtonsContainer = new Container(new FlowLayout());

        Button Delete = new Button("Delete");
        Button Edit = new Button("Edit");
        ButtonsContainer.addAll(Edit, Delete);

        EventDetail.add(ButtonsContainer);
        EventDetail.revalidate();

        Delete.addActionListener(ev -> {
            if (Dialog.show("Confirmation", "Are u Sure ? ", "OK", "ANNULER")) {
                String result = ServicesEvent.getInstance().DeleteEvent(c);
                if (!result.equals("Error")) {
                    Dialog.show("Success", result, new Command("OK"));
                    new Interfaceevent(Interfaceevent.currentForm, theme).show();
                } else {
                    Dialog.show("ERROR", "Server error", new Command("OK"));
                }
            } else {
// ki tnzel 3ea annuler chnowa issir    

//Display.getInstance().exitApplication();                   
            }

        });

        Edit.addActionListener(ev -> {
            c.setNomEvent(EventNameField.getText());
            c.setAdresse(addresseve.getText());

            c.setType(typeeve.getText());
            c.setPrix(Float.parseFloat(prixeve.getText()));
            c.setNbPlaces(Integer.parseInt(nombreplaceeve.getText()));
            c.setDescription(descriptioneve.getText());

            if (ServicesEvent.getInstance().EditEvent(c)) {
                Dialog.show("Success", "Club Edited", new Command("OK"));
                new Interfaceevent(Interfaceevent.currentForm, theme).show();
            } else {
                Dialog.show("ERROR", "Server error", new Command("OK"));
            }
        });

        EventDetail.getToolbar().addMaterialCommandToLeftBar("back", FontImage.MATERIAL_ARROW_BACK, ev -> {
            new Interfaceevent(Guide.current, theme).show();
        });

        return EventDetail;
    }

    public Form StatEvent(Resources theme) {

        EventPieChart a = new EventPieChart();
        Form stats_Form = a.execute();
        SpanLabel test_SpanLabel = new SpanLabel("Hiiii");
        Class cls = EventPieChart.class;
        stats_Form.getToolbar().addMaterialCommandToLeftBar("back", FontImage.MATERIAL_ARROW_BACK, ev -> {
            new Interfaceevent(Guide.current, theme).show();
        });

        return stats_Form;
    }

}
