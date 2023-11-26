import controller.LoginController;
import database.JDBCConnectionWrapper;
import javafx.application.Application;
import javafx.stage.Stage;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySql;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySql;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceImpl;
import view.LoginView;

import java.sql.Connection;

import static database.Constants.Schemas.PRODUCTION;

public class Main extends Application {
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        final Connection connection = new JDBCConnectionWrapper(PRODUCTION).getConnection();

        final RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySql(connection);
        final UserRepository userRepository = new UserRepositoryMySql(connection, rightsRolesRepository);

        final AuthenticationService authenticationService = new AuthenticationServiceImpl(userRepository,
                rightsRolesRepository);
        final LoginView loginView = new LoginView(primaryStage);

        new LoginController(loginView, authenticationService);
    }
}