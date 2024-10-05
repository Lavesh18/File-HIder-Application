package views;

import java.sql.SQLException;
import java.util.*;
import java.io.*;
import dao.DataDAO;
import model.Data;

public class UserView {
    private String email;

    UserView(String email) {
        this.email = email;
    }

    public void home() {
        do {
            System.out.println("Welcome " + this.email);
            System.out.println("Press 1 to show hidden files");
            System.out.println("Press 2 to hide a new file");
            System.out.println("Press 3 to unhide a file");
            System.out.println("Press 0 to exit");
            Scanner sc = new Scanner(System.in);
            int ch = Integer.parseInt(sc.nextLine());

            switch (ch) {
                case 1 -> {
                    try {
                        List<Data> files = DataDAO.getAllFiles(this.email);
                        System.out.println("ID - File Name");
                        for (Data file : files) {
                            System.out.println(file.getId() + " - " + file.getFileName());
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                case 2 -> {
                    System.out.println("Enter the file path");
                    String path = sc.nextLine();
                    File f = new File(path);

                    Data file = new Data(0, f.getName(), path,this.email);

                    try {
                        DataDAO.hideFile(file);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                case 3 -> {
                    try {
                        List<Data> files = DataDAO.getAllFiles(this.email);
                        System.out.println("ID - File Name");
                        for (Data file : files) {
                            System.out.println(file.getId() + " - " + file.getFileName());
                        }
                        System.out.println("Enter the id of file to unhide!");
                        int id = Integer.parseInt(sc.nextLine());

                        boolean isValidID =false;
                        for(Data file:files)
                        {
                            if(file.getId() == id)
                            {
                                isValidID = true;
                                break;
                            }
                            else
                            {
                                System.out.println("ID not found!");
                            }
                        }
                        if(isValidID)
                        {
                            DataDAO.unhide(id);
                        }
                    } catch (SQLException  e) {
                        e.printStackTrace();
                    } catch(IOException e)
                    {
                        e.printStackTrace(); 
                    }
                   

                }

                case 0 ->{
                    System.exit(0);
                }

            }

        } while (true);
    }
}
