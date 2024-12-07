package simulator.UI;

public interface SIMEventListener {
    void onStart();
    void onStop();
    void onStep();
    void onReset();
}
