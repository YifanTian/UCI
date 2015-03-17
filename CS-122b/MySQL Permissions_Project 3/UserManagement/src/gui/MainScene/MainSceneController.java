package gui.MainScene;

import gui.TableCellCheckBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Separator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.DatabaseLevelManager;
import model.GlobalPrivileges;
import model.ProcedureManager;
import model.UserManager;
import report.Report;
import beans.ColumnResource;
import beans.DatabaseLevelResource;
import beans.DatabaseResource;
import beans.ProcedureResource;
import beans.TableResource;
import beans.User;

public class MainSceneController implements Initializable {

	@FXML private ChoiceBox<Object> selectUserMenu;
	@FXML private Button manageUsersButton;
	
	@FXML private Button grantAllGlobalLevelButton;
	@FXML private Button grantNoneGlobalLevelButton;
	@FXML private Button publishChangesButton;
	
	@FXML private TableView<User> userPrivilegesTableView;
	
	@FXML private Button grantAllDatabaseLevelButton;
	@FXML private Button grantNoneDatabaseLevelButton;
	
	@FXML private TreeView<DatabaseLevelResource> databaseLevelTreeView;
	@FXML private TableView<DatabaseLevelResource> databaseLevelPrivilegesTableView;
	
	@FXML private TableView<ProcedureResource> procedureLevelTableView;
	
	@FXML private Button publishAllChangesButton;
	
	@FXML private Button generateReportButton;
	
	@FXML private Button launchStoredProcedureButton;
	
	// Table Contents
	private User selectedUser;
	private DatabaseLevelResource selectedDatabaseResource;
	
	private ObservableList<User> users = FXCollections.observableArrayList();
	private ObservableList<DatabaseLevelResource> resources = FXCollections.observableArrayList();
	private ObservableList<ProcedureResource> procedures = FXCollections.observableArrayList();
	
	private ArrayList<TableColumn<User, Boolean>> userTableColumns = new ArrayList<TableColumn<User, Boolean>>();
	private ArrayList<TableColumn<DatabaseLevelResource, Boolean>> databaseLevelTableColumns = new ArrayList<TableColumn<DatabaseLevelResource, Boolean>>();
	private ArrayList<TableColumn<ProcedureResource, Boolean>> procedureTableColumns = new ArrayList<TableColumn<ProcedureResource, Boolean>>();
	
	@Override
	public void initialize(URL location, ResourceBundle resourceBundle) 
	{	
		grantAllGlobalLevelButton.setDisable(true);
		grantNoneGlobalLevelButton.setDisable(true);
		grantAllDatabaseLevelButton.setDisable(true);
		grantNoneDatabaseLevelButton.setDisable(true);
		publishAllChangesButton.setDisable(true);
		
		resources = DatabaseLevelManager.getDatabaseLevelResources();
		procedures = ProcedureManager.getProcedureResources();
		users = UserManager.getUsers();
		users.addListener(new ListChangeListener<User>() {
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends User> c) {
				fillUserMenu();
			}
		});
		
		grantAllGlobalLevelButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				selectedUser.checkAllPrivileges(true);
			}
		});
		
		grantNoneGlobalLevelButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				selectedUser.checkAllPrivileges(false);
			}
		});
		
		grantAllDatabaseLevelButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				selectedDatabaseResource.checkAllPrivileges(true);
			}
		});
		
		grantNoneDatabaseLevelButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				selectedDatabaseResource.checkAllPrivileges(false);
			}
		});

		publishAllChangesButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					GlobalPrivileges.updatePrivileges(selectedUser);
					GlobalPrivileges.updateDatabasePrivileges(selectedUser, resources, procedures);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		UserManager.fetchUsers();
		
		setupUI();
	}
	
	public void fillUserMenu()
	{
		selectUserMenu.getItems().clear();
		
		selectUserMenu.getItems().add("Select a User");
		selectUserMenu.getItems().add(new Separator());
		
		for (User user: users)
		{
			selectUserMenu.getItems().add(user.getDisplayName());
		}
		
		if (selectedUser != null)
		{
			selectUserMenu.getSelectionModel().select(selectedUser.getDisplayName());
		}
		else
		{
			selectUserMenu.getSelectionModel().select(0);
		}
	}
	
	public void setupUI()
	{
		fillUserMenu();
		
		generateReportButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) 
			{
				Report.generateReport();
			}
		});
		
		launchStoredProcedureButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					Stage stage = new Stage();
					Parent root = FXMLLoader.load(getClass().getResource("/gui/AddMovieProcedureScene/AddMovieProcedureScene.fxml"));
					stage.setScene (new Scene(root, stage.getWidth(), stage.getHeight()));
					stage.setTitle("Add Movie");
					stage.centerOnScreen();
					stage.show();
				}
				catch (IOException e) 
		        {
		            e.printStackTrace();
		        }
			}
		});
		
		manageUsersButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
		        try 
		        {
		        	Stage stage = new Stage();
		        	Parent root = FXMLLoader.load(getClass().getResource("/gui/UserManagementScene/UserManagementScene.fxml"));
		        	
		            stage.setScene(new Scene(root, stage.getWidth(), stage.getHeight()));
		        	
		            stage.setTitle("MySQL User Management");
		            stage.centerOnScreen();
		            stage.show();
		        } 
		        catch (IOException e) 
		        {
		            e.printStackTrace();
		        }
			}
		});
		
		selectUserMenu.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) 
			{
				int index = selectUserMenu.getItems().indexOf(newValue) - 2;
				selectedUser = (index >= 0 && index < users.size()) ? users.get(index) : null;
				
				if (selectedUser == null)
				{
					clearPermissionsData(true);
				}
				else
				{
					fetchAndDiaplayPermissions();
				}
			}
		});
		
		userPrivilegesTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
			@Override
			public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) 
			{
				if (newValue != null)
				{
					userPrivilegesTableView.getSelectionModel().select(null);
				}
			}
		});
		
		userPrivilegesTableView.getColumns().addListener(new ListChangeListener<Object>() {
	        @Override
	        public void onChanged(Change<?> change) 
	        {
	        	change.next();
	        	if (change.wasReplaced()) 
	        	{
	        		userPrivilegesTableView.getColumns().clear();
	        		userPrivilegesTableView.getColumns().addAll(userTableColumns);
	        	}
	        }
	    });
		
		setupDatabaseLevelTreeView();
	}
	
	public void setupDatabaseLevelTreeView() {
        TreeItem<DatabaseLevelResource> rootItem = new TreeItem<>(null);
        rootItem.setExpanded(true);
        databaseLevelTreeView.setRoot(rootItem);
        databaseLevelTreeView.setShowRoot(false);
        databaseLevelTreeView.setCellFactory(new Callback<TreeView<DatabaseLevelResource>, TreeCell<DatabaseLevelResource>>() {
            @Override
            public TreeCell<DatabaseLevelResource> call(TreeView<DatabaseLevelResource> databaseTreeView) {
                return new TreeCell<DatabaseLevelResource>() {
                    @Override
                    protected void updateItem(DatabaseLevelResource resource, boolean aBool) 
                    {
                        super.updateItem(resource, aBool);
                        if (resource != null) 
                        {
                            setText(resource.getResourceType() + ": " + resource.getResourceName());
                        } 
                        else
                        {
                        	setText("");
                        }
                    }
                };
            }
        });
        databaseLevelTreeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<DatabaseLevelResource>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<DatabaseLevelResource>> observableValue, TreeItem<DatabaseLevelResource> old_item, TreeItem<DatabaseLevelResource> newItem) {
            	if (newItem != null)
            	{
            		selectedDatabaseResource = newItem.getValue();
            		populateDatabaseLevelPermissionsTable();
            	}
            	else
            	{
            		selectedDatabaseResource = null;
            		clearDatabaseLevelPermissionsTable();
            	}
            }
        });
    }
	
	public void populateDatabaseLevelPermissionsTable()
	{
		clearDatabaseLevelPermissionsTable();
		
		for (int i = 0; i < selectedDatabaseResource.getColumnTitles().size(); i++)
		{
			TableColumn<DatabaseLevelResource, Boolean> privilegeColumn = new TableColumn<DatabaseLevelResource, Boolean>(selectedDatabaseResource.getColumnTitles().get(i));
			privilegeColumn.setEditable(true);
			privilegeColumn.setPrefWidth(90);
			privilegeColumn.setCellFactory(new Callback<TableColumn<DatabaseLevelResource, Boolean>, TableCell<DatabaseLevelResource, Boolean>>() {
	            public TableCell<DatabaseLevelResource, Boolean> call(TableColumn<DatabaseLevelResource, Boolean> p) {
	                return new TableCellCheckBox<>();
	            }
	        });
			privilegeColumn.setCellValueFactory(new Callback<CellDataFeatures<DatabaseLevelResource, Boolean>,ObservableValue<Boolean>>() {
				@Override
				public ObservableValue<Boolean> call(CellDataFeatures<DatabaseLevelResource, Boolean> param)
				{   
					return param.getValue().getPrivileges().get(databaseLevelTableColumns.indexOf(privilegeColumn));
				}
			});
			
			databaseLevelTableColumns.add(privilegeColumn);
		}
		
		databaseLevelPrivilegesTableView.getColumns().addAll(databaseLevelTableColumns);
		
		ObservableList<DatabaseLevelResource> databaseLeveLResource = FXCollections.observableArrayList();
		databaseLeveLResource.add(selectedDatabaseResource);
		databaseLevelPrivilegesTableView.setItems(databaseLeveLResource);
	}
	
	public void clearDatabaseLevelPermissionsTable()
	{
		databaseLevelTableColumns.clear();
		databaseLevelPrivilegesTableView.getColumns().clear();
		//databaseLevelPrivilegesTableView.getItems().clear();
	}
	
	public void populateTree() 
	{
		databaseLevelTreeView.getSelectionModel().clearSelection();
		databaseLevelTreeView.getRoot().getChildren().clear();
		
        for (DatabaseLevelResource db : resources) 
        {
            TreeItem<DatabaseLevelResource> leaf = new TreeItem<>(db);
            leaf.setExpanded(false);
            
            databaseLevelTreeView.getRoot().getChildren().add(leaf);
            
            generateTableTreeItemChildren(leaf, (DatabaseResource) db);
        }
        
        databaseLevelTreeView.getSelectionModel().select(0);
    }

    public void generateTableTreeItemChildren(TreeItem<DatabaseLevelResource> parent, DatabaseResource resource) 
    {
        for (TableResource table : resource.getTables()) 
        {
        	TreeItem<DatabaseLevelResource> subTreeItem = new TreeItem<>(table);
            subTreeItem.setExpanded(false);
            
            parent.getChildren().add(subTreeItem);

            generateColumnTreeItemChildren(subTreeItem, table);
        }
    }
    
    public void generateColumnTreeItemChildren(TreeItem<DatabaseLevelResource> parent, TableResource resource) 
    {
        for (ColumnResource column : resource.getColumns()) 
        {
        	TreeItem<DatabaseLevelResource> subTreeItem = new TreeItem<>(column);
            subTreeItem.setExpanded(false);
            
            parent.getChildren().add(subTreeItem);
        }
    }
	
	public void fetchAndDiaplayPermissions()
	{
		if (selectedUser == null)
		{
			clearPermissionsData(true);
			return;
		}
		else
		{
			clearPermissionsData(false);
			
			grantAllGlobalLevelButton.setDisable(false);
			grantNoneGlobalLevelButton.setDisable(false);
			grantAllDatabaseLevelButton.setDisable(false);
			grantNoneDatabaseLevelButton.setDisable(false);
			publishAllChangesButton.setDisable(false);
		}
		
		for (int i = 0; i < selectedUser.getPrivilegeTitles().size(); i++)
		{
			TableColumn<User, Boolean> tableColumn = new TableColumn<User, Boolean>(selectedUser.getPrivilegeTitles().get(i));
			tableColumn.setEditable(true);
			tableColumn.setPrefWidth(90);
			tableColumn.setCellFactory(new Callback<TableColumn<User, Boolean>, TableCell<User, Boolean>>() {
	            public TableCell<User, Boolean> call(TableColumn<User, Boolean> p) {
	                return new TableCellCheckBox<>();
	            }
	        });
			tableColumn.setCellValueFactory(new Callback<CellDataFeatures<User,Boolean>,ObservableValue<Boolean>>() {
				@Override
				public ObservableValue<Boolean> call(CellDataFeatures<User, Boolean> param)
				{   
					return param.getValue().getPrivileges().get(userTableColumns.indexOf(tableColumn));
				}   
			});
			
			userTableColumns.add(tableColumn);
		}
		
		userPrivilegesTableView.getColumns().setAll(userTableColumns);
		
		ObservableList<User> user = FXCollections.observableArrayList();
		user.add(selectedUser);
		userPrivilegesTableView.setItems(user);
		
		DatabaseLevelManager.fetchDatabaseData(selectedUser);
		
		populateTree();
		
		ProcedureManager.fetchProcedures(selectedUser);
		
		TableColumn<ProcedureResource, String> nameColumn = new TableColumn<ProcedureResource, String>(ProcedureResource.getColumnTitles().get(0));
		nameColumn.setEditable(false);
		nameColumn.setPrefWidth(150);
		nameColumn.setCellValueFactory(new PropertyValueFactory<ProcedureResource, String>("procedureName"));
		
		procedureLevelTableView.getColumns().add(nameColumn);
		
		for (int i = 1; i < ProcedureResource.getColumnTitles().size(); i++)
		{
			TableColumn<ProcedureResource, Boolean> privilegeColumn = new TableColumn<ProcedureResource, Boolean>(ProcedureResource.getColumnTitles().get(i));
			privilegeColumn.setEditable(true);
			privilegeColumn.setPrefWidth(150);
			privilegeColumn.setCellFactory(new Callback<TableColumn<ProcedureResource, Boolean>, TableCell<ProcedureResource, Boolean>>() {
	            public TableCell<ProcedureResource, Boolean> call(TableColumn<ProcedureResource, Boolean> p) {
	                return new TableCellCheckBox<>();
	            }
	        });
			privilegeColumn.setCellValueFactory(new Callback<CellDataFeatures<ProcedureResource, Boolean>,ObservableValue<Boolean>>() {
				@Override
				public ObservableValue<Boolean> call(CellDataFeatures<ProcedureResource, Boolean> param)
				{   
					int index = procedureTableColumns.indexOf(privilegeColumn);
					return param.getValue().getPrivileges().get(index);
				}
			});
			
			procedureTableColumns.add(privilegeColumn);
		}
		
		procedureLevelTableView.getColumns().addAll(procedureTableColumns);
		
		procedureLevelTableView.setItems(procedures);
	}
	
	public void clearPermissionsData(boolean disableControls)
	{
		if (disableControls)
		{
			grantAllGlobalLevelButton.setDisable(true);
			grantNoneGlobalLevelButton.setDisable(true);
			grantAllDatabaseLevelButton.setDisable(true);
			grantNoneDatabaseLevelButton.setDisable(true);
			publishAllChangesButton.setDisable(true);
		}
		
		userTableColumns.clear();
		procedureTableColumns.clear();
		
		userPrivilegesTableView.getColumns().clear();
		databaseLevelPrivilegesTableView.getColumns().clear();
		procedureLevelTableView.getColumns().clear();
		
		databaseLevelTreeView.getSelectionModel().clearSelection();
		databaseLevelTreeView.getRoot().getChildren().clear();
		
		resources.clear();
		procedures.clear();
	}
}
