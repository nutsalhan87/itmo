package org.nutsalhan87.web2.controllers;

import org.nutsalhan87.web2.beans.Shot;
import org.nutsalhan87.web2.beans.ShotHistory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

@WebServlet("/check")
public class AreaCheckServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
        getServletContext().setAttribute("shots", new ShotHistory());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=utf-8");
        int startTime = Instant.now().getNano();
        int x = Integer.parseInt(req.getParameter("X"));
        float y = Float.parseFloat(req.getParameter("Y"));
        float r = Float.parseFloat(req.getParameter("R"));
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern("dd.MM.yy HH:mm:ss");
        String date = dateFormat.format(new Date());
        boolean result = isInSquare(x, y, r) || isInCircle(x, y, r) || isInTriangle(x, y, r);
        int time = (Instant.now().getNano() - startTime) / 1000;
        Shot shot = new Shot(date, time, x, y, r, result);

        ServletContext servletContext = getServletContext();
        Object shotHistory = servletContext.getAttribute("shots");
        if (shotHistory instanceof ShotHistory shots) {
            shots.addShot(shot);
        } else {
            ShotHistory shots = new ShotHistory();
            shots.addShot(shot);
            servletContext.setAttribute("shots", shots);
        }
        req.setAttribute("shot", shot);
        servletContext.getRequestDispatcher("/front-end/checked.jsp").forward(req, resp);
    }

    boolean isInSquare(int x, float y, float r) {
        return (-x <= r) && (y <= r) && (x <= 0) && (y >= 0);
    }

    boolean isInCircle(int x, float y, float r) {
        if (x < 0 || y < 0) {
            return false;
        }
        return x * x + y * y <= r * r / 4;
    }

    boolean isInTriangle(int x, float y, float r) {
        if (x < 0 || y > 0) {
            return false;
        }
        return 2 * y >= x - r;
    }
}
