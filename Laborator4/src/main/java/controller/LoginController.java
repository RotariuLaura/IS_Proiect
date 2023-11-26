package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import launcher.ComponentFactory;
import model.User;
import model.validator.Notification;
import service.book.BookServiceImpl;
import service.user.AuthenticationService;
import view.CustomerView;
import view.LoginView;

public class LoginController {

    private final LoginView loginView;
    private final AuthenticationService authenticationService;
    private ComponentFactory componentFactory;

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
                loginView.setActionTargetText("LogIn Successfull!");
                Stage primaryStage = componentFactory.getPrimaryStage();
                CustomerView customerView = new CustomerView(primaryStage);
                CustomerController customerController = new CustomerController(customerView, (BookServiceImpl) componentFactory.getBookService());
            }
        }
    }

    private class RegisterButtonListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<Boolean> registerNotification = authenticationService.register(username, password);

            if (registerNotification.hasErrors()) {
                loginView.setActionTargetText(registerNotification.getFormattedErrors());
            } else {
                loginView.setActionTargetText("Register successful!");
                Stage primaryStage = componentFactory.getPrimaryStage();
                CustomerView customerView = new CustomerView(primaryStage);
                CustomerController customerController = new CustomerController(customerView, (BookServiceImpl) componentFactory.getBookService());
            }
        }
    }

}