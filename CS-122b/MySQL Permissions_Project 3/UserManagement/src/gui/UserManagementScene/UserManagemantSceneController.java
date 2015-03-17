package gui.UserManagementScene;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.UserManager;
import beans.User;

public class UserManagemantSceneController implements Initializable {
	
	public static boolean isUserManagementWindowOpen = false;

	@FXML private TextField usernameField;
	@FXML private TextField passwordField;
	@FXML private TextField hostField;
	
	@FXML private Button createUserButton;
	
	@FXML private ListView<User> usersListView;
	
	@FXML private Button deleteSelectedUserButton;
	@FXML private Button deselectUserButton;
	
	@FXML private Label userNotificationLabel;
	
	private ObservableList<User> users = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{	
		deleteSelectedUserButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				deleteSelectedUser();
			}
		});
		
		deselectUserButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				usersListView.getSelectionModel().select(null);
			}
		});
		
		createUserButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				createUser();
			}
		});
		
		usersListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
			@Override
			public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
				//System.out.println("Selection changed to: " + users.indexOf(newValue) + " index");
				deleteSelectedUserButton.setDisable((users.indexOf(newValue) <= -1));
			}
		});
		usersListView.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {
            @Override
            public ListCell<User> call(ListView<User> p) {
            	ListCell<User> cell = new ListCell<User>() {
                    @Override
                    protected void updateItem(User user, boolean aBool) {
                        super.updateItem(user, aBool);
                        if (user != null) {
                            setText(user.getDisplayName());
                        } else {
                        	setText("");
                        }
                    }
                };
                return cell;
            }
        });
            
		users = UserManager.getUsers();
		usersListView.setItems(users);
		
		fetchUsers();
	}
	
	public void deleteSelectedUser()
	{
		User user = usersListView.getSelectionModel().getSelectedItem();
		
		if (user != null) 
		{
			try 
			{
				UserManager.deleteUser(user);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
			fetchUsers();
		}
		else
		{
			// TODO: Replace with error pop up?
			userNotificationLabel.setText("No User Selected");
		}
	}

	public void createUser() 
	{
		String username = usernameField.getText();
		String password = passwordField.getText();
		String host = hostField.getText();
		
		if (username != null && !username.isEmpty() && password != null && !password.isEmpty())
		{
			if (host == null || host.isEmpty())
			{
				host = "localhost";
			}
			
			try 
			{
				UserManager.createUser(username, password, host);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
			fetchUsers();
		}
		else
		{
			// TODO: Replace with error pop up?
			userNotificationLabel.setText("Not all required create user fields are filled out");
		}
	}
	
	public void fetchUsers() 
	{
		UserManager.fetchUsers();
	}
}
