// ============================================================
// LEGACY SUBJECT using java.util.Observable (Deprecated!)
// ============================================================
// Notice: we must EXTEND Observable. This is a key limitation
// because Java only allows single inheritance.
@SuppressWarnings("deprecation")
public class Legacyweatherdata extends Observable {

private float temperature;
private float humidity;

public void setMeasurements(float temperature, float humidity) {
this.temperature = temperature;
this.humidity = humidity;

// Required before notifying (marks the object as changed)
setChanged();

// Notify all observers (no data passed — pull model)
notifyObservers();
}

public float getTemperature() { return temperature; }
public float getHumidity() { return humidity; }
}
Create: LegacyDisplay.java
java
import java.util.Observable;
import java.util.Observer;

// ============================================================
// LEGACY OBSERVER using java.util.Observer (Deprecated!)
// ============================================================
// The update() method takes the Observable and an Object arg.
@SuppressWarnings("deprecation")
public class LegacyDisplay implements Observer {

@Override
public void update(Observable o, Object arg) {
if (o instanceof Legacyweatherdata) {
Legacyweatherdata data = (Legacyweatherdata) o;
System.out.println("Legacy Display -> Temp: "
+ data.getTemperature() + "°C, Humidity: "
+ data.getHumidity() + "%");
}
}
}
Create: LegacyMain.java
java
public class LegacyMain {
@SuppressWarnings("deprecation")
public static void main(String[] args) {

Legacyweatherdata data = new LegacyWeatherData();
LegacyDisplay display = new LegacyDisplay();

data.addObserver(display);

data.setMeasurements(26.0f, 60.0f);
data.setMeasurements(30.0f, 75.0f);
}
}
