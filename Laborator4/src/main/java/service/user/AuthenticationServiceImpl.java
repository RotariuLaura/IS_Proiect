package service.user;

import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.validator.Notification;
import model.validator.UserValidator;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Collections;

import static database.Constants.Roles.CUSTOMER;

public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;

    public AuthenticationServiceImpl(UserRepository userRepository, RightsRolesRepository rightsRolesRepository) {
        this.userRepository = userRepository;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    private String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[16];
        random.nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }
    @Override
    public Notification<Boolean> register(String username, String password) {
        Role customerRole = rightsRolesRepository.findRoleByTitle(CUSTOMER);
        if(userRepository.existsByUsername(username)){
            Notification <Boolean> usernameExistsNotification = new Notification<>();
            usernameExistsNotification.addError("Email is already taken!");
            usernameExistsNotification.setResult(Boolean.FALSE);
            return usernameExistsNotification;
        }
        String salt = generateSalt();
        User user = new UserBuilder()
                .setUsername(username)
                .setPassword(password)
                .setSalt(salt)
                .setRoles(Collections.singletonList(customerRole))
                .build();

        UserValidator userValidator = new UserValidator(user);
        boolean userValid = userValidator.validate();
        Notification <Boolean> userRegisterNotification = new Notification<>();
        if (!userValid){
            userValidator.getErrors().forEach(userRegisterNotification::addError);
            userRegisterNotification.setResult(Boolean.FALSE);
        } else {
            user.setPassword(hashPassword(password, salt));
            if(userRepository.save(user).equals(true)) {
                userRegisterNotification.setResult(Boolean.TRUE);
            } else {
                userRegisterNotification.setResult(Boolean.FALSE);
            }
        }
        return userRegisterNotification;
    }

    @Override
    public Notification<User> login(String username, String password) {
        if(userRepository.findByUsername(username) != null) {
            String salt = userRepository.findByUsername(username).getSalt();
            return userRepository.findByUsernameAndPassword(username, hashPassword(password, salt));
        }
        return userRepository.findByUsernameAndPassword(username, hashPassword(password, ""));
    }

    @Override
    public boolean logout(User user) {
        return false;
    }

    private String hashPassword(String password, String salt) {
        try {
            // Sercured Hash Algorithm - 256
            // 1 byte = 8 biți
            // 1 byte = 1 char
            String saltedPassword = password + salt;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(saltedPassword.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}