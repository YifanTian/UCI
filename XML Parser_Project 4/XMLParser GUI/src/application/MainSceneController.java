package application;

import java.awt.Desktop;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import connection.Reporter;
import parser.XMLParser;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainSceneController implements Initializable{

	@FXML
	private Button runButton;
	@FXML
	private TextArea outArea;
	@FXML
	private TextField dtdField;
	@FXML 
	private TextField xmlField;
	@FXML
	private Button browseDTDButton;
	@FXML
	private Button browseXMLButton;
	@FXML
	private Button clearButton;
	@FXML
	private Label genrelabel;
	@FXML
	private Label peoplelabel;
	@FXML
	private Label booklabel;
	@FXML
	private Label publisherlabel;
	@FXML
	private Label documentlabel;
	@FXML
	private Label mappinglabel;
	@FXML
	private Label timelabel;
	@FXML
	private Label countlabel;
	
	private String dtdFilePath;
	private String xmlFilePath;
	private Desktop desktop = Desktop.getDesktop();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
				
		runButton.disableProperty().bind(dtdField.textProperty().isEmpty());
		runButton.disableProperty().bind(xmlField.textProperty().isEmpty());
		
		runButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				dtdFilePath = dtdField.getText();
				xmlFilePath = xmlField.getText();
				XMLParser parser = new XMLParser(dtdFilePath, xmlFilePath);
				long startTime = System.currentTimeMillis();
				String result = parser.parse();
				int count = parser.getCount();
				long endTime = System.currentTimeMillis();
				timelabel.setText(String.valueOf((endTime - startTime)));
				outArea.setText(result);
				countlabel.setText(String.valueOf(count));
				ArrayList<Integer> report = Reporter.report();
				genrelabel.setText(report.get(3).toString());
				peoplelabel.setText(report.get(4).toString());
				mappinglabel.setText(report.get(0).toString());
				booklabel.setText(report.get(1).toString());
				publisherlabel.setText(report.get(5).toString());
				documentlabel.setText(report.get(2).toString());
				
			}
		});
		
		browseDTDButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				FileChooser fc = new FileChooser();
				File f = fc.showOpenDialog(new Stage());
				dtdField.setText(f.getAbsolutePath());  
			}
			
		});
		
		browseXMLButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				FileChooser fc = new FileChooser();
				File f = fc.showOpenDialog(new Stage());
				xmlField.setText(f.getAbsolutePath());
			}
			
		});
		
		clearButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				outArea.clear();
				genrelabel.setText("");
				peoplelabel.setText("");
				mappinglabel.setText("");
				booklabel.setText("");
				publisherlabel.setText("");
				documentlabel.setText("");
				timelabel.setText("");
				countlabel.setText("");
				
			}
			
		});
		
		
		
	}

}
