package muchbeer.raum.com.livedatamodel.screen;

import java.util.List;

import muchbeer.raum.com.data.model.Movie;

public interface MainScreen {

    void updateData(List<Movie> data);
    void setError(String msg);
}
