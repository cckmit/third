package org.opoo.apps.web.servlet;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.TexturePaint;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.security.SecureRandom;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * ÑéÖ¤Âë¡£
 * 
 * @author Alex Lin(alex@opoo.org)
 * @since 1.5
 */
public class ImageConfirmationServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1173484256520006476L;
	private static final Log log = LogFactory.getLog(ImageConfirmationServlet.class);

	class HumanStroke implements Stroke {

		BasicStroke stroke;
		float distortion;

		HumanStroke(float width, float distortion) {
			super();
			stroke = new BasicStroke(width);
			this.distortion = distortion;
		}

		public Shape createStrokedShape(Shape shape) {
			GeneralPath path = new GeneralPath();
			float coords[] = new float[6];
			for (PathIterator iterator = shape.getPathIterator(null); !iterator.isDone(); iterator.next()) {
				int type = iterator.currentSegment(coords);
				switch (type) {
				case 0: // '\0'
					distort(coords, 2);
					path.moveTo(coords[0], coords[1]);
					break;

				case 1: // '\001'
					distort(coords, 2);
					path.lineTo(coords[0], coords[1]);
					break;

				case 2: // '\002'
					distort(coords, 4);
					path.quadTo(coords[0], coords[1], coords[2], coords[3]);
					break;

				case 3: // '\003'
					distort(coords, 6);
					path.curveTo(coords[0], coords[1], coords[2], coords[3], coords[4], coords[5]);
					break;

				case 4: // '\004'
					path.closePath();
					break;
				}
			}

			return stroke.createStrokedShape(path);
		}

		void distort(float coords[], int numCoords) {
			for (int i = 0; i < numCoords; i++)
				coords[i] += (float) ((Math.random() * 2D - 1.0D) * (double) distortion);

		}
	}

	class DoubleStroke implements Stroke {

		BasicStroke stroke1;
		BasicStroke stroke2;

		public Shape createStrokedShape(Shape shape) {
			Shape outline = stroke1.createStrokedShape(shape);
			return stroke2.createStrokedShape(outline);
		}

		DoubleStroke(float width1, float width2) {
			super();
			stroke1 = new BasicStroke(width1);
			stroke2 = new BasicStroke(width2);
		}
	}

	class DefaultStroke implements Stroke {
		public Shape createStrokedShape(Shape shape) {
			return shape;
		}
		DefaultStroke() {
			super();
		}
	}

	public static final int WIDTH = 290;
	public static final int HEIGHT = 80;
	SecureRandom random;

	public ImageConfirmationServlet() {
		random = new SecureRandom();
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		javax.servlet.ServletOutputStream sos = resp.getOutputStream();
		resp.setContentType("image/jpg");
		String validation = (String) req.getSession().getAttribute("validationKey");
		RenderedImage rendImage = myCreateImage(validation);
		try {
			ImageIO.write(rendImage, "jpg", sos);
		} catch (IOException e) {
			log.error("Error: " + e.getMessage());
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	private RenderedImage myCreateImage(String pString) {
		BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT, 1);
		Graphics2D g2d = bufferedImage.createGraphics();
		BufferedImage tile = new BufferedImage(25, 25, 1);
		Graphics2D tg = tile.createGraphics();
		tg.setPaint(new GradientPaint(5F, 0.0F, Color.GRAY, 0.0F, 5F, Color.BLACK));
		tg.fillRect(0, 0, 25, 25);
		g2d.setPaint(new TexturePaint(tile, new Rectangle(0, 0, 5, 5)));
		g2d.fillRect(0, 0, 290, 80);
		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke(5F));
		g2d.drawRect(0, 0, 290, 80);
		Font fonts[] = new Font[15];
		fonts[0] = new Font("Serif", 0, 36);
		fonts[1] = new Font("SansSerif", 0, 36);
		fonts[2] = new Font("Monospaced", 0, 36);
		fonts[3] = new Font("Dialog", 0, 36);
		fonts[4] = new Font("DialogInput", 0, 36);
		fonts[5] = new Font("Serif", 1, 36);
		fonts[6] = new Font("SansSerif", 1, 36);
		fonts[7] = new Font("Monospaced", 1, 36);
		fonts[8] = new Font("Dialog", 1, 36);
		fonts[9] = new Font("DialogInput", 1, 36);
		fonts[10] = new Font("Serif", 2, 36);
		fonts[11] = new Font("SansSerif", 2, 36);
		fonts[12] = new Font("Monospaced", 2, 36);
		fonts[13] = new Font("Dialog", 2, 36);
		fonts[14] = new Font("DialogInput", 2, 36);
		GlyphVector gv = fonts[random.nextInt(fonts.length)].createGlyphVector(g2d.getFontRenderContext(), pString);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Stroke strokes[] = new Stroke[5];
		strokes[0] = new DefaultStroke();
		strokes[1] = new HumanStroke(1.0F, 2.0F);
		strokes[2] = new HumanStroke(2.0F, 1.0F);
		strokes[3] = new DoubleStroke(2.0F, 1.0F);
		strokes[4] = new DoubleStroke(1.0F, 1.0F);
		java.awt.Paint fillPaint = new Color(255, 255, 255, 100);
		java.awt.Paint linePaint = Color.WHITE;
		AffineTransform transforms[] = new AffineTransform[5];
		transforms[0] = new AffineTransform();
		transforms[1] = AffineTransform.getShearInstance(-0.40000000000000002D, 0.10000000000000001D);
		transforms[2] = AffineTransform.getShearInstance(0.40000000000000002D, 0.10000000000000001D);
		transforms[3] = AffineTransform.getShearInstance(0.20000000000000001D, -0.10000000000000001D);
		transforms[4] = AffineTransform.getShearInstance(-0.20000000000000001D, -0.10000000000000001D);
		for (int i = 0; i < pString.length(); i++) {
			AffineTransform save = g2d.getTransform();
			Shape letter = gv.getGlyphOutline(i);
			g2d.translate(i * 20 + 20, 50);
			Stroke stroke = strokes[random.nextInt(strokes.length)];
			g2d.setStroke(stroke);
			AffineTransform transform = transforms[random.nextInt(transforms.length)];
			g2d.transform(transform);
			g2d.setPaint(fillPaint);
			g2d.fill(letter);
			g2d.setPaint(linePaint);
			g2d.draw(letter);
			g2d.setTransform(save);
		}

		return bufferedImage;
	}
}
