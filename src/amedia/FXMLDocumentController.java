
package amedia;

import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import java.io.File;
import java.net.URL;
import javafx.util.Duration;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import static jdk.nashorn.internal.runtime.GlobalFunctions.anonymous;

/**
 *
 * @author gupta
 */
public class FXMLDocumentController implements Initializable {
    private MediaPlayer mediaPlayer;
    
    @FXML
    private MediaView mediaView;
    
    private String filePath;
    
    @FXML
    private Slider slider;
    
    @FXML
    private Slider seekSlider;

    
    @FXML
    private void handleButtonAction(ActionEvent event) 
    {
        
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Select a File(*.mp4)", "*.mp4");
            fileChooser.getExtensionFilters().add(filter);
            File file = fileChooser.showOpenDialog(null);
            filePath = file.toURI().toString();
            
            if(filePath != null)
            {
                Media media = new Media(filePath);
                mediaPlayer = new MediaPlayer(media);
                mediaView.setMediaPlayer(mediaPlayer);
                    DoubleProperty width = mediaView.fitWidthProperty();
                    DoubleProperty hight = mediaView.fitHeightProperty();
                    
                    width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
                    hight.bind(Bindings.selectDouble(mediaView.sceneProperty(), "hight"));
                    
                    slider.setValue(mediaPlayer.getVolume()*100);
                    slider.valueProperty().addListener(new InvalidationListener(){
                    @Override
                    public void invalidated(Observable observable) {
                        mediaPlayer.setVolume(slider.getValue()/100); //To change body of generated methods, choose Tools | Templates.
                    }
                        
                    });
                    
                    mediaPlayer.setOnReady(()->{
                //When player gets ready...
                    seekSlider.setMin(0);
                    seekSlider.setMax(mediaPlayer.getMedia().getDuration().toMinutes());
                    seekSlider.setValue(0);
                     });    
                    
                    
                    mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>(){
                    @Override
                    public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                        //seekSlider.setValue(newValue.toSeconds());
                        Duration d=mediaPlayer.getCurrentTime();
                        seekSlider.setValue(d.toMinutes());
                    }
                    });
                    
                    seekSlider.valueProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
                {
                    if (seekSlider.isPressed())
                    {
                        double val=seekSlider.getValue();
                        mediaPlayer.seek(new Duration(val*60*1000));
                    }
                }
            });
                    
                     
                  
                
                
                  
                mediaPlayer.play();
            }
            
            
    }
    
    @FXML
    private void pauseVideo(ActionEvent event)
    {
        mediaPlayer.pause();
    }
    
    @FXML
    private void playVideo(ActionEvent event)
    {
        mediaPlayer.play();
        mediaPlayer.setRate(1);
    }
    
    @FXML
    private void stopVideo(ActionEvent event)
    {
        mediaPlayer.stop();
    }
    
    @FXML
    private void fastVideo(ActionEvent event)
    {
        mediaPlayer.setRate(1.5);
    }
    
    @FXML
    private void fasterVideo(ActionEvent event)
    {
        mediaPlayer.setRate(2);
    }
    
    @FXML
    private void slowVideo(ActionEvent event)
    {
        mediaPlayer.setRate(.75);
    }
    
    @FXML
    private void slowerVideo(ActionEvent event)
    {
        mediaPlayer.setRate(.5);
    }
    
    @FXML
    private void exitVideo(ActionEvent event)
    {
        System.exit(0);
    }
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

   
    
}
