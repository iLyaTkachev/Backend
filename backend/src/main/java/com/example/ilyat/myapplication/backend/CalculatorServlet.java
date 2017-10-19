/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Servlet Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloWorld
*/

package com.example.ilyat.myapplication.backend;

import com.example.Result;
import com.google.gson.Gson;

import java.io.IOException;

import javax.servlet.http.*;

public class CalculatorServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest pRequest, HttpServletResponse pResponse)
            throws IOException {
        pResponse.setContentType("application/json");

        final Result result = new Result();
        try {
            final String input = pRequest.getParameter("input");
            final float res = Calculator.calculate(input);
            result.setResult(res);
        } catch (final Exception e) {
            result.setError(e.toString());
        }
        new Gson().toJson(result, pResponse.getWriter());
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String name = req.getParameter("name");
        resp.setContentType("text/plain");
        if (name == null) {
            resp.getWriter().println("Please enter a name");
        }
        resp.getWriter().println("Hello " + name);
    }
}
