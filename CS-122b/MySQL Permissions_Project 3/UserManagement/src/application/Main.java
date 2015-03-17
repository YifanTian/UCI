package application;
	
import gui.PaneHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import connection.ConnectionManager;


public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) 
	{
		try 
		{
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/MainScene/MainScene.fxml"));
			
			Parent mainScene = PaneHelper.loadPaneForAnchorParentWithFXMLLoader(fxmlLoader);
			
			Scene scene = new Scene(mainScene, primaryStage.getWidth(), primaryStage.getHeight());
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setScene(scene);
			
			primaryStage.setTitle("MySQL User Privilege Management");
			
			// TODO: Sort out min + max heights
			primaryStage.setMinWidth(600);
			primaryStage.setMinHeight(520);
			
			primaryStage.centerOnScreen();
			primaryStage.show();
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void stop() throws Exception 
	{
		ConnectionManager.closeSessionConnection();
		super.stop();
	}
	
	public static void main(String[] args) {
		launch(args);
		//ConsoleManager.run();
	}
}
