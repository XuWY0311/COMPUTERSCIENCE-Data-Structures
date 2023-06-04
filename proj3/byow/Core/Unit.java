package byow.Core;

import java.util.List;

public interface Unit {
    public Position keypoint();

    public String direction();

    public List<Position> wall();

    public List<Position> checkPoints();

    public int length();

    public int width();

}
