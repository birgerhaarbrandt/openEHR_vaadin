package org.vaadin.example;

import com.nedap.archie.rm.generic.PartyIdentified;
import com.nedap.archie.rm.generic.PartySelf;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.ehrbase.openehr.sdk.generator.commons.shareddefinition.Category;
import org.ehrbase.openehr.sdk.generator.commons.shareddefinition.Language;
import org.ehrbase.openehr.sdk.generator.commons.shareddefinition.Setting;
import org.ehrbase.openehr.sdk.generator.commons.shareddefinition.Territory;
import org.vaadin.example.datasource.DataService;
import org.vaadin.example.template.atemfrequenzcomposition.AtemfrequenzComposition;
import org.vaadin.example.template.atemfrequenzcomposition.definition.AtemfrequenzKategorieElement;
import org.vaadin.example.template.atemfrequenzcomposition.definition.AtemfrequenzObservation;
import org.vaadin.example.template.atemfrequenzcomposition.definition.StatusDefiningCode;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;

@PageTitle("Example Form")
@Route(value = "ExampleFormService", layout = MainView.class)
public class ExampleFormService extends VerticalLayout {

    DataService service;

    public ExampleFormService(DataService dataService) {
        service = dataService;

        // Add a headline
        H1 headline = new H1("Atemfrequenz");
        add(headline);

        // Create a layout box for the form
        VerticalLayout formLayout = new VerticalLayout();
        formLayout.setWidth("500px");
        formLayout.setPadding(true);
        formLayout.setSpacing(true);
        formLayout.getStyle().set("border", "1px solid #ccc");
        formLayout.getStyle().set("border-radius", "8px");
        formLayout.getStyle().set("box-shadow", "2px 2px 5px rgba(0, 0, 0, 0.1)");
        formLayout.getStyle().set("padding", "16px");

        // Add the form components to the box
        ComboBox<StatusDefiningCode> comboBox = new ComboBox<>("Status");
        comboBox.setItems(StatusDefiningCode.values());
        comboBox.setItemLabelGenerator(StatusDefiningCode::getValue);
        formLayout.add(comboBox);

        NumberField respMagnitude = new NumberField();
        respMagnitude.setLabel("Atemfrequenz [/min]");
        respMagnitude.setClearButtonVisible(true);
        formLayout.add(respMagnitude);

        DateTimePicker dateTimePicker = new DateTimePicker();
        dateTimePicker.setLabel("Messzeitpunkt");
        formLayout.add(dateTimePicker);

        Button sendButton = new Button("Submit");
        formLayout.add(sendButton);

        // Add the formLayout to the main layout
        add(formLayout);

        // Click listener for submit button
        sendButton.addClickListener(event -> {
            if (respMagnitude.getValue() != null && dateTimePicker.getValue() != null && comboBox.getValue() != null) {
                Double value = respMagnitude.getValue();
                LocalDateTime dateTime = dateTimePicker.getValue();
                StatusDefiningCode statusDefiningCode = comboBox.getValue();
                buildComposition(value, dateTime, statusDefiningCode);
                Notification.show("Submitted value: " + value);
            } else {
                Notification.show("Please fill out all fields", 3000, Notification.Position.MIDDLE);
            }
        });
    }

    private void buildComposition(double value, LocalDateTime time, StatusDefiningCode definingCode) {
        AtemfrequenzObservation observation = new AtemfrequenzObservation();
        observation.setMesswertMagnitude(value);
        observation.setLanguage(Language.DE);
        observation.setSubject(new PartySelf());
        observation.setMesswertUnits("/min");
        observation.setOriginValue(time);

        AtemfrequenzComposition composition = new AtemfrequenzComposition();
        composition.setAtemfrequenz(observation);
        composition.setStatusDefiningCode(definingCode);

        AtemfrequenzKategorieElement element = new AtemfrequenzKategorieElement();
        element.setValue("Observation");
        ArrayList<AtemfrequenzKategorieElement> elementArrayList = new ArrayList<>();
        elementArrayList.add(element);

        composition.setKategorie(elementArrayList);
        composition.setStartTimeValue(OffsetDateTime.of(2019, 4, 3, 22, 0, 0, 0, ZoneOffset.UTC));
        composition.setEndTimeValue(OffsetDateTime.now());
        composition.setLanguage(Language.DE);
        composition.setTerritory(Territory.DE);
        composition.setCategoryDefiningCode(Category.EVENT);
        composition.setSettingDefiningCode(Setting.NURSING_HOME_CARE);
        composition.setComposer(new PartyIdentified(null, "Birger", null));

        service.sendComposition(composition);
    }
}
