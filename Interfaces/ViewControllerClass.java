package Interfaces;

public interface ViewControllerClass {

    //to load data to be available in Class
    void loadDB();

    //to Layout the components of view to be available to display for all screen sizes
    void layout_view();

    // to control which elements can be viewed by user
    void setup_permissions();

    //to init default values of view components and assign methods to buttunos
    void setup_view() throws InterruptedException;

    //to setup menu button in all clases
    void setup_menu_buttons();







}
