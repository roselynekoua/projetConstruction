package com.gestion.managedbean.admin;

import java.io.FileInputStream;
import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class MyBean {
	private String imagefile;

    public void action() {
        setImagefile("c:/imageTest/papaWemba.jpg");
    }
    
    public void showImage() {
    	action();
        try {
            // Get image file.
            FileInputStream in = new FileInputStream(getImagefile());

            // Get image contents.
            int length = in.available();
            byte[] bytes = new byte[length];
            in.read(bytes);
            in.close();

            // Get response.
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
            
            // Write image contents to response.
            response.setContentType("image/jpeg");
            response.setContentLength(length);
            response.getOutputStream().write(bytes);
            context.responseComplete();
        } catch (IOException e) {
            System.out.println("Showing image failed, I/O error");
        }
    }

    public String getImagefile() {
        return imagefile;
    }

    public void setImagefile(String imagefile) {
        this.imagefile = imagefile;
    }
}
