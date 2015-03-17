package gui.AddMovieProcedureScene;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import employee.AddManager;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class AddMovieProcedureController implements Initializable {

	@FXML
	private Button runButton;
	@FXML
	private TextField titleTextField;
	@FXML
	private TextField yearTextField;
	@FXML
	private TextField directorTextField;
	@FXML
	private TextField bannerTextField;
	@FXML
	private TextField trailerTextField;
	@FXML
	private TextField genreTextField;
	@FXML
	private TextField firstTextField;
	@FXML
	private TextField lastTextField;
	@FXML
	private TextField DOBTextField;
	@FXML
	private TextField photoTextField;
	@FXML
	private TextField usernameTextField;
	@FXML
	private TextField passwordTextField;
	@FXML
	private Button loginButton;
	@FXML
	private TextArea resultArea;

	private String title;
	private int year;
	private String director;
	private String banner;
	private String trailer;
	private String genre;
	private String first;
	private String last;
	private java.sql.Date DOB;
	private String photo;
	private String username;
	private String password;

	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{	
		disableFields();
		
		loginButton.disableProperty().bind(
				Bindings.isEmpty(usernameTextField.textProperty()));
		loginButton.disableProperty().bind(
				Bindings.isEmpty(passwordTextField.textProperty()));
		loginButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				disableFields();
				username = usernameTextField.getText();
				password = passwordTextField.getText();
				if (AddManager.login(username, password)) {
					usernameTextField.setEditable(false);
					passwordTextField.setEditable(false);
					enableFields();
				} else {
					
				}

			}
		});

		runButton.disableProperty().bind(
				Bindings.isEmpty(titleTextField.textProperty()));
		runButton.disableProperty().bind(
				Bindings.isEmpty(yearTextField.textProperty()));
		runButton.disableProperty().bind(
				Bindings.isEmpty(directorTextField.textProperty()));
		runButton.disableProperty().bind(
				Bindings.isEmpty(bannerTextField.textProperty()));
		runButton.disableProperty().bind(
				Bindings.isEmpty(trailerTextField.textProperty()));
		runButton.disableProperty().bind(
				Bindings.isEmpty(genreTextField.textProperty()));
		runButton.disableProperty().bind(
				Bindings.isEmpty(firstTextField.textProperty()));
		runButton.disableProperty().bind(
				Bindings.isEmpty(lastTextField.textProperty()));
		runButton.disableProperty().bind(
				Bindings.isEmpty(DOBTextField.textProperty()));
		runButton.disableProperty().bind(
				Bindings.isEmpty(photoTextField.textProperty()));

		runButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				title = titleTextField.getText();
				year = Integer.parseInt(yearTextField.getText());
				director = directorTextField.getText();
				banner = bannerTextField.getText();
				trailer = trailerTextField.getText();
				genre = genreTextField.getText();
				first = firstTextField.getText();
				last = lastTextField.getText();
				DOB = Date.valueOf(DOBTextField.getText());
				photo = photoTextField.getText();
				String result = AddManager.send(title, year, director, banner,
						trailer, genre, first, last, DOB, photo);
				resultArea.setText(result);
				titleTextField.clear();
				yearTextField.clear();
				directorTextField.clear();
				bannerTextField.clear();
				trailerTextField.clear();
				genreTextField.clear();
				firstTextField.clear();
				lastTextField.clear();
				DOBTextField.clear();
				photoTextField.clear();

				usernameTextField.setEditable(true);
				passwordTextField.setEditable(true);
			}

		});
	}

	public void disableButton() {
		runButton.setDisable(true);
	}

	public void disableFields() {
		titleTextField.setDisable(true);
		yearTextField.setDisable(true);
		directorTextField.setDisable(true);
		bannerTextField.setDisable(true);
		trailerTextField.setDisable(true);
		genreTextField.setDisable(true);
		firstTextField.setDisable(true);
		lastTextField.setDisable(true);
		DOBTextField.setDisable(true);
		photoTextField.setDisable(true);
	}
	public void enableFields() {
		titleTextField.setDisable(false);
		yearTextField.setDisable(false);
		directorTextField.setDisable(false);
		bannerTextField.setDisable(false);
		trailerTextField.setDisable(false);
		genreTextField.setDisable(false);
		firstTextField.setDisable(false);
		lastTextField.setDisable(false);
		DOBTextField.setDisable(false);
		photoTextField.setDisable(false);
	}

}
