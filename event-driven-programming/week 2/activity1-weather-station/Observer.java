public interface Observer {

// Called by the Subject whenever its state changes.
// The three parameters carry the updated data (push model).
void update(float temperature, float humidity, float pressure);
}
