package org.vaadin.example;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;




@Route("/")
@UIScope
@Component
public final class MainView extends AppLayout {


    private final VerticalLayout drawerContent = new VerticalLayout();
    private final HorizontalLayout header = new HorizontalLayout();


    public MainView() {

        addToNavbar(createHeader());
        addToDrawer(createNavBar());

    }

    VerticalLayout createNavBar() {

        VerticalLayout layout = new VerticalLayout();
        Icon dashboardIcon = VaadinIcon.DASHBOARD.create();
        Icon medicationIcon = VaadinIcon.PILL.create();
        Icon diagnosisIcon = VaadinIcon.HEART.create();
        Icon formIcon = VaadinIcon.FORM.create();

        Span githubIcon = new Span();
        githubIcon.addClassNames("fa-solid fa-syringe", "icon-large");
        HorizontalLayout horizontalLayout4 = new HorizontalLayout(githubIcon, new Span("Vaccination"));
        horizontalLayout4.setAlignSelf(FlexComponent.Alignment.BASELINE);
        horizontalLayout4.setAlignItems(FlexComponent.Alignment.BASELINE);

        RouterLink vaccinationLink = new RouterLink();
        vaccinationLink.add(horizontalLayout4);
        vaccinationLink.setRoute(VaccinationService.class);

        layout.add(vaccinationLink);

        // Create RouterLinks with icons
        RouterLink dashboardLink = new RouterLink();
        dashboardLink.add(new HorizontalLayout(dashboardIcon, new Span("Vital Signs")));
        dashboardLink.setRoute(DashbordService.class);
        layout.add(dashboardLink);

        RouterLink medicationLink = new RouterLink();
        medicationLink.add(new HorizontalLayout(medicationIcon, new Span("Medication")));
        medicationLink.setRoute(MedicationService.class);
        layout.add(medicationLink);

        RouterLink diagnosesLink = new RouterLink();
        diagnosesLink.add(new HorizontalLayout(diagnosisIcon, new Span("Diagnoses")));
        diagnosesLink.setRoute(DiagnosesService.class);
        layout.add(diagnosesLink);

        RouterLink formsLink = new RouterLink();
         formsLink.add(new HorizontalLayout(formIcon, new Span("Form")));
         formsLink.setRoute(ExampleFormService.class);
         layout.add(formsLink);


        //RouterLink vaccinationLink = new RouterLink();
        //vaccinationLink.add(new HorizontalLayout(medicationIcon, new com.vaadin.flow.component.html.Span("Medication")));

        return layout;
    }


    HorizontalLayout createHeader() {

        HorizontalLayout layout = new HorizontalLayout();
        layout.add(new DrawerToggle());

        Image logo = new Image("images/openehr.png", "openEHR Logo");
        logo.setHeight("30px"); // Set the height of the logo image
        logo.addClassName("logo");
        logo.getStyle().set("margin-top", "10px");
        logo.getStyle().set("margin-left", "10px");

        layout.add(logo);

        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        // Create the spacer and set it to expand
        HorizontalLayout spacer = new HorizontalLayout();
        spacer.setWidth("90%"); // Takes up all available space
        spacer.setFlexGrow(1); // Make the spacer grow to take up the remaining space

        Button registerButton = new Button("Logout");
        registerButton.getStyle().setMarginRight("30");
        registerButton.addClickListener(buttonClickEvent -> {
            UI.getCurrent().getPage().setLocation("/logout");
        });

        layout.add(spacer, registerButton);

        layout.setWidthFull();

        return layout;
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

    }


}
