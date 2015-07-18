package topLevel;

import java.util.List;

import geometryHelp.Line;
import geometryHelp.Point;

public interface Renderer {
	public abstract void rCircle(Point center, double radius);
	public abstract void rLine(Line toRender);
	public abstract void rPoint(Point point);
	public abstract void rText(String text, double vertOffset, double textHeight);
	public abstract void rPolygon(List<Point> vertices);
}
