package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import launcher.ComponentFactory;
import model.User;
import model.validator.Notification;
import service.book.BookServiceImpl;
import service.order.OrderServiceImpl;
import service.user.AuthenticationService;
import view.CustomerView;
import view.EmployeeView;
import view.LoginView;

public class LoginController {

    private final LoginView loginView;
    private final AuthenticationService authenticationService;
    private final ComponentFactory componentFactory;

    public LoginController(LoginView loginView, AuthenticationService authenticationService, ComponentFactory componentFactory) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;

        this.loginView.addLoginButtonListener(new LoginButtonListener());
        this.loginView.addRegisterButtonListener(new RegisterButtonListener());

        this.componentFactory = componentFactory;
    }

    private class LoginButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(javafx.event.ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<User> loginNotification = authenticationService.login(username, password);

            if (loginNotification.hasErrors()){
                loginView.setActionTargetText(loginNotification.getFormattedErrors());
            }else{
                User loggedInUser = loginNotification.getResult();
                Long userId = loggedInUser.getId();
                loginView.setActionTargetText("LogIn Successful!");
                if(username.endsWith("@employee.com")) {
                    Stage primaryStage = componentFactory.getPrimaryStage();
                    EmployeeView employeeView = new EmployeeView(primaryStage);
                    EmployeeController employeeController = new EmployeeController(employeeView, (BookServiceImpl) componentFactory.getBookService());
                } else if (username.endsWith("@admin.com")) {

                } else {
                    Stage primaryStage = componentFactory.getPrimaryStage();
                    CustomerView customerView = new CustomerView(primaryStage);
                    CustomerController customerController = new CustomerController(customerView, (BookServiceImpl) componentFactory.getBookService(),
                            (OrderServiceImpl) componentFactory.getOrderService(), userId);
                }
            }
        }
    }

    private class RegisterButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<Boolean> registerNotification = authenticationService.register(username, password);
            Notification<User> loginNotification = authenticationService.login(username, password);
            if (registerNotification.hasErrors() || loginNotification.hasErrors()) {
                loginView.setActionTargetText(registerNotification.getFormattedErrors());
            } else {
                User loggedInUser = loginNotification.getResult();
                Long userId = loggedInUser.getId();
                loginView.setActionTargetText("Register successful!");
                if(username.endsWith("employee.com")){
                    Stage primaryStage = componentFactory.getPrimaryStage();
                    EmployeeView employeeView = new EmployeeView(primaryStage);
                    EmployeeController employeeController = new EmployeeController(employeeView, (BookServiceImpl) componentFactory.getBookService());
                } else if(username.endsWith("@admin.com")) {

                } else {
                    Stage primaryStage = componentFactory.getPrimaryStage();
                    CustomerView customerView = new CustomerView(primaryStage);
                    CustomerController customerController = new CustomerController(customerView, (BookServiceImpl) componentFactory.getBookService(),
                            (OrderServiceImpl) componentFactory.getOrderService(), userId);
                }
            }
        }
    }

}