package com.gestion.converter;
/*package com.gest.converter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import com.gest.model.Nationalite;
public class ComboNationalite implements Converter ,Serializable {
	
	public ComboNationalite(){
		listNationalite = new ArrayList<>();
		chargerListNationalite(listNationalite, 3);
	}
	
	private List<Nationalite> listNationalite;
	private Nationalite selectedNationalite = new Nationalite();
	
    private final static String[] libnationalite; 
	static {  
        libnationalite = new String[3];  
        libnationalite[0] = "Côte d'Ivoire";
        libnationalite[1] = "Gabon";
        libnationalite[2] = "Ghana";
    } 
	

	private void chargerListNationalite(List<Nationalite> list, int size) {  
        for(int i = 0 ; i < size ; i++){
        	Nationalite nat = new Nationalite();
        	nat.setCodeNationalite(i+1);
        	nat.setLibelleNationalite(libnationalite[i]);
        	list.add(nat);
        }
        
    } 

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
		System.out.println("Méthode converter getAsObject nationalité appelée");//Clean After

		// TODO Auto-generated method stub
		if (submittedValue.trim().equals("")) {
        	
            return null;
        } else {
            try {
            	
                for (Nationalite maNat : getListNationalite()) {
                    if (maNat.getLibelleNationalite().equalsIgnoreCase(submittedValue)) {
                        return maNat;
                    }
                }
                
            } catch(Exception exception) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid Nationalité"));
            }
        }
	return null;
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
		// TODO Auto-generated method stub
		System.out.println("Méthode converter getAsString nationalité appelée");//Clean After
		 if (value == null || value.equals("")) {
	            return "";
	        } else {
	            return ((Nationalite) value).getLibelleNationalite();
	        }
	}
	
	//-------------------------------Getter and Setter--------------------------------//

	
	public List<Nationalite> getListNationalite() {
		listNationalite = new ArrayList<>();
		for(Object uneNat : getObjectService().getObjects("Nationalite"))
			listNationalite.add((Nationalite) uneNat);
		return listNationalite;
	}

	public void setListNationalite(List<Nationalite> listNationalite) {
		this.listNationalite = listNationalite;
	}

	public Nationalite getSelectedNationalite() {
		return selectedNationalite;
	}

	public void setSelectedNationalite(Nationalite selectedNationalite) {
		this.selectedNationalite = selectedNationalite;
	}
	
	

}
*/