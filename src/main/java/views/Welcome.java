package views;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;

import dao.UserDAO;
import model.User;
import service.GenerateOTP;
import service.SendOTPService;
import service.UserService;

public class Welcome {
    public void WelcomeScreen()
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Welcome to the application");
        System.out.println("Press 1 to login");
        System.out.println("Press 2 for signup");
        System.out.println("Press 0 to exit");

        int choice = 0;
        try {
            choice = Integer.parseInt(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch (choice) {
            case 1->
                login();
            case 2-> signUp();
            case 0 -> System.exit(0);
        }
    }

    private void login() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your email please");
        String email = sc.nextLine();

        try {
            if(UserDAO.isExist(email))
            {
                String getOtp = GenerateOTP.getOtp();
                SendOTPService.sendOTP(email, getOtp);

                System.out.println("Enter the OTP");
                String OTP = sc.nextLine();

                if(getOtp.equals(OTP))
                {
                    new UserView(email).home();

                }else
                {
                    System.out.println("Wrong OTP");
                }
            }
            else
            {
                System.out.println("User not found");
            }
        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }
    private void signUp() {
        System.out.println("Enter Name");
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        System.out.println("Enter email");
        String email = sc.nextLine();

            String getOtp = GenerateOTP.getOtp();
            SendOTPService.sendOTP(email, getOtp);
    
            System.out.println("Enter the OTP");
            String OTP = sc.nextLine();
            if(getOtp.equals(OTP))
            {
                User user = new User(name, email);
                int response = UserService.saveUser(user);
                switch (response){
                    case 0 -> System.out.println("User already registered please logiin");
                    case 1 -> System.out.println("User registered successfully");
                }
            }else
            {
                System.out.println("Wrong OTP");
            }
 
        
    }
}
