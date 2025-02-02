package org.vaadin.example;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.dom.Element;

@JsModule("./animateCount.js")
public class Tile extends VerticalLayout {

    private final Div contentDiv;

    public Tile(String title, VaadinIcon icon, Double content, String additional) {
        addClassName("dashboard-tile");
        setPadding(true);
        setSpacing(true);
        setWidth("200px");
        setHeight("200px");
        getStyle().set("margin", "10px");
        getStyle().set("text", "white");

        // Title with icon
        HorizontalLayout header = new HorizontalLayout();
        Icon tileIcon = new Icon(icon);
        Span titleSpan = new Span(title);
        header.add(tileIcon, titleSpan);
        header.setAlignItems(Alignment.CENTER);

        // Content
        contentDiv = new Div();
        contentDiv.setText("0"); // Start with 0
        contentDiv.addClassName("tile-content");
        contentDiv.getStyle().set("font-size", "36px");
        contentDiv.getStyle().set("text-align", "center");
        contentDiv.getStyle().set("flex-grow", "1");

        // Ensure the content is centered within the tile
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        // Add components to the tile
        add(header, contentDiv);

        // Trigger the animation
        UI.getCurrent().getPage().executeJs(
                "animateCount($0, $1, $2, $3);",
                contentDiv.getElement(), // Element
                0, // Start
                content, // End
                2000 // Duration
        );


        Span footerSpan= new Span(additional);
        add(footerSpan);

    }

    // Method to change the color of the tile
    public void setTileColor(String color) {
        getStyle().set("background-color", color);
    }

}
