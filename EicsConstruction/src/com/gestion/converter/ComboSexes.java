package com.gestion.converter;
/*package com.gest.converter;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import com.gest.model.Sexe;


public class ComboSexes implements Converter {
	
		//Constructeur
	private ComboSexes(){
		listSexe = new ArrayList<Sexe>();
		chargerListSexe(listSexe, 2);
	}
	
	 private final static String[] libSexe;
	static {  
        libSexe = new String[2];  
        libSexe[0] = "M";  
        libSexe[1] = "F";  
    }  
	
	private List<Sexe> listSexe;
	private Sexe selectedSexe;
	
	 private void chargerListSexe(List<Sexe> list, int size) {
	        for(int i = 0 ; i < size ; i++){
	        	Sexe sx  = new Sexe();
	        	sx.setCodeSexe(i+1);
	        	//sx.setLibelleSexe(libSexe[i]);
	        	list.add(sx);
	        }
	    } 

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
		System.out.println("Méthode converter getAsObject sexe appelée");//Clean After

		// TODO Auto-generated method stub
		if (submittedValue.trim().equals("")) {
        	
            return null;
        } else {
            try {
            	
                for (Sexe sx : getListSexe()) {
                    if (sx.getLibelleSexe().equals(submittedValue)) {
                    	System.out.println("***Valeur de choix"+submittedValue);//Clean After
                        return sx;
                    }
                }
                
            } catch(Exception exception) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid sexe"));
            }
        }
	return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
		// TODO Auto-generated method stub
		System.out.println("Méthode converter getAsString sexe appelée");//Clean After
		 if (value == null || value.equals("")) {
	            return "";
	        } else {
	            return null;
	        }
	}
	
	//-------------------------------Getter and Setter--------------------------------//

	
	public List<Sexe> getListSexe() {
		listSexe = new ArrayList<>();
		for(Object sx : getObjectService().getObjects("Sexe"))
			listSexe.add((Sexe) sx);
		
		return listSexe;
	}

	public void setListSexe(List<Sexe> listSexe) {
		this.listSexe = listSexe;
	}

	public Sexe getSelectedSexe() {
		return selectedSexe;
	}

	public void setSelectedSexe(Sexe selectedSexe) {
		this.selectedSexe = selectedSexe;
	}

}
*/