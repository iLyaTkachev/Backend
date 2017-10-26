package com.example.ilyat.myapplication.backend;

import com.example.Result;
import com.example.Version;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VersionServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest pRequest, HttpServletResponse pResponse)
            throws IOException {
        pResponse.setContentType("application/json");
        Version version = new Version();
        String ver = ResourceBundle.getBundle("app").getString("application.version");
        version.setVersionCode(Integer.valueOf(ver));
        version.setHardUpdate(false);
        new Gson().toJson(version, pResponse.getWriter());
    }
}
