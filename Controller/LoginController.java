package Controller;
import GUI.*;
import Database.*;
import Models.*;

import java.awt.event.*;

import javax.sound.midi.MidiSystem;
 
//Login Controller
public class LoginController implements ActionListener{
    private LoginView view;
    private Database db;
    private User user;
    private RenterController rtCtrl;
    private ManagerController mgCtrl;

    //Default constructor to create instances of member variables
    public LoginController(Database db){
        this.db = db;
        this.user = new User();
        mgCtrl = new ManagerController();
        rtCtrl = new RenterController();
        
    }


    @Override
    public void actionPerformed(ActionEvent e)
    {
        //addListener();
        this.verifyLogin();

        if(e.getSource().equals(view.getButton()))
        {
            view.destroyFrame();
            System.out.println("userType in user is: " + user.getUserType());
            if(user.getUserType().equals("Renter"))
            {
                rtCtrl.enableView();
                rtCtrl.getRenterView().turnOn();
                rtCtrl.getRenterView().addLogoutListener(this);
            }
            else if(user.getUserType().equals("Landlord"))
            {
                LandlordView vw = new LandlordView();
                vw.setVisible(true);
            }
            else if(user.getUserType().equals("Manager"))
            {
                mgCtrl.enableView();
                mgCtrl.getView().turnOn();
                this.mgCtrl.getView().addLogoutListener(this);
            }
        }
        if(e.getSource().equals(rtCtrl.getRenterView().getLogout()))
        {
            System.out.println("hello");
            rtCtrl.getRenterView().destroyFrame();
            view.turnOn();
        }
        else if(e.getSource().equals(mgCtrl.getView().getLogout()))
        {
            System.out.println("done");
            mgCtrl.getView().destroyFrame();
            view.turnOn();
        }
    }
    
    //Verify user login credentials and return a boolean indicating its status
    public void verifyLogin(){
        user.setUsername(view.getUsername());
        user.setPassword(view.getPassword());

        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        db.initializeConnection();
        db.verifyUser(user.getUsername(), user.getPassword());
        user.setUserType(db.getUserType(user.getUsername(), user.getPassword()));
        db.close();
    }

    //Register new user
    public void registerUser(){

    }

    //getter for login view
    public LoginView getView(){
        return this.view;
    }

    //setter for login view
    public void setView(LoginView lv){
        this.view = lv;
    }

    //getter for database 
    public Database getDb(){
        return this.db;
    }

    //setter for database
    public void setDb(Database d){
        this.db = d;
    }

    //getter for user
    public User getUser(){
        return this.user;
    }

    //setter for user
    public void setUser(User u){
        this.user = u;
    }
    public void enableView()
    {
        view = new LoginView();
        
    }

    public void addListener()
    {
        this.view.addLoginListener(this);
    }
    //getter for register view
}
